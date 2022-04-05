var main = {
    init : function() {
        var _this = this;

        document.getElementById('btn-login').onclick = function() {
            _this.login();
        }
    },

    login : async function() {

        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;

        const url = "/login";
        const data = {
            account:username,
            passwd:password
        }

        const config = {
            method : "POST",
            headers : {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify(data)
        }

        const response = await fetch(url,config);

        if(response.status == 404 || response.status == 401) {
            let errorContent = document.getElementById('error');
            errorContent.innerText = "유저정보를 확인해 주세요";
            errorContent.style.color = 'red';
        } else if(response.status == 200) {

            const token = response.headers.get("Authorization");
            localStorage.setItem('token', token);

            alert("login success");
            window.location.href="/";
        }
        else {
            console.log("error!");
        }
    }
}

main.init();