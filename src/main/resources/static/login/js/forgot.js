document.querySelector(".submit").addEventListener("click", function() {
    var email = document.getElementById("email").value
    console.log(email)
    this.setAttribute('disabled','')
    fetch("http://localhost:8080/forgot-password?email="+email ,{
        method: "PUT",
        body: JSON.stringify(),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(function(response) {
        if(response.status != 200) {
            document.querySelector(".forgot-mess").style.display = "block"
        } else {
            window.location.href = "/forgot-password/done"
        }
    })
})
document.getElementById("email").addEventListener("keydown", function() {
    document.querySelector(".forgot-mess").style.display = "none"
})