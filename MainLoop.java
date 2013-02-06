public class MainLoop extends Thread {
	
	private final RGTIDN1 rGTIDN1;
	private final RgtiPanel panel;
	
	public MainLoop(RGTIDN1 rGTIDN1, RgtiPanel panel){
		this.rGTIDN1 = rGTIDN1;
		this.panel = panel;
	}
	
	@Override
	public void run(){
		rGTIDN1.init(); 
		while(!rGTIDN1.isOver()){
			rGTIDN1.update();
			rGTIDN1.cameraon();
			rGTIDN1.perspectivaon();
			panel.repaint();
			try {
				Thread.sleep(rGTIDN1.getDelay()); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	} 
}
