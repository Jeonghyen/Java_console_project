package com.project.dentist.patient.appointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Login;
import com.project.dentist.Output;
import com.project.dentist.admin.adminDiagnosis.SearchData;
import com.project.dentist.dataClass.*;

/**
 * 환자의 다음 진료 일정을 예약하거나 특정 진료 일자에 대기하는 클래스입니다.
 * @author 고민지
 *
 */
public class Appointments {

	private Scanner scan;
	private Calendar now;
	private Calendar theDateTime;
	private Doctor theDoctor;
	private ArrayList<Appointment> appointments;
	private Patient thePatient;
	

	/**
	 * 환자 모드로 로그인하여 예약하기를 이용할 때 생성되는 생성자입니다.
	 */
	public Appointments() {
		
		this.scan = new Scanner(System.in);
		this.now = Calendar.getInstance();
		
		this.theDoctor = null;
		this.theDateTime = Calendar.getInstance();
		theDateTime.set(Calendar.MINUTE, 0);
		
		this.appointments = new ArrayList<Appointment>();
		this.thePatient = Login.currentPatient;
	}
	
	/**
	 * 관리자 모드로 로그인하여 예약하기를 이용할 때 생성되는 생성자입니다.
	 * @param patient 다음 일정을 예약할 환자 객체
	 */
	public Appointments(Patient patient) {
		
		this.scan = new Scanner(System.in);
		this.now = Calendar.getInstance();
		
		this.theDoctor = null;
		this.theDateTime = Calendar.getInstance();
		theDateTime.set(Calendar.MINUTE, 0);
		
		this.appointments = new ArrayList<Appointment>();
		this.thePatient = patient;
	}
	
	public Calendar getTheDateTime() {
		return theDateTime;
	}
	
	public void setTheDateTime(String date, String time) {
		
		theDateTime.set(Integer.parseInt(date.split("-")[0])
				, Integer.parseInt(date.split("-")[1])-1
				, Integer.parseInt(date.split("-")[2])
				, Integer.parseInt(time.split(":")[0])
				, Integer.parseInt(time.split(":")[1]));
	}

	public Patient getThePatient() {
		return thePatient;
	}
	
	public Doctor getTheDoctor() {
		return theDoctor;
	}

	public void setTheDoctor(Doctor theDoctor) {
		this.theDoctor = theDoctor;
	}
	
	/**
	 * 예약 및 예약 대기 전과정의 흐름을 관리하는 메소드입니다.
	 */
	public void make() {
		
		
		Output.subMenuStart("예약하기");
		
		chooseDoctor();
		
		
		showScheduleTable();
		
		chooseDate();
		
		if(chooseTime()) {
			makeAppointment();
		} else {
			waiting();
		}
		
	}

	/**
	 * 예약 및 예약 대기 과정에서 의사 선택을 입력받는 메소드입니다.
	 */
	public void chooseDoctor() {

		System.out.println();

		while(true) {
			
			System.out.println("▶ 의사 선택 ----------------------------------");
			
			String[] allDoctors = new String[Data.dlist.size()];
			
			for(int i=0; i<Data.dlist.size(); i++) {
				allDoctors[i] = Data.dlist.get(i).getName() + " 의사";
			}
			
			Output.subMenuContent(allDoctors);

			System.out.println("-------------------------------------------");
			
			try {
				
				System.out.print("번호 입력: ✎");
				int input = scan.nextInt();

				if(1 <= input && input <= allDoctors.length) {
					System.out.println();
					this.theDoctor = Data.dlist.get(input-1);
					return;
				} else {
					throw new Exception();
				}
				
			} catch (Exception e) {
				System.out.printf("올바른 번호(1~%d)를 입력해주세요.\n", allDoctors.length);
				System.out.println();
			}
		}
		
	}
	
