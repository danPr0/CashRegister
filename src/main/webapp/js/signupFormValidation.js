// $(document).ready(() => {
//     const forms = document.getElementsByClassName("needs-validation");
//     const password = document.getElementById("password");
//     const passwordConfirm = document.getElementById("passwordConfirm");
//     const passwordIMismatchFeedback = document.getElementById("passwordMismatch");
//     const passwordConfirmMismatchFeedback = document.getElementById("passwordConfirmMismatch");
//     const passwordInvalidFeedback = $("#passwordInvalid");
//     const passwordConfirmInvalidFeedback = $("#passwordConfirmInvalid");
//
//     Array.prototype.filter.call(forms, form => {
//     //     form.submit(event => {
//
//     // $(document).on("submit", ".needs-validation", event => {
//     // form.on('submit', event => {
//         form.addEventListener("submit", event => {
//             if (form.checkValidity() === false) {
//                 event.preventDefault();
//                 event.stopPropagation();
//                 form.classList.add("was-validated");
//                 // passwordInvalidFeedback.classList.remove("visually-hidden");
//                 // passwordConfirmInvalidFeedback.classList.remove("visually-hidden");
//             } else if (password.value !== passwordConfirm.value) {
//                 event.preventDefault();
//                 event.stopPropagation();
//                 form.classList.add("was-validated");
//                 invalidate()
//             }
//
//             password.on("input", () => {
//                 if (password.value !== passwordConfirm.value)
//                     invalidate()
//                 else validate()
//             })
//
//             passwordConfirm.on("input", () => {
//                 if (password.value !== passwordConfirm.value)
//                     invalidate()
//                 else validate();
//             })
//
//             function invalidate() {
//                 // password.classList.add("is-invalid");
//                 // passwordInvalidFeedback.classList.add("visually-hidden");
//                 passwordIMismatchFeedback.classList.remove("visually-hidden");
//
//                 // passwordConfirm.classList.add("is-invalid");
//                 // passwordConfirmInvalidFeedback.classList.add("visually-hidden");
//                 passwordConfirmMismatchFeedback.classList.remove("visually-hidden");
//             }
//
//             function validate() {
//                 // password.classList.remove("is-invalid");
//                 // passwordInvalidFeedback.classList.remove("visually-hidden");
//                 passwordIMismatchFeedback.classList.add("visually-hidden");
//
//                 // passwordConfirm.classList.remove("is-invalid");
//                 // passwordConfirmInvalidFeedback.classList.remove("visually-hidden");
//                 passwordConfirmMismatchFeedback.classList.add("visually-hidden");
//             }
//         }, false);
//     });
// });
//
//
$(document).ready(() => {
    const form = $(".needs-validation").first();
    const password = $("#password");
    const passwordConfirm = $("#passwordConfirm");
    const passwordIMismatchFeedback = $("#passwordMismatch");
    const passwordConfirmMismatchFeedback = $("#passwordConfirmMismatch");
    const passwordInvalidFeedback = $("#passwordInvalid");
    const passwordConfirmInvalidFeedback = $("#passwordConfirmInvalid");

    // Array.prototype.filter.call(forms, form => {
    //     form.submit(event => {

    // $(document).on("submit", ".needs-validation", event => {
    // $(document).on('submit', ".needs-validation", "input",  event => {
    // password.on("input", () => {
    //     console.log(123)
    // })

    // submit.on("submit", (e) => {
    //     e.preventDefault();
    //     console.log(321)
    // })
    //
    // form.on("submit", (e) => {
    //     e.preventDefault()
    //     console.log(form[0].checkValidity())
    // })

// form.addEventListener("submit", event => {
    form.submit(e => {
        if (!form[0].checkValidity()) {
            e.preventDefault();
            // event.stopPropagation();
            form.addClass("was-validated");
        } else if (password.val() !== passwordConfirm.val()) {
            e.preventDefault();
            // event.stopPropagation();
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
            // password.classList.add("is-invalid");
            // passwordInvalidFeedback.classList.add("visually-hidden");
            passwordIMismatchFeedback.removeClass("visually-hidden");

            // passwordConfirm.classList.add("is-invalid");
            // passwordConfirmInvalidFeedback.classList.add("visually-hidden");
            passwordConfirmMismatchFeedback.removeClass("visually-hidden");
        }

        function validate() {
            // password.classList.remove("is-invalid");
            // passwordInvalidFeedback.classList.remove("visually-hidden");
            passwordIMismatchFeedback.addClass("visually-hidden");

            // passwordConfirm.classList.remove("is-invalid");
            // passwordConfirmInvalidFeedback.classList.remove("visually-hidden");
            passwordConfirmMismatchFeedback.addClass("visually-hidden");
        }
    });
    // });
})


