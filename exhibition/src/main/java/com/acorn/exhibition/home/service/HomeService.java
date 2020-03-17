package com.acorn.exhibition.home.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.acorn.exhibition.home.dto.ApiDto;
import com.acorn.exhibition.home.dto.CommentDto;


public interface HomeService {
	public String getEvent();
	public void getPopularEvents(HttpServletRequest request);
	public void getData(HttpServletRequest request);
	public void addExhibition(ApiDto dto);
	public void deleteFromDate(String fromTime); // 시스템 시간을 입력받아서 기존 DB제거
	//댓글 저장하는 메소드
	public void saveComment(HttpServletRequest request);
	public void deleteComment(int num);
	public void updateComment(CommentDto dto);
	public void commentList(HttpServletRequest request);
	//전체 공연 list
	public void list(HttpServletRequest request);
	public Map<String, Object> updateLikeCount(HttpServletRequest request);

}
