package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Manager.Setting;
import Manager.Shoppingmall;

public class Login extends JFrame implements ActionListener, Serializable {
	private static Login login;
	JPanel nP, cP, sP, eP;
	JLabel idLabel, pwLabel, joinlabel;
	JTextField idField, pwdField, loginField;
	JButton loginBtn, joinBtn;
	Socket client = null;
	MsCenter mc = null;
	Socket withClient = null;
	Socket withClient2 = null;
	private String msg;
	private static ClientChat ch=null;
	private static MsCenter ms;

	Login(ClientChat ch) {
		super("로그인");
		this.ch = ch;
		createpanel();
		setClose();
	}
	public static Login getInstance() {
		if(login==null) {
			login=new Login(ch);
		}
		return login;
		
		
	}

	private void createpanel() {
		this.setLayout(new BorderLayout());
		nP = new JPanel();
		nP.setBorder(new EmptyBorder(10, 10, 0, 10));
		idLabel = new JLabel("ID");
		nP.add(idLabel);

		// 텍스트 필드 만들기
		idField = new JTextField(15);
		nP.add(idField);

		// center 패널 만들기
		cP = new JPanel();
		pwLabel = new JLabel("암 호");
		pwdField = new JPasswordField(15);

		cP.add(pwLabel);
		cP.add(pwdField);
		// south 패널 만들기
		sP = new JPanel();
		loginBtn = new JButton("로그인");
		sP.add(loginBtn);

		eP = new JPanel();
		joinBtn = new JButton("회원가입");
		sP.add(joinBtn);
		loginBtn.addActionListener(this);
		joinBtn.addActionListener(this);

		// 패널 프레임에 넣기 위치도 지정"".
		this.add(nP, "North");
		this.add(cP, "Center");
		this.add(sP, "South");
		this.add(eP, "East");

		// 색 지정
		// this.setBackground(Color,blue);

		// 사이즈 정해주기
		this.setBounds(400, 500, 300, 200);

	}

	public void setClose() {
		// this.setVisible(false);
		// 자동으로 꺼지게만듬
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		// true면 화면에서 나타나고 false면 화면에서 사라져라
		this.setVisible(true);
	}

	private void loginchk(String msg) {
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] check = { idField.getText(), pwdField.getText(), "login" };
				//	mc = new MsCenter(ch);
					ms.allMsg(check);
					// client.streamSet(check);
//					int go = loginresult(msg);
//					if (go == 1) {
//						JOptionPane.showMessageDialog(null, "로그인 완료");
//						System.out.println("쇼핑몰고고");
//					} else if (go == 5) {
//						JOptionPane.showMessageDialog(null, "로그인 완료");
//						System.out.println("세팅고고");
//					} else if (go == 10) {
//						JOptionPane.showMessageDialog(null, "존재하지 않는 아이디거나 비밀번호가 맞지 않습니다.");
//						setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//						idField.setText("");
//						pwdField.setText("");
//					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loginchk(msg);
		gosignup();
	}

	private void gosignup() {
		joinBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Signup join = Signup.getInstance();
				new Signup(ch);
			}

		});
	}

	public int loginresult(String msg) {
		if (msg.contains("login/yes/1")) {
			JOptionPane.showMessageDialog(null, "로그인 완료");
			new Shoppingmall();
			System.out.println("쇼핑몰고고");
			return 1;
		} else if (msg.contains("login/yes/5")) {
			JOptionPane.showMessageDialog(null, "로그인 완료");
			new Setting(withClient,withClient2);
			System.out.println("세팅고고");
			return 5;
		} else if (msg.contains("login/yes/10")) {
			JOptionPane.showMessageDialog(null, "존재하지 않는 아이디거나 비밀번호가 맞지 않습니다.");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			idField.setText("");
			pwdField.setText("");
			return 10;
		}
		return 0;
	}

}
