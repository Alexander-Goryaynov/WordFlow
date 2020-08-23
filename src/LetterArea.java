
public class LetterArea {
	
	private char letterName;
	private int leftX;
	private int rightX;
	private int upY;
	private int downY;
	
	public LetterArea(char letterName, int leftX, int rightX, int upY, int downY) {
		this.letterName = letterName;
		this.leftX = leftX;
		this.rightX = rightX;
		this.upY = upY;
		this.downY = downY;
	}
	
	public char getLetterName() {
		return letterName;
	}

	public int getLeftX() {
		return leftX;
	}

	public int getRightX() {
		return rightX;
	}

	public int getUpY() {
		return upY;
	}

	public int getDownY() {
		return downY;
	}	
}
