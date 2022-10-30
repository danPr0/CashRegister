function togglePassword (toggleId, passwordId) {
    $(document).ready(() => {
        const passwordInput = $(`#${passwordId}`);

        $(`#${toggleId}`).click(() => {
            const type = (passwordInput.attr("type") === "password" ? "text" : "password");
            passwordInput.attr("type", type);
        })
    })
}