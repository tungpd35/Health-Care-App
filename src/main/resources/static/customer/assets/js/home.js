
if (getCookie("token") != "") {
    document.querySelector(".login-btn-nav").style.display = "none";
    document.querySelector(".nav-right").style.display = "normal";
    document.querySelector(".menu-wrapper").classList.remove("justify-content-end")
}else {
    document.querySelector(".login-btn-nav").style.display = "normal";
    document.querySelector(".nav-right").style.display = "none";
    document.querySelector(".menu-wrapper").classList.add("justify-content-end")
}
document.querySelector(".search-form a").addEventListener("click", function() {
    this.href = "http://localhost:8080/post/page/1/search/"+ document.querySelector(".input-form input").value
})
document.getElementById("profile").addEventListener("click", function() {
    window.location.href = "/profile"
})
document.getElementById("request").addEventListener("click", function() {
    window.location.href = "/profile"
})
document.getElementById("changepassword").addEventListener("click", function() {
    window.location.href = "/change-password"
})
document.getElementById("logout").addEventListener("click", function() {
    document.cookie = 'token' + '=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    window.location.href = "/"
})
document.querySelector(".icon-user").addEventListener("click",function() {
    if(document.querySelector(".nav-user-menu").style.display == "none") {
        document.querySelector(".nav-user-menu").style.display = "block"
    } else {
        document.querySelector(".nav-user-menu").style.display = "none"
    }

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