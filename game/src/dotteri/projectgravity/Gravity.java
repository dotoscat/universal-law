package dotteri.projectgravity;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Gravity {

	public class GVector2 extends Vector2{
		private boolean used = false;
		public boolean isUsed(){
			return used;
		}
		public void setUsed(){
			used = true;
		}
		public void setUnUsed(){
			used = false;
		}
	}

	private static int MAX = 32;
	public static GVector2[] value_list = new GVector2[MAX];
	private static Vector2 tmp = new Vector2();
	
	static public Vector2 applyGravity(float object_mass, float affected_object_mass,
		Vector2 object_position, Vector2 affected_object_position,
		float time, float factor){
		float mm = object_mass*affected_object_mass;
		float distance = object_position.dst(affected_object_position);
		float force = -(mm/distance)*factor;
		Vector2 vforce = Gravity.tmp;
					
		float p = MathUtils.atan2(affected_object_position.y-object_position.y, affected_object_position.x-object_position.x);
			
		vforce.x = force * MathUtils.cos(p);
		vforce.y = force * MathUtils.sin(p);
		
		vforce.mul(time);
		return vforce;
	}
	
	static private void clearPositionList(){
		for(int i = 0; i < Gravity.MAX; i += 1){
			value_list[i].setUnUsed();
		}
	}
	
	static private void addPosition(Vector2 value){
		for(int i = 0; i < Gravity.MAX; i += 1){
			if (value_list[i].isUsed() ){
				continue;
			}
			value_list[i].set(value);
			value_list[i].setUsed();
			break;
		}
	}
	
	/*
	static public void calculateOrbit(GravitationalObject object, GravitationalObject affected_object, float secs, float dt, float factor){
		float times = secs/dt;
		Gravity.clearPositionList();
		Vector2 value = Gravity.tmp;
		for(float i = 0.f; i < times; i += 1.f){
			value = Gravity.applyGravity(object, affected_object, i*dt, factor);
			Gravity.addPosition(Gravity.tmp);
		}
	}
	*/
}
