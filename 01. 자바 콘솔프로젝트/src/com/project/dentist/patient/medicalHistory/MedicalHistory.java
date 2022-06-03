package com.project.dentist.patient.medicalHistory;

import java.util.ArrayList;
import java.util.Scanner;

import com.project.dentist.Data;
import com.project.dentist.Login;
import com.project.dentist.Output;
import com.project.dentist.dataClass.Dentist;
import com.project.dentist.dataClass.Diagnosis;
import com.project.dentist.dataClass.DiagnosisDocument;
import com.project.dentist.dataClass.DiagnosisInfo;
import com.project.dentist.dataClass.Doctor;
import com.project.dentist.dataClass.Payment;
import com.project.dentist.dataClass.Treatment;
import com.project.dentist.patient.PatientOutput;
/**
 * 환자 진료 내역 클래스입니다.
 * @author 고수경
 */
public class MedicalHistory {

	
	/**
	 * 환자의 상세 진료 정보 내역을 출력하고 진료 내역에 해당하는 진단서를 조회합니다.
	 */
	// 진료 정보 + 진단서 조회
	public static void view() {



		Scanner scan = new Scanner(System.in);
		String date = "";
		String treatmentNum = "";
		String treatment = "";
		String num = "";
		int cnt = 0;




		String name = Login.currentPatient.getName();
		String seq = Login.currentPatient.getSeq();



		ArrayList<DiagnosisInfo> diagnosises = new ArrayList<DiagnosisInfo>(); // 찾는환자의 진료정보들


		Output.subMenuStart(name + "님 진료 내역");
		System.out.println("      [번호]      [진료 날짜]    [시술 내용]");

		Data.dglist.sort((d1, d2) -> d1.getDate().compareTo(d2.getDate()));
		
		for (DiagnosisInfo d : Data.dglist) {

			if (d.getPatientNum().equals(seq)) {
				cnt++;
				num = d.getSeq();
				date = d.getDate();
				treatmentNum = d.getTreatmentNum();

				diagnosises.add(d);

				for (Treatment t : Data.tlist) {
					if (treatmentNum.equals(t.getSeq())) {
						treatment = t.getTreamentName();

					}
				}

				System.out.printf("	%s	%s  %s\n", cnt, date, treatment);
				System.out.println();



			}

		}



		System.out.println("╰────────────────────────────────────────────╯");



		System.out.print("진단서를 확인하려면 확인하고 싶은 날짜의 번호를 입력하세요.\n(0입력 시 내 진료 정보 메뉴로 돌아갑니다) ✎ > ");
		boolean flag = true;
		while (flag) {
			int choice = scan.nextInt();
			if (choice <= diagnosises.size() && choice > 0) {
				DiagnosisInfo theDiagnosisInfo = diagnosises.get(choice - 1);


				viewRecord(theDiagnosisInfo, name);



				flag = false;
			} else if (choice == 0) {
				PatientOutput.medicalinfomenu();
				flag = false;
			} else {
				System.out.println();
				System.out.print(
						"존재하지 않은 진료 내역을 선택하였습니다.\n확인하고 싶은 날짜의 번호를 다시 입력하세요. 0 입력 시 내 진료 정보 메뉴로 돌아갑니다. ✎");
				flag = true;
			}
		}

		Output.pause();



	}

	// 진단서 출력 메소드
	private static void viewRecord(DiagnosisInfo theDiagnosisInfo, String name) {

		Doctor theDoctor = findDoctor(theDiagnosisInfo.getDoctorNum());
		DiagnosisDocument theDocument = findDocument(theDiagnosisInfo);

		System.out.println();
		System.out.println("─────────────────────────────────────────");
		System.out.println("\t\t진 단 서");
		System.out.println("───────┬────────┬─────────┬───────────────");
		System.out.printf("환자성명 | %s\t| 생년월일  | %s\n", name, Login.currentPatient.getBirthday());
		System.out.println("───────┼────────┴─────────┴───────────────");
		System.out.printf("전화번호 | %s\n", Login.currentPatient.getPhoneNum());
		System.out.printf("   주소 | %s\n", Login.currentPatient.getAddress());
		System.out.println("───────┼─────────────────────────────────");
		System.out.printf("진료구분 | %s\n", findClassificationName(theDiagnosisInfo.getTreatmentNum()));
		System.out.println("───────┼──────────────────────────────────");
		System.out.printf("진료의견 | %s\n", theDocument.getOpinion());
		System.out.println("───────┼──────────────────────────────────");
		System.out.printf("결제금액 | %,d원\n", findPayAmount(theDiagnosisInfo.getTreatmentNum()));
		System.out.println("───────┴──────────────────────────────────");
		System.out.println("위와 같이 진단함.");
		System.out.println();
		System.out.printf("내원 날짜: %s\n", theDiagnosisInfo.getDate());
		System.out.println();
		System.out.printf("%s 주소: %s\n", Dentist.name, Dentist.address);
		System.out.printf("전화번호: %s\n", Dentist.phoneNum);
		System.out.println();
		System.out.printf("\t\t\t의사 성명: %s\n", theDoctor.getName());
		System.out.println("─────────────────────────────────────────");


	}
	
	private static int findPayAmount(String treatmentNum) { 
		
		for (Payment p : Data.paylist) { // 결제 어레이리스트에서 결제 클래스 불러오기
			if (p.getSeq().equals(treatmentNum)) {
				return Integer.parseInt(p.getPay());
			}
		}
		return -1;
	}
	private static String findClassificationName(String classificationNum) {

		for (Treatment t : Data.tlist) { // 진료구분
			if (t.getSeq().equals(classificationNum)) {
				return t.getTreamentName();
			}
		}

		return null;
	}

	private static Doctor findDoctor(String doctorNum) {

		for (Doctor d : Data.dlist) {
			if (d.getSeq().equals(doctorNum)) {
				return d;
			}
		}
		return null;
	}


	private static DiagnosisDocument findDocument(DiagnosisInfo theDiagnosisInfo) {

		for (DiagnosisDocument d : Data.ddlist) {
			if (d.getDiagnosisNum().equals(theDiagnosisInfo.getSeq())) {
				return d;
			}
		}
		return null;
	}



}
