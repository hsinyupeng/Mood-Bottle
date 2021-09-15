import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CreateMember extends JFrame{

private JFrame createFrame;
private JButton create;
private JButton cancel;
private JTextField enterID;
private JTextField enterpassword;
private JTextField blank;
private String  FileLoginname; 
private BufferedImage image;
CreateMember(){	
	LoadLoginFile();
	SetCreateTable();
}


public void openCreateFrame() {
	createFrame.setVisible(true);
}
private void SetCreateTable() {
	createFrame=new JFrame();
	create= new JButton("Create");
	cancel= new JButton("Cancel");
	enterID=new JTextField("id");
	enterpassword=new JTextField("password");
	blank=new JTextField("");
	JScrollPane scrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
	
	create.setBounds(100, 300, 100, 30);
	cancel.setBounds(274, 300, 100, 30);
	enterID.setBounds(162, 100, 150, 30);
	enterpassword.setBounds(162, 175, 150, 30);
	blank.setBounds(0, 0, 1, 1);
	enterID.addFocusListener(new FocusListener() {	 
    	 String remindertxtString=enterID.getText();
                	 

		@Override
		public void focusGained(FocusEvent e) {
			String tempString = enterID.getText();
 			
 			if (tempString.equals(remindertxtString)){
 				enterID.setText("");
 				enterID.setForeground(Color.BLACK);
 			}
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			String tempString = enterID.getText();
 			if(tempString.equals("")) {
 				enterID.setForeground(Color.GRAY);
 				enterID.setText(remindertxtString);
 			}
			
		}});
	enterpassword.addFocusListener(new FocusListener() {
    	 
    	 String remindertxtString=enterpassword.getText();
                	 

		@Override
		public void focusGained(FocusEvent e) {
			String tempString = enterpassword.getText();
 			
 			if (tempString.equals(remindertxtString)){
 				enterpassword.setText("");
 				enterpassword.setForeground(Color.BLACK);
 			}
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			String tempString = enterpassword.getText();
 			if(tempString.equals("")) {
 				enterpassword.setForeground(Color.GRAY);
 				enterpassword.setText(remindertxtString);
 			}
			
		}});
	
	createFrame.add(create);
	createFrame.add(cancel);
	createFrame.add(enterID);
	createFrame.add(enterpassword);
	createFrame.add(blank);
	createFrame.setResizable(false);
	class ButtonListener implements ActionListener{   		
 		public Member member=new Member();
		public void actionPerformed(ActionEvent event)
 		{   String ID=enterID.getText();
 		    String password=enterpassword.getText();
 		    String command=event.getActionCommand();
			
 		    if(command.equals("Create")) {	    	
 		    		try {
						member.verify(ID, password);
					} 
 		    		catch (SQLException e) {						
						e.printStackTrace();
					}
					createFrame.setVisible(false);
					enterID.setText("id");
					enterpassword.setText("password");		    	
 			
 		    }
 			else {													
 					createFrame.setVisible(false);														
 						}	
 		}
	}	
     ActionListener listener = new ButtonListener();
     create.addActionListener(listener);	
     cancel.addActionListener(listener);
     createFrame.getContentPane().add(scrollPane);
     createFrame.pack();
     createFrame.setTitle("Create");
	 createFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
	 createFrame.setLocationRelativeTo(null);
	 createFrame.setVisible(false);
}
private void LoadLoginFile()
{
        FileLoginname="/create.png";//設定檔名
        try
        {
        	java.net.URL imgURL = CreateMember.class.getResource(FileLoginname);
        	image = ImageIO.read(imgURL);//讀取檔案
        }
        catch(Exception e)
        {
                javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: "+ FileLoginname);
                image=null;//如果錯誤的話顯示錯誤訊息
        }
}
}
