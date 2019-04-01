var trainee = 2;
var declaraties = "";
var reizen = "";

function DeclaratieToevoegen1() {
    var table = document.getElementById("Declaratie");
    console.log(table);
    var row = table.insertRow(document.getElementById("Declaratie").rows.length);
    
    var cell1 = row.insertCell(0);
    cell1.innerHTML = row.rowIndex;
    var cell2 = row.insertCell(1);
    cell2.setAttribute("name", "datum" + row.rowIndex);
    var input2 = document.createElement("input");
    input2.setAttribute("type", "date");
    cell2.appendChild(input2);
    var cell3 = row.insertCell(2);
    cell3.setAttribute("name", "omschrijving" + row.rowIndex);
    var input3 = document.createElement("input");
    input3.setAttribute("type", "text");
    input3.setAttribute("size", "75");
    cell3.appendChild(input3);
    var cell4 = row.insertCell(3);
    cell4.setAttribute("name", "bedragbtw" + row.rowIndex);
    var input4 = document.createElement("input");
    input4.setAttribute("type", "number");
    input4.setAttribute("min", "0");
    input4.setAttribute("value", "0");
    input4.setAttribute("step", ".01");
    cell4.appendChild(input4);
    var cell5 = row.insertCell(4);
    cell5.setAttribute("name", "btw" + row.rowIndex);
    var input5 = document.createElement("select");
    var array = ["Kies BTW tarief", "0%", "9%", "21%"];
    input5.id = "mySelect";
    cell5.appendChild(input5);
    for (var j = 0; j < array.length; j++) {
        var option = document.createElement("option");
        option.value = array[j];
        option.text = array[j];
        input5.appendChild(option);
    }
    var cell6 = row.insertCell(5);
    cell6.setAttribute("name", "bedrag" + row.rowIndex);
    var input6 = document.createElement("input");
    input6.setAttribute("type", "number");
    input6.setAttribute("min", "0");
    input6.setAttribute("value", "0");
    input6.setAttribute("step", ".01");
    cell6.appendChild(input6); 
    var cell7 = row.insertCell(6);
    cell7.setAttribute("name", "bestand" + row.rowIndex);
    var input7 = document.createElement("input");
    input7.setAttribute("type", "file");   
    cell7.appendChild(input7);
}

function DeclaratieToevoegen2(){
    var table = document.getElementById("Declaratiekm");
    console.log(table);
    var row = table.insertRow(document.getElementById("Declaratiekm").rows.length);

    var cell7 = row.insertCell(0);
    cell7.setAttribute("name", "datumkm" + row.rowIndex);
    var input7 = document.createElement("input");
    input7.setAttribute("type", "date");
    cell7.appendChild(input7);
    var cell8 = row.insertCell(1);
    cell8.setAttribute("name", "vertrek" + row.rowIndex);
    var input8 = document.createElement("input");
    input8.setAttribute("type", "text");
    input8.setAttribute("size", "50");
    cell8.appendChild(input8);
    var cell9 = row.insertCell(2);
    cell9.setAttribute("name", "aankomst" + row.rowIndex);
    var input9 = document.createElement("input");
    input9.setAttribute("type", "text");
    input9.setAttribute("size", "50");
    cell9.appendChild(input9);
    var cell10 = row.insertCell(3);
    cell10.setAttribute("name", "km" + row.rowIndex);
    var input10 = document.createElement("input");
    input10.setAttribute("type", "number");
    input10.setAttribute("min", "0");
    input10.setAttribute("value", "0");
    cell10.appendChild(input10);
}


