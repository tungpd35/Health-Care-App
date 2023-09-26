document.querySelector(".submit").addEventListener("click",function() {
    var email = document.getElementById("email").value
    var pass = document.getElementById("password").value
    console.log(email)
    console.log(pass)
    fetch("http://localhost:8080/login",
        {
            method: 'POST',
            body: JSON.stringify({"email":email, "password": pass}),
            headers:{
                'Content-Type': 'application/json'
            }
        }
    )
        .then(function(reponse) {
            if(reponse.status == 200) {
                window.location.href = "/";
            } else {
                document.querySelector(".mess-error").style.display = "block"
            }
        })

})
document.getElementById("email").addEventListener("keydown",function() {
    document.querySelector(".mess-error").style.display = "none"
})
document.getElementById("password").addEventListener("keydown",function() {
    document.querySelector(".mess-error").style.display = "none"
})