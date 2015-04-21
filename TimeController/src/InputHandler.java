import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
	private static boolean[] keys= new boolean[5]; // 0=left, 1 = right, 2= up, 3=down, 4 = SPACE
	private static boolean[] keysold=new boolean[5];
	
	public boolean[] getKeys(){
		return keys;
	}
	
	public boolean isLeftDown(){
		return keys[0];
	}
	
	public boolean isRightDown(){
		return keys[1];
	}
	
	public boolean isSpaceDown(){
		return keys[4];
	}
	
	public boolean isRightReleased(){
		
		boolean a= !keys[1]&&keysold[1];
		keysold[1]=keys[1];
		return a;
	}
	
	public boolean isLeftReleased(){
		
		boolean a= !keys[0]&&keysold[0];
		keysold[0]=keys[0];
		return a;
	}
	
	//creates the KeyEvents and adds Keys to the boolean
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) 
		{
			keys[0]=true;
			keysold[0]=true;
		}else if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
		{
			keys[1]=true;
			keysold[1]=true;
		}else if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
		{
			keys[2]=true;
			keysold[2]=true;
		}else if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
		{
			keys[3]=true;
			keysold[3]=true;
		}else if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
		{
			keys[4]=true;
			keysold[4]=true;
		}
		
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
			keys[0]=false;
		}else if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
		{
			keys[1]=false;
		}else if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
		{
			keys[2]=false;
		}else if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
		{
			keys[3]=false;
		}else if(code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER)
		{
			keys[4]=false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