function ReizenWegschrijven(){
    var table = document.getElementById("ReizenTableBody");
    table.innerHTML="";


    jsondata =JSON.parse(reizen);
    var col = [];
    // verkijg de keys van de data 
    for (var i = 0; i < jsondata.length; i++) {
        // loop over de keys van ieder element
        for (var key in jsondata[i]) {
            // als het element mist in de variable col dan voeg je het toe
            if (col.indexOf(key) === -1) {
                col.push(key);
            }
        }
    }

    for (var i = 0; i < jsondata.length; i++) {
        var row = table.insertRow(-1);

        for(var k=0;k<4;k++){
            var cellReis = row.insertCell(-1);
            var cellReisInput = document.createElement("input");
            cellReisInput.setAttribute("onfocusout","putreis(" + jsondata[i]["id"] + ")");
            if(k>0 && k<3){
                cellReisInput.setAttribute("type", "text");
                cellReisInput.setAttribute("size", "50");
                if(k==2) {
                    cellReisInput.value=jsondata[i]["van"];
                    cellReisInput.setAttribute("id","van" + jsondata[i]["id"]);
                } else {
                    cellReisInput.value=jsondata[i]["naar"];
                    cellReisInput.setAttribute("id","naar" + jsondata[i]["id"]);
                }
            }
            if(k==0){
                cellReisInput.setAttribute("type", "date");
                cellReisInput.value=jsondata[i]["datum"];
                cellReisInput.setAttribute("id","datum" + jsondata[i]["id"]);
            }
            if(k==3){
                cellReisInput.setAttribute("type", "number");
                cellReisInput.setAttribute("min", "0");
                cellReisInput.setAttribute("value", "0");
                cellReisInput.value=jsondata[i]["kilometers"];
                cellReisInput.setAttribute("id","kilometers" + jsondata[i]["id"]);
            }
            cellReis.appendChild(cellReisInput);

        }

    }
}

function Klik() {
    if (document.getElementById("demo").innerHTML == "Bewerk") {
        document.getElementById("demo").innerHTML = "Geklikt";
    } else {
        document.getElementById("demo").innerHTML = "Bewerk";
    }
}



function onload() {
    console.log(declaraties.length);
    if(declaraties.length==0) {
        loadDeclaraties();
    } else {
        
    }

    if(reizen.length==0){
        loadReizen();
        
    } else {
        ReizenWegschrijven();
    }

    var body = document.getElementById("thebody")


    var textje = document.createElement("div");
    textje.innerHTML = declaraties;
    var textje2 = document.createElement("div");
    textje2.innerHTML = reizen;
    body.appendChild(textje);
    body.appendChild(textje2);
}

function loadDeclaraties(){

        var api =  "api/DeclaratieForm/" + trainee

        // maak een nieuw request volgens het http protecol
        var xhttp = new XMLHttpRequest();
        console.log(api);
        // als staat van het XMLHTTPRequest object verandert doe dan het volgende
          xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                if (declaraties!=this.responseText) {
                    declaraties = this.responseText;
                    console.log("nu hier");
                    onload();
                }
            }
          };
        // geef aan dt je data wil gaan pakken uit de database
        // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/open
        xhttp.open("GET", "http://localhost:8082/"+api);
        // send request om data te gaan getten body wordt genegeerd
        // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/send
        xhttp.send();
}



function loadReizen(){
    var api =  "api/DeclaratieForm/Reis/" + trainee

    // maak een nieuw request volgens het http protecol
    var xhttp = new XMLHttpRequest();
    console.log(api);
    // als staat van het XMLHTTPRequest object verandert doe dan het volgende
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            if (reizen!=this.responseText) {
                reizen = this.responseText;
                console.log(reizen);
                onload();
            }
        }
    };
    // geef aan dt je data wil gaan pakken uit de database
    // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/open
    xhttp.open("GET", "http://localhost:8082/"+api);
    // send request om data te gaan getten body wordt genegeerd
    // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/send
    xhttp.send();
}

function putreis(id){
    var reis= {};
    reis.van = document.getElementById("van" + id).value;
    reis.naar = document.getElementById("naar" + id).value;
    reis.kilometers = document.getElementById("kilometers" + id).value;
    reis.datum = document.getElementById("datum" + id).value;
    reis.id = id;
    console.log(reis);
    putobject(JSON.stringify(reis));
}

function putobject(data){
    var api =  "api/DeclaratieForm/Reis/" + trainee

    // maak een nieuw request volgens het http protecol
    var xhttp = new XMLHttpRequest();
    console.log(api);
    // als staat van het XMLHTTPRequest object verandert doe dan het volgende
    xhttp.onreadystatechange = function() {
        console.log(this.status)
        if (this.readyState == 4 && this.status == 200) {
            reload();
        }
    };
    // geef aan dt je data wil gaan pakken uit de database
    // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/open
    xhttp.open("PUT", "http://localhost:8082/"+api);
    xhttp.setRequestHeader("Content-type", "application/json");
    // send request om data te gaan putten
    // https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/send
    xhttp.send(data);
}

