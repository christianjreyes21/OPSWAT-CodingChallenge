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
	<input type="hidden" value='${hashUploadOutput}' id="results">
	<div class="section"></div>
	<div class="container">
		<h3 class="center">Results</h3>
		<div class="section"></div>
		<div class="section"></div>
	
		
		<div class ="overall">
			<p>filename: ${fileName}</p>
		</div>
		<div class="section"></div>
		<div class="section"></div>
		<div class="results"></div>
		

		
		<div class="section"></div>
		<div class="section"></div>
		<div class="section"></div>
		
		
		
	</div>
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
	

	
	<script>
	$.ajax({
		url: "http://localhost:8080/getJson",
		method:"GET",
		dataType:"json",
		success: function(res){
			let fullHtml =''
			if(res.scan_all_result_a === 'No threat detected') {
				let forOverall = "<p>Overall Status: Clean</p>"
				$('.overall').append(forOverall);
			}
			for (let data in res.scan_details) {
				let threat = '';
				 if (res.scan_details[data].threat_found === '') {
					threat = 'clean';
				}
				else {
					threat = res[data].threat_found;
				} 
				let html = "<div class='result'>\n"
				html += "\t<p>Engine : " + data + "</p>\n"
				html += "\t<p>Threat found: " + threat + "</p>\n"  
				html += "\t<p>Scan result: " + res.scan_details[data].scan_result_i + "</p>\n"
				html += "\t<p>Def time: " + res.scan_details[data].def_time + "</p>\n"
				html += "</div>\n";
				html += "<div class=section></div>\n"
				fullHtml += html;
			}
			$('.results').append(fullHtml);
		}
	})
	</script>
	
	
</body>

</html>