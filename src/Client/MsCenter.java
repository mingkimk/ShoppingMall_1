package Client;

import java.net.Socket;
import java.util.ArrayList;

import DB.ManagementDAO;
import Manager.Setting;
import sun.security.jca.GetInstance;

public class MsCenter {
	private String msg = null;
	private Login login = null;
	private Signup join = null;
	// private Signup join = Signup.getInstance();
	private static ClientChat ch = null;
//	private Socket withClient = null;
//	private Socket withClient2 = null;
	Setting sett = null;
	// private ClientChat ch;
	private static MsCenter ms;

	public MsCenter(ClientChat ch) {
		this.ch = ch;
		// this.withClient = withClient;
		// this.withClient2 = withClient2;
		// checkMsg(msg);
//		this.join=join();
		new Login(ch);

	}

	public static MsCenter getInstance() {
if(ms==null) {
	ms=new MsCenter(ch);
}
	
	return ms;	
	}


//			public static ManagementDAO getInstance() {
//				if (DAOobj == null) {
//					DAOobj = new ManagementDAO();
//				}
//				return DAOobj;
//			}

	public void checkMsg(String msg) {
		if (msg.contains("member")) {
			join = new Signup(msg);
			join.membercheck(msg);
		} else if (msg.contains("login")) {

			login.loginresult(msg);
		} else if (msg.contains("check")) {

			join.idchk(msg);
			System.out.println("메세지체크");
			// join.jbchk(msg);
		}

	}

	public String[] allMsg(String[] in) {
		for (int i = 0; i < in.length; i++) {
			System.out.println("리턴값 확인중" + in[i]);
		}
		ch.streamSet(in);
		return in;

	}
	public ArrayList<String[]> list(String msg, Socket withClient) {
	
		System.out.println(msg);
		
		ch.send(msg, withClient);
		System.out.println("이건 메세지 전달");
		ch.send(msg, withClient);
		return null;
	}
		// this.withClient2=withClient;
		// this.ch = ch;
		// Setting sett = new Setting(withClient,withClient2);
		// sett.init(list);
		//System.out.println("msg:" + msg);
		//this.withClient2 = withServer;

	
//		if(list!=null) {
//			return list;
//		}
//		return list;
	//	return null;
	

	public ArrayList<String[]> back(Socket withServer, Socket withServer2, ArrayList<String[]> list) {
		System.out.println("This is MsCenter back");
		Setting sett = new Setting(withServer, withServer2);// withClient, withClient2
		System.out.println("go to Setting init");
		sett.init(list, withServer2);
		return list;

	}

}
