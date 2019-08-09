import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JTextArea;

public class FilterInforFromCampaign {
	
	public String[] readDataFromCampagin(File files2,JTextArea jta1,String rackname,String files1Maxi,BufferedWriter bw){
		String s=null;
		String []filterResult=new String[4];
		GUIForTool gui=new GUIForTool();
		try {
			FileReader fr1=new FileReader(files2);
			BufferedReader br1=new BufferedReader(fr1);
			while((s=br1.readLine())!=null){
				if(s.indexOf("run_type is")!=-1){
					filterResult[0]=s.substring(52).replace(" ", "");
				    continue; 
				}else if(s.indexOf("*XG748ES10S6TE1")!=-1){
					filterResult[1]=s.substring(39).replace("*\"", "");
					continue;
				}else if(s.indexOf("for push_log")!=-1){
					filterResult[2]=s.split(" ")[3];
					continue;
				}
			}
			br1.close();
			jta1.append("run                    type:    "+filterResult[0]+"\r\n");
			gui.fresh(jta1);
			jta1.append("build           version:    "+filterResult[1]+"\r\n");
			gui.fresh(jta1);
			jta1.append("log                    path:    "+filterResult[2]+"\r\n"+"\r\n");
			gui.fresh(jta1);
		} catch (FileNotFoundException e) {
			jta1.append("===ERROR===>>> "+"\\\\"+rackname+"\\log\\"+files1Maxi+"   no  campaign_start.txt"+"\r\n"+"\r\n");
			gui.fresh(jta1);
			try {
				bw.write(","+" ");
				bw.newLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			filterResult[3]="false";
			return filterResult;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		filterResult[3]="true";
		return filterResult;
	}
}

