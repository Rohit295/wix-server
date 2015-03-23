<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - Children</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/admin.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

</head>
<body>

    <%@ include file="/WEB-INF/jsp/admin/admin-top-bar.jsp"%>

	<div align="center" class="container">

        <h2>Children</h2>

        <a href="/admin/observables/add">Add Student</a>

		<table class="table table-striped table-condensed table-bordered table-hover">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>Route Id</th>
					<th>Route Stop Id</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${observables}" var="observable">
					<tr>
						<td align="center"><c:out value="${observable.id}" />&nbsp;&nbsp;<a href="/admin/observables/${observable.id}">Edit</a></td>
						<td align="center"><c:out value="${observable.name}" /></td>
						<td align="center"><c:out value="${routes[observable.routeId].name}" /></td>
   						<td align="center"><c:out value="${routeStops[observable.routeStopId].name}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</div>

</body>
</html>