package com.project.dentist.admin.adminEmployee;

import com.project.dentist.Data;
import com.project.dentist.dataClass.Doctor;
import com.project.dentist.dataClass.Nurse;

/**
 * 직원의 정보를 보여줍니다.
 * @author 백재민
 *
 */
public class List {
	
	public void list() {
		System.out.println();
		System.out.println("===============================[의사]===============================");
		System.out.println();
		System.out.println("[번호]\t[이름]\t[성별]    [생년월일]\t  [전화번호]\t   [입사일]");
		
		for (Doctor d : Data.dlist) {
			System.out.printf("%3s\t%s\t %s\t%s\t%s\t %s\n"
					, d.getSeq()
					, d.getName()
					, d.getGender().equals("1") ? "남자" : "여자"
					, d.getBirth()
					, d.getTel()
					, d.getEntry());
		}
		
		System.out.println();
		System.out.println("===============================[간호사]===============================");
		System.out.println();
		System.out.println("[번호]\t[이름]\t[성별]    [생년월일]\t  [전화번호]\t   [입사일]");
		
		for (Nurse n : Data.nlist) {
			System.out.printf("%3s\t%s\t %s\t%s\t%s\t %s\n"
					, n.getSeq()
					, n.getName()
					, n.getGender().equals("1") ? "남자" : "여자"
					, n.getBirth()
					, n.getTel()
					, n.getEntry());
		}
		System.out.println();
	}
	
}
