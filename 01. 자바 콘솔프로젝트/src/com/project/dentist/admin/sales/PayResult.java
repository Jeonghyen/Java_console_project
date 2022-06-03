package com.project.dentist.admin.sales;

import java.util.Calendar;
import com.project.dentist.Data;
import com.project.dentist.dataClass.Doctor;
import com.project.dentist.dataClass.Nurse;

/**
 * 직원의 연차별 급여를 계산합니다.
 * @author 백재민
 *
 */
public class PayResult {
	
	/**
	 * 직원별 연차 급여를 계산합니다.
	 * @param epay 급여
	 * @return 해당 연차의 급여를 반환합니다.
	 */
	public static int empPay(int epay) {

		try {

			for (Nurse n : Data.nlist) {
				Calendar calendar = Calendar.getInstance();

				String[] temp = n.getEntry().split("-");

				int year = Integer.parseInt(temp[0]);
				int month = Integer.parseInt(temp[1]);
				int day = Integer.parseInt(temp[2]);

				long u = calendar.getTimeInMillis();


				calendar.set(year, month, day);
				long ox = (u - calendar.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;

				switch ((int)ox) {
					case 0 : 
						epay += 1_800_000;
						break;
					case 1 : case 2 :
						epay += 2_110_000;
						break;
					case 3 : case 4 :
						epay += 2_220_000;
						break;
					case 5 : case 6 : case 7 : case 8 : case 9 :
						epay += 2_380_000;
						break;
					case 10 : case 11 : case 12 : case 13 : case 14 :
						epay +=  2_500_000;
						break;
					case 15 : case 16 : case 17 : case 18 : case 19 :
						epay += 2_700_000;
						break;
					case 20 : case 21 : case 22 : case 23 : case 24 :
						epay += 3_000_000;
						break;
					default :
						epay += 3_500_000;
						break;

				}

			}

			for (Doctor d : Data.dlist) {
				Calendar calendar = Calendar.getInstance();

				String[] temp = d.getEntry().split("-");

				int year = Integer.parseInt(temp[0]);
				int month = Integer.parseInt(temp[1]);
				int day = Integer.parseInt(temp[2]);

				long u = calendar.getTimeInMillis();


				calendar.set(year, month, day);
				long ox = (u - calendar.getTimeInMillis()) / 1000 / 60 / 60 / 24 / 365;

				switch ((int)ox) {
					case 0 : 
						epay += 2_200_000;
						break;
					case 1 : case 2 :
						epay += 2_410_000;
						break;
					case 3 : case 4 :
						epay += 2_620_000;
						break;
					case 5 : case 6 : case 7 : case 8 : case 9 :
						epay += 2_880_000;
						break;
					case 10 : case 11 : case 12 : case 13 : case 14 :
						epay +=  3_000_000;
						break;
					case 15 : case 16 : case 17 : case 18 : case 19 :
						epay += 3_300_000;
						break;
					case 20 : case 21 : case 22 : case 23 : case 24 :
						epay += 3_700_000;
						break;
					default :
						epay += 4_000_000;
						break;

				}

			}

		} catch (Exception e) {
			System.out.println("MonthSale.empPay");
			e.printStackTrace();
		}

		return epay;

	}

}
