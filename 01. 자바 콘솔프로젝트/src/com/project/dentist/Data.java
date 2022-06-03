package com.project.dentist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import com.project.dentist.dataClass.Answer;
import com.project.dentist.dataClass.Appointment;
import com.project.dentist.dataClass.Diagnosis;
import com.project.dentist.dataClass.DiagnosisDocument;
import com.project.dentist.dataClass.DiagnosisInfo;
import com.project.dentist.dataClass.Doctor;
import com.project.dentist.dataClass.DoctorPay;
import com.project.dentist.dataClass.Item;
import com.project.dentist.dataClass.Nurse;
import com.project.dentist.dataClass.NursePay;
import com.project.dentist.dataClass.OnlineQuestion;
import com.project.dentist.dataClass.Order;
import com.project.dentist.dataClass.Patient;
import com.project.dentist.dataClass.Payment;
import com.project.dentist.dataClass.Treatment;
import com.project.dentist.dataClass.WaitingList;

/**
 * 외부 데이터 파일을 불러오거나 저장하는 클래스입니다.
 * @author 고민지
 *
 */
public class Data {

	public static ArrayList<Patient> plist = new ArrayList<Patient>();
	public static ArrayList<Doctor> dlist = new ArrayList<Doctor>();
	public static ArrayList<Nurse> nlist = new ArrayList<Nurse>();

	public static ArrayList<DoctorPay> dplist = new ArrayList<DoctorPay>();
	public static ArrayList<NursePay> nplist = new ArrayList<NursePay>();

	public static ArrayList<Appointment> alist = new ArrayList<Appointment>();
	public static ArrayList<DiagnosisInfo> dglist = new ArrayList<DiagnosisInfo>();
	public static ArrayList<DiagnosisDocument> ddlist = new ArrayList<DiagnosisDocument>();
	public static ArrayList<Diagnosis> clist = new ArrayList<Diagnosis>(); //진료구분 
	public static ArrayList<Treatment> tlist = new ArrayList<Treatment>(); //시술
	public static ArrayList<WaitingList> wlist = new ArrayList<WaitingList>();
	
	
	public static ArrayList<Payment> paylist = new ArrayList<Payment>();
	public static ArrayList<Item> ilist = new ArrayList<Item>();

	public static ArrayList<OnlineQuestion> olist = new ArrayList<OnlineQuestion>();
	public static ArrayList<Answer> answerlist = new ArrayList<Answer>();
	
	public static ArrayList<Order> ordlist = new ArrayList<Order>();
	
	
	/**
	 * 공통으로 사용되는 외부 데이터 파일 전체를 불러오는 메소드입니다.
	 */
	public static void load() {

		// load할 파일 목록
		load(DataPath.회원정보);
		load(DataPath.의사);
		load(DataPath.간호사);

		load(DataPath.의사급여);
		load(DataPath.간호사_급여);

		load(DataPath.진료예약);
		load(DataPath.진료정보);
		load(DataPath.진단서);
		load(DataPath.진료구분);
		load(DataPath.시술);
		load(DataPath.예약대기);

		load(DataPath.결제);
		load(DataPath.의료용품);

		load(DataPath.온라인상담질문);
		load(DataPath.온라인상담답글);
		
		load(DataPath.주문비용);


	}
	
