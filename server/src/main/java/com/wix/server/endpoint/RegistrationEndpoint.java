package com.wix.server.endpoint;

import com.wix.common.model.UserInfo;
import com.wix.server.persistence.Device;
import com.wix.server.persistence.User;
import com.wix.server.persistence.UserDevice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import static com.wix.server.OfyService.ofy;

@RestController
@RequestMapping("/services/v1/*")
public class RegistrationEndpoint {

	private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public UserInfo login(@RequestParam("emailId") String emailId) {

		User user = findUserByEmailId(emailId);
		if (user == null) {
			user = new User();
			user.setEmailId(emailId);
			ofy().save().entity(user).now();
		}

		return user.getInfo();

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(@RequestParam("userId") String userId, @RequestParam("deviceId") String deviceId,
			@RequestParam("gcmRegistrationId") String gcmRegistrationId) {

		if (findDeviceByGcmRegistrationId(gcmRegistrationId) == null) {

			Device record = new Device();
			record.setId(deviceId);
			record.setGcmRegistrationId(gcmRegistrationId);

			ofy().save().entity(record).now();

		}

		// TODO this is not clear!!!
		User user = findUserByUserId(userId);
		if (user == null) {
			throw new RuntimeException("User with [" + userId + "] does not exist");
		}

		UserDevice userDevice = findUserDeviceByUserId(userId);
		if (userDevice == null) {
			userDevice = new UserDevice();
			userDevice.setUserId(userId);
		}
		userDevice.setDeviceId(deviceId);

		ofy().save().entity(userDevice).now();

	}

	private User findUserByUserId(String userId) {
		return ofy().load().type(User.class).filter("userId", userId).first().now();
	}

	private User findUserByEmailId(String emailId) {
		return ofy().load().type(User.class).filter("emailId", emailId).first().now();
	}

	private UserDevice findUserDeviceByUserId(String userId) {
		return ofy().load().type(UserDevice.class).filter("userId", userId).first().now();
	}

	private Device findDeviceByGcmRegistrationId(String gcmRegistrationId) {
		return ofy().load().type(Device.class).filter("gcmRegistrationId", gcmRegistrationId).first().now();
	}

}