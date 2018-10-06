package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class Client {
	String recommendations[][];
	boolean validated;
	public Socket connectToServer() {
		Socket s=null;
		try {
			s = new Socket("localhost",4444);
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	public boolean sendUserId(int userid,String auth_key) {
		Socket s = connectToServer();
		try {
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			DataInputStream din = new DataInputStream(s.getInputStream());
			sendCredentials(dout,userid, auth_key);
		    this.validated = verifyUser(din);
		    if(this.validated) {
		    	getRecommendations(s);
		    	s.close();
		    }
		    else
		    	s.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.validated;
	}
	public boolean verifyUser(DataInputStream din) {
		boolean check = false;
		try {
			int checkCode = din.readInt();
			System.out.println("Check code : "+checkCode);
			if(checkCode == 1) {
				check = true;
			}
			else
				check = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}
	public void sendCredentials(DataOutputStream dout,int user,String auth) {
		try {
			
			dout.writeInt(user);
			dout.writeUTF(auth);
			System.out.println("User id : "+user);
			System.out.println(" Auth key : "+auth);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void getRecommendations(Socket s) {
		ObjectInputStream is;
		try {
			is = new ObjectInputStream(s.getInputStream());
			this.recommendations = (String[][])is.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String [][] sendRecommedations(){
		return recommendations;
	}
}

