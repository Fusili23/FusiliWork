/* 1978009 양승언 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*; //필요한 클래스들을 임포트 해준다

public class Calculator extends JFrame { // JFrame 을 상속받은 Calculator 클래스 생성
	public Calculator() { // 생성자

		setTitle("계산기"); // 타이틀 달기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 닫으면 종료
		Container c = getContentPane(); // 부착된 컨텐트팬 알아내기
		c.setLayout(null); // 레이아웃은 없다
		setSize(800, 800); // 사이즈를 800, 800 설정

		JLabel screen = new JLabel();// screen이름을 가진 레이블 생성
		screen.setSize(300, 100);// 레이블의 사이즈 설정
		screen.setLocation(200, 100);// 레이블의 위치 설정
		screen.setFont(new Font("Arial", Font.BOLD, 60));// 레이블 글씨체 설정
		screen.setOpaque(true);
		screen.setBackground(Color.WHITE);
		c.add(screen);// 레이블을 컨테이너에 추가

		JButton cancel = new JButton("Erase");// 취소기능을 가진 JButton 생성
		cancel.setSize(100, 100);// 버튼 사이즈 설정
		cancel.setLocation(500, 100);// 버튼 위치 설정
		c.add(cancel);// 버튼을 컨테이너에 추가
		cancel.addActionListener(new ActionListener() {// 버튼에 액션 리스너를 달아준다
			public void actionPerformed(ActionEvent e) {
				int t = screen.getText().length();/// t에 screen에서 가져온 String 길이를 저장한다
				if (t == 0)// t가 0이라면
					screen.setText("");// t가 0이라면 아무런 입력도 발생하지 않았기 때문에 그 상태를 유지한다
				else if (t > 0) {// t가 0보다 크다면 입력이 발생했기 때문에
					String b = screen.getText().substring(0, t - 1);// b에다가 문자열에 입력된 값에서 마지막 글자를 지운 문자열을 저장
					screen.setText(b);// b를 화면에 표시
				} else
					screen.setText("error");// 다른 경우가 발생하면 에러를 표시한다
			}
		});
		
		JButton btn1 = new JButton("7"); // 7을 입력하는 버튼을 생성
		btn1.setSize(100, 100);// 사이즈 설정
		btn1.setLocation(200, 200);// 좌표설정
		c.add(btn1);// 컨테이너에 버튼 추가
		btn1.addActionListener(new ActionListener() {// 버튼에 액션리스너 달아주기
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "7");// 버튼이 눌린다면 이미 입력된 텍스트 뒤에 7을 추가한다
			}
		});

		JButton btn2 = new JButton("8");// 8을 입력하는 버튼을 생성
		btn2.setSize(100, 100);// 사이즈 설정
		btn2.setLocation(300, 200);// 좌표설정
		c.add(btn2);// 컨테이너에 버튼 추가
		btn2.addActionListener(new ActionListener() {// 버튼에 액션리스너 추가
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "8");// 버튼이 눌린다면 이미 입력된 텍스트 뒤에 7을 추가한다
			}
		});

		JButton btn3 = new JButton("9");// 9를 입력하는 버튼을 생성
		btn3.setSize(100, 100);// 사이즈 설정
		btn3.setLocation(400, 200);// 좌표설정
		c.add(btn3);// 컨테이너에 버튼 추가
		btn3.addActionListener(new ActionListener() {// 버튼에 액션리스너 추가
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "9");// 버튼이 눌린다면 이미 입력된 텍스트 뒤에 9을 추가한다
			}
		});

		JButton btn4 = new JButton("/");// 나누기 버튼 생성
		btn4.setSize(100, 100);// 사이즈 설정
		btn4.setLocation(500, 200);// 좌표설정
		c.add(btn4);// 컨테이너에 버튼 추가
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = screen.getText();// 레이블에 입력된 문자열을 가져온다
				char arr[] = new char[s.length()];// 가져온 문자열의 크기만큼을 가진 배열생성
				int x;
				int i;
				for (i = 0; i < s.length(); i++) {
					arr[i] = s.charAt(i);// 배열에 문자열을 하나씩 쪼개서 넣어준다
				}

				if (arr[i - 1] == '+')
					x = 1;// 마지막 인덱스의 값이 + 라면 x = 1
				else if (arr[i - 1] == 'X')
					x = 1;// 마지막 인덱스의 값이 X 라면 x = 1
				else if (arr[i - 1] == '-')
					x = 1;// 마지막 인덱스의 값이 - 라면 x = 1
				else if (arr[i - 1] == '/')
					x = 1;// 마지막 인덱스의 값이 / 라면 x = 1
				else
					x = 0;// 위의 네가지 조건에 해당되지 않는다면 x = 0

				switch (x) {
				case 1:// x가 1일때
					arr[i - 1] = '/';// 마지막 인덱스의 값을 /로 바꿔준다
					String str = String.valueOf(arr);// 변경된 배열을 문자열로 바꿔준다
					screen.setText(str);
					break;
				case 0:
					screen.setText(screen.getText() + "/");// x가 0이라면 스크린에서 가져온 문자열의 끝에 /를 추가해준다
					break;
				default:
					screen.setText(screen.getText() + "/");// 어느곳에도 해당되지 않는다면 스크린에서 가져온 문자열의 끝에 /를 추가해준다
					break;
				}
			}
		});

		JButton btn5 = new JButton("4");// 4를 입력하는 버튼생성
		btn5.setSize(100, 100);// 사이즈 설정
		btn5.setLocation(200, 300);// 좌표설정
		c.add(btn5);// 컨테이너에 버튼추가
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "4");// 클릭이 발생하면 입력된 문자열 뒤에 4를 추가한다
			}
		});

		JButton btn6 = new JButton("5");// 5를 입력하는 버튼 생성
		btn6.setSize(100, 100);// 사이즈 설정
		btn6.setLocation(300, 300);// 좌표지정
		c.add(btn6);// 컨테이너에 버튼추가
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "5");// 클릭이 발생하면 입력된 문자열 뒤에 5를 추가한다
			}
		});

		JButton btn7 = new JButton("6");// 6을 입력하는 버튼 생성
		btn7.setSize(100, 100);// 사이즈 설정
		btn7.setLocation(400, 300);// 좌표 지정
		c.add(btn7);
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "6");// 클릭이 발생하면 입력된 문자열 뒤에 6를 추가한다
			}
		});

		JButton btn8 = new JButton("X");// 곱하기버튼 생성
		btn8.setSize(100, 100);// 사이즈 설정
		btn8.setLocation(500, 300);// 좌표 설정
		c.add(btn8);// 컨테이너에 버튼 추가
		btn8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = screen.getText(); // 액션 이벤트가 발생하면 스크린에 입력된 텍스트를 가져온다
				char arr[] = new char[s.length()];// 가져온 문자열의 길이만큼의 배열을 생성한다
				int x;
				int i;
				for (i = 0; i < s.length(); i++) {
					arr[i] = s.charAt(i);// 문자열을 쪼개서 배열에 담아준다
				}
				// 문자열의 마지막에 연산 부호가 위치하면 x = 1
				if (arr[i - 1] == '+')
					x = 1;
				else if (arr[i - 1] == 'X')
					x = 1;
				else if (arr[i - 1] == '-')
					x = 1;
				else if (arr[i - 1] == '/')
					x = 1;
				else// 그렇지 않다면 x = 0
					x = 0;

				switch (x) {
				case 1:
					arr[i - 1] = 'X';
					String str = String.valueOf(arr);
					screen.setText(str);
					break;// 문자열이 연산자로 끝났다면 끝을 X로 바꿔준다
				case 0:
					screen.setText(screen.getText() + "X");
					break;//연산자로 끝나지 않았다면 원래대로 연산자를 추가한다
				default:
					screen.setText(screen.getText() + "X");
					break;//디폴트 케이스는 케이스 0과 똑같이
				}
			}
		});

		JButton btn9 = new JButton("1");//1을 입력하는 버튼
		btn9.setSize(100, 100);//사이즈
		btn9.setLocation(200, 400);//좌표
		c.add(btn9);//컨테이너에 버튼 추가
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "1");//버튼이 눌리면 원래 텍스트에 1을 추가
			}
		});

		JButton btn10 = new JButton("2");//2를 입력하는 버튼 추가
		btn10.setSize(100, 100);//사이즈
		btn10.setLocation(300, 400);//좌표
		c.add(btn10);//컨테이너에 버튼 추가
		btn10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "2");//버튼이 눌리면 원래 텍스트에 2을 추가
			}
		});

		JButton btn11 = new JButton("3");
		btn11.setSize(100, 100);//사이즈
		btn11.setLocation(400, 400);//좌표
		c.add(btn11);
		btn11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "3");//버튼이 눌리면 원래 텍스트에 3을 추가
			}
		});

		JButton btn12 = new JButton("-");// 빼기 버튼
		btn12.setSize(100, 100);//사이즈
		btn12.setLocation(500, 400);//좌표
		c.add(btn12);//컨테이너에 버튼 추가
		btn12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = screen.getText();//스크린에서 입력된 텍스트 가져오기
				char arr[] = new char[s.length()];//가져온 문자열의 길이만큼 배열 생성
				int x;
				int i;
				for (i = 0; i < s.length(); i++) {
					arr[i] = s.charAt(i);//문자열을 쪼개서 배열에 담아준다
				}
				//마지막 문자가 연산 부호라면 x = 1
				if (arr[i - 1] == '+')
					x = 1;
				else if (arr[i - 1] == 'X')
					x = 1;
				else if (arr[i - 1] == '-')
					x = 1;
				else if (arr[i - 1] == '/')
					x = 1;
				else
					x = 0;//아님 말구

				switch (x) {
				case 1:
					arr[i - 1] = '-';//x = 1 일때 마지막 입력된 값을 빼준다
					String str = String.valueOf(arr);//문자배열을 다시 문자열로 바꾼다
					screen.setText(str);//스크린에 출력
					break;
				case 0://x 가 1 이 아니라면 그대로 입력받은 부호를 출력
					screen.setText(screen.getText() + "-");
					break;
				default:
					screen.setText(screen.getText() + "-");
					break;
				}
			}
		});

		JButton btn13 = new JButton("hi");
		btn13.setSize(100, 100);
		btn13.setLocation(200, 500);
		c.add(btn13);

		JButton btn14 = new JButton("0");// 0입력 버튼
		btn14.setSize(100, 100);//사이즈
		btn14.setLocation(300, 500);//좌표
		c.add(btn14);//컨테이너에 버튼 추가
		btn14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(screen.getText() + "0");//입력된 텍스트 추가
			}
		});

		JButton btn15 = new JButton("Reset");//초기화 버튼 생성
		btn15.setSize(100, 100);//사이즈
		btn15.setLocation(400, 500);//좌표
		c.add(btn15);//컨테이너에 버튼 추가
		btn15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText("");//초기화를 위해 스크린에 아무것도 띄우지 않는다
			}
		});
		JButton btn16 = new JButton("+");// + 버튼
		btn16.setSize(100, 100);//사이즈
		btn16.setLocation(500, 500);//좌표
		c.add(btn16);//컨테이너에 버튼 추가
		btn16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = screen.getText();//스크린에 입력된 텍스트 가져오기
				char arr[] = new char[s.length()];//텍스트 길이만큼 배열 생성
				int x;
				int i;
				for (i = 0; i < s.length(); i++) {
					arr[i] = s.charAt(i);// 문자열을 쪼개서 배열에 담는다
				}
				//마지막 입력이 연산부호면 x = 1
				if (arr[i - 1] == '+')
					x = 1;
				else if (arr[i - 1] == 'X')
					x = 1;
				else if (arr[i - 1] == '-')
					x = 1;
				else if (arr[i - 1] == '/')
					x = 1;
				else//아니면 0
					x = 0;

				switch (x) {
				case 1:
					arr[i - 1] = '+';//마지막 인덱스를 + 로 변경
					String str = String.valueOf(arr);//스트링에 배열을 다시 집어넣기
					screen.setText(str);//출력
					break;
				case 0:
					screen.setText(screen.getText() + "+");//아니라면 입력 부호를 그대로 출력
					break;
				default:
					screen.setText(screen.getText() + "+");
					break;
				}
			}
		});

		JButton btn17 = new JButton("1978009 양승언");
		btn17.setSize(300, 100);
		btn17.setLocation(200, 600);
		c.add(btn17);

		JButton btn18 = new JButton("=");//계산을 완료하는 버튼
		btn18.setSize(100, 100);//사이즈
		btn18.setLocation(500, 600);//좌표
		c.add(btn18);//컨테이너에 버튼 추가
		btn18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = screen.getText();//스크린에서 텍스트 가져오기
				int x, y, n;
				char[] arr = new char[s.length()];//문자열 길이만큼 배열 생성
				for (int i = 0; i < s.length(); i++) {
					arr[i] = s.charAt(i);//문자열을 배열에 담기

				}

				for (int i = 0; i < s.length(); i++) {
					if (arr[i] == '+') {//연산 부호가 + 라면
						String[] array = s.split("\\+");
						//해당 연산자를 기준으로 문자열을 나눈다
						x = Integer.parseInt(array[0]);//나눈 문자열을 정수로 바꿔준다
						y = Integer.parseInt(array[1]);
						n = x + y;
						screen.setText(Integer.toString(n));// 두 수를 연산한 값을 출력
					}
				}//이하 동일
				for (int i = 0; i < s.length(); i++) {
					if (arr[i] == 'X') {
						String[] array = s.split("X");

						x = Integer.parseInt(array[0]);
						y = Integer.parseInt(array[1]);
						n = x * y;
						screen.setText(Integer.toString(n));
					}
				}
				for (int i = 0; i < s.length(); i++) {
					if (arr[i] == '/') {
						String[] array = s.split("/");

						x = Integer.parseInt(array[0]);
						y = Integer.parseInt(array[1]);
						n = x / y;
						screen.setText(Integer.toString(n));
					}
				}
				for (int i = 0; i < s.length(); i++) {
					if (arr[i] == '-') {
						String[] array = s.split("-");

						x = Integer.parseInt(array[0]);
						y = Integer.parseInt(array[1]);
						n = x - y;
						screen.setText(Integer.toString(n));
					}
				}
			}
		});

		setVisible(true);//보이도록 설정
	}

	public static void main(String[] args) {
		new Calculator();// 생성자 호출
	}
}
