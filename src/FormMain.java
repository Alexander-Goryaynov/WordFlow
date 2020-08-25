import java.awt.Color;
import java.awt.EventQueue;
import java.awt.MouseInfo;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class FormMain {

	private JFrame frmWordflowKeyboard;
	private JTextField textFieldNewWord;
	private KeyboardPanel panel;
	private JTextArea textArea;
	private SearchManager searchManager;
	private JButton btnFirst;
	private JButton btnSecond;
	private JButton btnThird;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain window = new FormMain();
					window.frmWordflowKeyboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWordflowKeyboard = new JFrame();
		frmWordflowKeyboard.setTitle("WordFlow Keyboard");
		frmWordflowKeyboard.setBounds(100, 100, 960, 567);
		frmWordflowKeyboard.setLocationRelativeTo(null);
		frmWordflowKeyboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmWordflowKeyboard.getContentPane().setLayout(springLayout);
		
		JMenuBar menuBar = new JMenuBar();
		springLayout.putConstraint(SpringLayout.NORTH, menuBar, 10, SpringLayout.NORTH, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, menuBar, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(menuBar);
		
		JMenuItem clearHistoryMenuItem = new JMenuItem("\u041E\u0447\u0438\u0441\u0442\u0438\u0442\u044C \u0438\u0441\u0442\u043E\u0440\u0438\u044E");
		clearHistoryMenuItem.setBackground(new Color(255, 228, 225));
		clearHistoryMenuItem.setFont(new Font("Cambria", Font.PLAIN, 16));
		clearHistoryMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var result = JOptionPane.showConfirmDialog(frmWordflowKeyboard,
						"Удалить все пользовательские слова?", "Очистить историю",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					try {
						searchManager.clearHistory();
						JOptionPane.showMessageDialog(frmWordflowKeyboard, "История успешно удалена");
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(frmWordflowKeyboard, ex.getMessage());
					}
				}
			}
		});
		menuBar.add(clearHistoryMenuItem);
		
		JMenuItem keyboardColorMenuItem = new JMenuItem("\u0426\u0432\u0435\u0442 \u043A\u043B\u0430\u0432\u0438\u0430\u0442\u0443\u0440\u044B");
		keyboardColorMenuItem.setBackground(new Color(224, 255, 255));
		keyboardColorMenuItem.setFont(new Font("Cambria", Font.PLAIN, 16));
		keyboardColorMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var color = JColorChooser.showDialog(frmWordflowKeyboard,
						"Цвет клавиатуры", panel.getKeyboardColor());
				if (color != null) {
					panel.setKeyboardColor(color);
					panel.repaint();
				}				
			}
		});
		menuBar.add(keyboardColorMenuItem);
		
		JMenuItem lineColorMenuItem = new JMenuItem("\u0426\u0432\u0435\u0442 \u043B\u0438\u043D\u0438\u0438");
		lineColorMenuItem.setBackground(new Color(250, 250, 210));
		lineColorMenuItem.setFont(new Font("Cambria", Font.PLAIN, 16));
		lineColorMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				var color = JColorChooser.showDialog(frmWordflowKeyboard,
						"Цвет линии", panel.getLineColor());
				if (color != null) {
					panel.setLineColor(color);
					panel.repaint();
				}
			}
		});
		menuBar.add(lineColorMenuItem);
		
		Timer timer = new Timer(1, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				var point = MouseInfo.getPointerInfo().getLocation();
				var winLocation = frmWordflowKeyboard.getLocationOnScreen();
				panel.addNewCoord((int)(point.getX() - winLocation.getX() - 15), 
						(int)(point.getY() - winLocation.getY() - 72));
			}
		});
		
		panel = new KeyboardPanel(new Color(252, 236, 201), Color.red);
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				timer.start();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				timer.stop();
				var words = searchManager.searchByPattern(panel.getLinedLetters());
				showProposals(words);
				panel.clearCoords();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(panel);
		
		btnFirst = new JButton("-");
		btnFirst.setFont(new Font("Cambria", Font.PLAIN, 14));
		btnFirst.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnFirst, 270, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -6, SpringLayout.NORTH, btnFirst);
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addButtonTextToArea(arg0);
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnFirst, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnFirst, 277, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(btnFirst);
		
		btnSecond = new JButton("-");
		springLayout.putConstraint(SpringLayout.WEST, btnSecond, 40, SpringLayout.EAST, btnFirst);
		btnSecond.setFont(new Font("Cambria", Font.PLAIN, 14));
		btnSecond.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, btnSecond, 0, SpringLayout.NORTH, btnFirst);
		btnSecond.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addButtonTextToArea(arg0);
			}
		});
		frmWordflowKeyboard.getContentPane().add(btnSecond);
		
		btnThird = new JButton("-");
		springLayout.putConstraint(SpringLayout.EAST, btnSecond, -40, SpringLayout.WEST, btnThird);
		springLayout.putConstraint(SpringLayout.WEST, btnThird, -280, SpringLayout.EAST, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnThird, 0, SpringLayout.EAST, panel);
		btnThird.setFont(new Font("Cambria", Font.PLAIN, 14));
		btnThird.setVisible(false);
		btnThird.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addButtonTextToArea(arg0);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnThird, 0, SpringLayout.NORTH, btnFirst);
		frmWordflowKeyboard.getContentPane().add(btnThird);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Cambria", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(textArea);
	    scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		springLayout.putConstraint(SpringLayout.NORTH, scroll, 67, SpringLayout.SOUTH, btnFirst);
		springLayout.putConstraint(SpringLayout.WEST, scroll, 0, SpringLayout.WEST, menuBar);
		springLayout.putConstraint(SpringLayout.SOUTH, scroll, -10, SpringLayout.SOUTH, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scroll, -8, SpringLayout.EAST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(scroll);
		
		textFieldNewWord = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, textFieldNewWord, -6, SpringLayout.NORTH, scroll);
		frmWordflowKeyboard.getContentPane().add(textFieldNewWord);
		textFieldNewWord.setColumns(10);
		
		JLabel lblNewWord = new JLabel("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u043B\u043E\u0432\u043E");
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewWord, -10, SpringLayout.NORTH, scroll);
		lblNewWord.setFont(new Font("Cambria", Font.PLAIN, 14));
		springLayout.putConstraint(SpringLayout.WEST, textFieldNewWord, 10, SpringLayout.EAST, lblNewWord);
		springLayout.putConstraint(SpringLayout.WEST, lblNewWord, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(lblNewWord);
		
		JButton btnNewWord = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		springLayout.putConstraint(SpringLayout.NORTH, textFieldNewWord, 0, SpringLayout.NORTH, btnNewWord);
		btnNewWord.setFont(new Font("Cambria", Font.BOLD, 14));
		springLayout.putConstraint(SpringLayout.WEST, btnNewWord, 423, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textFieldNewWord, -6, SpringLayout.WEST, btnNewWord);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewWord, -6, SpringLayout.NORTH, scroll);
		btnNewWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				var text = textFieldNewWord.getText();
				if (text.equals("")) {
					return;
				}
				text = text.toLowerCase();
				try {
					searchManager.addNewWordToFile(text);
					textFieldNewWord.setText("");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frmWordflowKeyboard, e.getMessage());
				}
			}
		});
		frmWordflowKeyboard.getContentPane().add(btnNewWord);
		searchManager = new SearchManager(panel);
		searchManager.loadWordsFromFile();
	}
	
	private void showProposals(ArrayList<String> words) {
		hideAllButtons();
		if (words.size() == 0) {			
			return;
		}
		JButton[] buttons = new JButton[] {btnFirst, btnSecond, btnThird};
		for (int i = 0; i < buttons.length; i++) {
			if (i < words.size()) {
				buttons[i].setText(words.get(i));
				buttons[i].setVisible(true);
			}
		}
	}
	
	private void hideAllButtons() {
		btnFirst.setVisible(false);
		btnSecond.setVisible(false);
		btnThird.setVisible(false);
	}
	
	private void addButtonTextToArea(ActionEvent event) {
		JButton button = (JButton)event.getSource();
		var text = textArea.getText();
		if (!text.equals("")) {
			if (text.charAt(text.length() - 1) != ' ') {
				text += " ";
				textArea.setText(text);
			}			
		}
		textArea.setText(textArea.getText() + button.getText() + " ");
		hideAllButtons();
	}

}