	/**
	 * 예약 및 예약대기 가능한 일자를 달력형식으로 출력하는 메소드입니다.
	 */
	public void showScheduleTable() {
		 
		Calendar temp = Calendar.getInstance(); //비교값 Calendar 
		Calendar scheduleDate = Calendar.getInstance();
		
		System.out.printf("\t\t\t%s월(☑ 선택가능)\n", now.get(Calendar.MONTH)+1);
		
		System.out.println("╭───────┬───────┬───────┬───────┬───────┬───────┬───────╮");
		System.out.println("|   일	|   월	|   화	|   수	|   목	|   금	|   토	|");
		System.out.println("├───────┼───────┼───────┼───────┼───────┼───────┼───────┤");
		
		
		//오늘날짜이전까지 틀만들기
		scheduleDate.set(Calendar.DATE, 1);
		if(scheduleDate.get(Calendar.DAY_OF_WEEK) > 1) {
			for(int i=0; i<scheduleDate.get(Calendar.DAY_OF_WEEK)-1; i++) {
			System.out.print("|\t");
			}
		}
		
		while(scheduleDate.get(Calendar.DATE) < temp.get(Calendar.DATE)) {
			System.out.printf("|  %2d\t", scheduleDate.get(Calendar.DATE));
			
			if(scheduleDate.get(Calendar.DAY_OF_WEEK) == 7) {
				System.out.println("|");
				System.out.println("|\t|\t|\t|\t|\t|\t|\t|");
			}
			scheduleDate.add(Calendar.DATE, 1);
		}
		
		
			
		//오늘날짜부터 한달만큼 틀만들기
		scheduleDate = Calendar.getInstance(); //-> 오늘기준 다음달 마지막날
		scheduleDate.set(Calendar.HOUR_OF_DAY, 0);
		scheduleDate.set(Calendar.MINUTE, 0);
		scheduleDate.set(Calendar.SECOND, 0);
	
		if(scheduleDate.get(Calendar.DATE) == 1) {
			temp.set(Calendar.DATE, temp.getActualMaximum(Calendar.DATE));
		} else {
			temp.add(Calendar.MONTH, 1);
			temp.set(Calendar.DATE, scheduleDate.get(Calendar.DATE) - 1);
		}
		
		
		while (scheduleDate.compareTo(temp) <= 0) {
			
			//ArrayList<Appointment> schedules = findAppointment(scheduleDate);
			
			if(scheduleDate.get(Calendar.MONTH) == now.get(Calendar.MONTH)+1
					&& scheduleDate.get(Calendar.DATE) == 1) {
				
				if(scheduleDate.get(Calendar.DAY_OF_WEEK) > 1) {
					for(int i=0; i<scheduleDate.get(Calendar.DAY_OF_WEEK)-1; i++) {
						System.out.print("|\t");
					}
				}
			}
			
			if(scheduleDate.get(Calendar.DAY_OF_WEEK) != 1) {
				System.out.printf("|  %2d☑\t", scheduleDate.get(Calendar.DATE));
			} else {
				System.out.printf("|  %2d \t", scheduleDate.get(Calendar.DATE));
			}
			
			if(scheduleDate.get(Calendar.DAY_OF_WEEK) == 7) {
				System.out.println("|");
				System.out.println("|\t|\t|\t|\t|\t|\t|\t|");
			}
			
			if(scheduleDate.get(Calendar.DATE) == scheduleDate.getActualMaximum(Calendar.DATE)) {
				if(scheduleDate.get(Calendar.DATE) == now.getActualMaximum(Calendar.DATE)) {
					System.out.println("╰───────┴───────┴───────┴───────┴───────┴───────┴──────╯");
					System.out.println();
					System.out.printf("\t\t\t%s월 (☑ 선택가능)\n", now.get(Calendar.MONTH)+2);
					System.out.println("╭───────┬───────┬───────┬───────┬───────┬───────┬───────╮");
					System.out.println("|   일	|   월	|   화	|   수	|   목	|   금	|   토	|");
					System.out.println("├───────┼───────┼───────┼───────┼───────┼───────┼───────┤");
					
				}
			}
			
			scheduleDate.add(Calendar.DATE, 1);
		}
		
		
		//한달이후부터 그 달의 마지막날까지 틀만들기
		temp.set(Calendar.DATE, scheduleDate.getActualMaximum(Calendar.DATE));
		
		while (scheduleDate.get(Calendar.DATE) < temp.get(Calendar.DATE)) {
			System.out.printf("|  %2d\t", scheduleDate.get(Calendar.DATE));
			
			if(scheduleDate.get(Calendar.DAY_OF_WEEK) == 7) {
				System.out.println("|");
				System.out.println("|\t|\t|\t|\t|\t|\t|\t|");
			}
			scheduleDate.add(Calendar.DATE, 1);
		}
		if(scheduleDate.get(Calendar.DAY_OF_WEEK) < 7) {
			for(int i=0; i<=7-scheduleDate.get(Calendar.DAY_OF_WEEK); i++) {
				System.out.print("|\t");
			}
			System.out.println("|");
		}
		
		System.out.println("╰───────┴───────┴───────┴───────┴───────┴───────┴───────╯");
		

	}
	
