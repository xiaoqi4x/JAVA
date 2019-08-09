import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * 7360_tool_201605181440 --add check vender line:111,don't create .jar
 * 7360_tool_201605181609 --fresh the new information immediately
 * 7360_tool_201605251113 --debug code and add note
 * @author xiaoqi4x
 *
 */
public class CheckRacksInformation implements ActionListener {
	private JFrame frame=new JFrame("7360 Tool");
	/**
	 * row1
	 */
	private JPanel panel=new JPanel();
	private JLabel label=new JLabel("              ========================================Check racks run_type,build version,log path========================================              ");
	/**
	 * row2
	 */
	private JButton bt1=new JButton("Import racks names");
	private JTextArea jta=new JTextArea(4,50);
	private JScrollPane scrollpane1=new JScrollPane(jta);
	private JButton bt2=new JButton("Check");
	/**
	 * row3
	 */
	private JTextArea jta1=new JTextArea(26,80);
	private JScrollPane scrollpane=new JScrollPane(jta1);
	/**
	 * row4
	 */
	private JButton bt3=new JButton("Clear");
	/**
	 * rackname: kxzeng-desk65  racklog:\\kxzeng-desk64\log
	 */
	public String[] rackname = null;
	/**
	 * define the construction function
	 */
	public CheckRacksInformation(){
		/**
		 * row1
		 */
		panel.add(label);
		/**
		 * row2
		 */
		bt1.addActionListener(this);
		panel.add(bt1);
		jta.setLineWrap(true);
		jta.setBorder(new LineBorder(Color.gray,1));
		panel.add(scrollpane1);
		bt2.addActionListener(this);
		panel.add(bt2);
		/**
		 * row3
		 */
		jta1.setEditable(false);
		jta1.setBorder(new LineBorder(Color.gray,1));
		jta1.setBackground(new Color(196,252,197));
		panel.add(scrollpane);
		/**
		 * row4
		 */
		bt3.addActionListener(this);
		panel.add(bt3);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
		frame.add(panel);
		frame.setBounds(400,150,950,700);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	/**
	 * main function
	 * @param args
	 */
	public static void main(String[] args) {
		new CheckRacksInformation();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s=null;
		/**
		 * import racks name to textarea(jta)
		 */
		if (e.getSource()==bt1){
			jta.setText("");
			File file=new File("C:\\Tools\\736_racks.txt");
			try {
				FileReader fr=new FileReader(file);
				BufferedReader br=new BufferedReader(fr);
				while((s=br.readLine())!=null){
					jta.append(s+",");	
				}
				br.close();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(frame,"C:\\Tools\\736_racks.txt is not exist,please check it!!");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(frame, "please check the content of C:\\Tools\\736_racks.txt");
			}
		}
		else if (e.getSource()==bt2){
			        /**
			         * define name for csv
			         */
					Date date=new Date();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd_HH_mm");
					String dateinfor=sdf.format(date);
					File csvfile=new File("C:\\Tools");
					File csvfile2=null;
					FileWriter fw=null;
					BufferedWriter bw=null;
					try {
						csvfile2=File.createTempFile("736_rack_information"+dateinfor,".csv",csvfile);
						fw=new FileWriter(csvfile2.getPath());
						bw=new BufferedWriter(fw);
						bw.write("\"DEVICE_NAME\""+","+"\"ITERATIONS\""+","+"\"START_TIME\""+","+"\"GAP_TIME\""+","+"\"TOTAL_TIME\""+","+"\"RUN_TYPE\""+","+"\"BB_BUILD\""+","+"\"LOG_PATH\""+","+"\"VENDER\""+","+"\"ScriptName\"");
						bw.newLine();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame, "csv cannot create successfully!!!");
					}
			 		rackname = null;
					rackname=jta.getText().replace("\n",",").split(",");
					Socket socket=null;
					for(int i=0;i<rackname.length;i++){
						try {
							bw.write(rackname[i]);
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						/**
						 * check net connection
						 */
						socket=new Socket();
						SocketAddress add=new InetSocketAddress(rackname[i],3389);
						try {
							socket.connect(add,3000);
							
						} catch (IOException e1) {
							jta1.append("===ERROR===>>> "+rackname[i]+"   cannot connect successfully,please check rackname or connection!!!"+"\r\n"+"\r\n");
							fresh(jta1);
							try {
								bw.write(","+" ");
								bw.newLine();
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							continue;
						}
						String runtype="***";
				 		String version="***";
				 		String logpath="***";
				 		String starttime="***";
				 		String vender="***";
						File file=new File("\\\\"+rackname[i]+"\\log\\");
						String []files1=file.list();
						Double max=0.0;
						/**
						 * record the newest folder id in files1
						 */
						int maxi=0;
						/**
						 * find the newest folder
						 */
						try{
							for(int k=0;k<files1.length;k++){
								File filemid=new File("\\\\"+rackname[i]+"\\log\\"+files1[k]);
								if(filemid.isDirectory()&&(files1[k].split("_").length==3)){
									if(max<Double.parseDouble(files1[k].split("_")[2])){
										max=Double.parseDouble(files1[k].split("_")[2]);
										maxi=k;
									}
								}
							}
						}catch(NullPointerException e1){
							jta1.append("===ERROR===>>> "+rackname[i]+"   maybe cannot find the log folder!!!"+"\r\n"+"\r\n");
							fresh(jta1);
							try {
								bw.write(","+" ");
								bw.newLine();
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							continue;
						}
						if(maxi==0){
							jta1.append("===ERROR===>>> "+rackname[i]+"   log folder is empty!!!"+"\r\n"+"\r\n");
							fresh(jta1);
							try {
								bw.write(","+" ");
								bw.newLine();
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							continue;
							}
						/**
						 * find the CLa log folder
						 */
							if(!(new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+"campaign_end.txt").exists())){
								File file2=new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+"campaign_start.txt");
								try {
									FileReader fr1=new FileReader(file2);
									BufferedReader br1=new BufferedReader(fr1);
									while((s=br1.readLine())!=null){
										if(s.indexOf("run_type is")!=-1){
											runtype=s.split(" ")[5];
										    continue; 
										}else if(s.indexOf("CLA Campaign Initialization Completed")!=-1){
											starttime=s.split(",")[0];
											continue; 
										}else if(s.indexOf("*XG736ES21S5E20")!=-1){
											version=s.substring(39).replace("*\""," ");
											continue; 
										}else if(s.indexOf("for push_log")!=-1){
											logpath=s.split(" ")[3];
											continue; 
										}else if(s.indexOf("FAB-CODE")!=-1){
											vender=s.split(" ")[1];
											continue;
										}	
									}
									bw.write(","+" "+","+","+","+","+runtype+","+version+","+logpath+","+vender+","+" ");
									bw.newLine();
									jta1.append("============>>>  "+rackname[i]+"    ST:"+" "+starttime+"\r\n");
									fresh(jta1);
									jta1.append("run                    type:    "+runtype+"\r\n");
									fresh(jta1);
									jta1.append("build           version:    "+version+"\r\n");
									fresh(jta1);
									jta1.append("log                    path:    "+logpath+"\r\n"+"\r\n");
									fresh(jta1);
									br1.close();
								} catch (FileNotFoundException e1) {
									jta1.append("===ERROR===>>> "+"\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"   no  campaign_start.txt"+"\r\n"+"\r\n");
									fresh(jta1);
									try {
										bw.write(","+" ");
										bw.newLine();
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									continue;
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}else {
								File file3=new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+"campaign_start.txt");
								try {
									FileReader fr2=new FileReader(file3);
									BufferedReader br2=new BufferedReader(fr2);
									while((s=br2.readLine())!=null){
										if(s.indexOf("run_type is")!=-1){
											runtype=s.split(" ")[5];
										    continue; 
										}else if(s.indexOf("CLA Campaign Initialization Completed")!=-1){
											starttime=s.split(",")[0];
											continue; 
										}else if(s.indexOf("*XG736ES21S5E20")!=-1){
											version=s.substring(39).replace("*\""," ");
											continue; 
										}else if(s.indexOf("for push_log")!=-1){
											logpath=s.split(" ")[3];
											continue; 
										}else if(s.indexOf("FAB-CODE")!=-1){
											vender=s.split(" ")[1];
											continue;
										}		
									}
									bw.write(","+" "+","+","+","+","+runtype+","+version+","+logpath+","+vender+","+" ");
									bw.newLine();
									jta1.append("===ERROR===>>> "+rackname[i]+"      CLA stopped!!!"+"    ST:"+" "+starttime+"\r\n");
									fresh(jta1);
									jta1.append("run                    type:    "+runtype+"\r\n");
									fresh(jta1);
									jta1.append("build           version:    "+version+"\r\n");
									fresh(jta1);
									jta1.append("log                    path:    "+logpath+"\r\n"+"\r\n");
									fresh(jta1);
									br2.close();
								} catch (FileNotFoundException e1) {
									jta1.append("===ERROR===>>> "+"\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"   no  campaign_start.txt"+"\r\n"+"\r\n");
									fresh(jta1);
									try {
										bw.write(","+" ");
										bw.newLine();
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									continue;
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								continue;
							}	
					}
					JOptionPane.showMessageDialog(frame, "log in the  C:\\Tools");
					try {
						bw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		}else if (e.getSource()==bt3){
			rackname = null;
			jta.setText("");
			jta1.setText("");
		}
	}
	public void fresh(JTextArea jta){
		jta.paintImmediately(jta.getX(), jta.getY(), jta.getWidth(), jta.getHeight());	
		return;
	}

}
