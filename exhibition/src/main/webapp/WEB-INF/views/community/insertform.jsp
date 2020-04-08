<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/community/insertform.jsp</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/custom.css" />
<!-- include summernote css/js-->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.8/summernote.js"></script>

<script src="${pageContext.request.contextPath }/resources/js/summernote/summernote-ko-KR.js"></script>
<script>
	$(document).ready(function() {
		$('#summernote').summernote({
			placeholder: 'Hello stand alone ui',
	        tabsize: 2,
	        height: 400,
	        toolbar: [
	          ['style', ['style']],
	          ['font', ['bold', 'underline', 'clear']],
	          ['color', ['color']],
	          ['para', ['ul', 'ol', 'paragraph']],
	          ['table', ['table']],
	          ['insert', ['link', 'picture', 'video','table','hr']],
	          ['view', ['fullscreen', 'codeview', 'help']]
	          
	          
	        ]
		});
	});
</script>
<style>
	/* textarea 의 크기가 SmartEditor 의 크기가 된다. */
	#content{
		display: none;
		width: 100%;
		height: 400px;
	}
	.sub-nav-left{
		display:block;
		position:relative;
		font-size:15px;
		float:none;
		margin-top:10px;
		margin-bottom: 20px;
		text-align:left;
		border-bottom:1px solid #ddd;
		padding:1px 0 5px;
		font-family: "Noto Sans KR","맑은 고딕","Malgun Gothic",;
	}
</style>
</head>
<body>
<jsp:include page="../include/navbar.jsp">
	<jsp:param value="community" name="category"/>
</jsp:include>
<div class="container">
	<div class="sub-nav-left">
		<a href="home.do">
			<img src="${pageContext.request.contextPath }/resources/images/home.png" alt="홈" />
		</a>
		> 
		<a href="${pageContext.request.contextPath }/community/comList.do">자유게시판</a>
		> 
		<a href="${pageContext.request.contextPath }/community/insertform.do">새글작성</a>
	</div>
	<form action="insert.do" method="post">
		<div class="form-group">
			<label for="writer">작성자</label>
			<input class="form-control" type="text" value="${id }" readonly="readonly"/>
		</div>
		<div class="form-group">
			<label for="writer">작성자</label>
			<input class="form-control" type="text"/>
		</div>
		<div class="form-group">
			<label for="title">제목</label>
			<input class="form-control" type="text" name="title" id="title"/>
		</div>
		<div class="form-group">
			<label for="content">내용</label>
			<textarea id="summernote" name="editordata"></textarea>
		</div>
		<button class="btn btn-primary" type="submit" onclick="submitContents(this);">저장</button>
		<button class="btn btn-warning" type="reset"><a href="${pageContext.request.contextPath }/community/comList.do">취소</a></button>
	</form>
</div>
</body>
</html>