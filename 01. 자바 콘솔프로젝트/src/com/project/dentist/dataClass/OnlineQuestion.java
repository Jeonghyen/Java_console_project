package com.project.dentist.dataClass;
//온라인상담 질문
/**
 * 온라인 상담 질문 목록을 저장하는 클래스입니다.
 * @author 김시현
 *
 */
public class OnlineQuestion {



	// 1,p1234,2022-03-01 22:30:55,충치,차가운 것을 먹으면 이가 시려요,차가운 물이나 아이스크림을 먹으면 어금니가 아파요
	// 번호,아이디,날짜,시간,카테고리,제목,내용

	String seq;
	String id;
	String date;
	String category;
	String title;
	String content;


	/**
	 * 상담 질문 목록을 초기화하는 생성자입니다.
	 * @param seq 번호
	 * @param id 아이디
	 * @param date 날짜
	 * @param category 카테고리
	 * @param title 제목
	 * @param content 내용
	 */
	public OnlineQuestion(String seq, String id, String date, String category, String title,
			String content) {
		super();
		this.seq = seq;
		this.id = id;
		this.date = date;
		this.category = category;
		this.title = title;
		this.content = content;
	}


	@Override
	public String toString() {
		return String.format(
				"onlineQuestion [seq=%s, id=%s, date=%s, category=%s, title=%s, content=%s]", seq,
				id, date, category, title, content);
	}


	public String getSeq() {
		return seq;
	}


	public void setSeq(String seq) {
		this.seq = seq;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}



	public String getCategory() {

		// 1. 충치, 2. 잇몸, 3. 교정, 4. 발치, 5. 미백, 6. 기타)



		if (this.category.equals("1")) {

			category = "충치";

		} else if (this.category.equals("2")) {

			category = "잇몸";


		} else if (this.category.equals("3")) {

			category = "교정";


		} else if (this.category.equals("4")) {

			category = "발치";


		} else if (this.category.equals("5")) {

			category = "미백";


		} else if (this.category.equals("6")) {

			category = "기타";


		}


		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}



}
