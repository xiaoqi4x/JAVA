import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextArea;

public class FilterInforFromCLALog {

	public String[] readDataFromCLA(String rackname,String files1Maxi,String files3I1,String testcase,JTextArea jta1,BufferedWriter bw){
		FileReader fr2=null;
		BufferedReader br2=null;
		FileReader fr3=null;
		BufferedReader br3=null;
		String s1=null;
		String s2=null;
		String filterResult[]=new String[5];
		int command5=0;
		int command5flag=0;
		int sleepcount=0;
		GUIForTool gui=new GUIForTool();
			try {
				fr2=new FileReader(new File("\\\\"+rackname+"\\log\\"+files1Maxi+"\\"+files3I1+"\\"+testcase));
				fr3=new FileReader(new File("\\\\"+rackname+"\\log\\"+files1Maxi+"\\"+files3I1+"\\"+testcase));
				br2=new BufferedReader(fr2);
				br3=new BufferedReader(fr3);
				while((s2=br3.readLine())!=null){
					command5=command5+1;
					if(s2.indexOf("dyn_cps.instance[0].umts_duplexing_mode.duplexing_mode =1")!=-1){
						break;
					}
				}
				br3.close();
				while((s1=br2.readLine())!=null){
					command5flag=command5flag+1;
					if(s1.indexOf("paramOpen:handle")!=-1){
						filterResult[0]=s1;
						continue;
					}else if(s1.indexOf("paramWrite:successful")!=-1){
						filterResult[1]=s1;
						continue;
					}else if(command5==(command5flag-4)){
						filterResult[2]=s1;
						continue;
					}else if(s1.indexOf("get_sleep_stats(0)")!=-1||s1.indexOf("get_sleep_stats(1)")!=-1||s1.indexOf("get_sleep_stats(2)")!=-1){
						sleepcount=sleepcount+1;
						continue;
					}
				}
				br2.close();
			} catch (FileNotFoundException e) {
				filterResult[4]="false";
			} catch (IOException e) {
			}
			filterResult[3]=String.valueOf(sleepcount);
			filterResult[4]="true";
			return filterResult;
	}
}
