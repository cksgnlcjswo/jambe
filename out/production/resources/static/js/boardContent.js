var main = {
    init : function() {
        var _this = this;

        for(let i=0;;++i) {
            let form = document.getElementById("btn-subComment"+i);
            if(form == null) break;
            document.getElementById("sub-comment-form"+i).style.display="none";
            form.onclick = function() {
                _this.toggle(i);
            }
        }

        document.getElementById('btn-update').onclick = function() {
            _this.update();
        }

        document.getElementById('btn-delete').onclick = function() {
            _this.delete();
        }

        document.getElementById('btn-commentCreate').onclick = function() {
            _this.commentCreate();
        }
    },

    toggle : function(i) {
        let form = document.getElementById("sub-comment-form"+i);
          if (form.style.display === "none") {
            form.style.display = "block";
          } else {
            form.style.display = "none";
          }
    },

    commentCreate : async function() {
        let comment = document.getElementById('comment').value;
        let post = document.getElementById('id').value;
        const url = "/api/v1/board/post/comment";

        const data = {
            content: comment,
            post: post
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
            alert('댓글이 등록되었습니다.')
            location.reload();
        } else {
            console.log("error")
        }
    },

    update : async function() {
        let title = document.getElementById('title').value;
        let content = document.getElementById('content').value;
        let id = document.getElementById('id').value;
        const url = '/api/v1/board/post/' + id;

        const data = {
            title: title,
            content: content
        };

        const config = {
             method: 'PUT',
             headers: {
                'Content-Type' : 'application/json',
             },
             body: JSON.stringify(data)
        };
        const response = await fetch(url,config);
        if(response.ok) {
            alert('글이 수정되었습니다.');
            window.history.back();
        } else {
            console.log("error!");
        }
    },

    delete : async function() {
        let id = document.getElementById('id').value;
        const url = '/api/v1/board/post/' + id;

        const config = {
            method: 'DELETE',
            headers: {
                'Content-Type' : 'application/json',
            },
        };

        const response = await fetch(url,config);

        if(response.ok) {
            alert('글이 삭제 되었습니다.')
            window.history.back();
        } else {
            console.log('error');
        }
    }
}

main.init();