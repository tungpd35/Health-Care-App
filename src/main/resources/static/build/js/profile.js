$(document).on("change", ".uploadProfileInput", function () {
    var triggerInput = this;
    var currentImg = $(this).closest(".pic-holder").find(".pic").attr("src");
    var holder = $(this).closest(".pic-holder");
    var wrapper = $(this).closest(".profile-pic-wrapper");
    $(wrapper).find('[role="alert"]').remove();
    triggerInput.blur();
    var files = !!this.files ? this.files : [];
    if (!files.length || !window.FileReader) {
        return;
    }
    if (/^image/.test(files[0].type)) {
        // only image file
        var reader = new FileReader(); // instance of the FileReader
        reader.readAsDataURL(files[0]); // read the local file

        reader.onload = function () {
            $(holder).addClass("uploadInProgress");
            $(holder).find(".pic").attr("src", this.result);
            $(document).find(".pic").attr("src", this.result)
            $(document).find(".user-profile img").attr("src", this.result)
            $(holder).append(
                '<div class="upload-loader"><div class="spinner-border text-primary" role="status"><span class="sr-only">Loading...</span></div></div>'
            );
            var formData = new FormData();
            var fileInput = document.getElementById("newProfilePhoto")
            console.log(fileInput)// Đây là input chứa file
            if(fileInput.files[0]) {
                formData.append('image', fileInput.files[0]);
                var fileInput = document.getElementById("newProfilePhoto");
                fetch("http://localhost:8080/api/staff/updateAvatar", {
                    method: "POST",
                    headers: {
                        'Authorization': "Bearer " + getCookie("token")
                    },
                    body: formData
                }).then(function(response) {
                    if(response.status != 200) {
                        console.log("Cập nhật thất bại!")
                    }
                })
            }
            // Dummy timeout; call API or AJAX below
            setTimeout(() => {
                $(holder).removeClass("uploadInProgress");
                $(holder).find(".upload-loader").remove();
                // If upload successful
                if (Math.random() < 0.9) {
                    $(wrapper).append(
                        '<div class="snackbar show" role="alert"><i class="fa fa-check-circle text-success"></i> Cập nhật ảnh đại diện thành công</div>'
                    );

                    // Clear input after upload
                    $(triggerInput).val("");

                    setTimeout(() => {
                        $(wrapper).find('[role="alert"]').remove();
                    }, 3000);
                } else {
                    $(holder).find(".pic").attr("src", currentImg);
                    $(wrapper).append(
                        '<div class="snackbar show" role="alert"><i class="fa fa-times-circle text-danger"></i> Có lỗi sảy ra, vui lòng thử lại</div>'
                    );

                    // Clear input after upload
                    $(triggerInput).val("");
                    setTimeout(() => {
                        $(wrapper).find('[role="alert"]').remove();
                    }, 3000);
                }
            }, 1500);
        };
        console.log(document.getElementById("newProfilePhoto").files[0])
    } else {
        $(wrapper).append(
            '<div class="alert alert-danger d-inline-block p-2 small" role="alert">Please choose the valid image.</div>'
        );
        setTimeout(() => {
            $(wrapper).find('role="alert"').remove();
        }, 3000);
    }
});

document.querySelector(".btn.btn-primary").addEventListener("click", function() {
    var name = document.querySelector("#first-name").value
    var gender = document.querySelector(".checked input").value
    var date = document.querySelector("#birthday").value
    var phonenumber = document.querySelector("#phonenumber").value
    var des = document.querySelector("#editor-one").innerHTML
    console.log(city[city.selectedIndex].text)
    fetch("http://localhost:8080/api/staff/updateInfo", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': "Bearer " + getCookie("token")
        },
        body: JSON.stringify({
            "name": name,
            "gender": gender,
            "date": date,
            "address": {
                "city": city[city.selectedIndex].text,
                "district": district[district.selectedIndex].text,
                "ward": ward[ward.selectedIndex].text
            },
            "phoneNumber": phonenumber,
            "description": des
        })
    }).then(function(response) {
        if(response.status != 200) {
            window.alert("Cập nhật thất bại")
        } else {
            window.alert("Cập nhật thông tin thành công!")
        }
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