package dotteri.projectgravity;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class MyGameScreen implements Screen {
	
	protected MyGame mygame = null;
	protected Stage stage = null;

	private Image NASA_background = null;
	private Sprite NASA_sprite_background = null;
	private String NASA_background_name = null;
	
	public void create(MyGame mygame){
		this.mygame = mygame;
		this.createStage(mygame);
		
		NASA_sprite_background = new Sprite();
		NASA_background = new Image(new SpriteDrawable(NASA_sprite_background));
		stage.addActor(NASA_background);
	}
	
	public Stage getStage(){
		return stage;
	}
	
	private void createStage(MyGame mygame){
		stage = new Stage(mygame.FWIDTH, mygame.FHEIGHT, true, mygame.batch);
		stage.setCamera(mygame.camera);
	}
	
	public InputProcessor getInputProcessor(){
		stage.unfocusAll();
		return stage;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}
	
	public void setBackgroundName(String name){
		NASA_background_name = name;
	}
	
	public void loadBackground(){
		mygame.LoadNASAImage(NASA_background_name, NASA_sprite_background);
		NASA_background.setWidth(NASA_sprite_background.getWidth());
		NASA_background.setHeight(NASA_sprite_background.getHeight());
	}
	
}
