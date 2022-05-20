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

// 싱글플레이
// 멀티플레이에서 소켓만 없어진 버전
public class TetrisSingle extends JFrame {
	Container c = getContentPane();
	MainPanel MP = new MainPanel();
	JButton mbtn = new JButton();
	JLabel NB = new JLabel();

	JLabel SL1 = new JLabel(); // 점수 라벨
	JLabel SL2 = new JLabel(); // 점수 라벨
	BlockThread BT;

	static int blocksize = 30; // 블록 사이즈 저장

	int fin = 0; // 블록이 바닥에 닿았음을 확인하는 변수
	int rand1 = 0; // 블록을 랜덤으로 뽑기 위한 변수

	// 다음 블록을 미리 보여주기 위한 변수
	int rand2 = (int) (Math.random() * 7);

	int score = 0; // 점수 저장

	int width = 150; // 블록 가로 저장 변수
	int height = 0; // 블록 높이 저장 변수
	int rot = 0; // 블록 방향 저장 변수

	boolean end = false; // 게임 종료 확인을 위한 변수

	int getX[] = new int[4], getY[] = new int[4]; // 블록 좌표 저장

	// 7가지 블록을 저장한 배열
	int block[][][][] = { {
			// ■
			// ■■■
			{ { 0, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 1, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
			{ { 0, 0, 1, 0 }, { 0, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } } },
			{
					// ■
					// ■■■
					{ { 0, 0, 0, 0 }, { 0, 0, 1, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 1, 0 }, { 1, 0, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } } },
			{
					// ■■
					// ■■
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } } },
			{
					// ■■■■
					{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 1, 0, 0 } } },
			{
					// ■
					// ■■■
					{ { 0, 0, 0, 0 }, { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } } },
			{
					// ■■
					// ■■
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 1, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 1, 0 }, { 0, 1, 1, 0 }, { 0, 1, 0, 0 }, { 0, 0, 0, 0 } } },
			{
					// ■■
					// ■■
					{ { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } },
					{ { 0, 1, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 0 } } } };

	// 게임 판을 저장한 배열
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
	

	public TetrisSingle() {
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		MP.setSize(645, 800);

		add(MP);

		BT = new BlockThread();

		NB.setText("NEXT BLOCK");
		NB.setFont(new Font("", Font.BOLD, 30));

		SL1.setText("SCORE");
		SL1.setFont(new Font("", Font.BOLD, 30));

		SL2.setFont(new Font("", Font.BOLD, 30));

		MP.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();

				if (keyCode == KeyEvent.VK_UP) {
					MP.Rotation_Block();
				}
				if (keyCode == KeyEvent.VK_DOWN) {
					MP.Down_Block();
				}
				if (keyCode == KeyEvent.VK_LEFT) {
					MP.Left_Block();
				}
				if (keyCode == KeyEvent.VK_RIGHT) {
					MP.Right_Block();
				}
			}
		});

		mbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});

		MP.setBackground(Color.WHITE);
		setSize(645, 800);

		setVisible(true);

		MP.requestFocus(true);
		BT.start();
	}

	class SaveScore extends JFrame {
		JLabel lbl1 = new JLabel();
		JLabel lbl2 = new JLabel();
		JLabel lbl3 = new JLabel();
		JTextField jtf1 = new JTextField();
		JButton btn = new JButton();
		
		static ResultSet rs;
		static Statement stmt;
		static String[] db_name = new String[10];
		static String[] db_score = new String[10];
		static int cnt = 0;
		
		public SaveScore() throws SQLException{
			
			Connection con = makeConnection();
			stmt = con.createStatement();
			StringBuffer sb = new StringBuffer();
			
			setTitle("점수 등록");
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

			btn.setText("점수 등록");
			btn.setFont(new Font("", Font.BOLD, 30));
			btn.setBounds(75, 150, 240, 30);
			add(btn);

			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String s = jtf1.getText();
					String s1 = Integer.toString(score);
					sb.append("insert into record(name, record) values ('" + s + "', " + s1 + ");");
					
					try {
						stmt.executeUpdate(sb.toString());
					} catch (SQLException ee) {
						System.out.println("에러");
					}
					
					setVisible(false);
					new ReStart();
				}
			});

		}
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
	class ReStart extends JFrame {

		JButton btn1 = new JButton();
		JButton btn2 = new JButton();

		public ReStart() {
			setTitle("점수 등록");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(null);
			setSize(400, 240);
			setVisible(true);

			mbtn.setText("시작 화면");
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
						System.out.println("에러");
					}

				}
			});

			btn2.setText("재시작");
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

			g.setColor(Color.PINK); // 블럭 색

			
			
			Next_Block(g); // 다음 나올 블록 미리 보기

			Stack_Block(); // 블록이 바닥에 닿았을때 벽으로 전환
			
			Break_Block(cnt1, cnt2, g); // 블록이 한 줄에 다 차면 블록 제거
			
			End_Game(); // 블록이 천장에 닿으면 게임 종료
			
			Show_Block(g); // 벽 생성

			Background(g); // 배경 생성
			
		
			
			if (fin == 1) {

				rand2 = (int) (Math.random() * 7);

				fin = 0;
			}
		}
		
		
		
		public void Next_Block(Graphics g) {
			for (int a = 0; a < 4; a++) {
				for (int b = 0; b < 4; b++) {
					if (block[rand2][0][a][b] == 1) {
						g.fill3DRect(b * blocksize + 450, a * blocksize + 60, blocksize, blocksize, true);
					}
				}
			}
		}

		public void Show_Block(Graphics g) {
			g.setColor(Color.PINK);

			for (int y = 0; y < 19; y++) {
				for (int x = 1; x < 11; x++) {
					if (gameboard[y][x] == 1) {
						g.fill3DRect(x * blocksize + 30, y * blocksize + 30, blocksize, blocksize, true);
					}
				}
			}
		}

		public void Break_Block(int cnt1, int cnt2, Graphics g) {
			int event = 0;
			for (int y = 0; y < 19; y++) {
				for (int x = 1; x < 11; x++) {
					if (gameboard[y][x] == 1) {
						cnt2++;
					}
				}
				if (cnt2 == 10) {
					for (int i = y; i > 1; i--) {
						for (int j = 1; j < 12; j++) {
							gameboard[i][j] = 0;
							gameboard[i][j] = gameboard[i - 1][j];
						}
					}
					score += 100;

				} else {
					Down_Block(cnt1, g); 
				}
				cnt2 = 0;
			}
		}

		public void Down_Block(int cnt1, Graphics g) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (block[rand1][rot][i][j] == 1) {
						getX[cnt1] = ((j * blocksize) + width) / blocksize; 
						getY[cnt1] = ((i * blocksize) + height) / blocksize;
						g.fill3DRect(getX[cnt1] * blocksize + 30, getY[cnt1] * blocksize + 30, blocksize, blocksize,
								true);

						cnt1++;
					}
				}
			}
		}

		
		public void Stack_Block() {
			try {
				for (int i = 0; i < 4; i++) {
					if (gameboard[getY[i] + 1][getX[i]] == 1) {
							
						for (int j = 0; j < 4; j++) {

							gameboard[getY[j]][getX[j]] = 1;

							width = 150;
							height = 0;
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

		
		public int Crash_LEFT() {
			for (int i = 0; i < 4; i++) {
				if (gameboard[getY[i]][getX[i] - 1] == 1) 
					return 1;
			}
			return 0; 
		}

		
		public int Crash_RIGHT() {

			for (int i = 0; i < 4; i++) {
				if (gameboard[getY[i]][getX[i] + 1] == 1) 
					return 1;
			}
			return 0; 
		}

		
		public void Rotation_Save() {
			
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

			
			int check = 0;
			int blank = 0;

			

			if (gameboard[getY[0]][getX[0]] == 1 || (rand1 == 6 && gameboard[getY[2]][getX[2]] == 1)
					|| (rand1 == 1 && gameboard[getY[1]][getX[1]] == 1)) {
				check = 1; 

				
				if (rand1 == 3) { 
					for (int i = 1; i < 5; i++)
						if (gameboard[getY[0]][getX[0] + i] == 0)
							blank++;
					if (blank < 4)
						check = 4;

				} else { 
					for (int i = 1; i < 4; i++)
						if (gameboard[getY[0]][getX[0] + i] == 0)
							blank++;
					if (blank < 3)
						check = 4;

				}

			}

			

			else if (gameboard[getY[2]][getX[2]] == 1) {

				check = 2; 
				
				for (int i = 1; i < 5; i++)
					if (gameboard[getY[2]][getX[2] - i] == 0)
						blank++;
				if (blank < 4)
					check = 4;
				

			} else if (gameboard[getY[3]][getX[3]] == 1) {

				check = 3; 
				
				for (int i = 0; i < 5; i++) {
					if (gameboard[getY[3]][getX[3] - i] == 0) {
						blank++;
					}
				}
				if (blank < 4) {
					check = 4;
				}
			}

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

		public void Background(Graphics g) {
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

		// 방향키 동작 설정
		void Block_Down() {
			height += blocksize;
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
			if (num == 0 && end == false) {
				width -= blocksize;
				MP.repaint();
			}
		}

		void Right_Block() {
			int num = Crash_RIGHT();
			if (num == 0 && end == false) {
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
						System.out.println("에러");
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
			
			while (true) {
				try {
					sleep(500);
					if (end == false) {
						MP.Block_Down();	
						// limit이 false일 경우에만 작동. true가 되면 테트리스 작동중지
					}
						
				} catch (InterruptedException e) {
					return;
				} 
			}
		}
	}

	public static void main(String[] args) {
		new TetrisSingle();
	}
}