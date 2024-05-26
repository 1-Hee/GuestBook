// 취소 버튼
let formBtnCancel = document.querySelector(".btn-frames > button:first-child");
// 액션 버튼, 수정도 해당
// let formBtnAction = document.querySelector(".btn-frames > button:last-child");

if(formBtnCancel != null){
	formBtnCancel.addEventListener("click", (e) => {
	    e.preventDefault();
	})	
}

/*
if(formBtnAction != null){
	formBtnAction.addEventListener("click", (e) => {
	    e.preventDefault();
	});	
}
*/

let btnClose =  document.querySelector(".form-article> .article-layer > .close-layer > img");
if(btnClose != null){
	btnClose.addEventListener("click", (e)=> {
	    e.preventDefault();
	    window.history.back();
	})	
}


 // 링크 이동 메서드
var moveLink = (url) => {
    location.href = ''
    let homeLink = document.createElement('a');
    homeLink.href = url;
    homeLink.click();
}