<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
<c:set var="cUserInfo" value="${sessionScope.userinfo}" />
    <link rel="stylesheet" href="assets/css/common-article.css"/>
    <title>방명록 작성하기</title>
</head>
<body>
    <div class="article-body">
        <form class="form-article" method="post" action="">
            <div class="article-layer">
                <div class="close-layer">
                     <img src="${root}/assets/img/ic_prev.svg">
                 </div>            
                <div class="title-layer">
                    <p>제목</p>                    
                </div>
                <input type="text" class="item-article title" name="title" placeholder="게시글 제목을 입력해주세요!"/>                
            </div>
			<c:if test="${cUserInfo ne null}">
	            <div class="article-layer">
	                <p>작성자</p>
	                <input type="text" class="item-article author" value="${cUserInfo.userName}" readonly name="author"/>
	            </div>            
            </c:if>
            <div class="article-layer">
                <p>내용</p>
                <textarea name="content" placeholder="게시글 내용을 입력해주세요."></textarea>
            </div>
            <div class="spacer"></div>
            <div class="btn-frames">
                <button class="btn-cancel">취소</button>
                <button class="btn-action">등록하기</button>
            </div>            
        </form>
    </div>
    <script>
	    let baseForm = document.querySelector(".article-body > .form-article");
	    let baseBtn = document.querySelector(".article-body > .form-article > .btn-frames > button:last-child");
    	const listURL = '${root}/guestbook';        	
	    baseBtn.addEventListener("click", (e) => {
	        e.preventDefault();
	        baseForm.setAttribute("action",  "${root}/write")
	        baseForm.submit();
	        alert('게시글이 작성되었습니다.');
	    });
    </script>
    <script src="${root}/assets/js/common-article.js"></script>
</body>
</html>