package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class GameModel implements Serializable{
	private ArrayList<String> wordList = new ArrayList<String>();
	private int currentWordIndex = 0;
	private int skips =0;
	private int mistakes=0;
	private WordPool wordPool;
	//declare time finished;
	
	public GameModel(int wordCount, ArrayList<String> wordList){
		wordPool = new WordPool(wordList);
		for(int i=0;i<wordCount;i++){
			this.wordList.add(wordPool.getRandomWord());
		}
	}
	public GameModel(int wordCount){
		wordPool = new WordPool(wordList);
		for(int i=0;i<wordCount;i++){
			wordList.add(wordPool.getRandomWord());
		}
	}
	
	public int getCurrentWordIndex(){
		return currentWordIndex;
	}
	public int getWordCount(){
		return wordList.size();
	}
	public String getWordOne(){
		return wordList.get(currentWordIndex);
	}
	public String getWordTwo() throws Exception{
		if(currentWordIndex<wordList.size())
			return wordList.get(currentWordIndex+1);
		else
			throw new Exception();
	}
	public void linkNextWord(){
		currentWordIndex++;
		
	}

	public boolean answerQuiz(String answer){
		if(answer.equals(wordList.get(currentWordIndex+1))){
			currentWordIndex++;
			return true;
		}else{
			mistakes++;
			return false;
		}
	}
	
	public void skipWord(){
		currentWordIndex++;
		skips++;
	}
	public void startQuizPhase(){
		currentWordIndex =0;
	}
	public void endQuizPhase(){
		int wordsRemaining = wordList.size()- currentWordIndex+1;
		skips+= wordsRemaining;
	}
	
	public int getSkips(){
		return skips;
	}
	public int getMistakes(){
		return mistakes;
	}


} 