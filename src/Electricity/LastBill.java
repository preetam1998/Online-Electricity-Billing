package Electricity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class LastBill extends JFrame implements ActionListener{
    JLabel l1;
    JTextArea t1;
    JButton b1;
    Choice c1;
    JPanel p1;
    LastBill(String meter){
        setSize(500,900);
        setLayout(new BorderLayout());
        
        p1 = new JPanel();
        
        l1 = new JLabel("Generate Bill");
        
        c1 = new Choice();
        // add meter no. to choice box
        try
        {        
            Conn c = new Conn();
            String q  = null;
                  
            q = "select * from login";
            ResultSet rs2 = c.s.executeQuery(q);
            String meter_no =  null;
            while(rs2.next())
            {
                meter_no = rs2.getString("meter_no");
                c1.add(meter_no); 
            }
                    
        }
        catch(SQLException aaa){}
        

        t1 = new JTextArea(50,15);
        JScrollPane jsp = new JScrollPane(t1);
        t1.setFont(new Font("Senserif",Font.ITALIC,18));
        
        b1 = new JButton("Generate Bill");
        
        p1.add(l1);
        p1.add(c1);
        add(p1,"North");
        
        add(jsp,"Center");
        add(b1,"South");
        
        b1.addActionListener(this);
        
        setLocation(350,40);
    }
    public void actionPerformed(ActionEvent ae){
        try{
            Conn c = new Conn();

            ResultSet rs = c.s.executeQuery("select * from customer where meter_no ="+c1.getSelectedItem());
            
            if(rs.next()){
                t1.append("\n    Customer Name:"+rs.getString("name"));
                t1.append("\n    Meter Number:  "+rs.getString("meter_no"));
                t1.append("\n    Address:            "+rs.getString("address"));
                t1.append("\n    State:                 "+rs.getString("state"));
                t1.append("\n    City:                   "+rs.getString("city"));
                t1.append("\n    Email:                "+rs.getString("email"));
                t1.append("\n    Phone Number  "+rs.getString("mobile"));
                t1.append("\n-------------------------------------------------------------");
                t1.append("\n");
            }

            t1.append("Details of the Last Bills\n\n\n");
            
            rs = c.s.executeQuery("select * from bill where meter_no ="+c1.getSelectedItem());
            
            while(rs.next()){
                t1.append("       "+ rs.getString("month") + "           " +rs.getString("total_bill") + "\n");
            }
            
            
            
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new LastBill("").setVisible(true);
    }
}

