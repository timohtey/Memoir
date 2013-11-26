package DAO;

public class StatisticsValues {

	private int id;
	private String gameName;
	private int accuracy;
	private float wordAverage;
	private int level;
	
	public StatisticsValues(){
		//filler code VVVV
		getId();
	}
	
	public StatisticsValues(int id, String gameName, int accuracy, float wordAverage, int level){
		this.id = id;
		this.gameName = gameName;
		this.accuracy = accuracy;
		this.wordAverage = wordAverage;
		this.level = level;
	} 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public float getWordAverage() {
		return wordAverage;
	}
	public void setWordAverage(int wordAverage) {
		this.wordAverage = wordAverage;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
}