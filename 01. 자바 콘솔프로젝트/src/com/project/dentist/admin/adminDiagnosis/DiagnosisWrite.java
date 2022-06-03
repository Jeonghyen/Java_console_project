package com.project.dentist.admin.adminDiagnosis;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import com.project.dentist.Data;
import com.project.dentist.DataPath;
import com.project.dentist.Output;
import com.project.dentist.dataClass.*;
import com.project.dentist.patient.appointment.Appointments;

/**
 * 환자의 진료정보 및 진단서를 작성하고 다음 진료일정을 예약하는 클래스입니다.
 * @author 고민지
 *
 */
public class DiagnosisWrite extends SearchData { 

	private String treatmentNum;
	private String opinion;
	private Patient thePatient;
	private Appointment recentDiagnosis;
	
	
	/**
	 * 환자의 진료기록과 진단서를 작성하고 다음 진료일정을 예약하는 전 과정의 흐름을 관리하는 메인 메소드입니다.
	 */
	public void main() {
		
		this.thePatient = findPatient();	//환자찾기
		
		if(thePatient == null) { return; }	//환자찾기 취소
			
		
		this.recentDiagnosis = findRecentDiagnosis(); //환자의 최근 진료기록찾기
		
		if(recentDiagnosis == null) {
			
			System.out.println("모든 진료 내역을 작성하셨습니다.");
			Output.pause();
			return;
		}
		
		
		writeDiagnosisInfo();				//진료정보 작성하기
		
		String result = askSaveWriting();	//진료정보 저장하기
		
		if(result.equals("N")) { return; } 	//진료정보 저장하기 취쇠
		
		
		setAppointment();					//다음 진료일정 예약하기
		
		Output.pause();
	}


	private void writeDiagnosisInfo() {
		
		Output.subMenuStart("진료 정보 작성");
		System.out.printf("<%s>\n", recentDiagnosis.getDate());
		Output.line();
		System.out.printf("이름: %s\n" , thePatient.getName());
		System.out.printf("생년월일: %s\n", thePatient.getBirthday());
		System.out.printf("주소: %s\n", thePatient.getAddress());
		System.out.printf("번호: %s\n", thePatient.getPhoneNum());
		System.out.println();
		
		writeDiagnosis();
		
		writeOpinion();
		
		Output.subMenuEnd();		
	}



	private void writeOpinion() {

		while(true) {
			System.out.println("▶ 의사 소견 작성 ─────────────────────────");
			
			System.out.print("소견: ✎"); 
			opinion = scan.nextLine(); 
			
			if(opinion != null && !opinion.equals("")) {
				return;
			} else {
				System.out.println("소견을 작성해주세요.");
			}
			System.out.println();
		}
	}

