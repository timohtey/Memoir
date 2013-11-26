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
	public static final int INITIAL_TIME_LIMIT = 60000;
	
	//Difficulty
	//TODO: save level in db;
	public static int linkLevel = 1;
	
	private ArrayList<String> wordList = new ArrayList<String>();
	private int mode = FIXED_TIME_MODE;
	private int wordCount = INITIAL_WORD_COUNT;
	private int timeLimit = INITIAL_TIME_LIMIT ;
	
	private int currentWordIndex = 0;
	private int tryCount = 0;
	private int correctCount = 0;
	
	private long timeFinished =0;
	private boolean isGameRecorded = false;
	//declare time finished;
	
	public GameModel(int mode, MemoirDAO dao){
		this.mode = mode;
		setDifficulty(mode);
		
		//get words
		wordList = dao.getWordList(wordCount);
		wordCount= wordList.size();
		System.out.println("test");
	}
	
	public GameModel(int mode, ArrayList<String> array){
		this.mode = mode;
		setDifficulty(mode);
		
		//get words
		wordList = array;
		wordCount= array.size();
		System.out.println("test");
	}
	
	public void setDifficulty(int mode){
		switch(mode){
		case FIXED_TIME_MODE : 
			timeLimit = INITIAL_TIME_LIMIT; 
			wordCount = INITIAL_WORD_COUNT + ((linkLevel-1) * WORD_DIFFICULTY_INCREMENT);
			break;
		case FIXED_WORDCOUNT_MODE :
			timeLimit = INITIAL_TIME_LIMIT - ((linkLevel-1) * TIME_DIFFICULTY_INCREMENT);
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
	public String getWordTwo(){
		if(currentWordIndex>=wordList.size())
			return "";
		return wordList.get(currentWordIndex+1);
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
	
	public int computeAccuracy(){
	
		if(tryCount==0)
			return 0;
		return (int)Math.round((float)correctCount / tryCount*100);
	}
	
	public float computeWordPerMin(){
		if(tryCount==0)
			return 0;
		return Math.round(correctCount/((float)(timeFinished/1000)/60));
	}
	
	public int getLinkLevel(){
		return linkLevel;
	}

	public void startQuizPhase(){
		currentWordIndex =0;
		setDifficulty(mode);
		isGameRecorded = true;
	}
	
	public boolean isGameRecorded(){
		return isGameRecorded;
	}
	
	public void endQuizPhase(boolean isFinished,Long timeFinished){
		int remainingWords = getWordCount() - getCurrentWordIndex()+1;
		this.timeFinished = timeFinished;
		tryCount += remainingWords;
		if(isFinished){
			linkLevel++;
			if(computeAccuracy()==1.0){
				linkLevel++;
			}
		}else if(remainingWords/getWordCount()>= .75 && linkLevel>=4){
			linkLevel-=3;
			
		}else if(remainingWords/getWordCount()>= .5&& linkLevel>=3){
			linkLevel-=2;
			
		}else if(remainingWords/getWordCount()>= .25&& linkLevel>=2){
			linkLevel-=1;
			
		}
	}
} 