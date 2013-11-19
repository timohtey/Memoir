package Model;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;

import DAO.MemoirDAO;

public class WordPool extends Activity{
	ArrayList<String> words = new ArrayList<String>();
	Random rand = new Random();
	MemoirDAO DAO = new MemoirDAO(WordPool.this);
	public WordPool(){
		populateWords();
	}
	
	public void populateWords(){
		words = DAO.getWordList();
	}
	
	public String getRandomWord(){
		int index = rand.nextInt(words.size());
		return words.get(index);
	}

}
