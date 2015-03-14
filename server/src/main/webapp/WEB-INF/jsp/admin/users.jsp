<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - Users</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/admin.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

</head>
<body>

    <%@ include file="/WEB-INF/jsp/admin/admin-top-bar.jsp"%>

    <div align="center" class="container">

        <h2>Users</h2>

        <a href="/admin/users/add">Add User</a>

		<table class="table table-striped table-condensed table-bordered table-hover">
			<thead>
				<tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Admin Role</th>
				</tr>
			</thead>
			<tbody>
                <c:forEach items="${accounts}" var="account">
                    <tr>
                        <td align="center"><c:out value="${account.id}" />&nbsp;&nbsp;<a href="/admin/users/${account.id}">Edit</a></td>
                        <td align="center"><c:out value="${account.name}" /></td>
                        <td align="center"><c:out value="${account.userName}" /></td>
                        <td align="center"><c:out value="${account.emailId}" /></td>
                        <td align="center"><c:out value="${account.adminRole}" /></td>
                    </tr>
                </c:forEach>
			</tbody>
		</table>

    </div>

</body>
</html>
