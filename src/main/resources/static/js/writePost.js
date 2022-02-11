var main = {
    init : function() {
        var _this = this;

        document.getElementById('register').onclick = function() {
            _this.save();
        }
    },

    save : async function() {
        let title = document.getElementById('title').value;
        let content = document.getElementById('content').value;
        let board = document.getElementById('board').value;
        let member = document.getElementById('member').value;

        const url = "/api/v1/board/post";

        const data = {
            title:title,
            content:content,
            board: board,
            member: member
        }

        const config = {
            method : "POST",
            headers : {
                "Content-Type" : "application/json",
            },
            body: JSON.stringify(data)
        }

        const response = await fetch(url,config);

        if(response.status===201) {
            alert("글이 등록되었습니다.");
            window.history.back();
        } else {
            console.log("error!");
        }
    },
}

main.init();