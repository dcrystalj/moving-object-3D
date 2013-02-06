import java.awt.Graphics;
import javax.swing.JComponent;

public class RgtiPanel extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final RGTIDN1 rGTIDN1;
	
	public RgtiPanel(RGTIDN1 rGTIDN1){
		this.rGTIDN1 = rGTIDN1;
		addKeyListener(this.rGTIDN1);
	}
	
	public void paintComponent(Graphics g){
		rGTIDN1.draw(g);
	}
	
}
