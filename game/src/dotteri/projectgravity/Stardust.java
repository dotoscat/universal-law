package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

public class Stardust extends GravitationalObject {
	
	ParallelAction parallel = null;
	AlphaAction alpha = null;
	ScaleToAction scale = null;
	
	enum State{
		Appear,
		Appeared,
		Disappear,
		Disappeared
	}
	
	State state = State.Disappeared;
	
	float time = 0.f;
	float duration = 0.f;
	
	Engine engine = null;
	
	public Stardust(Engine engine){
		this.engine = engine;
		
		parallel = new ParallelAction();
		alpha = new AlphaAction();
		scale = new ScaleToAction();
		
		sprite.setColor(Color.MAGENTA);
		
		this.setSize(32.f, 32.f);
		this.setRadius(16.f);
		this.setVisible(false);
	}
	
	public void set(float duration_appear){
		state = State.Appear;
		
		this.time = 0.f;
		this.duration = duration_appear;
		
		this.setVisible(true);
		
		Color color = this.getColor();
		color.a = 0.f;
		alpha.restart();
		alpha.setAlpha(1.f);
		alpha.setDuration(duration);
		this.clearActions();
		this.addAction(alpha);
		
		this.setScale(1.f);
	}
	
	public boolean isDisappeared(){
		return state == State.Disappeared;
	}
	
	public void setDisappear(float duration){
		this.clearActions();
		
		alpha.restart();
		alpha.setAlpha(0.f);
		alpha.setDuration(duration);
		
		scale.restart();
		scale.setScale(0.f);
		scale.setDuration(duration);
		
		parallel.restart();
		parallel.addAction(alpha);
		parallel.addAction(scale);
		
		this.addAction(parallel);
		
		this.time = 0.f;
		this.duration = duration;
		state = State.Disappear;
	}
	
	public void clean(){
		this.setVisible(false);
		state = State.Disappeared;
	}
	
	@Override
	public void act(float delta){
		if (!engine.isRunning()){
			return;
		}
		super.act(delta);
		this.time += delta;
		switch(state){
		case Appear:
			if (time > duration){
				state = State.Appeared;
			}
			break;
		case Disappear:
			if (time > duration){
				state = State.Disappeared;
				this.setVisible(false);
			}
			break;
		}
	}

}
