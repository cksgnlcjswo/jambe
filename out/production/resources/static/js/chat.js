var main = {
    init: function() {
        var _this = this;
        var sockJs = new SockJS("/api/v1/chat");
        var stomp = Stomp.over(sockJs);

        document.getElementById('btn-send').onclick = function() {
            send();
        }

        stomp.connect({}, function() {
            console.log("STOMP connection");

            stomp.subscribe("/sub/chat/room/" + roomId, function(msg) {
                let data = JSON.parse(msg.body);

                let text = data.message;
                let writer = data.writer;

                 if(writer == username) {

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
            });

            stomp.send('/pub/chat/enter', {},JSON.stringify({
                roomId: roomId,
                writer: username
            }));
        });

        function send() {
            let text = document.getElementById("msg").value;

            const message = {
                roomId: roomId,
                writer:username,
                message: text
            }

            stomp.send('/pub/chat/message',{},JSON.stringify(message));
            document.getElementById("msg").value = "";
        }
    }
}

main.init();
