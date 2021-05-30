const request = new XMLHttpRequest();
request.open("GET", "https://animechan.vercel.app/api/random");

request.onload = function () {
    const response = JSON.parse(this.responseText);
    console.log(response);
    var a = document.createElement("div")
    a.innerHTML = response.quote + ' , ' + response.character
    a.classList.add("alert-primary");
    a.classList.add("alert");

    const alert = document.querySelector("#alert");
    alert.appendChild(a)

}

request.onerror = function () {
    console.log("erro ao executar a requisição");
};

request.send();