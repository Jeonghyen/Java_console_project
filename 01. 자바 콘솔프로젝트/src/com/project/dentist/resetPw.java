package com.project.dentist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import com.project.dentist.dataClass.Patient;

/**
 * 비밀번호 재설정을 해주는 클래스입니다.
 * @author 김시현
 *
 */
public class resetPw {

	public static void main() {
		System.out.println("비밀번호를 5회 이상 틀렸습니다. 재설정 진입");
		
		String path = "src\\com\\project\\dentist\\data\\회원정보.txt";
		
		Scanner scan = new Scanner(System.in);
		
		
		String input3;
		
		HashMap<String,String> memlist = new HashMap<String,String>();
		
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line = null;
			
			
			while((line = reader.readLine()) != null) {
				String temp[] = line.split(",");
				
				memlist.put(temp[1], temp[3]);
				
			}
			
			boolean loop = true;
			
			while (loop) {
				
				System.out.print("아이디: ");
				String input = scan.nextLine();
				
				System.out.print("이름: ");
				String input2 = scan.nextLine();

				if (memlist.containsKey(input)) {
					if (memlist.get(input).equals(input2)) {
						password(input);
						break;
					} else {
						System.out.println("입력한 정보가 올바르지 않습니다.");
						System.out.println("다시 시도하려면 [엔터], 상위 메뉴로 돌아가려면 0을 입력");
						input3 = scan.nextLine();
						
						if(input3.equals("0")) {
							loop = false;
						} else if(input.equals("")) {
							
						}
					}
				} else {
					System.out.println("입력한 정보가 올바르지 않습니다.");
					System.out.println("다시 시도하려면 [엔터], 상위 메뉴로 돌아가려면 0을 입력");
					input3 = scan.nextLine();
					if(input3.equals("0")) {
						loop = false;
					} else if(input.equals("")) {
						
					}
				}
			}
		} catch (Exception e) {
			System.out.println("resetPw.resetPw");
			e.printStackTrace();
		}
	}
	
	private static void password(String id) {
		
		String path = "src\\com\\project\\dentist\\data\\회원정보.txt";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			System.out.println("본인 인증에 성공했습니다.");
			
			Scanner scan = new Scanner(System.in);
			
			String input = null;
			
			boolean loop = true;
			
			Patient result = null;
			
			for(Patient p : Data.plist) {
				if(p.getId().equals(id)) {
					result = p;
					break;
				}
			}
			
			while(loop) {
				System.out.println("새 비밀번호를 입력해주세요.");
				
				Scanner scan2 = new Scanner(System.in);
				input = scan2.nextLine();
				
				for(int i = 0; i < input.length(); i++) {
					int index = input.charAt(i);
					if(((index >= 48 && index <= 57) || (index >= 33 && index <= 46)|| (index >= 65 && index <= 90) || (index >= 97 && index <= 122)) && (input.length() >= 4 & input.length() <= 12)) {
						loop = false;
					} else {
						break;
					}
				}
				
				if(loop == true) {
					System.out.println();
					System.out.println("비밀번호가 올바르지 않습니다. 다시 입력해주세요.");
				}
				
				if(input.equals(result.getPassword())) {
					loop = true;
					System.out.println();
					System.out.println("기존 비밀번호와 같습니다. 다시 입력해주세요.");
				}
			}
			
			
			
			result.setPassword(input);
			
			save();
			System.out.println("비밀번호가 재설정 되었습니다. 엔터 입력 시 초기화면으로 이동합니다.");
			scan.nextLine();
		} catch (Exception e) {
			System.out.println("resetPw.password");
			e.printStackTrace();
		}
		
		
	}
	 
	/**
	 * 수정된 list를 파일에 저장하는 메소드입니다.
	 */
	public static void save() {
		
	
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(DataPath.회원정보));
			
			for(Patient p : Data.plist) {
	
				String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", p.getSeq(), p.getId(), p.getPassword(), p.getName(), p.getGender(), p.getBirthday(), p.getAddress(), p.getPhoneNum());
				
				writer.write(line);
			}
			
			writer.close();
			
			writer.close();
			
		} catch (Exception e) {
			System.out.println("Data.save");
			e.printStackTrace();
		}
	}
}
