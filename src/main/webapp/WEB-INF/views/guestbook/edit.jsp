<%@page import="com.onehee.guestbook.model.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
<%@ page import="com.onehee.guestbook.model.*" %>
<c:set var="boardInfo" value="${sessionScope.boardInfo}"/>
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
                </div>
                <input id="m-input-title" type="text" class="item-article title" value="${boardInfo.title}"/>                
            </div>
            <div class="article-layer">
                <p>작성자</p>
                <input type="text" class="item-article author" value="${boardInfo.author}" readonly/>
            </div>
            <div class="article-layer">
                <p>내용</p>
                <textarea id="m-input-content">${boardInfo.content}</textarea>
            </div>
            <div class="spacer"></div>
            <div class="btn-frames">
                <button class="btn-cancel">취소</button>
                <button class="btn-save" id="btn-modifly-aritlce">저장하기</button>
            </div>
        </form>
    </div>
    <script>
    // 수정 요청
    // 타이틀
    let titleElement = document.getElementById("m-input-title");
    // 내용
    let contElement = document.getElementById("m-input-content");
    let btnModify = document.getElementById("btn-modifly-aritlce");
    btnModify.addEventListener("click", (e) => {
        e.preventDefault();
        const boardDTO = {
            // DTO 객체의 필드 및 값을 설정
            idx : ${boardInfo.idx},
            title: titleElement.value,
            author : '${boardInfo.author}',
            content : contElement.value
        };
        console.dir(boardDTO);
        fetch("${root}/edit", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(boardDTO)
        })
            .then(response => { 
            	console.dir(response);
            	if(response.ok){
            		alert('수정 완료!');
            		let listURL = "${root}/guestbook";
            		moveLink(listURL);
            	}
            })
            .catch(error => {
                // PUT 요청 중 발생한 오류 처리
                console.error('There was a problem with your PUT request:', error);
            });

    });
    </script>
    <script src="${root}/assets/js/common-article.js"></script>
</body>
</html>