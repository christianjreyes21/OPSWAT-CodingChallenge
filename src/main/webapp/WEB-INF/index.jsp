<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CODING CHALLENGE</title>
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
	<div class="section"></div>
	<div class="container">
		<h3 class="center">CODING CHALLENGE</h3>
		<div class="section"></div>
		<div class="section"></div>
		<h5 class="center">Please upload your file.</h5>
		<div class="section"></div>
		<div class="section"></div>
		<div class="section"></div>
		<form action="/upload" method="POST" encType="multipart/form-data">
			<div class="file-field input-field">
				<div class="btn">
					<span>Select file</span> <input type="file" name="file">
				</div>
				<div class="file-path-wrapper">
					<input class="file-path validate" type="text">
				</div>
			</div>
			<div class="center">
				<button class="btn waves-effect waves-light" type="submit" name="action">Submit <i class="material-icons right">send</i></button>
			</div>
		</form>
		
	</div>
	<script type="text/javascript"
		src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	
</body>

</html>