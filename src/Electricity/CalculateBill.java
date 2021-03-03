package Electricity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CalculateBill extends JFrame implements ActionListener{
    JLabel H, l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,pht;
    JTextField t1,t3,t2,t4;
    Choice c1,c2;
    JButton b1,b2;
    JPanel p;
    String meter;
    CalculateBill(String meter){
        
        p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        
        H = new JLabel("Status of  Electricity Bill");
        H.setBounds(30, 10, 400, 30);
        
        //first Row..........
        l1 = new JLabel("Meter No");
        l1.setBounds(60, 70, 100, 30);
        
        l2 = new JLabel(meter);
        l2.setBounds(200, 70, 180, 20);
        
        //second Row...........
        l3 = new JLabel("Name");
        l3.setBounds(60, 110, 100, 30);
        
        l4 = new JLabel();
        l4.setBounds(200, 110, 180, 20);
//        p.add(l4);
        
        //third   Row...........
        l5 = new JLabel("Address");
        l5.setBounds(60, 150, 100, 30);
        
        l6 = new JLabel();
        l6.setBounds(200, 150, 180, 20);
//        p.add(l6);
        
        //fourth row....
        l7 = new JLabel("Month ");
        l7.setBounds(60, 200, 100, 30);
        
        c2 = new Choice();
        c2.setBounds(200, 200, 180, 20);
         c2.add("Month");
        c2.add("January");
        c2.add("February");
        c2.add("March");
        c2.add("April");
        c2.add("May");
        c2.add("June");
        c2.add("July");
        c2.add("August");
        c2.add("September");
        c2.add("October");
        c2.add("November");
        c2.add("December");
        
        //fifth row ..............
        l8 = new JLabel("Units Cosumed");
        l8.setBounds(35, 240, 100, 30);
        
        t1 = new JTextField();
        t1.setBounds(32, 270, 108, 30);
        
       //fifth row ..............
        
        l9 = new JLabel("Amount");
        l9.setBounds(240, 240, 100, 30);
        
        t2 = new JTextField();
        t2.setBounds(225, 270, 110, 30);
        
        //fifth row ..............
        l10 = new JLabel("Status ");
        l10.setBounds(420, 240, 100, 30);
        
        t3 = new JTextField();
        t3.setBounds(400, 270, 110, 30);
        
        //sisxth row ..............
        l11 = new JLabel("Total Unit Consume => ");
        l11.setBounds(35, 320, 200, 30);
        l11.setFont(new Font("Senserif",Font.PLAIN,20));
        
        t4 = new JTextField();
        t4.setBounds(245, 320, 110, 30);
       
        // get Name and address for given meter_no.............
        try
        {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+ meter +"'");
            while(rs.next())
            {
                l4.setText(rs.getString("name"));
                l6.setText(rs.getString("address"));
                
            }
        }catch(Exception e){}
        
        // get list bill remaining for  given meter and its status
        c2.addItemListener(new ItemListener(){
           public void itemStateChanged(ItemEvent ae){
               try
                {
                    Conn c = new Conn();
                    int i = 1;
                    ResultSet rs = c.s.executeQuery("select * from bill where month = '"+ c2.getSelectedItem() +"' and meter_no = '"+ meter +"'");
                    if(rs.next())
                    {
         
                       
                        t1.setText(rs.getString("units"));
                        t2.setText(rs.getString("total_bill"));
                        t3.setText(rs.getString("status"));
                        
                    }
                    else{
                        t1.setText("0.00");
                        t2.setText("00.00");
                        t3.setText("");
                    }
                    int sum = 0;
                ResultSet rs1 = c.s.executeQuery("select * from bill where status = '" + "unpaid" + "' and meter_no = '"+ meter +"'");
            
                while(rs1.next())
                {
                    sum += Integer.parseInt(rs1.getString("units"));
                }
            
                String s = Integer.toString(sum);
                t3.setText("unpaid");
                t4.setText(s);
                    
                }catch(Exception e){}
               
          }});
        
       
        
         
        
        b1 = new JButton("Create BILL ");
        b1.setBounds(100, 400, 130, 25);
        b2 = new JButton("Cancel");
        b2.setBounds(250, 400, 100, 25);
        
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);

        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        
        
        // Adding image
        pht = new JLabel("");
        pht.setBounds(30, 10, 400, 30);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(180, 270,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        pht = new JLabel(i3);
        
        
        
        H.setFont(new Font("Senserif",Font.PLAIN,26));
        //Move the label to center
        H.setHorizontalAlignment(JLabel.CENTER);
        
        p.add(H);
        
        p.add(l1);
        p.add(l2);
        p.add(l3);
        p.add(l4);
        p.add(l5);
        p.add(l6);
        p.add(l7);
        p.add(l8);
        p.add(l9);
        p.add(l10);
        p.add(l11);
        
        p.add(t1);
        p.add(t2);  
        p.add(t3);
        p.add(t4);
        
        p.add(c2);
        
        p.add(b1);
        p.add(b2);
        
        setLayout(new BorderLayout(30,30));
        
        
        add(p,"Center");
        add(pht,"West");
        
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);        
        setSize(750,500);
        setLocation(550,220);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
            String meter = l2.getText();
            String units = t4.getText();
            //String month = c2.getSelectedItem();

            int units_consumed = Integer.parseInt(units);

            int total_bill = 0;
            try{
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("select * from rent");
                while(rs.next()){
                    total_bill = units_consumed * Integer.parseInt(rs.getString("cost_per_unit")); // 120 * 7
                    total_bill += Integer.parseInt(rs.getString("meter_rent"));
                    total_bill += Integer.parseInt(rs.getString("service_charge"));
                    total_bill += Integer.parseInt(rs.getString("service_tax"));
                    total_bill += Integer.parseInt(rs.getString("swacch_bharat_cess"));
                    total_bill += Integer.parseInt(rs.getString("fixed_tax"));
                }
            }catch(Exception e){}

            String q = "update  bill set total_bill = '"+ total_bill+"' where meter_no = '" + meter + "'";

            try{
                Conn c1 = new Conn();
                c1.s.executeUpdate(q);
                JOptionPane.showMessageDialog(null,"Customer Bill Updated Successfully");
                this.setVisible(false);
            }catch(Exception aee){
                aee.printStackTrace();
            }

        }else if(ae.getSource()== b2){
            this.setVisible(false);
        }        
    }
    
       
    public static void main(String[] args){
        new CalculateBill("168423").setVisible(true);
    }
}
