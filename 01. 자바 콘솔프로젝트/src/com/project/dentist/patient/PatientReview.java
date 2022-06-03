package com.project.dentist.patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Login;

public class PatientReview {

	private static Data d = new Data();
	private static ReviewData r = new ReviewData();
	private static ArrayList<String> temp = new ArrayList<String>();
	

	
	 
	public static void drReview() {
		
		try {
		

			int total;
			int n;
			double[] avg = new double[Data.dlist.size()];
			
			for (int i=0 ; i<Data.dlist.size() ; i++) {
				ReviewData.rlist.clear();
				total = 0;
				n = 0;
				ReviewData.load(i);
				
				for (int j=0 ; j<ReviewData.rlist.size() ; j++) {
					total += ReviewData.rlist.get(j).getStar().length();
					n++;
				}
				avg[i] = total / (double)n;
			}
				
			System.out.println("[의사번호] [이름] [별점 평점]");
			for (int i=0 ; i<Data.dlist.size() ; i++) {
				System.out.printf("%7d. %s\t%7.1f\n", i+1, Data.dlist.get(i).getName(), avg[i]);
			}
			
			System.out.println();

			boolean loop = true;
			while (loop) {
				
				try {
					
					System.out.print("진료 후기를 조회할 의사를 선택해 주세요. ✎ ");
					
					Scanner scan = new Scanner(System.in);
					String drNum = scan.nextLine();
					System.out.println();
					 
					if (Integer.parseInt(drNum) > 0 && (Integer.parseInt(drNum) <= Data.dlist.size())) {
						PatientReview.printReview(drNum);
						loop = false;
					}
					
				} catch (NumberFormatException e) {
					System.out.println("⚠ 올바른 번호를 입력해 주세요. ⚠");
				}
				
			}
		} catch (Exception e) {
			System.out.println("PatientReview.DrReview");
		}
		
	}


	public static void printReview(String drNum) {
		
		int num = Integer.parseInt(drNum) - 1 ;
		
		ReviewData.rlist.clear();
		ReviewData.load(num);
		
		int n = 0;
		int page = 1;
		
			for (int i=0 ; i<ReviewData.rlist.size() ; i++) {
	
				Scanner scan = new Scanner(System.in);
				
				if (i % 5 == 0) {
					System.out.println("================= 바른치과 " + Data.dlist.get(num).getName() + " 의사의 후기 ================= ");
					System.out.println();
					System.out.println("[글번호]   [작성자]    [별점]  [후기]");
				}
				
				String id = ReviewData.rlist.get(i).getId();
				id = id.substring(0, id.length()-4) + "****";
				
				String star = ReviewData.rlist.get(i).getStar().replace("*", "★");
				String blank = "";
				
					for (int j=0 ; j<5-star.length() ; j++) {
						blank += "☆";
					}
			
				System.out.printf("%5d. %10s  %s  %-30s\n", i+1, id, star + blank, ReviewData.rlist.get(i).getComment());
			

				
				if (i == ReviewData.rlist.size() - 1) {
					System.out.println();
					System.out.println("=========================================================");
					System.out.print("[" + page + "/" + (ReviewData.rlist.size() % 5 == 0 ? ReviewData.rlist.size() / 5 : ReviewData.rlist.size() / 5 + 1) + "page]");
					System.out.println(" 마지막 페이지입니다. [엔터]를 입력하면 상위 메뉴로 돌아갑니다.");
					System.out.println();
					scan.nextLine();
					break;
				}
				if (i != 0 && (i+1) % 5 == 0) {
					System.out.println();
					System.out.println("=========================================================");
					System.out.print("[" + page + "/" + (ReviewData.rlist.size()/5 + 1) + "page]");
					System.out.println(" 다음 페이지: [엔터]");
					scan.nextLine();
					page++;
				}
			}
	
	
	}


