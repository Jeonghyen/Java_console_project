package com.project.dentist.admin.sales;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.admin.AdminMain;
import com.project.dentist.dataClass.DiagnosisInfo;
import com.project.dentist.dataClass.Order;

/**
 * 월 매출 정보를 보여줍니다.
 * @author 백재민
 *
 */
public class MonthSale {

	private static String input = "";
	private static ArrayList<DiagnosisInfo> dgdaylist = new ArrayList<DiagnosisInfo>();

	/**
	 * 년-월을 입력받아 해당 월의 매출 정보를 보여줍니다.
	 */
	public static void main() {

		int result = 0;
		int order = 0;

		boolean loop = true;
		
		while (loop) {
			Scanner scan = new Scanner(System.in);
			System.out.print("검색할 년-월을 입력하세요(0.취소) > ");
			input = scan.nextLine();

			if (input.equals("0")) {
				AdminMain.main();
			}
			if(input.length() < 7) {
				System.out.println("YYYY-MM 형식으로 입력해주세요.");
			} else {
				
				String[] month = {"01", "02", "03", "04", " 05", "06", "07", "08", "09", "10", "11", "12"};
				
				for(String s : month) {
					if(input.split("-")[1].equals(s)) { loop = false; }
				}
			}
		}
		
		for (int i = 0; i < Data.ordlist.size(); i++) {
			Data.ordlist = (ArrayList<Order>) Data.ordlist.stream().filter(x ->
			 	(x.getDate().split("-")[0] + "-" + x.getDate().split("-")[1]).equals(input)
			 ).collect(Collectors.toList());
		}
		
		for (int i = 0; i < Data.dglist.size(); i++) {
			dgdaylist = (ArrayList<DiagnosisInfo>) Data.dglist.stream().filter(x -> 
				(x.getDate().split("-")[0] + "-" + x.getDate().split("-")[1]).equals(input)
			).collect(Collectors.toList());
		}
		
		for (int i = 0; i < dgdaylist.size(); i++) {
			for (int j = 0; j < Data.paylist.size(); j++) {
				if (dgdaylist.get(i).getTreatmentNum().equals(Data.paylist.get(j).getSurgeSeq())) {
					result += Integer.parseInt(Data.paylist.get(j).getPay());
				}
			}
		}
		
		for (int i = 0; i < Data.ordlist.size(); i++) {
			order += Integer.parseInt(Data.ordlist.get(i).getItemprice()) * (Data.ordlist.get(i).getItemauto());
		}
		
		int epay = 0;
		int tax = 0;
		int sum = 0;

		epay = PayResult.empPay(epay);

		System.out.printf(" 매 출 액\t|\t\t\t+%,d원\n", result);
	      System.out.printf(" 급 여\t| %,d원\n", -epay);
	      System.out.printf(" 재 료 비\t| %,11d원\n", -order);
	      tax = (int) ((result - epay - order) * 0.15);
	      if (tax > 0) {
	      System.out.printf(" 세 금\t| %,11d원\n", -tax);
	      sum = result - epay - order - tax;
	      System.out.println();
	      System.out.println();
	      System.out.printf(" 합 계\t| %,11d원\t\t+%,d원\n", -epay-tax-order, result);
	      System.out.println("---------------------------------------------");
	      } else {
	    	  System.out.printf(" 세 금\t| %,11d원\n", tax);
	    	  sum = result - epay - order + tax;
	    	  System.out.println();
		      System.out.println();
		      System.out.printf(" 합 계\t| %,11d원\t\t+%,d원\n", -epay+tax-order, result);
		      System.out.println("---------------------------------------------");
	      }
		
		if (sum > 0) {
			System.out.printf(" 순 이 익\t|\t\t+%,d원\n", sum);
		} else {
			System.out.printf(" 순 이 익\t|\t\t%,d원\n", sum);
		}
			
		Output.pause();

	}

}
