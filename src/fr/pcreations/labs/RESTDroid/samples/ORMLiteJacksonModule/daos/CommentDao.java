package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.daos;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import fr.pcreations.labs.RESTDroid.core.DaoAccess;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models.Comment;

public class CommentDao extends BaseDaoImpl<Comment, Integer> implements DaoAccess<Comment> {

	public CommentDao(ConnectionSource connectionSource)
		throws SQLException {
		super(connectionSource, Comment.class);
	}

	public void updateOrCreate(Comment resource) throws SQLException {
		createOrUpdate(resource);
	}

	public <ID> Comment findById(ID resourceId) throws SQLException {
		return queryForId((Integer) resourceId);
	}

	public int deleteResource(Comment resource) throws SQLException {
		return delete(resource);
	}

	public int updateResource(Comment resource) throws SQLException {
		return update(resource);
	}

}
