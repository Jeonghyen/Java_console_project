package com.project.dentist.admin.adminOnlineChat;

import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Answer;

/**
 * 질문에 대한 답변을 작성하는 클래스입니다.
 * @author shkim
 *
 */
public class AnswerWrite {
	
	/**
	 * 질문번호의 답변이 비어있을 시 답변을 새로 추가하고
	 * 답변이 있을시 수정하게 해주는 메소드입니다.
	 * @param 질문번호
	 */
	public static void write(String s) {
		Scanner scan = new Scanner(System.in);
		Answer result = null;
		
		boolean loop = true;
		
		while(loop) {
			
			for (Answer a : Data.answerlist) {
	
				if (a.getCnum().equals(s)) {
					result = a;
				} 
			}
		
		
			if (result == null) {// 답변 새로 추가
				
				Output.subMenuStart(
						String.format("[%s],%s", Data.olist.get(Integer.parseInt(s) - 1).getCategory(),
								Data.olist.get(Integer.parseInt(s) - 1).getTitle()));
				
				System.out.println("[질문 내용]");
				for(int c = 0; c  < Data.olist.get(Integer.parseInt(s) - 1).getContent().length(); c++) {
					System.out.printf("%s", Data.olist.get(Integer.parseInt(s) - 1).getContent().charAt(c));
					if(c != 0 && c % 30 == 0) {
						System.out.println();
					}
				}
				
				System.out.println();
				System.out.println("------------------------------------------");
				System.out.print("[질문 답변] : 없음\n");

				Output.subMenuEnd();
				
				System.out.print("질문 답변 입력(취소 : 0) ✎");
				String content = scan.nextLine();
				
				if(content.equals("0")) {
					loop = false;
					break;
				}
	
				String seq = getSeq();
				
				Answer a = new Answer(seq,s,content);
				
				Data.answerlist.add(a);
				
				System.out.println();
				
				System.out.println("저장이 완료되었습니다.");
				
				Output.pause();
				
				loop = false;
					
	
			} else {
				Output.subMenuStart(
						String.format("[%s],%s", Data.olist.get(Integer.parseInt(s) - 1).getCategory(),
								Data.olist.get(Integer.parseInt(s) - 1).getTitle()));
				
				int i = 0;
				for(Answer b : Data.answerlist) {
					if(b.getCnum().equals(s)) {
						i = Integer.parseInt(b.getNum());
					}
				}
				
				System.out.println("[질문 내용]");
				for(int c = 0; c  < Data.olist.get(Integer.parseInt(s) - 1).getContent().length(); c++) {
					System.out.printf("%s", Data.olist.get(Integer.parseInt(s) - 1).getContent().charAt(c));
					if(c != 0 && c % 30 == 0) {
						System.out.println();
					}
				}
				
				System.out.println();
				System.out.println("-----------------------------------------");
				
				System.out.println("[질문 답변]");
				for(int k = 0; k  < Data.answerlist.get(i - 1).getContent().length(); k++) {
					System.out.printf("%s", Data.answerlist.get(i-1).getContent().charAt(k));
					if(k != 0 && k % 30 == 0) {
						System.out.println();
					}
				}
				System.out.println();

				Output.subMenuEnd();
				
				System.out.print("질문 답변 수정(취소 : 0)");
				String content = scan.nextLine();
				
				
				 if(content.equals("0")) {
					loop = false;
					
				} if(!content.equals("") && !content.equals("0")) {
					result.setContent(content);
					System.out.println("수정완료");
				}
				
				Output.pause();
				loop = false;
				
				
				
			}
			
		}
		
		Data.save("src\\com\\project\\dentist\\data\\온라인 상담 답글.txt");
	}

	private static String getSeq() {

		int max = 0;
		for (Answer s : Data.answerlist) {

			int seq = Integer.parseInt(s.getNum());

			if (seq > max) {
				max = seq;
			}
		}

		return (max + 1) + "";
	}
}
