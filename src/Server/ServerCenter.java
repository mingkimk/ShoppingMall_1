package Server;

import java.net.Socket;
import java.util.ArrayList;

import DB.DAOCenter;

public class ServerCenter {
	private ArrayList<ServerChat> sList = new ArrayList<>();
	private DAOCenter dc = DAOCenter.getInstance();
	private String[] check = null;
	private ServerChat chat = null;
	private Socket withClient = null;
	private Socket withClient2 = null;
	// private static ServerCenter sc;

	public ServerCenter() {

	}

//	public static ServerCenter getInstance() {
//		if (sc == null) {
//			new ServerCenter();
//		}
//		return sc;
//	}

	public void addServerChat(ServerChat s) {
		this.sList.add(s);
	}

	public void select(Object objectMember, Socket withClient, Socket withClient2) {
		chat = chat;
		this.withClient = withClient;
		this.withClient2 = withClient2;
//		check = (String[]) objectMember;
//		for (int i = 0; i < check.length; i++) {
//			if (check[check.length - 1].equals("login")) { // 로그인 체크
//				System.out.println(check[i]);
//				System.out.println("이건 로그인 : " + check[check.length - 1]);
//			} else if (check[check.length - 1].equals("join")) { // 회원가입 체크
//				System.out.println(check[i]);
//				System.out.println("이건회원가입 : " + check[check.length - 1]);
//				dc.Insert(check);
//				break;
//			} else if (check[check.length - 1].equals("check")) { // 아이디 중복 체크
//				System.out.println(check[i]);
//				System.out.println("이건중복체크지롱  : " + check[check.length - 1]);
//			}
		dc.whichone(objectMember, withClient, withClient2);
		// }
	}

	public void select(String msg, Socket withClient, Socket withClient2) {
		this.withClient = withClient;
		this.withClient2 = withClient2;
		dc.list(msg,withClient2);
		//ArrayList<String[]> list = dc.list();
		//goSC2(list,withClient2);
	}

	public void goSC(String msg, Socket withClient) {
		System.out.println("여기는 서버센터야 오바 : " + msg);
		chat = new ServerChat(withClient, withClient2, null);
		chat.send(msg, withClient);
	}

	public void goSC2(ArrayList<String[]> list, Socket withClient2) {
		this.withClient2=withClient2;
		System.out.println("여기는 서버센터야 오바 리스트는 넘어왔니?? : " + list);
		chat = new ServerChat(withClient, withClient2, null);
		chat.send2(list, withClient2);
		System.out.println("send2로 간다");
	}

}
