package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Message extends Label{
	
	private ParallelAction parallel = null;
	private AlphaAction alpha = null;
	private MoveToAction move = null;
	boolean used = false;
	float time = 0.f;
	float duration = 0.f;
	
	public Message(CharSequence text, LabelStyle style) {
		super(text, style);
		
		parallel = new ParallelAction();
		alpha = new AlphaAction();
		move = new MoveToAction();		
		this.setVisible(false);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void act(float delta){
		if (!this.isUsed() ){
			return;
		}
		super.act(delta);
		
		time += delta;
		if (time >= duration){
			used = false;
		}
	}
		
	public boolean isUsed(){
		return used;
	}
	
	public void set(float move_y, float duration){
		parallel.restart();
		
		Color color = this.getColor();
		color.a = 1.f;
		this.setColor(color);
		alpha.restart();
		alpha.setDuration(duration);
		alpha.setAlpha(0.f);
		
		float x = this.getX();
		float y = this.getY();
			
		move.restart();
		move.setDuration(duration);
		move.setPosition(x, y + move_y);
		
		parallel.addAction(alpha);
		parallel.addAction(move);
		
		this.clearActions();
		this.addAction(parallel);
		
		this.setVisible(true);
		used = true;
		time = 0.f;
		this.duration = duration;
	}
	
	public void reset(){
		this.setVisible(false);
		used = false;
	}
	
}
