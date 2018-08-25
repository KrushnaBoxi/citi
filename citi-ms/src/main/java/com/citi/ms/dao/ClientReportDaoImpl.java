package com.citi.ms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.citi.ms.model.ClientReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

@Repository
public class ClientReportDaoImpl {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<String> findByDistinctFieldValue(String collectionName, Object fieldName ) {
		Iterable<String> distinctResult;
		Iterable<Integer> distinctResult1;
		String fieldNameString = (String) fieldName;
		if (fieldName instanceof String) {			
			distinctResult = mongoTemplate.getCollection(collectionName).distinct(fieldNameString,String.class);
			List<String> listResult = new ArrayList<>();
			distinctResult.forEach(listResult::add);
			return listResult;
	    }
		else if (fieldName instanceof Integer) {
			distinctResult1 = mongoTemplate.getCollection(collectionName).distinct(fieldNameString, Integer.class);
			List<Integer> listResult = new ArrayList<>();
			List<String> newList = new ArrayList<String>(listResult.size());
			for (Integer myInt : listResult) {
				newList.add(String.valueOf(myInt));
			}
			// distinctResult1.forEach(listResult::add);
			return newList;
		}
		return null;
		
		
		//List<String> listResult = new ArrayList<>();
		/*List<String> listResult = new ArrayList<>(((ArrayList) distinctResult).size());
		for (Object object : listResult) {
			listResult.add(Objects.toString(object, null));
		}*/
		//distinctResult.forEach(listResult::add);
		//return listResult;
	}

	public List<ClientReport> executeDynamicQuery(TreeMap<String, Object> clientRequestMap) {
		if (clientRequestMap == null) {
			return null;
		}
		Criteria criteria = new Criteria();
		for (String key : clientRequestMap.keySet()) {
			if (key.equals("toDate")) {
				criteria = criteria.and(key).lte(clientRequestMap.get(key));
			} else if (key.equals("fromDate")) {
				criteria = criteria.and(key).gte(clientRequestMap.get(key));
			} else {
				criteria = criteria.and(key).is(clientRequestMap.get(key));
			}
		}
		Query query = new Query(criteria);
		List<ClientReport> clientReportsResult = mongoTemplate.find(query, ClientReport.class);
		return clientReportsResult;
	}

}
