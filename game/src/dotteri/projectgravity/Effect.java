package dotteri.projectgravity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.OrderedMap;

public abstract class Effect extends Actor {
	Sprite sprite = null;
	Vector2 position = null;
	Vector2 offset = null;
	
	float time = 1.f;
	float duration = 0.f;
	
	protected Engine engine = null;
	
	Effect(){
		sprite = new Sprite();
		position = new Vector2();
		offset = new Vector2();
		this.setVisible(false);
	}
		
	public void setSprite(OrderedMap models, String name, AssetManager assets){
		Util.configureSpriteWithModel(sprite, models, name, assets);
		offset.x = sprite.getWidth() / 2.f;
		offset.y = sprite.getHeight() / 2.f;
		sprite.setOrigin(offset.x, offset.y);
	}
	
	@Override
	public void setPosition(float x, float y){
		position.x = x;
		position.y = y;
		super.setPosition(x, y);
		this.update();
	}
	
	public void setPosition(Vector2 position){
		this.position.set(position);
		super.setPosition(position.x, position.y);
		this.update();
	}
	
	@Override
	public void setScale(float scale){
		sprite.setScale(scale);
	}
	
	@Override
	public void setScale(float scaleX, float scaleY){
		super.setScale(scaleX, scaleY);
		sprite.setScale(scaleX, scaleY);
	}

	@Override
	public float getScaleX(){
		return sprite.getScaleX();
	}
	
	@Override
	public float getScaleY(){
		return sprite.getScaleY();
	}
	
	protected void update(){
		sprite.setPosition(position.x-offset.x, position.y-offset.y);
	}
	
	@Override
	public void act(float delta){
		if (!this.isUsed() || (engine != null && engine.isPaused()) ){
			return;
		}
		time += delta;
		//use negative number on setDuration method to omit the next fork
		if (/*duration >= 0.f && */time >= duration){
			this.setVisible(false);
		}
		super.act(delta);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		Color color = this.getColor();
		sprite.setColor(color);
		sprite.draw(batch, color.a * parentAlpha);	
	}
	
	public void reset(){
		time = 1.f;
		duration = 0.f;
		this.clearActions();
		this.setVisible(false);
	}
	
	protected void resetTime(){
		time = 0.f;
	}
	
	protected void setDuration(float duration){
		this.duration = duration;
		//if duration -1 then is an endless effect
	}
	
	public boolean isUsed(){
		return time < duration;
	}
	
	public abstract void set(float x, float y, float duration);
	
}
