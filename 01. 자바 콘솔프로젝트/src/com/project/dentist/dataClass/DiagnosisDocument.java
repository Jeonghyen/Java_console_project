package com.project.dentist.dataClass;

/**
 * 진단서 정보를 저장하는 클래스입니다.
 * @author 고민지
 *
 */
public class DiagnosisDocument {
	
	private String seq;
	private String patientNum;
	private String diagnosisNum;
	private String opinion;
	
	/**
	 * 진단서 정보를 초기화하는 생성자입니다.
	 * @param seq 진단서 번호
	 * @param patientNum 환자 번호
	 * @param diagnosisNum 진료 구분 번호
	 * @param opinion 의사 소견
	 */
	public DiagnosisDocument(String seq, String patientNum, String diagnosisNum, String opinion) {
		this.seq = seq;
		this.patientNum = patientNum;
		this.diagnosisNum = diagnosisNum;
		this.opinion = opinion;
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getPatientNum() {
		return patientNum;
	}
	public void setPatientNum(String patientNum) {
		this.patientNum = patientNum;
	}
	public String getDiagnosisNum() {
		return diagnosisNum;
	}
	public void setDiagnosisNum(String diagnosisNum) {
		this.diagnosisNum = diagnosisNum;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	@Override
	public String toString() {
		return String.format(
				"DiagnosisDocument [seq=%s, patientNum=%s, diagnosisNum=%s, opinion=%s]", seq,
				patientNum, diagnosisNum, opinion);
	}
	
	
	
}
