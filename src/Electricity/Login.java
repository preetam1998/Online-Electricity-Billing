package Electricity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{
    JLabel l1,l2,l3, l4,l5;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1,b2, b3;
    JPanel p1,p2,p3,p4;
    Choice c1, c2;
    static String selected_string ;
    
    
    Login(){
        super("Login Page");
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        
        l1 = new JLabel("Username");
        l1.setBounds(300, 20, 100, 20);
        add(l1);
        l2 = new JLabel("Password");
        l2.setBounds(300, 60, 100, 20);
        add(l2);
        
        tf1 = new JTextField(15);
        tf1.setBounds(400, 20, 150, 20);
        add(tf1);
        pf2 = new JPasswordField(15);
        pf2.setBounds(400, 60, 150, 20);
        add(pf2);
        
        l4 = new JLabel("Logging in as");
        l4.setBounds(300, 100, 100, 20);
        add(l4);
        
        c1 = new Choice();
        c1.add("Select");
        c1.add("Admin");
        c1.add("Customer");
        c1.setBounds(400, 100, 150, 20);
        add(c1);
        
        
        l5 = new JLabel("select name ");
        l5.setBounds(300, 140, 100, 20);
        add(l5);
        
        c2 = new Choice();
        c2.setBounds(400, 140, 150, 20);
        add(c2);
        l5.setVisible(false);
        c2.setVisible(false);
        // Add  Item Listener for c1 chouice
        c1.addItemListener(new ItemListener(){
           public void itemStateChanged(ItemEvent ae){
               String ss = c1.getSelectedItem();
               if(ss.equals("Admin"))
               {
                   l5.setVisible(true);
                   c2.setVisible(true);
                   
                    try{        
                        Conn c = new Conn();
                        String a  = tf1.getText();
                        String b  = pf2.getText();
                        String user = c1.getSelectedItem();
                        String q  = null, q1 = null;
                   
                        try
                        {
                            q  = "select * from admin where username = '"+a+"' and password = '"+b+"' and user = '"+user+"'";
                            ResultSet rs1 = c.s.executeQuery(q);
                    
                            if(!rs1.next())
                            
                                    JOptionPane.showMessageDialog(null, "Invalid login for Admin"); 
                
                            q1  = "select name from login";
                            ResultSet rs2 = c.s.executeQuery(q1);
                            String meter =  null;
                            while(rs2.next())
                            {
                                String name = rs2.getString("name");
                                c2.add(name); 
                            }
                    
                        }catch(SQLException aaa){}
                    }catch(Exception aaaaaa){}
               
               }
               else
               {
                   l5.setVisible(false);
                   c2.setVisible(false);
               }
           } 
        });
        
        
        ImageIcon ic1 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i1 = ic1.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT);
        b1 = new JButton("Login", new ImageIcon(i1));
        b1.setBounds(330, 200, 100, 20);
        add(b1);
        
        ImageIcon ic2 = new ImageIcon(ClassLoader.getSystemResource("icon/cancel.jpg"));
        Image i2 = ic2.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT);
        b2 = new JButton("Cancel",new ImageIcon(i2));
        b2.setBounds(450, 200, 100, 20);
        add(b2);
        
        ImageIcon ic4 = new ImageIcon(ClassLoader.getSystemResource("icon/signup.png"));
        Image i4 = ic4.getImage().getScaledInstance(16, 16,Image.SCALE_DEFAULT);
        b3 = new JButton("Signup",new ImageIcon(i4));
        b3.setBounds(380, 230, 130, 20);
        add(b3);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);


        ImageIcon ic3 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image i3 = ic3.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT);
        ImageIcon icc3 = new ImageIcon(i3);
        l3 = new JLabel(icc3);
        l3.setBounds(0, 0, 250, 250);
        add(l3);
        
        setLayout(new BorderLayout());
    
     
        setSize(640,300);
        setLocation(600,300);
        setVisible(true);
        

        
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
            try{        
                Conn c = new Conn();
                String a  = tf1.getText();
                String b  = pf2.getText();
                String user = c1.getSelectedItem();
                String q  = null;
                if(user != "Admin")
                {
                    q  = "select * from login where username = '"+a+"' and password = '"+b+"' and user = '"+user+"'";
                    ResultSet rs = c.s.executeQuery(q);
                    if(rs.next()){
                        String meter = rs.getString("meter_no");
                        new Project(meter, user).setVisible(true);
                        this.setVisible(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid user......");
                        //tf1.setText("");
                        // pf2.setText("");
                    }
                }
                // selected item is admin.................................
                else
                {
                    q  = "select meter_no from login where name = '"+c2.getSelectedItem()+"'";
                    ResultSet rs = c.s.executeQuery(q);
                    if(rs.next()){
                        String meter = rs.getString("meter_no");
                        new Project(meter, user).setVisible(true);
                        this.setVisible(false);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid Admin.......");
                        //tf1.setText("");
                        // pf2.setText("");
                    }
                }
                    
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("error: "+e);
            }
        }else if(ae.getSource() == b2){
            this.setVisible(false);
        }else if(ae.getSource() == b3){
            this.setVisible(false);
            new Signup().setVisible(true);
            
        }
    }
    
    public static void main(String[] args){
        new Login().setVisible(true);
    }

    
}



