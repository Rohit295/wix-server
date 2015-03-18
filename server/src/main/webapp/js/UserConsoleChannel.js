/**
 * All Code related to channel opening for each USer Console page is driven out of here
 */

/**
 * startMonitoringRoute: called when user wants to start monitoring any kind of asset
 */
function startMonitoringRoute() {
	//channel = goog.appengine.Channel("${channelID}");
	var socket = channel.open();
	socket.onopen = socket_onOpen;
	socket.onmessage = socket_onMessage;
    socket.onerror = socket_onError;
    socket.onclose = socket_onClose;				
}

/**
 * 
 * 
 */
function socket_onOpen() {
	sendRouteToTrack(listOfRouteExecution[0]);
}


/**
 * Send back the specific route to track. It is possible that the same user wants to track more than one asset
 * at the same time, so this method is really used to call back to the server and add one more route for this user

 * @param routeID
 */
function sendRouteToTrack(routeID) {
	// create the path to add current user as a Listener for a route
	var pathForAddListener = API_BASE_URL + ADD_LISTENER_CHANNEL + "?consumerToken=" + "rohit295@gmail.com";
	pathForAddListener = pathForAddListener.replace("ROUTEID", routeID);
	var xhr = new XMLHttpRequest();
	xhr.open('POST', pathForAddListener, true);
	xhr.setRequestHeader("userId", "rohit295@gmail.com");
	xhr.send();
}

/**
 * Main Business method. Every time this method is called, it means that the server is pushing some data for the client to
 * consume. It can be one of
 * (a) first time list of route location stops for a specific asset
 * (b) incremental data for the specific route that is already being tracked
 * 
 * @param locationMessage
 * @returns
 */
function socket_onMessage(message) {
	var listCoordinates = message.data;
	firstLoadOfRouteExecution(listCoordinates);
}

function firstLoadOfRouteExecution(listCoordinates) {
	// If there is something returned, then it will be for format lat1:lng1|lat2:lng2 and so on. Split the string based on the
	// | separator and pull out the lat and longs
	var listLatLong = listCoordinates.split("|");
	var index;
	var listMapPathPoints = new Array(listLatLong.length);
	for (index = 0; index < listLatLong.length; index++) {
		var latlong = listLatLong[index].split(":");
		listMapPathPoints[index] = new google.maps.LatLng(latlong[0], latlong[1]);
	}
	
	// At this point, create the polyline to add to the map
	listOfRoutePolyLines[0] = new google.maps.Polyline({
	    path: listMapPathPoints,
	    geodesic: true,
	    strokeColor: '#FF0000',
	    strokeOpacity: 1.0,
	    strokeWeight: 2
	  });
	listOfRoutePolyLines[0].setMap(mapForConsole);
}


function socket_onClose() {
	
}

function socket_onError() {
	
}
