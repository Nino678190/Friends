function ansValidation() {
    var passValue = document.getElementById("password").value
    var confpassValue = document.getElementById("confirmPassword").value
    if (passValue === confpassValue) {
        window.alert("Das hat geklappt")
        return true;
    } else {
        window.alert("Passwort stimmt nicht überein");
        return false;
    }
}