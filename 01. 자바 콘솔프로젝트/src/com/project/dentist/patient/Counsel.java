package com.project.dentist.patient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Login;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Answer;
import com.project.dentist.dataClass.OnlineQuestion;

public class Counsel {



	private static Scanner scan;

	public Counsel() {


		this.scan = new Scanner(System.in); // 생성자에 처음부터 스캐너 생성해놓기

	}



	public static void counsel() {

		Output.subMenuStart("진료상담");
		String[] menu = {"FAQ", "온라인 상담", "상위메뉴"};
		Output.subMenuContent(menu);
		Output.subMenuEnd();


		Scanner scan = new Scanner(System.in);

		System.out.print("번호 입력✎ > ");

		String input = scan.nextLine();


		if (input.equals("1")) {

			Output.subMenuStart("FAQ");
			String[] menu2 = {"치아교정", "치아미백", "충치치료", "임플란트", "라미네이트", "치아잇몸성형", "상위메뉴"};
			Output.subMenuContent(menu2);
			Output.subMenuEnd();



			System.out.print("번호 입력 ✎ >");
			input = scan.nextLine();


			if (input.equals("1")) {

				faq(DataPath.FAQ질문_치아교정, DataPath.FAQ답변_치아교정);

			} else if (input.equals("2")) {
				faq(DataPath.FAQ질문_치아미백, DataPath.FAQ답변_치아미백);

			} else if (input.equals("3")) {

				faq(DataPath.FAQ질문_충치치료, DataPath.FAQ답변_충치치료);
			} else if (input.equals("4")) {
				faq(DataPath.FAQ질문_임플란트, DataPath.FAQ답변_임플란트);

			} else if (input.equals("5")) {
				faq(DataPath.FAQ질문_라미네이트, DataPath.FAQ답변_라미네이트);

			} else if (input.equals("6")) {
				faq(DataPath.FAQ질문_치아잇몸성형, DataPath.FAQ답변_치아잇몸성형);

			} else if (input.equals("0")) {


			} else {

				System.out.println("번호를 올바르게 입력하세요.");
			}



		} else if (input.equals("2")) {

			online();


		} else if (input.equals("0")) {

			return;

		} else {

			System.out.println("번호를 올바르게 입력하세요.");

		}



	}


	public static void online() {

		Output.subMenuStart("온라인 상담");
		String[] menu = {"질문 등록", "질문 내역", "상위메뉴"};
		Output.subMenuContent(menu);
		Output.subMenuEnd();


		try {

			Scanner scan = new Scanner(System.in);

			System.out.print("번호 입력✎:");
			String input = scan.nextLine();

			if (input.equals("1")) {


				writeQuestion();


			} else if (input.equals("2")) {

				viewQuestion();


			} else if (input.equals("0")) {



			} else {

				System.out.println();
				System.out.println("번호를 올바르게 입력하세요.");
				System.out.println();

			}



		} catch (Exception e) {
			System.out.println("counsel.online");
			e.printStackTrace();
		}



	}


