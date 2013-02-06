import javax.vecmath.*;

import java.util.*;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RGTIDN1 implements KeyListener{
	public static List<Point4f> vList = new LinkedList<Point4f>();
	public static List<Point4f> tmplist = new LinkedList<Point4f>();
	public static List<Point4f> tmplist1 = new LinkedList<Point4f>();
    public static List<Point3i> trianglesList = new LinkedList<Point3i>();
	public float rx, ry, rz, ty, tz, tx;
	public float scx=1, scy=1, scz=1, persp = 4;
	
    public static void main(String[] args) {
        //read file to List
        try{
            FileReader fin = new FileReader("inputfile.txt");
            Scanner src = new Scanner(fin);
            
            String str;
            float f1 = 0, f2 = 0, f3=0;
            int i1 = 0, i2 = 0, i3=0;
            while (src.hasNext()) {
                str = src.next();                
                if (str.equals("v")){
                    if(src.hasNext()){
                        f1=Float.parseFloat(src.next()); f2=Float.parseFloat(src.next()); f3=Float.parseFloat(src.next());
                    }
                    vList.add(new Point4f(f1,f2,f3,1));
                    tmplist.add(new Point4f(f1,f2,f3,1));
                    tmplist1.add(new Point4f(f1,f2,f3,1));
                }
                else if(str.equals("f")){
                    if(src.hasNext()){
                        i1=Integer.parseInt(src.next()); i2=Integer.parseInt(src.next()); i3=Integer.parseInt(src.next());
                    }
                    trianglesList.add(new Point3i(i1,i2,i3));
                    
                }
            }
            fin.close();
        }catch(IOException e){
            System.out.println(e);
        };   
        MainApp.start(new RGTIDN1());
    }
	protected boolean over;
	protected long delay = 50;
	protected int width = 600, height = 600;
	protected String title = "RGTI DN1 Tomaz Tomazic";
	
	// getters
	public boolean isOver() {
		return over;
	}
	
	public long getDelay() {
		return delay;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getTitle() {
		return title;
	}
	
    public void init() {
    }

    
    public void update() {

    	tmplist1 = new LinkedList<Point4f>();
    	
    	for(int i=0; i<vList.size(); i++){
    		Point4f v = new Point4f(tmplist.get(i).x-vList.get(i).x,tmplist.get(i).y-vList.get(i).y,tmplist.get(i).z-vList.get(i).z,1);
			
    		scale(scx, scy, scz).transform(vList.get(i));
    		rotateX(rx).transform(vList.get(i)); 
			rotateY(ry).transform(vList.get(i));
			rotateZ(rz).transform(vList.get(i));
			
			Point4f v1 = new Point4f(vList.get(i));
    		
			translate(tx+v.x, ty+v.y, tz+v.z).transform(v1);
    		
			tmplist.get(i).set(v1);
    		tmplist1.add(v1);
    	}
    	rx=0; ry=0; rz=0; tx=0; ty=0; tz=0; scx=1; scy=1; scz=1;
    }
    
    public void cameraon(){

    	for(int i=0; i<tmplist1.size(); i++){

    		rotateZ((float)Math.PI).transform(tmplist1.get(i));
    		translate(0, 0, -8 ).transform(tmplist1.get(i));
    	}
    	
    }
    public void perspectivaon(){
    	for(int i=0; i<tmplist1.size(); i++){
    		perspective(persp).transform(tmplist1.get(i));
    		tmplist1.get(i).project(tmplist1.get(i));
    	}
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        int scala=200;
        Color[] c = {Color.RED, Color.WHITE, Color.BLUE, Color.GREEN};

        if(tmplist.size()==0){
        	return;
        }
        for(int i=0; i<trianglesList.size(); i++){
        	try{
		    	g.setColor(c[i%4]);
		        g.drawLine(width/2+(int)(scala*tmplist1.get(trianglesList.get(i).x-1).x),  height/2+(int)(scala*tmplist1.get(trianglesList.get(i).x-1).y), width/2+(int)(scala*tmplist1.get(trianglesList.get(i).y-1).x), height/2+(int)(scala*tmplist1.get(trianglesList.get(i).y-1).y));
		        g.drawLine(width/2+(int)(scala*tmplist1.get(trianglesList.get(i).y-1).x), height/2+(int)(scala*tmplist1.get(trianglesList.get(i).y-1).y), width/2+(int)(scala*tmplist1.get(trianglesList.get(i).z-1).x),  height/2+(int)(scala*tmplist1.get(trianglesList.get(i).z-1).y));
		        g.drawLine(width/2+(int)(scala*tmplist1.get(trianglesList.get(i).z-1).x), height/2+(int)(scala*tmplist1.get(trianglesList.get(i).z-1).y), width/2+(int)(scala*tmplist1.get(trianglesList.get(i).x-1).x),  height/2+(int)(scala*tmplist1.get(trianglesList.get(i).x-1).y));
        	}
        	catch (Exception e) {
			}
        }
        
    }
    
    @Override
	public void keyPressed(KeyEvent e) {		
        int keyCode = e.getKeyCode();
        switch( keyCode ) { 
            case KeyEvent.VK_Q:
            	rx=(float)0.05;
                break;
            case KeyEvent.VK_W:
            	ry=(float)0.05;
                break;
            case KeyEvent.VK_E:
            	rz=(float)0.05;
                break;
            case KeyEvent.VK_A:
            	rx=(float)-0.05;
                break;
            case KeyEvent.VK_S:
            	ry=(float)-0.05;
                break;
            case KeyEvent.VK_D:
            	rz=(float)-0.05;
                break;
            case KeyEvent.VK_UP:
            	ty=(float)-0.05;
                break;
            case KeyEvent.VK_DOWN:
            	ty=(float)0.05;
                break;    
            case KeyEvent.VK_RIGHT:
            	tx=(float)0.05;
                break;
            case KeyEvent.VK_LEFT:
            	tx=(float)-0.05;
                break;
            case KeyEvent.VK_U:
            	for(int i=0; i<vList.size(); i++){
            		perspective(persp).transform(vList.get(i));
            	}
            	break;
            case KeyEvent.VK_J:
            	for(int i=0; i<vList.size(); i++){
            		perspective(persp*(-1)).transform(vList.get(i));
            	}
            	break;
            case KeyEvent.VK_P:
            	scx=(float)0.5;
            	scy=(float)0.5;
            	scz=(float)0.5;
                break;
            case KeyEvent.VK_O:
            	scx=(float)1.5;
            	scy=(float)1.5;
            	scz=(float)1.5;
                break;
        }
    }

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	Matrix4f rotateX(float alpha){
		Matrix4f m = new Matrix4f(
				1,0,0,0,
				0,(float)Math.cos(alpha),(float)Math.sin(alpha)*(-1),0,
				0,(float)Math.sin(alpha),(float)Math.cos(alpha),0,
				0,0,0,1
		);
		return m;
	}
	Matrix4f rotateY(float alpha){
		Matrix4f m = new Matrix4f(
				(float)Math.cos(alpha),0,(float)Math.sin(alpha),0,
				0,1,0,0,
				(float)Math.sin(alpha)*(-1),0,(float)Math.cos(alpha),0,
				0,0,0,1
		);
		return m;
		
	}
	Matrix4f rotateZ(float alpha){
		Matrix4f m = new Matrix4f(
				(float)Math.cos(alpha),(float)Math.sin(alpha)*(-1),0,0,
				(float)Math.sin(alpha),(float)Math.cos(alpha),0,0,
				0,0,1,0,
				0,0,0,1
		);
		return m;
	}
	Matrix4f translate(float dx, float dy, float dz){
		Matrix4f m = new Matrix4f(
				1,0,0,dx,
				0,1,0,dy,
				0,0,1,dz,
				0,0,0,1
		);
		return m;
		
	}
	Matrix4f scale(float sx, float sy, float sz){
		Matrix4f m = new Matrix4f(
				sx,0,0,0,
				0,sy,0,0,
				0,0,sz,0,
				0,0,0,1
		);
		return m;
		
	}
	Matrix4f perspective(float d){
		Matrix4f m = new Matrix4f(
				1,0,0,0,
				0,1,0,0,
				0,0,1,0,
				0,0,1/d,0
		);
		return m;
		
	}
}
