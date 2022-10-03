(() => {
    window.addEventListener('load', () => {
        var forms = document.getElementsByClassName('needs-validation');

        Array.prototype.filter.call(forms, form => {
            form.addEventListener('submit', event => {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();
