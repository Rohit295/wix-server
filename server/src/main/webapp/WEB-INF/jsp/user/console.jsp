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
	   	<script type="text/javascript" src="/js/UserConsoleChannel.js"></script>
		<script type="text/javascript">
			function initialize() {
				var mapOptions = {
				  center: { lat: -34.397, lng: 150.644},
				  zoom: 8
				};
			  	var map = new google.maps.Map(document.getElementById('UCMapCanvas'), mapOptions);
			}
			google.maps.event.addDomListener(window, 'load', initialize);
		</script>
 
	</head>
	<body>
		<div id="UCMapCanvas" class="UCMapCanvas"></div>
		Rohit Mani
		
	</body>
</html>