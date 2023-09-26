document.getElementById("btn-profile").addEventListener("click", function() {
    var name = document.getElementById("fullname").value
    var phone = document.getElementById("phonenumber").value
    fetch("http://localhost:8080/api/customer/updateProfile", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + getCookie("token")
        },
        body: JSON.stringify({"fullName": name,"phoneNumber": phone})
    }).then(function (response) {
        if(response.status == 200) {
            document.querySelector(".toast").classList.add("active")
            document.querySelector(".progress").classList.add("active")
            setTimeout(function () {
                document.querySelector(".toast").classList.remove("active")
                document.querySelector(".progress").classList.remove("active")
            },5000)
        }
    })
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