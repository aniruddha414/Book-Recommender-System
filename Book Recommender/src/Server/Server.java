package Server;

import java.sql.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Server {
	
	Connection con = null;
	PreparedStatement pst = null;
	ServerSocket ss = null;
	int userId;
	boolean verify;
	
	public static void main(String[] args) {
		Server s = new Server();
		s.connectToDatabase();
		s.validate(); // verify user
	}
	public Server() {
		try {
			ss = new ServerSocket(4444);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void connectToDatabase() {
		
		String username ="root";
		String password ="password";
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/recommender",username,password);
			System.out.println("Connection Established");
			Statement smt = null;
			smt = con.createStatement();
			String createTableBooks = "CREATE TABLE IF NOT EXISTS books(bookID INT NOT NULL,title VARCHAR(100),authors VARCHAR(300),ratings DECIMAL(3,2),imageURL VARCHAR(500),PRIMARY KEY(bookID))";
			smt.execute(createTableBooks);
			String createTableUsers = "CREATE TABLE IF NOT EXISTS users(userID INT NOT NULL,frequency INT,auth_key VARCHAR(20) NOT NULL,PRIMARY KEY(userID))";
			smt.execute(createTableUsers);
			
			System.out.println("Tables Created");
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public Socket connectToCLient() { // opens socket for communication
		
		Socket s = null;
		try {
			System.out.println("Server is Starting");
			s = ss.accept();
			System.out.println("Server ready to accept connection");
			return s;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return s;
	}

	public int[] getRecommendation(int userID) {
		int bookids[] = new int[5];
		// Run Script and get Recommendations
		
		System.out.println("User ID : "+userID);
		try{
			RConnection  rcon = new RConnection();
			System.out.println("R Connection Established");
			//  set path
			rcon.eval("source('E:\\\\Book Recommender Script R\\\\recommender.R')");
			
			System.out.println("File opened successfully");
			
			bookids = rcon.eval("getRecommendations("+userID+")").asIntegers();
			for (int i : bookids) {
				System.out.println("Book id : "+i);
			}
			return bookids;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// set path to script file 
		
		return bookids;
	}

	public String[][] arrangeRecommendations(int userID) {
		// after getting recommendations by running R script and than send to client
		int bookids[] = new int[5];
		// get bookids 
		bookids = getRecommendation(userID);
		
		// after getting bookids fetch from database
		ResultSet res = null;
		String searchBook = " SELECT bookID,title,authors,ratings,imageURL FROM recommender.books WHERE bookID = ?"; 
		
		
		String recommedations[][] = new String[5][3];
		
		
		// Title , authors , Image URL
		for (int i = 0; i < 5 ; i++) {
			
			try {
				pst=con.prepareStatement(searchBook);
				pst.setInt(1, bookids[i]);
				res = pst.executeQuery();
				while(res.next()) {
					String title = res.getString("title");
					float ratings = res.getFloat("ratings");
					String imageURL = res.getString("imageURL");
					
					// get title , ratings , imageURL
					recommedations[i][0] = title;
					recommedations[i][1] = Float.toString(ratings);
					recommedations[i][2] = imageURL;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return recommedations;
	}
	public boolean verifyUser(int user,String auth) { // verify userid and password
		ResultSet res = null;
		try {
			int userid=0;
			String auth_key=null;
			String checkUser = "SELECT userID,auth_key FROM recommender.users WHERE userID = ? ";
			pst = con.prepareStatement(checkUser);
			pst.setInt(1, user);
			res = pst.executeQuery();
			if(res.isBeforeFirst())
			{
				while(res.next()) {
					userid = res.getInt(1);
					auth_key = res.getString(2);
				}
				if(auth_key.equals(auth)) {
					return true;
				}
				else {
					System.out.println("Invalid password");
					return false;
				}
			}
			if(! res.isBeforeFirst())
			{
				System.out.println("Invalid Userid");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public void validate() {
		Socket s = connectToCLient();
		try {
			DataInputStream din = new DataInputStream(s.getInputStream());
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			userId = din.readInt();
			String auth = (String)din.readUTF();
			System.out.println("user id recieved from client : "+userId);
			System.out.println("Password from client : "+auth);
			if(verifyUser(userId, auth)) {
				System.out.println("User Authenticated");
				dout.writeInt(1);
				this.verify = true;
				sendRecommendationsToClient(s);
				s.close();
			}
			else {
				System.out.println("Invalid Users");
				dout.writeInt(0);
				//call validate again
				connectToDatabase();
				validate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void sendRecommendationsToClient(Socket s) {
		if(verify) {
			try {
				// send recommendations 
				String recommendations[][] = new String[5][3];
				recommendations = arrangeRecommendations(userId);
				ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
				os.writeObject(recommendations);
				System.out.println("Sent to client");
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		else
			System.out.println("User not verified");
	}
}
