import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.spec.DESKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Find_Control_Pc implements ActionListener{
	private JFrame jf=new JFrame("Find_Ctrol_Pc");
	private JPanel jp=new JPanel();
	private JTextField jt =new JTextField();
	private JButton jb=new JButton("Found");
	private JTextField jt2=new JTextField();
	private String desk20[]=new String[12];
	private String desk21[]=new String[9];
	private String desk22[]=new String[7];
	private String desk47[]=new String[13];
	private String desk49[]=new String[8];
	private String desk51[]=new String[9];
	public Find_Control_Pc() {
		jt.setPreferredSize(new Dimension(140, 40));
		Font f=new Font("default",Font.PLAIN,20);
		jt.setFont(f);
		jp.add(jt);
		jb.setPreferredSize(new Dimension(100, 40));
		jb.setFont(f);
		jp.add(jb);
		jt2.setPreferredSize(new Dimension(140, 40));
		jt2.setFont(f);
		jt2.setEditable(false);
		jp.add(jt2);
		jb.addActionListener(this);
		jf.add(jp);
		jf.setVisible(true);
		jf.setBounds(160, 160, 550, 120);
		jf.setResizable(false);	
		for(int i=0,j=72;i<12;i++) {
			desk20[i]="BEJSRTL0"+j;
			j++;
		}
		for(int i=0,j=84;i<9;i++) {
			desk21[i]="BEJSRTL0"+j;
			j++;
		}
		for(int i=0,j=93;i<7;i++) {
			desk22[i]="BEJSRTL0"+j;
			j++;
		}
		for(int i=0,j=60;i<13;i++) {
			desk47[i]="BEJSRTL0"+j;
			j++;
		}
		for(int i=0,j=52;i<8;i++) {
			desk49[i]="BEJSRTL0"+j;
			j++;
		}
		for(int i=0,j=41;i<9;i++) {
			desk51[i]="BEJSRTL0"+j;
			j++;
		}
	}
	public static void main(String[] args) {
		new Find_Control_Pc();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int flag=0;
		if(e.getSource()==jb) {
			for(int i=0;i<12;i++) {
				String compare=desk20[i];
				if (jt.getText().compareToIgnoreCase(compare)==0)
				{
					jt2.setText("kxzeng-desk20");
					break;
				}
				else
				{
					//int result=JOptionPane.showConfirmDialog(null, "需要将rack信息进行保存吗？","保存rack",JOptionPane.YES_NO_OPTION);
					for(int o=0;o<9;o++) {
						String compare5=desk21[o];
						if (jt.getText().compareToIgnoreCase(compare5)==0)
						{
							jt2.setText("kxzeng-desk21");
							break;
						}
						else 
						{
							for(int k=0;k<7;k++) {
								String compare1=desk22[k];
								if (jt.getText().compareToIgnoreCase(compare1)==0)
								{
									jt2.setText("kxzeng-desk22");
									break;
								}
								else 
								{
									for(int l=0;l<13;l++) {
										String compare2=desk47[l];
										if (jt.getText().compareToIgnoreCase(compare2)==0)
										{
											jt2.setText("kxzeng-desk47");
											break;
										}
										else 
										{
											for(int m=0;m<8;m++) {
												String compare3=desk49[m];
												if (jt.getText().compareToIgnoreCase(compare3)==0)
												{
													jt2.setText("kxzeng-desk49");
													break;
												}
												else 
												{
													for(int n=0;n<9;n++) {
														String compare4=desk51[n];
														if (jt.getText().compareToIgnoreCase(compare4)==0)
														{
															jt2.setText("kxzeng-desk51");
															break;
														}
														else 
														{
															jt2.setText("No this Rack");
														}
													}
												}
											}
										}
									}
							}
						}
				}
			}
				
		}
		
	}
		}
	}
}
