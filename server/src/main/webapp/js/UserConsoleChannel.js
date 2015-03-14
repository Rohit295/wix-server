/**
 * All Code related to channel opening for each USer Console page is driven out of here
 */

var channel;

/**
 * startMonitoringRoute: called when user wants to start monitoring any kind of asset
 */
function startMonitoringRoute() {
	channel = goog.appengine.channel("${channelID}");
	var socket = channel.open();
	socket.onopen = socket_onOpen;
	socket.onmessage = socket_onMessage;
    socket.onerror = socket_onError;
    socket.onclose = socket_onClose;				
}

/**
 * @param routeID
 * 
 * Send back the specific route to track. It is possible that the same user wants to track more than one asset
 * at the same time, so this method is really used to call back to the server and add one more route for this user
 */
function sendRouteToTrack(routeID) {
	var path = "" + routeID;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', path, true);
	xhr.send();
}

/**
 * 
 * 
 */
function socket_onOpen{
	
}

/**
 * @param locationMessage
 * @returns
 * 
 * Main Business method. Every time this method is called, it means that the server is pushing some data for the client to
 * consume. It can be one of
 * (a) route location stops for a specific asset
 * 
 */
function socket_onMessage(message) {
	
}

function socket_onError
