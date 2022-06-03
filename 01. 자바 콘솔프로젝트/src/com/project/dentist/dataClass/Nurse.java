package com.project.dentist.dataClass;
/**
 * 간호사 정보를 저장하는 클래스입니다.
 * @author 정혜인
 */
public class Nurse {
	
	private String seq;
	private String name;
	private String gender;
	private String birth;
	private String tel;
	private String entry;
	/**
	 * Nurse 객체가 만들어지면 Patient 멤버 변수들을 초기화하는 생성자입니다.
	 * @param seq 간호사 번호
	 * @param name 간호사 이름
	 * @param gender 간호사 성별
	 * @param birth 간호사 생년월일
	 * @param tel 간호사 전화번호
	 * @param entry 간호사 입사일
	 */
	public Nurse(String seq, String name, String gender, String birth, String tel, String entry) {
		this.seq = seq;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.tel = tel;
		this.entry = entry;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	@Override
	public String toString() {
		return String.format("Nurse [seq=%s, name=%s, gender=%s, birth=%s, tel=%s, entry=%s]", seq,
				name, gender, birth, tel, entry);
	}


}
