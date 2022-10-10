function togglePassword (toggleId, passwordId) {
    const toggle = document.getElementById(toggleId);
    const passwordInput = document.getElementById(passwordId);

    toggle.addEventListener("click", () => {
        const type = (passwordInput.getAttribute("type") === "password" ? "text" : "password");
        passwordInput.setAttribute("type", type);
    })
}