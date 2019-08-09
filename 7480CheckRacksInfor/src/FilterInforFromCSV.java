import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTextArea;

public class FilterInforFromCSV {


	/**
	 * define function to read data from csv
	 */
	public String readDataFromCSV(String rackname,BufferedWriter bw,File file1,JTextArea jta1){
		/**
		 * file:the csv which we create the new one
		 * ====> C:\Tools
		 * file1:the csv which the cla generate 
		 * ====> \\kxzeng-desk64\log\ST_7480_20160525171548\ST_7480_1.0_250516171908\results_BJ_7480_64_sim1_201605251800.csv
		 * Alt+/  shortcutkey for complete word
		 * Shift +Ctrl+/ note
		 * shift +Ctrl+\ cancel note
		 */
		FileReader fr1=null;
		FileReader fr2=null;
		BufferedReader br1=null;
		BufferedReader br2=null;
		String brString1=null;
		String brString2=null;
		String []resultInfor=null;
		String []fileName=null;
		String result=null;
		int totalRow=0;
		int totalRowFlag=0;
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		GUIForTool gui=new GUIForTool();
		try {
			fileName=file1.list();
			for(int i=0;i<fileName.length;i++){
				if(fileName[i].indexOf("results_BJ_7480")!=-1){
					fr1=new FileReader(new File(file1.getPath()+"\\"+fileName[i]));
					fr2=new FileReader(new File(file1.getPath()+"\\"+fileName[i]));
					br1=new BufferedReader(fr1);
					br2=new BufferedReader(fr2);
					while((brString1=br1.readLine())!=null){
						totalRow=totalRow+1;
						continue;
					}
					br1.close();
					while((brString2=br2.readLine())!=null){
						totalRowFlag=totalRowFlag+1;
						if(totalRowFlag==totalRow){
							resultInfor=brString2.split(",");
							break;
						}
					}
					br2.close();
				}
				else {
					continue;
				}
			}
			if(resultInfor!=null){
				result=resultInfor[8]+","+resultInfor[2]+","+resultInfor[3]+","+resultInfor[36]+","+resultInfor[9]+","+resultInfor[39];
			}else{
				jta1.append("===ERROR===>>> "+rackname+"   still no Result csv now!!!"+"\r\n"+"\r\n");
				gui.fresh(jta1);
			}

		} catch (FileNotFoundException e) {
			jta1.append("===ERROR===>>> "+rackname+"   still no Result csv now!!!"+"\r\n"+"\r\n");
			gui.fresh(jta1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
