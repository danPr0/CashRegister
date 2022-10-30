$(document).ready(() => {
    const select = $("#productMeasure");
    const quantityInput = $("#prodQuantity")

    select.change(() => {
        if (select.val() === "weight")
            quantityInput.attr("pattern", "([1-9]{1}[0-9]{0,3})|([0-9]{1,4}(.){1}[0-9]{1,3})")
        else quantityInput.attr("pattern", "[1-9]{1}[0-9]{0,3}")
    })
})