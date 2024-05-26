<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/commons/header.jsp" %>
   <link rel="stylesheet" href="${root}/assets/css/guestbook.css"/>       
   <title>방명록</title>
 </head>
<body>
    <div class="guest-book-body">
        <div class="top-bar">
            <img src="${root}/assets/img/fi-br-notebook.svg" alt="icon" class="top-icon"/>
            <p class="top-title">방명록</p>
            <c:if test="${empty userinfo}">
	            <button class="mv-login hover-mono30" id="mv-login">로그인</button>            
            </c:if>
            <c:if test="${!empty userinfo}">
	            <div class="btn-profile" id="mv-profile">
	                <img src="${root}/assets/img/icon_user.svg" alt="user-icon" class="icon-profile"/>
	            </div>            
            </c:if>
        </div>
        <div class="write-bar">
            <button class="btn-write hover-mono30" id="btn-mv-write">글쓰기</button>
            <div class="search-bar">
                <img src="${root}/assets/img/icon_search.svg" alt="icon-search" class="icon-search"/>
                <input type="text" class="input-search" placeholder="검색어:제목"/>
                <button class="btn-search hover-mono30">검색</button>
            </div>
        </div>
		<div class="d-guest-book-conatiner" id="d-guest-book-conatiner">
           <table>
               <thead>
                   <tr>
                       <th>제목</th>
                       <th>작성자</th>
                       <th>작성일시</th>
                   </tr>
               </thead>
               <tbody></tbody>
           </table>
        </div>     
		<div class="page-layer" id="page-layer">
			<ul>
			</ul>
		</div>           
    </div>
    <script>
    let articleAction = function (id) {
        location.href = "${root}/article?no=" + id;
    }
    // 아이템 렌더링...
    let tableBody = document.querySelector(
        "#d-guest-book-conatiner > table > tbody"
    );
    // 프로필 버튼
    let mvProfile = document.getElementById("mv-profile");
    // 로그인 버튼
    let mvLogin = document.getElementById("mv-login");
    //글쓰기 버튼
    let btnMvWrite = document.getElementById("btn-mv-write");

    if (mvProfile !== null) {
        mvProfile.addEventListener("click", (e) => {
            e.preventDefault();
            const url = "${root}/user/profile";
            moveLink(url);
        });
    }
    if (mvLogin !== null) {
        mvLogin.addEventListener("click", (e) => {
            e.preventDefault();
            const url = "${root}/user/login";
            moveLink(url);
        });
    }

    btnMvWrite.addEventListener("click", (e) => {
        e.preventDefault();
        const url = "${root}/write";
        moveLink(url);
    });

    const homeURL = '${root}/';
    // 홈화면 이동
    let elLogoImg = document.querySelector('.guest-book-body > .top-bar > img');
    let elLogoTitle = document.querySelector('.guest-book-body > .top-bar > p');
    elLogoImg.addEventListener("click", (e) => {
        moveLink(homeURL);
    });
    elLogoTitle.addEventListener("click", (e) => {
        moveLink(homeURL);
    });

    var pageNo = 1;
    var keyWord = '';
    var totalNavCnt = 1;
    var baseUrl = "${root}/list?pgno=" + pageNo + '&word=' + keyWord;
    // url setter
    let setBaseUrl = (pgNo = 1, word = '') => {
        pageNo = pgNo;
        keyWord = word;                    
        baseUrl = "${root}/list?pgno=" + pageNo + '&word=' + keyWord;
    }

    const config = {
        method: "GET", // *GET, POST, PUT, DELETE 등
        mode: "cors", // no-cors, *cors, same-origin
        cache: "no-cache"
    }

    // 이전 페이지
    let callPrev = function (e) {
        e.preventDefault();
        if (pageNo - 1 > 0) {
            pageNo--;
            setBaseUrl(pageNo, keyWord);
            loadCotnentList(pageNo);
        } else {
            alert('처음 페이지입니다');
        }
    }

    // 다음 페이지
    let callNext = function (e) {
        e.preventDefault
        if(pageNo + 1 <= totalNavCnt){
            pageNo++;
            setBaseUrl(pageNo, keyWord);
            loadCotnentList(pageNo);                        
        }else {
            alert('마지막 페이지입니다!');
        }                  
    }

    // 페이지 클릭
    let callPage = function (pgNo) {
        // e.preventDefault();
        pageNo = pgNo;
        setBaseUrl(pageNo, keyWord);
        loadCotnentList(pageNo);
    } 

    // 본문 리스트 세팅 함수
    const setCotentList = (list) => {
        tableBody.innerHTML = '';
        list.forEach(item => {
            let trElement = document.createElement('tr');
            let tdTitle = document.createElement('td');
            let tdAuthor = document.createElement('td');
            let tdDate = document.createElement('td');
            trElement.addEventListener("click", (e) => {
                articleAction(item.idx);
            });
            tdTitle.textContent = item.title;
            tdAuthor.textContent = item.author;
            // Date 객체로 변환
            const utcDate = new Date(item.createAt);
            // 한국 시간대(KST)로 포맷팅
            const kstDateStr = new Intl
                .DateTimeFormat('ko-KR', {
                    timeZone: 'Asia/Seoul',
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit',
                    hour: '2-digit',
                    minute: '2-digit',
                    second: '2-digit'
                })
                .format(utcDate); 
            

            let dateStr = kstDateStr.toLocaleUpperCase();                        
            tdDate.textContent = dateStr;
            trElement.appendChild(tdTitle);
            trElement.appendChild(tdAuthor);
            trElement.appendChild(tdDate);
            tableBody.appendChild(trElement);
        });
    }
    // 본문 페이지 네비게이션 세팅 함수
    const setPageNav = (data) => {
        // temp
        let pageLayerUl = document.querySelector("#page-layer > ul");
        pageLayerUl.innerHTML = '';
        if (data.navUnits.length > 0) {
            let leftImg = document.createElement('img');
            let rightImg = document.createElement('img');
            leftImg.src = '${root}/assets/img/ic_left.svg'
            rightImg.src = '${root}/assets/img/ic_right.svg'
            let arrowLeft = document.createElement('li');
            let arrowRight = document.createElement('li');
            arrowLeft.addEventListener("click", callPrev);
            arrowRight.addEventListener("click", callNext);
            arrowLeft.appendChild(leftImg);
            arrowRight.appendChild(rightImg);
            pageLayerUl.appendChild(arrowLeft);
            totalNavCnt = data.totalNavCnt
            // page set
            data.navUnits.forEach((item) => {
                let liUnit = document.createElement('li');
                let pUint = document.createElement('p');
                if (item == pageNo) {
                    liUnit
                        .classList
                        .add('nav-focus');
                }
                pUint.textContent = item;
                liUnit.appendChild(pUint);
                liUnit.addEventListener("click", function () {
                    callPage(item);
                });
                pageLayerUl.appendChild(liUnit);
            })
            pageLayerUl.appendChild(arrowRight);

        }
    }

    // 검색창 세팅 함수 검색 입력 창
    let searchForm = document.querySelector(
        ".write-bar > .search-bar > .input-search"
    );
    // 검색 버튼
    let btnSearch = document.querySelector(
        ".write-bar > .search-bar > .btn-search"
    )
    let loadCotnentList = (pgNo) => {
        fetch(baseUrl, config)
            .then(response => response.json())
            .then(data => {
                setCotentList(data.boardList);
                setPageNav(data.pgNav);
                // searchForm.value = data.keyword;
            });
    }
    window.onload = function() {
        loadCotnentList(pageNo);             
    }

    // 서치폼의 엔터 이벤트
    searchForm.addEventListener("keyup", function (e) {
        if (e.keyCode === 13) {
            // 엔터 키를 눌렀을 때 검색
            if (searchForm.value) {
                pageNo = 1;
                keyWord = searchForm.value;
                setBaseUrl(pageNo, keyWord)
                loadCotnentList(pageNo);
            }
        }
    })
    // 서치 폼의 빈 값 감지 리스너
    searchForm.addEventListener("input", function(event) {
        if(!searchForm.value){
            keyWord = '';
            pageNo = 1;
            setBaseUrl(pageNo, keyWord)
            loadCotnentList(pageNo);
        }
    });                
    // 검색 버튼 이벤트
    btnSearch.addEventListener("click", (e) => {
        e.preventDefault();
        if (searchForm.value) {
            pageNo = 1;
            keyWord = searchForm.value;
            setBaseUrl(pageNo, keyWord)
            loadCotnentList(pageNo);
        }
    });
    </script>
    <script src="${root}/assets/js/common-article.js"></script>
    <script src="${root}/assets/js/guestbook.js"></script>    
</body>
</html>