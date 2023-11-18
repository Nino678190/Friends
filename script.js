function registration() {
    var passValue = document.getElementById("password").value
    var confpassValue = document.getElementById("confirmPassword").value
    var gametag = document.getElementById("gametag").value
    var email = document.getElementById("email").value
    var geburtstag = document.getElementById("geburtsdatum").value
    var pronomen = document.getElementById("pronomen").value
    var interesse = ["minect","dwdq"]
    //document.getElementById("interessen").value
    if (passValue === confpassValue) {
        fetch("http://192.168.22.216:8080/signup", 
        {method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({username: gametag, password: passValue, email: email, geburtstag: geburtstag, pronomen: pronomen, interests: interesse})}).then(function(res){
            console.log(res)
            if (res.status == 200){
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
                if (res.status == 200){
                    location.href="/indexa.html"
                } 
                else {
                    window.alert("Benutzername und/oder Passwort falsch")
                }
            })
} 

if (document.getElementById("freundesuche")){
    document.getElementById("suchenform").addEventListener("submit", anfragetext)
}
function anfragetext(event){
    event.preventDefault()
    var text = document.getElementById("eingabesuche").value
    fetch("http://192.168.22.216:8080/search", 
        {method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"search": text})})
        .then(function(res){
            console.log(res)
                if (res.status == 200){
                    res.json().then((data) => {
                        queryUsers(data);
                    });
                } 
                else {
                    window.alert("Das hat nichts ergeben")
                }
            })

}

function queryUsers(data) {
    for (var user of data){
        console.log('user', user);
    }
}

