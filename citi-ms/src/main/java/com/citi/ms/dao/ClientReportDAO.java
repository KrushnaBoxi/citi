package com.citi.ms.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.citi.ms.model.ClientReport;
import org.springframework.data.mongodb.repository.Query;

public interface ClientReportDAO extends MongoRepository<ClientReport, String>{
	
	ClientReport findByInvestorName(String investorName);

	List<ClientReport> findByToDateBeforeAndFromDateAfter(Date toDate, Date fromDate);
	List<ClientReport> findByToDateLessThanEqualAndFromDateGreaterThanEqual(Date toDate, Date fromDate);
	List<ClientReport> findByToDateLessThanEqualAndFromDateGreaterThanEqualAndProgram(Date toDate, Date fromDate,
			String program);
	List<ClientReport> findByToDateLessThanEqualAndFromDateGreaterThanEqualAndInvestorName(Date toDate, Date fromDate,
			String investorName);
	List<ClientReport> findByToDateLessThanEqualAndFromDateGreaterThanEqualAndInvestorNameAndProgram(Date toDate,
			Date fromDate, String investorName, String program);
	//@Query("SELECT p.program FROM ClientReport p WHERE p.program = ?1")
   /* @Query(" { program : ?0}")
	public List<ClientReport> findDistinctProgram();*/
   /* @Query( fields="{ 'program' : 1, 'limit' : 1}")
    public List<String> findAllProgram();*/



}
