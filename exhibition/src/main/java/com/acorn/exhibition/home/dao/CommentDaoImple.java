package com.acorn.exhibition.home.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acorn.exhibition.com.dto.ComDto;
import com.acorn.exhibition.home.dto.CommentDto;
import com.acorn.exhibition.home.dto.FullCalendarDto;

@Repository
public class CommentDaoImple implements CommentDao{
	@Autowired
	private SqlSession session;
	
	//인자로 전달된 그룹번호(원글의 글번호)에 해당하는 댓글 목록 얻어오기
	@Override
	public List<CommentDto> getList(FullCalendarDto dto) {
		return session.selectList("comment.getList", dto);
	}

	@Override
	public void delete(int num) {
		//댓글 삭제해도 oracle table에서는 지우지 않고 deleted 칼럼에 "no"라고만 표시한다.
		//(해당 댓글이 DB에서도 삭제되면 대댓글의 경우 위치가 위로 당겨지기때문에 보기 좋지 않음)
		session.update("comment.delete", num); 

	}
	
	@Override
	public void insert(CommentDto dto) {
		session.insert("comment.insert", dto);
		
	}
	
	//댓글의 시퀀스 값 얻기
	//대댓글의 그룹번호를 댓글의 그룹번호로 한다. 따라서 먼저 댓글의 시퀀스 값을 얻어내서 ㅕㅔ
	@Override
	public int getSequence() {
		int seq=session.selectOne("comment.getSequence");
		return seq;
	}

	@Override
	public void update(CommentDto dto) {
		session.update("comment.update", dto);
		
	}

	@Override
	public int getCount() {
		int count=session.selectOne("comment.getCount");
		return count;
	}
	//게시판 댓글
	@Override
	public List<CommentDto> getList(ComDto dto) {
		return session.selectList("comment.getList", dto);
	}
	
	
}