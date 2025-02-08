document.querySelector("send_code").addEventListener("click", function () {
    // 이메일 입력 창에서 이메일 주소를 가져온다.
    var email = document.querySelector("#email").value;
    // 이메일 주소가 비어있는지 확인한다.
    if (email === "") {
        alert("이메일을 입력해주세요.");
        return;
    }
    // 이메일 주소를 JSON 형태로 만든다.
    var data = {"email": email};
    // POST /email-authentication 요청을 보낸다.
    fetch("/api/v1/auth/email-authentication", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then((response) => response.json())
        .then((result) => {
            if(result.code === 0){
                console.log("err" ,result);
                // 응답 결과를 alert로 보여준다.
                alert(result.message);
            }
            if(result.code === 1){
                // 응답 결과를 alert로 보여준다.
                console.log("err" ,result);
                alert(result.message);
            }
        });
});