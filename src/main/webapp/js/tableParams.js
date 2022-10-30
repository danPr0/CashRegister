function addTableParamListener(url) {
    $(document).ready(() => {
        const sort = $("#sortSelect");
        const order = $("#orderSelect");
        const perPage = $("#perPageSelect");

        sort.change(() => {
            window.location = "http://localhost:8080" + url + "?sortBy=" + sort.val();
        })

        order.change(() => {
            window.location = "http://localhost:8080" + url + "?orderBy=" + order.val();
        })

        perPage.change(() => {
            window.location = "http://localhost:8080" + url + "?total=" + perPage.val();
        })
    });
}