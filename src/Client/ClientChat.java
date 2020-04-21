package Client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class ClientChat {
	private Socket withServer = null;
	private Socket withServer2 = null;
	private InputStream reMsg = null;
	private OutputStream sendMsg = null;
	private String[] chk = null;
	String nnn = "";
	Scanner in = new Scanner(System.in);
	// Signup member = new Signup(null);
	String msg = null;
	ArrayList<String[]> list;
	//private ClientChat ch;
	private ClientChat ch=null;
	public static MsCenter ms=MsCenter.getInstance();
	

	public ClientChat(Socket withServer, Socket withServer2) {
		this.withServer = withServer;
		this.withServer2 = withServer2;
		start();
		streamSet(chk);
		receive();
		receive2(withServer2);
		send(msg, withServer2);
	}

	private void start() {
		//new Login(withServer);
		new MsCenter(ch);

	}

	public void receive() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("receive start~~");

					while (true) {
					
						reMsg = withServer.getInputStream();
						byte[] reBuffer = new byte[1024];
						reMsg.read(reBuffer);
						msg = new String(reBuffer);
						if (msg != null) {
							msg = msg.trim();
							System.out.println("클라이언트에서 메세지를 받았어요." + msg);

							gotoCenter(msg);
						}
					}
				} catch (Exception e) {
					
					System.out.println("client send end !!!");
					return;
				}
			}
		}).start();
	}

	public void receive2(Socket withServer2) { // 방금 socket을 넣음
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//Thread.sleep(1000);
					System.out.println("receive start2~~");

					while (true) {
						// if (o = null) {
						reMsg = withServer2.getInputStream();
						byte[] reBuffer = new byte[1024];
						reMsg.read(reBuffer);
						System.out.println("read it?");
						ByteArrayInputStream bais = new ByteArrayInputStream(reBuffer);

						ObjectInputStream ois = new ObjectInputStream(bais);
						System.out.println("no?");
						
						Object o = ois.readObject();
						System.out.println("object");
						
						//if (o != null) {
					
						//	ArrayList<String[]> list = (ArrayList<String[]>) o;
							ArrayList<String> list = (ArrayList<String>) o;
						//	Collections.addAll(c, elements)
							for (int i = 0; i < list.size(); i++) {
//								System.out.print(list.get(i)[0]);
//								System.out.print(list.get(i)[1]);
								System.out.println(list.get(i));
								System.out.println("--------");
						
								System.out.println(" go to center2");
							}
							gotoCenter2(list);
						
							
						//}
						// }
					}

				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println("client send2 end !!!");
					return;
				}
			}
		}).start();
	}

	private void gotoCenter(String msg) {
		MsCenter mc = new MsCenter(ch);
		mc.checkMsg(msg);
		System.out.println("go to MsCenter checkMsg"+msg);
	}

	private void gotoCenter2(ArrayList<String> list) {
		MsCenter mc = new MsCenter(ch);
		System.out.println("--go to MsCenter back"+list);
		mc.back(withServer,withServer2, list);
	
	}

	public void streamSet(String[] check) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (check != null) {

						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(check);

						byte[] bowl = baos.toByteArray();

						sendMsg = withServer.getOutputStream();

						sendMsg.write(bowl);
						System.out.println("보내기 완료");
					}

			System.out.println("서버에서 보낸 메시지 확인 :" + msg);
				} catch (Exception e) {
				}
			}
		}).start();
	}

	public void send(String msg, Socket withServer2) {
		this.withServer2 = withServer2;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// if (list != null) {
					System.out.println("여기는 클라이언트챗이다오바 : " + msg);
					sendMsg = withServer2.getOutputStream();
					sendMsg.write(msg.getBytes());
					System.out.println("리스트 달라는 메세지 보내기 완료");
					// }
				} catch (Exception e) {
				}
			}
		}).start();
	}

}
