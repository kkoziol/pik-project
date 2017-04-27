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

	<c:url var="search" value="/search"></c:url>
	<div id="mainWrapper">
		<div class="my-container">
			<div class="my-card">
				<div class="my-form">
					<c:url var="searchUrl" value="/search" />
					<form:form action="${search}" method="post" commandName="usersQuery" class="form-horizontal">
						<div align="center">
							<h1>Searching</h1>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="Search"> <i
								class="fa fa-user"></i>
							</label>
							<form:input path="query" type="text" class="form-control"
								placeholder="Enter query" />
						</div>
						<div class="form-actions">
							<input type="submit"
								class="btn btn-block btn-primary btn-default" value="Search!">
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>


</body>
</html>