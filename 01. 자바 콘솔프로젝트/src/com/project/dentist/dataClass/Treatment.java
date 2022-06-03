package com.project.dentist.dataClass;

/**
 * 시술 정보를 저장하는 클래스입니다.
 * @author 고민지
 *
 */
public class Treatment { //시술클래스

	private String seq;
	private String classificationNum;  //진료구분번호 == 증상번호
	private String treamentName;
	private String[] itemNums;
	
	/**
	 * 시술 정보를 초기화하는 생성자입니다.
	 * @param seq 시술 번호
	 * @param classificationNum 진료 구분 번호
	 * @param treamentName 시술명
	 * @param itemNum1 필요한 의료 용품1 번호
	 * @param itemNum2 필요한 의료 용품2 번호
	 * @param itemNum3 필요한 의료 용품3 번호
	 */
	public Treatment(String seq, String classificationNum, String treamentName, String itemNum1,
			String itemNum2, String itemNum3) {
		this.seq = seq;
		this.classificationNum = classificationNum;
		this.treamentName = treamentName;
		
		String[] temp = { itemNum1, itemNum2, itemNum3 };
		itemNums = temp;
	}
	public String getSeq() {
		return seq;
	}
	public String getClassificationNum() {
		return classificationNum;
	}
	public String getTreamentName() {
		return treamentName;
	}
	public String getItemNum1() {
		return itemNums[0];
	}
	public String getItemNum2() {
		return itemNums[1];
	}
	public String getItemNum3() {
		return itemNums[2];
	}
	public String[] getItemAllNums() {
		return itemNums;
	}
	
	
	@Override
	public String toString() {
		return String.format(
				"Treatment [seq=%s, classificationNum=%s, treatmentName=%s, itemNum1=%s, itemNum2=%s, itemNum3=%s]",
				seq, classificationNum, treamentName, itemNums[0], itemNums[1], itemNums[2]);
	}
	
}
