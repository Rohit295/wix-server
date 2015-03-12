package com.wix.server;

import com.wix.server.persistence.Device;
import com.wix.server.persistence.Track;
import com.wix.server.persistence.TrackListener;
import com.wix.server.persistence.TrackLocation;
import com.wix.server.persistence.User;
import com.wix.server.persistence.UserDevice;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes More on Objectify here :
 * https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

	static {
		ObjectifyService.register(User.class);
		ObjectifyService.register(Device.class);
		ObjectifyService.register(UserDevice.class);
		ObjectifyService.register(Track.class);
		ObjectifyService.register(TrackLocation.class);
		ObjectifyService.register(TrackListener.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}
