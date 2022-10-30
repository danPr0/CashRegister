function checkPasswordsMatch (password1Id, password2Id) {
    $(document).ready(() => {
        const form = $(".needs-validation").first();
        const password = $(`#${password1Id}`);
        const passwordConfirm = $(`#${password2Id}`);
        const passwordIMismatchFeedback = $(`#${password1Id}Mismatch`);
        const passwordConfirmMismatchFeedback = $(`#${password2Id}Mismatch`);

        form.submit(e => {
            if (password.val() !== passwordConfirm.val()) {
                e.preventDefault();
                form.addClass("was-validated");
                invalidate()
            }

            password.on("input", () => {
                if (password.val() !== passwordConfirm.val())
                    invalidate()
                else validate()
            })

            passwordConfirm.on("input", () => {
                if (password.val() !== passwordConfirm.val())
                    invalidate()
                else validate();
            })

            function invalidate() {
                passwordIMismatchFeedback.removeClass("visually-hidden");
                passwordConfirmMismatchFeedback.removeClass("visually-hidden");
            }

            function validate() {
                passwordIMismatchFeedback.addClass("visually-hidden");
                passwordConfirmMismatchFeedback.addClass("visually-hidden");
            }
        });
    })
}
