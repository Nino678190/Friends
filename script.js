function registration() {
    let passValue = document.getElementById("password").value
    let confpassValue = document.getElementById("confirmPassword").value
    let gametag = document.getElementById("gametag").value
    let email = document.getElementById("email").value
    let geburtstag = document.getElementById("geburtsdatum").value
    let pronomen = document.getElementById("pronomen").value
    let interesse = document.getElementById("interessen").value
    if (passValue === confpassValue) {
        fetch("http://192.168.22.216:8080/signup", 
        {method: "POST",
        credentials:"same-origin",
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
        credentials:"same-origin",
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
        credentials:"same-origin",
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
    tabellenbody.innerHTML = ""
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
        credentials:"same-origin",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"id": userid})}).then(function(res){
            console.log(res)
            res.json()
            .then(function (user){
                console.log(user)
                let pronouns = user.pronouns ?? "N/A"
                let tr = document.createElement("tr")
                let button = document.createElement("button")
                let buttonjs = "Button"+user.username
                tr.innerHTML = "<td>"+user.username+"</td><td>"+pronouns+"</td><td>"+user.interests+"</td><td>"+"<button id="+buttonjs+">Freundschaftsanfrage</button>"+"</td>"
                tabellenbody.appendChild(tr)
                document.getElementById(buttonjs)
                button = document.getElementById(buttonjs)
                button.onclick = function (){
                    console.log(user.username)
                    fetch("http://192.168.22.216:8080/requestfriend",
                        {method: "POST",
                        credentials:"same-origin",
                        headers: {"content-type": "application/json"},

                        body: JSON.stringify({"requested": userid})}).then(function(res){
                            window.alert("Freundschaftsanfrage gesendet")
                        }
                    
                    )
                }
            })
        }
    )}

function frage_freunde(){
    fetch("http://192.168.22.216:8080/queryfriendrequests", 
        {method: "POST",
        credentials: 'same-origin',
        headers: {"content-type": "application/json"},
        })
        .then(function(res){
            console.log(res)
                if (res.status == 200){
                    res.json().then((data) => {
                        queryUsers_Freunde(date);
                    });
                } 
                else {
                    window.alert("Das hat nichts ergeben")
                }
        })
}
            
function queryUsers_Freunde(data) {
    tabellenbody.innerHTML = ""
    for (let userid of data){
        console.log('userid', userid);
        queryuser(userid)
    }
}

function Freundschaftsanfragen(userid){
    fetch("http://192.168.22.216:8080/queryuser", 
        {method: "POST",
        credentials:"same-origin",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"id": userid})}).then(function(res){
            console.log(res)
            res.json()
            .then(function (user){
                console.log(user)
                let tr = document.createElement("tr")
                let button_annehmen_id = "Button_annehmen"+user.username
                let button_ablehnen_id = "Button_ablehnen"+user.username

                tr.innerHTML = "<td>"+user.username+
                "</td><td>"+user.interests+"</td><td>"+
                "<button id="+button_annehmen_id+">Freundschaftsanfrage annehmen</button>"+
                "</td><td>"+"<button id="+button_ablehnen_id+">Freundschaftsanfrage ablehnen</button>"+
                "</td>"
                tabellenanfrage.appendChild(tr)

                button_annehmen = document.getElementById(button_annehmen_id)
                button_ablehnen = document.getElementById(button_ablehnen_id)
                button_annehmen.onclick = function (){
                    console.log(user.username)
                    fetch("http://192.168.22.216:8080/acceptfriendrequest",
                    {method: "POST",
                    credentials:"same-origin",
                    headers: {"content-type": "application/json"},
                    body: JSON.stringify({"requestor": userid})}).then(function(res){
                        console.log(res);
                    })
                }
                button_ablehnen.onclick = function (){
                    console.log(user.username)
                    fetch("http://192.168.22.216:8080/denyfriendrequest",
                    {method: "POST",
                    credentials:"same-origin",
                    headers: {"content-type": "application/json"},
                    body: JSON.stringify({"requestor": userid})}).then(function(res){
                        console.log(res);
                    })
                }
            })
        }
    )}

            
function queryUsers_Anfragen(data) {
    tabellenbody.innerHTML = ""
    for (let userid of data){
        console.log('userid', userid);
        queryuser(userid)
    }
}

if (document.getElementById("proaseite")){
    document.getElementById("projektanlegung").addEventListener("submit", veroeffentlichen)
}
function veroeffentlichen(event){
    event.preventDefault()
    let name = document.getElementById("projektname").value
    let description = document.getElementById("Beschreibungprojekt").value
    let Projektleitung = document.getElementById("projektleiter")
    fetch("http://192.168.22.216:8080/createproject", 
        {method: "POST",
        credentials:"same-origin",
        headers: {"content-type": "application/json"},
        body: JSON.stringify({"title": name, "description": description, "projectleader": Projektleitung})}).then(function(res){
            console.log(res)
                if (res.status == 200){
                    location.href="/pros.html"
                } 
                else {
                    window.alert("Das hat nicht geklappt. Bitte versuche es erneut!")
                }
            })

} 