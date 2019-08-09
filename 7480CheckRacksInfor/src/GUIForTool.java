import javax.swing.JTextArea;

public class GUIForTool {
	
	public void fresh(JTextArea jta){
		jta.paintImmediately(jta.getX(), jta.getY(), jta.getWidth(), jta.getHeight());	
		return;
	}

}
