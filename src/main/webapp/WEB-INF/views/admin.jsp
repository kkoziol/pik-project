<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Admin page</title>
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
	<div id="mainWrapper">
		<div class="my-container">
			<div class="my-card">
				<div class="my-form">
					<div align="center">
						<h1>Hi admin ${username}</h1>
					</div>
					<button onclick="location.href='logout';"
						class="btn btn-block btn-primary btn-default">
						<span>Logout </span>
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>