window.addEventListener("DOMContentLoaded", function () {
    const userDetailJon = localStorage.getItem("userDetail");
    if (!userDetailJon) {
        return;
    }

    const userDetailObj = JSON.parse(userDetailJon);

    document.getElementById("profile_settings_name").textContent = userDetailObj.name;
    document.getElementById("profile_settings_username").textContent = userDetailObj.username;
    if(userDetailObj.photo){
        document.getElementById("profile_settings_photo_id").src = userDetailObj.photo.url;
    }
});

function goToProfileDetailEditPage(){
    window.location.href = "./profile-settings-edit.html";
}