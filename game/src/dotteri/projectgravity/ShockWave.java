package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;

public class ShockWave extends Effect {
	
	ParallelAction parallel = null;
	AlphaAction alpha = null;
	ScaleToAction scale = null;
		
	ShockWave(Engine engine){
		this.engine = engine;
		parallel = new ParallelAction();
		alpha = new AlphaAction();
		scale = new ScaleToAction();
	}

	@Override
	public void set(float x, float y, float duration) {
		// TODO Auto-generated method stub
		this.setPosition(x, y);

		this.clearActions();
		parallel.reset();
		
		this.setScale(0.f);
				
		scale.reset();
		scale.setScale(2.f);
		scale.setDuration(duration);
		parallel.addAction(scale);
		
		Color color = this.getColor();
		color.a = 1.f;
		alpha.reset();
		alpha.setAlpha(0.f);
		alpha.setDuration(duration);
		parallel.addAction(alpha);
		
		this.addAction(parallel);
		
		this.resetTime();
		this.setDuration(duration);
		this.setVisible(true);
	}
	
}
