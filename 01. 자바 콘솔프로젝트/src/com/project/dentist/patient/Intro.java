package com.project.dentist.patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Output;

public class Intro {

	
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