	public static void chooseDr() {
		//테스트 위해 진료정보의 DrNum을 1,1,1,1,1 -> 1,2,3,4,5로 바꿈
		
		Scanner scan = new Scanner(System.in);
			
			System.out.println("진료 후기 작성을 위해 진료 내역을 조회합니다.");
			System.out.printf("============== %s(%s)님의 진료 내역 ==============\n"
													, Login.currentPatient.getName()
											   	    , Login.currentPatient.getId());
			System.out.println();
			
			System.out.println("[번호]     [날짜]     [진료받은 의사]");
			
			int seq = 1;
			for (int i=0 ; i<Data.dglist.size() ; i++) {
				if (Data.dglist.get(i).getPatientNum().equals(Login.currentPatient.getSeq())) {
					
					String line = String.format("%4d.  %s       %s", seq
																	 , Data.dglist.get(i).getDate()
																	 , getDrName(Data.dglist.get(i).getDoctorNum()));
					temp.add(line);
					System.out.printf(line);
					System.out.println();
					seq++;
				}
			
			}
			
			if (seq == 1) {
				System.out.println("진료 내역이 없습니다. [엔터]를 입력하면 상위 메뉴로 돌아갑니다.");
				scan.nextLine();
			} else {
				
				boolean loop = true;
				while (loop) {
					System.out.println();
					System.out.println("=========================================================");
					System.out.print("진료 후기를 작성할 의사를 선택해 주세요. ✎");
					String toReviewDr = scan.nextLine();
			
					try {
						
						if ((Integer.parseInt(toReviewDr) > 0) && (Integer.parseInt(toReviewDr) < seq)) {
							writeReview(toReviewDr);
							loop = false;
						}
						
					} catch (NumberFormatException e) {
						System.out.println("⚠ 올바른 번호를 입력해 주세요. ⚠");
					}
				}
			}
		
	}
	
	
		
	public static String getDrName(String doctorNum) {
			
		return Data.dlist.get(Integer.parseInt(doctorNum)-1).getName();
			
	}

	public static String getDoctorNum(String DrName) {
		
		for (int i=0 ; i<Data.dlist.size() ; i++) {
			if (Data.dlist.get(i).getName().equals(DrName)) {
				return String.valueOf(i);
			}
		}
		return null;
	}

	public static void writeReview(String toReviewDr) {

		//목록번호로 입력받은 의사가 누구인지...
		
		Scanner scan = new Scanner(System.in);
		String chosenDr = "";
		for (int i=0; i<temp.size() ; i++) {
			temp.set(i, temp.get(i).replace(" ", ""));
			
			if (temp.get(i).substring(0, temp.get(i).indexOf(".")).equals(toReviewDr)) {
				chosenDr = temp.get(i).substring(temp.get(i).length()-3);
			}
		}
		
		System.out.printf("%s 의사의 진료 후기를 등록합니다.\n", chosenDr);
		int drNum = Integer.parseInt(getDoctorNum(chosenDr));
		
		ReviewData.rlist.clear();
		ReviewData.load(drNum);

		boolean loop = true;
		while (loop) {
			
			System.out.print("별점(*, **, ***, ****, *****): ");
			String star = scan.nextLine();
			
			System.out.print("후기(100자 이내): ");
			String comment = scan.nextLine();
			
			//후기번호,아이디,별점,한줄후기
			String seq = String.valueOf(ReviewData.rlist.size() + 1);
			String id = Login.currentPatient.getId();
			
			System.out.println();
			boolean subLoop = true;
			
			while (subLoop) {
				System.out.print("후기를 등록하시겠습니까? (Y/N): ");
				String answer = scan.nextLine();
	
				if (answer.equals("Y") || answer.equals("y")) {
		
					if (!star.equals("*") && !star.equals("**") && !star.equals("***")
						&& !star.equals("****") && !star.equals("*****")) {
						
						System.out.println("⚠ 별점은 1(*)~5(*****)점만 등록 가능합니다. 다시 입력해 주세요. ⚠");
						subLoop = false;
					
					} else if (comment.length() > 100) {
			
						System.out.println(" ⚠ 후기는 100자 이내 작성만 가능합니다. 다시 입력해 주세요. ⚠");
						subLoop = false;
						
						
					} else {
						
					ReviewData.rlist.add(new Review(seq, id, star, comment));
					System.out.println("후기 등록이 완료되었습니다. 엔터를 입력하면 상위 메뉴로 돌아갑니다.");
					ReviewData.save(drNum);
					scan.nextLine();
					subLoop = false;
					loop = false;
					
					}
					
				} else if (answer.equals("N") || answer.equals("n")) {
					System.out.println("후기 등록이 취소되었습니다. 엔터를 입력하면 상위 메뉴로 돌아갑니다.");
					scan.nextLine();
					subLoop = false;
					loop = false;
				
				} else {
					System.out.println("⚠ Y(등록) 혹은 N(취소)을 입력해 주세요. ⚠");
				}
			}
		}

	}
}



