document.querySelector(".submit").addEventListener("click", function() {
    var name = document.getElementById("name").value
    var email = document.getElementById("email").value
    var password = document.getElementById("password").value
    var repassword = document.getElementById("repassword").value
    var val = true;
    if(name == "") {
        document.querySelector(".name-mess").style.display = "block"
        val = false
    } else {
        document.querySelector(".name-mess").style.display = "none"
    }
    if(email == "") {
        document.querySelector(".email-mess").style.display = "block"
        document.querySelector(".email-mess").innerText = "Không được để trống"
        val = false
    }else {
        console.log(validateEmail(email))
        if(validateEmail(email) == false) {
            document.querySelector(".email-mess").style.display = "block"
            document.querySelector(".email-mess").innerText = "Email không hợp lệ"
            val = false
        } else {
            document.querySelector(".email-mess").style.display = "none"
        }
    }
    if(password == "") {
        document.querySelector(".password-mess").style.display = "block"
        document.querySelector(".password-mess").innerText = "Không được để trống"
        val = false
    } else {
        if(validatePassword(password) == false) {
            document.querySelector(".password-mess").style.display = "block"
            document.querySelector(".password-mess").innerText = "Mật khẩu phải >= 6 ký tự"
            val = false
        }
        else {
            document.querySelector(".password-mess").style.display = "none"
        }
    }
    if(password != repassword) {
        document.querySelector(".repassword-mess").style.display = "block"
        document.querySelector(".repassword-mess").innerText = "Mật khẩu không khớp"
        val = false
    } else {
        document.querySelector(".repassword-mess").style.display = "none"
    }
    if(val == true) {
        fetch("http://localhost:8080/staff/register", {
            method: 'POST',
            body: JSON.stringify({"fullName": name,"email":email, "password": password}),
            headers:{
                'Content-Type': 'application/json'
            }
        }).then(function(response) {
            if(response.status == 200) {
                window.location.href = "/register/done"
            }else {
                document.querySelector(".email-mess").style.display = "block"
                document.querySelector(".email-mess").innerText = "Email đã tồn tại"
            }
        })
    }
})
function validateEmail(email) {
    var re = /\S+@\S+\.\S+/;
    return re.test(email);
}
function validatePassword(password) {
    var re = /^.{6,}$/
    return re.test(password)
}
document.getElementById("name").addEventListener("keydown", function() {
    document.querySelector(".name-mess").style.display = "none"
})
document.getElementById("email").addEventListener("keydown", function() {
    document.querySelector(".email-mess").style.display = "none"
})
document.getElementById("password").addEventListener("keydown", function() {
    document.querySelector(".password-mess").style.display = "none"
})
document.getElementById("repassword").addEventListener("keydown", function() {
    document.querySelector(".repassword-mess").style.display = "none"
})