<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전체공연보기</title>
<jsp:include page="include/resource.jsp" />
<style>
@import url(//fonts.googleapis.com/earlyaccess/nanumpenscript.css);
	p, .form-group{
		font-size:20px;
		font-family: 'Nanum Pen Script', cursive;
	}
	
	button{
		vertical-align:middle;
	}
	/*검색버튼*/
	button.img-button{
		background:url("resources/images/button_search.png") no-repeat;
		border:none;
		width:38px;
		height:38px;
		cursor:pointer
	}
	/*breadcrumb 색상변경*/
	#bread{
		background-color: #ffdd33;
	}
	/*표 색상 변경*/
	.tr{
		background-color: #FAEBD7;
	}
	
	
	/*thead 색상변경*/
	/*
	.title{
		background-color: #4682B4;
	}*/
	
</style>
<style type="text/css">
    .condition{
   		margin: 10px 0 20px 0;
    }
    td, th{
    	text-align: center;
    }
    .heart{
		width: 20px;
		height: auto;
	}
	.arrow{
		width: 15px;
		height: auto;
	}
</style>
</head>
<body>
<jsp:include page="include/navbar.jsp">
	<jsp:param value="list" name="category"/>
</jsp:include>
<div class="container">
	<ol class="breadcrumb" id="bread">
		<li><a href="${pageContext.request.contextPath }/community/comList.do">목록</a></li>
		<li>전체공연 보기</li>
	</ol>
	
	
	<div class="condition" align="right">
		<form class="form-inline" action="list.do" method="get"> 
			<div class="form-group">
				<label for="condition">검색조건</label>
				<select class="form-control" name="condition" id="condition">
					<option value="title" <c:if test="${condition eq 'title' }">selected</c:if>>제목</option>
					<option value="place" <c:if test="${condition eq 'place' }">selected</c:if>>장소</option>
				</select>
				<input class="form-control" type="text" name="keyword" id="keyword" value="${keyword }" placeholder="검색어를 입력하세요" />
				<button type="submit" class="img-button"></button>
			</div>
		</form>
	</div>
	<c:choose>
		<c:when test="${not empty keyword }">
			<p>
				<strong>${keyword }</strong> 키워드로 검색된
				<strong>${totalRow }</strong> 개의 파일이 있습니다.
			</p>
		</c:when>
		<c:otherwise>
			<p><strong>${totalRow }</strong> 개의 파일이 있습니다.</p>
		</c:otherwise>
	</c:choose>
	<table class="table table-hover">
		<colgroup>
			<col class="col-xs-6"/>
			<col class="col-xs-1"/>
			<col class="col-xs-2"/>
			<col class="col-xs-3"/>
		</colgroup>
		<thead>
			<tr class="title">
				<th>공연명 </th>
				<th>좋아요</th>
				<th>장소</th>
				<th>공연기간</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="tmp" items="${requestScope.list }">
				<tr class="tr">
					<td>
						<a href="detail.do?seq=${tmp.seq }">
							${tmp.title }
						</a>				
					</td>				
					<td>
						<img class="heart" src="${pageContext.request.contextPath }/resources/images/red-heart.png" alt="" />
						${tmp.likeCount }
					</td>				
					<td>${tmp.place }</td>
					<td>${tmp.startdate } ~ ${tmp.enddate }</td>
				</tr>
				
			</c:forEach>
		</tbody>	
	</table>
	
	<div class="page-display" style="text-align: center;">
		<ul class="pagination pagination-sm">
			<c:choose>
				<c:when test="${startPageNum ne 1 }">
					<li>
						<a href="list.do?pageNum=${startPageNum-1 }&condition=${condition }&keyword=${encodedKeyword }">&laquo;</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="disabled">
						<a href="javascript:">&laquo;</a>
					</li>
				</c:otherwise>
			</c:choose>
			
			<c:forEach var="i" begin="${requestScope.startPageNum }" end="${requestScope.endPageNum }" step="1">
				<c:choose>
					<c:when test="${i eq pageNum }">
						<li class="active">
							<a href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedKeyword }">${i }</a>
						</li>
					</c:when>
					<c:otherwise>
						<li>
							<a href="list.do?pageNum=${i }&condition=${condition }&keyword=${encodedKeyword }">${i }</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			<c:choose>
				<c:when test="${endPageNum < totalPageCount }">
					<li>
						<a href="list.do?pageNum=${endPageNum+1 }&condition=${condition }&keyword=${encodedKeyword }">&raquo;</a>
					</li>
				</c:when>
				
				<c:otherwise>
					<li class="disabled">
						<a href="javascript:">&raquo;</a>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>
</body>
</html>