package com.project.dentist.patient.appointment;

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
import com.project.dentist.dataClass.Appointment;
import com.project.dentist.dataClass.Diagnosis;
import com.project.dentist.dataClass.WaitingList;



/**
 * 예약/대기취소를 하는 클래스 입니다.
 * 
 * @author 정혜인
 *
 */
public class AppointCancel {

	public static Scanner scan = new Scanner(System.in);
	private static HashMap<String, String> userAlist = new HashMap<String, String>();
	private static HashMap<String, String> userWlist = new HashMap<String, String>();
	private static ArrayList<String> waitList = new ArrayList<String>();
	private static ArrayList<String> waitList_seq = new ArrayList<String>();
	private static ArrayList<String> waitList_appointSeq = new ArrayList<String>();
	private static ArrayList<String> waitList_patientSeq = new ArrayList<String>();
	private static ArrayList<String> waitList_patientSeq2 = new ArrayList<String>();
	private static ArrayList<String> waitList_patientSeq3 = new ArrayList<String>();
	// static String num = "1";
	static String login = Login.currentPatient.getSeq(); // TODO 합칠 때 바꾸기



	// 진료예약
	// 1,1,1,2022-02-03,9:00,3
	// 예약번호,환자번호,의사번호,날짜,시간,진료구분번호


	// 예약대기
	// 1,1,1
	// 대기번호,예약번호,환자번호


	/**
	 * 환자의 대기/예약목록을 출력하는 메소드입니다.
	 */
	public static void viewWaitList() {



		try {

			// Data.load(); // TODO 합칠 때 없애기

			// 예약대기파일 불러오기

			BufferedReader reader = new BufferedReader(new FileReader(DataPath.예약대기));

			String line = null;

			while ((line = reader.readLine()) != null) {


				waitList.add(line);


			}

			for (String s : waitList) {


				String[] list = s.split(",");
				waitList_seq.add(list[0]);
				waitList_appointSeq.add(list[1]);
				waitList_patientSeq.add(list[2]);
				waitList_patientSeq2.add(list[3]);
				waitList_patientSeq3.add(list[4]);



			}


			reader.close();

			Output.subMenuStart("예약/대기 취소");
			// System.out.println("╭──────────────── 예약/대기 취소 ───────╮");

			// 환자의 예약목록 불러오기
			// 날짜는 오늘 이후 날짜


			// 환자 예약 취소하기

			String s = ""; // 예약목록 출력
			String a = ""; // 대기목록 출력
			int seq = 1; // 목록번호
			String checkWait = "";


			// 예약목록 출력
			for (Appointment list : Data.alist) {


				// TODO 메인이랑 합칠 때 바꾸기
				if (Login.currentPatient.getSeq().equals(list.getPatientNum())) { // 환자번호
					// if (num.equals(list.getPatientNum())) { // 로그인한 환자번호를 가진 예약데이터 가져오기

					Calendar now = Calendar.getInstance();
					Calendar appointDate = Calendar.getInstance();

					// 이전날짜 검사

					// 예약날짜
					String[] date = list.getDate().split("-");
					// 년
					int year = Integer.parseInt(date[0]);
					// 월
					int month = Integer.parseInt(date[1]);
					// 일
					int day = Integer.parseInt(date[2]);

					appointDate.set(year, month - 1, day); // 캘린더에 예약날짜 세팅



					boolean checkDate = appointDate.getTimeInMillis() > now.getTimeInMillis(); // 예약날짜
																								// >
																								// 현재날짜
																								// ->
																								// true여야함



					checkWait = "예약";


					if (checkDate) {

						s = String.format("%d. (%s) %s %s", seq, checkWait, list.getDate(),
								list.getTime());

						System.out.println(s);



						userAlist.put(String.valueOf(seq), list.getSeq()); // 글번호,예약번호
						userWlist.put(list.getSeq(), checkWait); // 예약번호,대기/예약
						// System.out.println(userAlist);
						// System.out.println(userWlist);
						seq++;

					}


				}

			} // for


			// 대기 목록 가져오기
			String d = ""; // 대기목록에 있는 예약번호 저장할 변수
			for (WaitingList w : Data.wlist) {


				// String login = Login.currentPatient.getSeq();
				// TODO 메인이랑 합칠 때 바꾸기

				if (login.equals(w.getPatientSeq1()) || login.equals(w.getPatientSeq2())
						|| login.equals(w.getPatientSeq3())) {
					// if (num.equals(w.getPatientSeq1()) || num.equals(w.getPatientSeq2())
					// || num.equals(w.getPatientSeq3())) { // 로그인한 환자번호를 가진 대기데이터 가져오기


					d = w.getAppointmentSeq(); // 예약번호 저장

				}



			}

			// 대기목록 출력
			for (Appointment list : Data.alist) {



				if (d.equals(list.getSeq())) {

					Calendar now = Calendar.getInstance();
					Calendar appointDate = Calendar.getInstance();

					// 이전날짜 검사

					// 예약날짜
					String[] date = list.getDate().split("-");
					// 년
					int year = Integer.parseInt(date[0]);
					// 월
					int month = Integer.parseInt(date[1]);
					// 일
					int day = Integer.parseInt(date[2]);

					appointDate.set(year, month - 1, day); // 캘린더에 예약날짜 세팅



					boolean checkDate = appointDate.getTimeInMillis() > now.getTimeInMillis(); // 예약날짜
																								// >
																								// 현재날짜
																								// ->
																								// true여야함



					checkWait = "대기";


					if (checkDate) {

						s = String.format("%d. (%s) %s %s", seq, checkWait, list.getDate(),
								list.getTime());

						System.out.println(s);



						userAlist.put(String.valueOf(seq), list.getSeq()); // 글번호,예약번호
						userWlist.put(list.getSeq(), checkWait); // 예약번호,대기/예약
						// System.out.println(userAlist);
						// System.out.println(userWlist);
						seq++;

					}


				}

			} // for



			// 대기목록 출력



			if (s.equals("") && a.equals("")) {

				System.out.println();
				System.out.println("예약내역이 없습니다.");
				System.out.println();

				// System.out.println("╰─────────────────────────────────────────────────────╯");
				Output.subMenuEnd();

				// pause();

			} else {
				// System.out.println("╰─────────────────────────────────────────────────────╯");

				Output.subMenuEnd();
				cancle();


			}



		} catch (Exception e) {
			System.out.println("AppointCancle.cancle");
			e.printStackTrace();
		}



	}// view