	/**
	 * 특정한 외부 데이터 파일을 불러오는 메소드입니다.
	 * @param path 외부 데이터 파일 경로
	 */
	public static void load(String path) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(path));

			String line = null;

			while ((line = reader.readLine()) != null) {

				String[] temp = line.split(",");

				if (DataPath.회원정보.equals(path)) {
					plist.add(환자(temp));
				} else if (DataPath.의사.equals(path)) {
					dlist.add(의사(temp));
				} else if (DataPath.간호사.equals(path)) {
					nlist.add(간호사(temp));
				} else if (DataPath.의사급여.equals(path)) {
					dplist.add(의사급여(temp));
				} else if (DataPath.간호사_급여.equals(path)) {
					nplist.add(간호사급여(temp));
				} else if (DataPath.진료정보.equals(path)) {
					dglist.add(진료정보(temp));
				} else if (DataPath.진료예약.equals(path)) {
					alist.add(진료예약(temp));
				} else if (DataPath.진단서.equals(path)) {
					ddlist.add(진단서(temp));
				} else if (DataPath.진료구분.equals(path)) {
					clist.add(진료구분(temp));
				} else if (DataPath.시술.equals(path)) {
					tlist.add(시술(temp));
				} else if (DataPath.예약대기.equals(path)) {
					wlist.add(예약대기(temp));
				} else if (DataPath.결제.equals(path)) {
					paylist.add(결제(temp));
				} else if (DataPath.의료용품.equals(path)) {
					ilist.add(의료용품(temp));
				} else if (DataPath.온라인상담질문.equals(path)) {
					olist.add(온라인상담질문(temp));
				} else if (DataPath.온라인상담답글.equals(path)) {
					answerlist.add(온라인상담답글(temp));
				}else if (DataPath.주문비용.equals(path)) {
		               ordlist.add(주문비용(temp));
	            }

			}

			reader.close();

		} catch (Exception e) {
			System.out.println("Data.load");
			e.printStackTrace();
		}
	}




	/**
	 * 외부 데이터 파일에 정보를 저장하는 메소드입니다.
	 * @param path
	 */
	public static void save(String path) {

		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			
			if (DataPath.회원정보.equals(path)) {
				save환자(writer);
			} else if (DataPath.의사.equals(path)) {
				save의사(writer);
			} else if (DataPath.간호사.equals(path)) {
				save간호사(writer);
			} else if (DataPath.의사급여.equals(path)) {
				save의사급여(writer);
			} else if (DataPath.간호사_급여.equals(path)) {
				save간호사급여(writer);
			} else if (DataPath.진료정보.equals(path)) {
				save진료정보(writer);
			} else if (DataPath.진료예약.equals(path)) {
				save진료예약(writer);
			} else if (DataPath.진단서.equals(path)) {
				save진단서(writer);
			} else if (DataPath.진료구분.equals(path)) {
				save진료구분(writer);
			} else if (DataPath.시술.equals(path)) {
				save시술(writer);
			} else if (DataPath.예약대기.equals(path)) {
				save예약대기(writer);
			} else if (DataPath.결제.equals(path)) {
				save결제(writer);
			} else if (DataPath.의료용품.equals(path)) {
				save의료용품(writer);
			} else if (DataPath.온라인상담질문.equals(path)) {
				save온라인상담질문(writer);
			} else if (DataPath.온라인상담답글.equals(path)) {
				save온라인상담답글(writer);
			}else if (DataPath.주문비용.equals(path)) {
	            save주문비용(writer);
	         }

			writer.close();

		} catch (Exception e) {
			System.out.println("Data.save");
			e.printStackTrace();
		}
	}


	private static Patient 환자(String[] temp) {
		return new Patient(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7]);
	}

	private static Doctor 의사(String[] temp) {
		return new Doctor(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6]);
	}

	private static Nurse 간호사(String[] temp) {
		return new Nurse(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
	}

	private static DoctorPay 의사급여(String[] temp) {
		return new DoctorPay(temp[0], temp[1], temp[2]);
	}

	private static NursePay 간호사급여(String[] temp) {
		return new NursePay(temp[0], temp[1], temp[2]);
	}

	private static DiagnosisInfo 진료정보(String[] temp) {
		return new DiagnosisInfo(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6],
				temp[7]);
	}

	private static Appointment 진료예약(String[] temp) {
		return new Appointment(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
	}

	private static DiagnosisDocument 진단서(String[] temp) {
		return new DiagnosisDocument(temp[0], temp[1], temp[2], temp[3]);
	}

	private static Diagnosis 진료구분(String[] temp) {
		return new Diagnosis(temp[0], temp[1]);
	}
	
	private static Treatment 시술(String[] temp) {
		return new Treatment(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
	}
	private static WaitingList 예약대기(String[] temp) {
		return new WaitingList(temp[0], temp[1], temp[2], temp[3], temp[4]);
	}
	
		private static Payment 결제(String[] temp) {
		return new Payment(temp[0], temp[1], temp[2]);
	}

	private static Item 의료용품(String[] temp) {
		return new Item(temp[0], temp[1], temp[2], Integer.parseInt(temp[3]),
				Integer.parseInt(temp[4]));
	}

	private static OnlineQuestion 온라인상담질문(String[] temp) {
		return new OnlineQuestion(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
	}

	private static Answer 온라인상담답글(String[] temp) {
		return new Answer(temp[0], temp[1], temp[2]);
	}private static Order 주문비용(String[] temp) {
	      return new Order(temp[0], temp[1], temp[2], Integer.parseInt(temp[3]), temp[4]);
	   }

	
	

	private static void save환자(BufferedWriter writer) throws Exception  {

		for(Patient p : plist) {
			String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n"
										, p.getSeq()
										, p.getId()
										, p.getPassword()
										, p.getName()
										, p.getGender()
										, p.getBirthday()
										, p.getAddress()
										, p.getPhoneNum());
					
			writer.write(line);
		}
	}

	private static void save의사(BufferedWriter writer) throws Exception {
		
		for (Doctor d : dlist) {
			
			//번호,이름,성별,생년월일,전화번호,입사일,자기소개서
			String line = String.format("%s,%s,%s,%s,%s,%s,%s\n"
					, d.getSeq()
					, d.getName()
					, d.getGender()
					, d.getBirth()
					, d.getTel()
					, d.getEntry()
					, d.getPath());
			
			writer.write(line);
		}
	}

	private static void save간호사(BufferedWriter writer) throws Exception {
		
		for (Nurse n : nlist) {
			String line = String.format("%s,%s,%s,%s,%s,%s\n"
					, n.getSeq()
					, n.getName()
					, n.getGender()
					, n.getBirth()
					, n.getTel()
					, n.getEntry());
			
			writer.write(line);
		}
	}

	private static void save의사급여(BufferedWriter writer) throws Exception {
		
		for (DoctorPay n : dplist) {
			String line = String.format("%s,%s,%s\n"
					, n.getSeq()
					, n.getYears()
					, n.getPay());
			
			writer.write(line);
		}
	}

	private static void save간호사급여(BufferedWriter writer) throws Exception  {

		for (NursePay n : nplist) {
			String line = String.format("%s,%s,%s\n"
					, n.getSeq()
					, n.getYears()
					, n.getPay());
			
			writer.write(line);
		}
	}

	private static void save진료정보(BufferedWriter writer) throws Exception  {

		for (DiagnosisInfo d : dglist) {
			String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s\n"
					, d.getSeq()
					, d.getTreatmentNum()
					, d.getAppointmentNum()
					, d.getPatientNum()
					, d.getDoctorNum()
					, d.getDate()
					, d.getTime()
					, d.getClassficationNum());
			
			writer.write(line);
		}
	}

	private static void save진료예약(BufferedWriter writer) throws Exception {
		
		for (Appointment a : alist) {
			String line = String.format("%s,%s,%s,%s,%s,%s\n"
					, a.getSeq()
					, a.getPatientNum()
					, a.getDoctorNum()
					, a.getDate()
					, a.getTime()
					, a.getClassficationNum());
			
			writer.write(line);
		}
	}

	private static void save진단서(BufferedWriter writer) throws Exception {
		
		for (DiagnosisDocument d : ddlist) {
			String line = String.format("%s,%s,%s,%s\n"
					, d.getSeq()
					, d.getPatientNum()
					, d.getDiagnosisNum()
					, d.getOpinion());
			
			writer.write(line);
		}
	}
	
	private static void save진료구분(BufferedWriter writer) throws Exception {
		
		for (Diagnosis d : clist) {
			String line = String.format("%s,%s\n"
					, d.getSeq()
					, d.getDiagnosis_name());
			
			writer.write(line);
		}
	}
	
	private static void save시술(BufferedWriter writer) throws Exception {
		
		for (Treatment d : tlist) {
			String line = String.format("%s,%s,%s,%s,%s,%s\n"
					, d.getSeq()
					, d.getClassificationNum()
					, d.getTreamentName()
					, d.getItemNum1()
					, d.getItemNum2()
					, d.getItemNum3());
			
			writer.write(line);
		}		
	}
	
	private static void save예약대기(BufferedWriter writer) throws Exception {

		for (WaitingList w : wlist) {
			String line = String.format("%s,%s,%s,%s,%s\n"
					, w.getSeq()
					, w.getAppointmentSeq()
					, w.getPatientSeq1()
					, w.getPatientSeq2()
					, w.getPatientSeq3());
			
			writer.write(line);
		}
		
	}

	private static void save결제(BufferedWriter writer) throws Exception {
		
		for (Payment p : paylist) {
			String line = String.format("%s,%s,%s\n"
					, p.getSeq()
					, p.getPay()
					, p.getSurgeSeq());
			
			writer.write(line);
		}
	}

	private static void save의료용품(BufferedWriter writer) throws Exception {

		for (Item i : ilist) {
			String line = String.format("%s,%s,%s,%d,%d\n"
					, i.getSeq()
					, i.getIname()
					, i.getPrice()
					, i.getAmount()
					, i.getAuto());
			
			writer.write(line);
		}
	}

	private static void save온라인상담질문(BufferedWriter writer) throws Exception  {
		
		for (OnlineQuestion o : olist) {
			String line = String.format("%s,%s,%s,%s,%s,%s\n"
					, o.getSeq()
					, o.getId()
					, o.getDate()
					, o.getCategory()
					, o.getTitle()
					, o.getContent());
			
			writer.write(line);
		}
	}

	private static void save온라인상담답글(BufferedWriter writer) throws Exception  {
		
		for (Answer a : answerlist) {
			String line = String.format("%s,%s,%s\n"
					, a.getNum()
					, a.getCnum()
					, a.getContent());
			
			writer.write(line);
		}
	}
	
	private static void save주문비용(BufferedWriter writer) throws Exception {

	      for (Order o : ordlist) {
	         String line = String.format("%s,%s,%s,%d,%s\n"
	               , o.getSeq()
	               , o.getItemseq()
	               , o.getItemprice()
	               , o.getItemauto()
	               , o.getDate());
	         
	         writer.write(line);
	      }
	   }


}
