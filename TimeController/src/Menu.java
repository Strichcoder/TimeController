import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class Menu {

	private int[][][] levels=new int[10][40][30];
	private int select;
	private Font registerFont;
	private static int maxlvl=0;
	
	public Menu() throws Exception{
		select=0;
		for(int nr=0;nr<levels.length;nr++){ // 10
			
			File loc= new File("lvl"+nr+".txt");
		
			try{
				FileReader file=new FileReader(loc);
				BufferedReader reader = new BufferedReader(file);
				for(int i=0;i<levels[0][0].length;i++){ // 30
					for(int j=0;j<levels[0].length;j++){ // 40
						levels[nr][j][i]=reader.read()-48;
					}
					reader.read();reader.read();
				}
				reader.close();
			}catch (FileNotFoundException e){
				System.out.println("File not found:"+loc.toString());
			}
			
		}
		// saves
		File loc= new File("saves.txt");
		try{
			FileReader file=new FileReader(loc);
			BufferedReader reader = new BufferedReader(file);
			maxlvl=(int)reader.read()-48;
			reader.close();
		}catch (FileNotFoundException e){
			System.out.println("File not found:"+loc.toString());
		}
		if(maxlvl<0){maxlvl=0;}
		
			registerFont = new Font("Comic Sans MS",Font.PLAIN, 12);
			
		
	}
	
	public boolean update(InputHandler inHandler){
		
		if(inHandler.isRightReleased()){
			select++;
		}
		if(inHandler.isLeftReleased()){
			select--;
		}
		select=(select+11)%11;
		
		return inHandler.isSpaceDown() && (select<=maxlvl || select==10) ;
	}
	
	public void draw(Graphics2D g2){
		
		g2.setColor(Color.darkGray); 
		g2.setFont(registerFont.deriveFont(40f));
		g2.drawString("Time Controller",180,80);
		
		g2.setFont(registerFont.deriveFont(25f));
		int drawy=128;
		int drawx;
		for(int i=0;i<2;i++){
			drawx=3*32-8;
			for(int j=0;j<5;j++){
				if(select==i*5+j){
					g2.setColor(Color.red);
					g2.drawRect(drawx-4,drawy-4,88,68);
				}
				for(int y=0;y<levels[0][0].length;y++){
					for(int x=0;x<levels[0].length;x++){
						if(levels[i*5+j][x][y]==1){
							g2.setColor(Color.darkGray); 
							if(maxlvl<i*5+j){
								g2.setColor(Color.lightGray);
							}
							g2.drawRect(2*x+drawx, 2*y+drawy, 1,1);
							
							if(i==0){
								g2.drawString(""+(i*5+j+1)+"",drawx+35,drawy+90);
							}else if(i==1){
								g2.drawString(""+(i*5+j+1)+"",drawx+35,drawy-10);
							}
						}
					}
				}
			    drawx+=3*32;
			}
			drawy+=5*32+2;
		}
		g2.setColor(Color.darkGray); 
		g2.setFont(registerFont.deriveFont(30f));
		g2.drawString("Exit",290,420);
		if(select==10){
			g2.setColor(Color.red);
			g2.drawRect(280,385,80,50);
		}
	}
	
	public int getSelect(){
		return select;
	}
	
	public static int getMaxlvl(){
		return maxlvl;
	}
}
