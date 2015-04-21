import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

public class Player {

	private final double tau=2*Math.PI; // one full angle (2*pi)
	
	private static int height=16;
	private static int width=16;
	
	private Image imgp; // player
	private Image imgg; // ghost
	
	private int len = 50;
	
	public double[] xpos=new double[len];
	public double[] ypos=new double[len];
	public double[] alpha= new double[len]; // angle in radians
	
	public double vel = 2;
	public double velw = tau/128; // velocity of angle
	
	public Player()
	{
		setStartPos(0,0);
		
		imgp = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("data/img/figurt.png"));
		while(imgp.getWidth(null)<0){} // TODO more appropriate way of waiting till it's loaded.
		imgg = Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("data/img/figurghostt.png"));
		while(imgg.getWidth(null)<0){} // TODO more appropriate way of waiting till it's loaded.
	}
	
	public void setStartPos(int x,int y){
		for(int i=0;i<len;i++){
			xpos[i]=(double)x;
			ypos[i]=(double)y;
			alpha[i]=tau*7/8;
		}
	}
	
	public void update(InputHandler inHandler){
		// shift
		for(int i=len-1;i>0;i--)
		{
			xpos[i]=xpos[i-1];
			ypos[i]=ypos[i-1];
			alpha[i]=alpha[i-1];
		}
		
		// turn
		if(inHandler.isLeftDown())
		{ // 0=left, 1 = right,
			alpha[0]+=velw;
			alpha[0]%=tau;
		}
		else if(inHandler.isRightDown())
		{ 
			alpha[0]-=velw;
			alpha[0]+=tau;
			alpha[0]%=tau;
		}
				
		// move
		xpos[0]+=Math.cos(alpha[0])*vel;
		ypos[0]-=Math.sin(alpha[0])*vel; 
	
	}
	
	
	public void draw(Graphics2D g,InputHandler inHandler){
		int frame=Math.abs((int)System.currentTimeMillis()/200)%2; // Millis/200 means 5 times per second a different sub-image
		// draw player
		int dir=0;// 0= right,1=down,2=left,3=up 
		if(alpha[0] <tau*7/8)
		{
			dir=1;
			if(alpha[0] < tau*5/8)
			{
			    dir=2;
			    if(alpha[0] < tau*3/8)
			    {
			        dir=3;
			        if(alpha[0] < tau*1/8)
			        {
			            dir=0;
		            }
				}
		    }
		}
		Image img=imgp;
		if(inHandler.isSpaceDown()) // SPACE
		{
			img=imgg;
		}
		g.drawImage(img,(int)xpos[0],(int)ypos[0],(int)xpos[0]+16,(int)ypos[0]+16,(2*dir+frame)*16,0,(2*dir+1+frame)*16,16,null);
		
		// draw ghost
		dir=0;// 0= right,1=down,2=left,3=up 
		if(alpha[len-1] <tau*7/8)
		{
			dir=1;
			if(alpha[len-1] < tau*5/8)
			{
			    dir=2;
			    if(alpha[len-1] < tau*3/8)
			    {
					        dir=3;
			        if(alpha[len-1] < tau*1/8)
			        {
			            dir=0;
		            }
				}
		    }
		}
		img=imgg;
		if(inHandler.isSpaceDown()) // SPACE
		{
			img=imgp;
		}
		g.drawImage(img,(int)xpos[len-1],(int)ypos[len-1],(int)xpos[len-1]+width,(int)ypos[len-1]+height,(2*dir+frame)*width,0,(2*dir+1+frame)*width,height,null);
		
	}
	
}
