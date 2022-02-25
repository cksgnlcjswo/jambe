var main = {
    init: function() {
        var _this = this;
        var sockJs = new SockJS("http://localhost:8080/ws/api/v1/chat");

        document.getElementById('btn-send').onclick = function() {
            send();
        }

        sockJs.onmessage = onMessage;
        sockJs.onopen = onOpen;
        sockJs.onclose = onClose;

        function onMessage(event) {

            let data = JSON.parse(event.data);
            console.log(data);
            let id = data.id;
            let text = data.text;

            //현재 로그인한 사용자라면
            if(username == id) {

                let ret="<li class='clearfix'>";
                    ret += "<div class='message-data text-right'>";
                    ret += "<span class='message-data-time'>10:10 AM, Today</span>";
                    ret += "<img src='#' alt='avatar'></div>";
                    ret += "<div class='message other-message float-right'>" + text + "</div></li>";
                    $('#chat-area').append(ret);
            } else {

                let ret = "<li class='clearfix'>";
                    ret += "<div class='message-data'>";
                    ret += "<span class='message-data-time'></span></div>";
                    ret += "<div class='message my-message'>"+ text +"</div></li>";
                    $('#chat-area').append(ret);
            }
        }

        function onOpen(event) {

            const text = username + " 님이 입장하셨습니다.";
            const msg = {
                id: username,
                text: text
            }
            sockJs.send(JSON.stringify(msg));
        }

        function onClose(event) {

            const text = username + " 님이 퇴장하셨습니다.";

            const msg = {
                id: username,
                text: text
            }
            sockJs.send(JSON.stringify(msg));
        }

        function send() {
            let msg = document.getElementById("msg").value;

            const message = {
                id: username,
                text: msg
            }

            sockJs.send(JSON.stringify(message));
            document.getElementById("msg").value = "";
        }
    }
}

main.init();
