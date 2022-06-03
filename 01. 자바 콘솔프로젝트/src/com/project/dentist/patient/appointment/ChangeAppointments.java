package com.project.dentist.patient.appointment;

import java.util.Calendar;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Login;
import com.project.dentist.Output;
import com.project.dentist.admin.adminDiagnosis.SearchData;
import com.project.dentist.dataClass.Appointment;
import com.project.dentist.dataClass.Diagnosis;
import com.project.dentist.dataClass.Doctor;

/**
 * 환자의 다음 진료 예약 정보를 변경하는 클래스입니다.
 * @author 고민지
 * @author 김승연
 *
 */
public class ChangeAppointments extends Appointments{ //XXX

	private Scanner scan;
	private Calendar now;
	private Appointment beforeAppointment;

	/**
	 * Scanner과 Calendar 참조변수에 새로운 객체를 생성하는 생성자입니다.
	 */
	public ChangeAppointments() {
		super();
		this.scan = new Scanner(System.in);
		this.now = Calendar.getInstance();
	}
	
	/**
	 * 예약 변경 전 과정의 흐름을 관리하는 메소드입니다.
	 */
	public void main() {
		
		boolean loop = true;
		while (loop) {
			
			Output.subMenuStart("예약 변경");
			String[] menu = { "예약일자 변경", "의사 변경", "진료구분 변경", "상위메뉴" }; 
			Output.subMenuContent(menu);
			Output.subMenuEnd();
			
			System.out.print("번호 입력: ✎ ");
			String input = scan.nextLine();
			System.out.println();
			
			if (input.equals("1")) {
				changeDate();
				
			} else if (input.equals("2")) {
				changeDr();

			} else if (input.equals("3")) {
				changeClassification();
			
			} else if (input.equals("0")) {
				loop = false;
				
			} else { 
				System.out.println("올바른 번호를 입력해주세요.");
				System.out.println();
			}
				
		}
	}
	
	/**
	 * 예약 정보 중 예약 일자 및 시간을 변경하는 메소드입니다.
	 */
	public void changeDate() {
		
		SearchData s = new SearchData();
		
		beforeAppointment = hasAppointment();
		setTheDoctor(s.findDoctor(beforeAppointment.getDoctorNum()));
		
		Output.subMenuStart("예약일자 변경");
		
		showScheduleTable();
		
		chooseDate();
		
		if(chooseTime()) {
			Output.subMenuEnd();
			makeAppointment();
		} else {
			System.out.println("⚠ 선택하신 시간으로는 예약을 변경하실 수 없습니다. ☑ 표시된 시간을 입력해주세요");
			System.out.println("⚠ 선택하신 시간에 대기하시려면 예약하기 메뉴를 이용해주세요.");
		}
		
	}
	
	/**
	 * 예약 정보 중 예약 의사를 변경하는 메소드입니다.
	 */
	public void changeDr() {
		
		beforeAppointment = hasAppointment();
		
		Output.subMenuStart("의사 변경");

		chooseDoctor();
		
		if(canChangeDr()) { 
			
			showBeforeAppointment();
			showAppointment(beforeAppointment.getDate(), beforeAppointment.getTime(), getTheDoctor().getSeq(), beforeAppointment.getClassficationNum());
			saveAppointment(beforeAppointment.getDate(), beforeAppointment.getTime(), getTheDoctor().getSeq(), beforeAppointment.getClassficationNum());
		
		} else {
			
			System.out.println("⚠ 선택하신 의사에게 이미 예약된 환자가 있습니다.");
			System.out.println();
			
			Output.subMenuEnd();
			setTheDateTime(beforeAppointment.getDate(), beforeAppointment.getTime());
			waiting();
		}
	}
	
	private boolean canChangeDr() {
		
		for(Appointment a : Data.alist) {
			if(a.getDate().equals(beforeAppointment.getDate())
					&& a.getTime().equals(beforeAppointment.getTime())
					&& a.getDoctorNum().equals(getTheDoctor().getSeq())) {
				return false;
			}
		}

		return true;
	}

