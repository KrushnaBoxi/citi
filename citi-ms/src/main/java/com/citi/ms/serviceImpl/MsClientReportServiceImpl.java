package com.citi.ms.serviceImpl;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ch.qos.logback.core.net.server.Client;
import com.citi.ms.dao.ClientReportDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.ms.dao.ClientReportDAO;
import com.citi.ms.model.ClientReport;
import com.citi.ms.model.CustomerRequest;
import com.citi.ms.model.CustomerResponse;
import com.citi.ms.model.ServerCustomerRequest;
import com.citi.ms.service.MsClientReportService;
import com.citi.ms.utils.CitiCommonMethods;

import lombok.extern.log4j.Log4j2;
@Service @Log4j2
public class MsClientReportServiceImpl implements MsClientReportService {

	@Autowired
	private ClientReportDAO clientReportDao;
	@Autowired
	private CitiCommonMethods citiCommonMethods;
	@Autowired
	private com.citi.ms.dao.ClientReportDaoImpl clientReportDaoImpl;

	@Override
	public CustomerResponse saveClientReport(ClientReport clientReport) {
		if(clientReport == null){
		clientReport = citiCommonMethods.getClientRequestDummy();
		}
		log.debug("client details: "+clientReport);
		List<ClientReport> clientReportList = new ArrayList<ClientReport>();
		ClientReport clientReport1 = clientReportDao.save(clientReport);
		clientReportList.add(clientReport1);
		return citiCommonMethods.getSuccessResponse(clientReportList);
	}
	
	public CustomerResponse getClientReport(CustomerRequest customerRequest){
		String dbMethodName = new String();
		ServerCustomerRequest scr = new ServerCustomerRequest();
		java.sql.Date fromDate = citiCommonMethods.getStringToDate(customerRequest.getFromDate());
		java.sql.Date toDate = citiCommonMethods.getStringToDate(customerRequest.getToDate());
		scr.setFromDate(fromDate);
		scr.setToDate(toDate);
		scr.setProgram(customerRequest.getProgramType());
		scr.setInvestorName(customerRequest.getInvestorName());
		//scr.setToDate(toDate);
		
		TreeMap<String, Object> queryRequestMap = getQueryMap(scr);
		if(customerRequest == null || customerRequest.getFromDate()== null || customerRequest.getToDate() == null ){
			return citiCommonMethods.getErrorResponse("Input parameter missing");
		}else{
			dbMethodName = validateClientRequest(customerRequest);
		}
		List<ClientReport> clientReportList = null;
		//log.debug("db method name: "+dbMethodName);
		/*switch (dbMethodName) {
        case "findByToDateAndFromDate":
        	//clientReportList = clientReportDao.findByToDateBeforeAndFromDateAfter(customerRequest.getToDate(), customerRequest.getFromDate());
        	clientReportList = clientReportDao.findByToDateLessThanEqualAndFromDateGreaterThanEqual(customerRequest.getToDate(), customerRequest.getFromDate());
        	break;
        case "findByToDateAndFromDateAndProgram":
        	clientReportList = clientReportDao.findByToDateLessThanEqualAndFromDateGreaterThanEqualAndProgram(customerRequest.getToDate(), customerRequest.getFromDate(), customerRequest.getProgramType());
            break;
        case "findByToDateAndFromDateAndInvestorName":
        	clientReportList = clientReportDao.findByToDateLessThanEqualAndFromDateGreaterThanEqualAndInvestorName(customerRequest.getToDate(), customerRequest.getFromDate(), customerRequest.getInvestorName());
            break;
        case "findAll":
        	clientReportList = clientReportDao.findByToDateLessThanEqualAndFromDateGreaterThanEqualAndInvestorNameAndProgram(customerRequest.getToDate(), customerRequest.getFromDate(), customerRequest.getInvestorName(), customerRequest.getProgramType());
            break;
        default:
        	clientReportList = clientReportDao.findAll();
            break;
        }*/
		return  citiCommonMethods.getSuccessResponse(clientReportList);
	}
	@Override
	public List<String> getProgramList(String fieldName) {
		/*CustomerRequest cs = new CustomerRequest();
		cs.setProgramType("abc");
		cs.setMaxTenor(10);*/
		//System.out.println("query output: "+clientReportDaoImpl.executeDynamicQuery(getQueryMap(cs)).size());
		 final String collectionName = "clientReport";
		return clientReportDaoImpl.findByDistinctFieldValue(collectionName, fieldName);
		// distinctProgram.stream().map(ClientReport::getProgram).distinct().collect(Collectors.toList());
	}

	private TreeMap<String, Object> getQueryMap(Object customerRequest) {
		TreeMap<String, Object> queryRequestMap = new TreeMap<String, Object>();
		Field[] classfieldsName = customerRequest.getClass().getDeclaredFields();
		for (Field field : classfieldsName) {
			field.setAccessible(true);
			try {
				if(field.getType().equals(int.class) && (int) field.get(customerRequest) != 0 ) {
					queryRequestMap.put(field.getName(), field.get(customerRequest));
				} else if(field.get(customerRequest)!=null && field.get(customerRequest)!="" && !field.getType().equals(int.class) ) {
					//System.out.println(field.getType());
					queryRequestMap.put(field.getName(), field.get(customerRequest));
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		
/*		for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(customerRequest.getClass())
				.getPropertyDescriptors()) {

			for (Field field : classfieldsName) {
				field.setAccessible(true);
				System.out.println("key : " + field.getName() + "value" + propertyDescriptor.getValue(field.getName())
						+ field.get(customerRequest));

				if (propertyDescriptor.getValue(field.getName()) != null) {
					
					 * System.out.println( "key : " + field.getName() + "value" +
					 * propertyDescriptor.getValue(field.getName()));
					 queryRequestMap.put(field.getName(), (String) propertyDescriptor.getValue(field.getName()));
				}
			}

		}
	}*/
		log.debug("Query param" +queryRequestMap);
		return queryRequestMap;
	}
	

	private String validateClientRequest(CustomerRequest customerRequest) {
		if(customerRequest.getInvestorName()== null && customerRequest.getProgramType()== null ){
			return new String("findByToDateAndFromDate");
		} else if(customerRequest.getInvestorName()== null){
			return new String("findByToDateAndFromDateAndProgram");
		}else if(customerRequest.getProgramType() == null){
			return new String("findByToDateAndFromDateAndInvestorName");
		}else{
			return new String("findAll");
		}
		
	}

	@Override
	public CustomerResponse getDynamicClientReport(CustomerRequest customerRequest) {
		
		if(customerRequest == null || customerRequest.getFromDate()== null || customerRequest.getToDate() == null ){
			return citiCommonMethods.getErrorResponse("Mandatory input parameter missing");
		}
		ServerCustomerRequest scr = new ServerCustomerRequest();
		java.sql.Date fromDate = citiCommonMethods.getStringToDate(customerRequest.getFromDate());
		java.sql.Date toDate = citiCommonMethods.getStringToDate(customerRequest.getToDate());
		scr.setFromDate(fromDate);
		scr.setToDate(toDate);
		scr.setProgram(customerRequest.getProgramType());
		scr.setInvestorName(customerRequest.getInvestorName());
		scr.setSupplierExcluded(customerRequest.getSupplierExcluded());
		TreeMap<String, Object> queryRequestMap = getQueryMap(scr);
		List<ClientReport> clientReportList = null;
		clientReportList = clientReportDaoImpl.executeDynamicQuery(queryRequestMap);
		return  citiCommonMethods.getSuccessResponse(clientReportList);
	}

}
