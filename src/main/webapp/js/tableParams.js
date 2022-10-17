function addTableParamListener(url) {
    window.addEventListener("load", () => {
        const sort = document.getElementById("sortSelect");
        const order = document.getElementById("orderSelect");
        const perPage = document.getElementById("perPageSelect")

        sort.addEventListener("change", () => {
            window.location = "http://localhost:8080" + url + "?sortBy=" + sort.value;
        })

        order.addEventListener("change", () => {
            window.location = "http://localhost:8080" + url + "?orderBy=" + order.value;
        })

        perPage.addEventListener("change", () => {
            window.location = "http://localhost:8080" + url + "?total=" + perPage.value;
        })
    });
}