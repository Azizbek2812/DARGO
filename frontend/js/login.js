function login() {
    const usernameInput = document.getElementById("username");
    const username = usernameInput.value;

    const passwordInput = document.getElementById("password");
    const password = passwordInput.value;

    let hasError = false;
    if (username === null || username === 'undefined' || username.length === 0) {
        document.getElementById("usernameErrorSpan").style.display = 'block';
        hasError = true;
    }

    if (password === null || password === 'undefined' || password.length === 0) {
        passwordInput.nextElementSibling.style.display = 'block';
        hasError = true;
    }

    if (hasError) {
        return;
    }
    document.getElementById("usernameErrorSpan").style.display = 'none';
    passwordInput.nextElementSibling.style.display = 'none';

    const body = {
        "username": username,
        "password": password
    }

    const lang = document.getElementById("current-lang").textContent;
    fetch("http://localhost:9090/auth/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept-Language':lang,
        },
        body: JSON.stringify(body)
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return Promise.reject(response.text());
        }
    }).then(responseData => {
        const data = responseData.data;
        console.log(data.jwt);
        localStorage.setItem("userDetail", JSON.stringify(data));
        localStorage.setItem("jwtToken", data.jwt);
        usernameInput.value = '';
        passwordInput.value = '';
        window.location.href = "./profile-post-list.html";
        // .then(data => {
        // localStorage.setItem("userDetail" , JSON.stringify(data));
        // localStorage.setItem("jwtToken" , data.jwt);
        // usernameInput.value = '';
        // passwordInput.value = '';
        // console.log(data.jwt)
    }).catch(error => {
        error.then(errMessage => {
            alert(errMessage)
        })
    })
}