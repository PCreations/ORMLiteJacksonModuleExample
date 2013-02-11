# Implementation example of RESTDroid with ORMLiteJacksonModule

This following README will guides you throughout the implementation of RESTDroid in a little application using ORMLite to store object in database and Jackson to parse them. If you're not familiar with RESTDroid concepts, you should take a look to [beginner user guide](https://github.com/PCreations/RESTDroid).

## Getting started

Our little application is quite simple, we have User and Comments. User has many Comments and Comment belongs to User. User is defined by username, password and a list of Comment. Comment is defined by an User, a title, a content and a date. All that stuff has to be stored locally and remotely synchronised.

First of all, download or clone [RESTDroid](https://github.com/PCreations/RESTDroid) and [ORMLiteJacksonModule](https://github.com/PCreations/ORMLiteJacksonModule).

Link them as Library to your Android project and update the android manifest :

<pre>
<code>
&lt;uses-permission android:name="android.permission.INTERNET" />
&lt;application
        ...
        &lt;activity
            ...
        &lt;/activity>
        &lt;service android:enabled="true" android:name="fr.pcreations.labs.RESTDroid.core.RestService">&lt;/service>
    &lt;/application>
&lt;service android:enabled="true" android:name="fr.pcreations.labs.RESTDroid.core.RestService">&lt;/service>
</code>
</pre>

## Create item classes

Now, let's create the Item and Comment class :

### User class :

<pre>
<code>
@DatabaseTable(tableName = "users", daoClass=UserDao.class)
public class User implements ResourceRepresentation&lt;Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1923466464769701666L;

	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField
	private String username;
	
	@DatabaseField
	private String password;
	
	@ForeignCollectionField(eager = true)
	private Collection&lt;Comment> comments = null;
	
	/* Fields for data persistence */
	
	@DatabaseField
	private int state;
	
	@DatabaseField
	private int resultCode;
	
	@DatabaseField
	private boolean transactingFlag;
	
	public User() {}
	
	public User(int id, String username, String password, int state,
			int resultCode, boolean transactingFlag) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.state = state;
		this.resultCode = resultCode;
		this.transactingFlag = transactingFlag;
	}

	public Integer getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Collection&lt;Comment> getComments() {
		ArrayList&lt;Comment> comments = new ArrayList&lt;Comment>();
		for(Comment comment : this.comments) {
			comments.add(comment);
		}
		return comments;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setComments(Collection&lt;Comment> comments) {
		this.comments = comments;
	}

	public int getState() {
		return state;
	}

	public int getResultCode() {
		return resultCode;
	}

	public boolean getTransactingFlag() {
		return transactingFlag;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setTransactingFlag(boolean transacting) {
		transactingFlag = transacting;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
</code>
</pre>


### Comment class

<pre>
<code>
@DatabaseTable(tableName="comments", daoClass=CommentDao.class)
public class Comment implements ResourceRepresentation&lt;Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537299509007827908L;
	
	public static final String USER_ID = "user_id";

	@DatabaseField(id=true)
	private int id;
	
	@DatabaseField
	private String title;
	
	@DatabaseField
	private String content;
	
	@DatabaseField
	private Date date;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = USER_ID)
	private User user;
	
	/* Fields for data persistence */
	
	@DatabaseField
	private int state;
	
	@DatabaseField
	private int resultCode;
	
	@DatabaseField
	private boolean transactingFlag;
	
	public Comment() {}
	
	public Comment(int id, String title, String content, Date date, User user) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public int getState() {
		return state;
	}

	public int getResultCode() {
		return resultCode;
	}

	public boolean getTransactingFlag() {
		return transactingFlag;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setTransactingFlag(boolean transacting) {
		transactingFlag = transacting;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
</code>
</pre>

Item classes must implements ResourceRepresentation. To handle data persistence we have to add few fields to our objects.

Let's now create the Dao classes

## Database Access Object classes

Database Access Object classes handle the reading and writing a class from the database.

### UserDao class

<pre>
<code>
public class UserDao extends BaseDaoImpl&lt;User, Integer> implements DaoAccess&lt;User>{

	public UserDao(ConnectionSource connectionSource)
		throws SQLException {
		super(connectionSource, User.class);
	}

	public void updateOrCreate(User resource) throws SQLException {
		CommentDao commentDao;
		try {
			commentDao = DatabaseManager.getInstance().getHelper().getNoteDao();
			for(Comment c : resource.getComments()) {
				c.setUser(resource);
				try {
					commentDao.updateOrCreate(c);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			createOrUpdate(resource);
		} catch (DatabaseManagerNotInitializedException e1) {
			e1.printStackTrace();
		}
	}

	public int deleteResource(User resource) throws SQLException {
		return delete(resource);
	}

	public int updateResource(User resource) throws SQLException {
		return update(resource);
	}

	public &lt;ID> User findById(ID resourceId) throws SQLException {
		return queryForId((Integer) resourceId);
	}
	
}
</code>
</pre>

### CommentDao class

<pre>
<code>
public class CommentDao extends BaseDaoImpl&lt;Comment, Integer> implements DaoAccess&lt;Comment> {

	public CommentDao(ConnectionSource connectionSource)
		throws SQLException {
		super(connectionSource, Comment.class);
	}

	public void updateOrCreate(Comment resource) throws SQLException {
		createOrUpdate(resource);
	}

	public &lt;ID> Comment findById(ID resourceId) throws SQLException {
		return queryForId((Integer) resourceId);
	}

	public int deleteResource(Comment resource) throws SQLException {
		return delete(resource);
	}

	public int updateResource(Comment resource) throws SQLException {
		return update(resource);
	}

}
</code>
</pre>

## ORMLite routine classes

We now need a DatabaseManager and a DatabaseHelper :

### DatabaseHelper :

<pre>
<code>
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "ormlitejackonmodule_example.db";
	private static final int DATABASE_VERSION = 1;

	private UserDao userDao = null;
	private CommentDao commentDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		int i = 1;
		try {
			TableUtils.createTable(connectionSource, Address.class); i++;
			TableUtils.createTable(connectionSource, Note.class); i++;
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create tables " + i, e);
		}
		Log.i(DatabaseHelper.class.getName(), "All tables has been created");
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, Comment.class, true);
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't update from version " + oldVer + " to " + newVer, e);
		}
	}
	
	public UserDao getUserDao() {
		if(null == userDao) {
			try {
				userDao = DaoManager.createDao(getConnectionSource(), Address.class);
			}catch(java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return userDao;
	}
	
	
	public CommentDao getCommentDao() {
		if(null == commentDao) {
			try {
				commentDao = DaoManager.createDao(getConnectionSource(), Note.class);
			}catch(java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return commentDao;
	}
}
</code>
</pre>

### DatabaseManager

<pre>
<code>
public class DatabaseManager {
	 
	static private DatabaseManager instance;
 
	static public void init(Context context) {
		if(null==instance) {
			instance = new DatabaseManager(context);
		}
	}
 
	static public DatabaseManager getInstance() {
		return instance;
	}
 
	private DatabaseHelper helper;
 
	private DatabaseManager(Context context) {
		helper = new DatabaseHelper(context);
	}
 
	public DatabaseHelper getHelper() throws DatabaseManagerNotInitializedException {
		if(null == instance)
			throw new DatabaseManagerNotInitializedException();
		return helper;
	}
}
</code>
</pre>

## The WebService helper

Now we have to create our own WebService helper class :

<pre>
<code>
public class FooWebService extends WebService{

	private final static String BASE_URI = "http://foo.bar";
	
	/* Constructor must be defined for dynamic instanciation */
	public FooWebService(Context context) {
		super(context);
	}
	
	public void getUser(RESTRequest&lt;User> r, int userID) {
		get(r, BASE_URI + "/user/" + userID);
	}
	
	public void addUser(RESTRequest&lt;User> r, User user) {
		post(r, BASE_URI + "/user/add", user);
	}
	
	public void postComment(RESTRequest&lt;Comment>, Comment comment) {
		post(r, BASE_URI + "/comment/add", comment);
	}
	
}
</code>
<pre>

And we're done ! 

## Handling request in User Interface

RESTDroid provides a simple way to handle request in User Interface via request listeners :

*	OnStartedRequestListener : triggered when the request starts
*	OnFailedRequestListener : triggered when the request has failed
*	OnFinishedRequestListener : triggered when the request is finished

You can add any listeners as you want for one specific request.

Here is the workflow to deals with RESTRequest in User Interface :

### Activity

<pre>
<code>
public class ORMLiteJacksonModuleExample extends Activity {

	private FooWebService ws;
	private User user;
	private RESTRequest&lt;User> getUserRequest;
	private RESTRequest&lt;Comment> addCommentRequest;
	
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
			
			/* Initialization of request */
			getUserRequest = ws.newRequest(User.class);
			addCommentRequest = ws.newRequest(Comment.class);

			getUserRequest.addOnStartedRequestListener(getUserRequestStarted);
			getUserRequest.addOnFailedRequestListener(getUserRequestFailed);
			getUserRequest.addOnFinishedRequestListener(getUserRequestFinished);
			
			addCommentRequest.addOnStartedRequestListener(addCommentRequestStarted);
			addCommentRequest.addOnFailedRequestListener(addCommentRequestFailed);
			addCommentRequest.addOnFinishedRequestListener(addCommentRequestFinished);
			
			ws.getUser(getUserRequest, 5); //retrieve from the server the user with id 5
			User fooUser = DatabaseManager.getInstance().getHelper().getUserDao().findById(4); //retrieve the User with id 4 in local database
			
			/* This will add comment in local database and add it on the server */
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
</code>
</pre>

I'm agree with you, it's a totally non sense example. The very important things to notice here are the RESTDroid initialization, how to create request and the ws.onPause() and ws.onResume() method.

