package com.project.dentist.admin.adminSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.admin.adminDiagnosis.DiagnosisSearch;
import com.project.dentist.dataClass.Appointment;
import com.project.dentist.dataClass.Doctor;

/**
 * 의사의 진료 예약 일정을 확인하는 클래스입니다.
 * @author 고민지
 *
 */
public class AdminSchedule {

	public Scanner scan;
	private Calendar now;

	/**
	 * Scanner와 Calendar 참조변수에 새로운 객체를 생성하는 생성자입니다.
	 */
	public AdminSchedule() {
		this.scan = new Scanner(System.in);
		this.now = Calendar.getInstance();
	}

	/**
	 * 의사의 진료 예약 일정을 확인하는 전 과정의 흐름을 관리하는 메소드입니다.
	 */
	public void main() { //XXX 수정(work -> main)

		String schedule = "의사 스케줄표";
		
		Output.subMenuStart("스케줄표 확인");

		String[] temp = new String[Data.dlist.size()];
		for (int i = 0; i < Data.dlist.size(); i++) {
			temp[i] = String.format("%s%s", Data.dlist.get(i).getName(), schedule);
		}

		Output.subMenuContent(temp);
		Output.subMenuEnd();


		int input = -1;
		boolean loop = true;

		while (loop) {

			try {
				scan = new Scanner(System.in);

				System.out.print("번호를 입력하세요: ✎");
				input = scan.nextInt();

				if (1 <= input && input <= Data.dlist.size()) {
					loop = false;
				} else {
					throw new Exception();
				}
				
			} catch (Exception e) {
				System.out.println("올바른 번호를 입력해주세요.(1~6)");
				System.out.println();
			}

		}
		System.out.println();

		Doctor theDoctor = Data.dlist.get(input - 1);
		
		while(checkSchedule(input, theDoctor) > 0) {
			Output.pause();
			System.out.println();
		};
		
		Output.pause(); 

	}

	private int checkSchedule(int input, Doctor theDoctor) {
		
		Output.subMenuStart(String.format("%s님의 스케줄", theDoctor.getName()));
		printScheduleTable(theDoctor);
		Output.subMenuEnd();
		
		boolean loop;
		loop = true;
		
		while (loop) {

			try {
				scan = new Scanner(System.in);

				System.out.print("일자를 입력하세요(상위메뉴0): ✎");
				input = scan.nextInt();

				if (input > 0) { //TODO 스케줄이 있는 날짜
					//다음으로 이동
					loop = false;
				} else if (input == 0) {
					//TODO 상위메뉴로 돌아가기
					return 0;
				} else {
					throw new Exception();
				}
				
			} catch (Exception e) {
				System.out.println("선택가능한 일자가 아닙니다. ☑ 표시된 일자를 입력해주세요.");
			}

		}
		
		Calendar theDate = Calendar.getInstance();
		theDate.set(now.get(Calendar.YEAR)
					, (input > now.get(Calendar.DATE)) ? now.get(Calendar.MONTH) : now.get(Calendar.MONTH) + 1
					, input); 
		
		System.out.println();
		Output.subMenuStart(String.format("%s님의 스케줄", theDoctor.getName()));
		printSchedule(theDoctor, theDate);
		Output.subMenuEnd();
		
		return 1;
	}

	private void printSchedule(Doctor theDoctor, Calendar theDate) {

		ArrayList<Appointment> schedules = new ArrayList<Appointment>();
		
		System.out.printf("<%d월 %d일>\n", theDate.get(Calendar.MONTH)+1, theDate.get(Calendar.DATE));
		System.out.println();
		
		findAppointment(schedules, theDoctor, theDate);
		
		System.out.printf("[%d개의 스케줄이 있습니다.]\n", schedules.size()); 
		System.out.println("---------------------------------------");
		
		schedules.sort((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime())); //시간순 정렬
		
		
		
		//[만약에 "환자번호로 환자이름찾기" , "진료구분번호로 진료구분명찾기"통합하면 삭제해야할 부분
		DiagnosisSearch a = new DiagnosisSearch();
		//]

		for(int i=0; i<schedules.size(); i++) {

			//[만약에 "환자번호로 환자이름찾기" , "진료구분번호로 진료구분명찾기"통합하면 삭제해야할 부분
			String name = a.findPatient(schedules.get(i).getPatientNum()).getName();
			String diagnosis = a.findClassificationName(schedules.get(i).getClassficationNum());
			//]

			System.out.printf("%d.[%s]\n", i+1, schedules.get(i).getTime());
			System.out.printf("예약 번호: %s\n", schedules.get(i).getSeq());
			System.out.printf("환자명(번호): %s(%s)\n", name, schedules.get(i).getPatientNum()); 
			System.out.printf("진료 구분: %s\n", diagnosis); 
			System.out.println("---------------------------------------");
		}
	}

