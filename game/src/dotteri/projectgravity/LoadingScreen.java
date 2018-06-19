package dotteri.projectgravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class LoadingScreen extends MyGameScreen {

	@Override
	public void create(MyGame mygame){
		mygame.assets.load("data/game_objects.png", Texture.class);
		mygame.assets.load("data/gui_objects.png", Texture.class);
		this.mygame = mygame;
	}
	
	@Override
	public void render(float delta){
		if (mygame.assets.update()){
			mygame.finishLoading();
		}
		Gdx.app.log("loading ", String.format("%g", mygame.assets.getProgress()));
		System.out.println(mygame.assets.getProgress());
	}
	
	@Override
	public void dispose(){
		super.dispose();
	}
	
}