	private static void viewQuestion() {


		String id = Login.currentPatient.getId();



		System.out
				.println("=======================================================================");

		System.out.printf("[%s님의 질문 내역]\n", Login.currentPatient.getName());

		System.out.println();

		System.out.println("[글번호]    [말머리]\t[날짜]\t                 [제목]\n");

		int n = 0;
		int seq = 1;
		String s = "";

		ArrayList<String> userQlist = new ArrayList<String>();
		HashMap<String, String> num = new HashMap<String, String>(); // 글번호 저장 리스트


		userQlist.clear();



		for (OnlineQuestion o : Data.olist) {


			if (o.getId().equals(id)) {

				s = String.format("%3d.\t    [%2s]\t[%10s]\t  %-30s\n", seq, o.getCategory(),
						o.getDate(), o.getTitle());


				num.put(String.valueOf(seq), o.getSeq());
				seq++;
				n++;


				userQlist.add(s);

			}

		}

		// 등록순으로 정렬
		for (int i = 0; i < userQlist.size(); i++) {

			System.out.println(userQlist.get(i));
		}



		if (n == 0) {

			System.out.printf("%s님의 질문 내역이 없습니다.\n", Login.currentPatient.getName());
		}

		System.out
				.println("=======================================================================");



		try {

			Scanner scan = new Scanner(System.in);

			System.out.print("번호입력(상위메뉴:0)✎ > ");
			String input = scan.nextLine();


			if (input.equals("")) {

				userQlist.clear();
				num.clear();
				return;


			} else

			if (input.equals("0")) {

				userQlist.clear();

				return;
			}



			// 답변 출력

			BufferedReader reader = new BufferedReader(new FileReader(DataPath.온라인상담답글));

			ArrayList<String> answer = new ArrayList<String>();

			String line = null;


			// 답변 없을 때 예외처리

			for (Answer a : Data.answerlist) {

				answer.add(a.getCnum()); // 답글 목록에 글번호만 모아서 ArrayList 만들기

			}

			if (!num.containsKey(input)) {

				System.out.println();
				System.out.println("번호를 올바르게 입력하세요."); // 질문번호 잘못 입력했을 때
				System.out.println();
				viewQuestion();

			} else if (!answer.contains(num.get(input))) {

				System.out.println();
				System.out.println("<등록된 답변이 없습니다.>"); // 답변이 등록되어있지 않을 때
				System.out.println();
				viewQuestion();
			}


			while ((line = reader.readLine()) != null) {


				String[] temp = line.split(",");



				// 입력한 문자가 숫자가 아닌 스트링이면 여기 input에서 에러가 난다
				// 숫자가 형태가 아닌 input을 숫자로 변환하려 하기 때문
				// num을 HashMap<Integer,String> 에서 <String,String>으로 변환


				if (temp[1].equals(num.get(input))) {



					System.out.println(
							"========================================================================================");
					System.out.println();
					System.out.println(Data.olist.get(Integer.parseInt(temp[1]) - 1).getTitle());
					System.out.println();
					System.out.println(
							"----------------------------------------------------------------------------------------");
					System.out.println();
					System.out.println(Data.olist.get(Integer.parseInt(temp[1]) - 1).getContent());
					System.out.println();

					System.out.println(
							"----------------------------------------------------------------------------------------");



					System.out.println("└▶\n" + temp[2]);

					System.out.println(
							"========================================================================================");


					pause();
					viewQuestion();

				}



			}



		} catch (Exception e) {

			System.out.println("번호를 올바르게 입력해주세요.");
			System.out.println("Counsel.viewAnswer");
			e.printStackTrace();

		}



	}



	private static void writeQuestion() {



		try {


			// TODO
			Scanner scan = new Scanner(System.in);
			System.out.println("[질문을 등록합니다.]");
			System.out.println();

			System.out.print("제목(20자 이내) : ");
			String title = scan.nextLine();

			System.out.println();

			System.out.print("말머리(1. 충치, 2. 잇몸, 3. 교정, 4. 발치, 5. 미백, 6. 기타) :");
			String category = scan.nextLine();

			System.out.println();

			System.out.print("내용(300자 이내):");
			String content = scan.nextLine();
			System.out.println();

			if (title.length() > 20 || content.length() > 300
					|| (!category.equals("1") && !category.equals("2") && !category.equals("3")
							&& !category.equals("4") && !category.equals("5")
							&& !category.equals("6"))) {

				System.out.println("등록에 실패했습니다. 올바른 형식을 확인 후 다시 입력해주세요.");
				System.out.println();


				System.out.print("계속하시려면[Enter]/상위메뉴[0]:");
				System.out.println();
				String input = scan.nextLine();

				if (input.equals("0")) {

					return;

				} else if (input.equals("")) {

					writeQuestion();

				}



			} else {


				System.out.println("질문을 등록하시겠습니까?(Y/N)");
				String input = scan.nextLine();


				if (input.toUpperCase().equals("Y")) {


					Calendar c = Calendar.getInstance();

					String date = String.format("%tF %tT", c, c);


					String seq = seq();

					OnlineQuestion o = new OnlineQuestion(seq, Login.currentPatient.getId(), date,
							category, title, content);

					Data.olist.add(o);

					System.out.println();

					if (questionSave()) {

						System.out.println("<등록이 완료되었습니다>.");
						pause();

					} else {

						throw new Exception();
					}



				} else if (input.toUpperCase().equals("N")) {

					System.out.println("질문 등록이 취소되었습니다. 엔터를 입력하면 상위 메뉴로 돌아갑니다.");
					pause();

					return;


				} else {

					System.out.println("올바른 문자를 입력해주세요.(Y/N)");
					writeQuestion();
				}

			}



		} catch (

		Exception e) {
			System.out.println("Counsel.writeQuestion");
			e.printStackTrace();
			System.out.println("등록에 실패했습니다. 올바른 형식을 확인 후 다시 입력해주세요.");
		}



	}

