<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Shopping List - Register Failed</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- External import for bootsrap and jquery -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!-- Css styles and scripts for register page -->
<link href="${pageContext.request.contextPath}/Resources/css/stylesRegister.css" type="text/css" rel="stylesheet">
<!-- Js script for transition for page -->
<script type="text/javascript" src="${pageContext.request.contextPath}/Resources/js/transition.js"></script>
</head>
<body>
	<!-- Central main container -->
	<div class="container">
		<div id="login-box">
			<!-- Main logo -->
			<div class="logo">
				<img src="${pageContext.request.contextPath}/Resources/files/logo.png" class="img img-responsive center-block" />
				<!-- Shopping list caption -->
				<h1 class="logo-caption">
					<span class="tweak">R</span>EGISTER <span class="tweak">Y</span>OUR
				</h1>
				<h1 class="logo-caption">
					<span class="tweak">S</span>HOPPING <span class="tweak">L</span>IST
				</h1>
			</div><!-- logo -->
			<!-- Register form -->	
			<form class="form-singin" method="post" action="register">
				<!-- Input - username -->
				<input type="text" name="inputUsername" placeholder="Username"
					class="form-control" required autofocus> 
				<!-- Input - email -->
				<input type="email" name="inputEmail" placeholder="Email"
					class="form-control" required autofocus> 
				<!-- Input - password -->
				<input type="password" name="inputPassword" placeholder="Password"
					class="form-control" required>
				<!-- Send data from form to register controller -->
				<button type="submit" class="btn btn-default btn-block btn-login">
					Register user
				</button>
			</form><!-- controls -->
			<!-- Go to login -->
			<form action="login">
				<button type="submit" class="btn btn-default btn-block btn-register">
					Sign In
				</button>
			</form><!-- login -->
			<!-- Caption for user exist -->
			<div class="logo">
				<h3 class="logo-caption">USER <span class="tweak">EXIST</span></h3>
			</div><!-- logo -->
		</div><!-- login-box -->
	</div><!-- container -->
	<!-- Background in the page -->
	<div id="particles-js"></div>
	<!-- Script for background -->
	<script src="${pageContext.request.contextPath}/Resources/js/backgroundShape.js"></script>
</body>
</html>
