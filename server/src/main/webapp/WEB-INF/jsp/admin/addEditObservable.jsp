<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - Students</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/admin.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

</head>
<body>

    <%@ include file="/WEB-INF/jsp/admin/admin-top-bar.jsp"%>

    <div class="edit-container">

        <form:form name="addEditForm" method="POST" action="/admin/observables" commandName="observable">

            <c:if test="${empty observable.id }">
                <h2>New Student</h2>
            </c:if>
            <c:if test="${not empty observable.id }">
                <h2>Edit Student</h2>
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
                                    <td class="form-entry-key">Route Id:</td>
                                    <td class="form-entry-value"><form:input tabindex="2" path="routeId" /></td>
                                </tr>
                                <tr>
                                    <td class="form-entry-key">Route Stop Id:</td>
                                    <td class="form-entry-value"><form:input tabindex="3" path="routeStopId"/></td>
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
                <button class="btn btn-warning btn-large" type="button" onclick="window.location.href='/admin/observables'">Cancel</button>
            </div>

        </form:form>

    </div>

</body>
</html>