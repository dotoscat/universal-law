package dotteri.projectgravity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.ObjectMap.Keys;

public class MyGame extends Game {
	public AssetManager assets = null;
	public OrthographicCamera camera = null;
	public SpriteBatch batch = null;
		
	public float FWIDTH = 960.f;
	public float FHEIGHT = 640.f;
	public int IWIDTH = 960;
	public int IHEIGHT = 640;
	
	public String VERSION = "09122013";
	public int SAVE_VERSION = 2;
	/*
	 * SAVE_VERSION 1
	 * sound
	 * music
	 * 
	 * SAVE_VERSION 2
	 * sound
	 * music
	 * screen factor (Desktop only)
	 * 
	 */
	public String AUTHOR = "Oscar Triano Garcia (.teri/dotteri)";
	public String TITLE = "Universal Law";
	
	public Engine engine = null;
	public MainMenu mainmenu = null;
	public OptionMenu optionmenu = null;
	public ShowRecords showrecords = null;
	public Credits credits = null;
	
	private MyGameScreen last_screen = null;
	private MyGameScreen current_screen = null;
	
	public OrderedMap game_objects_model = null;
	public OrderedMap gui_objects_model = null;
	
	public com.badlogic.gdx.graphics.g2d.BitmapFont font32 = null;
	public com.badlogic.gdx.graphics.g2d.BitmapFont font24 = null;
	public com.badlogic.gdx.graphics.g2d.BitmapFont font16 = null;
	public com.badlogic.gdx.graphics.g2d.BitmapFont font64 = null;
	public com.badlogic.gdx.graphics.g2d.BitmapFont font8 = null;
	//public com.badlogic.gdx.graphics.g2d.BitmapFont font16_star = null;
	
	private OrderedMap NASA_backgrounds = null;
	private Texture background = null;
	private String background_file = null;
	
	public ArcadeGameMode arcade_gamemode = null;
	
	int sound = 5;
	int music = 5;
	float screen_factor = 1.f;
	
	OrderedMap<String, Sound> sounds;
	OrderedMap<String, Music> musics;
	Music current_music = null;
		
	@Override
	public void create(){
		camera = new OrthographicCamera(960.f, 640.f);
		batch = new SpriteBatch();
		batch.setShader( SpriteBatch.createDefaultShader() );
		camera.translate(960.f/2.f, 640.f/2.f);
		assets = new AssetManager();
		this.setLoadingScreen();
		this.loadData();
	}
	