	/**
	 * 예약 및 예약 대기 과정에서 일자의 선택을 입력받는 메소드입니다.
	 * @return 예약 가능 일자인지 여부
	 */
	public boolean chooseDate() {
		
		while(true) {

			System.out.print("☑표시된 일자를 입력해주세요: ✎");
			
			int date = scan.nextInt();
			int month = (date >= now.get(Calendar.DATE) ? now.get(Calendar.MONTH) + 1 : now.get(Calendar.MONTH) + 2);
			int year = now.get(Calendar.YEAR);

			theDateTime.set(year, month - 1, date);
			
			appointments = findAppointment(theDateTime);
			
			if(date < 1 || date > now.getActualMaximum(Calendar.DATE) || theDateTime.get(Calendar.DAY_OF_WEEK) == 1) {
				System.out.println("예약/대기할 수 없는 일자입니다. ☑표시된 일자를 입력해주세요");
				System.out.println(); 
				
			} else {
				return false;
			} 
		}
	}
	
	private ArrayList<Appointment> findAppointment(Calendar theDate) {

		ArrayList<Appointment> temp = new ArrayList<Appointment>();
		
		for(Appointment a : Data.alist) {
			if(a.getDate().equals(String.format("%tF", theDate))){
				temp.add(a);
			} 
		}
		
		temp.sort((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()));
		
		return temp;
	}
	
