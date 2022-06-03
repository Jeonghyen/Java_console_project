package com.project.dentist.dataClass;

/**
 * 
 * 주문비용 정보를 저장하는 클래스입니다.
 * @author 백재민
 *
 */
public class Order {
	private String seq;
	private String itemseq;
	private String itemprice;
	private int itemauto;
	private String date;
	
	
	/**
	 * 주문비용 정보를 초기화하는 생성자입니다.
	 * @param seq 주문번호
	 * @param itemseq 용품번호
	 * @param itemprice 단가
	 * @param itemauto 자동주문수량
	 * @param date 날짜
	 */
	public Order(String seq, String itemseq, String itemprice, int itemauto, String date) {
		this.seq = seq;
		this.itemseq = itemseq;
		this.itemprice = itemprice;
		this.itemauto = itemauto;
		this.date = date;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getItemseq() {
		return itemseq;
	}

	public void setItemseq(String itemseq) {
		this.itemseq = itemseq;
	}

	public String getItemprice() {
		return itemprice;
	}

	public void setItemprice(String itemprice) {
		this.itemprice = itemprice;
	}

	public int getItemauto() {
		return itemauto;
	}

	public void setItemauto(int itemauto) {
		this.itemauto = itemauto;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("Order [seq=%s, itemseq=%s, itemprice=%s, itemauto=%s, date=%s]", seq,
				itemseq, itemprice, itemauto, date);
	}
	
}
