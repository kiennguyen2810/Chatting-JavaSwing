package chat.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
	private int maxChar;
	
	public JTextFieldLimit(int limit) {
		super();
		this.maxChar = limit;
	}
	
	public void insertString(int offs, String str, AttributeSet a) 
									throws BadLocationException {
		if(str == null) {
			return;
		}
		if(getLength() + str.length() <= this.maxChar) {
			super.insertString(offs, str, a);
		}
	}
	
}
