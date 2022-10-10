function controlMeasure() {
    const select = document.getElementById("productMeasure");
    const quantityInput = document.getElementById("prodQuantity")

    select.addEventListener("change", () => {
        if (select.value === "weight")
            quantityInput.setAttribute("pattern", "([1-9]{1}[0-9]*)|([0-9]+(.){1}[0-9]{0,3})")
        else quantityInput.setAttribute("pattern", "[1-9]{1}[0-9]*")
    })
}