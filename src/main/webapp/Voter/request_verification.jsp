<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style>
/* Style the top navigation bar */
.navbar {
  overflow: hidden;
  background-color: #333;
}

/* Style the navigation bar links */
.navbar a {
  float: left;
  display: block;
  color: white;
  text-align: center;
  padding: 14px 20px;
  text-decoration: none;
}
/* Change color on hover */
.navbar a:hover {
  background-color: #ddd;
  color: black;
}
.temp {
	text-align: center;
}
.header {
  padding: 80px;
  text-align: center;
  background: #3232a8;
  color: white;
}
</style>
<meta charset="ISO-8859-1">
<title>Request Accepted</title>
</head>
<body>
<div class="header">
  <h1>Election Commission of India</h1>
  <p>Online voting Portal</p>
</div>
<div class="navbar">
  <a href="/OnlineElectionSystem/home">Voter</a>
</div>
<div class="temp">
	<p>Request Id: ${request_id}</p>
	<p>Your request is submitted successfully. Please wait for BLO verification.</p>
 </div>
</body>
</html>