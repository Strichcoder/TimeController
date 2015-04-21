import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TimeController extends JPanel implements Runnable{

	
	public static int xMax = 640;
	public static int yMax = 480;
	public static Menu menu;
	public static Player player; 
	public static Level level;
	public static int lvl=0;
	public static int lvl_max=9;
	
	private InputHandler inHandler;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setSize(xMax+16, yMax+38);

		// // make a new JPanel (this TimeController class) and adds it to the frame
		 final TimeController mainPanel = new TimeController(); // constructor
		 frame.add(mainPanel);
		frame.setVisible(true);
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// constructor
	TimeController()
	{
		//player = new Player();
		inHandler = new InputHandler();
		addKeyListener(inHandler);
		
		new Thread(this).start();
	}

	// gets the focus from the frame to the panel // necessary for the input to work and nobody knows why :)
		public void addNotify()
		{
			super.addNotify();
			requestFocus();
		}
	
	public void run()
	{
		try{
		menu=new Menu();
		}catch(Exception e){
			System.out.println("Failed making Menu");
			e.printStackTrace();
		}

		while (true) {
			if(menu==null){ //run levels
				if(level.won) // next level
				{
					if(lvl<lvl_max){
						lvl++;
			
						try {
							level = new Level(lvl);
						} catch (Exception e) {
							System.out.println("Failed reading level");
							e.printStackTrace();
						}
						player.setStartPos(level.startx,level.starty);
					}else{ // finished the last lvl
						try{
							menu=new Menu();
						}catch(Exception e){
							System.out.println("Failed making Menu");
							e.printStackTrace();
						}
					}
					
					// saves
					if(lvl>Menu.getMaxlvl()){
						File loc= new File("saves.txt");
						try{
							FileWriter file=new FileWriter(loc);
							BufferedWriter writer = new BufferedWriter(file);
						
							writer.write(""+lvl);
						
							writer.close();
						}catch (FileNotFoundException e){
							System.out.println("File not found:"+loc.toString());
						}catch (IOException e){
							System.out.println("IOException while saving");
						}
					}
					
					
				}
								
			}else{ // run menu
				
			}
			update();
			repaint(); 
			try {
					Thread.sleep((long) 20.0); // ~50fps
				} catch (InterruptedException e) {
					System.out.println("Couldn't start sleeping:" + e);
					e.printStackTrace();
				}
		}
		
	}
	
	public void update(){
		if(menu==null){
			player.update(inHandler);
			int i=0;
			if(inHandler.isSpaceDown()){
				i=player.xpos.length-1;
			}
			if(player.xpos[i]<0 || player.ypos[i]<0 || player.xpos[i]>xMax || player.ypos[i]>yMax || level.collide((int)player.xpos[i]/16,(int)player.ypos[i]/16)){
				player.setStartPos(level.startx,level.starty);
			}
		}else{ // update menu
			if(menu.update(inHandler)){
				if(menu.getSelect()==10){
					System.exit(0);
				}
				lvl=menu.getSelect();
				try{
				    level = new Level(lvl);
			    } catch (Exception e) {
				    System.out.println("Failed reading level");
				    e.printStackTrace();
			    }
				player = new Player();
				player.setStartPos(level.startx,level.starty);
				menu=null;
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if(menu==null){ // draw level
			
		
			g2.setColor(Color.white); // cls
			g2.fillRect(0, 0, xMax, yMax);
		
			level.draw(g2);
			player.draw(g2,inHandler);
		}else{ // draw menu
			g2.setColor(Color.white); // cls
			g2.fillRect(0, 0, xMax, yMax);
			menu.draw(g2);
		}
	}
	

}
