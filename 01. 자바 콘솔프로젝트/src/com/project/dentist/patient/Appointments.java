package com.project.dentist.patient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Login;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Appointment;
import com.project.dentist.dataClass.Diagnosis;
import com.project.dentist.dataClass.Doctor;
import com.project.dentist.dataClass.WaitingList;

public class Appointments {

	private Scanner scan;
	private Calendar now;
	private boolean loop;

	public Appointments() {
		this.scan = new Scanner(System.in);
		this.now = Calendar.getInstance();
		this.loop = true;
	}
	
	public void make() {
		Output.subMenuStart("예약하기");
		printScheduleTable();
		
		loop = true;
		while(loop) {

			System.out.print("☑또는 ❎표시된 일자를 입력해주세요: ✎");
			int date = scan.nextInt();

			int year = now.get(Calendar.YEAR);
			int month = (date >= now.get(Calendar.DATE) ? now.get(Calendar.MONTH) + 1 : now.get(Calendar.MONTH) + 2);
			Calendar theDate = Calendar.getInstance();
			theDate.set(year, month - 1, date);
			ArrayList<Appointment> appointments = findAppointment(theDate);
			
			
			if(date < 1 || date > now.getActualMaximum(Calendar.DATE)) {
				
				System.out.println("예약/대기할 수 없는 일자입니다. ☑또는 ❎ 표시된 일자를 입력해주세요");
				System.out.println();
				
			} else if (appointments.size() < 8) {	
				
				scan = new Scanner(System.in);
				
				System.out.printf("%s월 %s일을 선택하시겠습니까? (Y/N): ✎", month, date);
				String input = scan.nextLine();
				
				if(input.toUpperCase().equals("Y")) {
					makeAppointment(String.format("%tF", theDate), appointments);
					loop = false;
				} else if(input.toUpperCase().equals("N")) {
					System.out.println();
				} else {
					System.out.println("Y 또는 N을 입력해주세요.");
				}
				
			} else {
				//waiting(String.format("%tF", theDate));
				loop = false;
			} 
		}
	}

//	private void waiting(String date) {
//		
//		System.out.println("▶ 시간 선택 ----------------------------------");
//		String[] openHours = {"9:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
//		Output.subMenuContent(openHours);
//		System.out.println("-------------------------------------------");
//
//		String time = null;
//		loop = true;
//		while(loop) {	
//			try {
//				
//				System.out.print("번호 입력: ✎");
//				int input = scan.nextInt();
//
//				if(input >= 1 && input <= openHours.length) {
//					time = openHours[input-1];
//					loop = false;
//				} else {
//					System.out.printf("올바른 번호(1~%d)를 입력해주세요.\n", openHours.length);
//				}
//
//			} catch (Exception e) {
//				System.out.println();
//			}
//		}	
//	
//		Appointment appointment = null;
//		for(Appointment a : Data.alist) {
//			if(a.getDate().equals(date) && a.getTime().equals(time)) {
//				appointment = a;
//			}
//		}
//		WaitingList waiting = null;
//		for(WaitingList w : Data.wlist) {
//			if(w.getAppointmentSeq().equals(appointment.getSeq())){
//				waiting = w;
//			}
//		}
//		//TODO 대기할 수 없는 일자일때....
//		System.out.println("▶ 대기 확인 ----------------------------------");
//		System.out.printf("대기일자: %s %s\n", date, time);
//		System.out.printf("대기인원: %s명\n", waiting.waitingSize());
//		System.out.printf("대기순번: %s\n", if(waiting.waitingSize()));
//		Output.subMenuEnd();
//		
//		while(true) {
//			System.out.print("대기하시겠습니까? (Y/N): ✎");
//			String input = scan.nextLine();
//			
//			if(input.toUpperCase().equals("Y")) {
//				//TODO 대기 확정하기
//				System.out.println("대기 완료하였습니다.");
//				return;
//			} else if(input.toUpperCase().equals("N")) {
//				System.out.println("상위 메뉴로 돌아갑니다.");
//				return;
//			} else {
//				System.out.println("Y 또는 N을 입력해주세요.");
//			}
//		}
//		
//	}

