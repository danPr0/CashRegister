// grecaptcha.enterprise.ready(function () {
//     grecaptcha.enterprise.execute('6Le7PtwiAAAAAK3P3KE9NUxnfwJavei_g-jWmkUD', {action: 'login'}).then(function (token) {
//         document.getElementById('g-recaptcha-response').value = token;
//     });
// });
grecaptcha.ready(function () {
    grecaptcha.execute('6Lcn7NwiAAAAAAwYtAsRIMOAyuRz4lXz20JGzqVO', {action: 'login'}).then(function (token) {
        document.getElementById('g-recaptcha-response').value = token;
    });
});