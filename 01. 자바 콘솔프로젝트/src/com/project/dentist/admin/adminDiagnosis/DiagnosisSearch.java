package com.project.dentist.admin.adminDiagnosis;

import java.util.ArrayList;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Dentist;
import com.project.dentist.dataClass.Diagnosis;
import com.project.dentist.dataClass.DiagnosisDocument;
import com.project.dentist.dataClass.DiagnosisInfo;
import com.project.dentist.dataClass.Doctor;
import com.project.dentist.dataClass.Patient;
import com.project.dentist.dataClass.Payment;
import com.project.dentist.dataClass.Treatment;

/**
 * 환자의 진료 기록과 진단서를 조회하는 클래스입니다.
 * @author 고민지
 *
 */
public class DiagnosisSearch extends SearchData { 

	private Patient thePatient;
	private ArrayList<DiagnosisInfo> diagnosises;
	
	/**
	 * 환자의 진료기록과 진단서를 조회하는 전 과정의 흐름을 관리하는 메인 메소드입니다.
	 */
	public void main() {

		this.thePatient = findPatient(); //환자찾기
		
		if(thePatient == null) { return; } //환자찾기 취소

		
		this.diagnosises = findDiagnosisInfo(thePatient); //환자의 진료정보찾기
		
		if(diagnosises.size() == 0) {		 									
			System.out.println("환자의 진료기록을 찾을 수 없습니다.");
			Output.pause();
			return;
		}
		
		
		showDiagnosies();
		
		askDocument(); //환자 진단서 출력하기

		Output.pause();

	}

	private void showDiagnosies() {
		
		Output.subMenuStart("진료 상세기록");
		
		Output.line();
		System.out.printf("[이름] %s\n", thePatient.getName());
		Output.line();
		System.out.println("[내원 날짜]\t[진료 구분]\t[시술 내용]");
		
		for(DiagnosisInfo d : diagnosises) {
			System.out.printf("%s\t%s\t\t%s\n"
					, d.getDate()
					, findClassificationName(d.getClassficationNum())
					, findTreatmentName(d.getTreatmentNum()));
		}
		
		Output.subMenuEnd();
	}

	private void askDocument() {

		while (true) {
			scan = new Scanner(System.in);
			System.out.print("진단서를 확인하시겠습니까? (Y/N): ✎");
			String input = scan.nextLine();

			if (input.toUpperCase().equals("Y")) {
				listDocument();
				return;
			} else if (input.toUpperCase().equals("N")) {
				return;
			} else {
				System.out.println("입력이 올바르지 않습니다. Y 또는 N을 입력해주세요.");
				System.out.println();
			}
		}
	}


	private void listDocument() {

		boolean loop = true;

		while (loop) {

			Output.subMenuStart("환자 진단서 확인");
			for (int i = 0; i < diagnosises.size(); i++) {
				System.out.printf("%d. %s\n", i + 1, diagnosises.get(i).getDate());
			}
			Output.subMenuEnd();

			System.out.print("번호(상위메뉴0): ✎");
			int input = scan.nextInt();

			if (input == 0) {
				loop = false;
			} else if (0 < input && input <= diagnosises.size()) {
				DiagnosisInfo theDiagnosisInfo = diagnosises.get(input - 1);
				showDocument(theDiagnosisInfo);
				Output.pause();
			} else {
				System.out.println("올바른 번호를 입력해주세요."); 
				System.out.println();
			}

		}
	}

	private void showDocument(DiagnosisInfo theDiagnosisInfo) {

		DiagnosisDocument theDocument = findDocument(theDiagnosisInfo);
		Patient thePatient = findPatient(theDocument.getPatientNum());
		Doctor theDoctor = findDoctor(theDiagnosisInfo.getDoctorNum());

		System.out.println("─────────────────────────────────────────");
		System.out.println("\t\t진 단 서");
		System.out.println("───────┬────────┬─────────┬───────────────");
		System.out.printf ("환자성명 | %s\t| 생년월일  | %s\n", thePatient.getName(), thePatient.getBirthday());
		System.out.println("───────┼────────┴─────────┴───────────────");
		System.out.printf("전화번호 | %s\n",	thePatient.getPhoneNum());
		System.out.printf("   주소 | %s\n", 	thePatient.getAddress());
		System.out.println("───────┼─────────────────────────────────");
		System.out.printf("진료구분 | %s\n",	findClassificationName(theDiagnosisInfo.getClassficationNum()));
		System.out.println("───────┼──────────────────────────────────");
		System.out.printf("진료의견 | %s\n",	theDocument.getOpinion());
		System.out.println("───────┼──────────────────────────────────");
		System.out.printf("결제금액 | %,d원\n", findPayAmount(theDiagnosisInfo.getTreatmentNum()));
		System.out.println("───────┴──────────────────────────────────");
		System.out.println("위와 같이 진단함.");
		System.out.println();
		System.out.printf("내원 날짜: %s\n",	theDiagnosisInfo.getDate());
		System.out.println();
		System.out.printf("%s 주소: %s\n",	Dentist.name, Dentist.address);
		System.out.printf("전화번호: %s\n",	Dentist.phoneNum);
		System.out.println();
		System.out.printf("\t\t\t의사 성명: %s\n", theDoctor.getName());
		System.out.println("─────────────────────────────────────────");
	}

}



//1.환자이름검색 
//	1.1 Scanner로 이름 입력받기
//	1.2 회원정보 ArrayList에서 회원정보 검색
// 		- 회원 저장
//	1.4 동명이인 처리
//		- 번호. 홍길동(23세, 남, 서울) 출력
//		- 번호입력받기
//2. 환자 진료 상세기록
//	2.1 표 출력
//		- 회원번호 > 진료정보 
//		- 이름
//		- 내원날짜, 증상, 시술 내용
//3. "진단서를 확인하시겠습니까?" 
//	3.1 Y > 진단서메뉴
//	3.2 N > 상위메뉴