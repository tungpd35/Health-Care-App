document.querySelector(".submit").addEventListener("click", function() {
    var password = document.getElementById("password").value
    var repassword = document.getElementById("repassword").value
    var val = true
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
        fetch(window.location.href + "/" + password, {
            method: "PUT",
            headers:{
                'Content-Type': 'application/json'
            }
        }).then(function(response) {
            if(response.status == 200) {
                window.location.href = "done";
            }
        })
    }
})
function validatePassword(password) {
    var re = /^.{6,}$/
    return re.test(password)
}
document.getElementById("password").addEventListener("keydown", function() {
    document.querySelector(".password-mess").style.display = "none"
})
document.getElementById("repassword").addEventListener("keydown", function() {
    document.querySelector(".repassword-mess").style.display = "none"
})