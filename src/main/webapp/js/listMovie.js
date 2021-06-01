/**
 *
 */

const user = JSON.parse(localStorage.getItem("User"));

let localMovie = {}

const deleteMovie = (id) => {
  console.log(id)
  const request = new XMLHttpRequest();
  const url = "/MVC_WEB/MovieAPI?movie-id=" + id
  request.open("DELETE", url, true);
  request.send();
  request.onload = function () {
    document.location.href = "/MVC_WEB/User/indexUser.html";
  }
}

const favorMovie = (id) => {
  console.log(id)
  const request = new XMLHttpRequest();
  const url = "/MVC_WEB/UserMoviesAPI?movie-id=" + id + "&user-id=" + user.id
  request.open("POST", url, true);
  request.send();
  request.onload = function () {
    document.location.href = "/MVC_WEB/User/indexUser.html";
  }
}

const updateMovie = (id) => {
  console.log(id)
  const request = new XMLHttpRequest();
  const url = "/MVC_WEB/MovieAPI?movie-id=" + id
  request.open("GET", url, true);
  request.send();
  request.onload = function () {
    window.localStorage.setItem('localMovie', this.responseText);
   	window.location.href = "/MVC_WEB/Movie/update-movie.html";
  }
}

const request = new XMLHttpRequest();
request.open("GET", "/MVC_WEB/MovieAPI");

request.onload = function () {
  console.log(this.responseText);
  const response = JSON.parse(this.responseText);
  console.log(JSON.parse(this.responseText));

  const table = document.querySelector(".table-movie");
  for (let line of response) {
    var row = document.createElement("tr");
    var id = document.createElement("td");
    id.innerHTML = line.id;
    row.appendChild(id);
    var name = document.createElement("td");
    name.innerHTML = line.name;
    row.appendChild(name);
    var producer = document.createElement("td");
    producer.innerHTML = line.producer;
    row.appendChild(producer);
    var genre = document.createElement("td");
    genre.innerHTML = line.genre;
    row.appendChild(genre);
    var releaseDate = document.createElement("td");
    releaseDate.innerHTML = line.releaseDate;
    row.appendChild(releaseDate);

    var action = document.createElement("td");

    var a = document.createElement("button")
    a.innerHTML = "Edit"
    a.classList.add("btn-warning");
    a.classList.add("btn");
    a.addEventListener("click", function () {      
      updateMovie(line.id)
    })
    action.appendChild(a)

    var d = document.createElement("button")
    d.innerHTML = "Delete"
    d.classList.add("btn-danger");
    d.classList.add("btn");
    d.addEventListener("click", function () {      
      deleteMovie(line.id)
    })
    action.appendChild(d)

    var f = document.createElement("button")
    f.innerHTML = "Favoritar"
    f.classList.add("btn-primary");
    f.classList.add("btn");
    f.addEventListener("click", function () {
      favorMovie(line.id)
    })
    action.appendChild(f)

    row.appendChild(action)
    table.appendChild(row);
  }
  console.log(table)
};

request.onerror = function () {
  console.log("erro ao executar a requisição");
};

request.send();
