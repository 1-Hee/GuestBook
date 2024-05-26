let deleteContent = document.querySelector(".modal-delete-article > .modal-conent");
let modalContent = ["정말로 게시글을 삭제하시겠습니까?", "삭제된 글을 복원이 어렵습니다.", "다시 한번 신중히 선택해주세요.😯", "[삭제]를 누르시면 삭제 처리가 완료됩니다."];

for (let i = 0; i < modalContent.length; i++) {
    let elementP = document.createElement('p');
    elementP.textContent = modalContent[i];
    deleteContent.appendChild(elementP);
}

// 휴지통 아이콘
let btnTransh = document.querySelector(
    ".article-layer > .title-layer > .ic-article-trash"
);
// 글 삭제 모달
let deleteModal = document.getElementById("d-modal-container");
// 취소 버튼
let wBtnCancel = document.getElementById("w-btn-cancel");
// 글 삭제 버튼
let btnDelete = document.getElementById("btn-delete-article");
// 배경
let darkFilter = document.querySelector(".dark-filter");

let dispose = () => {
    deleteModal.style.display = "none";
    darkFilter.style.display = "none";
}

if(btnTransh != null){
	btnTransh.addEventListener("click", function () {
	    if (deleteModal.style.display === "none") {
	        deleteModal.style.display = "block";
	        darkFilter.style.display = "block";
	    }
	});	
}

// 뒷배경 리스너
darkFilter.addEventListener("click", dispose);
wBtnCancel.addEventListener("click", dispose);