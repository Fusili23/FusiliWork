/* 1978009 양승언 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame {
	JLabel la;//레이블 생성
	JPasswordField jp;// 패스워드필드 생성
	JPasswordField jp1;// 패스워드필드 생성

	public SignUp() {
		setTitle("회원가입 프로그램");//타이틀
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//작업 종료
		Container c = getContentPane();//컨텐트팬 알아내기
		c.setLayout(null);//레이아웃 없음
		setSize(500, 800);//사이즈 설정
		String[] s = { "ID", "비밀번호", "비밀번호 중복체크", "이름", "생년월일", "성별", "연락처", "이메일", "주소", "취미", "기념일", "비밀번호 질문",
				"답변" };//각 요소의 이름을 가진 배열 생성
		String[] t = { "애완동물의 이름은?", "좋아하는 운동은?", "좋아하는 음식은?", "전공은?", "태어난 도시는?" };//질문 콤보박스에 들어갈 요소
		JLabel[] arr = new JLabel[13];//레이블 배열
		JTextField[] arr1 = new JTextField[13];//텍스트필드 배열

		for (int i = 0; i < 13; i++) {
			arr[i] = new JLabel();//인덱스 수만큼 레이블 생성
			arr[i].setText(s[i]);//배열 s에 담긴 요소들을 레이블에 하나씩 붙여준다
			arr[i].setBounds(50, 50 * i + 20, 150, 20);//레이블을 일정 간격으로 배열
			c.add(arr[i]);//컨테이너에 레이블 추가

			if ((i != 5 && i != 11) && (i != 1 && i != 2)) {
				arr1[i] = new JTextField();//특정 인덱스를 제외하고 텍스트필드를 달아준다
				arr1[i].setBounds(50, 50 * (i + 1), 150, 20);//사이즈 위치 설정
				c.add(arr1[i]);//컨테이너에 추가
			}
		}

		jp = new JPasswordField();//패스워드필드 생성
		jp.setBounds(50, 100, 150, 20);//위치 크기 설정

		c.add(jp);//컨테이너에 붙여준다

		jp1 = new JPasswordField();//패스워드 필드 생성
		jp1.setBounds(50, 150, 150, 20);//위치 크기 설정
		c.setFocusable(true);//포커스를 받을 수 있도록 한다
		c.requestFocus();//포커스 요청
		c.add(jp1);//컨테이너에 추가
		la = new JLabel();//레이블 생성
		la.setText("비밀번호가 다릅니다");//디폴트 텍스트 설정
		c.add(la);//컨테이너에 추가
		la.setBounds(200, 100, 150, 20);
		

		JButton b = new JButton("확인");//확인버튼 생성
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char[] s = jp.getPassword();//배열 s에 비밀번호를 가져온다
				char[] s1 = jp1.getPassword();//배열 s1에 비밀번호를 가져온다

				int flag = 0;
				for (int i = 0; i < s.length; i++) {
					if (s[i] != s1[i])
						flag = 1;
				}
				if (flag == 0) {
					la.setText("일치합니다");//텍스트 설정
				} else
					la.setText("X");//레이블 텍스트 설정
			}
		});
		b.setBounds(200, 150, 80, 80);//위치 크기 설정
		c.add(b);//컨테이너에 추가
		
		JRadioButton j1, j2;
		ButtonGroup group = new ButtonGroup();//버튼 그룹으로 라디오버튼을 묶는다
		j1 = new JRadioButton("남자");//j1은 남자
		j2 = new JRadioButton("여자");//j2는 여자
		j1.setBounds(50, 300, 50, 20);//위치 크기 설정
		j2.setBounds(100, 300, 50, 20);//위치 크기 설정
		group.add(j1);//그룹에 추가
		group.add(j2);//그룹에 추가
		c.add(j1);//컨테이너에 추가
		c.add(j2);//컨테이너에 추가
		
		JComboBox<String> box = new JComboBox<String>(t);
		box.setBounds(50, 600, 150, 20);//위치 좌표 설정
		c.add(box);//컨테이너에 추가
		
		JButton btn = new JButton("완료");//버튼 생성
		btn.setBounds(75, 700, 80, 80);//위치 좌표 설정
		btn.addActionListener(new ActionListener() {// 버튼에 액션 리스너를 달아준다
			public void actionPerformed(ActionEvent e) {
				String[] arr = new String[13];//문자열 배열 생성
				String gender, question, ID, Password, birth, phone, email, 
				address, hobby, memorial, answer, name;
				gender = "";
				for (int i = 0; i < 13; i++) {
					if ((i != 5 && i != 11) && (i != 1 && i != 2)) {
						arr[i] = arr1[i].getText();//배열에 가져온 텍스트들을 저장한다
					}
				}
				ID = arr[0];
				Password = arr[1];
				name = arr[3];
				birth = arr[4];
				phone = arr[6];
				email = arr[7];
				address = arr[8];
				hobby = arr[9];
				memorial = arr[10];
				answer = arr[12];

				if (j1.isSelected()) {
					gender = "남자";//j1가 설정되어 있으면 남자
				}
				else if (j2.isSelected()) {
					gender = "여자";//j2가 설정되어 있으면 여자
				}
				
				question = box.getSelectedItem().toString();//질의 문자열에는 콤보박스에서 가져온 항목을 표시
				// 입력받은 값을 모두 출력해준다
				System.out.println("아이디: "+ ID);
				System.out.println("이름: "+name);
				System.out.println("생일: " + birth);
				System.out.println("성별: " + gender);
				System.out.println("전화번호: " + phone);
				System.out.println("이메일: " + email);
				System.out.println("주소: " + address);
				System.out.println("취미: " +hobby);
				System.out.println("기념일: " + memorial);
				System.out.println(question +"에 대한 답은" + answer);
				
			}
		});
		c.add(btn);//컨테이너에 버튼 추가

		setVisible(true);//보이도록 한다
	}

	public static void main(String[] args) {
		new SignUp();
	}
}