	private void makeAppointment(String date, ArrayList<Appointment> appointments) {
		
		String time = chooseTime(appointments);
		Doctor doctor = chooseDoctor(appointments);
		Diagnosis diagnosis = chooseDiagnosis();
		checkAppointment(date, time, doctor.getName(), diagnosis.getDiagnosis_name());
		Output.subMenuEnd();

		
		loop = true;
		
		while(loop) {
			
			scan = new Scanner(System.in);
			System.out.print("예약을 확정하시겠습니까? (Y/N): ✎");
			String input = scan.nextLine();
			
			if(input.toUpperCase().equals("Y")) {
				
				Data.alist.add(new Appointment(findMaxSeq()
												, Login.currentPatient.getSeq()
												, doctor.getSeq()
												, date
												, time
												, diagnosis.getDiagnosis_name()));
				
				Data.save(DataPath.진료예약);
				System.out.println("예약이 확정되었습니다.");
				
				loop = false;
				
			} else if(input.toUpperCase().equals("N")) {
				System.out.println("메인 메뉴로 돌아갑니다.");
				loop = false;
				
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

	public String chooseTime(ArrayList<Appointment> appointments) {
		
		while(true) {
			System.out.println("▶ 시간 선택 ----------------------------------");
			
			String[] openHours = {"9:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
			
			HashSet<String> times = new HashSet<String>();
			for(String s : openHours) {
				times.add(s);
			}
			HashSet<String> noTimes = new HashSet<String>();
			for(Appointment a : appointments) {
				noTimes.add(a.getTime());
			}
			
			times.removeAll(noTimes);
			

			String[] yesTime = new String[times.size()];
			
			Iterator iter = times.iterator();
			
			for(int i=0; iter.hasNext(); i++) {
				yesTime[i] = iter.next().toString();
			}
			
			Arrays.sort(yesTime);
			for(String s : yesTime) {
				if(s.equals("9:00")) {
					for(int i=yesTime.length-2; i>=0; i--) {
						yesTime[i+1] = yesTime[i];
					}
					yesTime[0] = " 9:00";
				}
			}
			
			Output.subMenuContent(yesTime);
			System.out.println("-------------------------------------------");
			
			try {
				yesTime[0] = yesTime[0].replace(" ", ""); //출력때문에..
				System.out.print("번호 입력: ✎");
				int input = scan.nextInt();

				if(input >= 1 && input <= yesTime.length) {
					return yesTime[input-1];
				} else {
					System.out.printf("올바른 번호(1~%d)를 입력해주세요.\n", yesTime.length);
				}
				
			} catch (Exception e) {
				System.out.println();
			}
			
		}		
	}

	public Doctor chooseDoctor(ArrayList<Appointment> appointments) {

		loop = true;
		while(loop) {
			
			System.out.println("▶ 의사 선택 ----------------------------------");
			
			HashSet<String> doctors = new HashSet<String>();
			for(Doctor d : Data.dlist) {
				doctors.add(d.getSeq());
			}
			HashSet<String> noDoctors = new HashSet<String>();
			for(Appointment a : appointments) {
				noDoctors.add(a.getDoctorNum());
			}
			
			doctors.removeAll(noDoctors);
			

			Doctor[] yesDoctor = new Doctor[doctors.size()];
			
			Iterator iter = doctors.iterator();
			
			for(int i=0; iter.hasNext(); i++) {
				String seq = iter.next().toString();
				
				for(Doctor d : Data.dlist) {
					if (d.getSeq().equals(seq)) {
						yesDoctor[i] = d;
					}
				}
			}
			
			Arrays.sort(yesDoctor, (d1, d2) -> d1.getName().compareTo(d2.getName()));
			
			String[] temp = new String[yesDoctor.length];
			for(int i=0; i<yesDoctor.length; i++) {
				temp[i] = yesDoctor[i].getName() + " 의사";
			}
			
			Arrays.sort(temp);
			Output.subMenuContent(temp);
			System.out.println("-------------------------------------------");
			
			try {
				
				System.out.print("번호 입력: ✎");
				int input = scan.nextInt();

				if(input >= 1 && input <= yesDoctor.length) {
					return yesDoctor[input-1];
				} else {
					System.out.printf("올바른 번호(1~%d)를 입력해주세요.\n", yesDoctor.length);
				}
				
			} catch (Exception e) {
				System.out.println();
			}
		}
		
		return null;
	}

	public Diagnosis chooseDiagnosis() {
		
		loop = true;
		while(loop) {
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
					return Data.clist.get(input-1);
				} else {
					System.out.printf("올바른 번호(1~%d)를 입력해주세요.\n", Data.clist.size());
				}
			} catch (Exception e) {
				System.out.println();
			}
		}		
		
		return null;
	}

	private void checkAppointment(String date, String time, String doctorName, String diagnosisName) {

		System.out.println("▶ 예약 확인 ----------------------------------");
		System.out.printf("예약일자: %s %s\n", date, time);
		System.out.printf("담당의사: %s\n", doctorName);
		System.out.printf("진료구분: %s\n", diagnosisName);
		Output.subMenuEnd();
	}

	public void edit() {
		// TODO Auto-generated method stub
		
	}

	public void cancel() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private void printScheduleTable() {
		 
		Calendar temp = Calendar.getInstance(); //비교값 Calendar 
		Calendar scheduleDate = Calendar.getInstance();
		
		System.out.printf("%s월 ~ %s월\t\t(☑ 예약가능 ❎ 대기가능)\n", now.get(Calendar.MONTH)+1, now.get(Calendar.MONTH)+2);
		
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
			
			ArrayList<Appointment> schedules = findAppointment(scheduleDate);
			
			if(schedules.size() < 8) {
				System.out.printf("|  %2d☑\t", scheduleDate.get(Calendar.DATE));
			} else if (schedules.size() == 8){
				System.out.printf("|  %2d❎\t", scheduleDate.get(Calendar.DATE));
			}
			
			if(scheduleDate.get(Calendar.DAY_OF_WEEK) == 7) {
				System.out.println("|");
			}
			
			if(scheduleDate.get(Calendar.DATE) == scheduleDate.getActualMaximum(Calendar.DATE)) {
				if(scheduleDate.get(Calendar.DATE) == now.getActualMaximum(Calendar.DATE)) {
					System.out.println("├───────┼───────┼───────┼───────┼───────┼───────┼───────┤");
				} else {
//					for(int i=0; i<7-scheduleDate.get(Calendar.DAY_OF_WEEK); i++) {
//						System.out.print("|\t");
//					}
//					if(scheduleDate.get(Calendar.DAY_OF_WEEK)!=7) {
//						System.out.println("|");
//					}
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

	
}
