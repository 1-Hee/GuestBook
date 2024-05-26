<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="/WEB-INF/views/commons/header.jsp" %>
   <link rel="stylesheet" href="${root}/assets/css/index.css"/>    
   <title>1-hee's DashBoard</title>
</head>
<body>
    <div class="main-body">
        <div class="nav-main-page">
            <img class="main-icon" src="${root}/assets/img/book.png" alt="icon"/>
            <p class="main-title">1-hee’s DashBoard</p>        
            <div class="btn-guest-book" id="btn-guest-book">
                <img class="gb-icon" src="${root}/assets/img/fi-br-notebook.svg" alt="icon-btn"/>
                <p class="gb-text">방명록으로 이동하기</p>
                <div class="gp-spacer"></div>
            </div>
        </div>
    </div>    
    <script>
        let btnGuestBook = document.getElementById("btn-guest-book");
        btnGuestBook.addEventListener("click", (e)=> {
            location.href = "${root}/guestbook"
        });
    </script>
</body>
</html>
