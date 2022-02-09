var main = {
    init : function() {
        var _this = this;

        document.getElementById('btn-update').onclick = () => {
            _this.update();
        }

        document.getElementById('btn-delete').onclick = () => {
            _this.delete();
        }
    },

    update : async () => {
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

    delete : async () => {
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