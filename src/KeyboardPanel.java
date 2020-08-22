import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;

public class KeyboardPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private char[][] letters;
	private Color keyboardColor;
	
	public KeyboardPanel(Color keyboardColor) {
		loadLettersFromFile();
		this.keyboardColor = keyboardColor;
	}
	
	public void setKeyboardColor(Color keyboardColor) {
		this.keyboardColor = keyboardColor;
	}
	
	public Color getKeyboardColor() {
		return keyboardColor;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawLetters(g);
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
}
