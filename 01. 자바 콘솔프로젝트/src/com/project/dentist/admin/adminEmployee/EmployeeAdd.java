package com.project.dentist.admin.adminEmployee;

import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Doctor;
import com.project.dentist.dataClass.Nurse;

/**
 * 직원 정보를 추가하는 클래스입니다.
 * @author 백재민
 *
 */
public class EmployeeAdd {

	//직원 추가하기
	/**
	 * 직원 정보를 입력 받아 추가합니다.
	 */
	public static void add() {

		boolean loop = true;

		while (loop) {

			Scanner scan = new Scanner(System.in);

			System.out.println();
			System.out.println("================직원 추가===============");
			System.out.println("1. 의사 추가\t2. 간호사 추가\t 0. 취소하기");
			System.out.print("번호 입력✎ > ");
			String emp = scan.nextLine();

			if (emp.equals("1") || emp.equals("2")) {

				System.out.print("이름(0.취소) : ");
				String name = ""; 

				while (true) {
					name = scan.nextLine();
					if (name.equals("0")) {
						EmployeeList.main();
					}
					if(AddCheck.isName(name)) {
						break;
					} else {
						System.out.print("이름(0.취소) : ");
						continue;
					}
				}
				System.out.println();
				System.out.print("성별(1.남자, 2.여자, 0.취소) : ");
				String gender;

				while (true) {
					gender = scan.nextLine();
					if (gender.equals("0")) {
						EmployeeList.main();
					}
					if (AddCheck.isGender(gender)) {
						break;
					} else {
						System.out.print("성별(1.남자, 2.여자, 0.취소) : ");
						continue;
					}
				}

				System.out.println();
				System.out.print("생년월일(YYYY-MM-DD) : ");
				String birth;

				while (true) {
					birth = scan.nextLine();
					if (AddCheck.isBirth(birth)) {
						break;
					} else {
						System.out.print("생년월일(YYYY-MM-DD) : ");
						continue;
					}
				}

				System.out.println();
				System.out.print("전화번호(010-XXXX-XXXX) : ");
				String tel;

				while (true) {
					tel = scan.nextLine();
					if (AddCheck.isTel(tel)) {
						break;
					} else {
						System.out.print("전화번호(010-XXXX-XXXX) : ");
						continue;
					}
				}
				
				System.out.println();
				System.out.print("입사일(YYYY-MM-DD) : ");
				String entry;

				while (true) {
					entry = scan.nextLine();
					if (AddCheck.isBirth(entry)) {
						break;
					} else {
						System.out.print("입사일(YYYY-MM-DD) : ");
						continue;
					}
				}

				if (emp.equals("1")) {
					System.out.println();
					System.out.print("자기소개서 path(.txt 형식으로 입력) : ");
					String path;

					while (true) {
						path = scan.nextLine();
						if (AddCheck.isPath(path)) {
							break;
						} else {
							System.out.print("자기소개서 path(.txt 형식으로 입력) : ");
							continue;
						}
					}
					String seq = getdSeq();

					System.out.println("입력한 정보 : " + seq + " " + name + " " + gender + " " + birth + " " + tel + " " + entry + " " + path);

					Doctor d = new Doctor(seq,name,gender,birth,tel,entry,path);

					Data.dlist.add(d);
					System.out.println("직원 추가가 완료되었습니다.");
					loop = false;
				} else if (emp.equals("2")) {
					String seq = getnSeq();
					System.out.println("입력한 정보 : " + seq + " " + name + " " + gender + " " + birth + " " + tel + " " + entry);

					Nurse n = new Nurse(seq, name, gender, birth, tel, entry);
					Data.nlist.add(n);
					System.out.println("직원 추가가 완료되었습니다.");
					loop = false;
				}
			} else if (emp.equals("0")){
				loop = false;
			} else {
				System.out.println("1, 2, 0 중 올바른 숫자를 입력하세요.");
			}
		}

		Output.pause();
	}


	//의사 번호 부여
	private static String getdSeq() {
		int dmax = 0;
		for (Doctor d : Data.dlist) {
			int seq = Integer.parseInt(d.getSeq());
			if (seq > dmax) {
				dmax = seq;
			}
		}
		return (dmax + 1) + "";
	}

	//간호사 번호 부여
	private static String getnSeq() {
		int nmax = 0;
		for (Nurse n : Data.nlist) {
			int seq = Integer.parseInt(n.getSeq());
			if (seq > nmax) {
				nmax = seq;
			}
		}
		return (nmax + 1) + "";
	}


	//회원 삭제하기
	/**
	 * 직원 이름을 검색해 직원을 삭제합니다.
	 */
	public static void delete() {

		boolean loop = true;

		while (loop) {

			Scanner scan = new Scanner(System.in);
			System.out.print("1. 의사\t2. 간호사\t0. 취소 : ✎__");
			String chose = scan.nextLine();

			if (chose.equals("1") || chose.equals("2") || chose.equals("0")) {

				if (chose.equals("1")) {

					System.out.print("삭제하실 의사 이름을 입력하세요. ✎__ ");

					String input = scan.nextLine();

					Doctor result = null;

					for (Doctor d : Data.dlist) {
						if (d.getName().equals(input)) {
							result = d;
							break;
						}
					}

					if (result != null) {
						Data.dlist.remove(result);
						System.out.println("삭제가 완료되었습니다.");
					} else {
						System.out.println("입력하신 이름이 존재하지 않습니다.");
					}

					Output.pause();

				} else if (chose.equals("2")){

					scan = new Scanner(System.in);
					System.out.print("삭제하실 간호사 이름을 입력하세요. ✎__ ");

					String input = scan.nextLine();

					Nurse result = null;

					for (Nurse n : Data.nlist) {
						if (n.getName().equals(input)) {
							result = n;
							break;
						}
					}

					if (result != null) {
						Data.nlist.remove(result);
						System.out.println("삭제가 완료되었습니다.");
						loop = false;
					} else {
						System.out.println("입력하신 이름이 존재하지 않습니다.");
					}

					Output.pause();
				} else {
					loop = false;
				}

			} else {
				System.out.println("번호를 다시 확인해 주세요.");
			}

		}

	}//delete

}
