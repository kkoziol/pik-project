<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Register page</title>
<link href="<c:url value='/css/bootstrap.css' />" rel="stylesheet"></link>
<link href="<c:url value='/css/app.css' />" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#datepicker").datepicker();
	});
</script>
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

<body>

	<c:url var="addAction" value="/user/add"></c:url>
	<div id="mainWrapper">
		<div class="my-container">
			<div class="my-card">
				<div class="my-form">
					<c:url var="loginUrl" value="/login" />
					<form:form action="${addAction}" method="post"
						class="form-horizontal" commandName="user">
						<div align="center">
							<h1>Registration</h1>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for=Login> <i
								class="fa fa-user"></i>
							</label>
							<form:input path="login" type="text" class="form-control"
								placeholder="Enter Login" />
							<c:if test="${pageContext.request.method=='POST'}">
								<form:errors path="login" cssClass="error" />
							</c:if>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="Password"><i
								class="fa fa-lock"></i></label>
							<form:input path="password" type="password" class="form-control"
								placeholder="Enter Password" required="true" />
							<c:if test="${pageContext.request.method=='POST'}">
								<form:errors path="password" cssClass="error" />
							</c:if>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for=Name> <i
								class="fa fa-address-card"></i>
							</label>
							<form:input path="name" type="text" class="form-control"
								placeholder="Enter Name" />
							<c:if test="${pageContext.request.method=='POST'}">
								<form:errors path="name" cssClass="error" />
							</c:if>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for=Surname> <i
								class="fa fa-address-card"></i>
							</label>
							<form:input path="surname" type="text" class="form-control"
								placeholder="Enter Surname" />
							<c:if test="${pageContext.request.method=='POST'}">
								<form:errors path="surname" cssClass="error" />
							</c:if>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for=Email> <i
								class="fa fa-envelope"></i>
							</label>
							<form:input path="email" type="text" class="form-control"
								placeholder="Enter Email" required="true" />
							<c:if test="${pageContext.request.method=='POST'}">
								<form:errors path="email" cssClass="error" />
							</c:if>
						</div>

						<div class="input-group input-sm">
							<label class="input-group-addon" for=dateOfBirth> <i
								class="fa fa-calendar"></i>
							</label>
							<form:input path="dateOfBirth" type="text" class="form-control"
								id="datepicker" placeholder="Enter birth date" />
							<c:if test="${pageContext.request.method=='POST'}">
								<form:errors path="dateOfBirth" cssClass="error" />
							</c:if>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for=sex> <i
								class="fa fa-venus-mars"></i>
							</label>
							<form:input path="sex" type="text" class="form-control"
								placeholder="Enter Sex" />
							<c:if test="${pageContext.request.method=='POST'}">
								<form:errors path="sex" cssClass="error" />
							</c:if>
						</div>
						<div class="form-actions">
							<input type="submit"
								class="btn btn-block btn-primary btn-default" value="Join us!">
						</div>
						<button onclick="location.href='index';"
							class="btn btn-block btn-primary btn-default">
							<span>Back</span>
						</button>
					</form:form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>