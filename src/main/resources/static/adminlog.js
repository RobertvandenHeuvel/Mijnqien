function logout() {
    localStorage.clear();
    location.href = "logout";
}

var input = 0;
function herDeclaForm(input){
    var api =  ""
    switch(input){
        case 1:
            api =  "api/Email/herinnering1";
        break;
        case 2:
            api =  "api/Email/herinnering2";
        break;
        case 3:
            api =  "api/Email/herinnering3";
        break;
        case 4:
            api =  "api/Email/herinnering4";
        break;
    }
    herDeclaForm1(api);
}

inputString = "";
function herDeclaForm1(inputString){
    var api =  inputString;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        console.log(this.status)
        if (this.readyState == 4 && this.status == 202) {
            console.log("gelukt");
        }
    };

    xhttp.open("POST", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");

    xhttp.send();
}