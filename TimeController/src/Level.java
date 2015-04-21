import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Image;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;



/*
 * The levels are stored in a 40x30 .txt file
 * 
 * 0 = free space
 * 1 = wall
 * 2 = start position
 * 3 = target
 * 
 * 
 */
public class Level {

	public int[][] level= new int[40][30];
	public int startx=16;
	public int starty=16;
	
	private Image wall;
	private Image target;
	private File loc;
	
	public boolean won=false;
	
	// constructor
	public Level(int nr) throws Exception
	{
		
		loc= new File("lvl"+nr+".txt");
		
		try{
		FileReader file=new FileReader(loc);
		BufferedReader reader = new BufferedReader(file);
		for(int i=0;i<level[0].length;i++){ // 30
			for(int j=0;j<level.length;j++){ // 40
				level[j][i]=reader.read()-48;
				if(level[j][i]==2)
				{
					startx=j*16;
					starty=i*16;
				}
			}
			reader.read();reader.read();
		}
		reader.close();
		}catch (FileNotFoundException e){
		    System.out.println("File not found:"+loc.toString());
		}
		
		won=false;
		
		wall = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("data/img/wall.png"));
		while(wall.getWidth(null)<0){} // TODO more appropriate way of waiting till it's loaded.
		target = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("data/img/target.png"));
		while(target.getWidth(null)<0){} // TODO more appropriate way of waiting till it's loaded.
		
	}
	
	public boolean collide(int x, int y)
	{
		if(level[x][y]==1 || level[x+1][y]==1 || level[x][y+1]==1 || level[x+1][y+1]==1){
			return true;
		}
		else if(level[x][y]==3 || level[x+1][y]==3 || level[x][y+1]==3 || level[x+1][y+1]==3){
			won=true;
		}
		return false;
	}
	public void draw(Graphics2D g)
	{
		for(int i=0;i<level[0].length;i++){
			for(int j=0;j<level.length;j++){
				if(level[j][i]==1){
					g.drawImage(wall,j*16,i*16,null); // TODO unnice hardcoded
				}else if(level[j][i]==3){
					g.drawImage(target,j*16,i*16,null);
				}
			}
		}
		
	}
	
}
