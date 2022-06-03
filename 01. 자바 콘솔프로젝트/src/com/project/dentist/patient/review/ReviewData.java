package com.project.dentist.patient.review;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.project.dentist.DataPath;

/**
 * ArrayList에 저장된 Review 객체를 데이터 파일(txt)에 읽고 쓰기 위한 클래스입니다.
 * @author 김승연
 *
 */
public class ReviewData {

	public static ArrayList<Review> rlist = new ArrayList<Review>();

	/**
	 * 데이터 파일의 의사별 진료 후기를 읽어와 ArrayList<Review> rlist에 넣어주는 메소드입니다.
	 * @param drNum 의사번호
	 */
	   public static void load(int drNum) {
	      
	      
	      try {
	         
	    	BufferedReader reader = new BufferedReader(new FileReader(DataPath.진료후기 + (drNum + 1) + ".txt"));

	        String line = null;

	        while ((line = reader.readLine()) != null) {
	        	//1,N06iPAKB5,*****,정말 친절하시고 치료도 잘하세요 최고!
	            //후기번호,아이디,별점,한줄후기
	            String[] temp = line.split(",");
	            
	            Review r = new Review(temp[0],temp[1],temp[2],temp[3]);
	            rlist.add(r);
	            
	         }
	        

	         reader.close();
	         
	         
	      } catch (Exception e) {
	         System.out.println("Data.load");
	         e.printStackTrace();
	      }
	   }
	   
	   /**
	    * ArrayList<Review> rlist에 들어 있는 진료 후기를 데이터 파일에 저장하는 메소드입니다.
	    * @param drNum 의사번호
	    */
	   public static void save(int drNum) {
		   	   
		try {
		
			BufferedWriter writer = new BufferedWriter(new FileWriter(DataPath.진료후기 + (drNum + 1) + ".txt"));

		   for (Review r : rlist) {
				
				//1,N06iPAKB5,*****,정말 친절하시고 치료도 잘하세요 최고!
	            //후기번호,아이디,별점,한줄후기
			   
				String line = String.format("%s,%s,%s,%s\n"
										, r.getSeq()
										, r.getId()
										, r.getStar()
										, r.getComment());
								
				writer.write(line);
				
			}
			   
		   writer.close();
			   
		} catch (Exception e) {
			System.out.println("ReviewData.save");
			e.printStackTrace();
		}
	  }
}