	/**
	 * 예약/대기목록의 번호를 입력받아 각각을 취소하는 기능으로 갈 메소드입니다.
	 */
	private static void cancle() {

		try {

			// System.out.println(userAlist);
			// System.out.println(userWlist);

			System.out.println();
			System.out.print("번호입력 [상위메뉴 : 0 ] ✎ > ");
			String input = scan.nextLine();


			System.out.println();

			// 예약취소
			// input이 글번호와 같으면 그 글번호의 예약번호를 받아서 예약내역을 가져옴

			if (userAlist.containsKey(input)) {

				String appointNum = "";

				appointNum = userAlist.get(input); // 예약번호



				if (userWlist.get(appointNum).equals("예약")) {



					// 대기파일에 선택한 예약번호 없을시 그냥 삭제
					if (!waitList_appointSeq.contains(appointNum)) {

						deletAppointNoWait(appointNum);

					} else { // 대기파일에 선택한 예약번호 있을시


						for (WaitingList w : Data.wlist) {


							if (w.getAppointmentSeq().contains(appointNum)
									&& w.getPatientSeq1().equals("null")) { // 예약번호에 대기인원 없을시 그냥
																			// 예약번호 삭제

								deletAppointNoWait(appointNum);


							} else if (w.getAppointmentSeq().contains(appointNum)
									&& !w.getPatientSeq1().equals("null")) { //// 예약번호에 대기인원 있을시
																				//// 예약정보에
																				//// 환자번호 바꿔치기(대기번호
																				//// 1번인
																				//// 환자번호로)

								changeAppoint(appointNum);

							}

						}


					}

				} else {

					System.out.println("대기취소");
					deletWait(appointNum);


				}



			} else if (input.equals("0")) {

				return;

			}


			else {
				System.out.println();
				System.out.println("번호를 올바르게 입력해주세요.");
				System.out.println();
				cancle();

			}


		} catch (Exception e) {
			System.out.println("AppointCancle.cancle");
			e.printStackTrace();
		}



	}// cancle


