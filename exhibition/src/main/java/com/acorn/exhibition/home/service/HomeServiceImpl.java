package com.acorn.exhibition.home.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.acorn.exhibition.comment.dao.CommentDao;
import com.acorn.exhibition.home.dao.HomeDao;
import com.acorn.exhibition.home.dto.ExhibitionDto;
import com.acorn.exhibition.home.dto.CommentLikeDto;
import com.acorn.exhibition.home.dto.CommentDto;
import com.acorn.exhibition.home.dto.FullCalendarDto;
import com.acorn.exhibition.home.dto.LikeDto;
import com.acorn.exhibition.home.dto.mapDto;
import com.acorn.exhibition.util.PageMaker;
import com.acorn.exhibition.util.XmlParser;

@Service
public class HomeServiceImpl implements HomeService{
	@Autowired
	private HomeDao dao;
	@Autowired
	private CommentDao commentDao;
	
	/*
	  	- 작성자 : 김현경
	  	- 작성일 : 2020-02-27
	  	- 수정일 : 2020-04-19
	  	- Method 설명 : fullcalendar의 getEvents.do 요청에 대해 jsonObject(event 1개의 정보)가 담겨 있는 jsonArray(event들)로 응답.
  	*/
	@Override
	public List<Map<String, Object>> getEvent(HttpServletRequest request) {
		
		// 작성자:김현경, fullcalendar 출력할 data list
		List<FullCalendarDto> list=dao.getEvent();
		
		// 작성자:김현경, event들을 담을 ArrayList객체
		List<Map<String, Object>> events = new ArrayList<Map<String, Object>>();
		
		/*
		 * 작성자:김현경, FullCalendarDto에 담겨 있는 데이터를 Map객체에 담고, ArrayList에 추가하기
		 * Key값이 fullcalendar의 EventObject의 속성명과 같아야 렌더링가능.
		 * 참고 문서 : https://fullcalendar.io/docs/event-object
		 */
		for(FullCalendarDto tmp:list) {
			String realMname=tmp.getRealmname();
			Map<String, Object> event=new HashMap<String, Object>();
			event.put("title", tmp.getTitle());
			event.put("start", tmp.getStartdate());
			event.put("end", tmp.getEnddate());
			event.put("url", request.getContextPath()+"/detail.do?seq="+tmp.getSeq());		
			switch (realMname) {
				case "음악":
					event.put("color", "#6937a1");
					break;
				case "연극":
					event.put("color", "#008d62");
					break;
				case "미술":
					event.put("color", "#2a67b7");
					break;
				case "무용":
					event.put("color", "#dc143c");
					break;
				case "기타":
					event.put("color", "#6937a1");
					break;
				default:
					event.put("color", "#ff3399");
					break;
			}
			
			events.add(event);
			
		}
		
		return events;
	}

	@Override
	public void getPopularEvents(ModelAndView mView) {
		List<FullCalendarDto> list=dao.getPopularEvents();
		mView.addObject("list", list);
	}
	
