import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;


public class KeyboardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int startX = 30;
	private static final int startY = 50;
	private static final int stepX = 75;
	private static final int stepY = 90;
	private static final int btnOffsetX = 5;
	private static final int btnOffsetY = 45;
	private static final int btnWidth = 50;
	private static final int btnHeight = 60;
	private char[][] letters;
	private Color lineColor;
	private Color keyboardColor;
	private ArrayList<Integer> lineCoords;
	private ArrayList<LetterArea> letterAreas;
	
	public KeyboardPanel(Color keyboardColor, Color lineColor) {
		loadLettersFromFile();
		this.keyboardColor = keyboardColor;
		this.lineColor = lineColor;
		lineCoords = new ArrayList<>();
		calculateLetterAreas();
	}
	
	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	
	public void setKeyboardColor(Color keyboardColor) {
		this.keyboardColor = keyboardColor;
	}
	
	public Color getKeyboardColor() {
		return keyboardColor;
	}
	
	public void addNewCoord(int x, int y) {
		// User can draw line for not more than 2 minutes (1000 coords per second).
		if (lineCoords.size() > 240_000) {
			lineCoords.clear();
		}
		lineCoords.add(x);
		lineCoords.add(y);
		repaint();
	}
	
	public void clearCoords() {
		lineCoords.clear();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawLetters(g);
		drawLine(g, lineCoords);
	}
	
	public String getLinedLetters() {
		var result = "";
		char currentLetter = 0;
		for (int i = 0; i < lineCoords.size(); i += 2) {
			char areaChar = getLetterByCoords(lineCoords.get(i), lineCoords.get(i + 1));
			// Only one point for each button.
			if (areaChar != 0 && areaChar != currentLetter) {
				result += Character.toString(areaChar);
				currentLetter = areaChar;				
			}
		}
		return result;
	}
	
	private void loadLettersFromFile() {
		letters = new char[3][12];
		try {
			var br = new BufferedReader(new FileReader(new File("keyboard-letters.txt")));
			var str = "";
			var i = 0;
			while ((str = br.readLine()) != null) {
				var chars = str.toCharArray();
				for (int j = 0; j < chars.length; j++) {
					letters[i][j] = chars[j];
				}
				i++;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void calculateLetterAreas() {
		letterAreas = new ArrayList<>();
		int x;
		var y = startY;
		for (var arr : letters) {
			x = startX;
			for (var letter : arr) {
				if (letter != 0) {
					var area = new LetterArea(
						letter,
						x - btnOffsetX,
						x - btnOffsetX + btnWidth,
						y - btnOffsetY,
						y - btnOffsetY + btnHeight
					);
					letterAreas.add(area);
					x += stepX;
				}
			}
			y += stepY;
		}
	}
	
	private void drawLetters(Graphics g) {
		g.setColor(keyboardColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getContrastForegroundColor(keyboardColor));
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 64));	
		int x;
		var y = startY;
		for (var arr : letters) {
			x = startX;
			for (var letter : arr) {	
				if (letter != 0) {
					g.drawString(Character.toString(letter), x, y);
					g.drawRect(x - btnOffsetX, y - btnOffsetY, btnWidth, btnHeight);
					x += stepX;
				}				
			}
			y += stepY;
		}
	}
	
	private Color getContrastForegroundColor(Color color) {
		float[] rgbs = new float[3];
		color.getRGBColorComponents(rgbs);
		int fR = rgbs[0] > 0.5 ? 0 : 1;
		int fG = rgbs[1] > 0.5 ? 0 : 1;
		int fB = rgbs[2] > 0.5 ? 0 : 1;
		double luminance = 0.2126 * (fR * fR) + 0.7152 * (fG * fG) + 0.0722 * (fB * fB);
		Color foreground = Color.WHITE;
		if (luminance < 0.54) {
			foreground = Color.BLACK;
		}
		return foreground;
	}
	
	private void drawLine(Graphics g, ArrayList<Integer> lineCoords) {
		g.setColor(lineColor);
		var g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(4f));
		for (int i = 0; i < lineCoords.size(); i += 2) {
			var x = lineCoords.get(i);
			var y = lineCoords.get(i + 1);
			g2.drawLine(x, y, x + 1, y + 1);
		}
	}
	
	private char getLetterByCoords(int x, int y) {
		for (var area : letterAreas) {
			if (x > area.getLeftX() && x < area.getRightX() &&
				y > area.getUpY() && y < area.getDownY()) {
				return area.getLetterName();
			}
		}
		return 0;
	}
}