	/**
	 * 환자가 예약을 취소할 때 그 예약의 대기인원이 있는경우
	 * 대기인원을 예약자로 바꿔주는 메소드입니다.
	 * 
	 * @param appointNum 예약번호
	 */
	private static void changeAppoint(String appointNum) {


		// System.out.println("노");



		Output.subMenuStart("예약취소");



		for (Appointment a : Data.alist) {

			if (a.getSeq().equals(appointNum)) {

				int dNum = Integer.parseInt(a.getDoctorNum());
				// int diaNum = Integer.parseInt(a.getClassficationNum());

				System.out.printf("예약일자 : %s %s\n", a.getDate(), a.getTime());
				System.out.printf("담당의사 : %s\n", Data.dlist.get(dNum - 1).getName());
				System.out.printf("진료증상 : %s\n", findClassificationName(a.getClassficationNum()));
				// TODO 진료구분을 숫자로 받을건지 이름으로 받을건지?


			}



		}


		Output.subMenuEnd(); // 출력

		// System.out.println(appointNum);
		System.out.println();
		System.out.print("예약을 취소하시겠습니까?(Y/N) [ 상위메뉴 : 0 ]  ✎ > ");
		String input = scan.nextLine();

		System.out.println();



		boolean result = false;


		if (input.toUpperCase().equals("Y")) {


			//// 예약번호에 대기인원 있을시 예약정보에
			//// 환자번호 바꿔치기(대기번호 1번인
			//// 환자번호로)


			String d = "";


			for (WaitingList w : Data.wlist) {

				if (w.getAppointmentSeq().equals(appointNum)) {

					d = w.getPatientSeq1(); // 대기한 환자번호 d에 저장

				}


			}


			for (Appointment a : Data.alist) {

				if (a.getSeq().equals(appointNum)) {

					a.setPatientNum(d); // 환자번호 바꾸기


				}

			}

			Data.save(DataPath.진료예약);


			// 대기파일 수정 -> 대기에서 예약확정된 환자번호 null로바꾸고 그 뒤의 대기번호2에 있는 환저번호를 대기번호1로 옮기기


			for (WaitingList w : Data.wlist) {

				if (w.getAppointmentSeq().equals(appointNum)) {

					w.setPatientSeq1("null");

					if (w.getPatientSeq1().equals("null")) {

						w.setPatientSeq1(w.getPatientSeq2());
						w.setPatientSeq2(w.getPatientSeq3());
						w.setPatientSeq3("null");

					}

				}


			}

			Data.save(DataPath.예약대기);



			System.out.println("예약이 취소되었습니다.");

			return;



		} else if (input.toUpperCase().equals("N")) {

			System.out.println("상위메뉴로 돌아갑니다.");

			return;


		} else if (input.equals("0")) {


			return;


		} else {

			System.out.println();
			System.out.println("문자를 올바르게 입력해주세요.");
			System.out.println();
			deletAppointNoWait(appointNum);
		}



	}


	/**
	 * 대기를 취소하는 메소드입니다.
	 * 
	 * @param appointNum 예약번호
	 */
	public static void deletWait(String appointNum) {


		try {

			Output.subMenuStart("대기취소");


			for (Appointment a : Data.alist) {

				if (a.getSeq().equals(appointNum)) {

					int dNum = Integer.parseInt(a.getDoctorNum());
					// int diaNum = Integer.parseInt(a.getClassficationNum());

					System.out.printf("예약일자 : %s %s\n", a.getDate(), a.getTime());
					System.out.printf("담당의사 : %s\n", Data.dlist.get(dNum - 1).getName());
					System.out.printf("진료증상 : %s\n",
							findClassificationName(a.getClassficationNum()));
					// 진료구분을 숫자로 받을건지 이름으로 받을건지? > 숫자로


				}



			}



			Output.subMenuEnd(); // 출력

			System.out.println();
			System.out.print("대기를 취소하시겠습니까?(Y/N) [ 상위메뉴 : 0 ]  ✎ > ");
			String input = scan.nextLine();
			System.out.println();



			if (input.toUpperCase().equals("Y")) {


				for (WaitingList w : Data.wlist) {


					if (w.getAppointmentSeq().equals(appointNum)) {

						// TODO 합칠 때 바꾸기

						if (w.getPatientSeq1().equals(login)) {



							w.setPatientSeq1(w.getPatientSeq2());
							w.setPatientSeq2(w.getPatientSeq3());
							w.setPatientSeq3("null");



						} else if (w.getPatientSeq2().equals(login)) {


							w.setPatientSeq2(w.getPatientSeq3());
							w.setPatientSeq3("null");


						} else if (w.getPatientSeq3().equals(login)) {


							w.setPatientSeq3("null");

						}

					}

				}



				// if (w.getPatientSeq1().equals(num)) {
				//
				//
				//
				// w.setPatientSeq1(w.getPatientSeq2());
				// w.setPatientSeq2(w.getPatientSeq3());
				// w.setPatientSeq3("null");
				//
				//
				//
				// } else if (w.getPatientSeq2().equals(num)) {
				//
				//
				// w.setPatientSeq2(w.getPatientSeq3());
				// w.setPatientSeq3("null");
				//
				//
				// } else if (w.getPatientSeq3().equals(num)) {
				//
				//
				// w.setPatientSeq3("null");
				//
				// }
				//
				// }
				//
				//
				//
				// }



				Data.save(DataPath.예약대기);

				System.out.println("대기가 취소되었습니다.");
				// pause();


			} else if (input.toUpperCase().equals("N")) {

				System.out.println("상위메뉴로 돌아갑니다.");
				pause();
				return;


			} else if (input.equals("0")) {


				return;


			} else {

				System.out.println();
				System.out.println("문자를 올바르게 입력해주세요.");
				System.out.println();
				deletWait(appointNum);
			}



		} catch (Exception e) {
			System.out.println("AppointCancle.deletWait");
			e.printStackTrace();
		}



	}


