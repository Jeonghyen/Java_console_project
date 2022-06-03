package com.project.dentist;

import java.io.BufferedReader;
import java.io.FileReader;
import com.project.dentist.dataClass.Patient;
import com.project.dentist.patient.PatientMain;

/**
 * 로그인을 하는 클래스입니다.
 * @author 김승연
 *
 */
public class Login {
	
	//Patient 생성자 (회원번호, 아이디, 비밀번호, 이름, 성별, 생년월일, 주소, 핸드폰번호)
	public static Patient currentPatient;
	/**
	 * 아이디와 비밀번호를 입력받아 중복된값을 검사하는 메소드입니다.
	 * @param 아이디
	 * @param 비밀번호
	 */
	public static void login(String id, String pw) {

		
		try {
			
			//1,fj0yfE6Z0,xfeIKsSU,권희성,1,1956-02-27,대구시 강남구 다산동 60번지,010-4428-3324
			BufferedReader reader = new BufferedReader(new FileReader("src\\com\\project\\dentist\\data\\회원정보.txt"));
			
			String line = "";
			int exist = -1;
			
			while ((line = reader.readLine()) != null) {
				String temp[] = line.split(",");
				if (temp[1].equals(id)) {
					//아이디 존재
					//1번 회원 아이디 fj0yfE6Z0, 비밀번호 xfeIKsSU -> 테스트용
						exist = 1;
					if (temp[2].equals(pw)) { 
						currentPatient = new Patient(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7]);
						System.out.println();
						System.out.println("🦷환자 모드로 로그인 성공했습니다!!🦷");
						PatientMain.main();
						
						//비밀번호 O, 로그인 성공
					
					} else {
						Main.loginTry++;
						System.out.println("비밀번호가 올바르지 않습니다. 다시 입력해주세요.");
						
					}
				}
				
			}
			if (exist < 0) {
				System.out.println("아이디가 올바르지 않습니다. 다시 입력해주세요.");
			}
			
		
			
			
		} catch (Exception e) {
			System.out.println("Login.login");
			e.printStackTrace();
		}
		
		
		
		
	}

}