	/**
	 * 예약 및 예약 대기 과정에서 시간의 선택을 입력받는 메소드입니다.
	 * @return 예약 가능한 시간인지 여부
	 */
	public boolean chooseTime() {
		
		System.out.println();
		while(true) {
			
			System.out.println("▶ 시간 선택 (☑ 예약가능 ❎ 대기가능) ----------------------");
			
			String[] openHours = {"9:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
			
			ArrayList<String> noTimes = new ArrayList<String>();
			
			
			for(Appointment a : appointments) {
				
				if(!a.getDoctorNum().equals(theDoctor.getSeq())){
					continue;
				}
				noTimes.add(a.getTime());
			}
			
				
			for(String n : noTimes) {
				for(int i=0; i<openHours.length; i++) {
					
					if(openHours[i].equals(n)) {
						openHours[i] = openHours[i] + " ❎"; 
						break;
					}
				}
			}
			
			for(int i=0; i<openHours.length; i++) {
				
				if(!openHours[i].contains("❎")) {
					openHours[i] = openHours[i] + " ☑"; 
				}
			}
			
			Output.subMenuContent(openHours);
			
			System.out.println("-------------------------------------------");
			
			try {
				
				System.out.print("번호 입력: ✎");
				int input = scan.nextInt();

				if(1 <= input && input <= openHours.length) {
					
					String result = openHours[input-1].substring(openHours[input-1].length() - 1);
					String[] time = openHours[input-1].substring(0, 5).replace(" ", "").split(":");
					
					theDateTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
					
					if(result.equals("☑")) {
						return true;
					} else if(result.equals("❎")) {
						return false;
					}
					
				} else {
					throw new Exception();
				}
				
			} catch (Exception e) {
				System.out.printf("올바른 번호(1~%d)를 입력해주세요.\n", openHours.length);
				System.out.println();
			}
			
		}	
		
	}

	private boolean hasAppointment() { //XXX 수정(추가)
		
		for (Appointment a : Data.alist) {
			
			if(thePatient.getSeq().equals(a.getPatientNum())) {
				
				if(a.getDateTime().compareTo(now) > 0) {
					
					SearchData s = new SearchData();

					System.out.println("⚠ 이미 다음 진료일자가 예약되어있습니다.");
					System.out.println();
					Output.subMenuStart("진료 예약정보");
					System.out.printf("[환자 성명] %s\n",thePatient.getName());
					System.out.printf("[예약 일자] %s\n", a.getDate());
					System.out.printf("[예약 시간] %s\n", a.getTime());
					System.out.printf("[담당 의사] %s\n", s.findDoctor(a.getDoctorNum()).getName());
					System.out.printf("[진료 구분] %s\n", s.findClassificationName(a.getClassficationNum()));
					Output.subMenuEnd();
					System.out.println("⚠ 재예약을 하시려면 취소 후 이용하시거나 변경을 이용하시기 바랍니다.");
					System.out.println();
					return true;
				}
			}
		}
				
		return false;
	}

	/**
	 * 예약 불가능한 일자에 예약 대기를 하는 메소드입니다.
	 */
	public void waiting() {

		String date = String.format("%tF", theDateTime);
		String time = theDateTime.get(Calendar.HOUR_OF_DAY) + ":00";
		
		String appointmentSeq = null;
		String appointmentPatientSeq = null; 
		
		for(Appointment a : appointments) {
			if(a.getDoctorNum().equals(theDoctor.getSeq())) {
				if(a.getTime().equals(time)) {
					appointmentSeq = a.getSeq();
					appointmentPatientSeq = a.getPatientNum(); 
				}
			}
		}
		
		if(appointmentPatientSeq.equals(thePatient.getSeq())) {
			Output.subMenuEnd();
			System.out.println("⚠ 이미 선택하신 날짜에 예약되어있습니다.");
			System.out.println();
			return;
		}
		
		for(WaitingList w : Data.wlist) {
			
			if(w.getAppointmentSeq().equals(appointmentSeq)) {
				
				if(w.getPatientSeq1().equals(thePatient.getSeq())
					||w.getPatientSeq2().equals(thePatient.getSeq())
					||w.getPatientSeq3().equals(thePatient.getSeq())){
					
					Output.subMenuEnd();
					System.out.println("⚠ 이미 선택하신 날짜에 대기되어있습니다.");
					System.out.println();
					return;
				}
							
				if(w.getPatientSeq2().equals("null")) {
					checkWaiting(date, time, w, 2);
					return;
				} else if(w.getPatientSeq3().equals("null")) {
					checkWaiting(date, time, w, 3);
					return;
				} else {
					Output.subMenuEnd();
					System.out.println("⚠ 현재 대기 인원이 가득차서 대기할 수 없습니다.");
					System.out.println();
					return;
				}
			}
		}
		
		checkWaiting(date, time, new WaitingList(findMaxSeq_wlist(), appointmentSeq, thePatient.getSeq()) , 1);
	}
	
	private String findMaxSeq_wlist() {
		
		int max = 0;
		
		for(WaitingList w : Data.wlist) {
			if(Integer.parseInt(w.getSeq()) > max) { 
				max = Integer.parseInt(w.getSeq());
			}
		}
		
		return "" + (max + 1);
		
	}

	private void checkWaiting(String date, String time, WaitingList w, int waitingNum) {
		
		System.out.println("▶ 대기 확인 ----------------------------------");
		System.out.printf("대기일자: %s %s\n", date, time);
		System.out.printf("대기인원: %s명\n", waitingNum - 1);
		System.out.printf("대기순번: %s\n", waitingNum);
		Output.subMenuEnd();
		
		while(true) {
			scan = new Scanner(System.in);
			System.out.print("대기하시겠습니까? (Y/N): ✎");
			String input = scan.nextLine();
			
			if(input.toUpperCase().equals("Y")) {
				
				switch(waitingNum) {
					
					case 1: Data.wlist.add(w);
							break;
					case 2: w.setPatientSeq2(thePatient.getSeq());
							break;
					case 3: w.setPatientSeq3(thePatient.getSeq());
							break;
				}
				
				Data.save(DataPath.예약대기);
				System.out.println("대기 완료하였습니다.");
				return;
				
			} else if(input.toUpperCase().equals("N")) {
				System.out.println("상위 메뉴로 돌아갑니다.");
				return;
			} else {
				System.out.println("Y 또는 N을 입력해주세요.");
			}
		}
	}

	private void makeAppointment() {
		
		if(hasAppointment()) {
			return;
		}
		
		String date = String.format("%tF", theDateTime);
		String time = theDateTime.get(Calendar.HOUR_OF_DAY) + ":00";
	
		if(!checkAppointment(date, time)) {
			return;
		} 
		
		Diagnosis diagnosis = chooseDiagnosis();
		
		checkAppointment(date, time, diagnosis.getDiagnosis_name());
		
		saveAppointment(date, time, diagnosis);
	}

	
	/**
	 * 예약 과정에서 입력받은 일자, 시간, 의사, 진료구분으로 예약할지 여부를 확인하는 메소드입니다. 
	 * @param date 예약 일자
	 * @param time 예약 시간
	 * @return 예약 확정 여부
	 */
	public boolean checkAppointment(String date, String time) {
		
		while(true) {
			scan = new Scanner(System.in);

			System.out.println();
			String tempDate = ( date.split("-")[2].equals("10") 
								|| date.split("-")[2].equals("20")
								|| date.split("-")[2].equals("30")) ? date.split("-")[2] : date.split("-")[2].replace("0", "");
			System.out.printf("%s월 %s일 %s시에 예약하시겠습니까? (Y/N): ✎", date.split("-")[1].replace("0", ""), tempDate, time.split(":")[0]);
			String input = scan.nextLine();
			System.out.println();
			
			if(input.toUpperCase().equals("Y")) {
				return true;
			} else if(input.toUpperCase().equals("N")) {
				System.out.println();
				return false;
			} else {
				System.out.println("Y 또는 N을 입력해주세요.");
			}	
		}
	}

	private void saveAppointment(String date, String time, Diagnosis diagnosis) {
		
		while(true) {
			
			scan = new Scanner(System.in);
			
			System.out.print("예약을 확정하시겠습니까? (Y/N): ✎");
			String input = scan.nextLine();
			
			if(input.toUpperCase().equals("Y")) {
				
				Data.alist.add(new Appointment(findMaxSeq()
												, thePatient.getSeq()
												, theDoctor.getSeq()
												, date
												, time
												, diagnosis.getSeq())); //XXX 수정(변경 getName -> seq)
				
				Data.save(DataPath.진료예약);
				System.out.println("예약이 확정되었습니다.");
				return;
				
			} else if(input.toUpperCase().equals("N")) {
				
				System.out.println("메인 메뉴로 돌아갑니다.");
				return;
				
			} else {
				System.out.println("Y 또는 N을 입력해주세요");
			}
		}
	}

	private String findMaxSeq() {
		
		int max = 0;
		
		for(Appointment a : Data.alist) {
			if(Integer.parseInt(a.getSeq()) > max) { 
				max = Integer.parseInt(a.getSeq());
			}
		}
		return "" + (max + 1);
	}

		
	/**
	 * 예약 및 예약 대기 과정에서 증상의 선택을 입력받는 메소드입니다.
	 * @return
	 */
	public Diagnosis chooseDiagnosis() {
		
		while(true) {
			System.out.println("▶ 진료 구분 선택 ------------------------------");
			String[] diagnosises = new String[Data.clist.size()];
			for(int i=0; i<Data.clist.size(); i++) {
				diagnosises[i] = Data.clist.get(i).getDiagnosis_name();
			}
			Output.subMenuContent(diagnosises);
			System.out.println("-------------------------------------------");
			
			try {
				
				System.out.print("번호 입력: ✎");
				int input = scan.nextInt();

				if(input >= 1 && input <= Data.clist.size()) {
					System.out.println();
					return Data.clist.get(input-1);
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.printf("올바른 번호(1~%d)를 입력해주세요.\n", Data.clist.size());
				System.out.println();
			}
		}		
		
	}

	private void checkAppointment(String date, String time, String diagnosisName) {

		System.out.println("▶ 예약 확인 ----------------------------------");
		System.out.printf("예약일자: %s %s\n", date, time);
		System.out.printf("담당의사: %s\n", theDoctor.getName());
		System.out.printf("진료구분: %s\n", diagnosisName);
		Output.subMenuEnd();
	}


	
	


	
}
