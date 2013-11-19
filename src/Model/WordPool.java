package Model;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;

import DAO.MemoirDAO;

public class WordPool {
	ArrayList<String> words = new ArrayList<String>();
	Random rand = new Random();
	public WordPool(ArrayList<String> wordList){
		populateWords(wordList);
	}
	
	public void populateWords(ArrayList<String> wordList){
		words = wordList;
	}
	
	public String getRandomWord(){
		int index = rand.nextInt(words.size());
		return words.get(index);
	}

}
