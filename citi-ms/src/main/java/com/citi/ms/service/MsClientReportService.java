package com.citi.ms.service;

import java.util.List;

import com.citi.ms.model.ClientReport;
import com.citi.ms.model.CustomerRequest;
import com.citi.ms.model.CustomerResponse;

public interface MsClientReportService {
	
	public CustomerResponse saveClientReport(ClientReport clientReport);
	public CustomerResponse getClientReport(CustomerRequest customerRequest);
	public CustomerResponse getDynamicClientReport(CustomerRequest customerRequest);

   	public List<String> getProgramList(String program);
}
