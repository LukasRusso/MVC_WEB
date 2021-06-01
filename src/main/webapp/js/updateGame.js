console.log(window.localStorage.getItem('localGame'));

const localGame = JSON.parse(window.localStorage.getItem('localGame'));

const id = document.querySelector("#game-id");
console.log(id)
console.log(localGame.id)
id.value = localGame.id
const gameName = document.querySelector("#game-name");
gameName.value = localGame.name
const producer = document.querySelector("#game-producer");
producer.value = localGame.producer
const genre = document.querySelector("#game-genre");
genre.value = localGame.genre
const releaseDate = document.querySelector("#game-releaseDate");
releaseDate.value = localGame.releaseDate

const updateGame = () => {
    const request = new XMLHttpRequest();
    const url = "/MVC_WEB/GameAPI?game-name=" + gameName.value + "&game-producer=" + producer.value + "&game-genre=" + genre.value + "&game-releaseDate=" + releaseDate.value + "&game-id=" + id.value;
    request.open("PUT", url, true);
    request.send();
    request.onload = function () {
        window.localStorage.setItem('localGame', "");
        window.location.href = "/MVC_WEB/User/indexUser.html";
    }
}

const button = document.querySelector(".btn-primary")
button.addEventListener("click",updateGame)