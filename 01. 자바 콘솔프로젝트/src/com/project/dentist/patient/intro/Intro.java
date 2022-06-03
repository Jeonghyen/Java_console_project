package com.project.dentist.patient.intro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Output;
import com.project.dentist.patient.PatientOutput;

/**
 * 
 * 환자 모드의 병원 소개 클래스입니다.
 * @author 김승연
 *
 */
public class Intro {

	
	/**
	 * 의사 선택, 정보 조회를 위해 의사 목록을 출력하는 메소드입니다.
	 */
	public static void drList() {
		
		boolean loop = true;
		while (loop) {
			if (Data.dlist.size() == 0) {
				Data.load();
			}
			
			Output.subMenuStart("의료진 정보");
			String[] menu = new String[Data.dlist.size()+1];
			for (int i=0 ; i<Data.dlist.size() ; i++) {
				menu[i] = Data.dlist.get(i).getName();
			}		
			menu[Data.dlist.size()] = "상위메뉴";
			Output.subMenuContent(menu);
			Output.subMenuEnd();
			
			try {
				
				System.out.print("확인할 의사 번호를 입력하세요. ✎ ");
	   		 	
				Scanner scan = new Scanner(System.in);
				String drNum = scan.nextLine();
		   		System.out.println();
	   		 			
		   		if (Integer.parseInt(drNum) < 7 && Integer.parseInt(drNum) > 0) {
		   			printDr(drNum);
		   			 
		   		} else if (drNum.equals("0")) {
		   			loop = false;
		   		}
	   		
			} catch (NumberFormatException e) {
				System.out.println("⚠ 올바른 번호를 입력해 주세요. ⚠");

			} catch (Exception e) {
				System.out.println("Intro.drList");
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 의사별 자기소개서를 불러오는 메소드입니다.
	 * @param drNum 의사 목록의 확인하고 싶은 의사 번호
	 */
	public static void printDr(String drNum) {
		
		
		try {
		
			BufferedReader reader = new BufferedReader(new FileReader(DataPath.자기소개서 + drNum + ".txt"));
			
			String line = "";
			while ((line = reader.readLine()) != null) {
				
				System.out.println(line);
			}
			System.out.println();
			reader.close();
			
			PatientOutput.pause();
			
		} catch (Exception e) {
			System.out.println("Intro.printDr");
			e.printStackTrace();
		}

		
	}
	
	
}
