<%--Created by Yaroslav Bodyak on 11.12.2018--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
	<head>
		<title>Login Page</title>
		<link rel="stylesheet" type="text/css" href="/css/login.css" />
	</head>
	<body>
		<div style = "position:absolute; left:100px; top:200px;">
			<form name="loginForm" method="POST" action="controller">
				<fieldset>
					<legend align="center">Login Form</legend>
					<input type="hidden" name="command" value="login"/>
					Login:<br/><input class="inputElement" type="text" name="login" value=""/><br/>
					Password:<br/><input class="inputElement" type="password" name="password" value=""/><br/><br/>
					<input type="submit" value="Log In"/>
					<input type="button" value="Registration" onclick='location.href="controller?command=gotoregistration"'/>
				</fieldset>
				<div class="errorMessage">
					</br>${errorLoginOrPassword}
				</div>
			</form>
		</div>
	</body>
</html>
