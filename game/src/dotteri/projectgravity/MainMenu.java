package dotteri.projectgravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class MainMenu extends MyGameScreen{
	
	private TextButton arcade_button = null;
	private Button arcade_level_up = null;
	private Button arcade_level_down = null;
	private Label level = null;
	private TextButton option_button = null;
	private TextButton exit_button = null;
	
	class StartArcade extends ClickListener{
				
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.engine.reset();
			mygame.setArcadeGameMode();
			mygame.setScreenEngine();
			mygame.playSound("press");
		}
		
	}
	
	class ShowArcadeScoreList extends ClickListener{

		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.setShowRecordsScreen(mygame.arcade_gamemode);
			mygame.playSound("press");
		}
		
	}
	
	class DecreaseArcadeLevel extends ClickListener{
		
		MyGame mygame = null;
		public DecreaseArcadeLevel(MyGame mygame){
			this.mygame = mygame;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.arcade_gamemode.levelDown();
			level.setText(mygame.arcade_gamemode.getLevelString());
		}
	}
	
	class IncreaseArcadeLevel extends ClickListener{
		
		MyGame mygame = null;
		public IncreaseArcadeLevel(MyGame mygame){
			this.mygame = mygame;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.arcade_gamemode.levelUp();
			level.setText(mygame.arcade_gamemode.getLevelString());
		}
	}
	
	class Credits extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.playSound("press");
			mygame.setScreenCredits();
		}
	}
	
	class Exit extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.playSound("press");
			Gdx.app.exit();
		}
	}
	
	@Override
	public void create(MyGame mygame){
		super.create(mygame);
				
		//arcade button
		arcade_button = new TextButton("ARCADE", GuiElements.getTextButtonStyle() );
		arcade_button.setPosition
		((mygame.FWIDTH-arcade_button.getWidth() )/2.f, 412.f);
		arcade_button.addCaptureListener(new StartArcade());
		stage.addActor(arcade_button);
		
		//arcade score button
		
		TextButton arcade_score = new TextButton("ARCADE SCORES", GuiElements.getTextButtonStyle());
		arcade_score.setPosition
		(arcade_button.getX(), arcade_button.getY() - arcade_score.getHeight() - 8.f);
		arcade_score.addCaptureListener(new ShowArcadeScoreList());
		stage.addActor(arcade_score);
		
		/*
		//arcade level down button
		arcade_level_down = new Button(GuiElements.getDecrementadorButton() );
		arcade_level_down.setPosition
		((mygame.FWIDTH-arcade_button.getWidth() )/2.f + arcade_button.getWidth() + 8.f, arcade_button.getY());
		arcade_level_down.addCaptureListener(new DecreaseArcadeLevel(mygame) );
		stage.addActor(arcade_level_down);
		//label level
		level = new Label("1", GuiElements.getLabelStyle() );
		level.setWidth(64.f);
		level.setPosition
		((mygame.FWIDTH-arcade_button.getWidth() )/2.f + arcade_button.getWidth() + 64.f, arcade_button.getY());
		stage.addActor(level);
		//arcade level up button
		arcade_level_up = new Button(GuiElements.getIncrementerButton() );
		arcade_level_up.setPosition
		((mygame.FWIDTH-arcade_button.getWidth() )/2.f + arcade_button.getWidth() + 128.f, arcade_button.getY());
		arcade_level_up.addCaptureListener(new IncreaseArcadeLevel(mygame) );
		stage.addActor(arcade_level_up);
		*/
		
		//options button
		option_button = new TextButton("Options", GuiElements.getTextButtonStyle() );
		option_button.addCaptureListener( GuiElements.getGoToOptions_action() );
		option_button.setPosition
		((mygame.FWIDTH-arcade_button.getWidth() )/2.f, 256.f);
		stage.addActor(option_button);
		
		//credits button
		
		TextButton credits_button = new TextButton("credits", GuiElements.getTextButtonStyle());
		credits_button.addCaptureListener(new Credits());
		credits_button.setPosition(option_button.getX(), option_button.getY() - option_button.getHeight() - 8.f);
		stage.addActor(credits_button);
		
		//exit button
		exit_button = new TextButton("EXIT", GuiElements.getTextButtonStyle() );
		exit_button.setPosition
		(credits_button.getX(), credits_button.getX() - credits_button.getWidth() - 8.f);
		exit_button.addCaptureListener( new Exit() );
		stage.addActor(exit_button);
				
		//Title
		
		Label.LabelStyle title_style = new Label.LabelStyle(mygame.font64, Color.WHITE);
		Label title = new Label(mygame.TITLE, title_style);
		title.setPosition((mygame.FWIDTH - title.getWidth()) / 2.f, mygame.FHEIGHT - title.getHeight() );
		stage.addActor(title);
		
		//Label version
		Label.LabelStyle small_style = new Label.LabelStyle(mygame.font24, Color.WHITE);
		Label version = new Label("ver." + mygame.VERSION, small_style);
		version.setPosition(title.getX(), title.getY() - 32.f );
		stage.addActor(version);
		
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
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
		super.dispose();
	}
	
}
