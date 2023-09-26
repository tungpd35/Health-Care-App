document.getElementById("post").addEventListener("click", function() {
    var title = document.getElementById("title").value
    var serviceId = document.getElementById("service").value
    var city = document.querySelector(".selecter-city").options[document.querySelector(".selecter-city").selectedIndex].text
    var district = document.querySelector(".selecter-district").options[document.querySelector(".selecter-district").selectedIndex].text
    var ward = document.querySelector(".selecter-ward").options[document.querySelector(".selecter-ward").selectedIndex].text
    var phoneNumber = document.getElementById("phonenumber").value
    var email = document.getElementById("email").value
    var addressDetail = document.getElementById("addressdetail").value
    var description = document.getElementById("editor-one").innerText
    var skills = document.getElementById("editor-two").innerText
    var education = document.getElementById("editor-three").innerText
    document.querySelector("#user-btn").addEventListener("click", function() {
        console.log("adsf")
        this.classList.add("show")
        document.querySelector(".dropdown-menu.dropdown-usermenu.pull-right").classList.add("show")
    })
    console.log(description + skills + education)
    fetch("http://localhost:8080/staff/newPost", {
        method: "POST",
        body: JSON.stringify({
            "title": title,
            "description": description,
            "skills": skills,
            "experience": education,
            "education": education,
            "phoneNumber": phoneNumber,
            "location": {"city": city,"district": district,"ward": ward,"detail":addressDetail},
            "email": email,
            "serviceId": serviceId
        }),
        headers:{
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + getCookie("token")
        }
    }).then(function(response) {
        if(response.status == 200) {
            window.alert("Đăng tin thành công")
        } else {
            window.alert("Đăng tin thất bại")
        }
    })
})
document.getElementById("notice").addEventListener("click", function () {
    document.querySelector(".badge.bg-green").style.display = "none"
    fetch("http://localhost:8080/api/staff/readNotice",{
        method: "GET",
        headers:{
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + getCookie("token")
        }
    }).then(function(response) {
        console.log(response.status)
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