package com.project.dentist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import com.project.dentist.dataClass.Patient;
import com.project.dentist.patient.PatientMain;

/**
 * 회원가입 정보를 받아오는 클래스입니다.
 * @author 김시현
 *
 */
public class Sign {

	public void sign() {
	
				String line = "";
				String inputId = null;
				String inputPass = null;
				String inputName = null;
				String inputGender = null;
				String inputBirth = null;
				String inputTel = null;
				String inputAdd = null;
				
				Scanner scan = new Scanner(System.in);
				
				boolean loop = true;
				
				while(loop) { 
					System.out.println("아이디(4 ~ 12자 영문, 숫자 및 특수문자 입력)");
					inputId = scan.nextLine();
					
					for(int i = 0; i < inputId.length(); i++) {
						int index = inputId.charAt(i);
						if(((index >= 48 && index <= 57) || (index >= 33 && index <= 46)|| (index >= 65 && index <= 90) || (index >= 97 && index <= 122)) && (inputId.length() >= 4 & inputId.length() <= 12)) {
							loop = false;
						} else { 
							System.out.println("ID가 올바르지 않습니다. 다시 입력해주세요.");
							break;
						}
					}
					
					for(Patient p : Data.plist) {
						if(inputId.equals(p.getId())) {
							loop = true;
							System.out.println("중복된 ID입니다. 다시 입력해주세요.");
							break;
						}
					} 
				}
				
				loop = true;
				
				System.out.println();
				while(loop) {
					System.out.println("비밀번호(4 ~ 12자 영문, 숫자 및 특수문자 입력)");
					inputPass = scan.nextLine();
					
					for(int i = 0; i < inputPass.length(); i++) {
						int index = inputPass.charAt(i);
						if(((index >= 48 && index <= 57) || (index >= 33 && index <= 46)|| (index >= 65 && index <= 90) || (index >= 97 && index <= 122)) && (inputPass.length() >= 4 & inputPass.length() <= 12)) {
							loop = false;
						} else {
							System.out.println("비밀번호가 올바르지 않습니다. 다시 입력해주세요");
							break;
						}
					}
				}
				
				loop = true;
				System.out.println();
				while(loop) {
					System.out.println("이름(2~5자 한글 입력) : ");
					inputName = scan.nextLine();
					if(!(nameCheck(inputName))) {
						System.out.println("이름이 올바르지 않습니다. 다시 입력해주세요.");
					} else {
						loop = false;
					}
				}
				
				loop = true;				
				System.out.println();
				while(loop) {
					System.out.println("성별(1. 남자, 2.여자) : ");
					inputGender = scan.nextLine();
					if(!(genderCheck(inputGender))) {
						System.out.println("성별이 올바르지 않습니다. 다시 입력해주세요.");
					} else {
						loop = false;
					}
				}
				
				loop = true;
				System.out.println();

				while(loop) {
					System.out.println("생년월일(yyyy-mm-dd) : ");
					inputBirth = scan.nextLine();
					if(!(birthCheck(inputBirth))) {
						System.out.println("생년월일이 올바르지 않습니다. 다시 입력해주세요.");
					} else {
						loop = false;
					}
				}
				
				loop = true;
				System.out.println();

				while(loop) {
					System.out.println("전화번호(010-xxxx-xxxx) : ");
					inputTel = scan.nextLine();
					if(!(TelCheck(inputTel))) {
						System.out.println("전화번호가 올바르지 않습니다. 다시 입력해주세요.");
					} else {
						loop = false;
					}
				}
				
				loop = true;
				System.out.println();

				while(loop) {
					System.out.println("주소지(한글 및 숫자만 입력)");
					inputAdd = scan.nextLine();
					if(!(AddCheck(inputAdd))) {
						System.out.println("주소가 올바르지 않습니다. 다시 입력해주세요.");
					} else {
						loop = false;
					}
				}
				
				String seq = getSeq();
				
				Patient p = new Patient(seq, inputId, inputPass, inputName, inputGender, inputBirth, inputAdd, inputTel);
				
				Data.plist.add(p);
				
				Data.save(DataPath.회원정보);
				
				System.out.println("저장이 완료되었습니다.");
				
			
	}
	private boolean AddCheck(String add) {
		
		for (int i = 0; i < add.length(); i++) {
			char c = add.charAt(i);
			
			if ((c > '가' && c < '힣') || (c > '1' && c < '9')) {
				return true;
			}
		}
		return false;
	}
	
	private boolean TelCheck(String tel) {
		if(tel.charAt(3) == '-' && tel.charAt(8) == '-' && tel.length() < 14) {
			return true;
		}
		return false;
	}
	
	private boolean birthCheck(String birth) {
		if (birth.charAt(4) == '-' && birth.charAt(7) == '-' && birth.length() < 11) {
			return true;
		} 
		return false;
	}
	
	private boolean genderCheck(String gender) {
		if (gender.equals("1") || gender.equals("2")) {
			return true;
		}

		return false;
	}
	
	private boolean nameCheck(String name) {
		if(name.length() < 2 || name.length() > 5) { 
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
	
	private String getSeq() {
		
		int max = 0;
		for(Patient p : Data.plist) {
			
			int seq = Integer.parseInt(p.getSeq());
			
			if (seq > max) {
				max = seq;
			}
		}
		
		return (max + 1) +  "";
	}
}
