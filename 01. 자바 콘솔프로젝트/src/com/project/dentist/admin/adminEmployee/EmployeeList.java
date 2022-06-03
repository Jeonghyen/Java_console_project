package com.project.dentist.admin.adminEmployee;

import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Output;

/**
 * 직원 관리 메뉴 목록을 보여줍니다.
 * @author 백재민
 *
 */
public class EmployeeList {
	
	/**
	 * 직원 관리 메뉴입니다.
	 */
	public static void main() {
		
		List l = new List();
		String[] con = { "직원 목록", "직원 추가", "직원 삭제", "상위메뉴" };
		
		boolean loop = true;
		
		while (loop) {
		
		Output.subMenuStart("직원 정보");
		Output.subMenuContent(con); 
		Output.subMenuEnd();
		
		Scanner scan = new Scanner(System.in);
		System.out.print("번호 입력: ");
		String input = scan.nextLine();
		
		if (input.equals("1")) {
			l.list();
			Output.pause();
		} else if (input.equals("2")) {
			EmployeeAdd.add();
		} else if (input.equals("3")) {
			EmployeeAdd.delete();
		} else if (input.equals("0")) {
			loop = false;
		} else {
			System.out.println("잘못된 번호입니다.");
		}
		
		}
		
		Data.save(DataPath.의사);
		Data.save(DataPath.간호사);

	}

}
