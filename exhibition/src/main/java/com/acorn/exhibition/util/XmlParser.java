package com.acorn.exhibition.util;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.acorn.exhibition.home.dto.ExhibitionDto;

/**
 * Open API로 요청을 보내어 응답 받은 XML 형식의 데이터를 원하는 형식으로 파싱하는 클래스
 * @author hyeonkyung
 *
 */
public class XmlParser {
	
    /**
     * tag 이름을 가진 노드의 value 값을 추출한다.
     * getDetailData()메소드 호출시 내부적으로 getTagValue()메소드도 호출된다.
     * @param tag
     * @param eElement-요소 노드
     * @return String-요소 노드의 tag명
     */
	public static String getTagValue(String tag, Element eElement) {
	    NodeList nlList =  eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) {
	        return null;
	    }    
	    return nValue.getNodeValue();
	}
	
	/**
	 * 메서드를 호출하면서 공연 고유 번호를 전달하면 Open API로 요청을 보내 XML데이터를 파싱하여 ExhibitionDto에 담는다.
	 * @param seq-공연 고유 번호
	 * @return ExhibitionDto-xml 문서에서 파싱한 값을 DTO에 담아서 리턴
	 */
	public static ExhibitionDto getDetailData(int seq) {
		
		ExhibitionDto dto=new ExhibitionDto();
		
		try{
			String url = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/"
					+ "?ServiceKey=ePfX3pu9m%2BajEWgHGiwfbd%2BNSNIpVJ58g9N%2Fp%2B%2F996n%2FnwagNeyc52WUEYYlE34jKS00Eg3EOlVVVu4g0OTkSQ%3D%3D"
					+ "&seq="+seq;
			
			//XML문서를 DOM(문서 객체)로 파싱 
			DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			//DOM Tree가 XML 문서의 구조대로 완성될 수 있게 한다.
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			//DOM에서 파싱할 tag
			NodeList nList = doc.getElementsByTagName("perforInfo");
			System.out.println("파싱할 리스트 수 : "+ nList.getLength());
			
			for(int temp = 0; temp < nList.getLength(); temp++){
				Node nNode = nList.item(temp);
				if(nNode.getNodeType() == Node.ELEMENT_NODE){
					
					Element eElement = (Element)nNode;
					
					dto.setSeq(Integer.parseInt(getTagValue("seq", eElement)));
					dto.setTitle(getTagValue("title", eElement));
					dto.setStartDate(getTagValue("startDate", eElement));
					dto.setEndDate(getTagValue("endDate", eElement));
					dto.setPlace(getTagValue("place", eElement));
					dto.setRealmName(getTagValue("realmName", eElement));
					dto.setArea(getTagValue("area", eElement));
					dto.setSubTitle(getTagValue("subTitle", eElement));
					dto.setPrice(getTagValue("price", eElement));
					dto.setContents1(getTagValue("contents1", eElement));
					dto.setContents2(getTagValue("contents2", eElement));
					dto.setUrl(getTagValue("url", eElement));
					dto.setPhone(getTagValue("phone", eElement));
					dto.setGpsX(getTagValue("gpsX", eElement));
					dto.setGpsY(getTagValue("gpsY", eElement));
					dto.setImgUrl(getTagValue("imgUrl", eElement));
					dto.setPlaceUrl(getTagValue("placeUrl", eElement));
					dto.setPlaceAddr(getTagValue("placeAddr", eElement));
					dto.setPlaceSeq(getTagValue("placeSeq", eElement));
					
					System.out.println("######################");
					//System.out.println(eElement.getTextContent());
					System.out.println("일련번호  : " + getTagValue("seq", eElement));
					System.out.println("제목  : " + getTagValue("title", eElement));
					System.out.println("시작일 : " + getTagValue("startDate", eElement));
					System.out.println("마감일  : " + getTagValue("endDate", eElement));
					System.out.println("장소  : " + getTagValue("place", eElement));
					System.out.println("분류명  : " + getTagValue("realmName", eElement));
					System.out.println("지역  : " + getTagValue("area", eElement));
					System.out.println("공연부제목  : " + getTagValue("subTitle", eElement));
					System.out.println("티켓요금  : " + getTagValue("price", eElement));
					System.out.println("내용1  : " + getTagValue("contents1", eElement));
					System.out.println("내용2  : " + getTagValue("contents2", eElement));
					System.out.println("관람URL  : " + getTagValue("url", eElement));
					System.out.println("문의처  : " + getTagValue("phone", eElement));
					System.out.println("GPS-X좌표  : " + getTagValue("gpsX", eElement));
					System.out.println("GPS-Y좌표  : " + getTagValue("gpsY", eElement));
					System.out.println("이미지  : " + getTagValue("imgUrl", eElement));
					System.out.println("공연장URL  : " + getTagValue("placeUrl", eElement));
					System.out.println("공연장 주소  : " + getTagValue("placeAddr", eElement));
					System.out.println("문화예술공간 일련번호  : " + getTagValue("placeSeq", eElement));
					
				}	// for end
			}	// if end
				
		
		} catch (Exception e){	
			e.printStackTrace();
		}	// try~catch end
		
		return dto;
		
	} //getData method end
	

}
