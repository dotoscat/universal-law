package dotteri.projectgravity;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	
	public static MyGame mygame = null;
	public static LwjglApplicationConfiguration cfg = null;
	
	public static void main(String[] args) {		
		Main.mygame = new MyGame();
		cfg = new LwjglApplicationConfiguration();
		cfg.title = mygame.TITLE + " ver." + mygame.VERSION;
		cfg.useGL20 = true;
		//cfg.width = (int)(mygame.FWIDTH * factor);
		//cfg.height = (int)(mygame.FHEIGHT * factor);
		new LwjglApplication(Main.mygame, cfg);
	}
	
}
