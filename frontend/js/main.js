function dateFormat(dateSrt) {
    const date = new Date(dateSrt);
    return String(date.getDate()).padEnd(2, "0") + "."
        + String(date.getMonth() + 1).padStart(2, "0") + "."
        + date.getFullYear() + " "
        + String(date.getHours()).padStart(2, "0") + ":"
        + String(date.getMinutes()).padStart(2, "0");
}

function showPopup(message) {
    let container = document.getElementById("popup-container");
    console.log(container);
    // Create new popup element
    let popup = document.createElement("div");
    popup.classList.add("popup");
    popup.innerText = message;

    // Append popup to container
    container.appendChild(popup);

    // Remove popup after 3 seconds
    setTimeout(() => {
        popup.classList.add("hide"); // Fade out
        setTimeout(() => popup.remove(), 500); // Remove from DOM
    }, 3000);
}
