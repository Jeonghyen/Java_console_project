package com.project.dentist.dataClass;

import java.util.Calendar;

/**
 * 예약 정보를 저장하는 클래스입니다.
 * @author 고민지
 *
 */
public class Appointment {

	private String seq;
	private String patientNum; //(F)
	private String doctorNum; //(F)

	private Calendar dateTime;  //date + time 하나로 저장***  > 초기화 시에는 따로 넣어줌
	private String classficationNum; //증상번호(F)
	
	
	/**
	 * 예약 정보를 초기화하는 생성자입니다.
	 * @param seq 예약 번호
	 * @param patientNum 환자 번호
	 * @param doctorNum 의사 번호
	 * @param date 날짜(년월일)
	 * @param time 시간
	 * @param classficationNum 진료 구분 번호
	 */
	public Appointment(String seq, String patientNum, String doctorNum, String date, String time,
			String classficationNum) {

		this.seq = seq;
		this.patientNum = patientNum;
		this.doctorNum = doctorNum;
		this.classficationNum = classficationNum;

		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(date.split("-")[0])
				, Integer.parseInt(date.split("-")[1])-1
				, Integer.parseInt(date.split("-")[2])
				, Integer.parseInt(time.split(":")[0])
				, Integer.parseInt(time.split(":")[1]));
		
		this.dateTime = c;		
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
	public String getDoctorNum() {
		return doctorNum;
	}
	public void setDoctorNum(String doctorNum) {
		this.doctorNum = doctorNum;
	}
	
	/**
	 * 예약 날짜 및 시간을 얻는 메소드입니다.
	 * @return Calendar 타입의 날짜와 시간
	 */
	public Calendar getDateTime() {
		return dateTime;
	}
	
	/**
	 * 예약 날짜를 얻는 메소드입니다.
	 * @return String 타입의 날짜
	 */
	public String getDate() {
		return String.format("%tF", dateTime);
	}
	/**
	 * 예약 시간을 얻는 메소드입니다.
	 * @return String 타입의 시간
	 */
	public String getTime() {
		return dateTime.get(Calendar.HOUR_OF_DAY) + ":" + (dateTime.get(Calendar.MINUTE) < 10 ? "0" + dateTime.get(Calendar.MINUTE) : dateTime.get(Calendar.MINUTE));
	}
	
	public void setDate(String date) { 
		String[] dates = date.split("-");
		dateTime.set(Integer.parseInt(dates[0]), 
						Integer.parseInt(dates[1]) - 1, 
						Integer.parseInt(dates[2]));
	}
	public void setTime(String time) { 
		
		String[] times = time.split(":");
		dateTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
		dateTime.set(Calendar.MINUTE, Integer.parseInt(times[1]));
		
	}
	public void setDateTime(String date, String time) {
		setDate(date);
		setTime(time);
	}
	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
	}
	public String getClassficationNum() {
		return classficationNum;
	}
	public void setClassficationNum(String classficationNum) {
		this.classficationNum = classficationNum;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Reservation [seq=%s, patientNum=%s, doctorNum=%s, dateTime=%s, classficationNum=%s]",
				seq, patientNum, doctorNum, dateTime, classficationNum);
	}
	
	
	

}
