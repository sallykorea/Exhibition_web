package com.acorn.exhibition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Service
public class HomeService {
	@Autowired
	private HomeDao dao;
	
	public String getEvent() {
		List<FullCalendarDto> list=dao.getEvent();
		String jsonStr=null;
		try {
			ObjectMapper mapper=new ObjectMapper();
			jsonStr=mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonStr;
	}
}