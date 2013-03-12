package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.RESTDroid;

import android.content.Context;
import fr.pcreations.labs.RESTDroid.core.RESTRequest;
import fr.pcreations.labs.RESTDroid.core.WebService;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.Comment;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.User;

public class FooWebService extends WebService{

	private final static String BASE_URI = "http://foo.bar";
	
	/* Constructor must be defined for dynamic instanciation */
	public FooWebService(Context context) {
		super(context);
	}
	
	public RESTRequest<User> getUser(Class<User> clazz, int userID) {
		return get(clazz, BASE_URI + "/user/" + userID);
	}
	
	public RESTRequest<User> addUser(Class<User> clazz, User user) {
		return post(clazz, BASE_URI + "/user/add", user);
	}
	
	public RESTRequest<Comment> postComment(Class<Comment> clazz, Comment comment) {
		return post(clazz, BASE_URI + "/comment/add", comment);
	}
	
}
