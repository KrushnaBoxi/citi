package com.citi.ms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.citi.ms.model.ClientReport;
import com.citi.ms.model.CustomerRequest;
import com.citi.ms.model.CustomerResponse;
import com.citi.ms.service.MsClientReportService;
import com.citi.ms.utils.ReportConstants;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@RestController @Log4j2
@RequestMapping(ReportConstants.CITI_URL)
public class CitiReportController {
	
	@Autowired
	private MsClientReportService msClientReportService;
	
	@RequestMapping(value = ReportConstants.GET_REPORT_REQUEST_HANDLE_URL, method = RequestMethod.POST, produces = { "application/json" })
	public CustomerResponse getClientReportDetailsHandler(@RequestBody CustomerRequest customerRequest ){
		log.debug("Get client Data: " + customerRequest.toString());
		//msClientReportService.getClientReport(customerRequest);
		//return msClientReportService.getClientReport(customerRequest);
		return msClientReportService.getDynamicClientReport(customerRequest);
	}
	
	@RequestMapping(value = ReportConstants.SET_REPORT_REQUEST_HANDLE_URL, method = RequestMethod.POST, produces = { "application/json" })
	public CustomerResponse setClientReportsDetailsHandler(@RequestBody ClientReport clientReport){
		//log.debug("Save client request details "+ clientReport);

		return msClientReportService.saveClientReport(clientReport);
		//return msClientReportService.getClientReport();
	}

	@RequestMapping(value = ReportConstants.GET_PROGRAM_HANDLE_URL, method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<List<String>> getProgramList(@RequestParam String fieldName) {
		List<String> responseMessage = new ArrayList<String>();
		System.out.println("field name : "+fieldName);
		if (fieldName.isEmpty()) {
			responseMessage.add("Field Missing");
			return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
		} else {
			responseMessage = msClientReportService.getProgramList(fieldName);
		}
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}


}
