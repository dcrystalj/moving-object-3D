import javax.swing.JFrame;

public class MainApp {
	
	public static JFrame frame;
		
	public static void start(RGTIDN1 rGTIDN1){
		frame = new JFrame(rGTIDN1.getTitle());
		frame.setSize(rGTIDN1.getWidth(), rGTIDN1.getHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RgtiPanel panel = new RgtiPanel(rGTIDN1);
		frame.add(panel);
		frame.setVisible(true);
		panel.requestFocus();
		MainLoop loop = new MainLoop(rGTIDN1, panel);
		loop.start();
	}
}

