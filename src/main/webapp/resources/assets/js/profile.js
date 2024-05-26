window.onload = function () {
    let withdrawalContent = document.querySelector(
        ".modal-withdrawal > .modal-conent"
    );
    let modalContent = ["정말로 회원탈퇴를 진행하시겠습니까?", "회원탈퇴 후에는 복구할 수 없습니다.", "[회원 탈퇴]를 누르시면 탈퇴 처리됩니다."];

    for (let i = 0; i < modalContent.length; i++) {
        let elementP = document.createElement('p');
        elementP.textContent = modalContent[i];
        withdrawalContent.appendChild(elementP);
    }

    // 회원탈퇴 버튼
    let withrawalBtn = document.getElementById("btn-withdrawal");
    // 회원탈퇴 모달
    let widthrawalModal = document.getElementById("show-withrawal-container");
    // 비밀번호 변경
    let chagePwdBtn = document.getElementById("p-change-pwd");
    // 비밀번호 변경 모달
    let changwPwdModal = document.getElementById("change-pwd-container");
    // 배경
    let darkFilter = document.querySelector(".dark-filter");
    // 회원탈퇴 취소 버튼
    let wBtnCancel = document.getElementById("w-btn-cancel");
    // 비밀번호 변경 취소 버튼
    let pwdBtnCancel = document.getElementById("pwd-btn-cancel");

    let dispose = () => {
        widthrawalModal.style.display = "none";
        changwPwdModal.style.display = "none";
        darkFilter.style.display = "none";
    }

    // 회원탈퇴 버튼 리스너
    withrawalBtn.addEventListener("click", function () {
        if (widthrawalModal.style.display === "none") {
            widthrawalModal.style.display = "block";
            darkFilter.style.display = "block";
        }
    });
    // 비밀번호 변경 리스너
    chagePwdBtn.addEventListener("click", () => {
        if (changwPwdModal.style.display === "none") {
            changwPwdModal.style.display = "block";
            darkFilter.style.display = "block";

        }
    });

    // 뒷배경 리스너
    darkFilter.addEventListener("click", dispose);
    wBtnCancel.addEventListener("click", (e) => {
        e.preventDefault();
        dispose();
    });
    pwdBtnCancel.addEventListener("click", (e) => {
        e.preventDefault();
        dispose();
    });
    
    
    let btnClose = document.querySelector(".profile-body > .d-user-profile > .close-layer > img");
    btnClose.addEventListener("click", (e) => {
        e.preventDefault();
        window.history.back();
    })
}