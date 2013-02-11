package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models;

import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import fr.pcreations.labs.RESTDroid.core.ResourceRepresentation;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.daos.UserDao;

@DatabaseTable(tableName = "users", daoClass=UserDao.class)
public class User implements ResourceRepresentation<Integer>{

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
	private Collection<Comment> comments = null;
	
	/* Fields for data persistence */
	
	@DatabaseField
	private int state;
	
	@DatabaseField
	private int resultCode;
	
	@DatabaseField
	private boolean transactingFlag;
	
	public User() {}
	
	public User(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
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

	public Collection<Comment> getComments() {
		ArrayList<Comment> comments = new ArrayList<Comment>();
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

	public void setComments(Collection<Comment> comments) {
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
