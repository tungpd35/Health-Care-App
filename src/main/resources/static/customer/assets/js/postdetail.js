const button = document.querySelector("button"),
    toast = document.querySelector(".toast");
(closeIcon = document.querySelector(".close")),
    (progress = document.querySelector(".progress"));

let timer1, timer2;

button.addEventListener("click", () => {
    toast.classList.add("active");
    progress.classList.add("active");

    timer1 = setTimeout(() => {
        toast.classList.remove("active");
    }, 5000); //1s = 1000 milliseconds

    timer2 = setTimeout(() => {
        progress.classList.remove("active");
    }, 5300);
});

closeIcon.addEventListener("click", () => {
    toast.classList.remove("active");

    setTimeout(() => {
        progress.classList.remove("active");
    }, 300);

    clearTimeout(timer1);
    clearTimeout(timer2);
});
var url = window.location.href;

// Tìm vị trí của chuỗi "/post/" trong URL
var postIdStart = url.indexOf("/post/") + 6;

// Tìm vị trí của chuỗi "/detail" trong URL, bắt đầu từ vị trí postIdStart
var postIdEnd = url.indexOf("/detail", postIdStart);

// Trích xuất chuỗi ID từ vị trí postIdStart đến postIdEnd
var id = url.slice(postIdStart, postIdEnd);
console.log(id); // In ra ID lấy được từ URL
console.log(getCookie("token"))
document.getElementById("request-btn").addEventListener("click", function() {
    fetch("http://localhost:8080/api/sendRequest", {
        method: "POST",
        headers: {
            'ContentType': 'application/json',
            'Authorization': 'Bearer ' + getCookie("token"),
            'postId': id
        }
    }).then(function (response) {
        if(response.status == 200) {
            document.querySelector(".toast").classList.add("active")
            document.querySelector(".progress").classList.add("active")
            setTimeout(function () {
                document.querySelector(".toast").classList.remove("active")
                document.querySelector(".progress").classList.remove("active")
            },5000)
        } else {
            document.querySelector(".info-request").style.display = "block"
            document.querySelector(".blur").style.display = "block"
        }
    })
})
document.getElementById("guest").addEventListener("click", function () {
    var name = document.querySelector(".form-control.name").value
    var phone = document.querySelector(".form-control.phone").value
    if ( name == "" || phone == "") {
        window.alert("Vui lòng điền đẩy đủ thông tin!")
    } else {
        fetch("http://localhost:8080/api/guestRequest", {
            method: "POST",
            body: JSON.stringify({"fullName": name, "phone": phone}),
            headers: {
                'Content-Type': 'application/json',
                'postId': id
            }
        }).then(function(response) {
            if (response.status == 200) {
                var name = document.querySelector(".form-control.name").value
                var phone = document.querySelector(".form-control.phone").value
                document.querySelector(".info-request").style.display = "none"
                document.querySelector(".blur").style.display = "none"
                document.querySelector(".toast").classList.add("active")
                document.querySelector(".progress").classList.add("active")
                setTimeout(function () {
                    document.querySelector(".toast").classList.remove("active")
                    document.querySelector(".progress").classList.remove("active")
                },5000)
            }
        })
    }
})
document.querySelector(".x-icon").addEventListener("click", function() {
    document.querySelector(".info-request").style.display = "none"
    document.querySelector(".blur").style.display = "none"
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