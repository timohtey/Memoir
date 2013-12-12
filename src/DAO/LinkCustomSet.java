package DAO;

import java.io.Serializable;

public class LinkCustomSet implements Serializable{

	String setName;
	String description;
	int accuracy;
	int wordAverage;
	String words;
	
	public String getWords(){
		return words;
	}
	
	public void setWords(String words){
		this.words = words;
	}
	
	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public int getWordAverage() {
		return wordAverage;
	}
	public void setWordAverage(int wordAverage) {
		this.wordAverage = wordAverage;
	}
	
}
