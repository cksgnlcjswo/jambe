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
        console.log(response);
        if(response.ok) {

            window.location.href="/";
        } else {
            console.log("error!");
        }
    }
}

main.init();