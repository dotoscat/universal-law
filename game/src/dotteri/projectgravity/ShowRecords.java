package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ShowRecords extends MyGameScreen {
	
	ShowRecordList records = null;
	TextButton exit = null;
	TextButton clear = null;
	Clear clear_action = null;
	ConfirmClear confirm_clear = null;
	GameMode game_mode = null;
	
	private class Back extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.gotoLastScreen();
			mygame.playSound("press");
		}
		
	}
	
	private class Clear extends ClickListener{
		
		ShowRecords show_records = null;
		
		public Clear(ShowRecords show_records){
			this.show_records = show_records;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			show_records.showConfirmClear();
		}
		
	}
	
	public class ConfirmClear extends Dialog{

		ShowRecords show_records = null;
		TextButton yes = null;
		TextButton no = null;
		
		public ConfirmClear(String title, WindowStyle windowStyle, ShowRecords show_records){
			super(title, windowStyle);
			
			this.text("Are you sure to clear the records?", GuiElements.getLabelStyle());
			
			yes = new TextButton("yes", GuiElements.getTextButtonStyle());
			no = new TextButton("no", GuiElements.getTextButtonStyle());
			
			this.button(no, no);
			this.button(yes, yes);
			this.show_records = show_records;
		}
		
		@Override
		protected void result(java.lang.Object object){
			TextButton answer = (TextButton)object;
			if(answer == yes){
				show_records.clearRecordList();
				return;
			}
		}		
	}
	
	public ShowRecords(MyGame mygame){
		super.create(mygame);
		
		records = new ShowRecordList(10, mygame);
		records.setPosition(32.f, 32.f);
		stage.addActor(records);
		records.setVisible(true);
		
		exit = new TextButton("Back", GuiElements.getTextButtonStyle());
		stage.addActor(exit);
		exit.addCaptureListener(new Back());
		exit.setPosition(records.getX(), 16.f);
		
		clear_action = new Clear(this);
		
		clear = new TextButton("Clear", GuiElements.getTextButtonStyle());
		stage.addActor(clear);
		clear.addCaptureListener(clear_action);
		clear.setPosition((mygame.FWIDTH - clear.getWidth()) / 2.f, 16.f);
		
		Window.WindowStyle window_style = new Window.WindowStyle(mygame.font24, Color.WHITE, GuiElements.getBackground1_drawable());
		
		confirm_clear = new ConfirmClear("Warning", window_style, this);	
	}
	
	public void loadScores(GameMode game_mode){
		this.game_mode = game_mode;
		records.loadFrom(game_mode);
	}
	
	public void showConfirmClear(){
		confirm_clear.show(this.stage);
	}
	
	public void clearRecordList(){
		game_mode.clearRecordList();
		this.loadScores(game_mode);
		mygame.saveData();
	}
	
}
