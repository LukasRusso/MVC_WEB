function showChartsUser(){
	document.getElementById("ChartCardUser").hidden = false;
}

function showChartsAddress(){
	document.getElementById("ChartCardAddress").hidden = false;
}

function getColors(){
	return ["#ff0000", "#ff4000", "#ff8000","#ffff00","#bfff00","#80ff00","#40ff00","#00ff00","#00ff40","#00ff80",
	"#00ffbf","#00ffff","#00bfff","#0080ff","#0040ff","#0000ff","#4000ff","#8000ff","#bf00ff","#ff00ff","#ff00bf",
	"#ff0080","#ff0040","#ff0000"];
}

function createChart(div, titleChart, vLabels, vData, vBackColor){	
	Chart.helpers.each(Chart.instances, function (instance){		
		if(instance.canvas.id == div.canvas.id) instance.destroy();
	});
	
	var chart = new Chart(div, {
		type:"pie",
		data: {
			labels: vLabels,
			datasets: [{
				data: vData,				
				backgroundColor: vBackColor			
			}]
		},
		options: {
			plugins: {title: {display: true, text: titleChart}},
			scale: {
				xAxes: [{display: true}],
				yAxes: [{display: true}]
			}
		}
	});
}

function getChart_UserName(){
	showChartsUser();
	var route = "/MVC_WEB/UserAPI?list_users=true";		
	
	$.ajax(
		{url: route, 
		method: "GET",		
		headers: {"Content-Type":"application/json", "charset":"UTF-8"}, 
		success: function(response){									
			return createChart_UserName(response.Users);					
		},
		error: function(response){			
			alert(response.status + " - Error: " + response.responseJSON.Error);			
		}}
	);
}

function createChart_UserName(list){
	var div = document.getElementById("chart_UserName").getContext("2d");	
	var namesLabel = [];
	var namesData = [];
	var colorsData = [];
	var checkName = true;
	
	list.forEach(user =>{
		var firstName = user.name.toUpperCase().split(" ")[0];		
		checkName = true;
		
		var pos = namesLabel.indexOf(firstName);				
		if(pos != -1) {
			namesData[pos] += 1;
			checkName = false;
		} 		
		
		//create all arrays
		if(checkName) {
			namesLabel.push(firstName);
			namesData.push(1);
			var colors = getColors();
			var colorPush = getColors()[0];
			while(colorsData.includes(colorPush)){
				var random = Math.floor(Math.random() * (colors.length + 1));
				colorPush = getColors()[random];
			}
			colorsData.push(colorPush);			
		}
	});	
	
	createChart(div, "Count User's Name'", namesLabel, namesData, colorsData);	
}

function getChart_UserAge(){			
	showChartsUser();
	var route = "/MVC_WEB/UserAPI?list_users=true";		
	
	$.ajax(
		{url: route, 
		method: "GET",		
		headers: {"Content-Type":"application/json", "charset":"UTF-8"}, 
		success: function(response){									
			return createChart_UserAge(response.Users);					
		},
		error: function(response){			
			alert(response.status + " - Error: " + response.responseJSON.Error);
		}}
	);
}

function createChart_UserAge(list){
	var div = document.getElementById("chart_UserAge").getContext("2d");	
	var agesLabel = [];
	var agesData = [];
	var colorsData = [];
	var checkName = true;
	
	list.forEach(user =>{
		var age = getAgeFromUser(user.birthday);		
		checkAge = true;
		
		var pos = agesLabel.indexOf(age);				
		if(pos != -1) {
			agesData[pos] += 1;
			checkAge = false;
		} 		
		
		//create all arrays
		if(checkAge) {
			agesLabel.push(age);
			agesData.push(1);
			var colors = getColors();
			var colorPush = getColors()[0];
			while(colorsData.includes(colorPush)){
				var random = Math.floor(Math.random() * (colors.length + 1));
				colorPush = getColors()[random];
			}
			colorsData.push(colorPush);			
		}
	});	
	
	createChart(div, "Count User's Age'", agesLabel, agesData, colorsData);	
}

function getAgeFromUser(dateString){
	var now = new Date();
	var date = new Date(dateString);	
	var age = now.getFullYear() - date.getFullYear();
	var month = now.getMonth() - date.getMonth();
	if(month < 0 || (month === 0 && now.getDate() < date.getDate())) age--;
	return age;	
}

function getChart_AddressState(){			
	showChartsAddress();
	var route = "/MVC_WEB/AddressAPI?list_Address=true";		
	
	$.ajax(
		{url: route, 
		method: "GET",		
		headers: {"Content-Type":"application/json", "charset":"UTF-8"}, 
		success: function(responseSucces){									
			return createChart_AddressState(responseSucces.Address);					
		},
		error: function(response){
			console.log(response);			
			alert(response.status + " - Error: " + response);
		}}
	);
}

function createChart_AddressState(list){
	var div = document.getElementById("chart_AddressState").getContext("2d");	
	var stateLabel = [];
	var stateData = [];
	var colorsData = [];
	var checkState = true;
	
	list.forEach(address =>{		
		var state = address.addr_state;		
		checkState = true;
		
		var pos = stateLabel.indexOf(state);				
		if(pos != -1) {
			stateData[pos] += 1;
			checkState = false;
		} 		
		
		//create all arrays
		if(checkState) {
			stateLabel.push(state);
			stateData.push(1);
			var colors = getColors();
			var colorPush = getColors()[0];
			while(colorsData.includes(colorPush)){
				var random = Math.floor(Math.random() * (colors.length + 1));
				colorPush = getColors()[random];
			}
			colorsData.push(colorPush);			
		}
	});	
	
	createChart(div, "Count Address's State'", stateLabel, stateData, colorsData);	
}

function getChart_AddressCity(){			
	showChartsAddress();
	var route = "/MVC_WEB/AddressAPI?list_Address=true";		
	
	$.ajax(
		{url: route, 
		method: "GET",		
		headers: {"Content-Type":"application/json", "charset":"UTF-8"}, 
		success: function(responseSucces){									
			return createChart_AddressCity(responseSucces.Address);	
		},
		error: function(response){
			console.log(response);			
			alert(response.status + " - Error: " + response);
		}}
	);
}

function createChart_AddressCity(list){
	var div = document.getElementById("chart_AddressCity").getContext("2d");	
	var cityLabel = [];
	var cityData = [];
	var colorsData = [];
	var checkCity = true;
	
	list.forEach(address =>{
		console.log(address);
		var city = address.addr_city;		
		checkCity = true;
		
		var pos = cityLabel.indexOf(city);				
		if(pos != -1) {
			cityData[pos] += 1;
			checkCity = false;
		} 		
		
		//create all arrays
		if(checkCity) {
			cityLabel.push(city);
			cityData.push(1);
			var colors = getColors();
			var colorPush = getColors()[0];
			while(colorsData.includes(colorPush)){
				var random = Math.floor(Math.random() * (colors.length + 1));
				colorPush = getColors()[random];
			}
			colorsData.push(colorPush);			
		}
	});	
	
	createChart(div, "Count Address's City'", cityLabel, cityData, colorsData);	
}