let currentPage = 1;
let query = null;

window.addEventListener("DOMContentLoaded", function () {
    var url_string = window.location.href; // www.test.com?id=dasdasd
    var url = new URL(url_string);
    var urlQuery = url.searchParams.get("query");
    if (urlQuery) {
        query = urlQuery;
        getPostList();
    }

    // search input
    const searchBtn = document.getElementById("srp-search-buttonId");
    if (searchBtn) {
        searchBtn.addEventListener("click", (e) => {
            e.preventDefault();
            const query = document.getElementById("srp-search-inputId").value;
            if (query && query.length > 0) {
                window.location.href = "./search-result-page.html?query=" + query;
            }
        })
    }

    const searchInput = document.getElementById("srp-search-inputId");
    if (searchInput) {
        searchInput.addEventListener("keypress", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                console.log("aaaaaaaaaaaaaaaaaaaaa")
                if (searchInput.value && searchInput.value.length > 0) {
                    window.location.href = "./search-result-page.html?query=" + searchInput.value;
                }
            }
        })
    }
});

function getPostList() {
    const lang = document.getElementById("current-lang").textContent;
    let size = 9;
    const body = {
        "query": query
    }
}

function showPostList(postList) {
    const parent = document.getElementById("post-search-result-containerId")
    parent.innerHTML = '';
    if (postList.length === 0) {
        return;
    }
}