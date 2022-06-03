package com.project.dentist.admin.adminDiagnosis;

import java.util.ArrayList;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.Output;
import com.project.dentist.dataClass.*;

/**
 * 데이터 정보간 연결관계를 이용하여 특정 데이터 정보를 통해 다른 데이터 정보를 찾는 메소드입니다.
 * @author 고민지
 *
 */
public class SearchData {  

	public Scanner scan;
	
	public SearchData() {
		this.scan = new Scanner(System.in);
	}

	/**
	 * 환자 성명을 입력받아 특정 환자의 정보를 찾는 메소드입니다.
	 * @return 환자 정보 객체
	 */
	public Patient findPatient() {
		
		ArrayList<Patient> patients = new ArrayList<Patient>(); //검색한 이름에 맞는 환자번호 저장 
		Patient thePatient = null;
				
		while(thePatient == null) {
			
			Output.subMenuStart("환자 정보 검색");
			
			System.out.print("이름: ✎(상위메뉴: 0)");
			String input = scan.nextLine(); 
			
			if(input.equals("0")) {
				Output.subMenuEnd();
				System.out.println();
				break;
			}
			
			for(Patient p : Data.plist) {   //환자정보 ArrayList
				if(p.getName().equals(input)) {
					patients.add(p);
				}
			}
			
			if(patients.size() == 1) {
				thePatient = patients.get(0);
				
			} else if(patients.size() == 0) {
				System.out.println("등록된 환자명을 입력해주세요."); 
				
			} else {
				
				boolean loop = true;
				
				while(loop) {
					System.out.println("-------------------------------");
					
					for(int i=0; i<patients.size(); i++) {
					
						System.out.printf("%d. %s(%s, %s, %s)\n"
								, i+1
								, patients.get(i).getName()
								, patients.get(i).getBirthday()
								, patients.get(i).getGender().equals("1") ? "남자" : "여자"
								, patients.get(i).getAddress());
					}
					System.out.println();
					
					System.out.print("번호: ✎");
					int inputNum = scan.nextInt(); 
					
					if(1 <= inputNum && inputNum <= patients.size()) {
						thePatient = patients.get(inputNum - 1);
						loop = false;
					} else {
						System.out.println("알맞은 번호를 입력해주세요."); 
					}
				}
			}
			Output.subMenuEnd();
			System.out.println();
		
		}
		return thePatient;
	}
	
	/**
	 * 시술 번호를 이용하여 시술명을 찾는 메소드입니다.
	 * @param treatmentNum 시술 번호
	 * @return 시술 번호와 일치하는 시술명
	 */
	public String findTreatmentName(String treatmentNum) {

		for (Treatment t : Data.tlist) { // 시술내용
			if (t.getSeq().equals(treatmentNum)) {
				return t.getTreamentName();
			}
		}

		return null;
	}

	/**
	 * 진료 구분 번호를 이용하여 진료 구분명을 찾는 메소드입니다.
	 * @param classificationNum 진료 구분 번호
	 * @return 진료 구분 번호와 일치하는 진료 구분명
	 */
	public String findClassificationName(String classificationNum) {

		for (Diagnosis s : Data.clist) { // 진료구분
			if (s.getSeq().equals(classificationNum)) {
				return s.getDiagnosis_name();
			}
		}

		return null;
	}
	
	/** 
	 * 시술 번호를 이용하여 결제 금액을 찾는 메소드입니다.
	 * @author 백재민
	 * @param treatmentNum 시술 번호
	 * @return 시술 번호와 일치하는 결제 금액
	 */
	public int findPayAmount(String treatmentNum) { 
		
		for (Payment p : Data.paylist) { // 결제 어레이리스트에서 결제 클래스 불러오기
			if (p.getSeq().equals(treatmentNum)) {
				return Integer.parseInt(p.getPay());
			}
		}
		return -1;
	}

	/**
	 * 환자 번호를 이용하여 환자 객체를 찾는 메소드입니다.
	 * @param patientNum
	 * @return 환자 번호와 일치하는 환자 정보 객체
	 */
	public Patient findPatient(String patientNum) {

		for (Patient p : Data.plist) {
			if (p.getSeq().equals(patientNum)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * 의사 번호를 이용하여 의사 객체를 찾는 메소드입니다.
	 * @param doctorNum 의사 번호
	 * @return 의사 번호와 일치하는 의사 정보 객체
	 */
	public Doctor findDoctor(String doctorNum) {

		for (Doctor d : Data.dlist) {
			if (d.getSeq().equals(doctorNum)) {
				return d;
			}
		}
		return null;
	}


	/**
	 * 진료 정보 객체를 이용하여 진료 정보 번호와 일치하는 진단서 객체를 찾는 메소드입니다.
	 * @param theDiagnosisInfo 진료 정보 객체
	 * @return 진료 정보 번호와 일치하는 진단서 객체
	 */
	public DiagnosisDocument findDocument(DiagnosisInfo theDiagnosisInfo) {

		for (DiagnosisDocument d : Data.ddlist) {
			if (d.getDiagnosisNum().equals(theDiagnosisInfo.getSeq())) {
				return d;
			}
		}
		return null;
	}

	/**
	 * 환자 정보 객체를 이용하여 모든 진료 정보객체를 찾는 메소드입니다.
	 * @param thePatient 환자 정보 객체
	 * @return 환자 번호와 일치하는 모든 진료 정보 객체
	 */
	public ArrayList<DiagnosisInfo> findDiagnosisInfo(Patient thePatient) {
		
		ArrayList<DiagnosisInfo> diagnosises = new ArrayList<DiagnosisInfo>(); 
		
		for (DiagnosisInfo d : Data.dglist) {
			if (d.getPatientNum().equals(thePatient.getSeq())) { // 환자번호와 진료정보의 환자번호가 같으면
				diagnosises.add(d);
			}
		}
		
		return diagnosises;
	}



}

