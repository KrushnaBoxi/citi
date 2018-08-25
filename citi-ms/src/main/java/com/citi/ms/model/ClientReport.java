package com.citi.ms.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document @Getter @Setter @Builder @ToString @NoArgsConstructor @AllArgsConstructor
public class ClientReport implements Serializable {
	private static final long serialVersionUID = 516898520280769315L;
	//@Id
	//private long id;
	private String investorName;
	private String program;
	private String limit;
	private String ccySold;
	private String investorPricing;
	private String supplierExcluded;
	private String supplierCountiesPermitted;
	private String obligorCodesPermitted;
	private int minTenor;
	private int maxTenor;
	private int minBookingMargin;
	private int maximumNumberOfNotesAllowed;
	private long minimumNoteSize;
	private Date lastUpdated;
	private Date toDate;
	private Date fromDate;
	
	

}
