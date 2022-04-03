import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.TextArea;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Result extends JFrame {
	//Result GUI
	private JPanel contentPane;

	public Result(Process puzzle) {
		setTitle("Fifteen Puzzle Solver");
		puzzle.process();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Puzzle Awal");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 10, 96, 23);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new Puzzle(4, 230, 30, puzzle.get_arr_mat_start(), puzzle.get_mat_start().get_step());
		panel.setBounds(10, 43, 255, 241);
		contentPane.add(panel);
		
		JLabel lblPuzzleAkhir = new JLabel("Puzzle Akhir");
		lblPuzzleAkhir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPuzzleAkhir.setBounds(10, 294, 96, 23);
		contentPane.add(lblPuzzleAkhir);
		
		JPanel panel_1 = new Puzzle(4, 230, 30, puzzle.get_arr_mat_start(), puzzle.get_mat_end().get_step());
		panel_1.setBounds(10, 327, 255, 241);
		contentPane.add(panel_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(275, 10, 481, 558);
		contentPane.add(textArea);
		
		JLabel lblNewLabel_1 = new JLabel("* Klik puzzle untuk melihat pergerakannya");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel_1.setBounds(10, 570, 245, 13);
		contentPane.add(lblNewLabel_1);
		textArea.append("Result: \n");
		int[] kurang = puzzle.get_kurang();
		for (int i = 0; i < 16; i++) {
			textArea.append("Kurang(" + (i+1) + ") : " + kurang[i] + "\n");
		}
		textArea.append("Nilai Sigma Kurang+X : " + puzzle.get_kurangX() + "\n");
		if (puzzle.isSolvable()) {
			//berhasil diselesaikan
			textArea.append("Waktu eksekusi: " + puzzle.get_time_process() + " ms\n");
			textArea.append("Total simpul yang dibangkitkan: " + puzzle.get_total_simpul() + "\n");
			Matrix mat_end = puzzle.get_mat_end();
			ArrayList<String> step = mat_end.get_step();
			textArea.append("Langkah yang dilakukan: \n");
			for (int i = 0; i < step.size(); i++) {
				textArea.append("Langkah " + (i+1) + ": " + step.get(i) + "\n");
			}
		}
		else {
			//tidak berhasil diselesaikan
			textArea.append("Puzzle Tidak Dapat DIselesaikan");
		}
	}
}
