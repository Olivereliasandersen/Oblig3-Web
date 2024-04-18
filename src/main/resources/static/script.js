//Funksjon som printer ut at du må fylle inn input
function nullInnhold(input){
    document.getElementById(input+"ErGalt").innerHTML="Du må skrive noe inn i "+input;
}
//Funksjon som viser billetten
function visBilett(billetter) {
    let liste = document.getElementById("kinobilletter");
    liste.innerHTML = "";
    let ut = "<div class='col-xs-2'>Film</div>" + "<div class='col-xs-2'>Antall</div>" + "<div class='col-xs-2'>Fornavn</div>" +
        "<div class='col-xs-2'>Etternavn</div>" + "<div class='col-xs-2'>TelefonNr</div>" + "<div class='col-xs-2'>Epost</div><br><br>"

    for (let p of billetter){
        ut += "<div class='row'><div class='col-xs-2'>"+p.film+"</div><div class='col-xs-2'>"+p.antall+"</div><div class='col-xs-2'>"+p.fornavn+"</div>"+
            "<div class='col-xs-2'>"+p.etternavn+"</div><div class='col-xs-2'>"+p.telefon+"</div><div class='col-xs-2'>"+p.epost+" <button onclick='oppdaterBillett()'>Oppdater</button></div></div><hr>"
    }
    liste.innerHTML=ut;
}
//Funksjon som fjerner den røde skriften som kommer når du har skrevet noe feil i input
function fjernRodSkrift(){
    document.getElementById("filmErGalt").innerHTML="";
    document.getElementById("antallErGalt").innerHTML="";
    document.getElementById("fornavnErGalt").innerHTML="";
    document.getElementById("etternavnErGalt").innerHTML="";
    document.getElementById("telefonnummerErGalt").innerHTML="";
    document.getElementById("epostErGalt").innerHTML="";
}
//Funksjon som sjekker om fornavn er skrevet på riktig måte, om ikke ber den deg skrive inn noe på nytt
function valideringNavn(fornavn){
    const gyldigBokstaver = /^[a-zA-Z+æ+ø+å+Æ+Ø+Å]+$/;
    if (fornavn.match(gyldigBokstaver)){
        return true;
    }
    else{
        document.getElementById("fornavnErGalt").innerHTML="Skriv inn et gyldig fornavn";
        return false;
    }
}
//Funksjon som sjekker om etternavn er skrevet på riktig måte, om ikke ber den deg skrive inn noe på nytt
function valideringNavn2(etternavn){
    const gyldigBokstaver = /^[a-zA-Z+æ+ø+å+Æ+Ø+Å]+$/;
    if (etternavn.match(gyldigBokstaver)){
        return true;
    }
    else {
        document.getElementById("etternavnErGalt").innerHTML="Skriv inn et gyldig etternavn";
        return false;
    }
}
//Funksjon som sjekker om telefonnummer er skrevet på riktig måte, om ikke ber den deg skrive inn noe på nytt
function valideringTelefon(telefon){
    const gyldigNummer = /^[0-9]{8}$/im;
    if (telefon.match(gyldigNummer)){
        return true;
    }
    else {
        document.getElementById("telefonnummerErGalt").innerHTML="Skriv inn et gyldig telefonnummer";
        return false;
    }
}
//Funksjon som sjekker om epost er skrevet på riktig måte, om ikke ber den deg skrive inn noe på nytt
function valideringEpost(epost){
    const gyldigEpost = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (epost.match(gyldigEpost)){
        return true;
    }
    else {
        document.getElementById("epostErGalt").innerHTML="Skriv inn en gyldig epost";
        return false;
    }
}
//Funksjon som bruker alle valideringene vi lagde rett før og nullinnhold funsksjonen til å gå gjennom alt å se om du er feil.
function validering(film,antall,fornavn,etternavn,telefon,epost){
    let gyldig = true;
    if (film === ""){
        nullInnhold("film")
        gyldig = false;
    }
    if (antall === ""){
        nullInnhold("antall")
        gyldig = false;
    }
    if (fornavn === ""){
        nullInnhold("fornavn")
        gyldig = false;
    }
    else if (valideringNavn(fornavn) === false){
        gyldig = false;
    }
    if (etternavn === ""){
        nullInnhold("etternavn")
        gyldig = false;
    }
    else if (valideringNavn2(etternavn) === false){
        gyldig = false;
    }
    if (telefon === ""){
        nullInnhold("telefonnummer")
        gyldig = false;
    }
    else if (valideringTelefon(telefon) === false){
        gyldig = false;
    }
    if (epost === ""){
        nullInnhold("epost")
        gyldig = false;
    }
    else if (valideringEpost(epost) === false){
        gyldig = false;
    }
    return gyldig;
}
//Funksjon som tar inn det som ble skrevet i inputen og lager billetten
function registrer() {
    fjernRodSkrift();
    let film = document.getElementById("film").value;
    let antall = document.getElementById("antall").value;
    let fornavn = document.getElementById("fornavn").value;
    let etternavn = document.getElementById("etternavn").value;
    let telefon = document.getElementById("telefon").value;
    let epost = document.getElementById("epost").value;
    if (validering(film,antall,fornavn,etternavn,telefon,epost) === false){
        return;
    }
    let filmbillett = {
        film: film,
        antall: antall,
        fornavn: fornavn,
        etternavn: etternavn,
        telefon: telefon,
        epost: epost
    }
    $.post("/lagre", filmbillett, function(){
        getData()})

    document.getElementById("film").value="";
    document.getElementById("antall").value="";
    document.getElementById("fornavn").value="";
    document.getElementById("etternavn").value="";
    document.getElementById("telefon").value="";
    document.getElementById("epost").value="";
}
//Funksjon som sletter billetten
function slettBilletter(){
    document.getElementById("kinobilletter").innerHTML="";
    $.post("/slettAlle", function(){})
}

function getData(){
    $.get("/hentAlle", function(data){visBilett(data)})
}

function oppdaterBillett(){
    const billett = {
        id : $("#id").val(),
        film : $("#film").val(),
        antall : $("#antall").val(),
        fornavn : $("#fornavn").val(),
        etternavn : $("#etternavn").val(),
        telefon : $("#telefon").val(),
        epost : $("#epost").val(),
    };
    $.post("/oppdater",billett, function(){
        hentAlle();
    })
}
