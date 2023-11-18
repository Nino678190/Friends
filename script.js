function registration() {
    var passValue = document.getElementById("password").value
    var confpassValue = document.getElementById("confirmPassword").value
    var gametag = document.getElementById("gametag").value
    var email = document.getElementById("email").value
    var geburtstag = document.getElementById("geburtsdatum").value
    var pronomen = document.getElementById("pronomen").value
    var interesse = document.getElementById("interessen").value
    if (passValue === confpassValue) {
        fetch("http://192.168.22.216:8080/signup", 
        {method: "POST",
        body: JSON.stringify({username: gametag, password: passValue, email: email, geburtstag: geburtstag, pronomen: pronomen, interessen: interesse})}).then(function(res){
            if (res.registriert == true){
                location.href="/login.html"
            } else {
                window.alert("Das hat nicht geklappt, versuche es bitte nochmal")
            }
    })
    } else {
        window.alert("Passwort stimmt nicht überein");
        return false;
    }
    
}
if (document.getElementById("loginpage")){
    document.getElementById("loginform").addEventListener("submit", verification)
}
function verification(event){
    event.preventDefault()
    var ema = document.getElementById("emalogin").value
    var pas = document.getElementById("paslogin").value
    fetch("http://192.168.22.216:8080/login", 
        {method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"username": ema, "password": pas})}).then(function(res){
            console.log(res)
            res.json().then(function(data){
                if (data.success == true){
                    location.href="/indexa.html"
                } 
                else {
                    window.alert("Benutzername und/oder Passwort falsch")
                }
            })
        })
    } 