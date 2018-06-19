package dotteri.projectgravity;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionMenu extends MyGameScreen {
	
	private Slider sound_slider = null;
	private Label sound_label = null;
	
	private Slider music_slider = null;
	private Label music_label = null;
		
	private Button return_button = null;
	private Button save_and_return_button = null;
	
	private ButtonGroup group_screens = null;
	TextButton button_1x = null;
	TextButton button_075x = null;
	TextButton button_05x = null;
	
	class ChangeSound extends ChangeListener{
		
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// TODO Auto-generated method stub
			Slider sound_slider = (Slider)actor;
			mygame.setSoundLevel( (int)sound_slider.getValue() );
			mygame.playSound("press");
		}
		
	}
	
	class ChangeMusic extends ChangeListener{
		
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// TODO Auto-generated method stub
			Slider music_slider = (Slider)actor;
			mygame.setMusicLevel( (int)music_slider.getValue() );
		}
		
	}
	
	class ChangeResolution extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			TextButton button = (TextButton) event.getListenerActor();
			float factor = Float.valueOf( button.getLabel().getText().toString() );
			mygame.setScreenFactor(factor);
		}
		
	}
	
	class Return extends ClickListener{

		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.gotoLastScreen();
			mygame.playSound("press");
		}
		
	}
	
	class SaveAndReturn extends ClickListener{

		@Override
		public void clicked(InputEvent event, float x, float y){
			//do something more like save the options...
			mygame.saveData();
			mygame.gotoLastScreen();
			mygame.playSound("press");
		}
		
	}
		
	@Override
	public void create(MyGame mygame){
		super.create(mygame);
		
		//sound label
		sound_label = new Label("SOUND", GuiElements.getLabelStyle() );
		sound_label.setPosition(128.f, mygame.FHEIGHT-250.f);
		stage.addActor(sound_label);
		//slider sound
		sound_slider = new Slider(0.f, 5.f, 1.f, false, GuiElements.getSliderStyle());
		sound_slider.addCaptureListener( new ChangeSound() );
		sound_slider.setPosition
		(sound_label.getX() + sound_label.getWidth() + 8.f, sound_label.getY());
		stage.addActor(sound_slider);
		//music label
		music_label = new Label("MUSIC", GuiElements.getLabelStyle() );
		music_label.setPosition(128.f, mygame.FHEIGHT-250.f-sound_label.getHeight()-32.f);
		stage.addActor(music_label);
		//slider music
		music_slider = new Slider(0.f, 5.f, 1.f, false, GuiElements.getSliderStyle() );
		music_slider.addCaptureListener( new ChangeMusic() );
		stage.addActor(music_slider);
		music_slider.setPosition(music_label.getX() + music_label.getWidth() + 8.f, music_label.getY());
		//cancel button
		return_button = new TextButton("return", GuiElements.getTextButtonStyle() );
		return_button.addCaptureListener( new Return() );
		return_button.setPosition(8.f, 32.f);
		stage.addActor(return_button);
		//ok button
		save_and_return_button = new TextButton("Save and return", GuiElements.getTextButtonStyle() );
		save_and_return_button.addCaptureListener( new SaveAndReturn() );
		save_and_return_button.setPosition(return_button.getX() + return_button.getWidth() + 32.f, return_button.getY());
		stage.addActor(save_and_return_button);
		
		if (Gdx.app.getType() == ApplicationType.Desktop){
			Label screen_label = new Label("screen", GuiElements.getLabelStyle());
			screen_label.setPosition(music_label.getX(), music_label.getY() - music_label.getHeight() - 32.f);
			stage.addActor(screen_label);
			
			group_screens = new ButtonGroup();
			
			button_1x = new TextButton("1", GuiElements.getSmallTextButtonStyle());
			button_075x = new TextButton("0.75", GuiElements.getSmallTextButtonStyle());
			button_05x = new TextButton("0.5", GuiElements.getSmallTextButtonStyle());
			
			button_1x.setPosition(screen_label.getX() + screen_label.getWidth() + 64.f, screen_label.getY());
			button_075x.setPosition(button_1x.getX() + button_1x.getWidth() + 8.f, screen_label.getY());
			button_05x.setPosition(button_075x.getX() + button_075x.getWidth() + 8.f, screen_label.getY());
			
			ChangeResolution change_resolution = new ChangeResolution();
			
			button_1x.addCaptureListener(change_resolution);
			button_075x.addCaptureListener(change_resolution);
			button_05x.addCaptureListener(change_resolution);
			
			group_screens.add(button_1x);
			group_screens.add(button_075x);
			group_screens.add(button_05x);
			
			stage.addActor(button_1x);
			stage.addActor(button_075x);
			stage.addActor(button_05x);
		}
		
	}
	
	public void setSoundLevel(int level){
		sound_slider.setValue(level);
	}
	
	public void setMusicLevel(int level){
		music_slider.setValue(level);
	}
	
	public float getScreenFactor(){
		TextButton button = (TextButton) group_screens.getChecked();
		if (button == null){
			return 1.f;
		}
		return Float.valueOf(button.getLabel().getText().toString() );
	}
	
	public void setScreenFactor(float factor){
		if (factor == 0.75f){
			button_075x.setChecked(true);
		}
		else if (factor == 0.5f){
			button_05x.setChecked(true);
		}
		else{
			button_1x.setChecked(true);
		}
	}
	
}
