<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
<c:if test="${cookie.user_name ne null}">
	<c:set var="userName" value="${cookie.user_name.value}"/>
</c:if>
   <link rel="stylesheet" href="${root}/assets/css/profile.css"/>   
   <title>프로필 페이지</title>
 </head>
 <body>
        <div class="profile-body">
            <div class="d-user-profile">
                <div class="close-layer">
                    <img src="${root}/assets/img/ic_close.svg">
                </div>            
                <p class="t-user-profile">사용자 프로필</p>
                <div class="u-id-container">
                    <img src="${root}/assets/img/icon_user.svg" alt="icon-user" class="u-icon-user"/>
                    <input type="text" class="u-id-input" value="${userName}" readonly="readonly"/>
                </div>
                <div class="frame-edit-pwd">
                    <p id="p-change-pwd">비밀번호 변경</p>
                </div>
                <div class="frame-profile-btns">
                    <button class="btn-logout" id="btn-logout">로그아웃</button>
                    <button class="btn-withdrawal" id="btn-withdrawal">회원탈퇴</button>
                </div>
            </div>
            <!-- 회원탈퇴 -->
            <div class="dark-filter" style="display: none;"></div>
            <div
                class="modal-container"
                style="display: none;"
                id="show-withrawal-container">
                <div class="modal-withdrawal">
                    <div class="modal-conent"></div>
                    <div class="frame-btns">
                        <button class="btn-cancel" id="w-btn-cancel">취소</button>
                        <button class="btn-do-withrawal" id="btn-do-withrawal">회원탈퇴</button>
                    </div>
                </div>
            </div>
            <!-- 비밀번호 변경 -->
            <div class="modal-container" style="display: none;" id="change-pwd-container">
                <form id="form-change-pwd" class="form-change-pwd" method="POST" action="">
                    <p>비밀번호 변경</p>
                    <p>현재 사용중인 비밀번호를 입력해주세요</p>
                    <div class="d-input-pwd">
                        <img src="${root}/assets/img/icon_lock.svg" alt="icon-lock" class="u-icon-lock"/>
                        <input type="password" class="u-input-pwd" id="current-user-pwd" name="currentPassword" placeholder="현재 비밀번호를 입력해주세요."/>
                    </div>
                    <p>새로 변경하실 비밀번호를 입력해주세요.</p>
                    <div class="d-input-pwd">
                        <img src="${root}/assets/img/icon_lock.svg" alt="icon-lock" class="u-icon-lock"/>
                        <input type="password" class="u-input-pwd" id="new-change-pwd" name="newPassword" placeholder="비밀번호를 입력해주세요."/>
                    </div>
                    <div class="d-input-pwd-container">
                        <div class="d-input-pwd">
                            <img src="${root}/assets/img/icon_lock.svg" alt="icon-lock" class="u-icon-lock"/>
                           <input type="password" class="u-input-pwd" id="check-change-pwd" placeholder="변경하실 비밀번호를 입력해주세요."/>
                         </div>
                        <p id="check-pwd-guide" style="display: none;"></p>
                    </div>
                    <div class="frame-pwd-changes">
                        <button class="btn-cancel" id="pwd-btn-cancel">취소</button>
                        <button class="btn-change-pwd" id="btn-change-pwd">변경하기</button>
                    </div>
                    <input type="text" style="display: none;" name="userName" value="${userName}"/>                    
                </form>
            </div>
            <form id="form-logout" method="GET" accept="" style="display: none;">
            </form>            
            <form id="form-withrawal" method="POST" style="display: none;">
                <input type="text" name="userName" value="${userName}" style="display: none;"/>
            </form>
        </div>       
        <script src="${root}/assets/js/profile.js"></script>
        <script>
        // 회원탈퇴 관련
        // 회원탈퇴 버튼
        let btnDoWithrawal = document.getElementById("btn-do-withrawal");
        // 회원탈퇴 폼
        let formWithrawal = document.getElementById("form-withrawal");
        btnDoWithrawal.addEventListener("click", (e) => {
            e.preventDefault();
            formWithrawal.setAttribute("action", "${root}/user/withrawal");
            formWithrawal.submit();
        });        
        
 	   	// 로그아웃 관련
        let btnLogout = document.getElementById("btn-logout");
        let formLogout = document.getElementById("form-logout");
        // 로그아웃
        btnLogout.addEventListener("click", (e)=> {
            e.preventDefault();
            formLogout.setAttribute("action", "${root}/user/logout");
            formLogout.submit();
        });
        // 비밀번호 변경 관련
        // 비밀번호 변경 버튼
        let btnChangePwd = document.getElementById("btn-change-pwd");                
        // 비밀번호 변경 폼
        let formChangePwd = document.getElementById("form-change-pwd");
        // 기존 비밀번호
        let inputCurrent = document.getElementById("current-user-pwd");
        // 비밀번호 입력
        let inputPwd = document.getElementById("new-change-pwd");
        // 비밀번호 체크
        let checkPwd = document.getElementById("check-change-pwd");
        // 가이드 문구
        let checkPwdGuide = document.getElementById("check-pwd-guide");
        let msgArr = [
            "비밀번호가 일치하지 않습니다.",            
            "비밀번호가 일치합니다."
        ]
        checkPwd.addEventListener("keyup", (e) => {
            e.preventDefault();
            if(inputPwd.value.length > 0 && checkPwd.value.length > 0){
                checkPwdGuide.style.display = "block";
                let flag = inputPwd.value === checkPwd.value;
                if(flag){
                    checkPwdGuide.textContent = msgArr[1];
                    checkPwdGuide.classList.add("text-green");
                    checkPwdGuide.classList.remove("text-red");
                }else {
                    checkPwdGuide.textContent = msgArr[0];
                    checkPwdGuide.classList.add("text-red");
                    checkPwdGuide.classList.remove("text-green");
                }
            }else {
                checkPwdGuide.style.display = "none";
            }
        });
        // 비밀번호 변경        
        btnChangePwd.addEventListener("click", (e) => {
            e.preventDefault();
            // 현재 비밀번호 체크
            if(!inputCurrent.value){
                alert("현재 비밀번호를 입력해주세요");
                return;
            }else if(!(inputPwd.value === checkPwd.value)){
                alert("입력하신 비밀번호가 일치하지 않습니다.")
                return;
            }else{
                formChangePwd.setAttribute("action", "${root}/user/changepwd")
                formChangePwd.submit();
            }
        });
        </script>
    </body>
</html>