function registration() {
    let passValue = document.getElementById("password").value
    let confpassValue = document.getElementById("confirmPassword").value
    let gametag = document.getElementById("gametag").value
    let email = document.getElementById("email").value
    let geburtstag = document.getElementById("geburtsdatum").value
    let pronomen = document.getElementById("pronomen").value
    let interesse = ["minect","dwdq"]
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
    let ema = document.getElementById("emalogin").value
    let pas = document.getElementById("paslogin").value
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
    let text = document.getElementById("eingabesuche").value
    fetch("http://192.168.22.216:8080/searchuser", 
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
    for (let userid of data){
        console.log('userid', userid);
        queryuser(userid)
    }
}

function project_registration(){
    
}

function queryuser(userid){
    
    fetch("http://192.168.22.216:8080/queryuser", 
        {method: "POST",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"id": userid})}).then(function(res){
            console.log(res)
            res.json()
            .then(function (user){
                console.log(user)
                let tabellename = document.getElementById("usernameTabelle")
                let tabellepronomen = document.getElementById("pronomenTabelle")
                let tabelleinteressen = document.getElementById("interessenTabelle")
                tabellename.innerHTML = user.username;
                tabellepronomen.innerHTML = user.pronouns;
                tabelleinteressen.innerHTML = user.interests;
            })
        }
    )}