	/** 
	 * 예약 정보 중 진료 구분을 변경하는 메소드입니다.
	 */
	public void changeClassification() {
		
		beforeAppointment = hasAppointment();
		
		Output.subMenuStart("진료구분 변경");
		Diagnosis theDiagnosis = chooseDiagnosis();
		Output.subMenuEnd();
		
		showBeforeAppointment();
		showAppointment(beforeAppointment.getDate(), beforeAppointment.getTime(), beforeAppointment.getDoctorNum(), theDiagnosis.getSeq());
		saveAppointment(beforeAppointment.getDate(), beforeAppointment.getTime(), beforeAppointment.getDoctorNum(), theDiagnosis.getSeq());
	}
	
	private void makeAppointment() { //TODO override개념...?
		
		String date = String.format("%tF", getTheDateTime());
		String time = getTheDateTime().get(Calendar.HOUR_OF_DAY) + ":00";
	
	
		if(!checkAppointment(date, time)) {
			return;
		} 
		
		showBeforeAppointment();
		showAppointment(date, time, beforeAppointment.getDoctorNum(), beforeAppointment.getClassficationNum());
		
		saveAppointment(date, time, beforeAppointment.getDoctorNum(), beforeAppointment.getClassficationNum());
	
	}
	
	 
	private Appointment hasAppointment() { 
		
		for (Appointment a : Data.alist) {
				
				if(super.getThePatient().getSeq().equals(a.getPatientNum())) {
					
					if(a.getDateTime().compareTo(now) > 0) {
						return a;
					}
				}
			}
		
		System.out.println("⚠ 오류가 발생하면 관리자한테 문의해주세요.");
		return null;
	}
	
		
	private void showBeforeAppointment() { 
	
		SearchData s = new SearchData();

		System.out.println();
		Output.subMenuStart("기존 예약정보 확인");
		System.out.printf("[환자 성명] %s\n", s.findPatient(beforeAppointment.getPatientNum()).getName());
		System.out.printf("[예약 일자] %s\n", beforeAppointment.getDate());
		System.out.printf("[예약 시간] %s\n", beforeAppointment.getTime());
		System.out.printf("[담당 의사] %s\n", s.findDoctor(beforeAppointment.getDoctorNum()).getName());
		System.out.printf("[진료 구분] %s\n", s.findClassificationName(beforeAppointment.getClassficationNum()));
		Output.subMenuEnd();

	}
	
	private void showAppointment(String date, String time, String doctorNum, String diagnosisNum) {

		SearchData s = new SearchData();
		
		Output.subMenuStart("새로운 예약정보 확인");
		System.out.printf("[환자 성명] %s\n",getThePatient().getName());
		System.out.printf("[예약 일자] %s\n", date);
		System.out.printf("[예약 시간] %s\n", time);
		System.out.printf("[담당 의사] %s\n", s.findDoctor(doctorNum).getName());
		System.out.printf("[진료 구분] %s\n", s.findClassificationName(diagnosisNum));
		Output.subMenuEnd();
	}
	
	private void saveAppointment(String date, String time, String doctorNum, String diagnosisNum) {
		
		while(true) {
			
			scan = new Scanner(System.in);
			
			System.out.print("예약을 변경하시겠습니까? (Y/N): ✎");
			String input = scan.nextLine();
			
			if(input.toUpperCase().equals("Y")) {

				beforeAppointment.setDate(date);
				beforeAppointment.setTime(time.replace(" ", ""));
				beforeAppointment.setDoctorNum(doctorNum);
				beforeAppointment.setClassficationNum(diagnosisNum);
			
				
				Data.save(DataPath.진료예약);
				System.out.println("예약이 변경되었습니다.");
				return;
				
			} else if(input.toUpperCase().equals("N")) {
				
				System.out.println("메인 메뉴로 돌아갑니다.");
				return;
				
			} else {
				System.out.println("Y 또는 N을 입력해주세요");
			}
		}
	}

	
	
}

