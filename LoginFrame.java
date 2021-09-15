
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import java.io.*;

import java.sql.SQLException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LoginFrame extends CreateMember {
 private String  FileLoginname; 
 private BufferedImage image ;
 
 private JFrame loginframe;
 private JButton button=new JButton("Login");
 private JButton create=new JButton("Create");
 private JTextField text1=new JTextField("USER ID");
 private JTextField text2=new JTextField("Password");
 private JTextField text0=new JTextField("");
 
 public LoginFrame() {
	 LoadLoginFile();
	 SetLoginTable();
	
 }
 
 public JFrame getLoginframe() {
	return loginframe;
}

private void LoadLoginFile(){
         FileLoginname="/login.png";//設定檔名
         try
         {
        	 java.net.URL imgURL = LoginFrame.class.getResource(FileLoginname);
        	 image = ImageIO.read(imgURL);//讀取檔案
         }
         catch(Exception e)
         {
                 javax.swing.JOptionPane.showMessageDialog(null, "載入圖檔錯誤: "+ FileLoginname);
                 image=null;//如果錯誤的話顯示錯誤訊息
         }
}
         public void SetLoginTable()//設定版面
         {
                 loginframe= new JFrame("");
                 JLabel scrollPane = new JLabel(new ImageIcon(image));//把Image放進label裡
                 text0.setBounds(0, 0, 1, 1);
                 text1.setBounds(115, 230, 200, 20);
                 text2.setBounds(115, 280, 200, 20);
                 button.setBounds(135, 320, 150, 20);
                 create.setBounds(160, 350, 100, 20);
                 text1.addFocusListener(new FocusListener() {               	 
                	 String remindertxtString=text1.getText();
					@Override
					public void focusGained(FocusEvent e) {
						String tempString = text1.getText();            			
             			if (tempString.equals(remindertxtString)){
             				text1.setText("");
             				text1.setForeground(Color.BLACK);
             			}						
					}
					@Override
					public void focusLost(FocusEvent e) {
						String tempString = text1.getText();
             			if(tempString.equals("")) {
             				text1.setForeground(Color.GRAY);
             				text1.setText(remindertxtString);
             			}						
					}});
                  text2.addFocusListener(new FocusListener() {
                	 
                	 String remindertxtString=text2.getText();
                            	 

					@Override
					public void focusGained(FocusEvent e) {
						String tempString = text2.getText();
             			
             			if (tempString.equals(remindertxtString)){
             				text2.setText("");
             				text2.setForeground(Color.BLACK);
             			}
						
					}

					@Override
					public void focusLost(FocusEvent e) {
						String tempString = text2.getText();
             			if(tempString.equals("")) {
             				text2.setForeground(Color.GRAY);
             				text2.setText(remindertxtString);
             			}
						
					}});
        
                 loginframe.add(button);
                 loginframe.add(text0);
                 loginframe.add(text1);
                 loginframe.add(text2);
                 loginframe.add(create);
                 
                 class ButtonListener implements ActionListener{   
                	public Member member=new Member();
             		public void actionPerformed(ActionEvent event)             		
             		{   
             			String ID=text1.getText();
             		    String password=text2.getText();
             		    String command=event.getActionCommand();
             		    
					
             		    if(command.equals("Login")) {
             		    	
             		    		try {
									if(member.login(ID, password)=="1") {
										loginframe.setVisible(false);
										new MainFrame(ID);
									}
								} catch (SQLException e) {									
									e.printStackTrace();
								}	
                     			}
            		    	           		    	           		    
             		    else if(command.equals("Create")) {            		    	
             		    	new CreateMember();
             		    	openCreateFrame();
             		    }
            		
             		}
             		}
                 ActionListener listener = new ButtonListener();
                 button.addActionListener(listener);
                 create.addActionListener(listener);
                 loginframe.add(scrollPane);
                 loginframe.pack();
                 loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 loginframe.setTitle("Login");
                 loginframe.setLocationRelativeTo(null);
                 loginframe.setVisible(true);
                 loginframe.setResizable(false);

         }
}
