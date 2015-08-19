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

    <div class="col-md-12" style="padding-top: 15px; padding-bottom: 15px;">
        <div class="row">
            <div class="col-sm-4" style="padding: 10px; background-color: lightgrey;">
                <div style="font-size: 24px;">${route.name}</div>
                <div class="row" style="padding-top: 5px; padding-bottom: 5px;">
                    <div style="font-size: 18px;">Route Stops &nbsp; - &nbsp; <b><a href="">Add Stop</a></b></div>
                    <hr/>
                    <ol>
                        <c:forEach items="${route.routeLocations}" var="routeLocation" varStatus="idx">
                            <c:if test="${not empty routeLocation.routeStop}">
                                <li>${routeLocation.routeStop.name}</li>
                            </c:if>
                        </c:forEach>
                    </ol>
                </div>
                <div class="row">
                    <div style="font-size: 18px;">Route Runs &nbsp; - &nbsp; <b><a href="">Add Run</a></b></div>
                    <hr/>
                    <ol>
                        <c:forEach items="${routeRuns}" var="routeRun" varStatus="idx">
                            <li>${routeRun.executionStartTime} by ${users[routeRun.routeExecutor.userId].name}</li>
                        </c:forEach>
                    </ol>
                </div>
            </div>
            <div class="col-sm-8" style="height: 100%">
                <div id="map-canvas" style="width: 100%; height: 100%;"></div>
            </div>
        </div>
    </div>

</body>
</html>