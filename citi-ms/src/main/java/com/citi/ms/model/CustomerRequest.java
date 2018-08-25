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
	
	private Date toDate;
	private Date fromDate;
	private String investorName;
	private String program;
	private int maxTenor;
	

}
