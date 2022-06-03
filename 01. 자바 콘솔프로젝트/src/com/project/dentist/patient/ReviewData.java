package com.project.dentist.patient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.project.dentist.DataPath;

public class ReviewData {

	public static ArrayList<Review> rlist = new ArrayList<Review>();
	  
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