window.addEventListener("DOMContentLoaded", function () {
    const userDetailJon = localStorage.getItem("userDetail");
    if (!userDetailJon) {
        return;
    }

    // const userDetailObj = JSON.parse(userDetailJon);

    // document.getElementById("profile_settings_name").value = userDetailObj.name;
    // document.getElementById("profile_settings_username").value = userDetailObj.username;
    // if(userDetailObj.photo){
    //     document.getElementById("profile_settings_photo").src = userDetailObj.photo.url;
    // }
});

function profileDetailUpdate() {
    const name = document.getElementById("profile_settings_name").value
    if (!name) {
        alert("Enter the name input. Mazgimisiz!")
        return;
    }

    const body = {
        "name": name
    }
}

function profilePasswordUpdate() {
    const currentPswd = document.getElementById("profile_settings_current_pswd").value
    const newPswd = document.getElementById("profile_settings_new_pswd").value
    if (!currentPswd || !newPswd) {
        alert("Enter all inputs")
        return;
    }

    const body = {
        "currentPswd": currentPswd,
        "newPswd": newPswd
    }

    const jwt = localStorage.getItem('jwtToken');
    if (!jwt) {
        window.location.href = './login.html';
        return;
    }

    const lang = document.getElementById("current-lang").textContent;
}

function profileUserNameChange() {
    const username = document.getElementById("profile_settings_username").value
    if (!username) {
        alert("Enter all inputs")
        return;
    }
    const body = {
        "username": username
    }

    const jwt = localStorage.getItem('jwtToken');
    if (!jwt) {
        window.location.href = './login.html';
        return;
    }
}

function profileUserNameChangeConfirm() {
    const confirmCode = document.getElementById("profileUserNameChaneConfirmInputId").value
    if (!confirmCode) {
        alert("Enter all inputs")
        return;
    }
}

//------------ Change username confirm modal start ------------
const modal = document.getElementById('simpleModalId');

function openModal() {
    modal.style.display = 'block';
}

function closeModal() {
    modal.style.display = "none";
}

window.onclick = (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};

//------------ Change username confirm modal end ------------

// ------------ Image preview ------------
function previewImage(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function () {
        const img = document.getElementById('profile_settings_photo');
        img.src = reader.result;
    };

    if (file) {
        reader.readAsDataURL(file);
        document.getElementById('profile_settings_upload_img_btn_id').style.display = 'inline-block';
    }
}

// ------------ Image upload ------------
function uploadImage() {
    const fileInput = document.getElementById('imageUpload');
    const file = fileInput.files[0];
    if (file) {
        const formData = new FormData();
        formData.append('file', file);

        const jwt = localStorage.getItem('jwtToken');
        if (!jwt) {
            window.location.href = './login.html';
            return;
        }
        const lang = document.getElementById("current-lang").textContent;
        //
        // fetch('http://localhost:8080/attach/upload', {
        //     method: 'POST',
        //     headers: {
        //         'Accept-Language': lang,
        //         'Authorization': 'Bearer ' + jwt
        //     },
        //     body: formData
        // })
        //     .then(response => {
        //         if (!response.ok) {
        //             throw new Error('Network response was not ok');
        //         }
        //         return response.json();
        //     })
        //     .then(data => {
        //         console.log('Success:', data);
        //         if (data.id) {
        //             updateProfileImage(data.id); // profile update image
        //
        //             const userDetailJon = localStorage.getItem("userDetail");
        //             const userDetail = JSON.parse(userDetailJon);
        //             userDetail.photo = {};
        //             userDetail.photo.id = data.id;
        //             userDetail.photo.url = data.url;
        //             localStorage.setItem("userDetail", JSON.stringify(userDetail));
        //
        //             // document.getElementById("header_user_image_id").src =data.url;
        //         }
        //     })
        //     .catch(error => {
        //         console.error('Error:', error);
        //     });
    }
}

function updateProfileImage(photoId) {
    if (!photoId) {
        return;
    }

    const body = {
        "photoId": photoId
    }

    const jwt = localStorage.getItem('jwtToken');
    if (!jwt) {
        window.location.href = './login.html';
        return;
    }

    const lang = document.getElementById("current-lang").textContent;
}