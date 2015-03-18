<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<html style="min-width:1360px;">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    	<title>WIX - User Console</title>

		<link rel="stylesheet" href="/css/wix_new.css">
		<script type="text/javascript" src="/_ah/channel/jsapi"></script>
	   	<script type="text/javascript"
			src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBhsW91k1kk72HSr4Qqk-LxhJHfHF7IYdA">
		</script> <!-- TODO: replace the Key with the correct Key when ready -->
	   	<script type="text/javascript" src="/js/urls.js"></script>

		<script type="text/javascript">
			var mapForConsole;
			function initialize() {
				var mapOptions = {
				  center: { lat: 17.4324341, lng: 78.3612987},
				  zoom: 8
				};
			  	var mapForConsole = new google.maps.Map(document.getElementById('UCMapCanvas'), mapOptions);
			}
			google.maps.event.addDomListener(window, 'load', initialize);
			
			// Load the Channel Id from here
			var channel;
			channel = new goog.appengine.Channel("${channelID}");
			
			// store the RouteExecution IDs as a global list
			var listOfRouteExecution = new Array("${fn:length(RouteExecutions)}");
			<c:forEach items="${RouteExecutions}" var="aRouteExecution" varStatus="routesCounter">
				listOfRouteExecution["${routesCounter.index}"] = "${aRouteExecution}"
			</c:forEach>
				
			// store the locations for each Route Execution. This is an array that contains as many
			// elements as routes, this user could be interested in. Remember that the list of locations
			// itself is an array
			var listOfRoutePolyLines = new Array(listOfRouteExecution.length);
				
		</script>
	   	<script type="text/javascript" src="/js/UserConsoleChannel.js"></script>
 
	</head>
	<body>
		<div id="UCMapCanvas" class="UCMapCanvas"></div>
		Rohit Mani
		<a  href="#" onclick="startMonitoringRoute(); return false;">Start Monitoring</a>
	</body>
</html>