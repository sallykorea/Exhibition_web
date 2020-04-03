package com.acorn.exhibition.home.dao;

import java.util.List;

import com.acorn.exhibition.home.dto.ApiDto;

import com.acorn.exhibition.home.dto.FullCalendarDto;
import com.acorn.exhibition.home.dto.LikeDto;

public interface HomeDao {
	public List<FullCalendarDto> getEvent();
	public FullCalendarDto getData(int seq);
	public void insert(ApiDto dto);
	//list
	public int getCount(FullCalendarDto dto);
	public List<FullCalendarDto> getList(FullCalendarDto dto);
	public List<FullCalendarDto> getfavoriteList(FullCalendarDto dto);
	public List<FullCalendarDto> getdateList(FullCalendarDto dto);
	// 좋아요
	public int findLike(FullCalendarDto dto);
	public int getLikeCount(int seq);
	public boolean removeOnExhibitionLike(FullCalendarDto dto);
	public boolean addOnExhibitionLike(FullCalendarDto dto);
	public boolean addLikeCount(FullCalendarDto dto);
 	public boolean minusLikeCount(FullCalendarDto dto);
 	public String getExhibitionLikeId(LikeDto likeDto);
 	
}
