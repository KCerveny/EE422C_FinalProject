package shared;
import java.io.Serializable;

public class Message implements Serializable {
	private static final long serialVersionUID = -1841437677212743569L;

	private final String text;
	private final int funNumber;
	
	public Message(String text, int funNumber) {
		this.text = text;
		this.funNumber = funNumber;
	}
	
	String getText() {
		return text;
	}

	int getFunNumber() {
		return funNumber;
	}

	@Override
	public String toString() {
		return "Message [text=" + text + ", funNumber=" + funNumber + "]";
	}
}
