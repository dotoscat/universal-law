package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class Blackhole extends GravitationalObject{
	
	static Vector2 position = new Vector2();
	static Vector2 velocity = new Vector2();
	
	Sphere area = null;
	ScaleToAction scale = null;

	Sprite accretion_1 = null;
	Sprite accretion_2 = null;
	Sprite accretion_3 = null;
	float accretion_rotation_speed = -90.f;
	boolean accretion_is_visible = true;
	
	enum State{
		Dead,
		BeBorn,
		Live,
		Die,
	}
	State state = State.Dead;
	
	float BEBORN = 0.25f;
	float LIFE = 0.f;
	float DIE = 0.25f;
	
	float time = 0.f;	
	MyGame mygame = null;
	
	public Blackhole(float radius, MyGame mygame){
		scale = new ScaleToAction();
		area = new Sphere(Vector3.Zero, radius);
		this.mygame = mygame;
						
		accretion_1 = new Sprite();
		Util.configureSpriteWithModel(accretion_1, mygame.game_objects_model, "accretion", mygame.assets);
		accretion_1.setOrigin(accretion_1.getWidth()/2.f, accretion_1.getHeight()/2.f);
		accretion_1.setColor(Color.RED);
				
		accretion_2 = new Sprite();
		Util.configureSpriteWithModel(accretion_2, mygame.game_objects_model, "accretion", mygame.assets);
		accretion_2.setOrigin(accretion_2.getWidth()/2.f, accretion_2.getHeight()/2.f);
		accretion_2.setColor(Color.ORANGE);
				
		accretion_3 = new Sprite();
		Util.configureSpriteWithModel(accretion_3, mygame.game_objects_model, "accretion", mygame.assets);
		accretion_3.setOrigin(accretion_3.getWidth()/2.f, accretion_3.getHeight()/2.f);
		accretion_3.setColor(Color.YELLOW);
		
		this.setCollisionRadius(14.f);
		this.setAreaRadius(128.f);
		this.reset();
	}
		
	@Override
	public void act(float delta){
		
		Engine engine = mygame.engine;
		
		if (!engine.isRunning()){
			return;
		}
		
		super.act(delta);
		
		this.time += delta;
		accretion_1.setRotation(accretion_1.getRotation() + accretion_rotation_speed * delta);
		accretion_2.setRotation(accretion_2.getRotation() + (accretion_rotation_speed/2.f) * delta);
		accretion_3.setRotation(accretion_3.getRotation() + (accretion_rotation_speed/4.f) * delta);
		switch(state){
		
		case BeBorn:
			if (time > BEBORN){
				state = State.Live;
				this.time = 0;
			}
			break;
		case Live:
			if (LIFE > 0.f && time > LIFE){
				this.setToDying();
			}
			break;
			case Die:
				if (this.time > DIE){
					this.setVisible(false);
					state = State.Dead;
				}
			break;
		}
		
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		Color color_1 = accretion_1.getColor();
		Color color_2 = accretion_2.getColor();
		Color color_3 = accretion_3.getColor();
				
		if (accretion_is_visible){
			accretion_1.draw(batch, color_1.a * parentAlpha);
			accretion_2.draw(batch, color_2.a * parentAlpha);
			accretion_3.draw(batch, color_3.a * parentAlpha);
		}
		
		super.draw(batch, parentAlpha);
	}
	
	public void set(float x, float y, float beborn_time, float life_time, float die_time){
		BEBORN = beborn_time;
		LIFE = life_time;
		DIE = die_time;
		area.center.x = x;
		area.center.y = y;
		state = State.BeBorn;
		this.setScale(0.f);
		this.clearActions();
		scale.restart();
		scale.setScale(1.f);
		scale.setDuration(BEBORN);
		this.addAction(scale);
		time = 0.f;
		this.setVisible(true);
		this.setPosition(x, y);
		mygame.playSound("appear");
	}
	
	@Override
	public void setPosition(float x, float y){
		accretion_1.setPosition(x - (accretion_1.getWidth() / 2.f), y - (accretion_1.getHeight() / 2.f));
		accretion_2.setPosition(x - (accretion_2.getWidth() / 2.f), y - (accretion_2.getHeight() / 2.f));
		accretion_3.setPosition(x - (accretion_3.getWidth() / 2.f), y - (accretion_3.getHeight() / 2.f));
		super.setPosition(x, y);
	}
	
	public boolean isLiving(){
		return state == State.Live;
	}
	
	public boolean isDead(){
		return state == State.Dead;
	}
	
	public void setToDying(){
		state = State.Die;
		this.clearActions();
		scale.setDuration(DIE);
		scale.setScale(0.f);
		this.addAction(scale);
		this.time = 0.f;
	}
	
	public void setToDying(float DIE){
		this.DIE = DIE;
		this.setToDying();
	}
	
	@Override
	public void reset(){
		state = State.Dead;
		this.setVisible(false);
		this.time = 0.f;
		this.clearActions();
		super.reset();
	}
	
	public void setAccretionDisk(boolean set){
		accretion_is_visible = set;
	}
	
	@Override
	public void setAreaRadius(float radius){
		super.setAreaRadius(radius);
		
		accretion_1.setSize(radius*2.f, radius*2.f);
		accretion_1.setOrigin(accretion_1.getWidth()/2.f, accretion_1.getHeight()/2.f);
		Vector2 pos = this.getPosition();
		accretion_1.setPosition(pos.x - (accretion_1.getWidth() / 2.f), pos.y - (accretion_1.getHeight() / 2.f));
		
		accretion_2.setSize(radius*2.f, radius*2.f);
		accretion_2.setOrigin(accretion_2.getWidth()/2.f, accretion_2.getHeight()/2.f);
		pos = this.getPosition();
		accretion_2.setPosition(pos.x - (accretion_2.getWidth() / 2.f), pos.y - (accretion_2.getHeight() / 2.f));
		
		accretion_3.setSize(radius*2.f, radius*2.f);
		accretion_3.setOrigin(accretion_3.getWidth()/2.f, accretion_3.getHeight()/2.f);
		pos = this.getPosition();
		accretion_3.setPosition(pos.x - (accretion_3.getWidth() / 2.f), pos.y - (accretion_3.getHeight() / 2.f));
		
	}
	
	@Override
	public void setScale(float scaleX, float scaleY){
		super.setScale(scaleX, scaleY);
		accretion_1.setScale(scaleX, scaleY);
		accretion_2.setScale(scaleX, scaleY);
		accretion_3.setScale(scaleX, scaleY);
	}
	
	@Override
	public void setScale(float scale){
		super.setScale(scale);
		accretion_1.setScale(scale);
		accretion_2.setScale(scale);
		accretion_3.setScale(scale);
	}
	
}
