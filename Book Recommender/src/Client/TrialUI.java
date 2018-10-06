package Client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.TextField;

import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TrialUI extends javax.swing.JFrame {

    /**
     * Creates new form TrialUI
     */
    Integer userId;
    String recommendations[][];
    boolean verify;
    public TrialUI() {
    	setBounds(new Rectangle(100, 100, 0, 0));
    	setMinimumSize(new Dimension(1218, 722));
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\pc\\Desktop\\books.jpg"));
    	setTitle("Recommender");
    	
    	setFont(new Font("Algerian", Font.PLAIN, 12));
    	setSize(911,537);
    	setLocationRelativeTo(null);
    	getContentPane().setBackground(new Color(255, 192, 203));
    	setBackground(new Color(0, 0, 0));
        initComponents();
    }
  
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jDialog1 = new javax.swing.JDialog();
        jTextField1 = new javax.swing.JTextField();
        jTextField1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		jTextField1.setText("");
        	}
        });
        jTextField1.setHorizontalAlignment(SwingConstants.CENTER);
        jTextField1.setBounds(162, 159, 253, 44);
        jTextField1.setBackground(Color.WHITE);
        jTextField1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        jTextField1.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        Login = new javax.swing.JButton();
        Login.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 16));
        Login.setSelected(true);
        Login.setOpaque(false);
        Login.setBounds(162, 342, 253, 48);
        Login.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        Login.setIcon(new ImageIcon("C:\\Users\\Owner\\Documents\\NetBeansProjects\\GUI\\images\\lo.png"));
        Reset = new javax.swing.JButton();
        Reset.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 16));
        Reset.setBackground(UIManager.getColor("Button.shadow"));
        Reset.setBounds(162, 418, 253, 43);
        Reset.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jTextField1.setText("Username");
        
       
        Login.setText("LOGIN");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);        
            }
        });

        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        
        passwordField = new JPasswordField();
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setBounds(162, 229, 253, 44);
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(Color.GRAY);
        passwordField.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));
        passwordField.setText("abc12345");
        passwordField.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        
        txtPleaseLoginTo = new JTextField();
        txtPleaseLoginTo.setOpaque(false);
        txtPleaseLoginTo.setBounds(162, 122, 221, 24);
        txtPleaseLoginTo.setForeground(Color.BLACK);
        txtPleaseLoginTo.setEditable(false);
        txtPleaseLoginTo.setBorder(null);
        txtPleaseLoginTo.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtPleaseLoginTo.setText("Please login to access books");
        txtPleaseLoginTo.setColumns(10);
        
        label_1 = new JLabel("");
        label_1.setMinimumSize(new Dimension(666, 375));
        label_1.setMaximumSize(new Dimension(1200, 1080));
        label_1.setBounds(0, 0, 1200, 675);
        label_1.setIcon(new ImageIcon("E:\\java pro\\Book Recommender\\images\\ui1.jpg"));
        getContentPane().setLayout(null);
        getContentPane().add(txtPleaseLoginTo);
        getContentPane().add(jTextField1);
        getContentPane().add(Login);
        getContentPane().add(passwordField);
        getContentPane().add(Reset);
        
        JLabel Passwordicon = new JLabel("");
        Passwordicon.setIcon(new ImageIcon("E:\\java pro\\Book Recommender\\images\\password.png"));
        Passwordicon.setBounds(114, 235, 39, 38);
        getContentPane().add(Passwordicon);
        
        JLabel Usericon = new JLabel("");
        Usericon.setIcon(new ImageIcon("E:\\java pro\\Book Recommender\\images\\user.png"));
        Usericon.setBounds(114, 159, 55, 44);
        getContentPane().add(Usericon);
        getContentPane().add(label_1);

       pack();
    }// </editor-fold>                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        jTextField1.setText("Username");
        passwordField.setText("Password");
    }                                        

                     
    
//Login button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
         String pass="12345";
         String password=passwordField.getText();
         Client client = null;
         TrialUI2 t = null;
         userId=Integer.parseInt(jTextField1.getText());
         System.out.println(userId);
        try {
            client= new Client();
            verify = client.sendUserId(userId,password);
            System.out.println("Verify : "+verify);
            System.out.println(" In Trial UI ");
            
            System.out.println("verify :  "+verify);
        } catch (Exception ex) {
            Logger.getLogger(TrialUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Loading l=new Loading();
        if(this.verify){
        	//l.setVisible(true);
        	this.setVisible(false);//To hide first window
        	
        	recommendations = client.sendRecommedations();
            t = new TrialUI2(recommendations);
          //  l.setVisible(false);
            t.setVisible(true);//to take to next window
            System.out.println("User verified");
            for (String[] strings : recommendations) {
				System.out.println("Title : "+strings[0]);
				System.out.println("Rating : "+strings[1]);
				System.out.println("imageURL : "+strings[2]);
				System.out.println();
			}
        }
        else {
        	System.out.println("Invalid credentials from page 1");
        	//this.setVisible(true);
        	Verification v=new Verification();
        	v.setVisible(true);
        	jTextField1.setText("Username");
            passwordField.setText("abc12345");
        }
    }
    
    
    public static void main(String args[]) throws IOException {
  
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TrialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrialUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrialUI().setVisible(true);
               
            }
        });
       
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton Login;
    private javax.swing.JButton Reset;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private JPasswordField passwordField;
    private JTextField txtPleaseLoginTo;
    private JLabel lblNewLabel;
    private JLabel label_1;
}
