import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.sql.*;

public class db extends JFrame implements ActionListener {

	static ResultSet rs;
	static Statement stmt;
	static String[] name = new String[10];
	static String[] score = new String[10];
	static int cnt = 0;
	
	public db() throws SQLException {
		Connection con = makeConnection();
		stmt = con.createStatement();
		StringBuffer sb = new StringBuffer();

		rs = stmt.executeQuery("select * from record order by record desc"); // rs = resultset <- 얘를 함수에 바로
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(null);
		setSize(500, 500);

		JTextField tf = new JTextField("이름을 입력하세요");
		tf.setBounds(100, 100, 100, 100);
		c.add(tf);

		JLabel lb = new JLabel("점수: ");
		lb.setBounds(100, 200, 100, 50);
		c.add(lb);

		JLabel lb2 = new JLabel("0");
		lb2.setBounds(150, 200, 100, 50);
		c.add(lb2);

		JButton btn = new JButton("확인");
		btn.setBounds(200, 200, 100, 100);
		btn.addActionListener(new ActionListener() {// 버튼에 액션 리스너를 달아준다
			public void actionPerformed(ActionEvent e) {
				String s = tf.getText();
				String s1 = lb2.getText();
				sb.append("insert into record(name, record) values ('" + s + "', " + s1 + ");");
				
				try {
					stmt.executeUpdate(sb.toString());
				} catch (SQLException ee) {
					System.out.println("에러");
				}

			}
		});
		c.add(btn);

		JButton btn1 = new JButton("기록조회");
		btn1.setBounds(300, 200, 100, 100);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new recordWindow(name, score);
				setVisible(false);
			}
		});
		c.add(btn1);
		printData(rs);
		setVisible(true);
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

	public static void printData(ResultSet srs) throws SQLException {
		cnt = 0;
		while (srs.next() && cnt < 10) {
			System.out.print(srs.getString("name") + " ");
			name[cnt] = srs.getString("name");
			System.out.print(srs.getString("record") + "점");
			score[cnt] = srs.getString("record");
			System.out.println(" ");
			cnt++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws SQLException {
		new db();
	}
}
