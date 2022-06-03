package com.project.dentist.admin.inventory;

import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Item;

/**
 * 재고 수량 목록을 보여주는 메뉴입니다.
 * @author 백재민
 *
 */
public class List {
	
	/**
	 * 재고 관련 정보를 보여줍니다.
	 */
	public void list() {
		System.out.println();
		System.out.println("==================[수량 확인]==================");
		System.out.println();
		System.out.println("[번호]\t [이름(사용처)]    [단가]\t  [잔여수량]");
		
		for (Item i : Data.ilist) {
			System.out.printf("%3s\t%10s\t%4s원\t    %3d\n"
					, i.getSeq()
					, i.getIname()
					, i.getPrice()
					, i.getAmount());
		}
		
		System.out.println();
		
	}
	
	/**
	 * 자동 주문 수량 설정을 변경합니다.
	 * -- 설정 변경 후 아이템이 사용되고 난 뒤에 반영됩니다.
	 */
	public void auto() {
		System.out.println();
		System.out.println("==================[주문 하기]==================");
		System.out.println();
		System.out.println("[번호]\t [이름(사용처)]    [단가]\t  [잔여수량]\t[자동 주문]");
		
		for (Item i : Data.ilist) {
			System.out.printf("%3s\t%10s\t%4s원\t    %3d\t\t%5d\n"
					, i.getSeq()
					, i.getIname()
					, i.getPrice()
					, i.getAmount()
					, i.getAuto());
		}
		
		Item result = null;
		
		Scanner scan = new Scanner(System.in);
		System.out.print("자동 주문 수량을 변경할 재고의 번호를 입력하세요.(0.취소) > ");
		String input = "";
		
		while(true) {
		
		input = scan.nextLine();
		
			if (input.equals("0")) {
				return;
			} else if (Integer.parseInt(input) > Data.ilist.size()) {
				System.out.print("자동 주문 수량을 변경할 재고의 번호를 다시 입력하세요.(0.취소) > ");
			} else {
				break;
			}
		}
		
		for (Item i : Data.ilist) {
			if(i.getSeq().equals(input)) {
				result = i;
				break;
			}
		}
		
		if(result != null) {
			
			System.out.print("[변경 전 자동 주문 수량] : " + result.getAuto() + "[변경 후] : ");
			String auto = scan.nextLine();
			
			if (!auto.equals("")) {
				for (int i = 0; i < auto.length(); i++) {
					if (auto.charAt(i) < '0' || auto.charAt(i) > '9') {
						System.out.println("0~9의 숫자로 입력해주세요.");
						auto();
					} else {
						result.setAuto(Integer.parseInt(auto));
						System.out.println("변경이 완료되었습니다.");
						Output.pause();
						break;
					}
				}
			}
		}
						
	}//save
	
}
