package com.project.dentist.admin.adminOnlineChat;

import java.util.ArrayList;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Answer;
import com.project.dentist.dataClass.OnlineQuestion;

/**
 * 온라의 상담의 질문목록을 출력합니다.
 * @author 김시현
 *
 */
public class AdminOnlineChat {
	
	/**
	 * 등록되어 있는 모든 질문내역을 보여줍니다.
	 */
	public static void main() {

		System.out.println(123);

		Scanner scan = new Scanner(System.in);

		int count = 1;
		System.out.println();

		int page = 1;

		int totalpage = Data.olist.size() / 5 + 1;
		String line = "";

		String input2 = null;
		String con = "작성완료";
		int exit = 0;

		ArrayList<String> num = new ArrayList<String>();
		for (int i = 0; i < Data.olist.size(); i++) {
			num.add((Data.olist.get(i).getSeq()));
		}
		
		ArrayList<String> num2 = new ArrayList<String>();
		for (int i = 0; i < Data.answerlist.size(); i++) {
			num2.add((Data.answerlist.get(i).getCnum()));
		}


		Output.subMenuStart("온라인 상담");
		System.out.println("\t\t\t\t☑ = 작성완료");
		for (int i = 0; i < Data.olist.size(); i++) {

			exit = 0;

			String input = "";

			line = String.format("%2s.[%s]%s", Data.olist.get(i).getSeq(),
					Data.olist.get(i).getCategory(), Data.olist.get(i).getTitle());

			
			if(num2.contains(String.valueOf(i + 1))) {
				System.out.printf("%s ☑\n", line);
			} else {
				System.out.println(line);
			}
			
		


			if (i == Data.olist.size() - 1) {
				//System.out.printf("     \t\t\t현재 페이지[%s/%s]\n", page, totalpage);
				//Output.subMenuEnd();
			}
			if (count % 5 == 0) {
				System.out.printf("     \t\t\t현재 페이지[%s/%s]\n", page, totalpage);
				Output.subMenuEnd();
				System.out.print("글번호를 입력해주세요. 다음페이지 [Enter] (취소 : 0) ✎");
				input = scan.nextLine();
				
				if(input.equals("")) {
					Output.subMenuStart("온라인 상담");
					System.out.println("\t\t\t\t ☑ = 작성완료");
				}
				
				
				page++;

			}


			if (input.equals("0")) {
				break;
			} else if (input.equals("")) {
				
			} else if (num.contains(input)) {
				AnswerWrite.write(input);
				exit = 1;
				break;
			} else {
				System.out.println("잘못된 번호입니다.");
				exit = 1;
				Output.pause();
			}

			count++;

			if (exit == 1) {
				break;
			}
		}



	}


}


