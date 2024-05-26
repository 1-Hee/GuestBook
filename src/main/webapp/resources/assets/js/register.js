let guideContainer = document.querySelector(".register-guide");
let guideArrays = [
    "1-hee’s DashBoard에 오신 것을 환영합니다.",
    "이곳은 누구나 편하게 하고싶은 말을 남길 수 있는 방명록 사이트 입니다.",
    "저희 사이트에서는 회원 정보 관리를 위하여 아래와 같이 최소한의 정보만을 수집하고 있습니다.",
    "현재 웹 사이트에서 수집하는 정보는 정보를 제공한 주체가 누구인지 ‘식별’할 수 없는 것을 원칙으로 하고 있습니다.",
    "따라서, 하기 양식에 따라 정보를 입력하실 때에는 민감한 정보가 포함되지 않도록 입력하실 것을 권장드립니다",
    "아래의 양식에 맞추어 정보를 입력하시면, 등록하신 정보로 로그인하실 수 있습니다.",
    "1-hee’s DashBoard에 오신 것을 진심으로 환영합니다.",
    "💻 개발자 연락처 : onehee9710@gmail.com"
];
for(let i = 0; i < guideArrays.length ; i ++){
    let elementP = document.createElement('p');
    elementP.textContent = guideArrays[i];
    guideContainer.appendChild(elementP);
}


let btnClose =  document.querySelector("#form-register > .close-layer > img");
btnClose.addEventListener("click", (e)=> {
    e.preventDefault();
    window.history.back();
})
