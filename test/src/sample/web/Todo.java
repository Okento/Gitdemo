package sample.web;

import java.sql.Timestamp;

public class Todo {
	
	Integer id;
	String title;
	String task;
	Timestamp limitdate;
	Timestamp lastupdate;
	String userid;
	Integer status;
	
	public String getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTask() {
		return task;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}

}
