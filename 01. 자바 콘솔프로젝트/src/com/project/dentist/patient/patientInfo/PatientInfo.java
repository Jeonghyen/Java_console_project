package com.project.dentist.patient.patientInfo;

import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Login;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Patient;
/**
 * 환자 정보 클래스입니다.
 * @author 고수경
 *
 */
public class PatientInfo {

	/**
	 * 현재 로그인한 회원의 정보를 출력합니다.
	 */
	// 회원 정보 조회
	public static void view() {

		
		
		String name = Login.currentPatient.getName();
		String gender = Login.currentPatient.getGender();
		String birth = Login.currentPatient.getBirthday();
		String phone = Login.currentPatient.getPhoneNum();
		String address = Login.currentPatient.getAddress();
		

		

		Output.subMenuStart("내 정보");
		System.out.println("[이름]" + name);
		System.out.println("[성별]" + (gender.equals("1") ? "남자" : "여자"));
		System.out.println("[생년월일]" + birth);
		System.out.println("[전화번호]" + phone);
		System.out.println("[주소지]" + address);
		Output.subMenuEnd();

		Output.pause();

	



	}
	
	

	/**
	 * 현재 로그인한 회원의 정보를 수정합니다.
	 */
	// 회원 정보 수정
	public static void edit() {
		Scanner scan = new Scanner(System.in);

		
		view();

		Patient result = null;

		for (Patient p : Data.plist) {
			if (p.getId().equals(Login.currentPatient.getId())) {
				result = p;
				break;
			}
		}


		Output.subMenuStart("정보 수정");

		Boolean flag = true;

		if (result != null) {
			while (flag) {

				System.out.print("이름 ✎");
				String name = scan.nextLine();



				if (!name.equals("")) { // 입력을 했을 경우



					if (checkName(name)) {

						result.setName(name); // 이름 수정
						Login.currentPatient.setName(name);
						flag = false;
					} else {
						System.out.println("이름은 2~5자 한글로 입력하세요.");
						flag = true;
					}
				} else {
					flag = false;
				}
			}

			flag = true;

			while (flag) {
				System.out.print("성별 1(남자), 2(여자) ✎");
				String gender = scan.nextLine();

				if (!gender.equals("")) {
					if (gender.equals("1") || gender.equals("2")) {
						result.setGender(gender);
						Login.currentPatient.setGender(gender);
						flag = false;
					} else {
						System.out.println("성별은 1(남자), 2(여자) 중 선택하세요.");
						flag = true;
					}
				} else {
					flag = false;
				}
			}


			flag = true;

			while (flag) {
				System.out.print("생년월일(yyyy-mm-dd) ✎");
				String birth = scan.nextLine();

				if (!birth.equals("")) {

					if (checkBirth(birth)) {
						result.setBirthday(birth);
						Login.currentPatient.setBirthday(birth);
						flag = false;
					} else {
						System.out.println("“yyyy-mm-dd” 형태의 숫자 입력만 허용합니다.");
						flag = true;
					}
				} else {
					flag = false;
				}
			}


			flag = true;

			while (flag) {
				System.out.print("전화번호(010-xxxx-xxxx) ✎");
				String phonenum = scan.nextLine();

				if (!phonenum.equals("")) {
					if (checkPhonenum(phonenum)) {
						result.setPhoneNum(phonenum);
						Login.currentPatient.setPhoneNum(phonenum);
						flag = false;

					} else {
						System.out.println("010-xxxx-xxxx 형태만 허용합니다.");
						flag = true;
					}
				} else {
					flag = false;
				}
			}


			flag = true;

			while (flag) {
				System.out.print("주소지 ✎");
				String address = scan.nextLine();

				if (!address.equals("")) {
					if (checkAddress(address)) {
						result.setAddress(address);
						Login.currentPatient.setAddress(address);
						flag = false;
					} else {
						System.out.println("한글과 숫자만 이용해서 입력하세요.");
						flag = true;
					}
				}else {
					flag = false;
				}
			}



			System.out.println("정보 수정이 모두 완료되었습니다.");
			Data.save(DataPath.회원정보);
			System.out.println();


		} else {
			System.out.println("수정할 정보가 없습니다. 다시 시도해주세요.");
		}
		Output.subMenuEnd();
		Output.pause();

		

	}

	private static boolean checkBirth(String birth) {
		// 1999-09-11

		String a = birth.substring(4, 5);

		String b = birth.substring(7, 8);

		String temp[] = birth.split("-");

		int year = Integer.parseInt(temp[0]);
		int month = Integer.parseInt(temp[1]);
		int day = Integer.parseInt(temp[2]);
		

		
		
		if (!a.equals("-") || !b.equals("-") || year <= 1800 || month < 1 || month > 12 || day < 1
				|| day > 31) {
			return false;
		} else {
			return true;
		}



	}



	private static boolean checkName(String name) {

		if (name.length() < 2 && name.length() > 5) {
			return false;
		}
		for (int i = 0; i < name.length(); i++) {


			char c = name.charAt(i);

			if (c < '가' || c > '힣') {
				return false;
			}

		}
		return true;
	}

	private static boolean checkAddress(String str) {

		for (int i = 0; i < str.length(); i++) {


			char c = str.charAt(i);

			if ((c >= '가' && c <= '힣') || (c > '1' && c < '9')){
				return true;
				
			} else {
				return false;
			}


		}

		return true;


	}

	private static boolean checkPhonenum(String phonenum) {


		// 010-2222-2222
		String one = phonenum.substring(3, 4);
		String two = phonenum.substring(8, 9);

		if (one.equals("-") && two.equals("-")) {
			return true;
		} else {
			return false;
		}

	}



}
