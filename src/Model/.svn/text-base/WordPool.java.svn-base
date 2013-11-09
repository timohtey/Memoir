package Model;

import java.util.ArrayList;
import java.util.Random;

public class WordPool {
	ArrayList<String> words = new ArrayList<String>();
	Random rand = new Random();
	public WordPool(){
		populateWords();
	}
	
	public void populateWords(){
		words.add("Dog");
		words.add("Boat");
		words.add("Cat");
		words.add("Mouse");
		words.add("Brick");
		words.add("Crown");
		words.add("Pencil");
		words.add("Door");
		words.add("Table");
		words.add("Paper");
	}
	
	public String getRandomWord(){
		int index = rand.nextInt(words.size());
		return words.get(index);
	}

}
