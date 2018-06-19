package dotteri.projectgravity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;

public class GravitationalObject extends GameObject {

	protected float mass;
	Vector2 velocity = null;
	Sphere area_radius = null;
	float original_area_radius = 0.f;
	
	public GravitationalObject(){
		velocity = new Vector2(Vector2.Zero);
		area_radius = new Sphere(Vector3.Zero, 0.f);
	}
	
	public void setMass(float mass){
		this.mass = mass;
	}
	
	public float getMass(){
		return mass;
	}
	
	public Vector2 applyGravityWith(GravitationalObject object, float time, float factor){
		Vector2 force = Gravity.applyGravity(object.mass, this.mass,
				object.getPosition(), this.getPosition(), time, factor);
		velocity.add(force);
		return force;
	}
	
	@Override
	public void act(float delta){
		position.x += velocity.x * delta;
		position.y += velocity.y * delta;
		super.act(delta);
		area_radius.center.x = position.x;
		area_radius.center.y = position.y;
		area_radius.radius = this.getScaleX() * original_area_radius;//this.getScaleY()
	}
	
	@Override
	public void reset(){
		super.reset();
		area_radius.radius = original_area_radius;
		this.resetVelocity();
	}
	
	public void setAreaRadius(float radius){
		area_radius.radius = radius;
		original_area_radius = radius;
	}
	
	public boolean isInsideAreaRadius(GravitationalObject object){
		//System.out.println
		//("x: " + Float.toString(area_radius.center.x) + " ;y: " + Float.toString(area_radius.center.y) + " ;radius: " + Float.toString(area_radius.radius));
		return area_radius.overlaps(object.area_radius);
	}
	
	public void resetVelocity(){
		velocity.x = 0.f;
		velocity.y = 0.f;
	}
	
	public void setVelocity(float x, float y){
		velocity.x = x;
		velocity.y = y;
	}
	
	public Vector2 getVelocity(){
		return velocity;
	}
	
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		area_radius.center.x = x;
		area_radius.center.y = y;
	}
	
	@Override
	public void setPosition(Vector2 position){
		super.setPosition(position.x, position.y);
		area_radius.center.x = position.x;
		area_radius.center.y = position.y;
	}
	
}