	private void writeDiagnosis() {
		
		while(true) {
			System.out.println("▶ 시술 선택 ─────────────────────────");
			
			listTreatmemt();
			
			System.out.print("번호: ✎"); 
			scan = new Scanner(System.in);
			treatmentNum = scan.nextLine(); 
			
			try {					
				if(1<=Integer.parseInt(treatmentNum) && Integer.parseInt(treatmentNum) <= Data.tlist.size()) {
					return;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("올바른 번호를 입력해주세요.");
				System.out.println();
			}

			System.out.println();
		}
	}

	private String askSaveWriting() {
		
		while(true) {
			
			System.out.print("작성하신 진료 정보를 저장하시겠습니까? (Y/N): ✎");
			String input = scan.nextLine();
			System.out.println();
			
			if (input.toUpperCase().equals("Y")) {
				saveWriting();
				return "Y";
				
			} else if (input.toUpperCase().equals("N")) {
				return "N";
			} else {
				System.out.println("입력이 올바르지 않습니다. Y 또는 N을 입력해주세요.");
				System.out.println();
			}
			
		}
	} 

	private void saveWriting() {

		//진료기록 저장
		String diagnosisNum = findMaxSeq_dglist();
		
		DiagnosisInfo dg
				= new DiagnosisInfo(diagnosisNum, treatmentNum, recentDiagnosis); 
		
		useItems(treatmentNum);  //원래DiagnosisInfo의 생성자에 들어가야함
		
		Data.dglist.add(dg);
		Data.save(DataPath.진료정보);
		
		
		//진단서 저장
		DiagnosisDocument dd
				= new DiagnosisDocument(findMaxSeq_ddlist(), thePatient.getSeq(), diagnosisNum, opinion);
		
		Data.ddlist.add(dd);
		Data.save(DataPath.진단서);
		
		System.out.println("작성하신 진료 정보가 저장되었습니다.");
		System.out.println();
	}
	
	private void setAppointment() {
		
		while(true) {
			
			System.out.print("다음 진료 날짜를 입력하시겠습니까? (Y/N): ✎");
			String input = scan.nextLine();
			System.out.println();
			
			if (input.toUpperCase().equals("Y")) {
				
				Appointments appointment = new Appointments(thePatient);
				appointment.make();
				
				return;
				
			} else if (input.toUpperCase().equals("N")) {
				
				return;

			} else {
				
				System.out.println("입력이 올바르지 않습니다. Y 또는 N을 입력해주세요.");
				System.out.println();
			}
			
		}
		
	}
	
	private void useItems(String treatmentNum) {
		
		String[] usedItems = null;
		
		for(Treatment t : Data.tlist) {
			if(t.getSeq().equals(treatmentNum)) {
				usedItems = t.getItemAllNums();
			}
		}
		
		for(int i=0; i<usedItems.length; i++) {
			for(Item item : Data.ilist) {
				if(item.getSeq().equals(usedItems[i])) {
					item.setAmount(item.getAmount() - 1);
				}
				order(item);
			}
		}
		
		
		Data.save(DataPath.의료용품);
		
	}

	private void order(Item item) {
		
		Calendar c = Calendar.getInstance();
		
		if(item.getAmount() < item.getAuto()) {
			
			Order order = new Order(findOrderSeq()
							, item.getSeq()
							, item.getPrice()
							, item.getAuto()
							, c.get(Calendar.YEAR) + "-0" + (c.get(Calendar.MONTH)+ 1) + "-" + c.get(Calendar.DATE));
			
//			1,의료용품번호,단가,자동주문수량,2022-04-01
			
			Data.ordlist.add(order);
			item.setAmount(item.getAmount() + item.getAuto());
			Data.save(DataPath.주문비용);
			
		}
	}

	private String findOrderSeq() {
		
		int max = 0;
		
		for(Order o : Data.ordlist) {
			if(Integer.parseInt(o.getSeq()) > max) { 
				max = Integer.parseInt(o.getSeq());
			}
		}
		
		return "" + (max + 1);
	}

	private String findMaxSeq_dglist() {
		
		int max = 0;
		
		for(DiagnosisInfo d : Data.dglist) {
			if(Integer.parseInt(d.getSeq()) > max) { 
				max = Integer.parseInt(d.getSeq());
			}
		}
		
		return "" + (max + 1);
		
	}
	
	private String findMaxSeq_ddlist() { 
		
		int max = 0;
		
		for(DiagnosisDocument d : Data.ddlist) {
			if(Integer.parseInt(d.getSeq()) > max) { 
				max = Integer.parseInt(d.getSeq());
			}
		}
		
		return "" + (max + 1);
		
	}
	
	private Appointment findRecentDiagnosis() {
		
		Calendar now = Calendar.getInstance();
		

		
		ArrayList<Appointment> temp = new ArrayList<Appointment>();
		
		for (Appointment a : Data.alist) {
			
			if(thePatient.getSeq().equals(a.getPatientNum())) {
				if(a.getDateTime().compareTo(now) < 0) {
					temp.add(a);
				}
			}
		}
		

		Data.alist.sort((a1, a2) -> {
			return a1.getDateTime().compareTo(a2.getDateTime());
		}); 
		
		for(int i=temp.size()-1; i>=0; i--) {
			
			for(DiagnosisInfo d : Data.dglist) {
				
				//System.out.println("@@@"+ d.getAppointmentNum());
				//System.out.println("@@@"+ temp.get(i).getSeq());
				if(temp.get(i).getSeq().equals(d.getAppointmentNum())) {
					temp.remove(i);
					break;
				} 
			}
		}

		//out.println("@"+ temp.size());
		if(temp.size() == 0) {
			return null;
		} else {
			//System.out.println("@"+ temp.get(temp.size()-1));
			return temp.get(temp.size()-1);
		}
	
	}

	private void listTreatmemt() {
	
		for(int i=0; i<Data.tlist.size(); i++) {
			System.out.printf("%d. %s\n", i+1, Data.tlist.get(i).getTreamentName());
		}
	
	}

}

/*
	1. 환자정보 출력하기
		1.1 이름, 생년월일, 주소
		1.2 전화번호
 	2. 가장 최근 내원날짜 불러오기
 		2.1 환자번호 > 예약날짜 중 현재시간 이전의 가장 최근 날짜의 예약 정보를 불러오기
 		2.2 그 날짜의 예약번호가 진료정보에 있는지 확인하기
 		2.3 있으면 "작성할 진료 내역이 없습니다."
 		2.4 없으면
 			- 진료번호 max+1 저장
 			- 환자번호, 의사번호, 내원날짜(최근날짜), 진료시간, 예약번호를 예약정보에서 불러와서 저장
 	3. 증상번호 입력받기 >
 			- 증상 목록 출력하기
 			- 증상 번호 입력받기
 			- 증상 번호 저장
 	4. 시술내용 입력받기
 			- 시술내용(시술명) 시술파일 이용하여 출력
 			- 번호 입력받기
 			- 시술 번호 불러와서 저장  > 진료정보 ArrayList에 업데이트
 	5. 의사 소견 입력받기
 			- 진단서 번호 max +1
 			- 환자번호, 진료번호 위에서 불러오기
 			- 입력받아서 저장
 	6. 저장하기

*/