document.getElementById("delete-btn").addEventListener("click", function() {
   document.getElementById("accept-btn").value = this.value
})
document.getElementById("accept-btn").addEventListener("click", function () {
    fetch("http://localhost:8080/api/deletePost/" + this.value, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + getCookie("token")
        }
    }).then(function (response) {
        if(response.status == 200) {
            location.reload()
        }else {
            window.alert("Lỗi")
        }
    })
})
document.getElementById("logout").addEventListener("click", function() {
    document.cookie = 'token' + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
})
function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}