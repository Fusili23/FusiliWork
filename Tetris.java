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

// ��Ƽ�÷��� 
public class Tetris extends JFrame {
	Container c = getContentPane();		// ����Ʈ ���� ����
	MainPanel MP = new MainPanel();		// ���� �г� ����
	JButton mbtn = new JButton();		// JButton ����
	JLabel NB = new JLabel();			// JLabel ����

	JLabel SL1 = new JLabel(); // ���� ��
	JLabel SL2 = new JLabel(); // ���� ��
	BlockThread BT;	

	static int blocksize = 30; // ��� ������ ����

	int fin = 0; // ����� �ٴڿ� ������� Ȯ���ϴ� ����
	int rand1 = 0; // ����� �������� �̱� ���� ����

	// ���� ����� �̸� �����ֱ� ���� ����
	int rand2 = (int) (Math.random() * 7);

	int score = 0; // ���� ����

	int width = 150; // ��� ���� ���� ����
	int height = 0; // ��� ���� ���� ����
	int rot = 0; // ��� ���� ���� ����

	boolean end = false; // ���� ���� Ȯ���� ���� ����

	int getX[] = new int[4], getY[] = new int[4]; // ��� ��ǥ ����

	// 7���� ����� ������ �迭
	int block[][][][] = { {
			// ��
			// ����
			{ { 0, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 1, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 1, 0 }, { 0, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } } },
			{
					// ��
					// ����
					{ { 0, 0, 0, 0 }, { 0, 0, 1, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 1, 0 }, { 1, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } } },
			{
					// ���
					// ���
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } } },
			{
					// �����
					{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 } } },
			{
					// ��
					// ����
					{ { 0, 0, 0, 0 }, { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } } },
			{
					// ���
					// ���
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } } },
			{
					// ���
					// ���
					{ { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } } } };

	// ���� ���� ������ �迭
	static int[][] gameboard = { { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	static int[][] gameboard2 = { { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	public Tetris() {
		setTitle("Tetris");	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		MP.setSize(1290, 800);

		add(MP);	// ���� �г� ����

		BT = new BlockThread();	// ��Ͻ����� ����

		NB.setText("NEXT BLOCK");
		NB.setFont(new Font("", Font.BOLD, 30));

		SL1.setText("SCORE");
		SL1.setFont(new Font("", Font.BOLD, 30));

		SL2.setFont(new Font("", Font.BOLD, 30));

		MP.addKeyListener(new KeyAdapter() {	// ����� �����̱� ���� Ű����� ����
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();

				if (keyCode == KeyEvent.VK_UP) {	// �� ����Ű �Է½�
					MP.Rotation_Block();	// ��� ȸ��
				}
				if (keyCode == KeyEvent.VK_DOWN) {	// �Ʒ� ����Ű �Է½�
					MP.Down_Block();	// ����� ����
				}
				if (keyCode == KeyEvent.VK_LEFT) {	// ���� ����Ű �Է½� 
					MP.Left_Block();	// ����� �������� �̵�
				}
				if (keyCode == KeyEvent.VK_RIGHT) {	// ������ ����Ű �Է½� 
					MP.Right_Block();	// ����� ���������� �̵�
				}
			}
		});

		mbtn.addActionListener(new ActionListener() {	// ��ư ������ ����
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});

		MP.setBackground(Color.WHITE);	// ����� ������� ����
		setSize(1290, 800);	// ũ�� ����

		setVisible(true);	// ȭ�鿡 ��������

		MP.requestFocus(true);	// Ű �̺�Ʈ ���� ������Ʈ�� ������ ����
		BT.start();	// ������ ����
	}

	class SaveScore extends JFrame {		// ����� DB�� �����ϴ� JFrame
		JLabel lbl1 = new JLabel();
		JLabel lbl2 = new JLabel();
		JLabel lbl3 = new JLabel();
		JTextField jtf1 = new JTextField();
		JButton btn = new JButton();

		ResultSet rs;
		Statement stmt;
		String[] db_name = new String[10];
		String[] db_score = new String[10];
		int cnt = 0;

		public SaveScore() throws SQLException {	

			Connection con = makeConnection();
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();

			setTitle("���� ���");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
			setSize(400, 240);
			setVisible(true);

			lbl1.setText("SCORE :");
			lbl1.setFont(new Font("", Font.BOLD, 30));
			lbl1.setBounds(30, 30, 150, 30);
			add(lbl1);

			lbl2.setText(Integer.toString(score));
			lbl2.setFont(new Font("", Font.BOLD, 30));
			lbl2.setBounds(180, 30, 400, 30);
			add(lbl2);

			lbl3.setText("NAME :");
			lbl3.setFont(new Font("", Font.BOLD, 30));
			lbl3.setBounds(30, 90, 150, 30);
			add(lbl3);

			jtf1.setBounds(150, 90, 210, 30);
			add(jtf1);

			btn.setText("���� ���");
			btn.setFont(new Font("", Font.BOLD, 30));
			btn.setBounds(75, 150, 240, 30);
			add(btn);

			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String s = jtf1.getText();	// �ؽ�Ʈ�ʵ忡 ���� �̸��� String���� ��ȯ�Ͽ� ����
					String s1 = Integer.toString(score);	// ������ String���� ��ȯ�Ͽ� ����
					sb.append("insert into record(name, record) values ('" + s + "', " + s1 + ");");	// �̸��� ������ DB�� ����

					try {
						stmt.executeUpdate(sb.toString());
					} catch (SQLException ee) {
						System.out.println("����");
					}

					setVisible(false);
					new ReStart();
				}
			});

		}
	}
	
	public static Connection makeConnection() { // db�� ����

		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");
			System.out.println("DB ���� �Ϸ�");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����");
		} catch (SQLException e) {
			System.out.println("DB ���� ����");
		}
		return con;
	}
	class ReStart extends JFrame {	// ���� ��� �� �ٽ� ������ �� ������ ����ȭ������ ���ư� ������ ���ϴ� â


		JButton btn1 = new JButton();
		JButton btn2 = new JButton();

		public ReStart() {
			setTitle("���� ���");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
			setSize(400, 240);
			setVisible(true);

			mbtn.setText("���� ȭ��");
			mbtn.setFont(new Font("", Font.BOLD, 30));
			mbtn.setBounds(75, 30, 240, 30);
			add(mbtn);

			mbtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int y = 0; y < 19; y++)
						for (int x = 1; x < 11; x++)
							gameboard[y][x] = 0;
					score = 0;
					width = 150;
					height = 0;
					
					setVisible(false);
					try {
						new Tetris_Main();
					} catch (SQLException ee) {
						System.out.println("����");
					}

				}
			});

			btn2.setText("�����");
			btn2.setFont(new Font("", Font.BOLD, 30));
			btn2.setBounds(75, 90, 240, 30);
			add(btn2);

			btn2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int y = 0; y < 19; y++)
						for (int x = 1; x < 11; x++)
							gameboard[y][x] = 0;
					score = 0;
					width = 150;
					height = 0;
					end = false;
					setVisible(false);
				}
			});

		}
	}

	class MainPanel extends JPanel {
		public void paintComponent(Graphics g) {

			int cnt1 = 0, cnt2 = 0;
			MP.requestFocus(true);
			super.paintComponent(g);

			NB.setLocation(420, 30);
			MP.add(NB);

			SL1.setLocation(450, 200);
			MP.add(SL1);

			SL2.setLocation(450, 240);
			MP.add(SL2);
			SL2.setText(Integer.toString(score));

			g.setColor(Color.PINK); // �� ��

			
			
			Next_Block(g); // ���� ���� ��� �̸� ����

			Stack_Block(); // ����� �ٴڿ� ������� ������ ��ȯ
			
			Break_Block(cnt1, cnt2, g); // ����� �� �ٿ� �� ���� ��� ����
			
			End_Game(); // ����� õ�忡 ������ ���� ����
			
			Show_Block(g); // �� ����

			Background(g); // ��� ����

			Multi_Show_Block(g);	// ��Ƽ �÷��� ȭ�� ���
			
			if (fin == 1) {

				rand2 = (int) (Math.random() * 7);

				fin = 0;
			}
		}
		
		public void Multi_Show_Block(Graphics g) {	// ��Ƽ�÷����� ȭ���� ������ �޾ƿ� ���
	          g.setColor(Color.PINK);

	          for (int y = 0; y < 20; y++) {
	             for (int x = 0; x < 12; x++) {
	                if (gameboard2[y][x] == 1) {
	                   g.fill3DRect(x * blocksize + 630, y * blocksize + 30, blocksize, blocksize, true);
	                }
	             }
	          }
	       }
		
		public void Next_Block(Graphics g) {	// ���� ����� �̸���
			for (int a = 0; a < 4; a++) {
				for (int b = 0; b < 4; b++) {
					if (block[rand2][0][a][b] == 1) {	// ���� ������ ����� �̸� ���
						g.fill3DRect(b * blocksize + 450, a * blocksize + 60, blocksize, blocksize, true);
					}
				}
			}
		}

		public void Show_Block(Graphics g) {	// ���� ����
			g.setColor(Color.PINK);

			for (int y = 0; y < 19; y++) {
				for (int x = 1; x < 11; x++) {
					if (gameboard[y][x] == 1) {	// ���� ���尡 1�̸� ���� ����
						g.fill3DRect(x * blocksize + 30, y * blocksize + 30, blocksize, blocksize, true);
					}
				}
			}
		}

		public void Break_Block(int cnt1, int cnt2, Graphics g) {	// ���� �ν�
			int event = 0;
			for (int y = 0; y < 19; y++) {
				for (int x = 1; x < 11; x++) {
					if (gameboard[y][x] == 1) {
						cnt2++;	// ���ٿ� �ϳ��� ������������ count �� ����
					}
				}
				if (cnt2 == 10) {	// ������ �� ��
					for (int i = y; i > 1; i--) {
						for (int j = 1; j < 12; j++) {
							gameboard[i][j] = 0;	// ���� �� ���� 0���� �ʱ�ȭ �� ��
							gameboard[i][j] = gameboard[i - 1][j];	// �� ���� �ִ� ���� �Ʒ��� ����
						}
					}
					score += 100;	// ������ 100�� ����

				} else {
					Down_Block(cnt1, g); // �� ���� ��� ������� ä������ ���� ���� ����� ���������� ��
				}
				cnt2 = 0;
			}
		}

		public void Down_Block(int cnt1, Graphics g) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (block[rand1][rot][i][j] == 1) {
						getX[cnt1] = ((j * blocksize) + width) / blocksize; // getX, getY�� ����� ��ǥ�� ����
						getY[cnt1] = ((i * blocksize) + height) / blocksize;
						g.fill3DRect(getX[cnt1] * blocksize + 30, getY[cnt1] * blocksize + 30, blocksize, blocksize,
								true);	// �������� ����� ���

						cnt1++;	// count�� ����
					}
				}
			}
		}

		// �ٴڿ� ���� ����� ������ ���ϴ��� �˻�
		// ���� �Ǹ� width=150, height=0, rot = 0 ���� �ʱ�ȭ
		public void Stack_Block() {
			try {
				for (int i = 0; i < 4; i++) {
					if (gameboard[getY[i] + 1][getX[i]] == 1) {
							
						for (int j = 0; j < 4; j++) {

							gameboard[getY[j]][getX[j]] = 1;

							width = 150;	// ����
							height = 0;		// ����
							fin = 1;
							rot = 0;
							rand1 = rand2;
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return;
			}

		}

		
		public int Crash_LEFT() {	// ���� �� �浹 ����
			for (int i = 0; i < 4; i++) {
				if (gameboard[getY[i]][getX[i] - 1] == 1) 
					return 1;	// �浹�ϸ� 1�� ��ȯ����
			}
			return 0; // �浹���� ������ 0�� ��ȯ
		}

		
		public int Crash_RIGHT() {	// ������ �� �浹 ����

			for (int i = 0; i < 4; i++) {
				if (gameboard[getY[i]][getX[i] + 1] == 1) 
					return 1;	// �浹�ϸ� 1�� ��ȯ����
			}
			return 0; // �浹���� ������ 0��ȯ
		}

		// getX, getY�� ȸ���� ������ ��ǥ�� ����ص�
		// X��ǥ �¿� 1, 2ĭ �̳��� ���� �ִٸ� �� ���̸�ŭ �¿�� �о ��ġ
		public void Rotation_Save() {
			// getX,Y�� ���� ȸ�� ������ ������ǥ�� ��� ����صΰ�, �ؿ� �������� �� ������ǥ�� ���� ���� ����� �Ǵ�
			int cnt2 = 0;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					int r_rotation = (rot % 4) + 1;
					if (r_rotation == 4)
						r_rotation = 0;
					if (block[rand1][r_rotation][i][j] == 1) {
						getX[cnt2] = ((j * blocksize) + width) / blocksize;
						getY[cnt2] = ((i * blocksize) + height) / blocksize;
						cnt2++;
					}
				}
			}

			// getX, getY�� ��ǥ�� �̿�
			int check = 0;	// ������ ���� ��ġ���� üũ
			int blank = 0;	// ���� üũ

			// ���� ��

			if (gameboard[getY[0]][getX[0]] == 1 || (rand1 == 6 && gameboard[getY[2]][getX[2]] == 1)
					|| (rand1 == 1 && gameboard[getY[1]][getX[1]] == 1)) {
				check = 1; // ȸ���� ������ ��ġ�� ���� ��ġ�� 1

				
				if (rand1 == 3) { // ���ڸ����� ��� ȸ���� ������ �ִ� ������ ������ ȸ������
					for (int i = 1; i < 5; i++)
						if (gameboard[getY[0]][getX[0] + i] == 0)
							blank++;
					if (blank < 4)
						check = 4;

				} 
				else { // ȸ�� �� �� �ִ� ������ ���ٸ� ȸ���� ���� ����
					for (int i = 1; i < 4; i++)
						if (gameboard[getY[0]][getX[0] + i] == 0)
							blank++;
					if (blank < 3)
						check = 4;

				}

			}

			// ������ ��

			else if (gameboard[getY[2]][getX[2]] == 1) {

				check = 2; // ȸ���� ������ ��ġ�� ���� ��ġ�� 2
				

				for (int i = 1; i < 5; i++)
					if (gameboard[getY[2]][getX[2] - i] == 0)
						blank++;
				if (blank < 4)
					check = 4;
				

			} else if (gameboard[getY[3]][getX[3]] == 1) {

				check = 3; // ȸ���� ������ ��ġ�� ���� ��ġ�� 3
				
				for (int i = 0; i < 5; i++) {
					if (gameboard[getY[3]][getX[3] - i] == 0) {
						blank++;
					}
				}
				if (blank < 4) {
					check = 4;
				}
			}
			// üũ�� ������ �°� �̵�
			if (check == 1) { 
				width += blocksize;
				rot++;
				rot = rot % 4;
			} else if (check == 2) {
				width -= blocksize * 2;
				rot++;
				rot = rot % 4;
			} else if (check == 3) {
				width -= blocksize;
				rot++;
				rot = rot % 4;
			} else if (check == 4) {
				return;
			} else {
				rot++;
				rot = rot % 4;
			}

		}

		public void Background(Graphics g) {	// ��� ����
			g.setColor(Color.GRAY);

			for (int y = 0; y < 20; y++) {
				for (int x = 0; x < 12; x++) {
					if (gameboard[y][x] == 1) {
						g.fill3DRect((x + 1) * blocksize, (y + 1) * blocksize, blocksize, blocksize, true);
					} else {
						g.drawRect((x + 1) * blocksize, (y + 1) * blocksize, blocksize, blocksize);
					}
				}
			}
		}

		// ����Ű ���� ����
		void Block_Down() {
			height += blocksize;	// ����� ũ�⸸ŭ ������
			MP.repaint();
		}

		void Rotation_Block() {
			Rotation_Save();
			if (end == false)
				repaint();
		}

		void Down_Block() {
			if (end == false) {
				height += blocksize;
				MP.repaint();
			}
		}

		void Left_Block() {
			int num = Crash_LEFT();
			if (num == 0 && end == false) { // num�� 1�̸� �浹�Ͽ� �������� ���� ����
				width -= blocksize;
				MP.repaint();
			}
		}

		void Right_Block() {
			int num = Crash_RIGHT();
			if (num == 0 && end == false) {	 // num�� 1�̸� �浹�Ͽ� ���������� ���� ����
				width += blocksize;
				MP.repaint();
			}
		}

		public void End_Game() {
			for (int x = 1; x < 11; x++) {
				if (gameboard[2][x] == 1) {
					end = true;
					try {
						new SaveScore();
					} catch (SQLException ee) {
						System.out.println("����");
					}
				}
			}
		}
	}

	class BlockThread extends Thread {
		MainPanel MP = new MainPanel();
		
		BufferedReader in = null;
		BufferedWriter out = null;
		ServerSocket listener = null;
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		
        
        
		public void run() {
			try {
				listener = new ServerSocket(9999);
				System.out.println("������ ��ٸ��� �ֽ��ϴ�.....");
				socket = listener.accept();
				System.out.println("����Ǿ����ϴ�.");
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			while (true) {
				try {
					sleep(500);
					
					if (end == false) {
						MP.Block_Down();
						String str = Arrays.deepToString(gameboard).replaceAll("[^0-9]","");
						out.write(str + "\n");
						out.flush();
						String inputMessage = in.readLine();
						
						for (int i = 0 ; i < 21; i++) {
							for (int j = 0; j < 12; j++) {
								
								String x = inputMessage.substring(((i ) * 12)+j, ((i)*12) + j + 1);
								
								int y = Integer.parseInt(x);
								gameboard2[i][j] = y;
							}
						}
						
						
						// limit�� false�� ��쿡�� �۵�. true�� �Ǹ� ��Ʈ���� �۵�����
					}
						
				} catch (InterruptedException e) {
					return;
				} catch (IOException e) {
					System.out.println("��� â�� �о���̴��� ������ �߻��߽��ϴ�");
				}
			}
		}
	}

	public static void main(String[] args) {
		new Tetris();
	}
}