	/*
	 * - 작성자 : 김현경
	 * - 작성일 : 2020-04-22
	 * - Method 설명 : 공연 상세 정보를 조회하는 메소드 
	 */
	@Override
	public void getData(HttpServletRequest request) {
		
		//파라미터로 전달되는 정보들을 갖고 온다.
		int seq=Integer.parseInt(request.getParameter("seq"));
		String id=(String)request.getSession().getAttribute("id");
		String strPageNum=request.getParameter("pageNum");

		//Open API 요청을 보내 공연에 대한 상세정보를 받아오고, 이를 exhibitionDto에 담아 request 객체에 담기
		ExhibitionDto exhibitionDto = XmlParser.getDetailData(seq);
		exhibitionDto.setLikeCount(dao.getData(seq).getLikeCount());
		request.setAttribute("exhibitionDto", exhibitionDto);
		
		//페이징 처리. PageMaker 객체를 활용하여 필요한 데이터를 계산한다.
		PageMaker pageMaker=new PageMaker();
		pageMaker.setPageRowCount(8);
		if(strPageNum != null){
			pageMaker.setPageNum(strPageNum);
		}
		pageMaker.setTotalRow(commentDao.getCount());
		
		//댓글 목록을 얻어오기
		FullCalendarDto dto=new FullCalendarDto();
		dto.setSeq(seq);
		dto.setStartRowNum(pageMaker.getStartRowNum());
		dto.setEndRowNum(pageMaker.getEndRowNum());
		List<CommentDto> commentList=commentDao.getList(dto);
		request.setAttribute("commentList", commentList);
		
		//공연, 댓글 좋아요 : 좋아요 한 적이 있다면 빨간 하트 표시, 없다면 빈하트 표시
		//로그인한 아이디가 해당 공연 또는 댓글을 좋아요 한 적이 있는지 id 동일 여부로 판단
		List<CommentLikeDto> comLikeList=new ArrayList<CommentLikeDto>();
		String ExhibitionLikeId=null;
		boolean isCommentLikeId=false;
		
		if(id!=null) {
			 //공연 좋아요
			 LikeDto likeDto=new LikeDto(seq, id);
			 ExhibitionLikeId=dao.getExhibitionLikeId(likeDto);
			 
			 //댓글 좋아요
			 for(int i=0;i<commentList.size();i++) {
				CommentDto commentDto = commentList.get(i);
				int num = commentDto.getNum();
				CommentLikeDto comLikeDto = new CommentLikeDto(id,num);
				String CommentLikeId=commentDao.getCommentLikeId(comLikeDto);
				if(id.equals(CommentLikeId)) {
				   isCommentLikeId = true;
				   comLikeDto.setIsCommentLikeId(isCommentLikeId);
				  
				}else {
				   isCommentLikeId = false;
				   comLikeDto.setIsCommentLikeId(isCommentLikeId);
				}
				comLikeList.add(comLikeDto);
			 }//for end
		}//if end
		      
		request.setAttribute("ExhibitionLikeId", ExhibitionLikeId);
		request.setAttribute("id", id);
		request.setAttribute("comLikeList", comLikeList);
		
	}
	
	@Override
	public void addExhibition(ExhibitionDto dto) {
		dao.insert(dto);
		
	}
	
