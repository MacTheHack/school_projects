package p1;

import java.io.*;
import javax.swing.*;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Icon icon;
	private String text;
	
	public Message(String text, Icon icon) {
		this.text=text;
		this.icon=icon;
	}
	
	public String getText() {
		return text;
	}
	
	public Icon getIcon() {
		return icon;
	}
}
