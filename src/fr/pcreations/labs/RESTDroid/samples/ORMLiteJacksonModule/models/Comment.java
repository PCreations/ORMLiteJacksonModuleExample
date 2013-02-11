package fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.models;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.pcreations.labs.RESTDroid.core.ResourceRepresentation;
import fr.pcreations.labs.RESTDroid.samples.ORMLiteJacksonModule.daos.CommentDao;

@DatabaseTable(tableName="comments", daoClass=CommentDao.class)
public class Comment implements ResourceRepresentation<Integer>{

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
	
	public Comment(String title, String content, Date date, User user) {
		super();
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
