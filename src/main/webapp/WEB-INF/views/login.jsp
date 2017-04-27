<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Login page</title>
<link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
<style>
h1 {
	color: white;
	font-family: 'Helvetica Neue', sans-serif;
	font-size: 46px;
	font-weight: 100;
	line-height: 50px;
	letter-spacing: 1px;
	padding: 0 0 5px;
	border-bottom: double #555;
}
</style>
</head>

<body onload='document.f.j_username.focus();' bgcolor="orange">
	<div id="mainWrapper">
		<div class="my-container">
			<div class="my-card">
				<div class="my-form">

					<c:url var="loginUrl" value="/login" />
					<form action="${loginUrl}" method="post" class="form-horizontal">
						<div align="center">
							<h1>Sign In</h1>
						</div>
						<c:if test="${param.error != null}">
							<div class="alert alert-danger">
								<p>Invalid username or password.</p>
							</div>
						</c:if>
						<c:if test="${param.logout != null}">
							<div class="alert alert-success">
								<p>You have been logged out successfully.</p>
							</div>
						</c:if>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="username"><i
								class="fa fa-user"></i></label> <input type="text" class="form-control"
								id="username" name="ssoId" placeholder="Enter Username" required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="password"><i
								class="fa fa-lock"></i></label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="Enter Password" required>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<div class="form-actions">
							<input type="submit"
								class="btn btn-block btn-primary btn-default" value="Log in">
						</div>
						<button onclick="location.href='index';"
							class="btn btn-block btn-primary btn-default">
							<span>Back</span>
						</button>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>