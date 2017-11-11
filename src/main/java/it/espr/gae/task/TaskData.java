package it.espr.gae.task;

import java.io.Serializable;
import java.util.UUID;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
public class TaskData {

	@Id
	private String id;

	@Serialize(zip = true)
	private Serializable data;

	public TaskData() {
		super();
	}

	public TaskData(Serializable data) {
		this(key(), data);
	}

	public TaskData(String id, Serializable data) {
		this();
		this.id = id;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public static String key() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
