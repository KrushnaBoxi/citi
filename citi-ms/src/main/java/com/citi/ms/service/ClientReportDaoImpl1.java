package com.citi.ms.service;

import com.citi.ms.dao.ClientReportDAO;
import com.citi.ms.model.ClientReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClientReportDaoImpl1  {
    private static final String COLLECTION = "clientReport";
    @Autowired
    MongoTemplate mongoTemplate;

    /*public List<String> findByField(String program) {
        return mongoTemplate.getCollection(COLLECTION).distinct(program, ClientReport.class);
    }*/
}
