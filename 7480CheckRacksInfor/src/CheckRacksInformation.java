import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
 * 
 * 20160519----add fresh immediately and check AT comamnd and create csv
 * 20160520----debug, and add column "cla state"
 * 20160526----add new class to get some value from Result csv
 * 20160606----creater class for every part
 * 20160612----add key"reset""crash""currenttime"
 * @author xiaoqi4x
 *
 **/
public class CheckRacksInformation implements ActionListener {

	private JFrame frame=new JFrame("7480 Tool");
	private JPanel panel=new JPanel();
	private JLabel label=new JLabel("              ========================================Check racks run_type,build version,log path========================================              ");
	private JButton bt1=new JButton("Import racks names");
	private JTextArea jta=new JTextArea(4,50);
	private JScrollPane scrollpane1=new JScrollPane(jta);
	private JButton bt2=new JButton("Check");
	private JTextArea jta1=new JTextArea(26,80);
	private JScrollPane scrollpane=new JScrollPane(jta1);
	private JButton bt3=new JButton("Clear");
	public String[] rackname = null;
	/**
	 * 构造函数，添加监听
	 * 
	 **/
	public CheckRacksInformation(){
		panel.add(label);
		bt1.addActionListener(this);
		panel.add(bt1);
		jta.setLineWrap(true);
		jta.setBorder(new LineBorder(Color.gray,1));
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
	/**
	 * 主函数入口
	 * 
	 **/
	public static void main(String[] args) {
		new CheckRacksInformation();
	}
	/**
	 * 重写监听方法
	 * 
	 **/
	@Override
	public void actionPerformed(ActionEvent e) {
		String s=null;
		if (e.getSource()==bt1){
			jta.setText("");
			File file=new File("C:\\Tools\\748_racks.txt");
			try {
				/**
				 * 将FileReader传递给BufferedReader，采用BufferedReader的readLine()方法和read()方法来读取文件内容
				 * 
				 **/
				FileReader fr=new FileReader(file);
				BufferedReader br=new BufferedReader(fr);
				while((s=br.readLine())!=null){
				jta.append(s+",");	
				}
				br.close();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(frame,"C:\\Tools\\748_racks.txt is not exist,please check it!!");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(frame, "please check the content of C:\\Tools\\748_racks.txt");
			}
		}else if (e.getSource()==bt2){
			GUIForTool gui=new GUIForTool();
			/**
			 * 定义时间格式
			 * 
			 **/
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd_HH_mm");
			String dateinfor=sdf.format(date);
			File csvfile=new File("C:\\Tools");
			File csvfile2=null;
			FileWriter fw=null;
			BufferedWriter bw=null;
			rackname = null;
			rackname=jta.getText().replace("\n",",").split(",");
			Socket socket=null;
			try {
				/**
				 * 创建CSV文件记录结果
				 * 
				 * */
				csvfile2=File.createTempFile("748_rack_information"+dateinfor,".csv",csvfile);
				fw=new FileWriter(csvfile2.getPath());
				bw=new BufferedWriter(fw);
				bw.write("\"DEVICE_NAME\""+","+"\"ITERATIONS\""+","+"\"START_TIME\""+","+"\"CURRENT_TIME\""+","+"\"TOTAL_TIME\""+","+"\"RESETS\""+","+"\"CRASHES\""+","+"\"RUN_TYPE\""+","+"\"BB_BUILD\""+","+"\"LOG_PATH\""+","+"\"Script_Name\""+","+"\"PARAM_OPEN\""+","+"\"PARAM_WRITE\""+","+"\"UMTS_MODE\""+","+"\"COUNT_WRONG_SlEEP_AT\""+","+"\"CLA_STATE\""+","+" ");
				bw.newLine();
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(frame, "csv cannot create successfully!!!");
			}	
			//************************************************************************************************
			for(int i=0;i<rackname.length;i++){
				try {
					bw.write(rackname[i]);
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				socket=new Socket();
				SocketAddress add=new InetSocketAddress(rackname[i],3389);
				try {
					socket.connect(add,3000);
					
				} catch (IOException e1) {
					jta1.append("===ERROR===>>> "+rackname[i]+"   cannot connect successfully,please check rackname or connection!!!"+"\r\n"+"\r\n");
					gui.fresh(jta1);
					try {
						bw.write(","+" ");
						bw.newLine();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					continue;
				}
				String []filterFromCamResult=new String[4];
				String []filterFromClaResult=new String[5];
		 		String clastate="***";
		 		String []result=new String[6];
		 		File file=new File("\\\\"+rackname[i]+"\\log\\");
				String []files1=file.list();
				Double max=0.0;
				int maxi=0;
				try{
					for(int k=0;k<files1.length;k++){
						if(files1[k].indexOf("ST_7480")==0){
							if(max<Double.parseDouble(files1[k].substring(8))){
								max=Double.parseDouble(files1[k].substring(8));
								maxi=k;
							}
						}
					}
				}catch(NullPointerException e1){
					jta1.append("===ERROR===>>> "+rackname[i]+"   maybe cannot open the log folder!!!"+"\r\n"+"\r\n");
					gui.fresh(jta1);
					try {
						bw.write(","+" ");
						bw.newLine();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					continue;
				}
				if(maxi==0){
					jta1.append("===ERROR===>>> "+rackname[i]+"   log folder is empty!!!"+"\r\n"+"\r\n");
					gui.fresh(jta1);
					try {
						bw.write(","+" ");
						bw.newLine();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					continue;
					}
				File file1=new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]);
				if(!(new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+"campaign_end.txt").exists())){
					jta1.append("============>>>  "+rackname[i]+"\r\n");
					gui.fresh(jta1);
					clastate="run";
				}else {
					jta1.append("===ERROR===>>> "+rackname[i]+"      CLA stopped!!!"+"\r\n");
					clastate="stop";
					gui.fresh(jta1);
				}
					File files2=new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+"campaign_start.txt");
				/**
				 * get runtype,version,log path from campaign_start
				 */
				FilterInforFromCampaign fifca=new FilterInforFromCampaign();
				filterFromCamResult=fifca.readDataFromCampagin(files2, jta1, rackname[i], files1[maxi], bw);
				if(filterFromCamResult[3].equals("false")){
					continue;
				}
				String []files3=file1.list();
				String testcase=null;
				for(int i1=0;i1<files3.length;i1++){
					if((new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+files3[i1]).isDirectory())&&(files3[i1].indexOf("ST_748")!=-1)){
						if ((new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+files3[i1]+"\\ST_7480_2.0.txt").exists())){
							testcase="ST_7480_2.0.txt";
							FilterInforFromCLALog fifcla=new FilterInforFromCLALog();
							filterFromClaResult=fifcla.readDataFromCLA(rackname[i], files1[maxi], files3[i1], testcase, jta1, bw);
						}else if((new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+files3[i1]+"\\ST_7480_1.0.txt").exists())){
							testcase="ST_7480_1.0.txt";
							FilterInforFromCLALog fifcla=new FilterInforFromCLALog();
							filterFromClaResult=fifcla.readDataFromCLA(rackname[i], files1[maxi], files3[i1], testcase, jta1, bw);
						}else {
							jta1.append("===ERROR===>>> "+"\\\\"+rackname+"\\log\\"+files1[maxi]+"\\"+files3[i1]+"      no"+testcase+"\r\n"+"\r\n");
							gui.fresh(jta1);
							try {
								bw.write(","+" ");
								bw.newLine();
							} catch (IOException e2) {
								e2.printStackTrace();
							}
							continue;
						}
						FilterInforFromCSV fifc=new FilterInforFromCSV();
						String result1=fifc.readDataFromCSV(rackname[i], bw, new File("\\\\"+rackname[i]+"\\log\\"+files1[maxi]+"\\"+files3[i1]), jta1);
						if(result1!=null){
							result=result1.split(",");
						}
						else continue;
					}
				}
				try {
					bw.write(","+result[0]+","+result[1]+","+result[2]+","+","+result[4]+","+result[5]+","+filterFromCamResult[0]+","+filterFromCamResult[1]+","+filterFromCamResult[2]+","+result[3]+","+filterFromClaResult[0]+","+filterFromClaResult[1]+","+filterFromClaResult[2]+","+filterFromClaResult[3]+","+clastate+","+" ");
					bw.newLine();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			JOptionPane.showMessageDialog(frame, "log in the  C:\\Tools");
			try {
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if (e.getSource()==bt3){
			rackname = null;
			jta.setText("");
			jta1.setText("");
		}
	}
}
