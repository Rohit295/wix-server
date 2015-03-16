<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - Accounts</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/admin.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

</head>
<body>

    <%@ include file="/WEB-INF/jsp/admin/admin-top-bar.jsp"%>

    <div class="edit-container">

        <form:form name="addEditForm" method="POST" action="/admin/users" commandName="account">

            <c:if test="${empty account.id }">
                <h2>New Account</h2>
            </c:if>
            <c:if test="${not empty account.id }">
                <h2>Edit Account</h2>
            </c:if>

            <form:hidden path="id" />

            <table class="table">
                <tbody>
                    <tr>

                        <td style="float: left;">
                            <table>
                                <tr>
                                    <td class="form-entry-key">Name:</td>
                                    <td class="form-entry-value"><form:input tabindex="1" path="name" /></td>
                                </tr>
                                <tr>
                                    <td class="form-entry-key">Username:</td>
                                    <td class="form-entry-value"><form:input tabindex="2" path="userName" /></td>
                                </tr>
                                <tr>
                                    <td class="form-entry-key">Password:</td>
                                    <td class="form-entry-value"><form:input tabindex="3" path="password" type="password"/></td>
                                </tr>
                                <tr>
                                    <td class="form-entry-key">Email:</td>
                                    <td class="form-entry-value"><form:input tabindex="4" path="emailId" /></td>
                                </tr>
                                <tr>
                                    <td class="form-entry-key">Admin Role:</td>
                                    <td class="form-entry-value"><form:checkbox tabindex="5" path="adminRole" /></td>
                                </tr>
                            </table>
                        </td>

                        <td>
                            <div style="margin-left: 25; color: red; font-size: 0.9em;" align="left">
                                <c:if test="${not empty model.errors}">
                                    <p style="padding: 5 0 0 0; margin: 0 0 0 0;">Please correct the following errors</p>
                                    <ul>
                                        <c:forEach items="${model.errors}" var="error">
                                            <li><c:out value="${error}" /></li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </div>
                        </td>

                    </tr>
                </tbody>
            </table>

            <hr width="100%" />

            <div>
                <button class="btn btn-primary btn-large" type="submit">Save</button>
                <button class="btn btn-warning btn-large" type="button" onclick="window.location.href='/admin/users'">Cancel</button>
            </div>

        </form:form>

    </div>

</body>
</html>