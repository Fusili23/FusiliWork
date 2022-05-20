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

public class Tetris_Start extends JFrame {
	public Tetris_Start() {
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		setSize(400, 600);
		setVisible(true);

		JButton Single = new JButton("SINGLE PLAY");
		Single.setBounds(100, 200, 200, 50);
		add(Single);

		Single.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TetrisSingle();
				setVisible(false);
			}
		});

		JButton Multi = new JButton("MULTI PLAY");
		Multi.setBounds(100, 300, 200, 50);
		add(Multi);
		Multi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Tetris();
				setVisible(false);
			}
		});
		JButton Back = new JButton("BACK");
		Back.setBounds(100, 400, 200, 50);
		add(Back);
		
		Back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new Tetris_Main();
					setVisible(false);
				} catch (SQLException ee) {
					System.out.println("¿¡·¯");
				}

			}
		});
	}
}