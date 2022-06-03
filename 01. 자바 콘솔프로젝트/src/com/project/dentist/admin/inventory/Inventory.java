package com.project.dentist.admin.inventory;

import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Output;

/**
 * 재고 관리 메뉴입니다.
 * @author 백재민
 *
 */
public class Inventory {
	
	/**
	 * 메뉴를 보여줍니다.
	 */
	public static void main() {
		
		List l = new List();
		String[] con = { "수량 확인", "주문 하기", "상위메뉴" };
		
		boolean loop = true;
		
		while (loop) {
			Output.subMenuStart("재고 확인");
			Output.subMenuContent(con);
			Output.subMenuEnd();
			
			Scanner scan = new Scanner(System.in);
			System.out.print("번호 입력: ");
			String input = scan.nextLine();
			
			if (input.equals("1")) {
				l.list();
			} else if (input.equals("2")) {
				l.auto();
			} else if (input.equals("0")) {
				loop = false;
			} else {
				System.out.println("잘못된 번호입니다.");
			}
			
			
		}
		
		Data.save(DataPath.의료용품);
		
	}

}
