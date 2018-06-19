package dotteri.projectgravity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Trail extends Actor {
	
	Sprite sprite = null;
	Vector2 position = null;
	
	public Trail(MyGame mygame){
		sprite = new Sprite();
		Util.configureSpriteWithModel(sprite, mygame.game_objects_model, "trail", mygame.assets);
		sprite.setSize(8.f, 8.f);
		sprite.setOrigin(4.f, 4.f);
		this.setVisible(false);
		sprite.setColor(1.f, 1.f, 1.f, 0.5f);
		position = new Vector2();
	}
		
	@Override
	public void setPosition(float x, float y){
		position.x = x;
		position.y = y;
		sprite.setPosition(x-4.f, y-4.f);
		super.setPosition(x-4.f, y-4.f);
	}
	
	public Vector2 getPosition(){
		return position;
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		//System.out.println(parentAlpha);
		sprite.draw(batch, parentAlpha);
	}
	
	public void setExist(boolean exists){
		this.setVisible(exists);
	}
	
	public boolean exists(){
		return this.isVisible();
	}
	
}
