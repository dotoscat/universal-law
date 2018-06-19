package dotteri.projectgravity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Nova extends Effect {

	ScaleToAction scale_in = null;
	ScaleToAction scale_out = null;
	SequenceAction sequence = null;
	
	GameObject source = null;
	
	Nova(Engine engine){
		this.engine = engine;
		scale_in = new ScaleToAction();
		scale_out = new ScaleToAction();
		sequence = new SequenceAction();
	}
	
	@Override
	public void set(float x, float y, float lifespan){
		this.setPosition(x, y);

		this.clearActions();
		sequence.reset();
		
		this.setScale(0.f);
				
		scale_in.reset();
		scale_in.setScale(2.f);
		scale_in.setDuration(lifespan/2.f);
		sequence.addAction(scale_in);
	
		scale_out.reset();
		scale_out.setScale(0.f);
		scale_out.setDuration(lifespan/2.f);
		sequence.addAction(scale_out);
		
		this.addAction(sequence);
		
		this.resetTime();
		this.setDuration(lifespan);
		this.setVisible(true);
	}
	
	public void setSource(GameObject source){
		this.source = source;
	}
	
	@Override
	public void act(float delta){
		if (source != null){
			Vector2 pos = source.getPosition();
			this.setPosition(pos.x, pos.y);
		}
		super.act(delta);
	}
	
}
