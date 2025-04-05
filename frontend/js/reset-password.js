function resetPassword() {
    const usernameInput = document.getElementById("username");
    const username = usernameInput.value;
    if (!username) {
        return;
    }

    const body = {
        "username": username
    }

    const lang = document.getElementById("current-lang").textContent;
}