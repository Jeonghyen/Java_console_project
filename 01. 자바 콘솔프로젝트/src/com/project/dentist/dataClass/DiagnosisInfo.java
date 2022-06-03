package com.project.dentist.dataClass;

import java.util.Calendar;
import com.project.dentist.Data;

/** 
 * 진료 정보를 저장하는 클래스입니다.
 * @author 고민지
 *
 */
public class DiagnosisInfo {
	
	private String seq;
	private String treatmentNum; //시술번호
	private String appointmentNum;
	private String patientNum;
	private String doctorNum;
	private String date;
	private String time;
	private String classficationNum;
	
	
	/**
	 * 의사가 입력하는 진료 정보로 초기화하는 생성자입니다.
	 * @param seq 진료 번호
	 * @param treatmentNum 시술 번호
	 * @param appointment 예약 정보 객체
	 */
	public DiagnosisInfo(String seq, String treatmentNum, Appointment appointment) { //진단서 작성시 사용
		
		this.seq = seq;
		this.treatmentNum = treatmentNum;
		this.appointmentNum = appointment.getSeq();
		this.patientNum = appointment.getPatientNum();
		this.doctorNum = appointment.getDoctorNum();
		this.date = appointment.getDate();
		String time = appointment.getTime().split(":")[0] + ":" + String.format("%02d",(int)(Math.random() * 60));
		this.time = time;

		this.classficationNum = findDiagnosisSeq(treatmentNum);
	}
	
	/**
	 * 데이터 파일의 진료 정보로 초기화하는 생성자입니다.
	 * @param seq 진료 번호
	 * @param treatmentNum 시술 번호
	 * @param appointmentNum 예약 번호
	 * @param patientNum 환자 번호
	 * @param doctorNum 의사 번호
	 * @param date 날짜
	 * @param time 시간
	 * @param classficationNum 진료 구분 번호
	 */
	public DiagnosisInfo(String seq, String treatmentNum, String appointmentNum, String patientNum,
			String doctorNum, String date, String time, String classficationNum) {

		this.seq = seq;
		this.treatmentNum = treatmentNum;
		this.appointmentNum = appointmentNum;
		this.patientNum = patientNum;
		this.doctorNum = doctorNum;
		this.date = date;
		this.time = time;
		this.classficationNum = classficationNum;
	}
	
	private String findDiagnosisSeq(String treatmentNum) {

		for(Treatment t : Data.tlist) {
			if(t.getSeq().equals(treatmentNum)) {
				return t.getClassificationNum();
			}
		}
		return null;
	}

	public String getSeq() {
		return seq;
	}

	public String getTreatmentNum() {
		return treatmentNum;
	}

	public String getAppointmentNum() {
		return appointmentNum;
	}

	public String getPatientNum() {
		return patientNum;
	}

	public String getDoctorNum() {
		return doctorNum;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
	
	public Calendar getDateTime() {  
		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(date.split("-")[0])
				, Integer.parseInt(date.split("-")[1])
				, Integer.parseInt(date.split("-")[2])
				, Integer.parseInt(time.split(":")[0])
				, Integer.parseInt(time.split(":")[1]));
		return c;
	}

	public String getClassficationNum() {
		return classficationNum;
	}

	@Override
	public String toString() {
		return String.format(
				"DiagnosisInfo [seq=%s, treatmentNum=%s, appointmentNum=%s, patientNum=%s, doctorNum=%s, date=%s, time=%s, classficationNum=%s]",
				seq, treatmentNum, appointmentNum, patientNum, doctorNum, date, time, classficationNum);
	}

	
	

	
	
	
}
