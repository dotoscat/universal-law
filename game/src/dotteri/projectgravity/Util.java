package dotteri.projectgravity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.OrderedMap;

public class Util {

	static public void configureSpriteWithModel(Sprite sprite, OrderedMap models, String name, AssetManager assets){
		OrderedMap model = (OrderedMap)models.get(name);
		String str_texture = (String)model.get("texture");
		Float f_x = (Float)model.get("x");
		Float f_y = (Float)model.get("y");
		Float f_width = (Float)model.get("width");
		Float f_height = (Float)model.get("height");
		sprite.setTexture((Texture) assets.get(str_texture));
		sprite.setRegion(f_x.intValue(), f_y.intValue(), f_width.intValue(), f_height.intValue());
		Float f_size_width = (Float)model.get("size_width");
		Float f_size_height = (Float)model.get("size_height");
		if (f_size_width != null && f_size_height != null){
			sprite.setSize(f_size_width, f_size_height);
		}else{
			sprite.setSize(f_width, f_height);
		}
	}
	
	static public void configureSpriteWithModelWithoutTexture(Sprite sprite, OrderedMap models, String name){
		OrderedMap model = (OrderedMap)models.get(name);
		Float f_x = (Float)model.get("x");
		Float f_y = (Float)model.get("y");
		Float f_width = (Float)model.get("width");
		Float f_height = (Float)model.get("height");
		sprite.setRegion(f_x.intValue(), f_y.intValue(), f_width.intValue(), f_height.intValue() );
		Float f_size_width = (Float)model.get("size_width");
		Float f_size_height = (Float)model.get("size_height");
		if (f_size_width != null && f_size_height != null){
			sprite.setSize(f_size_width, f_size_height);
		}else{
			sprite.setSize(f_width, f_height);
		}
	}
	
	static public void configureTextureRegion(TextureRegion region, OrderedMap models, String name, AssetManager assets){
		OrderedMap model = (OrderedMap)models.get(name);
		String str_texture = (String)model.get("texture");
		Float f_x = (Float)model.get("x");
		Float f_y = (Float)model.get("y");
		Float f_width = (Float)model.get("width");
		Float f_height = (Float)model.get("height");
		region.setTexture((Texture) assets.get(str_texture));
		region.setRegion(f_x.intValue(), f_y.intValue(), f_width.intValue(), f_height.intValue());
	}
	
	static public int getRandomValueBetween(int start, int end){
		int value = MathUtils.random.nextInt() % (end+1);
		while(value < start){
			value = MathUtils.random.nextInt() % (end+1);
		}
		return value;
	}
	
}
