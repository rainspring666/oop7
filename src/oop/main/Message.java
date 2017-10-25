package oop.main;

import java.io.Serializable;
import java.util.Vector;

public class Message  implements Serializable {
	String type;
	Vector content;
	
	public Message(String type, Vector content) {
		super();
		this.type = type;
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vector getContent() {
		return content;
	}

	public void setContent(Vector content) {
		this.content = content;
	}
 
}