	private void findAppointment(ArrayList<Appointment> schedules, Doctor theDoctor,
			Calendar theDate) {

		for(Appointment a : Data.alist) {
			
			if(!a.getDate().equals(String.format("%tF", theDate))){
				continue;
			} else if (!a.getDoctorNum().equals(theDoctor.getSeq())) {
				continue;
			} 
			
			schedules.add(a);
		}
	}

	private boolean isAppointment(Doctor theDoctor, Calendar theDate) {
		
		for(Appointment a : Data.alist) {
			
			if(!a.getDoctorNum().equals(theDoctor.getSeq())){
				continue;
			} else if (!a.getDate().equals(String.format("%tF", theDate))) {
				continue;
			} 
			if(a.getDateTime().getTimeInMillis() <= now.getTimeInMillis()) {
				continue;
			}
			return true;
			
		}
		return false;
	}

	private void printScheduleTable(Doctor theDoctor) {
		
		Calendar temp = Calendar.getInstance(); //비교값 Calendar 
		Calendar scheduleDate = Calendar.getInstance();
		
		System.out.printf("\t\t\t%s월(☑ : 스케줄있는 날짜)\n", now.get(Calendar.MONTH)+1);
		//System.out.printf("%s월 ~ %s월\t\t(☑ : 스케줄있는 날짜)\n", now.get(Calendar.MONTH)+1, now.get(Calendar.MONTH)+2);
		
		System.out.println("╭───────┬───────┬───────┬───────┬───────┬───────┬───────╮");
		System.out.println("|   일	|   월	|   화	|   수	|   목	|   금	|   토	|");
		System.out.println("├───────┼───────┼───────┼───────┼───────┼───────┼───────┤");
		
		
		//오늘날짜이전까지 틀만들기
		scheduleDate.set(Calendar.DATE, 1);
		if(scheduleDate.get(Calendar.DAY_OF_WEEK) > 1) {
			for(int i=0; i<scheduleDate.get(Calendar.DAY_OF_WEEK)-1; i++) {
			System.out.print("|\t");
		}
			
		while(scheduleDate.get(Calendar.DATE) < temp.get(Calendar.DATE)) {
			System.out.printf("|  %2d\t", scheduleDate.get(Calendar.DATE));
			
			if(scheduleDate.get(Calendar.DAY_OF_WEEK) == 7) {
				System.out.println("|");
				System.out.println("|\t|\t|\t|\t|\t|\t|\t|");
			}
			scheduleDate.add(Calendar.DATE, 1);
		}
		
			
		//오늘날짜부터 틀만들기
		scheduleDate = Calendar.getInstance(); //-> 오늘기준 다음달 마지막날
		scheduleDate.set(Calendar.HOUR_OF_DAY, 0);
		scheduleDate.set(Calendar.MINUTE, 0);
		scheduleDate.set(Calendar.SECOND, 0);
		temp.add(Calendar.MONTH, 1);
		temp.set(Calendar.DATE, temp.getActualMaximum(Calendar.DATE));
		
		
		
		while (scheduleDate.compareTo(temp) <= 0) {
			
			if(isAppointment(theDoctor, scheduleDate)) {
				System.out.printf("|  %2d☑\t", scheduleDate.get(Calendar.DATE));
			} else {
				System.out.printf("|  %2d\t", scheduleDate.get(Calendar.DATE));
			}
			
			if(scheduleDate.get(Calendar.DAY_OF_WEEK) == 7) {
				System.out.println("|");
				System.out.println("|\t|\t|\t|\t|\t|\t|\t|");
			}
			
			if(scheduleDate.get(Calendar.DATE) == scheduleDate.getActualMaximum(Calendar.DATE)) {
				if(scheduleDate.get(Calendar.DATE) == now.getActualMaximum(Calendar.DATE)) {
					System.out.println("╰───────┴───────┴───────┴───────┴───────┴───────┴───────╯");
					System.out.println();
					System.out.printf("\t\t\t%s월 (☑ 선택가능)\n", now.get(Calendar.MONTH)+2);
					System.out.println("╭───────┬───────┬───────┬───────┬───────┬───────┬───────╮");
					System.out.println("|   일	|   월	|   화	|   수	|   목	|   금	|   토	|");
					System.out.println("├───────┼───────┼───────┼───────┼───────┼───────┼───────┤");
					
				} else {
					for(int i=0; i<7-scheduleDate.get(Calendar.DAY_OF_WEEK); i++) {
						System.out.print("|\t");
					}
					if(scheduleDate.get(Calendar.DAY_OF_WEEK)!=7) {
						System.out.println("|");
					}
				}
			}
			
			scheduleDate.add(Calendar.DATE, 1);
		}
		
		System.out.println("╰───────┴───────┴───────┴───────┴───────┴───────┴───────╯");
		
		}	

	}

}


//1.캘린더에 이번달 + 다음달 출력  > 1일일때는 어떻게 할지...
//2. 스케줄이 있는 날짜에 ☑ 같이출력

