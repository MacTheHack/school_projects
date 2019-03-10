package test;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private ImageIcon profilepic;

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
	
	public int hashCode() {
		return username.hashCode();
	}

	public boolean equals(Object obj) {
		return username.equals(obj);
	}

}
