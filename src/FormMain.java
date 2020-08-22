import java.awt.Color;
import java.awt.EventQueue;
import java.awt.MouseInfo;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.Timer;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormMain {

	private JFrame frmWordflowKeyboard;
	private JTextField textFieldNewWord;
	private KeyboardPanel panel;

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
		frmWordflowKeyboard.setBounds(100, 100, 875, 529);
		frmWordflowKeyboard.setLocationRelativeTo(null);
		frmWordflowKeyboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmWordflowKeyboard.getContentPane().setLayout(springLayout);
		
		JMenuBar menuBar = new JMenuBar();
		springLayout.putConstraint(SpringLayout.NORTH, menuBar, 10, SpringLayout.NORTH, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, menuBar, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(menuBar);
		
		JMenuItem clearWordsMenuItem = new JMenuItem("\u041E\u0447\u0438\u0441\u0442\u0438\u0442\u044C \u0438\u0441\u0442\u043E\u0440\u0438\u044E");
		clearWordsMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		menuBar.add(clearWordsMenuItem);
		
		JMenuItem keyboardColorMenuItem = new JMenuItem("\u0426\u0432\u0435\u0442 \u043A\u043B\u0430\u0432\u0438\u0430\u0442\u0443\u0440\u044B");
		keyboardColorMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.setKeyboardColor(JColorChooser.showDialog(frmWordflowKeyboard,
						"÷вет клавиатуры", panel.getKeyboardColor()));
				panel.repaint();
			}
		});
		menuBar.add(keyboardColorMenuItem);
		
		JMenuItem lineColorMenuItem = new JMenuItem("\u0426\u0432\u0435\u0442 \u043B\u0438\u043D\u0438\u0438");
		lineColorMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel.setLineColor(JColorChooser.showDialog(frmWordflowKeyboard,
						"÷вет линии", panel.getLineColor()));
				panel.repaint();
			}
		});
		menuBar.add(lineColorMenuItem);
		
		Timer timer = new Timer(10, new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				var point = MouseInfo.getPointerInfo().getLocation();
				panel.addNewCoord(point.x - 265, point.y - 160);
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
				panel.clearCoords();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, panel, 6, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 238, SpringLayout.SOUTH, menuBar);
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(panel);
		
		JButton btnFirst = new JButton("-");
		springLayout.putConstraint(SpringLayout.NORTH, btnFirst, 15, SpringLayout.SOUTH, panel);
		btnFirst.setVisible(false);
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnFirst, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnFirst, 277, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(btnFirst);
		
		JButton btnSecond = new JButton("-");
		springLayout.putConstraint(SpringLayout.NORTH, btnSecond, 15, SpringLayout.SOUTH, panel);
		btnSecond.setVisible(false);
		btnSecond.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnSecond, 6, SpringLayout.EAST, btnFirst);
		springLayout.putConstraint(SpringLayout.EAST, btnSecond, 565, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(btnSecond);
		
		JButton btnThird = new JButton("-");
		btnThird.setVisible(false);
		btnThird.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnThird, 0, SpringLayout.NORTH, btnFirst);
		springLayout.putConstraint(SpringLayout.WEST, btnThird, 570, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnThird, 852, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(btnThird);
		
		JTextArea textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 55, SpringLayout.SOUTH, btnFirst);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 0, SpringLayout.WEST, menuBar);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -10, SpringLayout.SOUTH, frmWordflowKeyboard.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea, -8, SpringLayout.EAST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(textArea);
		
		textFieldNewWord = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldNewWord, 16, SpringLayout.SOUTH, btnFirst);
		frmWordflowKeyboard.getContentPane().add(textFieldNewWord);
		textFieldNewWord.setColumns(10);
		
		JLabel lblNewWord = new JLabel("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0441\u043B\u043E\u0432\u043E");
		springLayout.putConstraint(SpringLayout.WEST, textFieldNewWord, 6, SpringLayout.EAST, lblNewWord);
		springLayout.putConstraint(SpringLayout.EAST, textFieldNewWord, 325, SpringLayout.EAST, lblNewWord);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewWord, 19, SpringLayout.SOUTH, btnFirst);
		springLayout.putConstraint(SpringLayout.WEST, lblNewWord, 10, SpringLayout.WEST, frmWordflowKeyboard.getContentPane());
		frmWordflowKeyboard.getContentPane().add(lblNewWord);
		
		JButton btnNewWord = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		btnNewWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnNewWord, 13, SpringLayout.EAST, textFieldNewWord);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewWord, 0, SpringLayout.SOUTH, textFieldNewWord);
		frmWordflowKeyboard.getContentPane().add(btnNewWord);
	}
}
