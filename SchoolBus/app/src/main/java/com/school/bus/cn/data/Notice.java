package com.school.bus.cn.data;


public class Notice {
	private int id;
	private String title;

	public Notice(String content, String title, String time) {
		this.content = content;
		this.title = title;
		this.time = time;
	}

	private String time;
	private String content;
	private int driverId;

	public Notice(int id, String title, String time, String content,
			int driverId) {
		super();
		this.id = id;
		this.title = title;
		this.time = time;
		this.content = content;
		this.driverId = driverId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