	public static boolean questionSave() {

		try {

			// 온라인질문

			BufferedWriter writer = new BufferedWriter(new FileWriter(DataPath.온라인상담질문));

			// TODO
			for (OnlineQuestion s : Data.olist) {


				// 번호,아이디,날짜시간,카테고리,제목,내용

				String line = String.format("%s,%s,%s,%s,%s,%s\n", s.getSeq(), s.getId(),
						s.getDate(), s.getCategory(), s.getTitle(), s.getContent());



				writer.write(line);

			}

			writer.close();


			return true;



		} catch (Exception e) {
			System.out.println("Data.save");
			e.printStackTrace();


			return false;
		}

	}


	public static void faq(String q, String a) {

		Scanner scan = new Scanner(System.in);
		String input;
		String question = "";

		try {

			BufferedReader reader = new BufferedReader(new FileReader(q));

			ArrayList<String> list = new ArrayList<String>();

			String line = null;

			while ((line = reader.readLine()) != null) {


				list.add(line);



			}



			int n = 0;
			int page = 1;


			System.out.println("────────────────────────────────────────");
			System.out.println();

			for (int i = 0; i < list.size(); i++) { // page출력


				System.out.println(list.get(i));

				n++;


				System.out.println();
				if (n == list.size()) {
					System.out.println("<마지막페이지 입니다.>");


				}


				if (n % 5 == 0 || n == list.size()) {


					int totalPage = ((int) (list.size() / 5));

					// if (totalPage % 5 > 0) {
					//
					// totalPage++;
					// }

					if (list.size() > 5 * totalPage) {

						totalPage++;
					} // 페이징처리


					System.out.printf("                                              page[%d/%d]\n",
							page, totalPage);
					System.out.println();

					System.out.println("────────────────────────────────────────");

					nextPage();
					System.out.print("번호입력✎ > ");
					input = scan.nextLine();

					if (input.equals("0")) {

						break;

					}



					BufferedReader reader2 = new BufferedReader(new FileReader(a));
					reader = new BufferedReader(new FileReader(q));



					ArrayList<String> alist = new ArrayList<String>();



					for (String s : list) { // FAQ질문출력

						String[] temp2 = s.split(",");


						if (input.toUpperCase().equals(temp2[0])) {


							alist.add(temp2[0]);

							System.out.println(
									"================================================================");
							System.out.println(s);


						}


					}



					if (input.equals("")) {


					} else if (!alist.contains(input.toUpperCase())) {
						System.out.println();

						System.out.println("번호를 올바르게 입력해주세요.");
						System.out.println();


					}

					while ((line = reader2.readLine()) != null) { // FAQ답변출력

						String[] temp = line.split("@");


						if (temp[1].equals(input.toUpperCase())) {



							System.out.println(
									"---------------------------------------------------------------");
							System.out.println("▶" + temp[2]);

							System.out.println(
									"================================================================");


							System.out.println("계속하시려면[Enter]/상위메뉴[0]:");
							input = scan.nextLine();

							if (input.equals("0")) {

								return;

							} else if (input.equals("")) {


							} else {

								System.out.println("번호를 바르게 입력해주세요.");
							}



						}


					}


					page++;

				}


			}



		} catch (

		Exception e) {
			System.out.println("상담.counsel");
			e.printStackTrace();
		}
	}



	public static void pause() {

		System.out.println("계속하시려면 [엔터]를 입력하세요.");


		// 프로그램일시 정지

		Scanner scan = new Scanner(System.in);

		scan.nextLine(); // 블럭 == 일시정지


	}

	public static void nextPage() {

		System.out.println("다음페이지[Enter]/상위메뉴[0] 입력");



	}

	private static String seq() {

		int max = 0;

		for (OnlineQuestion s : Data.olist) {

			int seq = Integer.parseInt(s.getSeq());


			if (seq > max) {


				max = seq;


			}


		}
		return (max + 1) + "";
	}


}
