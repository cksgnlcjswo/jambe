var main = {
    init: function() {
        var _this = this;
        const webSocket = new WebSocket("ws://localhost:8080/api/v1/chat");
        var username = [[${#authentication.principal.username}]];

        document.getElementById('btn-send').onclick = function() {
            _this.send();
        }

        webSocket.onmessage = _this.onMessage;
        webSocket.onopen = _this.onOpen;
        webSocket.onclose = _this.onClose;
    },

    onMessage : function(event) {
        let data = JSON.parse(event.data);
        let id = data.id;
        let text = data.text;

        //현재 로그인한 사용자라면
        if(username == id) {
            let ret="<li class='clearfix'>";
                ret +=  "<div class='message-data text-right'>";
                ret +=  "<span class='message-data-time'>10:10 AM, Today</span>"
                ret += "<img src='#' alt='avatar'></div>"
                ret += "<div class='message other-message float-right'>" + text + "</div></li>"
                $('chat-area').append(ret);
        } else {
            let ret = "<li class='clearfix'>"
                ret += "<div class='message-data'>"
                ret += "<span class='message-data-time'></span></div>";
                ret += "<div class='message my-message'>"+ text +"</div></li>"
                $('chat-area').append(ret);
        }

    },

    onOpen : function(event) {
        const text = username + " 님이 입장하셨습니다.";
        const msg = {
            id: username,
            text: text
        }
        webSocket.send(JSON.stringify(msg));
    },

    onClose : function(event) {
        const text = username + " 님이 퇴장하셨습니다.";

        const msg = {
            id: username,
            text: text
        }
        webSocket.send(JSON.stringify(msg));
    },

    send: function() {
        let msg = document.getElementById("msg").value;
        console.log("msg: "+msg);
        webSocket.send(msg);
        document.getElementById("msg").innerText = "";
    }
}

main.init();