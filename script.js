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

function verification(){
    var ema = document.getElementById("emalogin").value
    var pas = document.getElementById("paslogin").value
    fetch("/datenbank", 
        {method: "POST",
        body: JSON.stringify({username: ema, password: pas})}).then(function(res){
            if (res.erfolg == true){
                location.href="/indexa.html"
            } else {
                window.alert("Benutzername und/oder Passwort falsch")
            }
    })
} 