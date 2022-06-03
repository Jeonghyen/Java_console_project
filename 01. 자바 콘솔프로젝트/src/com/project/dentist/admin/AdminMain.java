package com.project.dentist.admin;

import java.util.Scanner;

import com.project.dentist.Output;
import com.project.dentist.admin.adminDiagnosis.AdminDiagnosis;
import com.project.dentist.admin.adminEmployee.EmployeeList;
import com.project.dentist.admin.adminOnlineChat.AdminOnlineChat;
import com.project.dentist.admin.adminSchedule.AdminSchedule;
import com.project.dentist.admin.inventory.Inventory;
import com.project.dentist.admin.sales.MonthSale;

/**
 * 관리자 권한으로 하는 모든 업무의 흐름을 관리하는 클래스입니다.
 * @author 고민지
 * @author 김시현
 * @author 백재민
 *
 */
public class AdminMain {
	
	public static void main() { //관리자 메인화면

		AdminDiagnosis diagnosis = new AdminDiagnosis();
		AdminSchedule schedule = new AdminSchedule();
		boolean loop = true;
		
		while (loop) {
			Output.subMenuStart("관리자");
			System.out.println("\t1. 스케줄표 확인\t2. 환자 진료 정보");
			System.out.println("\t3. 직원 정보\t4. 매출액 조회");
			System.out.println("\t5. 재고 관리\t6. 온라인 상담");
			System.out.println("\t0. 로그아웃");
			Output.subMenuEnd();
			
			Scanner scan = new Scanner(System.in);
			System.out.print("번호 입력: ✎ ");
			String input = scan.nextLine();
			
			if (input.equals("1")) {
				schedule.main(); 
			} else if (input.equals("2")) {
				diagnosis.main(); 
			} else if (input.equals("3")) {
				EmployeeList.main();
			} else if (input.equals("4")) {
				MonthSale.main();
			} else if (input.equals("5")) {
				Inventory.main();
			} else if (input.equals("6")) {
				AdminOnlineChat.main();
			} else if (input.equals("0")) {
				loop = false;
			} else {
				System.out.println("잘못된 번호입니다.");
			}
		
			System.out.println();
			
		}
		
	}

}
