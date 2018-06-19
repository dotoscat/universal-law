package dotteri.projectgravity;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.collision.*;

public class GameObject extends Actor {
	Sprite sprite = null;
	Sphere sphere = null;
	Circle circle = null;
	float original_circle_radius = 0.f;
	float original_sphere_radius = 0.f;
	Vector2 position = null;
	protected Vector2 offset = null;
	
	GameObject(){
		sprite = new Sprite();
		sphere = new Sphere(Vector3.Zero, 0.f);
		circle = new Circle();
		position = new Vector2();
		offset = new Vector2();
	}
			
	public void setTextureAndRegion(Texture texture, int x, int y, int width, int height){
		sprite.setTexture(texture);
		sprite.setRegion(x, y, width, height);
	}
	
	@Override
	public void setSize(float width, float height){
		offset.x = width/2.f;
		offset.y = height/2.f;
		sprite.setOrigin(width/2.f, height/2.f);
		sprite.setSize(width, height);
		super.setSize(width, height);
	}
	
	public void setCollisionRadius(float radius){
		sphere.radius = radius;
		original_sphere_radius = radius;
		circle.radius = radius;
		original_circle_radius = radius;
	}
	
	public boolean checkCollisionWith(GameObject gameobject){
		return sphere.overlaps(gameobject.sphere);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		this.update();
		sphere.radius = this.getScaleX() * original_sphere_radius;//or getScaleY()
		circle.radius = this.getScaleX() * original_circle_radius;
	}
	
	public void reset(){
		this.setScale(1.f);
		sphere.radius = original_sphere_radius;
		circle.radius = original_circle_radius;
	}
	
	private void update(){
		sprite.setPosition(position.x-offset.x, position.y-offset.y);
		sphere.center.x = position.x;
		sphere.center.y = position.y;
		circle.x = position.x;
		circle.y = position.y;
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		Color color = this.getColor();
		sprite.setColor(color);
		sprite.draw(batch, color.a * parentAlpha);	
	}
	
	@Override
	public void setPosition(float x, float y){
		this.position.x = x;
		this.position.y = y;
		this.update();
		super.setPosition(x, y);
	}
	
	public void setPosition(Vector2 position){
		this.position.x = position.x;
		this.position.y = position.y;
		this.update();
		super.setPosition(position.x, position.y);
	}
		
	public boolean isInsideRectangle(Rectangle area){
		return Intersector.overlapCircleRectangle(circle, area);
	}
		
	@Override
	public void setScale(float scale){
		super.setScale(scale);
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
	
	public Vector2 getPosition(){
		return this.position;
	}
	
	public void setModel(OrderedMap models, String name, AssetManager assets){
		Util.configureSpriteWithModel(sprite, models, name, assets);
		this.setSize(sprite.getWidth(), sprite.getHeight());
	}
	
	public void setModelWithoutTexture(OrderedMap models, String name){
		Util.configureSpriteWithModelWithoutTexture(sprite, models, name);
		this.setSize(sprite.getWidth(), sprite.getHeight());
	}
	
}
