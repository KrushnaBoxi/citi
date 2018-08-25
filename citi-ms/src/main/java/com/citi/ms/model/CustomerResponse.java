package com.citi.ms.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class CustomerResponse {
	
	private int responseCode;
	private String responseMessage;
	private List<ClientReport> clientReport;

}
