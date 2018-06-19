package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class Star extends GravitationalObject {

	enum State{
		BeBorn,
		Living,
		BeingDevoured,
		Dying,
		Dead
	}
	
	static float BEBORN = 1.f;
	static float DYING = 1.f;
	static float BEINGDEVOURED = 1.f;
	
	State state = State.Dead;
	static Color BLUESTAR = new Color(0.7f, 0.7f, 0.9f, 1.f);
	float time = 0.f;
	float living = 0.f;
	int amount = 0;
	StringBuilder stramount = null;
	
	BitmapFont.TextBounds amount_bounds = null;
	BitmapFont font = null;
	
	ParallelAction parallel = null;
	AlphaAction alpha = null;
	ColorAction change_color = null;
	ScaleToAction scale = null;
	
	Engine engine = null;
	
	static private class Bright extends Sprite{
		
		float duration;
		float time;
		float scale;
		
		enum State{
			Grow,
			Shrink,
		}
		
		State state;
		
		public Bright(){
			state = State.Grow;
			duration = 1.f;
			time = 0.f;
			scale = 1.f;
			this.setScale(scale);
		}
		
		public void step(float delta){
			this.time += delta;
			
			if (state == State.Grow){
				scale += (time * 1.2 / duration) * delta;
				this.setScale(scale);
				
				if (time >= duration){
					state = State.Shrink;
				}
			}
			else if (state == State.Shrink){
				scale -= 1.2 - ((time * 1.2 / duration) * delta);
				this.setScale(scale);
				
				if (time >= duration){
					state = State.Shrink;
				}
			}
			
		}
		
	}
	
	Bright bright1 = null;
	Bright bright2 = null;
	
	public Star(Engine engine){
		this.engine = engine;
		font = engine.mygame.font24;
		
		stramount = new StringBuilder();
		amount_bounds = new BitmapFont.TextBounds();
		
		parallel = new ParallelAction();
		alpha = new AlphaAction();
		change_color = new ColorAction();
		scale = new ScaleToAction();
		this.setVisible(false);
		
		this.setCollisionRadius(16.f);
		this.setAreaRadius(32.f);
		
		bright1 = new Bright();
		Util.configureSpriteWithModel(bright1, engine.mygame.game_objects_model, "bright1", engine.mygame.assets);
		bright2 = new Bright();
		Util.configureSpriteWithModel(bright2, engine.mygame.game_objects_model, "bright1", engine.mygame.assets);		
	}
	
	@Override
	public void act(float delta){
		if(!engine.isRunning()){
			return;
		}
		super.act(delta);
		time += delta;
		
		switch(state){
		case BeBorn:
			if (time > BEBORN){
				time = 0.f;
				state = State.Living;
				
				change_color.restart();
				change_color.setEndColor(BLUESTAR);
				change_color.setDuration(living);
				
				scale.restart();
				scale.setScale(2.f);
				scale.setDuration(living);
				
				parallel.restart();
				parallel.addAction(change_color);
				parallel.addAction(scale);
				
				this.clearActions();
				this.addAction(parallel);
				
			}
			break;
		case Living:
			if (time > living){
				this.kill();
				Vector2 pos = this.getPosition();
				engine.addNova(pos.x, pos.y, 1.f, null);
				engine.addShockWave(pos.x, pos.y, 2.f);
			}
			bright1.step(delta);
			bright2.step(delta);
			break;
		case Dying:
			if (time > DYING){
				state = State.Dead;
				this.setVisible(false);
				engine.getGameMode().starDead(this);
			}
			break;
		case BeingDevoured:
			if (time > BEINGDEVOURED){
				state = State.Dead;
				this.setVisible(false);
				engine.getGameMode().starDevoured(this);
			}
			break;
		}

	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha){
		bright1.draw(batch);
		bright2.draw(batch);
		super.draw(batch, parentAlpha);
		/*
		if(state == State.Living){
			Vector2 pos = this.getPosition();
			font.setColor(Color.DARK_GRAY);
			font.draw(batch, stramount, pos.x-(amount_bounds.width/2.f), pos.y+(amount_bounds.height/2.f) );
		}
		*/
	}
	
	public void set(int amount, Color maincolor, float life){
		sprite.setColor(maincolor);
		this.setColor(maincolor);
		living = life;
		time = 0.f;
		state = State.BeBorn;
		this.amount = amount;
		stramount.setLength(0);
		stramount.append(amount);
		font.getBounds(stramount, amount_bounds);
		
		Color color = this.getColor();
		color.a = 0.f;
		alpha.restart();
		alpha.setAlpha(1.f);
		alpha.setDuration(BEBORN);
		
		this.setScale(0.f);
		scale.restart();
		scale.setScale(1.f);
		scale.setDuration(BEBORN);
		
		parallel.restart();
		parallel.addAction(alpha);
		parallel.addAction(scale);
		
		this.clearActions();
		this.addAction(parallel);
		
		this.setVisible(true);
		
	}
	
	public boolean isDead(){
		return state == State.Dead;
	}
	
	public void kill(){
		state = State.Dying;
		this.shrinkStar();
	}
	
	public void devour(){
		state = State.BeingDevoured;
		this.shrinkStar();
	}
	
	private void shrinkStar(){
		time = 0.f;
		
		scale.restart();
		scale.setScale(0.f);
		scale.setDuration(DYING);
		
		alpha.restart();
		alpha.setAlpha(0.f);
		alpha.setDuration(DYING);
		
		this.clearActions();
		
		parallel.reset();
		parallel.addAction(scale);
		parallel.addAction(alpha);
		
		this.addAction(parallel);
	}
	
	public boolean isLiving(){
		return state == State.Living;
	}
	
	public int getAmount(){
		return amount;
	}
	
	@Override
	public void reset(){
		this.clearActions();
		time = 0.f;
		this.setVisible(false);
		state = State.Dead;
		super.reset();
	}
	
}
