package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Advert extends Label {

	float screen_width = 0.f;
	float screen_height = 0.f;
	
	AlphaAction alpha = null;
	
	float scale = 1.f;
	float scale_step = 1.f;
	float final_scale = 1.f;
	
	float time = 0.f;
	float duration = 0.f;
	
	public Advert(CharSequence text, LabelStyle style, float screen_width, float screen_height) {
		super(text, style);
		this.setScreenSize(screen_height, screen_width);
		alpha = new AlphaAction();
		this.setVisible(false);
		// TODO Auto-generated constructor stub
	}

	public void setScreenSize(float screen_height, float screen_width){
		this.screen_width = screen_width;
		this.screen_height = screen_height;
	}
	
	@Override
	public void act(float delta){
		if (this.isOver() ){
			return;
		}
		super.act(delta);
		
		if ( (scale_step > 0 && scale < final_scale) 
				|| (scale_step < 0 && scale > final_scale) ){
			scale += scale_step * delta;
			this.setFontScale(scale);
			TextBounds bounds = this.getTextBounds();
			this.setPosition( (screen_width - bounds.width) / 2.f,
				(screen_height - bounds.height) / 2.f);
		}
		this.layout();
		
		time += delta;

	}
	
	public void set(float initial_scale, float final_scale,
		float initial_alpha, float final_alpha, float duration){
		
		Color color = this.getColor();
		color.a = initial_alpha;
		this.setColor(color);
		alpha.restart();
		alpha.setAlpha(final_alpha);
		alpha.setDuration(duration);
		this.clearActions();
		this.addAction(alpha);
		
		scale = initial_scale;
		this.setFontScale(initial_scale);
		this.final_scale = final_scale;
		scale_step = (final_scale - initial_scale);
		//System.out.format("initial_alpha: %g - final_alpha: %g\n", initial_alpha, final_alpha);
		
		this.time = 0.f;
		this.duration = duration;
		
		this.setVisible(true);
	}
	
	public boolean isOver(){
		return time >= duration;
	}
	
}
