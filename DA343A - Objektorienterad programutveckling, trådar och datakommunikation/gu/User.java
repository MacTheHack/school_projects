package gu;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private ImageIcon profilepic;
	private int status;

	public User(String username, ImageIcon profilepic) {
		this.username=username;
		this.profilepic=profilepic;
	}

	public String getUsername() {
		return username;
	}

	public ImageIcon getProfilepic() {
		return profilepic;
	}
	public void setStatus(int status) {
		this.status=status;
	}
	public int getStatus() {
		return status;
	}
	
	
	public int hashCode() {
		return username.hashCode();
	}

	public boolean equals(Object obj) {
		return username.equals(obj);
	}

}
