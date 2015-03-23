<nav class="navbar navbar-inverse navbar-fixed-top">

  <div class="container-fluid">

    <div class="navbar-header">

      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>

      <a class="navbar-brand" href="/admin/index">WhereisX</a>

    </div>

    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/admin/profile">${userInfo.name}</a></li>
        <!--  li><a href="javascript:$.post('/logout');">Logout</a></li-->
        <li><a href="<c:url value="/logout" />">Logout</a></li>
      </ul>
    </div>

  </div>

  <div class="row container-fluid" style="background: grey">

     <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav navbar-left">
            <li <c:if test="${activeTab == 'index'}">class="active"</c:if>><a href="/admin/index">Home</a></li>
            <li <c:if test="${activeTab == 'routes'}">class="active"</c:if>><a href="/admin/routes">Routes</a></li>
            <li <c:if test="${activeTab == 'observables'}">class="active"</c:if>><a href="/admin/observables">Students</a></li>
            <li <c:if test="${activeTab == 'users'}">class="active"</c:if>><a href="/admin/users">Users</a></li>
            <li <c:if test="${activeTab == 'analytics'}">class="active"</c:if>><a href="/admin/analytics">Analytics</a></li>
          </ul>
     </div>

  </div>

</nav>