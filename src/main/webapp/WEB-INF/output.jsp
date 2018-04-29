<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>App Vetting</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
	<style>
		.ListArea {
		width:49.5%;
		height:50%;
		}
		.push {
		margin-left: 31.6%;
		}
	</style>
</head>
<body>
	<input type="hidden" value=${hashUploadOutput} id="results">
	<div class="section"></div>
	<div class="container">
		<h3 class="center">Results</h3>
		<div class="section"></div>
		<div class="section"></div>
		<h5 class="center">Filename: ${fileName}</h5>
		<h5 class="center">${hashOutput}</h5>
		<h5 class="center">${uploadOutput.data_id}</h5>
		<h5 class="center">${hashUploadOutput}</h5>
		<div class="section"></div>
		<div class="section"></div>
		<div class="section"></div>
		
		
		
	</div>
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	<script>
		var string = document.getElementById("results").value;
		var results = JSON.parse(string);
		console.log(string);
		console.log(results); 
	</script>
	
</body>

</html>