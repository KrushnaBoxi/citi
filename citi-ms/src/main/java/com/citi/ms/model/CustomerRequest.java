package com.citi.ms.model;



import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class CustomerRequest {
	
	private String toDate;
	private  String  fromDate;
	private String investorName;
	private String programType;
	private int maxTenor;
	private String supplierExcluded;
	

}
