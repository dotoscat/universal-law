package dotteri.projectgravity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Engine extends MyGameScreen {

	class EventListener extends com.badlogic.gdx.scenes.scene2d.InputListener{
		
		Engine engine = null;
		
		public EventListener(Engine engine){
			this.engine = engine;
		}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			if (!engine.isRunning()){
				return true;
			}
			//System.out.format("Touch: %g, %g, %d, %d\n", x, y, pointer, button);
			current_player_blackhole.setToDying();
			if(i_player_blackhole == 0){
				i_player_blackhole = 1;
			}
			else if(i_player_blackhole == 1){
				i_player_blackhole = 0;
			}
			current_player_blackhole = player_blackhole[i_player_blackhole];
			current_player_blackhole.set(x, y, 0.25f, 0.f, 0.25f);
			engine.getGameMode().playerMovesBlackhole(current_player_blackhole);
			return true;
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer){
			if (!engine.isRunning()){
				return;
			}
			current_player_blackhole.setPosition(x, y);
			//engine.getGameMode().playerMovesBlackhole(player_blackhole);
			//just dragged, move is touch at the screen to place the player blackhole
		}
		
	}
	
	class PauseEngine extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.engine.pauseEngine();
			mygame.playSound("press");
		}	
	}
	
	class ContinueEngine extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.engine.continueEngine();
			mygame.playSound("press");
		}	
	}
	
	class ResetEngine extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.engine.reset();
			mygame.playSound("press");
		}	
	}
	
	class ExitEngine extends ClickListener{
		
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.setScreenMainMenu();
			mygame.playSound("press");
		}
	}
	
	class EnterName implements TextInputListener{

		Engine engine = null;
		GameMode.Record record = null;
				
		public EnterName(Engine engine){
			this.engine = engine;
		}
		
		public void setRecord(GameMode.Record record){
			this.record = record;
		}
		
		@Override
		public void input(String text) {
			// TODO Auto-generated method stub
			record.setName(text);
			engine.getGameMode().setDataToRecord(record);
			gamemode.save();//this is for demo version
			engine.gotoShowRecord();
		}

		@Override
		public void canceled() {
			// TODO Auto-generated method stub
			engine.gotoShowRecord();
		}
		
	}
	
	class GoToPlayerChooses extends ClickListener{
				
		@Override
		public void clicked(InputEvent event, float x, float y){
			mygame.engine.gotoPlayerChooses();
			mygame.playSound("press");
		}	
	}
	
	enum State{
		Running,
		Pause,
		Ready,
		GameOver,
		InsertNewRecord,
		ShowRecord,
		PlayerChooses
	};
	
	State state = State.Ready;
	
	private GameMode gamemode = null;
		
	NeutronStar collector = null;
	Blackhole player_blackhole[] = null;
	Blackhole current_player_blackhole = null;
	int i_player_blackhole = 0;
	
	final static int STARS = 4;
	Star[] star = null;
	
	final static int BLACKHOLE = 4;
	Blackhole[] blackhole = null;
	
	final static int NOVA = 8;
	Nova[] nova = null;
	
	final static int SHOCKWAVE = 8;
	ShockWave[] shockwave = null;
		
	final static int MESSAGE = 16;
	Message[] message = null;
	
	Rectangle level_area = null;
	Rectangle orb_spawn_zone = null;
		
	StringBuilder string_buffer = null;
		
	Label HUD_left = null;
	StringBuilder HUD_strbuilder_left = null;
	Label HUD_center = null;
	StringBuilder HUD_strbuilder_center = null;
	Label HUD_right = null;
	StringBuilder HUD_strbuilder_right = null;
	
	Label label_points = null;
	Label label_time = null;
	
	Group pause_options = null;
	Button pause_button = null;
	
	Group player_chooses = null;
	Button player_chooses_try_button = null;
	Button player_chooses_go_to_main_menu_button = null;
	
	ShowRecordList record_list = null;
	Button record_list_ok = null;
	
	Advert announce = null;
	
	static final float FACTOR = 10000.f;
	
	EnterName enter_name = null;
	
	float collector_out = 0.f;
	float COLLECTOR_OUT = 1.f;
	
	@Override
	public void create(MyGame mygame){
		super.create(mygame);
		
		string_buffer = new StringBuilder();
		
		level_area = new Rectangle(0.f, 0.f, 960.f, 640.f);
		orb_spawn_zone = new Rectangle();
		orb_spawn_zone.x = 64.f;
		orb_spawn_zone.y = 64.f;
		orb_spawn_zone.width = 960.f-64.f;
		orb_spawn_zone.height = 640.f-64.f;
				
		//Actor click_area
		Actor click_area = new Actor();
		click_area.setBounds(0.f, 0.f, mygame.FWIDTH, mygame.FHEIGHT);
		click_area.setSize(mygame.FWIDTH, mygame.FHEIGHT);
		click_area.addCaptureListener(new EventListener(this));
		stage.addActor(click_area);
		//
		
		player_blackhole = new Blackhole[2];
		
		player_blackhole[0] = new Blackhole(64.f, mygame);
		player_blackhole[0].setModel(mygame.game_objects_model, "gravitational_pit", mygame.assets);
		player_blackhole[0].setMass(2.0f);
		player_blackhole[0].setAccretionDisk(false);
		
		player_blackhole[1] = new Blackhole(64.f, mygame);
		player_blackhole[1].setModel(mygame.game_objects_model, "gravitational_pit", mygame.assets);
		player_blackhole[1].setMass(2.0f);
		player_blackhole[1].setAccretionDisk(false);
		
		current_player_blackhole = player_blackhole[i_player_blackhole];
		
		collector = new NeutronStar(this);
		collector.setModel(mygame.game_objects_model, "collector", mygame.assets);
		collector.setSize(16, 16);
		collector.setPosition(mygame.FWIDTH/2.f, mygame.FHEIGHT/2.f);
		collector.setMass(1.f);

		stage.addActor(collector);
		stage.addActor(player_blackhole[0]);
		stage.addActor(player_blackhole[1]);
		
		nova = new Nova[NOVA];
		for(int i = 0; i < NOVA; i += 1){
			nova[i] = new Nova(this);
			nova[i].setSprite(mygame.game_objects_model, "nova", mygame.assets);
			stage.addActor(nova[i]);
		}
		
		star = new Star[STARS];
		for(int i = 0; i < STARS; i += 1){
			star[i] = new Star(this);
			star[i].setModel(mygame.game_objects_model, "star", mygame.assets);
			stage.addActor(star[i]);
			star[i].setMass(0.25f);
		}
		
		blackhole = new Blackhole[BLACKHOLE];
		for(int i = 0; i < BLACKHOLE; i += 1){
			blackhole[i] = new Blackhole(8, mygame);
			blackhole[i].setModel(mygame.game_objects_model, "gravitational_pit", mygame.assets);
			stage.addActor(blackhole[i]);
		}
		
		shockwave = new ShockWave[SHOCKWAVE];
		for(int i = 0; i < SHOCKWAVE; i += 1){
			shockwave[i] = new ShockWave(this);
			shockwave[i].setSprite(mygame.game_objects_model, "shock_wave", mygame.assets);
			stage.addActor(shockwave[i]);
		}
			
		message = new Message[MESSAGE];
		for(int i = 0; i < MESSAGE; i += 1){
			message[i] = new Message("", GuiElements.getMessageStyle() );
			stage.addActor(message[i]);
		}
				
		//HUD
		Label.LabelStyle hud_style = new Label.LabelStyle();
		hud_style.font = mygame.font32;
		hud_style.fontColor = Color.CYAN;
				
		HUD_left = new Label("", hud_style);
		HUD_left.setPosition(8.f, mygame.FHEIGHT - HUD_left.getHeight() - 16.f);
		stage.addActor(HUD_left);
		HUD_center = new Label("", hud_style);
		HUD_center.setY( mygame.FHEIGHT - HUD_center.getHeight() - 16.f);
		stage.addActor(HUD_center);
		HUD_right = new Label("", hud_style);
		HUD_right.setY( mygame.FHEIGHT - HUD_right.getHeight() - 16.f);
		stage.addActor(HUD_right);
		
		HUD_strbuilder_left = new StringBuilder();
		HUD_strbuilder_center = new StringBuilder();
		HUD_strbuilder_right = new StringBuilder();
		
		//pause management
		pause_button = new Button(GuiElements.getPauseButton() );
		pause_button.addCaptureListener( new PauseEngine() );
		pause_button.setPosition((mygame.FWIDTH - pause_button.getWidth()) / 2.f, 4.f);
		stage.addActor(pause_button);
		
		pause_options = new Group();
		stage.addActor(pause_options);
		pause_options.setVisible(false);
		
		TextButton options_continue = new TextButton("Continue", GuiElements.getTextButtonStyle() );
		options_continue.setPosition((mygame.FWIDTH - options_continue.getWidth()) / 2.f, 300.f);
		options_continue.addCaptureListener( new ContinueEngine() );
		pause_options.addActor(options_continue);
		
		TextButton options_options = new TextButton("Options", GuiElements.getTextButtonStyle() );
		options_options.setPosition(options_continue.getX(), 250.f);
		options_options.addCaptureListener( GuiElements.getGoToOptions_action() );
		pause_options.addActor(options_options);
		
		TextButton options_reset = new TextButton("Reset", GuiElements.getTextButtonStyle() );
		options_reset.setPosition(options_continue.getX(), 200.f);
		options_reset.addCaptureListener( new ResetEngine() );
		pause_options.addActor(options_reset);
		
		TextButton options_exit = new TextButton("Exit to Main Menu", GuiElements.getTextButtonStyle() );
		options_exit.setPosition((mygame.FWIDTH - options_exit.getWidth()) / 2.f, 150.f);
		options_exit.addCaptureListener( new ExitEngine() );
		pause_options.addActor(options_exit);
				
		//Engine announce
		announce = new Advert("", GuiElements.getAnnounceStyle(), mygame.FWIDTH, mygame.FHEIGHT );
		stage.addActor(announce);

		//PlayerChooses menu
		player_chooses = new Group();
		stage.addActor(player_chooses);
		player_chooses.setVisible(false);
		
		player_chooses_try_button = new TextButton("Try again", GuiElements.getTextButtonStyle());
		player_chooses_try_button.setPosition
		((mygame.FWIDTH - player_chooses_try_button.getWidth()) / 2.f,
		(mygame.FHEIGHT - player_chooses_try_button.getHeight()) / 2.f);
		player_chooses_try_button.addCaptureListener(new ResetEngine());
		player_chooses.addActor(player_chooses_try_button);
		
		player_chooses_go_to_main_menu_button = new TextButton("Exit", GuiElements.getTextButtonStyle());
		player_chooses_go_to_main_menu_button.setPosition
		((mygame.FWIDTH - player_chooses_go_to_main_menu_button.getWidth()) / 2.f,
		(mygame.FHEIGHT - player_chooses_go_to_main_menu_button.getHeight()) / 2.f
		+ -player_chooses_try_button.getHeight() + -32.f);
		player_chooses_go_to_main_menu_button.addCaptureListener(new ExitEngine());
		player_chooses.addActor(player_chooses_go_to_main_menu_button);
		
		//Show score list		
		record_list = new ShowRecordList(10, mygame);
		record_list.setPosition(32.f, 32.f);
		record_list_ok = new TextButton("Ok", GuiElements.getTextButtonStyle());
		record_list_ok.addCaptureListener(new GoToPlayerChooses());
		record_list.addActor(record_list_ok);
		record_list_ok.setPosition(record_list.getX() - 32.f, -8.f);
		
		stage.addActor(record_list);
		record_list.setVisible(false);
		
		//---
		
		enter_name = new EnterName(this);
		
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		if (state == State.Ready && announce.isOver() ){
			mygame.playMusic("theme1");
			state = State.Running;
			announce.setVisible(false);
			this.enablePauseButton();
		}
		
		if (state == State.GameOver && announce.isOver()){
			mygame.playMusic("wind2");
			GameMode.Record new_record = gamemode.thereIsNewRecord();
			if (new_record != null){
				state = State.InsertNewRecord;
				enter_name.setRecord(new_record);
				Gdx.input.getTextInput(enter_name, "New record! " + gamemode.getRecord(), "Me");
			}else{
				state = State.PlayerChooses;
				player_chooses.setVisible(true);
			}
		}
		
		if (state == State.InsertNewRecord){
			//implement InsertNewRecord
		}
		
		
		if (state == State.Running){

		//Check if it's GameOver
		if (collector.isLiving() && !collector.isInsideRectangle(level_area) ){
			collector_out += delta;
		}else{
			collector_out = 0.f;
		}
			
		if ((collector.isLiving() && collector_out > COLLECTOR_OUT)
			|| gamemode.isGameOver() || collector.isDissapeared() ){
			state = State.GameOver;
			mygame.stopCurrentMusic();
			mygame.playSound("wind");
			this.disablePauseButton();
			this.setAnnounce_GameOver();
		}
		//---
		
		for(int i = 0; i < STARS; i += 1){
			if (star[i].isLiving() && collector.isLiving() && collector.checkCollisionWith(star[i]) ){
				Star star = this.star[i];
				if (gamemode != null){
					gamemode.starIsDevoured(star);
					Vector2 pos = collector.getPosition();
					this.addNova(pos.x, pos.y, 0.5f, collector);
					star.devour();
				}
			}
			else if(star[i].isLiving() && current_player_blackhole.isLiving() && current_player_blackhole.checkCollisionWith(star[i])){
				star[i].devour();
				this.addMassToPlayerBlackhole(star[i].getMass() );
				Vector2 pos = collector.getPosition();
				this.addNova(pos.x, pos.y, 0.5f, current_player_blackhole);
			}
		}
		
		for(int i = 0; i < BLACKHOLE; i += 1){
			if (!blackhole[i].isDead() && current_player_blackhole.checkCollisionWith(blackhole[i])){
				blackhole[i].setToDying(0.07f);
				//player_blackhole.setMass(blackhole[i].getMass() + player_blackhole.getMass());
			}
			else if (blackhole[i].isLiving() && blackhole[i].isInsideAreaRadius(collector)){
				collector.applyGravityWith(blackhole[i], delta, FACTOR);
				if (blackhole[i].checkCollisionWith(collector)){
					collector.setToDisappearing();
					collector.setBlackhole(blackhole[i]);
				}
			}
		}
		
		if(!current_player_blackhole.isDead() ){
			collector.applyGravityWith(current_player_blackhole, delta, FACTOR);
		}
		
		if(collector.isLiving() && current_player_blackhole.isLiving() && collector.checkCollisionWith(current_player_blackhole)){
			collector.setToDisappearing();
			collector.setBlackhole(current_player_blackhole);
		}
		
		if(gamemode != null){
			gamemode.step(delta);
		}
		
		}//if (state == State.Running)
		
		HUD_strbuilder_left.setLength(0);
		HUD_strbuilder_center.setLength(0);
		HUD_strbuilder_right.setLength(0);
		gamemode.updateHUD();
		HUD_left.setText(HUD_strbuilder_left);
		
		HUD_center.setText(HUD_strbuilder_center);
		HUD_center.setX( (mygame.FWIDTH - HUD_center.getTextBounds().width ) / 2.f );
		
		HUD_right.setText(HUD_strbuilder_right);
		
		HUD_right.setX( (mygame.FWIDTH - HUD_right.getTextBounds().width) - 8.f);
		
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
	
	public Blackhole addBlackhole(float x, float y, float beborn, float life, float die){
		Blackhole a_blackhole = null;
		for(int i = 0; i < BLACKHOLE; i += 1){
			if(blackhole[i].isDead()){
				a_blackhole = blackhole[i];
			}
		}
		if (a_blackhole != null){
			a_blackhole.set(x, y, beborn, life, die);
		}
		return a_blackhole;
	}
	
	public Star addStar(int amount, Color color, float lifespan){
		Star a_star = null;
		for(int i = 0; i < STARS; i += 1){
			if (star[i].isDead()){
				a_star = star[i];
				break;
			}
		}
		if (a_star != null){
			a_star.set(amount, color, lifespan);
		}
		return a_star;
	}
	
	public Rectangle getSpawnZone(){
		return orb_spawn_zone;
	}
				
	public boolean isGameOver(){
		return state == State.GameOver;
	}
	
	public boolean isRunning(){
		return state == State.Running;
	}
	
	public boolean isPaused(){
		return state == State.Pause;
	}
	
	public void resetCollectorPosition(){
		collector.setPosition(mygame.FWIDTH/2.f, mygame.FHEIGHT/2.f);
	}
	
	public void cleanStars(){
		for(int i = 0; i < STARS; i += 1){
			star[i].reset();
		}
	}
	
	public void cleanBlackholes(){
		for(int i = 0; i < BLACKHOLE; i += 1){
			blackhole[i].reset();
		}
	}
	
	public void cleanMessages(){
		for(int i = 0; i < MESSAGE; i += 1){
			message[i].reset();
		}
	}
		
	public void setGameMode(GameMode mode){
		this.gamemode = mode;
	}
	
	public GameMode getGameMode(){
		return gamemode;
	}
	
	public void reset(){
		if(gamemode != null){
			gamemode.reset();
		}
		this.resetCollectorPosition();
		this.cleanStars();
		this.cleanBlackholes();
		this.cleanMessages();
		this.cleanEffects();
		player_blackhole[0].setMass(2.0f);
		player_blackhole[0].reset();
		player_blackhole[1].setMass(2.0f);
		player_blackhole[1].reset();
		collector.reset();//reset status
		collector.resetVelocity();
		state = State.Ready;
		this.setAnnounce("READY", 1.f, 2.f, 1.f, 0.f, 1.f);
		mygame.stopCurrentMusic();
		mygame.playSound("voice");
		pause_button.setVisible(false);
		pause_options.setVisible(false);
		player_chooses.setVisible(false);
	}
	
	public void pauseEngine(){
		state = State.Pause;
		this.disablePauseButton();
		pause_options.setVisible(true);
	}
	
	public void disablePauseButton(){
		pause_button.setDisabled(true);
		pause_button.setVisible(false);
	}
	
	public void enablePauseButton(){
		pause_button.setDisabled(false);
		pause_button.setVisible(true);
	}
	
	public void continueEngine(){
		state = State.Running;
		this.enablePauseButton();
		pause_options.setVisible(false);
		player_chooses.setVisible(false);
	}
		
	public void addBonusMessage(int points, float x, float y, float velocity_y, float life){
		string_buffer.setLength(0);
		string_buffer.append("BONUS ");
		string_buffer.append(points);
		this.addMessage(string_buffer.toString(), x, y, velocity_y, life);
	}
	
	public void addGetAmountMessage(int points, float x, float y, float velocity_y, float life){
		string_buffer.setLength(0);
		string_buffer.append("+");
		string_buffer.append(points);
		this.addMessage(string_buffer.toString(), x, y, velocity_y, life);
	}
	
	public void setAnnounce(String content, float initial_scale, float final_scale, float initial_alpha, float final_alpha, float duration){
		announce.setText(content);
		TextBounds bounds = announce.getTextBounds();
		announce.setPosition((mygame.FWIDTH - bounds.width)/2.f, 
				(mygame.FHEIGHT - bounds.height)/2.f);
		announce.set(initial_scale, final_scale, initial_alpha, final_alpha, duration);
	}
	
	public void setAnnounce_GameOver(){
		this.setAnnounce("GAME OVER", 2.f, 1.f, 0.f, 1.f, 1.f);
	}
	
	public void addMessage(String content, float x, float y, float velocity_y, float life){
		for(int i = 0; i < MESSAGE; i += 1){
			if (message[i].isUsed()){
				continue;
			}
			
			Message a_message = message[i];
			a_message.setText(content);
			a_message.setPosition(x, y);
			a_message.set(velocity_y, life);
			
			break;
		}
	}
	
	public Label getHUD_left(){
		return HUD_left;
	}
	
	public StringBuilder getHUD_strbuilder_left(){
		return HUD_strbuilder_left;
	}
	
	public Label getHUD_center(){
		return HUD_center;
	}
	
	public StringBuilder getHUD_strbuilder_center(){
		return HUD_strbuilder_center;
	}
	
	public Label getHUD_right(){
		return HUD_right;
	}
	
	public StringBuilder getHUD_strbuilder_right(){
		return HUD_strbuilder_right;
	}
	
	public void addNova(float x, float y, float lifespan, GameObject source){
		for(int i = 0; i < NOVA; i += 1){
			if (nova[i].isUsed()){
				continue;
			}
			nova[i].set(x, y, lifespan);
			nova[i].setSource(source);
			break;
		}
	}
	
	public void cleanEffects(){
		for(int i = 0; i < NOVA; i += 1){
			nova[i].reset();
		}
		for(int i = 0; i < SHOCKWAVE; i += 1){
			shockwave[i].reset();
		}
	}
	
	public void addShockWave(float x, float y, float lifespan){
		for(int i = 0; i < SHOCKWAVE; i += 1){
			if(shockwave[i].isUsed()){
				continue;
			}
			shockwave[i].set(x, y, lifespan);
			break;
		}
	}
	
	public boolean occupyGravitationalObjectArea(GravitationalObject object){
		/*
		 * Star
		 * Blackholes
		 * Player blackhole
		 * Collector/whitedwarf
		 * Skip object if are the same object to check out
		 */
		
		if (object != collector && object.isInsideAreaRadius(collector)){
			//System.out.println("object collides with collector");
			return true;
		}
		
		if (object != current_player_blackhole && current_player_blackhole.isLiving() && object.isInsideAreaRadius(current_player_blackhole)){
			//System.out.println("object collides with player blackhole");
			return true;
		}
		
		for (int i = 0; i < STARS; i += 1){
			if (object != star[i] && !star[i].isDead() && object.isInsideAreaRadius(star[i])){
				//System.out.println("object collides with a star" + Integer.toString(i));
				return true;
			}
		}
		
		for (int i = 0; i < BLACKHOLE; i += 1){
			if (object != blackhole[i] && !blackhole[i].isDead() && object.isInsideAreaRadius(blackhole[i])){
				//System.out.println("object collides with a blackhole" + Integer.toString(i));
				return true;
			}
		}
		return false;
	}
	
	public void gotoShowRecord(){
		state = State.ShowRecord;
		record_list.loadFrom(gamemode);
		record_list.setVisible(true);
	}
	
	public void gotoPlayerChooses(){
		state = State.PlayerChooses;
		record_list.setVisible(false);
		player_chooses.setVisible(true);
	}
	
	public Blackhole getPlayerBlackhole(){
		return current_player_blackhole;
	}
	
	public void addMassToPlayerBlackhole(float mass){
		player_blackhole[0].setMass(player_blackhole[0].getMass() + mass);
		player_blackhole[1].setMass(player_blackhole[1].getMass() + mass);
	}
	
}
