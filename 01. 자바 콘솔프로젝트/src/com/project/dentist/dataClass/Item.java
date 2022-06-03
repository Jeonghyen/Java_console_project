package com.project.dentist.dataClass;
//의료용품

/**
 * 
 * 의료용품 정보를 저장하는 클래스입니다.
 * @author 백재민
 *
 */
public class Item {
	
	private String seq;
	private String iname;
	private String price;
	private int amount;
	private int auto;
	
	/**
	 * 의료용품 정보를 초기화하는 생성자입니다.
	 * @param seq 용품번호
	 * @param iname 의료용품명
	 * @param price 단가
	 * @param amount 잔여수량
	 * @param auto 자동주문수량
	 */
	public Item(String seq, String iname, String price, int amount, int auto) {
		this.seq = seq;
		this.iname = iname;
		this.price = price;
		this.amount = amount;
		this.auto = auto;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getAuto() {
		return auto;
	}

	public void setAuto(int auto) {
		this.auto = auto;
	}

	@Override
	public String toString() {
		return String.format("Item [seq=%s, iname=%s, price=%s, amount=%s, auto=%s]", seq, iname,
				price, amount, auto);
	}

}
