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
var directionsService = new google.maps.DirectionsService();
var directionsDisplay = [];

function initialize() {

  map = new google.maps.Map(document.getElementById('map-canvas'));

  var polylineColors = [ "red", "blue", "green", "yellow", "purple" ];
  var requests = [];

  <c:forEach items="${routes}" var="route" varStatus="idx1">

      var dd = new google.maps.DirectionsRenderer({suppressMarkers: true, polylineOptions: {strokeColor:polylineColors[${idx1.index}]}});
      dd.setMap(map);

      directionsDisplay.push(dd);

      var waypts = [];
      var start;
      var end;

      <c:forEach items="${route.route.routeLocations}" var="rl" varStatus="idx">
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

      requests.push({
          origin: start,
          destination: end,
          waypoints: waypts,
          optimizeWaypoints: true,
          travelMode: google.maps.TravelMode.DRIVING
      });

  </c:forEach>

  var k = 0;
  var reqCount = requests.length;
  for (i = 0; i < reqCount; i++) {
      directionsService.route(requests[i], function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
          directionsDisplay[k++].setDirections(response);
          addMarkers();
        }
      });
  }

}

function addMarkers() {

    var infowindow = new google.maps.InfoWindow();

    var markerIcons = [ "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
                        "http://maps.google.com/mapfiles/ms/icons/red-dot.png",
                        "http://maps.google.com/mapfiles/ms/icons/green-dot.png",
                        "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png",
                        "http://maps.google.com/mapfiles/ms/icons/purple-dot.png" ];

    <c:forEach items="${routes}" var="route" varStatus="idx1">
        <c:forEach items="${route.route.routeLocations}" var="rl" varStatus="idx">
            <c:if test="${not empty rl.routeStop}">
              var marker = new google.maps.Marker({
                    icon: markerIcons[${idx1.index}],
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
    </c:forEach>

}

google.maps.event.addDomListener(window, 'load', initialize);

</script>

<style>
html,body,.col-md-12,.row {
  height:100%;
}
</style>

</head>
<body>

    <%@ include file="/WEB-INF/jsp/admin/admin-top-bar.jsp"%>

    <div class="col-md-12" style="padding-top: 15px; padding-bottom: 15px;">
        <div class="row">
            <div class="col-sm-4" style="padding: 10px; background-color: lightgrey;">
                <c:forEach items="${routes}" var="route" varStatus="idx">
                    <div class="row" style="padding-top: 5px; padding-bottom: 5px; margin-right: 0px; margin-left: 0px;">
                        <div class="row" style="margin-right: 0px; margin-left: 0px;">
                            <h4>${idx.index + 1}&nbsp;&nbsp;${route.route.name}&nbsp;&nbsp;<a href="/admin/routes/${route.route.id}">Edit</a></h4>
                        </div>
                        <div class="row" style="margin-right: 0px; margin-left: 0px;">
                            ${route.numberOfStops} Stops
                        </div>
                        <div class="row" style="margin-right: 0px; margin-left: 0px;">
                            ${fn:length(route.routeRuns)} Runs
                        </div>
                        <div class="row" style="margin-right: 0px; margin-left: 0px;">
                            <c:if test="${route.runningStatus}">
                                <b>Currently running</b>
                            </c:if>
                            <c:if test="${not route.runningStatus && route.lastRunCompletedTimestamp gt 0}">
                                <jsp:useBean id="endTime" class="java.util.Date" />
                                <jsp:setProperty name="endTime" property="time" value="${route.lastRunCompletedTimestamp}" />
                                <b>Run last completed @ <fmt:formatDate value="${endTime}" pattern="MM/dd/yyyy HH:mm" /></b>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="col-sm-8" style="height: 100%">
                <div id="map-canvas" style="width: 100%; height: 100%;"></div>
            </div>
        </div>
    </div>

</body>
</html>