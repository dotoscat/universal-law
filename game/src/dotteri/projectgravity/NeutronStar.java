package dotteri.projectgravity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

public class NeutronStar extends GravitationalObject {
	
	Engine engine = null;
	State state = State.Living;
	ScaleToAction scale = null;
	Blackhole black_hole = null;
	
	float time = 0.f;
	static float DISAPPEARING = 0.12f;
	
	enum State{
		Living,
		Disappearing,
		Dissapeared
	}
	
	public NeutronStar(Engine engine){
		this.engine = engine;
		State state = State.Living;
		scale = new ScaleToAction();
		
		this.setAreaRadius(16.f);
		this.setCollisionRadius(4.f);
	}
	
	@Override
	public void act(float delta){
		if (!engine.isRunning()){
			return;
		}
		super.act(delta);
		switch(state){
		case Disappearing:
			this.time += delta;
			if (this.time > DISAPPEARING){
				state = State.Dissapeared;
				this.setVisible(false);
				if (black_hole != null){
					Vector2 pos = black_hole.getPosition();
					engine.addNova(pos.x, pos.y, 0.12f, black_hole);
				}
			}
		break;
		}
	}
	
	public boolean isDissapeared(){
		return state == State.Dissapeared;
	}
	
	public boolean isLiving(){
		return state == State.Living;
	}
	
	@Override
	public void reset(){
		this.time = 0.f;
		state = State.Living;
		this.setVisible(true);
		super.reset();
	}
	
	public void setToDisappearing(){
		this.time = 0.f;
		state = State.Disappearing;
		this.clearActions();
		scale.setScale(0.f);
		scale.setDuration(DISAPPEARING);
		this.addAction(scale);
	}
	
	public void setBlackhole(Blackhole black_hole){
		this.black_hole = black_hole;
	}
	
}
