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

// 멀티플레이 
public class Tetris extends JFrame {
	Container c = getContentPane();		// 컨탠트 팬을 생성
	MainPanel MP = new MainPanel();		// 메인 패널 생성
	JButton mbtn = new JButton();		// JButton 생성
	JLabel NB = new JLabel();			// JLabel 생성

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

		add(MP);	// 메인 패널 생성

		BT = new BlockThread();	// 블록스레드 설정

		NB.setText("NEXT BLOCK");
		NB.setFont(new Font("", Font.BOLD, 30));

		SL1.setText("SCORE");
		SL1.setFont(new Font("", Font.BOLD, 30));

		SL2.setFont(new Font("", Font.BOLD, 30));

		MP.addKeyListener(new KeyAdapter() {	// 블록을 움직이기 위한 키어댑터 설정
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();

				if (keyCode == KeyEvent.VK_UP) {	// 위 방향키 입력시
					MP.Rotation_Block();	// 블록 회전
				}
				if (keyCode == KeyEvent.VK_DOWN) {	// 아래 방향키 입력시
					MP.Down_Block();	// 블록을 내림
				}
				if (keyCode == KeyEvent.VK_LEFT) {	// 왼쪽 방향키 입력시 
					MP.Left_Block();	// 블록을 왼쪽으로 이동
				}
				if (keyCode == KeyEvent.VK_RIGHT) {	// 오른쪽 방향키 입력시 
					MP.Right_Block();	// 블록을 오른쪽으로 이동
				}
			}
		});

		mbtn.addActionListener(new ActionListener() {	// 버튼 리스너 설정
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});

		MP.setBackground(Color.WHITE);	// 배경을 흰색으로 설정
		setSize(1290, 800);	// 크기 설정

		setVisible(true);	// 화면에 나오게함

		MP.requestFocus(true);	// 키 이벤트 받을 컴포넌트를 강제로 설정
		BT.start();	// 스레드 시작
	}

	class SaveScore extends JFrame {		// 결과를 DB에 저장하는 JFrame
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
					String s = jtf1.getText();	// 텍스트필드에 적은 이름을 String으로 변환하여 저장
					String s1 = Integer.toString(score);	// 점수를 String으로 변환하여 저장
					sb.append("insert into record(name, record) values ('" + s + "', " + s1 + ");");	// 이름과 점수를 DB에 저장

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
	class ReStart extends JFrame {	// 점수 등록 후 다시 게임을 할 것인지 시작화면으로 돌아갈 것인지 정하는 창


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

			Multi_Show_Block(g);	// 멀티 플레이 화면 출력
			
			if (fin == 1) {

				rand2 = (int) (Math.random() * 7);

				fin = 0;
			}
		}
		
		public void Multi_Show_Block(Graphics g) {	// 멀티플레이의 화면의 정보를 받아와 출력
	          g.setColor(Color.PINK);

	          for (int y = 0; y < 20; y++) {
	             for (int x = 0; x < 12; x++) {
	                if (gameboard2[y][x] == 1) {
	                   g.fill3DRect(x * blocksize + 630, y * blocksize + 30, blocksize, blocksize, true);
	                }
	             }
	          }
	       }
		
		public void Next_Block(Graphics g) {	// 다음 블록을 미리봄
			for (int a = 0; a < 4; a++) {
				for (int b = 0; b < 4; b++) {
					if (block[rand2][0][a][b] == 1) {	// 랜덤 생성된 블록을 미리 출력
						g.fill3DRect(b * blocksize + 450, a * blocksize + 60, blocksize, blocksize, true);
					}
				}
			}
		}

		public void Show_Block(Graphics g) {	// 벽을 생성
			g.setColor(Color.PINK);

			for (int y = 0; y < 19; y++) {
				for (int x = 1; x < 11; x++) {
					if (gameboard[y][x] == 1) {	// 게임 보드가 1이면 벽을 생성
						g.fill3DRect(x * blocksize + 30, y * blocksize + 30, blocksize, blocksize, true);
					}
				}
			}
		}

		public void Break_Block(int cnt1, int cnt2, Graphics g) {	// 블럭을 부심
			int event = 0;
			for (int y = 0; y < 19; y++) {
				for (int x = 1; x < 11; x++) {
					if (gameboard[y][x] == 1) {
						cnt2++;	// 한줄에 하나가 차있을떄마다 count 를 더함
					}
				}
				if (cnt2 == 10) {	// 한줄이 다 참
					for (int i = y; i > 1; i--) {
						for (int j = 1; j < 12; j++) {
							gameboard[i][j] = 0;	// 가득 찬 줄을 0으로 초기화 한 후
							gameboard[i][j] = gameboard[i - 1][j];	// 위 층에 있던 줄을 아래로 내림
						}
					}
					score += 100;	// 점수를 100점 더함

				} else {
					Down_Block(cnt1, g); // 한 행이 모두 블록으로 채워지지 않을 때만 블록이 내려가도록 함
				}
				cnt2 = 0;
			}
		}

		public void Down_Block(int cnt1, Graphics g) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (block[rand1][rot][i][j] == 1) {
						getX[cnt1] = ((j * blocksize) + width) / blocksize; // getX, getY에 블록의 좌표를 저장
						getY[cnt1] = ((i * blocksize) + height) / blocksize;
						g.fill3DRect(getX[cnt1] * blocksize + 30, getY[cnt1] * blocksize + 30, blocksize, blocksize,
								true);	// 떨어지는 블록을 출력

						cnt1++;	// count를 더함
					}
				}
			}
		}

		// 바닥에 닿은 블록이 벽으로 변하는지 검사
		// 벽이 되면 width=150, height=0, rot = 0 으로 초기화
		public void Stack_Block() {
			try {
				for (int i = 0; i < 4; i++) {
					if (gameboard[getY[i] + 1][getX[i]] == 1) {
							
						for (int j = 0; j < 4; j++) {

							gameboard[getY[j]][getX[j]] = 1;

							width = 150;	// 가로
							height = 0;		// 세로
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

		
		public int Crash_LEFT() {	// 왼쪽 벽 충돌 감지
			for (int i = 0; i < 4; i++) {
				if (gameboard[getY[i]][getX[i] - 1] == 1) 
					return 1;	// 충돌하면 1을 반환해줌
			}
			return 0; // 충돌하지 않으면 0을 반환
		}

		
		public int Crash_RIGHT() {	// 오른쪽 벽 충돌 감지

			for (int i = 0; i < 4; i++) {
				if (gameboard[getY[i]][getX[i] + 1] == 1) 
					return 1;	// 충돌하면 1을 반환해줌
			}
			return 0; // 충돌하지 않으면 0반환
		}

		// getX, getY에 회전한 도형의 좌표를 기록해둠
		// X좌표 좌우 1, 2칸 이내에 벽이 있다면 그 길이만큼 좌우로 밀어서 배치
		public void Rotation_Save() {
			// getX,Y에 다음 회전 도형의 절대좌표를 모두 기록해두고, 밑에 구문에서 그 절대좌표의 값이 벽에 닿는지 판단
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

			// getX, getY의 좌표를 이용
			int check = 0;	// 도형이 벽과 겹치는지 체크
			int blank = 0;	// 공백 체크

			// 왼쪽 벽

			if (gameboard[getY[0]][getX[0]] == 1 || (rand1 == 6 && gameboard[getY[2]][getX[2]] == 1)
					|| (rand1 == 1 && gameboard[getY[1]][getX[1]] == 1)) {
				check = 1; // 회전한 도형의 위치가 벽과 겹치면 1

				
				if (rand1 == 3) { // 일자막대의 경우 회전할 여유가 있는 공백이 없으면 회전막음
					for (int i = 1; i < 5; i++)
						if (gameboard[getY[0]][getX[0] + i] == 0)
							blank++;
					if (blank < 4)
						check = 4;

				} 
				else { // 회전 할 수 있는 공백이 없다면 회전을 하지 않음
					for (int i = 1; i < 4; i++)
						if (gameboard[getY[0]][getX[0] + i] == 0)
							blank++;
					if (blank < 3)
						check = 4;

				}

			}

			// 오른쪽 벽

			else if (gameboard[getY[2]][getX[2]] == 1) {

				check = 2; // 회전한 도형의 위치가 벽과 겹치면 2
				

				for (int i = 1; i < 5; i++)
					if (gameboard[getY[2]][getX[2] - i] == 0)
						blank++;
				if (blank < 4)
					check = 4;
				

			} else if (gameboard[getY[3]][getX[3]] == 1) {

				check = 3; // 회전한 도형의 위치가 벽과 겹치면 3
				
				for (int i = 0; i < 5; i++) {
					if (gameboard[getY[3]][getX[3] - i] == 0) {
						blank++;
					}
				}
				if (blank < 4) {
					check = 4;
				}
			}
			// 체크된 도형에 맞게 이동
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

		public void Background(Graphics g) {	// 배경 생성
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
			height += blocksize;	// 블록의 크기만큼 내려감
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
			if (num == 0 && end == false) { // num이 1이면 충돌하여 왼쪽으로 가지 않음
				width -= blocksize;
				MP.repaint();
			}
		}

		void Right_Block() {
			int num = Crash_RIGHT();
			if (num == 0 && end == false) {	 // num이 1이면 충돌하여 오른쪽으로 가지 않음
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
			try {
				listener = new ServerSocket(9999);
				System.out.println("연결을 기다리고 있습니다.....");
				socket = listener.accept();
				System.out.println("연결되었습니다.");
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
						
						
						// limit이 false일 경우에만 작동. true가 되면 테트리스 작동중지
					}
						
				} catch (InterruptedException e) {
					return;
				} catch (IOException e) {
					System.out.println("상대 창을 읽어들이는중 오류가 발생했습니다");
				}
			}
		}
	}

	public static void main(String[] args) {
		new Tetris();
	}
}