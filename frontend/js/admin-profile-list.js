window.addEventListener("DOMContentLoaded", function () {
    getProfileList();
});
let currentPage = 1;
let pageSize = 10;

function getProfileList() {
    const jwt = localStorage.getItem('jwtToken');
}
