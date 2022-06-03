package com.project.dentist.dataClass;
/**
 * 결제 정보를 저장하는 클래스입니다. 
 * @author 정혜인
 */
public class Payment {



	private String seq;
	private String pay;
	private String surgeSeq;
	/**
	 * Payment 객체가 만들어지면 Patient 멤버 변수들을 초기화하는 생성자입니다.
	 * @param seq 결제번호
	 * @param pay 결제금액
	 * @param surgeSeq 시술번호
	 */
	public Payment(String seq, String pay, String surgeSeq) {
		super();
		this.seq = seq;
		this.pay = pay;
		this.surgeSeq = surgeSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getSurgeSeq() {
		return surgeSeq;
	}

	public void setSurgeSeq(String surgeSeq) {
		this.surgeSeq = surgeSeq;
	}

	@Override
	public String toString() {
		return String.format("Payment [seq=%s, pay=%s, surgeSeq=%s]", seq, pay, surgeSeq);
	}



}
