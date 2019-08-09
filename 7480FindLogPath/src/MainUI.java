import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class MainUI implements ActionListener {
	
	private JFrame frame=new JFrame("7480 Tool");
	private JPanel panel=new JPanel();
	private JLabel label=new JLabel("              ==========================================================find build log path==========================================================              ");
	private JLabel label1=new JLabel("input version name");
	private JTextArea jta1=new JTextArea(1,30);
	private JLabel label2=new JLabel("date  			range");
	private JTextArea jta2=new JTextArea(1,30);
	private JLabel label3=new JLabel("	To	");
	private JTextArea jta3=new JTextArea(1,30);
	private JLabel label4=new JLabel("	log server	");
	private JTextArea jta4=new JTextArea(1,30);
	//JScrollPane scrollpane1=new JScrollPane(jta);
	private JButton bt2=new JButton("Check");
	//private JTextArea jta1=new JTextArea(26,80);
	private JScrollPane scrollpane=new JScrollPane(jta1);
	private JButton bt3=new JButton("Clear");
	public String[] rackname = null;
	public MainUI(){
		panel.add(label);
		panel.add(label1);
		jta1.setLineWrap(true);
		jta1.setBorder(new LineBorder(Color.gray,1));
		panel.add(label2);
		panel.add(scrollpane1);
		bt2.addActionListener(this);
		panel.add(bt2);
		jta1.setEditable(false);
		jta1.setBorder(new LineBorder(Color.gray,1));
		jta1.setBackground(new Color(196,252,197));
		panel.add(scrollpane);
		bt3.addActionListener(this);
		panel.add(bt3);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
		frame.add(panel);
		frame.setBounds(400,150,950,700);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	

	public static void main(String[] args) {
		new MainUI();

	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
