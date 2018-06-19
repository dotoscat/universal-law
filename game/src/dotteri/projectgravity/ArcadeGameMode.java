package dotteri.projectgravity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ArcadeGameMode extends GameMode {

	private int MAXLEVELS = 10;
	private int level = 1;
	StringBuilder strlevel = null;
	
	float time = 0.f;
	float TIME = 1.f;
	
	int STARS = 10;
	int stars_left = 10;
	
	int seconds = 30;
	Float ms = 0.f;
	int score = 0;
	int chain = 1;
	
	Color bluestar = new Color(0.9f, 0.9f, 1.f, 1.f);
	
	public ArcadeGameMode(MyGame mygame) {
		super(mygame, "arcade");
		strlevel = new StringBuilder();
		this.setCaptionData1("score");
		this.useData2(true);
		this.setCaptionData2("level");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void reset(){
		TIME = 1.f;
		time = 0.f;
		level = 7;
		STARS = 7;
		stars_left = STARS;
		score = 0;
		seconds = 30;
		ms = 0.f;
	}
	
	@Override
	void step(float delta) {
		// TODO Auto-generated method stub		
		this.time += delta;
		ms += delta;
		
		if (ms >= 1.f){
			seconds -= 1;
			ms = 0.f;
		}

		Engine engine = mygame.engine;
		if (time > TIME){
			if (MathUtils.random.nextBoolean() ){//star
				int amount = 7;
				float life = amount;
				if (MathUtils.random(10) < level * 7 / 10 ){
					amount = 9;
					life = amount;
				}
				Star a_star = engine.addStar(amount, bluestar, life-(level*0.7f));
				//10 is the maximum level
				if (a_star != null){
					Rectangle spawn_zone = engine.getSpawnZone();
					float x = Util.getRandomValueBetween((int)spawn_zone.x, (int)spawn_zone.width);
					float y = Util.getRandomValueBetween((int)spawn_zone.y, (int)spawn_zone.height);
					a_star.setPosition(x, y);
					while(engine.occupyGravitationalObjectArea(a_star) ){
						x = Util.getRandomValueBetween((int)spawn_zone.x, (int)spawn_zone.width);
						y = Util.getRandomValueBetween((int)spawn_zone.y, (int)spawn_zone.height);
						a_star.setPosition(x, y);
					}
				}
				
			}
			this.time = 0.f;
		}//if (time > TIME)
		
	}

	@Override
	public void startGame(){
		//Star a_star = engine.addStar(Color.CYAN, 5.f);
		Engine engine = mygame.engine;
		engine.getHUD_left().setVisible(true);
		engine.getHUD_center().setVisible(true);
		engine.getHUD_right().setVisible(true);
		this.reset();
	}
	
	public void levelUp(){
		level += 1;
		if (level > MAXLEVELS){
			level = MAXLEVELS;
		}
		this.updateString();
	}
	
	public void levelDown(){
		level -= 1;
		if (level < 1){
			level = 1;
		}
		this.updateString();
	}
	
	private void updateString(){
		strlevel.setLength(0);
		strlevel.append(level);
	}
	
	public CharSequence getLevelString(){
		return strlevel;
	}

	@Override
	public void starIsDevoured(Star star) {
		// TODO Auto-generated method stub
		score += star.getAmount() * chain;
		seconds += star.getAmount();
		if (seconds > 60){
			score += seconds*chain - 60;
			float x = mygame.engine.getHUD_left().getX();
			float y = mygame.engine.getHUD_left().getY();
			if (chain > 1){
				mygame.engine.addMessage(String.format("x%d bonus +%d", chain, seconds - 60), x, y-32.f, 32.f, 2.f);
			}else{
				mygame.engine.addMessage(String.format("bonus +%d", seconds - 60), x, y-32.f, 32.f, 2.f);
			}
			seconds = 60;
			ms = 0.f;
		}
		Vector2 pos = star.getPosition();
		stars_left -= 1;
		if (stars_left == 0){
			level += 1;
			if (chain > 1){
				mygame.engine.addMessage(String.format("x%d %d level!", chain, level), pos.x, pos.y, 32.f, 2.f);
			}else{
				mygame.engine.addMessage(String.format("%d level!", level), pos.x, pos.y, 32.f, 2.f);
			}
			STARS += level * 7;
			stars_left = STARS;
		}else{
			if (chain > 1){
				mygame.engine.addMessage(String.format("x%d %d stars left", chain, stars_left), pos.x, pos.y, 32.f, 2.f);
			}else{
				mygame.engine.addMessage(String.format("%d stars left", stars_left), pos.x, pos.y, 32.f, 2.f);
			}
		}
		chain += 1;
	}

	@Override
	public void starDead(Star star) {
		// TODO Auto-generated method stub
		Vector2 pos = star.getPosition();
		int amount = star.getAmount();
		if (amount > 7){
			Blackhole a_blackhole = mygame.engine.addBlackhole(pos.x, pos.y, 0.25f, 7.f, 0.25f);
			a_blackhole.setMass(star.getMass());
		}
	}

	@Override
	public void starDevoured(Star star) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void blackholeDie(Blackhole blackhole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateHUD() {
		// TODO Auto-generated method stub
		mygame.engine.getHUD_strbuilder_center().append(seconds);
		mygame.engine.getHUD_strbuilder_left().append(score);
		mygame.engine.getHUD_strbuilder_right().append("level ");
		mygame.engine.getHUD_strbuilder_right().append(level);
	}

	@Override
	boolean isGameOver() {
		// TODO Auto-generated method stub
		return seconds <= 0;
	}

	@Override
	Record thereIsNewRecord() {
		for(int i = 0; i < MAXRECORD; i += 1){
			if (record[i].isData1EqualTo(score)){
				return null;
			}
			else if (record[i].isData1LowerThan(score)){
				this.carryDownScoreList(i);
				return record[i];
			}
		}
		return null;
	}
	
	@Override
	void setDataToRecord(Record record){
		record.setData1(score);
		record.setData2(level);
	}
	
	@Override
	public int getRecord(){
		return score;
	}

	@Override
	public void playerMovesBlackhole(Blackhole blackhole) {
		// TODO Auto-generated method stub
		chain = 1;
	}
	
}
