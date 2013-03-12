package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule;

import java.sql.Date;
import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.ormlitejacksonmoduleexample.R;

import fr.pcreations.labs.RESTDroid.core.RESTDroid;
import fr.pcreations.labs.RESTDroid.core.RESTRequest;
import fr.pcreations.labs.RESTDroid.core.RequestListeners;
import fr.pcreations.labs.RESTDroid.exceptions.DatabaseManagerNotInitializedException;
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
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.init(getApplicationContext());
        RESTDroid.init(getApplicationContext());
        try {
			ws = (FooWebService) RESTDroid.getInstance().getWebService(FooWebService.class);
			getUserRequest = ws.getUser(User.class, 5); //retrieve from the server the user with id 5
			User fooUser = DatabaseManager.getInstance().getHelper().getUserDao().findById(4); //retrieve the User with id 4 in local database
			addCommentRequest = ws.postComment(Comment.class, new Comment("My first comment", "This is my first comment !", Date.valueOf("2013-02-11"), fooUser));
			getUserRequest.setRequestListeners(this, GetUserRequestListeners.class);
			addCommentRequest.setRequestListeners(this, AddCommentRequestListeners.class);
			ws.executeRequest(getUserRequest);
			ws.executeRequest(addCommentRequest);
			
		} catch (RESTDroidNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseManagerNotInitializedException e) {
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
    
    public class GetUserRequestListeners extends RequestListeners {
    	
    	private OnStartedRequestListener onStarted = new OnStartedRequestListener() {

    		public void onStartedRequest() {
    			Log.i("foo", "getUserRequest has started !");
    		}
    		
    	};
    	
    	private OnFailedRequestListener onFailed = new OnFailedRequestListener() {

    		public void onFailedRequest(int resultCode) {
    			Log.e("foo", "Unfortunately getUserRequest failed with result code " + resultCode);
    		}
    		
    	};
    	
    	private OnFinishedRequestListener onFinished = new OnFinishedRequestListener() {

    		public void onFinishedRequest(int resultCode) {
    			Log.i("foo", "getUserRequest has finished with result code " + resultCode);
    			user = (User) mRequest.getResourceRepresentation();
    		}
    		
    	};
    	
    	public GetUserRequestListeners() {
    		super();
    		addOnStartedRequestListener(onStarted);
    		addOnFailedRequestListener(onFailed);
    		addOnFinishedRequestListener(onFinished);
    	}
    }
    
    public class AddCommentRequestListeners extends RequestListeners {
    	
    	private OnStartedRequestListener onStarted = new OnStartedRequestListener() {

    		public void onStartedRequest() {
    			Log.i("foo", "addCommentRequest has started !");
    		}
    		
    	};
    	
    	private OnFailedRequestListener onFailed = new OnFailedRequestListener() {

    		public void onFailedRequest(int resultCode) {
    			Log.e("foo", "Unfortunately addCommentRequest failed with result code " + resultCode);
    		}
    		
    	};
    	
    	private OnFinishedRequestListener onFinished = new OnFinishedRequestListener() {

    		public void onFinishedRequest(int resultCode) {
    			Log.i("foo", "addCommentRequest has finished with result code " + resultCode);
    		}
    		
    	};
    	
    	public AddCommentRequestListeners() {
    		super();
    		addOnStartedRequestListener(onStarted);
    		addOnFailedRequestListener(onFailed);
    		addOnFinishedRequestListener(onFinished);
    	}
    }
    
}
