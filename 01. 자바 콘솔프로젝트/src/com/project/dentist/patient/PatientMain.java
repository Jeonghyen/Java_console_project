package com.project.dentist.patient;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

import com.project.dentist.Data;
import com.project.dentist.Login;
import com.project.dentist.Main;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Appointment;
import com.project.dentist.patient.appointment.AppointCancel;
import com.project.dentist.patient.appointment.Appointments;
import com.project.dentist.patient.appointment.ChangeAppointments;
import com.project.dentist.patient.counsel.Counsel;
import com.project.dentist.patient.intro.Intro;
import com.project.dentist.patient.review.PatientReview;

/**
 * 환자 권한으로 하는 모든 업무의 흐름을 관리하는 클래스입니다.
 * @author 고수경
 * @author 정혜인
 * @author 김승연
 */
public class PatientMain {

	/**
	 * 환자로 로그인하면 출력되는 메뉴 선택하는 메소드입니다. 
	 * 각 기능에 맞는 번호를 선택하면 상세 메뉴로 이동합니다. 
	 */
	public static void main() {

		boolean loop = true;

		while (loop) {


			Main.loginTry = 0;


			Calendar now = Calendar.getInstance();
			System.out.println();
			System.out.println("-----------------🦷알림창🦷------------------");
			
			if (hasAppointment() == null) {
				System.out.printf("%s님, 다음 예약이 없습니다.\n'진료 예약'을 선택해 검진 예약을 진행해 보세요!\n", Login.currentPatient.getName());
				System.out.println("------------------------------------------");
				System.out.println();
			} else {
				
				

				
				if (String.format("%tF", now).equals(hasAppointment().getDate())) {
					System.out.printf("오늘은 %s님의 검진일(%s)입니다.", Login.currentPatient.getName()
																  , hasAppointment().getDate());
					System.out.println("------------------------------------------");
					System.out.println();
					
				} else {
					System.out.printf("%s님, 검진일(%s)까지 %d일 남았습니다!\n", Login.currentPatient.getName()
                            , hasAppointment().getDate()
                            , aaa());
					System.out.println("------------------------------------------");
					System.out.println();
				}
				System.out.println();
			}
			
			Output.subMenuStart("환자 모드");
			System.out.println("\t1. 병원 소개\t2. 내 진료 정보\n"
							+ "\t3. 진료 예약\t4. 진료 상담\n"
							+ "\t5. 진료 후기\n\t0. 로그아웃");
			Output.subMenuEnd();
			






			Scanner scan = new Scanner(System.in);

			Intro intro = new Intro();
			System.out.print("번호 입력: ✎");
			String input = scan.nextLine();
			System.out.println();


			if (input.equals("1")) {
				// 병원소개

				boolean subLoop = true;
				while (subLoop) {

		        	 PatientOutput.introduce();
		        	 
		        	 System.out.print("확인할 정보 번호를 입력하세요. ✎ ");
		        	 String subInput = scan.nextLine();
		        	 System.out.println();
		        	 
		        	 if (subInput.equals("1")) {
		        		 
		        		 PatientOutput.printIntro();
		        		 PatientOutput.pause();
		        		 
		        	 } else if (subInput.equals("2")) {
		        		 
		              	 Intro.drList();
		        		 
		        	 } else if (subInput.equals("0")) {
		        		 
		        		 subLoop = false;
		        		 
		        	 } else {
		        		System.out.println("⚠ 올바른 번호를 입력해 주세요. ⚠");
		        	 }
	             }



			} else if (input.equals("2")) {
				// 내 진료 정보
				
				PatientOutput.medicalinfomenu();

			} else if (input.equals("3")) {
				// 진료 예약
				Appointments appointment = new Appointments(); 
				ChangeAppointments change = new ChangeAppointments(); 
				
				boolean subLoop = true;
				
				while(subLoop) {
					
					PatientOutput.appoint();
					System.out.print("번호를 입력하세요. ✎ ");
					String subInput = scan.nextLine();
					System.out.println();
					
					if (subInput.equals("1")) {
						appointment.make();
					} else if (subInput.equals("2")) {
						change.main();
					} else if (subInput.equals("3")) {
						AppointCancel.viewWaitList();
					} else if (subInput.equals("0")) {
						subLoop = false;
					} else {
						System.out.println("올바른 번호(0~3)를 입력해주세요.");
					}
					Output.pause();
				}
			} else if (input.equals("4")) {
				// 진료 상담

				Counsel.counsel();
			} else if (input.equals("5")) {
	            // 진료 후기
	        	 
	        	 boolean subLoop = true;
	        	 while (subLoop) {
	        		
	        		 PatientOutput.review();
		        	 System.out.print("번호를 입력하세요. ✎ ");
		        	 String subInput = scan.nextLine();
		        	 System.out.println();
		        	 
		        	 if (subInput.equals("1")) {
		        		 //진료 후기 조회

		        		 PatientReview.drReview();
		        		
		        	 } else if (subInput.equals("2")) {
		        		 //진료 후기 작성
			
		        		PatientReview.chooseDr();
		        		 
		        	 } else if (subInput.equals("0")) {
		        		 
		        		 subLoop = false;
		        		 
		        	 } else { 
		        		 
		        		 System.out.println("⚠ 올바른 번호를 입력해 주세요. ⚠");
		        	 }
	        	 }
	        	 
	         } else if (input.equals("0")) {
	        	 
	        	 loop = false;           
	        	 
	         }
	                 
	      }



	   }// main
	
	private static Appointment hasAppointment() {
		Calendar now = Calendar.getInstance();
		
		for (Appointment a : Data.alist) {
			
			if(Login.currentPatient.getSeq().equals(a.getPatientNum())) {
				
				if(a.getDateTime().compareTo(now) > 0) {
					return a;
				}
			}
		}
		return null;
	}
	
	   private static int aaa() {
	         String[] date = hasAppointment().getDate().split("-");
	         
	         LocalDate theDate = LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
	         java.time.Period period = LocalDate.now().until(theDate);

	         return period.getDays();
	      }
	
}