	/*
	 * - 작성자 : 김현경
	 * - 작성일 : 2020-04-22
	 * - Method 설명 :
	 * 	전체 공연 list를 조회하는 메소드. 
	 * 	검색 조건이 있을 경우, 조건에 맞는 data만 조회한다.
	 */
	@Override
	public void list(HttpServletRequest request) {
		
		//검색과 관련된 파라미터를 읽어옴.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		String startdate=request.getParameter("startDate");
		String enddate=request.getParameter("endDate");
		
		//검색 조건이 있다면 dto에 담아 DB 조회시 파라미터로 보낼 준비 및 request 객체에 필요한 정보 담기 
		FullCalendarDto dto=new FullCalendarDto();
		if(keyword!=null || (startdate!=null && startdate!=null)) {
			if (condition.equals("title")) {//제목 검색
				dto.setTitle(keyword);
			}else if (condition.equals("place")) {//장소 검색
				dto.setPlace(keyword);
			}else if (condition.equals("date")) {//기간 검색
				dto.setStartdate(startdate);
				dto.setEnddate(enddate);
			}
			
			//링크에 검색어를 그대로 출력해주기 위해 인코딩
			String encodedKeyword=null;
			if(keyword!=null) {
				try {
					encodedKeyword=URLEncoder.encode(keyword, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			request.setAttribute("keyword", keyword);
			request.setAttribute("encodedKeyword", encodedKeyword);
			request.setAttribute("condition", condition);
			request.setAttribute("startdate", startdate);
			request.setAttribute("enddate", enddate);

		}//if end
		
		//페이징 처리. PageMaker 객체를 활용하여 필요한 데이터를 계산한다.
		PageMaker pageMaker=new PageMaker();
		
		//페이지 번호가 파라미터로 넘어온다면
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){
			//페이지 번호를 설정한다.
			pageMaker.setPageNum(strPageNum);
		}
		
		//list에 출력할 전체 데이터 개수 
		pageMaker.setTotalRow(dao.getCount(dto));
		
		//선택된 페이지에 해당하는 data를 조회 하기 위해 필요한 정보를 dto에 담기
		dto.setStartRowNum(pageMaker.getStartRowNum());
		dto.setEndRowNum(pageMaker.getEndRowNum());
		
		//DB 에서 글 목록을 얻어온다.
		List<FullCalendarDto> list=dao.getList(dto);
		request.setAttribute("list", list);

		//request 객체에 pageMaker를 전달해 페이징 처리
		request.setAttribute("pageMaker", pageMaker);
		
	}

	/*
	 * - 작성자 : 김현경
	 * - 작성일 : 2020-04-25
	 * - Method 설명 : 공연 상세 정보의 좋아요  기능
	 */
	static final int CLICKED=1;
	
	@Override
	public Map<String, Object> updateLikeCount(HttpServletRequest request) {
		
		int seq=Integer.parseInt(request.getParameter("seq"));
		String id=(String)request.getSession().getAttribute("id");
		
		FullCalendarDto dto=new FullCalendarDto();
		dto.setSeq(seq);
		dto.setId(id);
		
		//{"isSuccess":boolean, "likecount":number}
		Map<String, Object> map=new HashMap<String, Object>();

		//exhibition_like 테이블에서 로그인된 id가 like를 클릭한적 있는지 찾아보기
		int isClicked=dao.findLike(dto);
		int likeCount=0;
		
		if(isClicked==CLICKED) { //클릭한적 있다면
			//exhibition_like 테이블에서 정보를 제거하고
			boolean isRemoved=dao.removeOnExhibitionLike(dto);
			//tb_api_date 테이블에서 likeCount 개수를 하나 빼준다.
			boolean minusLikeCount=dao.minusLikeCount(dto);
			likeCount=dao.getData(seq).getLikeCount();

			if(isRemoved && minusLikeCount) {
				map.put("isSuccess", true);
				map.put("likecount", likeCount);
				return map;
			}else {
				map.put("isSuccess", false);
				map.put("likecount", likeCount);
				return map;
			}
			
			
		}else { //클릭한적 없다면
			
			//exhibition_like 테이블에 id와 seq번호를 저장하고
			boolean isAdded=dao.addOnExhibitionLike(dto);
			//tb_api_date 테이블에서 likeCount 개수를 하나 더해준다.
			boolean addLikeCount=dao.addLikeCount(dto);
			likeCount=dao.getData(seq).getLikeCount();
			
			if(isAdded && addLikeCount) {
				map.put("isSuccess", true);
				map.put("likecount", likeCount);
				return map;
			}else {
				map.put("isSuccess", false);
				map.put("likecount", likeCount);
				return map;
			}
		}//if(isClicked==CLICKED) end
		
	}//updateLikeCount() end

	@Override
	public void listfavor(HttpServletRequest request) {
		/*
		 *  request 에 검색 keyword 가 전달될수도 있고 안될수도 있다.
		 *  - 전달 안되는 경우 : navbar 에서 cafe를 누른경우 
		 *  - 전달 되는 경우 : 하단에 검색어를 입력하고 검색 버튼을 누른경우
		 *  - 전달 되는 경우2: 이미 검색을 한 상태에서 하단 페이지 번호를 누른 경우 
		 */
		
		//검색과 관련된 파라미터를 읽어와 본다.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		String startdate=request.getParameter("startDate");
		String enddate=request.getParameter("endDate");
		String startdateFormat="";
		String enddateFormat="";
		
		if(startdate!=null && startdate!=null){
			String[] startdateArr=startdate.split("-");
			String[] enddateArr=enddate.split("-");
			
			for(int i=0; i<startdateArr.length; i++) {
				startdateFormat+=startdateArr[i];
			}
			
			for(int i=0; i<enddateArr.length; i++) {
				enddateFormat+=enddateArr[i];
			}
			
		}
		
		FullCalendarDto dto=new FullCalendarDto();
		if(keyword!=null || (startdate!=null && startdate!=null)) {
			if (condition.equals("title")) {//제목 검색
				dto.setTitle(keyword);
			}else if (condition.equals("place")) {//장소 검색
				dto.setPlace(keyword);
			}else if (condition.equals("date")) {//기간 검색
				dto.setStartdate(startdateFormat);
				dto.setEnddate(enddateFormat);
			}
			
			/*
			 *  검색 키워드에는 한글이 포함될 가능성이 있기 때문에
			 *  링크에 그대로 출력가능하도록 하기 위해 미리 인코딩을 해서
			 *  request 에 담아준다.
			 */
			String encodedKeyword=null;
			if(keyword!=null) {
				try {
					encodedKeyword=URLEncoder.encode(keyword, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}

			//키워드와 검색조건을 request 에 담는다. 
			request.setAttribute("keyword", keyword);
			request.setAttribute("encodedKeyword", encodedKeyword);
			request.setAttribute("condition", condition);
			request.setAttribute("startdate", startdate);
			request.setAttribute("enddate", enddate);
			request.setAttribute("startdateFormat", startdateFormat);
			request.setAttribute("enddateFormat", enddateFormat);

		}//if end
		
		//한 페이지에 나타낼 row 의 갯수
		final int PAGE_ROW_COUNT=10;
		//하단 디스플레이 페이지 갯수
		final int PAGE_DISPLAY_COUNT=5;
		
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//전체 row 의 갯수를 읽어온다.
		int totalRow=dao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}		
		// CafeDto 객체에 위에서 계산된 startRowNum 과 endRowNum 을 담는다.
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		List<FullCalendarDto> list=dao.getListfavor(dto);
		request.setAttribute("list", list);
		
		//EL, JSTL 을 활용하기 위해 필요한 모델을 request 에 담는다.
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("totalRow", totalRow);
		
		
	}

	@Override
	public List<mapDto> maplistplace(HttpServletRequest request) {
		mapDto dto = new mapDto();
		List<mapDto> maplist=dao.mapList(dto);
		//request.setAttribute("maplist", maplist);
		//request.setAttribute("jsonmap",JSONArray.toJSONString(maplist));
		return maplist;
	}

	@Override
	public void maplist(HttpServletRequest request) {
	
		//검색과 관련된 파라미터를 읽어와 본다.
		String keyword=request.getParameter("keyword");
		System.out.println(keyword);
		
		FullCalendarDto dto=new FullCalendarDto();
			//장소 검색
			dto.setPlace(keyword);
			/*
			 *  검색 키워드에는 한글이 포함될 가능성이 있기 때문에
			 *  링크에 그대로 출력가능하도록 하기 위해 미리 인코딩을 해서
			 *  request 에 담아준다.
			 */
			String encodedKeyword=null;
			if(keyword!=null) {
				try {
					encodedKeyword=URLEncoder.encode(keyword, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			//키워드와 검색조건을 request 에 담는다. 
			request.setAttribute("keyword", keyword);
			request.setAttribute("encodedKeyword", encodedKeyword);
		//한 페이지에 나타낼 row 의 갯수
		final int PAGE_ROW_COUNT=10;
		//하단 디스플레이 페이지 갯수
		final int PAGE_DISPLAY_COUNT=5;
		
		//보여줄 페이지의 번호
		int pageNum=1;
		//보여줄 페이지의 번호가 파라미터로 전달되는지 읽어와 본다.	
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){//페이지 번호가 파라미터로 넘어온다면
			//페이지 번호를 설정한다.
			pageNum=Integer.parseInt(strPageNum);
		}
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//전체 row 의 갯수를 읽어온다.
		int totalRow=dao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount=(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}		
		// CafeDto 객체에 위에서 계산된 startRowNum 과 endRowNum 을 담는다.
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		List<FullCalendarDto> mapplacelist=dao.getListmap(dto);
		request.setAttribute("mapplacelist", mapplacelist);
		
		//EL, JSTL 을 활용하기 위해 필요한 모델을 request 에 담는다.
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("totalRow", totalRow);
		
		
		
	}

	

}
