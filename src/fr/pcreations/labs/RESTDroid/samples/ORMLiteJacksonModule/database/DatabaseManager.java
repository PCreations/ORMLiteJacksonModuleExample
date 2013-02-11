package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.database;

import android.content.Context;
import fr.pcreations.labs.RESTDroid.exceptions.DatabaseManagerNotInitializedException;

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
