package com.project.dentist.dataClass;
//진료 후기
public class Review {
	
   //1,N06iPAKB5,*****,정말 친절하시고 치료도 잘하세요 최고!
   
   private String seq;
   private String id;
   private String star;
   private String comment;
   
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
