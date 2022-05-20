import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jdk.internal.org.jline.utils.InputStreamReader;

public class Tetris_Main extends JFrame {
	static ResultSet rs;
	static Statement stmt;
	static String[] name1 = new String[10];
	static String[] score1 = new String[10];
	static int cnt = 0;
	
	public Tetris_Main() throws SQLException {
		Connection con = makeConnection();
		stmt = con.createStatement();
		StringBuffer sb = new StringBuffer();
		
		rs = stmt.executeQuery("select * from record order by record desc");
		
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		setSize(400, 600);
		setVisible(true);

		JLabel Title = new JLabel("Tetris");
		Title.setBounds(180, 100, 200, 50);
		add(Title);

		JButton Start = new JButton("GAME START");
		Start.setBounds(100, 200, 200, 50);
		add(Start);

		Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Tetris_Start();
				setVisible(false);
			}
		});

		JButton Rank = new JButton("RANKING");
		Rank.setBounds(100, 300, 200, 50);
		Rank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cnt = 0;
					while (rs.next() && cnt < 10) {
						
						name1[cnt] = rs.getString("name");
						
						score1[cnt] = rs.getString("record");
						cnt++;
					}
					setVisible(false);
					new recordWindow(name1, score1);
				} catch (SQLException ee) {
					System.out.println("에러");
				}
			}
		});
		add(Rank);

		JButton End = new JButton("END GAME");
		End.setBounds(100, 400, 200, 50);
		add(End);

		End.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static Connection makeConnection() { // db에 연결

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
			System.out.println("DB 연결 완료");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 로드 에러");
		} catch (SQLException e) {
			System.out.println("DB 연결 오류");
		}
		return con;
	}

	public static void main(String[] args) throws SQLException{
		new Tetris_Main();
	}
}