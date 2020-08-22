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
	private char[][] letters;
	private Color lineColor;
	private Color keyboardColor;
	private ArrayList<Integer> lineCoords;
	
	public KeyboardPanel(Color keyboardColor, Color lineColor) {
		loadLettersFromFile();
		this.keyboardColor = keyboardColor;
		this.lineColor = lineColor;
		lineCoords = new ArrayList<>();
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
	
	private void drawLetters(Graphics g) {
		g.setColor(keyboardColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getContrastForegroundColor(keyboardColor));
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 64));	
		var x = 40;
		var y = 60;
		for (var arr : letters) {
			x = 50;
			for (var letter: arr) {	
				if (letter != 0) {
					g.drawString(Character.toString(letter), x, y);
					drawSquare(g, x, y);
					x += 65;
				}				
			}
			y += 70;
		}
	}
	
	private void drawSquare(Graphics g, int x, int y) {
		g.drawRect(x - 5, y - 45, 50, 60);
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
		g2.setStroke(new BasicStroke(3f));
		for (int i = 0; i < lineCoords.size(); i+=2) {
			var x = lineCoords.get(i);
			var y = lineCoords.get(i + 1);
			g2.drawLine(x, y, x + 1, y + 1);
		}
	}
}
