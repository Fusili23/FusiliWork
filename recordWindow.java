import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class recordWindow extends JFrame {

	public recordWindow(String arr[], String arr1[]) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(300, 600);
		JLabel jb[] = new JLabel[10];
		JLabel jb1[] = new JLabel[10];
		setVisible(true);

		String name[] = new String[10];
		String score[] = new String[10];
		int cnt = 0;
		while (true) {
			if (cnt == 10)
				break;
			name[cnt] = arr[cnt];
			score[cnt] = arr1[cnt];
			cnt++;
		}

		for (int i = 0; i < 10; i++) {
			jb[i] = new JLabel();
			jb[i].setText(arr[i]);
			jb[i].setBounds(20, 50 + (20 * (i + 1)), 50, 20);
			jb[i].setBorder(new LineBorder(Color.black));
			add(jb[i]);
		}

		for (int i = 0; i < 10; i++) {
			jb1[i] = new JLabel();
			jb1[i].setText(arr1[i]);
			jb1[i].setBounds(70, 50 + (20 * (i + 1)), 50, 20);
			jb1[i].setBorder(new LineBorder(Color.black));
			add(jb1[i]);
		}
		
		JButton btn1 = new JButton("나가기");
		btn1.setBounds(90, 380, 100, 100);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Tetris_Main();
				} catch (SQLException ee) {
					System.out.println("에러");
				}
				setVisible(false);
			}
		});
		add(btn1);
		
		JLabel title = new JLabel("TOP10");
		title.setBounds(60, 0, 100, 100);
		add(title);

	}
}
