$(document).ready(() => {
    const forms = $(".needs-validation");

    for (let i = 0; i < forms.length; i++) {
        const form = forms.eq(i);
        form.submit(e => {
            if (!form[0].checkValidity()) {
                e.preventDefault();
                // event.stopPropagation();
                form.addClass("was-validated");
            }
        });
    }
})