package com.project.dentist.dataClass;

/**
 * 예약 대기 정보를 저장하는 클래스입니다.
 * @author 고민지
 *
 */
public class WaitingList {

	private String seq;
	private String appointmentSeq;
	private String patientSeq1;
	private String patientSeq2;
	private String patientSeq3;
	
	
	/**
	 * 새로운 예약 대기목록을 생성하는 과정에서 예약 정보를 초기화하는 생성자입니다.
	 * @param seq 대기 번호
	 * @param appointmentSeq 예약 번호
	 * @param patientSeq 환자 번호
	 */
	public WaitingList(String seq, String appointmentSeq, String patientSeq) { //대기하기용
		this.seq = seq;
		this.appointmentSeq = appointmentSeq;
		this.patientSeq1 = patientSeq;
		
	}
	
	/**
	 * 예약 대기 정보를 초기화하는 생성자입니다.
	 * @param seq 대기 번호
	 * @param appointmentSeq 예약 번호
	 * @param patientSeq1 첫번째 예약 대기 환자 번호
	 * @param patientSeq2 두번째 예약 대기 환자 번호
	 * @param patientSeq3 세번째 예약 대기 환자 번호
	 */
	public WaitingList(String seq, String appointmentSeq, String patientSeq1, String patientSeq2,
			String patientSeq3) {
		this.seq = seq;
		this.appointmentSeq = appointmentSeq;
		this.patientSeq1 = patientSeq1;
		this.patientSeq2 = patientSeq2;
		this.patientSeq3 = patientSeq3;
	}
	

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getAppointmentSeq() {
		return appointmentSeq;
	}

	public void setAppointmentSeq(String appointmentSeq) {
		this.appointmentSeq = appointmentSeq;
	}

	public String getPatientSeq1() {
		return patientSeq1;
	}

	public void setPatientSeq1(String patientSeq1) {
		this.patientSeq1 = patientSeq1;
	}

	public String getPatientSeq2() {
		return patientSeq2;
	}

	public void setPatientSeq2(String patientSeq2) {
		this.patientSeq2 = patientSeq2;
	}

	public String getPatientSeq3() {
		return patientSeq3;
	}

	public void setPatientSeq3(String patientSeq3) {
		this.patientSeq3 = patientSeq3;
	}

	public String[] getAllPatientSeq() {
		return new String[] { patientSeq1, patientSeq2, patientSeq3 };
	}
	
	/**
	 * 예약 대기 환자 인원을 확인하는 메소드입니다. 
	 * @return
	 */
	public int waitingSize() {
		int count = 0; 
		if(patientSeq1.equals("null")) { 
			count++;
		} 
		if(patientSeq2.equals("null")) {
			count++;
		} 
		if(patientSeq3.equals("null")) {
			count++;
		} 
		return count;
	}
	
	/**
	 * 예약 대기가 가능한지 여부를 확인하는 메소드입니다.
	 * @return 예약 대기 가능 여부
	 */
	public boolean canWait() { 
		if(!patientSeq1.equals("null") && !patientSeq2.equals("null") && !patientSeq3.equals("null")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 특정 예약 번호에 예약 대기 환자를 추가하는 메소드입니다.
	 * @param patientSeq 환자 번호
	 * @return 예약 대기가 완료되었는지 여부
	 */
	public boolean addPatient(String patientSeq) {
		
		if(patientSeq2.equals("null")) {
			patientSeq2 = patientSeq;
			return true;
		} else if(patientSeq3.equals("null")) { 
			patientSeq3 = patientSeq;
			return true;
		} 
		
		return false;
	}
	

	@Override
	public String toString() {
		return String.format(
				"WaitingList [seq=%s, appointmentSeq=%s, patientSeq1=%s, patientSeq2=%s, patientSeq3=%s]",
				seq, appointmentSeq, patientSeq1, patientSeq2, patientSeq3);
	}


	
	
	
}
