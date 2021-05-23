const localMovie = JSON.parse(window.localStorage.getItem('localMovie'));
console.log(localMovie)

const id = document.querySelector("#movie-id");
console.log(id)
console.log(localMovie.id)
id.value = localMovie.id
const movieName = document.querySelector("#movie-name");
movieName.value = localMovie.name
const producer = document.querySelector("#movie-producer");
producer.value = localMovie.producer
const genre = document.querySelector("#movie-genre");
genre.value = localMovie.genre
const releaseDate = document.querySelector("#movie-releaseDate");
releaseDate.value = localMovie.releaseDate

const updateMovie = () => {
    const request = new XMLHttpRequest();
    const url = "/FTT-WEB-091/movie?movie-name=" + movieName.value + "&movie-producer=" + producer.value + "&movie-genre=" + genre.value + "&movie-releaseDate=" + releaseDate.value + "&movie-id=" + id.value;
    request.open("PUT", url, true);
    request.send();
    request.onload = function () {
        window.localStorage.setItem('localMovie', "");
        window.location.href = "index.html";
    }

}

const button = document.querySelector(".btn-primary")
button.addEventListener("click",updateMovie)