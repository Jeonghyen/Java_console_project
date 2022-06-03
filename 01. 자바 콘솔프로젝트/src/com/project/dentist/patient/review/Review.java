package com.project.dentist.patient.review;

/**
 * 환자들의 후기를 저장할 리뷰 클래스입니다.
 * @author 김승연
 *
 */
public class Review {
	
   //1,N06iPAKB5,*****,정말 친절하시고 치료도 잘하세요 최고!
   
   private String seq;
   private String id;
   private String star;
   private String comment;
   
   
   /**
    * 리뷰 객체 생성자입니다.
    * @param seq 후기 번호
    * @param id 작성자 아이디
    * @param star 별점
    * @param comment 후기 내용
    */
   public Review(String seq, String id, String star, String comment) {
	   super();
	   this.seq = seq;
	   this.id = id;
	   this.star = star;
	   this.comment = comment;
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
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return String.format("Review [seq=%s, id=%s, star=%s, comment=%s]", seq, id, star, comment);
	}
	   
}
