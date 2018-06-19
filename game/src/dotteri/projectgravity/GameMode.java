package dotteri.projectgravity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public abstract class GameMode{

	public class Record{
		StringBuilder name = null;
		Integer data1 = null;
		Integer data2 = null;
		
		public Record(){
			name = new StringBuilder();
			data1 = new Integer(0);
			data2 = new Integer(0);
		}
		
		public void setName(CharSequence new_name){
			name.setLength(0);
			name.append(new_name);
		}
		
		public StringBuilder getName(){
			return name;
		}
		
		public void setData1(int number){
			data1 = number;
		}
		
		public Integer getData1(){
			return data1;
		}
		
		public void setData2(int number){
			data2 = number;
		}
		
		public Integer getData2(){
			return data2;
		}
		
		public boolean isData1LowerThan(int number){
			return data1 < number;
		}
		
		public boolean isData1EqualTo(int number){
			return data1 == number;
		}
		
		public void copy(Record record){
			this.setName(record.name);
			data1 = record.data1;
			data2 = record.data2;
		}
		
		public void reset(){
			name.setLength(0);
			data1 = 0;
			data2 = 0;
		}
	}
	
	final static int VERSION = 1;
	MyGame mygame = null;
	static int MAXRECORD = 10;
	Record[] record;
	String file_name = null;
	
	boolean use_data2 = false;
	String caption_data1 = "";
	String caption_data2 = "";
	
	public GameMode(MyGame mygame, String file_name) {
		this.mygame = mygame;
		//create the records
		record = new Record[MAXRECORD];
		for(int i = 0; i < MAXRECORD; i += 1){
			record[i] = new Record();
		}
		this.file_name = file_name;
		this.load();
	}
	
	abstract public void reset();
	abstract public void startGame();
	abstract public void starIsDevoured(Star star);
	abstract public void starDead(Star star);
	abstract public void starDevoured(Star star);
	//yes, a star can be devoured and finally IS devoured
	//a star dies by being devoured
	abstract public void blackholeDie(Blackhole blackhole);
	abstract public void playerMovesBlackhole(Blackhole blackhole);
	abstract public void updateHUD();
	abstract boolean isGameOver();
	abstract Record thereIsNewRecord();
	abstract void setDataToRecord(Record record);
	abstract int getRecord();
	abstract void step(float time);
	
	protected void carryUpScoreList(){
		
	}
	
	protected void carryDownScoreList(int i_star){
		for(int i = MAXRECORD - 1; i > i_star; i -= 1){
			record[i].copy(record[i - 1]);
		}
	}
	
	public Record[] getRecordList(){
		return record;
	}
	
	public void save(){
		FileHandle file_handle = Gdx.files.local("data/" + file_name + ".data");
		try{
			FileOutputStream stream = new FileOutputStream(file_handle.file());
			ObjectOutputStream object_stream = new ObjectOutputStream(stream);
			object_stream.writeInt(VERSION);
			for(int i = 0; i < 10; i += 1){
				object_stream.writeUTF(record[i].getName().toString());
				object_stream.writeInt(record[i].getData1());
				object_stream.writeInt(record[i].getData2());
			}
			object_stream.close();
		}catch (FileNotFoundException file_not_found){
			file_not_found.getMessage();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(){
		try{
			FileHandle file_handle = Gdx.files.local("data/" + file_name + ".data");
			FileInputStream stream = new FileInputStream(file_handle.file());
			ObjectInputStream object_stream = new ObjectInputStream(stream);
			object_stream.readInt();//read VERSION
			for(int i = 0; i < 10; i += 1){
				record[i].setName(object_stream.readUTF());
				record[i].setData1(object_stream.readInt());
				record[i].setData2(object_stream.readInt());
			}
			object_stream.close();
		}catch (FileNotFoundException file_not_found){
			//...
		}catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
	
	public void useData2(boolean use){
		use_data2 = use;
	}
	
	public boolean isUsingData2(){
		return use_data2;
	}
	
	public void setCaptionData1(String caption_data){
		this.caption_data1 = caption_data;
	}
	
	public String getCapationData1(){
		return  caption_data1;
	}
	
	public void setCaptionData2(String caption_data){
		this.caption_data2 = caption_data;
	}
	
	public String getCapationData2(){
		return  caption_data2;
	}
	
	public void clearRecordList(){
		for(Record a_record: record){
			a_record.reset();
		}
	}
	
}