	public void finishLoading() {		
		FileHandle file_font = Gdx.files.internal("data/DigitalDream.ttf");
		FreeTypeFontGenerator font_DigitalDream = new FreeTypeFontGenerator
				(file_font);

		//FreeTypeFontGenerator font_gravitat = new FreeTypeFontGenerator
				//(Gdx.files.internal("data/gravitat.ttf") );
		
		font32 = font_DigitalDream.generateFont(32);
		font24 = font_DigitalDream.generateFont(24);
		font64 = font_DigitalDream.generateFont(64);
		//font16_star = font_gravitat.generateFont(16);
		
		font_DigitalDream.dispose();
		
		//font_gravitat.dispose();
		
		JsonReader jsonreader = new JsonReader();
		
		try{
			game_objects_model = (OrderedMap) jsonreader.parse(Gdx.files.internal("data/game_objects_model.json") );
		}catch (com.badlogic.gdx.utils.SerializationException parse_error){
			System.out.println(parse_error.getMessage() );
		}
		try{
			gui_objects_model = (OrderedMap) jsonreader.parse(Gdx.files.internal("data/gui_objects_model.json") );
		}catch (com.badlogic.gdx.utils.SerializationException parse_error){
			System.out.println(parse_error.getMessage() );
		}
		try{
			NASA_backgrounds = (OrderedMap) jsonreader.parse(Gdx.files.internal("data/nasa_images.json"));
		}catch (com.badlogic.gdx.utils.SerializationException parse_error){
			System.out.println(parse_error.getMessage() );
		}
		//load sounds
		sounds = new OrderedMap<String, Sound>();
		try{
			Array array_sounds = (Array) jsonreader.parse(Gdx.files.internal("data/sounds.json"));
			Iterator a_sound = array_sounds.iterator();
			while( a_sound.hasNext() ){
				OrderedMap info = (OrderedMap)a_sound.next();
				String key = (String) info.get("key");
				String file = (String) info.get("file");
				sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(file)) );
			}
		}catch (com.badlogic.gdx.utils.SerializationException parse_error){
			System.out.println(parse_error.getMessage() );
		}
		//load music
		musics = new OrderedMap<String, Music>();
		try{
			Array array_musics = (Array) jsonreader.parse(Gdx.files.internal("data/musics.json"));
			Iterator a_music = array_musics.iterator();
			while( a_music.hasNext() ){
				OrderedMap info = (OrderedMap)a_music.next();
				String key = (String) info.get("key");
				String file = (String) info.get("file");
				musics.put(key, Gdx.audio.newMusic(Gdx.files.internal(file)) );
				musics.get(key).setLooping(true);
			}
		}catch (com.badlogic.gdx.utils.SerializationException parse_error){
			System.out.println(parse_error.getMessage() );
		}
		//
		
		GuiElements.create(this);
		
		engine = new Engine();
		engine.create(this);
		engine.setBackgroundName("background1");
		mainmenu = new MainMenu();
		mainmenu.create(this);
		mainmenu.setBackgroundName("background2");
		optionmenu = new OptionMenu();
		optionmenu.create(this);
		optionmenu.setBackgroundName("background3");
		showrecords = new ShowRecords(this);
		//showrecords.create(this);//already at the ShowRecords' contructor
		showrecords.setBackgroundName("background1");
		credits = new Credits(this);//same as showrecords
		credits.setBackgroundName("background2");
		
		arcade_gamemode = new ArcadeGameMode(this);
		optionmenu.setSoundLevel(sound);
		optionmenu.setMusicLevel(music);
		if (Gdx.app.getType() == ApplicationType.Desktop){
			optionmenu.setScreenFactor(screen_factor);
			this.setScreenFactor(screen_factor);
		}
		this.setScreenMainMenu();
	}

	@Override
	public void dispose() {
		batch.dispose();
		engine.dispose();
		mainmenu.dispose();
		assets.dispose();
		font24.dispose();
		font32.dispose();
		font64.dispose();
		
		Keys<String> sounds_keys = sounds.keys();
		while(sounds_keys.hasNext()){
			String key = sounds_keys.next();
			sounds.get(key).dispose();
		}
		
		Keys<String> musics_keys = musics.keys();
		while(musics_keys.hasNext()){
			String key = musics_keys.next();
			musics.get(key).dispose();
		}
		
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
				
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public void setScreenMainMenu(){
		this.setScreen(mainmenu);
		this.stopCurrentMusic();
		this.playMusic("main");
	}
	
	public void setScreenEngine(){
		this.setScreen(engine);
		engine.getGameMode().startGame();
		current_music.stop();
	}
	
	private void setLoadingScreen(){
		LoadingScreen loadingscreen = new LoadingScreen();
		loadingscreen.create(this);
		super.setScreen(loadingscreen);
		//loading screen has not any background or anything to show
	}
	
	public void gotoLastScreen(){
		this.setScreen(last_screen);
	}
	
	public void setScreenOptions(){
		this.setScreen(optionmenu);
	}
	
	public void setScreenCredits(){
		this.setScreen(credits);
	}
	
	public void setShowRecordsScreen(GameMode gamemode){
		this.setScreen(showrecords);
		showrecords.loadScores(gamemode);
	}
	
	private void setScreen(MyGameScreen screen){
		last_screen = current_screen;
		current_screen = screen;
		InputProcessor input = current_screen.getInputProcessor();
		Gdx.input.setInputProcessor(current_screen.getInputProcessor() );
		current_screen.loadBackground();
		super.setScreen(screen);
	}
	
	public void setArcadeGameMode(){
		engine.setGameMode(arcade_gamemode);
	}
	
	public void setSoundLevel(int level){
		sound = level;
	}
	
	public void setMusicLevel(int level){
		music = level;
		if(current_music != null){
			current_music.setVolume(music / 5.f);
		}
	}
	
	public void loadData(){
		if (!Gdx.files.isLocalStorageAvailable()){
			return;
		}
		FileHandle save = Gdx.files.local("save.data");
		if (!save.exists()){
			sound = 5;
			music = 5;
			screen_factor = 1.f;
			return;
		}
		FileInputStream save_file = null;
		try {
			save_file = new FileInputStream(save.file());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ObjectInputStream save_stream = new ObjectInputStream(save_file);
			save_stream.readInt();//Version
			sound = save_stream.readInt();
			music = save_stream.readInt();
			if (Gdx.app.getType() == ApplicationType.Desktop){
				screen_factor = save_stream.readFloat();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveData(){
		if (!Gdx.files.isLocalStorageAvailable()){
			return;
		}
		FileHandle save = Gdx.files.local("save.data");
		FileOutputStream save_file = null;
		try {
			save_file = new FileOutputStream(save.file());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ObjectOutputStream save_stream = new ObjectOutputStream(save_file);
			save_stream.writeInt(SAVE_VERSION);
			save_stream.writeInt(sound);
			save_stream.writeInt(music);
			if (Gdx.app.getType() == ApplicationType.Desktop){
				save_stream.writeFloat(screen_factor);
			}
			save_stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void LoadNASAImage(String name, Sprite sprite){
		if (background != null){
			assets.unload(background_file);
		}
		
		OrderedMap a_NASA_background = (OrderedMap)NASA_backgrounds.get(name);
		background_file = (String)a_NASA_background.get("texture");
		assets.load(background_file, Texture.class);
		assets.finishLoading();
		System.out.println(assets.getAssetNames().toString());
		Util.configureSpriteWithModel(sprite, NASA_backgrounds, name, assets);
		
		background = assets.get(background_file, Texture.class);
	}
	
	public long playSound(String key){
		return sounds.get(key).play(sound / 5.f);
	}
	
	public void playMusic(String key){
		Music a_music = musics.get(key);
		a_music.setVolume(music / 5.f);
		a_music.play();
		current_music = a_music;
	}
	
	public void stopCurrentMusic(){
		if (current_music == null)return;
		current_music.stop();
	}
	
	public void setScreenFactor(float factor){
		this.screen_factor = factor;
		Gdx.graphics.setDisplayMode((int)(FWIDTH * factor), (int)(FHEIGHT * factor), false);
	}
	
	public float getScreenFactor(){
		return screen_factor;
	}
	
}
