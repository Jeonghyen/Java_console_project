package com.project.dentist;

import java.util.Scanner;

/**
 * 공통으로 사용하는 정적 출력을 모아놓은 클래스입니다.
 * @author 고민지
 * @author 백재민
 *
 */
public class Output {
	
	/**
	 * 메뉴의 시작을 알리는 구분선과 메뉴의 제목을 출력하는 메소드입니다.
	 * @param 메뉴의 제목
	 */
	public static void subMenuStart(String title) {
		
		System.out.print("╭");
		int b = "─────────────────────────────────────────".length() - (title.length() + 2);
		
		for(int i = 1; i < b/2; i++) {
			System.out.print("─");
		}
		
		System.out.print(" " + title + " ");
		
		for(int i = 1; i < b/2; i++) {
			System.out.print("─");
		}
		
		System.out.println("╮");
		
	}
	
	/**
	 * 메뉴의 목차를 출력하는 메소드입니다.
	 * @param 메뉴의 목차
	 */
	public static void subMenuContent(String[] title) {
		if(title.length <= 3) {
			
			for(int i=1; i<=title.length; i++) {
				if(title[i-1].contains("상위메뉴") || title[i-1].equals("취소") ) {
					System.out.printf("  0. %s\n", title[i-1]);
				} else {
					System.out.printf("  %d. %s\n", i, title[i-1]);
				}
			}
			
		} else {
			
			for(int i=1; i<=title.length; i++) {
				
				if(title[i-1].contains("상위메뉴") || title[i-1].equals("취소") ) {
					System.out.printf("  0. %s", title[i-1]);
				} else {
					System.out.printf("  %d. %s", i, title[i-1]);
				}
				
				
				if(i % 2 == 0 || i == title.length) {
					System.out.println();
				} else {
					if(title[i-1].length() < 4) { 
						System.out.print("\t\t");
					} else {
						System.out.print("\t");
					}
				}
			}
		}		
	}

	
	/**
	 * 메뉴의 끝을 알리는 구분선을 출력하는 메소드입니다.
	 */
	public static void subMenuEnd() {
		System.out.println("╰─────────────────────────────────────────╯");
	}
	
	/** 
	 * 구분선을 출력하는 메소드입니다.
	 */
	public static void line() {
		System.out.println("-----------------------------------------");
	}
	
	
	/**
	 * 다음 동작을 진행하기 전 일시적으로 흐름을 중지시키는 메소드입니다.
	 */
	public static void pause() {
	
		System.out.println("계속하시려면 [Enter]를 입력하세요. ↲"); 
		
		Scanner scan = new Scanner(System.in);
		
		scan.nextLine(); 
	}

}
