package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Credits extends MyGameScreen {

	private static String credits = "Game by Oscar Triano Garcia (dotteri/.teri <teritriano(at)gmail.com>)" +
			"\n\n\nBackground image by NASA, ESA, and The Hubble Heritage Team (STScI/AURA)" +
			"\n\n\nGraphics made with Inkscape and Gimp" +
			"\n\n\nSound effects and music made with LMMS, ZynAddSubFx and Audacity";
	
	class Back extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.gotoLastScreen();
		}
	}
	
	public Credits(MyGame mygame){
		super.create(mygame);
		
		TextButton back = new TextButton("back", GuiElements.getTextButtonStyle() );
		back.setPosition(8.f, 8.f);
		back.addCaptureListener(new Back() );
		stage.addActor(back);
		
		Label.LabelStyle style = new Label.LabelStyle(GuiElements.getLabelStyle());
		style.fontColor = Color.WHITE;
		TextureRegion region = new TextureRegion();
		Util.configureTextureRegion(region, mygame.gui_objects_model, "background1_ninepatch", mygame.assets);
		style.background = new NinePatchDrawable(new NinePatch(region, 5, 5, 5, 5));
		
		Label author = new Label(credits, style);
		author.setWrap(true);
		author.setPosition(32.f, mygame.FHEIGHT - author.getHeight() - 256.f);
		author.setWidth(mygame.FWIDTH /1.1f);
		author.setHeight(480.f);
		stage.addActor(author);
	}
}
