<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
   <link rel="stylesheet" href="${root}/assets/css/login.css"/>   
   <title>사용자 로그인</title>
</head>
<body>
    <div class="main-body">
        <form id="form-login" class="form-login" method="POST" action="#">
        	<div class="close-layer">
               <img src="${root}/assets/img/ic_close.svg">
            </div>
            <div class="d-id-input">
                <img src="${root}/assets/img/icon_user.svg" alt="icon-user" class="lg-icon-user"/>
                <input type="text" class="id-input" name="userName" placeholder="닉네임을 입력해주세요."/>                
            </div>
            <div class="d-pwd-input">
                <img src="${root}/assets/img/icon_lock.svg" alt="icon-user" class="lg-icon-lock"/>
                <input type="password" class="pwd-input" name="userPassword" placeholder="비밀번호를 입력해주세요."/>                                
            </div>
            <div class="register-frame">
                <p class="register-guide">계정이 없으신가요? 지금 바로 가입하기</p>
                <a href="#" class="mv-register" id="btn-mv-register">회원 가입</a>
            </div>
            <button class="btn-login" id="btn-do-login" action="">로그인</button>
        </form>
    </div>  
	<script>
       // 회원 가입 이동
       let btnMvRegsiter = document.getElementById("btn-mv-register");
       btnMvRegsiter.addEventListener("click", (e) => {
           e.preventDefault();
           location.href = "${root}/user/register"
       });

       // 로그인 버튼
       let mBtnLogin = document.getElementById("btn-do-login");
       mBtnLogin.addEventListener("click", (e) => {
           e.preventDefault();
           if (!document.querySelector("#form-login > .d-id-input > .id-input").value) {
               alert("닉네임이 비었습니다!");
               return;
           } else if (!document.querySelector("#form-login > .d-pwd-input > .pwd-input").value) {
               alert("비밀번호를 입력해주세요!");
               return;
           } else {
               let form = document.querySelector("#form-login");
               form.setAttribute("action", "${root}/user/login");
               form.submit();
           }
       });
       
       let btnClose =  document.querySelector("#form-login > .close-layer > img");
       btnClose.addEventListener("click", (e)=> {
           e.preventDefault();
           window.history.back();
       });
    </script>    
</body>
</html>