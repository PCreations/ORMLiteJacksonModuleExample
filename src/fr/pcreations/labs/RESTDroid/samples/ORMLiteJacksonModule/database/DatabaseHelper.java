package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.daos.CommentDao;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.daos.UserDao;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.Comment;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "ormlitejackonmodule_example.db";
	// Si on change la version la base doit se mettre à jour et réinstalle toutes les tables. Cela permet de ne pas avoir à effacer les données manuellement sur le téléphone
	private static final int DATABASE_VERSION = 1;
	// DAO pour l'objet Personne - la clé dans la base est un int donc on met Integer en second
	private UserDao userDao = null;
	private CommentDao commentDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		int i = 1;
		try {
			// Ici on doit mettre toutes les tables de notre base en lui envoyant sa classe associée
			TableUtils.createTable(connectionSource, Comment.class); i++;
			TableUtils.createTable(connectionSource, User.class); i++;
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create tables " + i, e);
		}
		Log.i(DatabaseHelper.class.getName(), "All tables has been created");
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			// On détruit toutes les tables et leur contenu
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, Comment.class, true);

			// Puis on les recrée
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
