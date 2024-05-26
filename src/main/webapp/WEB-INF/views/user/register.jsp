<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
        <link rel="stylesheet" href="${root}/assets/css/register.css"/>
        <title>사용자 회원가입</title>
    </head>
    <body>
        <div class="main-body">
            <form id="form-register" class="form-register" method="POST" action="#">
                <div class="close-layer">
                    <img src="${root}/assets/img/ic_close.svg">
                </div>            
                <div class="register-title">
                    <p>1-hee’s DashBoard
                    </p>
                </div>
                <div class="register-guide"></div>
                <div class="d-r-id-container">
                    <div class="d-r-id-input">
                        <img src="${root}/assets/img/icon_user.svg" alt="icon-user" class="r-icon-user"/>
                        <input
                            type="text"
                            class="r-id-input"
                            id="r-id-input"
                            name="userName"                            
                            placeholder="사용하실 닉네임을 입력해주세요."/>
                    </div>
                    <p class="ind-nick-name" id="ind-nick-name" style="display: none;"></p>
                </div>
                <div class="d-r-pwd-container">
                    <div class="d-r-pwd-input">
                        <img src="${root}/assets/img/icon_lock.svg" alt="icon-lock" class="r-icon-lock"/>
                        <input
                            type="password"
                            class="r-pwd-input"
                            id="r-pwd-input"
                            name="userPassword"                                                        
                            placeholder="비밀번호를 입력해주세요."/>
                    </div>
                </div>
                <div class="d-r-pwd-container">
                    <div class="d-r-pwd-input">
                        <img src="${root}/assets/img/icon_lock.svg" alt="icon-lock" class="r-icon-lock"/>
                        <input
                            type="password"
                            class="r-pwd-input"
                            id="r-pwd-check"
                            placeholder="비밀번호를 한번 더 입력해주세요."/>
                    </div>
                    <p class="ind-pwd" id="ind-pwd-guide" style="display: none;"></p>
                </div>
                <button class="btn-register" id="btn-register" action="">회원가입</button>
            </form>
        </div>
        <script>
            // 닉네임 체크 로직 관련... 가이드 문구
            let nickGuideArr = ["사용자 닉네임은 2글자 이상 16자 이하여야 합니다.", "는 사용할 수 있습니다.", "는 사용할 수 없습니다."]

            // 닉네임 가이드 요소
            let indNickName = document.getElementById("ind-nick-name");
            // 닉네임 입력 요소
            let rIdInput = document.getElementById("r-id-input");
            // 사용 가능한지 체크하는 변수
            let isValidName = false;                                	

            rIdInput.addEventListener("keyup", (e) => {
                e.preventDefault();
                let qUserid = e.target.value;
                // console.log("id >> ", qUserid);
                if (qUserid.length == 0) {
                    indNickName.style.display = "none";
                } else {
                    indNickName.style.display = "block";
                    if (qUserid.length < 2 || qUserid.length > 16) {
                        indNickName.textContent = nickGuideArr[0];
                        indNickName
                            .classList
                            .add("text-red");
                        indNickName
                            .classList
                            .remove("text-green");
                    } else {
                        fetch("${root}/user/idcheck/" + qUserid)
                            .then(response => response.text())
                            .then(data => {
                                console.log(data);
                                if (data === "true") {
                                   	isValidName = true;
                                    indNickName.textContent = qUserid + nickGuideArr[1];
                                    indNickName
                                        .classList
                                        .add("text-green");

                                    indNickName
                                        .classList
                                        .remove("text-red");

                                } else {
                                	isValidName = false;
                                    indNickName.textContent = qUserid + nickGuideArr[2];
                                    indNickName
                                        .classList
                                        .add("text-red");
                                    indNickName
                                        .classList
                                        .remove("text-green");

                                }
                            });
                    }
                }
            })

            // 비밀번호 일치 체크 로직 관련..
            let msgArr = ["비밀번호가 일치하지 않습니다.", "비밀번호가 일치합니다."]
            // 비밀번호
            let rPwdInput = document.getElementById("r-pwd-input");
            // 비밀번호 체크
            let rPwdCheck = document.getElementById("r-pwd-check");
            // 비밀번호 가이드 요소
            let indPwdGuide = document.getElementById("ind-pwd-guide");

            rPwdCheck.addEventListener("keyup", (e) => {
                e.preventDefault();
                if (rPwdInput.value.length > 0 && rPwdCheck.value.length > 0) {
                    indPwdGuide.style.display = "block";
                    let flag = rPwdInput.value === rPwdCheck.value;
                    if (flag) {
                        indPwdGuide.textContent = msgArr[1];
                        indPwdGuide
                            .classList
                            .add("text-green");
                        indPwdGuide
                            .classList
                            .remove("text-red");
                    } else {
                        indPwdGuide.textContent = msgArr[0];
                        indPwdGuide
                            .classList
                            .add("text-red");
                        indPwdGuide
                            .classList
                            .remove("text-green");
                    }
                } else {
                    indPwdGuide.style.display = "none";
                }
            })
            
			// 회원가입 요청
            // 회원가입 폼
            let formRegister = document.getElementById("form-register");
            // 회원가입 버튼
            let btnRegister = document.getElementById("btn-register");
            btnRegister.addEventListener("click", (e) => {
                e.preventDefault();
                // 닉네임 유효 체크
                if(!isValidName){
                    alert("사용할 수 없는 닉네임입니다.")
                    return;
                }else if(!(rPwdInput.value === rPwdCheck.value)){ // 비밀번호
                    alert("비밀번호가 일치하지 않습니다.");
                    return;
                }else {
                    formRegister.setAttribute("action", "${root}/user/register")
                    formRegister.submit();
                }
            });            
        </script>
        <script src="${root}/assets/js/register.js"></script>
    </body>
</html>