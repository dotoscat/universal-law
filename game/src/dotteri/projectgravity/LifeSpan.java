package dotteri.projectgravity;

public class LifeSpan {

	float time = 0.f;
	float max_time = 0.f;
	
	public void add(float delta){
		time += delta;
	}
	
	public void setMaxTime(float max_time){
		this.max_time = max_time;
	}
	
	public boolean isOver(){
		return time > max_time;
	}
	
	public void reset(){
		time = 0.f;
	}
	
}
