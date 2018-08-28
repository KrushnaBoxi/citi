package com.citi.ms.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.citi.ms.model.ClientReport;
import com.citi.ms.model.CustomerResponse;


@Component
public class CitiCommonMethods {

	public String getCurrentDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return localDateTime.toString();
	}
	
	public Date getCurrentDate() {
		//SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		//String me = formatDate.format(new Date());
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		// System.out.println("date is: "+date);
		/*String dateString = format.format(time);
		Date date = null;
		try {
			date = (Date) format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		// LocalDate localDate = LocalDate.now();
		return date;
	}

	public java.sql.Date getStringToDate(String inputDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD"); 
		java.sql.Date sqlDate = java.sql.Date.valueOf(inputDate);
		Date utilDate;
		
		
		
		/*try {
			 sqlDate = java.sql.Date.valueOf(inputDate);
			//utilDate = formatter.parse(inputDate);
			utilDate = (Date)formatter.parse(inputDate);
			System.out.println("utilDate "+utilDate);
			sqlDate = new java.sql.Date(utilDate.getTime());
			System.out.println("sqlDate "+ sqlDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy");
		String finalString = newFormat.format(date);*/
		return sqlDate;
	}

	public CustomerResponse getSuccessResponse(List<ClientReport> clientReport) {
		return CustomerResponse.builder().clientReport(clientReport).responseCode(200).responseMessage("Success").build();
	}
	

	public CustomerResponse getErrorResponse(String errorMessage) {
		return CustomerResponse.builder().responseCode(400).responseMessage(errorMessage).build();
	}
	
	public ClientReport getClientRequestDummy(){
		return ClientReport.builder().investorName("Bank of Coimbatore ").program("Coromandel Train Company").limit("USD 100 mn")
				.ccySold("EUR, USD").investorPricing("60 bps").supplierExcluded("ABC Ltd, Xyz Ltd").supplierCountiesPermitted("Europe, US")
				.obligorCodesPermitted("Ericsson AB, LM Ericsson & Ericsson GMBH").minTenor(15).maxTenor(200).minBookingMargin(5)
				.maximumNumberOfNotesAllowed(5).minimumNoteSize(100000).lastUpdated(getCurrentDate()).toDate(getCurrentDate()).fromDate(getCurrentDate()).build();
	}

	private long getTransactionId() {
		Random random = new Random(System.nanoTime() % 100000);
		long uniqueTansactionId = random.nextInt(1000000000);
		return uniqueTansactionId;
	}



	// convert object to toByteArray 
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bytes;
	}

	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object obj = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (ois != null) {
				ois.close();
			}
		}
		return obj;
	}

}
