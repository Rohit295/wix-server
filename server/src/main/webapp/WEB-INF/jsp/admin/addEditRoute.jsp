<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

<title>Admin - Routes</title>

<link rel="stylesheet" href="/bootstrap-3.3.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/admin.css">

<script type="text/javascript" src="/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="/bootstrap-3.3.0/js/bootstrap.min.js"></script>

<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>

<script>

var map;
var directionsDisplay;
var directionsService = new google.maps.DirectionsService();

function initialize() {

  directionsDisplay = new google.maps.DirectionsRenderer({suppressMarkers: true});

  var mapOptions = {
    zoom: 13
  }

  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
  directionsDisplay.setMap(map);

  var waypts = [];
  var start;
  var end;

  <c:forEach items="${route.routeLocations}" var="rl" varStatus="idx">
       <c:choose>
            <c:when test="${idx.first}">
                start = new google.maps.LatLng("${rl.location.latitude}", "${rl.location.longitude}");
            </c:when>
            <c:when test="${idx.last}">
                end = new google.maps.LatLng("${rl.location.latitude}", "${rl.location.longitude}");
            </c:when>
            <c:otherwise>
                waypts.push({
                      location:new google.maps.LatLng("${rl.location.latitude}", "${rl.location.longitude}"),
                      stopover:true
                });
            </c:otherwise>
      </c:choose>
  </c:forEach>

  var request = {
      origin: start,
      destination: end,
      waypoints: waypts,
      optimizeWaypoints: true,
      travelMode: google.maps.TravelMode.DRIVING
  };

  directionsService.route(request, function(response, status) {
    if (status == google.maps.DirectionsStatus.OK) {
      directionsDisplay.setDirections(response);
      addMarkers();
    }
  });

}

function addMarkers() {

    var infowindow = new google.maps.InfoWindow();

    <c:forEach items="${route.routeLocations}" var="rl" varStatus="idx">
        <c:if test="${not empty rl.routeStop}">
          var marker = new google.maps.Marker({
                position: new google.maps.LatLng("${rl.location.latitude}", "${rl.location.longitude}"),
                map: map,
                title: '${rl.routeStop.name}'
            });
            google.maps.event.addListener(marker, 'click', function() {
                var contentStr = '<div><h3>${rl.routeStop.name}</h3><h6>${rl.routeStop.address}</h6></div>';
                infowindow.setContent(contentStr);
                infowindow.open(map,this);
            });
        </c:if>
    </c:forEach>
}

google.maps.event.addDomListener(window, 'load', initialize);

</script>

</head>
<body>

    <%@ include file="/WEB-INF/jsp/admin/admin-top-bar.jsp"%>

	<div align="center" class="container">

        <h2>Route [${route.name}]</h4>

        <h4>Route Executions</h4>

		<table class="table table-striped table-condensed table-bordered table-hover">
			<thead>
				<tr>
					<th>Id</th>
					<th>Executing User Id</th>
					<th>Start Time</th>
					<th>End Time</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${routeExecutions}" var="routeExecution">
                    <tr>
                        <td align="center"><c:out value="${routeExecution.id}" />
                            &nbsp;&nbsp;<a href="/admin/routeexecution/${routeExecution.id}">Monitor</a>
                        </td>
                        <td align="center"><c:out value="${routeExecution.routeExecutor.userId}" /></td>
                        <jsp:useBean id="startDateObject" class="java.util.Date" />
                        <jsp:setProperty name="startDateObject" property="time" value="${routeExecution.startTime}" />
                        <td align="center"><fmt:formatDate value="${startDateObject}" pattern="MM/dd/yyyy HH:mm" /></td>
                        <td align="center"><c:out value="${routeExecution.endTime}" /></td>
                    </tr>
                </c:forEach>
			</tbody>
		</table>

        <h4>Configured Route</h4>

        <div id="map-canvas" style="width:1000px; height:800px;"></div>

	</div>

</body>
</html>