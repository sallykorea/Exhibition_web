package com.acorn.exhibition.comment.service;
/**
 * 페이징 처리를하는 클래스
 * <br/>
 * <strong>!반드시 사용해야하는 메소드!</strong>
 * 1. setPageNum() 
 * 2. setTotalRow() 
 * 반드시 순서대로 사용. 
 * <br/>
 * setTotalRow() 메소드를 콜하면 pageNum, totalRow 필드를 사용하여
 * 페이징 계산을 하기 때문에 setPageNum() 메소드로 클라이언트가 선택한 페이지 번호가 반드시 
 * 필드에 저장되어 있어야함.
 * <br/>
 * <br/>
 * pageRowCount, pageDisplayCount 필드를 커스터마이징 하고 싶은 경우
 * setPageRowCount(), setPageDisplayCount()를 사용.
 * 
 * @author hyeonkyung
 *
 */
public class PageMaker {
	private int pageRowCount=10;
	private int pageDisplayCount=5;
	private int pageNum=1;
	private int startRowNum;
	private int endRowNum;
	private int totalRow;
	private int totalPageCount;
	private int startPageNum;
	private int endPageNum;
	
	/**
	 * 한 페이지에 나타낼 row의 개수를 커스터마이징 할 때 사용.
	 * <br />
	 * default 10
	 * @param pageRowCount
	 */
	public void setPageRowCount(int pageRowCount) {
		this.pageRowCount=pageRowCount;
	}
	
	/**
	 * 하단의 페이징 디스 플레이 페이지 갯수를 커스터마이징 할 때 사용 
	 * <br />
	 * default 5
	 * @param pageDisplayCount
	 */
	public void setPageDisplayCount(int pageDisplayCount) {
		this.pageDisplayCount=pageDisplayCount;
	}
	
	/**
	 * 
	 * 클라이언트가 선택한 페이지 번호를 세팅하는 메소드. 문자열을 전달하면 int type으로 파싱한다.
	 * @param String
	 * @exception strPageNum 인자의 값이 null일 경우, NumberFormatException 발생
	 */
	public void setPageNum(String strPageNum) {
		this.pageNum=Integer.parseInt(strPageNum);	
	}
	
	/**
	 * 전체 data의 개수가 세팅 되는 시점에 calcData() 메소드가 호출된다.
	 * @param data의 총 개수
	 */
	public void setTotalRow(int totalRow) {
		this.totalRow=totalRow;
		calcData();
	}
	
	/**
	 * 페이징 처리에 필요한 데이터를 계산함
	 */
	private void calcData() {
		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		startRowNum=1+(pageNum-1)*pageRowCount;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		endRowNum=pageNum*pageRowCount;
		//전체 페이지의 갯수 구하기
		totalPageCount=(int)Math.ceil(totalRow/(double)pageRowCount);
		//시작 페이지 번호
		startPageNum=1+((pageNum-1)/pageDisplayCount)*pageDisplayCount;
		//끝 페이지 번호
		endPageNum=startPageNum+pageDisplayCount-1;
		//끝 페이지 번호가 잘못된 값이라면 보정해준다. 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount;
		}
	}
	
	//jsp 페이지에서 필요한 값을 사용할 수 있도록 getter 메소드 
	public int getStartRowNum() {
		return startRowNum;
	}
	
	public int getEndRowNum() {
		return endRowNum;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public int getStartPageNum() {
		return startPageNum;
	}
	
	public int getEndPageNum() {
		return endPageNum;
	}
	
	public int getTotalPageCount() {
		return totalPageCount;
	}
	
	public int getTotalRow() {
		return totalRow;
	}
	
}
