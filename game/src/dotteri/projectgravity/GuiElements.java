package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class GuiElements {

	static private GuiElements gui_elements = null;
	
	private Button.ButtonStyle right_button = null;
	private Button.ButtonStyle left_button = null;
	private TextButton.TextButtonStyle text_button_style = null;
	private TextButton.TextButtonStyle small_text_button_style = null;
	private Label.LabelStyle label = null;
	private Slider.SliderStyle slider = null;
	private Button.ButtonStyle pause_button = null;
	private Label.LabelStyle message = null;
	private Label.LabelStyle announce = null;
	private NinePatchDrawable background1_drawable = null;
	
	private ClickListener gotooptions = null;

	static MyGame mygame = null;
		
	class GoToOptions extends ClickListener{
						
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.setScreenOptions();
			super.clicked(event, x, y);
		}
		
	}
	
	private GuiElements(MyGame mygame){
		GuiElements.mygame = mygame;
		
		TextureRegion text_button = new Sprite();
		Util.configureTextureRegion
		(text_button, mygame.gui_objects_model, "background1_ninepatch", mygame.assets);
		
		background1_drawable = new NinePatchDrawable(new NinePatch(text_button, 5, 5, 5, 5));
		
		text_button_style = new TextButton.TextButtonStyle();
		text_button_style.fontColor = Color.WHITE;
		text_button_style.font = mygame.font24;
		text_button_style.up = new NinePatchDrawable(new NinePatch(text_button, 5, 5, 5, 5));
		text_button_style.up.setMinWidth(256);
		text_button_style.up.setMinHeight(8);
		text_button_style.down = text_button_style.up;
		text_button_style.downFontColor = Color.DARK_GRAY;
		
		small_text_button_style = new TextButton.TextButtonStyle();
		small_text_button_style.fontColor = Color.WHITE;
		small_text_button_style.font = mygame.font24;
		small_text_button_style.up = new NinePatchDrawable(new NinePatch(text_button, 5, 5, 5, 5));
		small_text_button_style.up.setMinWidth(128);
		small_text_button_style.up.setMinHeight(8);
		small_text_button_style.down = small_text_button_style.up;
		small_text_button_style.downFontColor = Color.DARK_GRAY;
		small_text_button_style.checkedFontColor = Color.DARK_GRAY;
		
		Sprite tmp_sprite = null;
	
		tmp_sprite = new Sprite();
		Util.configureSpriteWithModel
		(tmp_sprite, mygame.gui_objects_model, "arrow_up", mygame.assets);
		NinePatch right_button_up = new NinePatch(tmp_sprite);

		tmp_sprite = new Sprite();
		Util.configureSpriteWithModel
		(tmp_sprite, mygame.gui_objects_model, "arrow_over", mygame.assets);
		NinePatch right_button_over = new NinePatch(tmp_sprite);
		
		right_button = new Button.ButtonStyle();
		right_button.up = new NinePatchDrawable(right_button_up);
		right_button.down = new NinePatchDrawable(right_button_over);
		right_button.over = new NinePatchDrawable(right_button_over);
		
		tmp_sprite = new Sprite();
		Util.configureSpriteWithModel
		(tmp_sprite, mygame.gui_objects_model, "arrow_up", mygame.assets);
		tmp_sprite.flip(true, false);
		NinePatch left_button_up = new NinePatch(tmp_sprite);

		tmp_sprite = new Sprite();
		Util.configureSpriteWithModel
		(tmp_sprite, mygame.gui_objects_model, "arrow_over", mygame.assets);
		tmp_sprite.flip(true, false);
		NinePatch left_button_over = new NinePatch(tmp_sprite);
		
		left_button = new Button.ButtonStyle();
		left_button.up = new NinePatchDrawable(left_button_up);
		left_button.down = new NinePatchDrawable(left_button_over);
		left_button.over = new NinePatchDrawable(left_button_over);
		
		//label
		label = new Label.LabelStyle(mygame.font24, Color.WHITE);
		
		//Slider style
		Sprite slider_sprite = new Sprite();
		Util.configureSpriteWithModel
		(slider_sprite, mygame.gui_objects_model, "slider", mygame.assets);
		Sprite knob_sprite = new Sprite();
		Util.configureSpriteWithModel
		(knob_sprite, mygame.gui_objects_model, "slider_knob", mygame.assets);
		slider = new Slider.SliderStyle(new NinePatchDrawable( new NinePatch(slider_sprite) ),
				new NinePatchDrawable(new NinePatch(knob_sprite) )
		); 
		
		//Pause style
		Sprite sprite_button_pause = new Sprite();
		Util.configureSpriteWithModel
		(sprite_button_pause, mygame.gui_objects_model, "pause_button", mygame.assets);
		NinePatchDrawable pause_button_up = new NinePatchDrawable(new NinePatch(sprite_button_pause) );
		
		Sprite sprite_button_pause_down = new Sprite();
		Util.configureSpriteWithModel
		(sprite_button_pause_down, mygame.gui_objects_model, "pause_button_down", mygame.assets);
		NinePatchDrawable pause_button_down = new NinePatchDrawable(new NinePatch(sprite_button_pause_down) );
		
		pause_button = new Button.ButtonStyle(pause_button_up, pause_button_down, null);
		
		//label message style
		message = new Label.LabelStyle(mygame.font24, Color.GREEN);
		
		//label announce style
		announce = new Label.LabelStyle(mygame.font64, Color.GREEN);
		
		//GoToOptions action
		gotooptions = new GoToOptions();
		
	}
	
	static public void create(MyGame mygame){
		gui_elements = new GuiElements(mygame);
	}
		
	static public Button.ButtonStyle getDecrementadorButton(){
		return gui_elements.left_button;
	}
	
	static public Button.ButtonStyle getIncrementerButton(){
		return gui_elements.right_button;
	}
	
	static public TextButton.TextButtonStyle getTextButtonStyle(){
		return gui_elements.text_button_style;
	}
	
	static public TextButton.TextButtonStyle getSmallTextButtonStyle(){
		return gui_elements.small_text_button_style;
	}
	
	static public Label.LabelStyle getLabelStyle(){
		return gui_elements.label;
	}
	
	static public Slider.SliderStyle getSliderStyle(){
		return gui_elements.slider;
	}
	
	static public ClickListener getGoToOptions_action(){
		return gui_elements.gotooptions;
	}
	
	static public Button.ButtonStyle getPauseButton(){
		return gui_elements.pause_button;
	}
	
	static public Label.LabelStyle getMessageStyle(){
		return gui_elements.message;
	}
	
	static public Label.LabelStyle getAnnounceStyle(){
		return gui_elements.announce;
	}
	
	static public NinePatchDrawable getBackground1_drawable(){
		return gui_elements.background1_drawable;
	}
	
	//button (simple) style
	
}
