package com.project.dentist.dataClass;
//의사
/**
 * 의사 정보를 저장하는 클래스입니다.
 * @author 정혜인
 *
 */
public class Doctor {
	
	private String seq;
	private String name;
	private String gender;
	private String birth;
	private String tel;
	private String entry;
	private String path;
	
	/**
	 * 의사 정보를 초기화하는 생성자입니다.
	 * @param seq 번호
	 * @param name 이름
	 * @param gender 성별
	 * @param birth 생년월일
	 * @param tel 전화번호
	 * @param entry 입사일
	 * @param path 자기소개서경로
	 */
	public Doctor(String seq, String name, String gender, String birth, String tel, String entry,
			String path) {
		this.seq = seq;
		this.name = name;
		this.gender = gender;
		this.birth = birth;
		this.tel = tel;
		this.entry = entry;
		this.path = path;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return String.format(
				"Dentist [seq=%s, name=%s, gender=%s, birth=%s, tel=%s, entry=%s, path=%s]", seq,
				name, gender, birth, tel, entry, path);
	}
	
}
