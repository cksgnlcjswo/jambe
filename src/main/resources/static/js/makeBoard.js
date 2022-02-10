var main = {
    init : () => {

        var _this = this;
        document.getElementById("btn-create").onclick = () => {
            _this.create();
        }
    },

    create: async () => {
        let category = document.getElementById('category').value;
        const url = /api/v1/board;

        const data = {
            category:category
        }

        const config = {
             method: 'POSt',
             headers: {
                 'Content-Type' : 'application/json',
             },
             body: JSON.stringify(data)
        }

        const response = await fetch(url,config);

        if(response.status==201) {
             alert('게시판이 등록되었습니다.');
             window.location.href="/";
        } else {
             console.log("error!");
        }
    }
}

main.init()