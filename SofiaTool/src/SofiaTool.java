import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/*this application used to count modem crashes/resets/critical issues/total issues
we can export this java to jar
right click-> export->jar->finish
if u want open jar via double click, u need add a "Main-Class: package.javaname" in the MANIFEST.MF
open MANIFEST.MF via WinRAR*/
public class SofiaTool implements MouseListener {
	private JFrame frame=new JFrame("count Modem Crashes/Resets/Critical issues/Total issues");
	private JPanel panel=new JPanel();
	//private JScrollPane scrollpanel=new JScrollPane();
	//create label for four directory
	private JLabel mc=new JLabel("Total          MPanics",SwingConstants.CENTER);
	private JLabel crash=new JLabel("Total          MResets",SwingConstants.CENTER);
	private JLabel reboot=new JLabel("Forced        Reboots",SwingConstants.CENTER);
	private JLabel ci=new JLabel("Other Critical Issues",SwingConstants.CENTER);
	private JLabel ti=new JLabel("Total               Issues",SwingConstants.CENTER);
	//create directory textfield
	private JTextField mctext=new JTextField();
	private JTextField crashtext=new JTextField();
	private JTextField reboottext=new JTextField();
	private JTextField citext=new JTextField();
	private JTextField titext=new JTextField();
	//create directory button
	private JButton mcbutton=new JButton("...");
	private JButton crashbutton=new JButton("...");
	private JButton rebootbutton=new JButton("...");
	private JButton cibutton=new JButton("...");
	private JButton tibutton=new JButton("...");
	//create count button
	private JButton mcbuttonc=new JButton("=");
	private JButton crashbuttonc=new JButton("=");
	private JButton rebootbuttonc=new JButton("=");
	private JButton cibuttonc=new JButton("=");
	private JButton tibuttonc=new JButton("=");
	private JButton clear=new JButton("Clear All");
	//create result Label
	private JTextField mcresult=new JTextField("");
	private JTextField crashresult=new JTextField("");
	private JTextField rebootresult=new JTextField("");
	private JTextField ciresult=new JTextField("");
	private JTextField tiresult=new JTextField("");
	//create log infor
	private JTextArea log=new JTextArea(20,74);
	private JScrollPane scrollpanel=new JScrollPane(log);
	
	
	public SofiaTool(){
		
		//add Modem crashesmc
		panel.add(mc);
		mctext.setColumns(50);
		panel.add(mctext);
		mcbutton.addMouseListener(this);
		panel.add(mcbutton);
		mcbuttonc.addMouseListener(this);
		panel.add(mcbuttonc);
		mcresult.setColumns(5);
		mcresult.setEditable(false);
		panel.add(mcresult);
		//add resets
		panel.add(crash);
		crashtext.setColumns(50);
		crashtext.setEditable(false);
		panel.add(crashtext);
		//crashbutton.addMouseListener(this);
		panel.add(crashbutton);
		//crashbuttonc.addMouseListener(this);
		panel.add(crashbuttonc);
		crashresult.setColumns(5);
		crashresult.setEditable(false);
		panel.add(crashresult);
		//add forced reboot
		panel.add(reboot);
		reboottext.setColumns(50);
		reboottext.setEditable(false);
		panel.add(reboottext);
		//rebootbutton.addMouseListener(this);
		panel.add(rebootbutton);
		//rebootbuttonc.addMouseListener(this);
		panel.add(rebootbuttonc);
		rebootresult.setColumns(5);
		rebootresult.setEditable(false);
		panel.add(rebootresult);
		//add cirtical issue
		panel.add(ci);
		citext.setColumns(50);
		panel.add(citext);
		cibutton.addMouseListener(this);
		panel.add(cibutton);
		cibuttonc.addMouseListener(this);
		panel.add(cibuttonc);
		ciresult.setColumns(5);
		ciresult.setEditable(false);
		panel.add(ciresult);
		//add Total issues
		panel.add(ti);
		titext.setColumns(50);
		panel.add(titext);
		tibutton.addMouseListener(this);
		panel.add(tibutton);
		tibuttonc.addMouseListener(this);
		panel.add(tibuttonc);
		tiresult.setColumns(5);
		tiresult.setEditable(false);
		panel.add(tiresult);
		clear.addMouseListener(this);
		panel.add(scrollpanel);
		panel.add(clear);
		frame.add(panel);
		frame.setBounds(500,200,850,600);
		//frame.getContentPane().setBackground(Color.CYAN);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	

	public static void main(String[] args) {
		new SofiaTool();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==mcbutton||e.getSource()==crashbutton||e.getSource()==tibutton||e.getSource()==cibutton){
		JFileChooser fileChooser = new JFileChooser("D:\\");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			while(e.getSource()==mcbutton||e.getSource()==crashbutton||e.getSource()==tibutton||e.getSource()==cibutton){
			if(e.getSource()==mcbutton){ 
			    mctext.setText(fileChooser.getSelectedFile().getAbsolutePath());	
			    break;
			}else if(e.getSource()==crashbutton){
			    crashtext.setText(fileChooser.getSelectedFile().getAbsolutePath());
			    break;
		    }else if(e.getSource()==tibutton){
			    titext.setText(fileChooser.getSelectedFile().getAbsolutePath());
			    break;
			}else if(e.getSource()==cibutton){
			    citext.setText(fileChooser.getSelectedFile().getAbsolutePath());
			    break;
			}
			}
		}
		}
		/*note :ctrl+shift+/ ctrl+shift+\
		count modem crash
		count Resets
		count ciritcal issue
		count total issue */
		if(e.getSource()==mcbuttonc){
			int count=0;
			String s=null;
			File file=new File(mctext.getText()+"\\PhoneDoctor_Logs\\history_event");
			try {
				FileReader fr=new FileReader(file);
				BufferedReader br=new BufferedReader(fr);
				try {
					log.append("========================MPanics========================"+"\r\n");
					log.append(mctext.getText()+"\\PhoneDoctor_Logs\\history_event"+"\r\n");
					while((s=br.readLine())!=null){
						if(s.indexOf("MPANIC /data/logs/crashlog")!=-1){
							count=count+1;
							log.append(s+"\r\n");
						}
					}
					br.close();
					mcresult.setText(String.valueOf(count));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				//e1.printStackTrace();
				log.append("========================MPanics========================"+"\r\n");
				log.append(mctext.getText()+"\\PhoneDoctor_Logs\\history_event"+"\r\n");
				log.append("***ERROR:can not find history_event"+"\r\n");
				mcresult.setText("ERROR");
			}			
		}else if(e.getSource()==crashbuttonc){;
			int dcount=0;
			File file=new File(crashtext.getText()+"\\Application_Logs\\Campaign");
			File []files=file.listFiles();
			log.append("========================MRESETS========================"+"\r\n");
			log.append(crashtext.getText()+"\\Application_Logs\\Campaign"+"\r\n");
			for(int i=0;i<files.length;i++){
				if(files[i].isDirectory()){
					log.append(files[i].getAbsolutePath()+"\r\n");
					dcount=dcount+1;
				}
			}
			crashresult.setText(String.valueOf(dcount));
		}else if(e.getSource()==cibuttonc){
			int mcount=0;
			String s=null;
			File mfile=new File(mctext.getText()+"\\PhoneDoctor_Logs\\history_event");
			try {
				FileReader mfr=new FileReader(mfile);
				BufferedReader mbr=new BufferedReader(mfr);
				try {
					log.append("========================MPanics========================"+"\r\n");
					log.append(mctext.getText()+"\\PhoneDoctor_Logs\\history_event"+"\r\n");
					while((s=mbr.readLine())!=null){
						if(s.indexOf("MPANIC /data/logs/crashlog")!=-1){
							mcount=mcount+1;
							log.append(s+"\r\n");
						}
					}
					mbr.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (FileNotFoundException e1) {
				//e1.printStackTrace();
				log.append("========================MPanics========================"+"\r\n");
				log.append(mctext.getText()+"\\PhoneDoctor_Logs\\history_event"+"\r\n");
				log.append("***ERROR:can not find history_event"+"\r\n");
				mcresult.setText("ERROR");
			}
			if(!mcresult.getText().equals("ERROR")){
			int count=0;
			File file=new File(citext.getText()+"\\PhoneDoctor_Logs");
			String []files1=file.list();
			log.append("========================Other CRITICAL ISSUES========================"+"\r\n");
			log.append(crashtext.getText()+"\\PhoneDoctor_Logs"+"\r\n");
			for(int j=0;j<files1.length;j++){
				if(files1[j].indexOf("crashlog")!=-1){
					String s1=null;
					File file2=new File(citext.getText()+"\\PhoneDoctor_Logs\\"+files1[j]+"\\crashfile");
					try {
						FileReader fr1=new FileReader(file2);
						BufferedReader br1=new BufferedReader(fr1);
						try {
							while((s1=br1.readLine())!=null){
								if(s1.equalsIgnoreCase("CRITICAL=YES")){
									count=count+1;
									log.append(citext.getText()+"\\PhoneDoctor_Logs\\"+files1[j]+"\r\n");
								}
							}
							br1.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						//log.append("********************"+citext.getText()+"\\PhoneDoctor_Logs\\"+files1[j]+"can not find crashfile********************"+"\r\n");
					}
				}
			}
			ciresult.setText(String.valueOf(count-mcount));
			}
			
		}else if(e.getSource()==tibuttonc){
			int count=0;
			File file=new File(titext.getText()+"\\PhoneDoctor_Logs");
			log.append("========================TOTAL ISSUES========================"+"\r\n");
		    log.append(titext.getText()+"\\PhoneDoctor_Logs"+"\r\n");
			String []files=file.list();
			for(int i=0;i<files.length;i++){
				if(files[i].indexOf("crashlog")==0){
					log.append(files[i]+"\r\n");
					count=count+1;
				}
			}
			tiresult.setText(String.valueOf(count));
		}else if(e.getSource()==clear){
			mctext.setText(null);
			crashtext.setText(null);
			citext.setText(null);
			titext.setText(null);
			mcresult.setText(null);
			crashresult.setText(null);
			ciresult.setText(null);
			tiresult.setText(null);
			log.setText(null);
		}
	}




	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
