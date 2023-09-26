document.getElementById("btn-update").addEventListener("click", function() {
    fetch("http://localhost:8080/api/customer/changePassword", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + getCookie("token")
        },
        body: JSON.stringify({"oldPassword": document.getElementById("old-password").value, "newPassword": document.getElementById("new-password").value, "newPasswordRepeat": document.getElementById("password-repeat").value})
    }).then(function(response){
        return response.text()
    }).then(function (result) {
        if(result == "Password incorrect") {
            document.querySelector(".password-mess").style.display ="block"
        }
        if(result == "Password not match") {
            document.querySelector(".password-repeat").style.display = "block"
        }
        if(result == "Password is too sort") {
            document.querySelector(".newpassword").style.display = "block"
        }
        if(result == "ok") {
            document.querySelector("#old-password").value = ""
            document.querySelector("#new-password").value = ""
            document.querySelector("#password-repeat").value = ""
            document.querySelector(".toast").classList.add("active")
            document.querySelector(".progress").classList.add("active")
            setTimeout(function () {
                document.querySelector(".toast").classList.remove("active")
                document.querySelector(".progress").classList.remove("active")
            },5000)
        }
    })
})
document.querySelector("#old-password").addEventListener("keydown", function () {
    document.querySelector(".password-mess").style.display = "none"
})
document.querySelector("#new-password").addEventListener("keydown", function () {
    document.querySelector(".newpassword").style.display = "none"
})
document.querySelector("#password-repeat").addEventListener("keydown", function () {
    document.querySelector(".password-repeat").style.display = "none"
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