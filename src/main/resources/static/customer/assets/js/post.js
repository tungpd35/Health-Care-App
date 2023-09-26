document.getElementById("fill-by-service").innerText = document.querySelector(".option.selected.focus").innerText
document.getElementById("fil-by-city").innerText = document.querySelector("#select-location .option.selected.focus").innerText
document.querySelectorAll(".page-link").forEach(function(btn) {
    btn.addEventListener("click", function () {
        var url = window.location.href
        if(url.indexOf("/new") != -1 || url.indexOf("/popular") != -1) {
            window.location.href = url.substring(0, url.indexOf("/page") + 5) + "/" + this.innerText + url.substring(url.lastIndexOf("/"))
        } else {
            window.location.href = url.substring(0, url.indexOf("/page") + 5) + "/" + this.innerText
        }

    })

})
document.querySelectorAll(".select-job-items .nice-select .list .option").forEach(function(btn) {
    btn.addEventListener("click", function() {
        if(this.innerText == "Phổ Biến") {
            var url = window.location.href
            if(url.indexOf("/new") != -1) {
                window.location.href = url.substring(0, url.indexOf("/new")) + "/popular"
            } else if (url.indexOf("/popular") == -1){
                window.location.href += "/popular"
            }
        }else if(this.innerText == "Mới Nhất") {
            var url = window.location.href
            if(url.indexOf("/popular") != -1) {
                window.location.href = url.substring(0, url.indexOf("/popular")) + "/new"
            }
            else if (url.indexOf("/new") == -1){
                window.location.href += "/new"
            }
        }
    })
})
document.querySelectorAll("#serviceId .option").forEach(function(btn) {
    btn.addEventListener("click", function() {
        var url = window.location.href
        if(this.value == 0) {
            if(url.indexOf("/city") != -1) {
                window.location.href = url.substring(0, url.indexOf("/post") + 5) + url.substring(url.indexOf("/city"))
            }
            else {
                window.location.href = url.substring(0, url.indexOf("/post") + 5) + "/page/1"
            }
        } else if(url.indexOf("/city") == -1)  {
            window.location.href = url.substring(0, url.indexOf("/post") + 5) +  "/service/" + this.value + "/page/1"

        } else if(url.indexOf("/city") != -1) {
                window.location.href = url.substring(0,url.indexOf("/post") + 5) + "/service/" + this.value + url.substring(url.indexOf("/city"))
        }
    })
})
document.querySelectorAll("#select-location .option").forEach(function(btn) {
    btn.addEventListener("click", function() {
        var url = window.location.href
        if(this.value == 0) {
            if(url.indexOf("/service") != -1) {
                window.location.href = url.substring(0, url.indexOf("/city")) + "/page/1"
            }
            else {
                window.location.href = url.substring(0, url.indexOf("/post") +5) + "/page/1"
            }
        }
        else if(url.indexOf("/service") == -1) {
            window.location.href = url.substring(0, url.indexOf("/post") + 5) +  "/city/" + this.innerText + "/page/1"
        }
        else if(url.indexOf("/service") != -1) {
            if(url.indexOf("/city") == -1) {
                window.location.href = url.substring(0,url.indexOf("/page")) + "/city/" + this.innerText + "/page/1"
            }
            else {
                window.location.href = url.substring(0,url.indexOf("/city")) + "/city/" + this.innerText + "/page/1"
            }
        }
    })
})
if (getCookie("token") != "") {
    document.querySelector(".login-btn-nav").style.display = "none";
    document.querySelector(".nav-right").style.display = "normal";
    document.querySelector(".menu-wrapper").classList.remove("justify-content-end")
}else {
    document.querySelector(".login-btn-nav").style.display = "normal";
    document.querySelector(".nav-right").style.display = "none";
    document.querySelector(".menu-wrapper").classList.add("justify-content-end")
}
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
