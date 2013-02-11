package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.daos;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import fr.pcreations.labs.RESTDroid.core.DaoAccess;
import fr.pcreations.labs.RESTDroid.exceptions.DatabaseManagerNotInitializedException;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.database.DatabaseManager;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.Comment;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.User;

public class UserDao extends BaseDaoImpl<User, Integer> implements DaoAccess<User>{

	public UserDao(ConnectionSource connectionSource)
		throws SQLException {
		super(connectionSource, User.class);
	}

	public void updateOrCreate(User resource) throws SQLException {
		CommentDao commentDao;
		try {
			commentDao = DatabaseManager.getInstance().getHelper().getCommentDao();
			for(Comment c : resource.getComments()) {
				c.setUser(resource);
				try {
					commentDao.updateOrCreate(c);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			createOrUpdate(resource);
		} catch (DatabaseManagerNotInitializedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public int deleteResource(User resource) throws SQLException {
		return delete(resource);
	}

	public int updateResource(User resource) throws SQLException {
		return update(resource);
	}

	public <ID> User findById(ID resourceId) throws SQLException {
		return queryForId((Integer) resourceId);
	}
	
}
