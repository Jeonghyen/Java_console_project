package com.project.dentist.dataClass;

/**
 * 진료목록을 저장하는 클래스입니다.
 * 
 * @author 정혜인
 *
 */

public class Diagnosis {



	// 1,치아교정

	private String seq;
	private String diagnosis_name;

	/**
	 * 
	 * @param seq 진료번호
	 * @param diagnosis_name 진료이름
	 */
	public Diagnosis(String seq, String diagnosis_name) {
		super();
		this.seq = seq;
		this.diagnosis_name = diagnosis_name;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getDiagnosis_name() {
		return diagnosis_name;
	}

	public void setDiagnosis_name(String diagnosis_name) {
		this.diagnosis_name = diagnosis_name;
	}

	@Override
	public String toString() {
		return String.format("DiagnosisDivision [seq=%s, diagnosis_name=%s]", seq, diagnosis_name);
	}



}
