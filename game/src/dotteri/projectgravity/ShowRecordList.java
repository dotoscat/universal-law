package dotteri.projectgravity;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class ShowRecordList extends Group {
	
	Label[] position = null;
	Label[] name = null;
	Label[] data1 = null;
	Label[] data2 = null;
	Label caption_data1 = null;
	Label caption_data2 = null;
	
	Group group_data2 = null;
	Image background = null;
	
	static final float MARGIN = 16.f;
	
	static final float POSITION_POSITION = 8.f;
	static final float POSITION_LENGTH = 32.f;
	static final float NAME_POSITION = MARGIN + POSITION_POSITION + POSITION_LENGTH;
	static final float NAME_LENGTH = 256.f + MARGIN*2;
	static final float DATA1_POSITION = MARGIN + NAME_POSITION + NAME_LENGTH;
	static final float DATA1_LENGTH = 128.f;
	static final float DATA2_POSITION = MARGIN + DATA1_POSITION + DATA1_LENGTH;
	static final float DATA2_LENGTH = 128.f;
	
	public ShowRecordList(int entries, MyGame mygame){
		position = new Label[entries];
		name = new Label[entries];
		data1 = new Label[entries];
		data2 = new Label[entries];
		
		TextureRegion region = new TextureRegion();
		Util.configureTextureRegion(region, mygame.gui_objects_model, "background1_ninepatch", mygame.assets);
		
		background = new Image(new NinePatch(region, 5, 5, 5, 5));
		//background dimensions are set with setWidth and setHeight
		//inside the method loadFrom
		background.setPosition(0.f, 8.f);
		this.addActor(background);
				
		for(int i = 0; i < entries ; i += 1){
			position[i] = new Label(Integer.toString(i + 1), GuiElements.getLabelStyle());
			position[i].setPosition(8.f, (entries-i) * (position[i].getHeight() + MARGIN) );
			this.addActor(position[i]);
			
			name[i] = new Label("Test", GuiElements.getLabelStyle());
			name[i].setPosition(NAME_POSITION, position[i].getY());
			this.addActor(name[i]);
			
			data1[i] = new Label("data", GuiElements.getLabelStyle());
			data1[i].setPosition(DATA1_POSITION, name[i].getY());
			
			this.addActor(data1[i]);
		}
		caption_data1 = new Label("Caption 1", GuiElements.getLabelStyle());
		caption_data1.setPosition(DATA1_POSITION, data1[0].getY() + MARGIN*3);
		this.addActor(caption_data1);
		
		group_data2 = new Group();
		
		caption_data2 = new Label("Caption 2", GuiElements.getLabelStyle());
		caption_data2.setPosition(DATA2_POSITION, caption_data1.getY() );
		group_data2.addActor(caption_data2);
		
		for(int i = 0; i < entries; i += 1){
			data2[i] = new Label("data 2", GuiElements.getLabelStyle());
			data2[i].setPosition(DATA2_POSITION, name[i].getY());
			group_data2.addActor(data2[i]);
		}
		this.addActor(group_data2);
		
		background.setHeight(caption_data1.getY() + caption_data1.getHeight());
	}
	
	public void loadFrom(GameMode game_mode){
		GameMode.Record[] list = game_mode.getRecordList();
		
		caption_data1.setText(game_mode.getCapationData1());
		for(int i = 0; i < list.length; i += 1){
			name[i].setText(list[i].getName());
			data1[i].setText(list[i].getData1().toString());
		}
		
		if (game_mode.isUsingData2()){
			group_data2.setVisible(true);
			caption_data2.setText(game_mode.getCapationData2());
			for(int i = 0; i < list.length; i += 1){
				data2[i].setText(list[i].getData2().toString());
			}
			background.setWidth(DATA2_POSITION + DATA2_LENGTH + MARGIN);
		}else{
			group_data2.setVisible(false);
			background.setWidth(DATA1_POSITION + DATA1_LENGTH + MARGIN);
		}
	}

	@Override
	public float getHeight(){
		return background.getHeight();
	}
	
	@Override
	public float getWidth(){
		return this.background.getWidth();
	}
	
}
