
function sort(url) {
    const select = document.getElementById("sortSelect");

    select.addEventListener("change", () => {
        window.location = "http://localhost:8080" + url + select.value;
        // const http = window.XMLHttpRequest;
        // http.open("GET", "http://localhost:8080" + url + select.value);
        // http.send();
        // $.get("http://localhost:8080" + url + select.value, function(data, status) {
        //     console.log((`${data}`))
        // })
        // let param = "default";
        //
        // args.forEach(value => {
        //     if (select.getElementsByClassName(value).item(0).textContent.trim() === select.value)
        //         param = value;
        // })

        // if (select.getElementsByClassName("productId").item(0).textContent.trim() === select.value)
        //     param = "productId";
        // else if (select.getElementsByClassName("quantity").item(0).textContent.trim() === select.value)
        //     param = "quantity";
        // else param = "default";


    })
}