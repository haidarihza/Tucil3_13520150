import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Start extends JFrame {
	//Start GUI
	private JPanel contentPane;
	private JTextField textField;
	private File file;
	private Process puzzle;

	public Start() {
		setTitle("Fifteen Puzzle Solver");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Fifteen Puzzle Solver");
		lblNewLabel.setFont(new Font("Tekton Pro Cond", Font.BOLD, 40));
		lblNewLabel.setBounds(202, 92, 282, 60);
		contentPane.add(lblNewLabel);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Random Sample");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton.setBounds(272, 177, 141, 46);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Choose File");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton_1.setBounds(272, 234, 133, 39);
		contentPane.add(rdbtnNewRadioButton_1);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnNewRadioButton);
		buttonGroup.add(rdbtnNewRadioButton_1);
				
		textField = new JTextField();
		textField.setBounds(270, 274, 146, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Choose FIle");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(294, 308, 97, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNewRadioButton_1.isSelected()) {
					if(e.getSource()==btnNewButton) {
						JFileChooser fileChooser = new JFileChooser();
						
						fileChooser.setCurrentDirectory(new File(".")); //sets current directory
						int response = fileChooser.showOpenDialog(null); //select file to open
						if(response == JFileChooser.APPROVE_OPTION) {
							file = new File(fileChooser.getSelectedFile().getAbsolutePath());
							try {
								puzzle = new Process(file);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
							textField.setText(file.toString());
						}
					}
				}
			}
		});

		try {
			puzzle = new Process(null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Solve!!!");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBounds(283, 408, 120, 33);
		contentPane.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNewRadioButton_1.isSelected() || rdbtnNewRadioButton.isSelected()) {
					if(e.getSource()==btnNewButton_1) {
						Result res = new Result(puzzle);
						res.setVisible(true);
						dispose();
					}
				}
			}
		});
	}
}