	/**
	 * 취소할 예약에 대기인원이 없는 경우 예약데이터를 삭제하는 메소드입니다.
	 * 
	 * @param appointNum 예약번호
	 */
	public static void deletAppointNoWait(String appointNum) {

		try {

			Output.subMenuStart("예약취소");



			for (Appointment a : Data.alist) {

				if (a.getSeq().equals(appointNum)) {

					System.out.println(findClassificationName(a.getClassficationNum()));

					int dNum = Integer.parseInt(a.getDoctorNum());
					// int diaNum = Integer.parseInt(a.getClassficationNum());

					System.out.printf("예약일자 : %s %s\n", a.getDate(), a.getTime());
					System.out.printf("담당의사 : %s\n", Data.dlist.get(dNum - 1).getName());
					System.out.printf("진료증상 : %s\n",
							findClassificationName(a.getClassficationNum()));
					// TODO 진료구분을 숫자로 받을건지 이름으로 받을건지?


				}



			}


			Output.subMenuEnd(); // 출력

			System.out.println();
			System.out.print("예약을 취소하시겠습니까?(Y/N) [ 상위메뉴 : 0 ]  ✎ > ");
			String input = scan.nextLine();
			System.out.println();



			boolean result = false;


			if (input.toUpperCase().equals("Y")) {


				BufferedWriter writer = new BufferedWriter(new FileWriter(DataPath.진료예약));

				String line = null;
				Appointment delet = null;


				for (Appointment a : Data.alist) { // 예약번호 일치하는 객체 뽑기


					if (a.getSeq().equals(appointNum)) {

						delet = a;

					}



				}


				if (delet == null) {

					System.out.println("일치하는 예약이 없습니다.");
					deletAppointNoWait(appointNum);
				}



				result = Data.alist.remove(delet); // 뽑은 객체 삭제

				Data.save(DataPath.진료예약); // 삭제한 ArrayList 파일에 반영


				if (result) {
					System.out.println();
					System.out.println("예약이 취소되었습니다.");
					System.out.println();
					// pause();
					return;

				} else {
					System.out.println();
					System.out.println("예약취소가 실패하였습니다.");
					System.out.println();
					deletAppointNoWait(appointNum);


				}



			} else if (input.toUpperCase().equals("N")) {

				System.out.println("상위메뉴로 돌아갑니다.");
				pause();
				return;


			} else if (input.equals("0")) {


				return;


			} else {

				System.out.println();
				System.out.println("문자를 올바르게 입력해주세요.");
				System.out.println();
				deletAppointNoWait(appointNum);
			}



		} catch (Exception e) {
			System.out.println("AppointCancle.deletAppoint");
			e.printStackTrace();
		}


	}



	public static void pause() {

		System.out.println("계속하시려면 [엔터]를 입력하세요.");


		// 프로그램일시 정지

		Scanner scan = new Scanner(System.in);

		scan.nextLine(); // 블럭 == 일시정지


	}

	private static String findClassificationName(String classificationNum) {

		for (Diagnosis s : Data.clist) { // 진료구분
			if (s.getSeq().equals(classificationNum)) {
				return s.getDiagnosis_name();
			}
		}

		return null;
	}



}// class


