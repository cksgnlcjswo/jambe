var main = {
    init : function() {
        var _this = this;
        document.getElementById("btn-create").addEventListener("click", function() {
            _this.save();
        });
    },

    save : async function() {
        let category = document.getElementById('category').value;
        console.log("category is: ",category);
        let url = "/api/v1/board";

        const data = {
            category:category
        }

        const config = {
             method: 'POST',
             headers: {
                 'Content-Type' : 'application/json',
             },
             body: JSON.stringify(data)
        }

        const response = await fetch(url,config);

        console.log(response)
        if(response.ok) {
             console.log("?")
             alert('게시판이 등록되었습니다.');
             window.location.href="/";
        } else {
             console.log("error!");
        }
    }
};

main.init();