package DB;

import java.net.Socket;

import Server.ServerChat;

public interface DAOInterface {
	
	Boolean Insert(Object DTO, String notice, Socket withClient);
	int Select(String id, String pwd,String notice, Socket withClient);
	Boolean Edit(Object DTO);
	Boolean Delete(Object DTO);
	

}

