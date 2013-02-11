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
	
	public void getUser(RESTRequest<User> r, int userID) {
		get(r, BASE_URI + "/user/" + userID);
	}
	
	public void addUser(RESTRequest<User> r, User user) {
		post(r, BASE_URI + "/user/add", user);
	}
	
	public void postComment(RESTRequest<Comment>, Comment comment) {
		post(r, BASE_URI + "/comment/add", comment);
	}
	
}
