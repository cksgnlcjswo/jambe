var main = {
    init : function() {
        var _this = this;

        document.getElementById('btn-create').onclick = function() {
            _this.save();
        }
    },

    save : async function() {

        let account = document.getElementById('account').value;
        let name = document.getElementById('name').value;
        let nickname = document.getElementById('nickname').value;
        let email = document.getElementById('email').value;
        let passwd = document.getElementById('passwd').value;

        const url = "/user/signup";
        const data = {
            account : account,
            name : name,
            nickname : nickname,
            email : email,
            passwd : passwd
        }

        const config = {
            method : "POST",
            headers : {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(data)
        }

        const response = await fetch(url,config);

        if(response.ok) {
            alert("회원등록 되었습니다.");
            window.location.href="/auth/login";
        } else {
            console.log("error!");
        }
    }
}

main.init();