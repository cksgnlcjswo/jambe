var main = {
    init : function() {
        var _this = this;

        document.getElementById('btn-board').onclick = function() {
            const url = "/api/v1/boards";
            _this.getRequest(url);
        }

        document.getElementById('btn-board-create').onclick = function() {
            const url = "/board";
            _this.getRequest(url);
        }
    },

    getRequest : async function(url) {
        const token = localStorage.getItem('token') || '';

        const config = {
            method : "GET",
            headers : {
                "Authorization" : 'Bearer '+token,
            },

        }
        const response = await fetch(url,config);

        if(response.ok) {
            window.location.href = ""
        }
    }
}

main.init();