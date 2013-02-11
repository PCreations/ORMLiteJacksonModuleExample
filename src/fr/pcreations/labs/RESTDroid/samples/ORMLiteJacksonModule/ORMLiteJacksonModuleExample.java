package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule;

import java.sql.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.ormlitejacksonmoduleexample.R;

import fr.pcreations.labs.RESTDroid.core.RESTDroid;
import fr.pcreations.labs.RESTDroid.core.RESTRequest;
import fr.pcreations.labs.RESTDroid.core.RESTRequest.OnFailedRequestListener;
import fr.pcreations.labs.RESTDroid.core.RESTRequest.OnFinishedRequestListener;
import fr.pcreations.labs.RESTDroid.core.RESTRequest.OnStartedRequestListener;
import fr.pcreations.labs.RESTDroid.exceptions.RESTDroidNotInitializedException;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.RESTDroid.FooWebService;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.database.DatabaseManager;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.Comment;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.User;

public class ORMLiteJacksonModuleExample extends Activity {

	private FooWebService ws;
	private User user;
	private RESTRequest<User> getUserRequest;
	private RESTRequest<Comment> addCommentRequest;
	
	private OnStartedRequestListener getUserRequestStarted = new OnStartedRequestListener() {

		public void onStartedRequest() {
			Log.i("foo", "getUserRequest has started !");
		}
		
	};
	
	private OnStartedRequestListener addCommentRequestStarted = new OnStartedRequestListener() {

		public void onStartedRequest() {
			Log.i("foo", "addCommentRequest has started !");
		}
		
	};
	
	private OnFailedRequestListener getUserRequestFailed = new OnFailedRequestListener() {

		public void onFailedRequest(int resultCode) {
			Log.e("foo", "Unfortunately getUserRequest failed with result code " + resultCode);
		}
		
	};
	
	private OnFailedRequestListener addCommentRequestFailed = new OnFailedRequestListener() {

		public void onFailedRequest(int resultCode) {
			Log.e("foo", "Unfortunately addCommentRequest failed with result code " + resultCode);
		}
		
	};
	
	private OnFinishedRequestListener getUserRequestFinished = new OnFinishedRequestListener() {

		public void onFinishedRequest(int resultCode) {
			Log.i("foo", "getUserRequest has finished with result code " + resultCode);
			user = getUserRequest.getResourceRepresentation();
		}
		
	};
	
	private OnFinishedRequestListener addCommentRequestFinished = new OnFinishedRequestListener() {

		public void onFinishedRequest(int resultCode) {
			Log.i("foo", "addCommentRequest has finished with result code " + resultCode);
		}
		
	};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.init(getApplicationContext());
        RESTDroid.init(getApplicationContext());
        try {
			ws = (FooWebService) RESTDroid.getInstance().getWebService(FooWebService.class);
			
			getUserRequest.addOnStartedRequestListener(getUserRequestStarted);
			getUserRequest.addOnFailedRequestListener(getUserRequestFailed);
			getUserRequest.addOnFinishedRequestListener(getUserRequestFinished);
			
			addCommentRequest.addOnStartedRequestListener(addCommentRequestStarted);
			addCommentRequest.addOnFailedRequestListener(addCommentRequestFailed);
			addCommentRequest.addOnFinishedRequestListener(addCommentRequestFinished);
			
			ws.getUser(getUserRequest, 5); //retrieve from the server the user with id 5
			User fooUser = DatabaseManager.getInstance().getHelper().getUserDao().findById(4); //retrieve the User with id 4 in local database
			ws.postComment(addCommentRequest, new Comment("My first comment", "This is my first comment !", Date.valueOf("2013-02-11"), fooUser));
			
		} catch (RESTDroidNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        setContentView(R.layout.activity_ormlite_jackson_module_example);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	ws.onPause();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	ws.onResume();
    }
    

}
