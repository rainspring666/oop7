package oop.main;

import java.sql.Timestamp;

public class Doc {//文件信息类
	private String ID;//档案号
	private String creator;//创作者
	private Timestamp timestamp;//貌似是用来保存时间的
	private String description;//文档描述
	private String filename;//文件名字
	
	public Doc(String ID, String creator, Timestamp timestamp, String description, String filename) {
		super();//可要可不要
		this.ID = ID;
		this.creator = creator;
		this.timestamp = timestamp;
		this.description = description;
		this.filename=filename;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}



