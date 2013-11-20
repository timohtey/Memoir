package Model;

import java.io.Serializable;
import java.util.ArrayList;

import DAO.MemoirDAO;

public class GameModel implements Serializable{
	
	//CONSTANTS
	public static final int FIXED_TIME_MODE = 0;
	public static final int FIXED_WORDCOUNT_MODE =1;
	public static final int WORD_DIFFICULTY_INCREMENT = 5;
	public static final int TIME_DIFFICULTY_INCREMENT = 10000;
	public static final int INITIAL_WORD_COUNT= 10;
	public static final int INITIAL_TIME_LIMIT = 300000;
	
	//Difficulty
	public static int linkLevel = 1;
	
	private ArrayList<String> wordList = new ArrayList<String>();
	private int mode = FIXED_TIME_MODE;
	private int wordCount = INITIAL_WORD_COUNT;
	private int timeLimit = INITIAL_TIME_LIMIT ;
	
	private int currentWordIndex = 0;
	private int tryCount = 0;
	private int correctCount = 0;
	//declare time finished;
	
	public GameModel(int mode, MemoirDAO dao){
		this.mode = mode;
		setDifficulty(mode);
		
		//get words
		WordPool wordPool = new WordPool(dao.getWordList());
		for(int i=0;i<wordCount;i++){
			wordList.add(wordPool.getRandomWord());
		}
		
	}
	
	public void setDifficulty(int mode){
		switch(mode){
		case FIXED_TIME_MODE : 
			timeLimit = INITIAL_TIME_LIMIT; 
			wordCount = INITIAL_WORD_COUNT + (linkLevel * WORD_DIFFICULTY_INCREMENT);
			break;
		case FIXED_WORDCOUNT_MODE :
			timeLimit = INITIAL_TIME_LIMIT - (linkLevel * TIME_DIFFICULTY_INCREMENT);
			wordCount = INITIAL_WORD_COUNT;
			break;
		}
	}
	
	public int getCurrentWordIndex(){
		return currentWordIndex;
	}
	public int getWordCount(){
		return wordList.size();
	}
	
	public int getTimeLimit(){
		return timeLimit;
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
	public void nextWord(){
		if(currentWordIndex<wordList.size()-2){
			currentWordIndex++;	
		}else if(currentWordIndex == wordList.size()-2){
			startQuizPhase();
		}
	}
	public void prevWord(){
		if(currentWordIndex>0){
			currentWordIndex--;
		}
	}

	public boolean answerQuiz(String answer){
		tryCount++;
		if(answer.toLowerCase().equals(wordList.get(currentWordIndex+1))){
			currentWordIndex++;
			correctCount++;
			return true;
		}else{
			return false;
		}
	}
	
	public float computeAccuracy(){
		return Math.round((float)correctCount * tryCount);
	}

	public void startQuizPhase(){
		currentWordIndex =0;
		setDifficulty(mode);
	}
	public void endQuizPhase(boolean isFinished){
		int remainingWords = getWordCount() - getCurrentWordIndex()+1;
		tryCount += remainingWords;
		if(isFinished){
			linkLevel++;
			if(computeAccuracy()==1.0){
				linkLevel++;
			}
		}else if(remainingWords/getWordCount()>= .75){
			linkLevel-=3;
			
		}else if(remainingWords/getWordCount()>= .5){
			linkLevel-=2;
			
		}else if(remainingWords/getWordCount()>= .25){
			linkLevel-=1;
			
		}
	}

} 