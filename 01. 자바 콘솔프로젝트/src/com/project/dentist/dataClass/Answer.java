package com.project.dentist.dataClass;
//온라인상담 답글
/**
 * 온라인 상담 답변을 저장하는 클래스입니다.
 * @author 김시현
 *
 */
public class Answer {
	private String num;
	private String cnum;
	private String content;
	
	/**
	 * 상담 답변을 초기화하는 생성자입니다.
	 * @param num 답글번호
	 * @param cnum 글번호
	 * @param content 답변내용
	 */
	public Answer(String num, String cnum, String content) {
		this.num = num;
		this.cnum = cnum;
		this.content = content;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCnum() {
		return cnum;
	}

	public void setCnum(String cnum) {
		this.cnum = cnum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return String.format("Answer [num=%s, cnum=%s, content=%s]", num, cnum, content);
	}
	
	


}
