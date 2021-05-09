function showElements(){
	document.getElementById("ChartCard").hidden = false;
}

function createChart(object){
	var chart = new Chart(div, {
		type:"pie",
		data: {
			labels: [],
			datasets: [{
				data: [],
				borderColor: [],
				backgroundColor: [],
				borderWidth: 2
			}]
		},
		options: {
			scale: {
				xAxes: [{display: true}],
				yAxes: [{display: true}]
			}
		}
	});
}

function getChart_UserName(elementId){
	showElements();
		
	var div = document.getElementById(elementId).getContext("2d");
	var chart = new Chart(div, {
		type:"pie",
		data: {
			labels: ["Lucas", "Araujo", "Russo"],
			datasets: [{
				data: [1500, 1000, 500],
				borderColor: ["red", "blue", "yellow"],
				backgroundColor: ["rgb(255, 0, 0, 0.3)", "rgb(0, 0, 255, 0.3)","rgb(255, 255, 0, 0.3)"],
				borderWidth: 2
			}]
		},
		options: {
			scale: {
				xAxes: [{display: true}],
				yAxes: [{display: true}]
			}
		}
	});
}

function getChart_UserAge(elementId){
	showElements();
		
	var div = document.getElementById(elementId).getContext("2d");
	var chart = new Chart(div, {
		type:"pie",
		data: {
			labels: ["15", "25", "60"],
			datasets: [{
				data: [1500, 1000, 500],
				borderColor: ["red", "blue", "yellow"],
				backgroundColor: ["rgb(255, 0, 0, 0.3)", "rgb(0, 0, 255, 0.3)","rgb(255, 255, 0, 0.3)"],
				borderWidth: 2
			}]
		},
		options: {
			scale: {
				xAxes: [{display: true}],
				yAxes: [{display: true}]
			}
		}
	});
}