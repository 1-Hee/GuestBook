<%@page import="com.onehee.guestbook.model.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
<c:set var="cUserInfo" value="${sessionScope.userinfo}" />
<c:set var="cArticle" value="${sessionScope.article}" />
<%
	BoardDTO mArticle = null;
	if(session.getAttribute("article")!= null){
		mArticle = (BoardDTO) session.getAttribute("article");
	}
%>
    <link rel="stylesheet" href="assets/css/common-article.css"/>
    <title>글제목...</title>
</head>
<body>
    <div class="article-body">
        <form class="form-article" method="post" action="">
            <div class="article-layer title-layer">
                <div class="close-layer">
                     <img src="${root}/assets/img/ic_prev.svg">
                 </div>                        
                <div class="title-layer">
                    <p>제목</p>
                    <c:if test="${cUserInfo.userName eq cArticle.author}">
						<img src="${root}/assets/img/ic_trans_bin.svg" alt="icon-trans-bin" class="ic-article-trash"/>                        
                    </c:if>                    
                </div>
                <input type="text" class="item-article title" value="" id="d-article-title" readonly/>                
            </div>
            <div class="article-layer">
                <p>작성자</p>
                <input type="text" class="item-article author" id="d-article-author" readonly/>
            </div>
            <div class="article-layer">
                <p>내용</p>
                <textarea readonly><%= mArticle != null? mArticle.getContent() : ""  %></textarea>
            </div>
            <div class="spacer"></div>
            <c:if test="${cUserInfo.userName eq cArticle.author}">
	            <div class="btn-frames">
	                <button class="btn-action">수정하기</button>
	            </div>			                
            </c:if>            
        </form>
        <!-- 삭제 경고 알림창... -->
        <div class="dark-filter" style="display: none;"></div>
        <div class="modal-container" style="display: none;" id="d-modal-container">
            <div class="modal-delete-article">
                <div class="modal-conent"></div>
                <div class="frame-btns">
                    <button class="btn-cancel" id="w-btn-cancel">취소</button>
                    <button class="btn-delete-article" id="btn-delete-article">삭제</button>
                </div>
            </div>
        </div>        
    </div>
    <script>
    	let sTitle = '<%= mArticle != null ? mArticle.getTitle() : "" %>';
    	let sAuthor= '<%= mArticle != null ? mArticle.getAuthor() : "" %>'    			
    	let titleElement = document.getElementById('d-article-title');
    	let authorElement = document.getElementById('d-article-author');
    	titleElement.value = sTitle;
    	authorElement.value = sAuthor;
    	
    	const listURL = '${root}/guestbook';        	
    	// 삭제
        let btnDeleteArticle = document.getElementById("btn-delete-article");
        btnDeleteArticle.addEventListener("click", (e) => {
            e.preventDefault();
            let url = "${root}/delete?idx=" + '<%= mArticle.getIdx() %>';
            let config = {
                method: "DELETE", // *GET, POST, PUT, DELETE 등
            }
            fetch(url, config)
            .then(response => {
            	if(response.status === 200){
            		dispose();
            		moveLink(listURL);
            	    // window.history.back();  
            		// alert('게시글이 삭제되었습니다.');            	    
            	}
            });
        });
        
        let mvModifyBtn = document.querySelector(".article-body > .form-article > .btn-frames > button:last-child");
        mvModifyBtn.addEventListener("click", (e) => {
            e.preventDefault();
            location.href = "${root}/edit?idx=" + '<%= mArticle.getIdx() %>';
        })
    	
    </script>
    <script src="${root}/assets/js/common-article.js"></script>
    <script src="${root}/assets/js/article.js"></script>    
</body>
</html>