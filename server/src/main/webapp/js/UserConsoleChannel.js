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
 * (a) route location stops for a specific asset
 * 
 * @param locationMessage
 * @returns
 */
function socket_onMessage(message) {
	var listCoordinates = message.data;

	// If there is something returned, then it will be for format lat1:lng1|lat2:lng2 and so on. Split the string based on the
	// | separator and pull out the lat and longs
	var listLatLong = listCoordinates.split("|");
	
}


function socket_onClose() {
	
}

function socket_onError() {
	
}
