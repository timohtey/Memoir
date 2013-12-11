package DAO;
import java.util.ArrayList;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoirDAO{
	
	private static ArrayList<String> wordList;
	private static ArrayList<String> tipList;
	
	//Text file names for words and tips
	private static final String WORD_TEXTFILE = "words.txt"; 
	private static final String TIPS_TEXTFILE = "tips.txt";
	
	//Table Names
	private static final String WORD_TABLE = "WordList";
	private static final String TIPS_TABLE = "TipList";
	private static final String LINK_SET_TABLE = "Sets";
	private static final String LINK_WORD_TABLE = "CustomWords";
	private static final String LINK_WORD_SET_TABLE = "WordSet";	
	
	//Id column name
	private static final String KEY_ID = "_id";
	
	//WordList Table - column names
	private static final String KEY_WORD = "word";
	
	//TipList Table - column names
	private static final String KEY_TIP = "tip";
	
	
	//Link Utility Table - column names
	private static final String KEY_SET_ID = "set_id";
	private static final String KEY_WORD_ID = "word_id";
	private static final String KEY_SET_NAME = "set_name";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_DATE_PLAYED = "date_last_played";
	private static final String KEY_ACCURACY= "accuracy";
	private static final String KEY_WORD_AVERAGE = "word_average";
 		
	private static final String DB_NAME =  "WordListDB";
	private static final int DB_VERSION = 1;
	
	private DbHelper dbHelper; 
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	private static final String CREATE_WORD_TABLE = "CREATE TABLE " +  WORD_TABLE + " ( "
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_WORD + " TEXT NOT NULL);";
	private static final String CREATE_TIPS_TABLE = "CREATE TABLE " +  TIPS_TABLE + " ( "
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_TIP + " TEXT NOT NULL);";
	
	private static final String CREATE_LINK_SET_TABLE  = "CREATE TABLE " + LINK_SET_TABLE + " ( "
			+ KEY_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_SET_NAME + " TEXT NOT NULL, "
			+ KEY_DESCRIPTION + " TEXT, " 
			+ KEY_DATE_PLAYED + " DATE, "
			+ KEY_ACCURACY + " INTEGER, " 
			+ KEY_WORD_AVERAGE + " INTEGER);";
	
	private static final String CREATE_LINK_WORD_TABLE = "CREATE TABLE " +  LINK_WORD_TABLE + " ( "
			+ KEY_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_WORD + " TEXT NOT NULL);";
	
	private static final String CREATE_LINK_WORD_SET_TABLE = "CREATE TABLE " +  LINK_WORD_SET_TABLE + " ( "
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_SET_ID + " INTEGER NOT NULL, "
			+ KEY_WORD_ID + " INTEGER NOT NULL, FOREIGN KEY("+ KEY_SET_ID + ") REFERENCES "
			+ LINK_SET_TABLE + "(" + KEY_SET_ID + "), FOREIGN KEY(" + KEY_WORD_ID + ")  REFERENCES "
			+ LINK_WORD_TABLE + "(" + KEY_WORD_ID + "));";


	private static class DbHelper extends SQLiteOpenHelper{


		public DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION); 
			TextFileReader reader = new TextFileReader();
			
			wordList = new ArrayList<String>();
			wordList = null;
			wordList = reader.readFileFromAssets(context, WORD_TEXTFILE);
			
			tipList = new ArrayList<String>();
			tipList = null;
			tipList = reader.readFileFromAssets(context, TIPS_TEXTFILE);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(CREATE_WORD_TABLE);
			for(int i = 0; i < wordList.size(); i++){
				db.execSQL("INSERT INTO " + WORD_TABLE +
						"(" + KEY_WORD + ") VALUES ('" + wordList.get(i) + "');");	
			}
			
			db.execSQL(CREATE_TIPS_TABLE);
			for(int i = 0; i < tipList.size(); i++){
				db.execSQL("INSERT INTO " + TIPS_TABLE +
						"(" + KEY_TIP + ") VALUES (\"" + tipList.get(i) + "\");");	
			}
			
			db.execSQL(CREATE_LINK_SET_TABLE);
			db.execSQL(CREATE_LINK_WORD_TABLE);
			db.execSQL(CREATE_LINK_WORD_SET_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + TIPS_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + LINK_SET_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + LINK_WORD_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + LINK_WORD_SET_TABLE);
			onCreate(db);
			
		}
		
	}
	 
	public MemoirDAO(Context c){
		ourContext = c;
	}

	public MemoirDAO open()throws SQLException{
		dbHelper = new DbHelper(ourContext);
		ourDatabase = dbHelper.getWritableDatabase();
		return this;
	}
	
	public ArrayList<String> getTipList(){
		return tipList;
	}
	public void close(){
		dbHelper.close();
	}
	
	//Adding a word set in Link Custom
		public void insertWordList(String title, String description, ArrayList<String> wordList){
			int setLastIndex = 0;
			int wordLastIndex = 0;
			
			ourDatabase.execSQL("INSERT INTO " + LINK_SET_TABLE +
							"(" + KEY_SET_NAME + ", " + KEY_DESCRIPTION + ") VALUES ('" + title + "', '" + description + "');");
			
			for(int i = 0 ; i < wordList.size(); i++){
				ourDatabase.execSQL("INSERT INTO " + LINK_WORD_TABLE +
							"(" + KEY_WORD + ") VALUES ('" + wordList.get(i) + "');");
			}
			
			setLastIndex = getLastIndex(KEY_SET_ID, LINK_SET_TABLE);
			wordLastIndex = getLastIndex(KEY_WORD_ID, LINK_WORD_TABLE);
			wordLastIndex -= wordList.size() - 1;
			
			for(int j = 0 ; j < wordList.size(); j++){
				ourDatabase.execSQL("INSERT INTO " + LINK_WORD_SET_TABLE +
							"(" + KEY_SET_ID +", " + KEY_WORD_ID + ") VALUES (" + setLastIndex + ", " + wordLastIndex +");");
				wordLastIndex++;
			}
		}
		
		private int getLastIndex(String column, String table){
			int index = 0;
			String[] columns = new String[]{column};
			Cursor c = ourDatabase.query(table, columns, null, null, null, null, null);
			
			int iRow = c.getColumnIndex(column);
			
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				index = c.getInt(iRow);
			}
			return index;
		}
		
		//Fetching the word list of a given set
		public ArrayList<String> getWordSet(String title){
			ArrayList<String> wordList = new ArrayList<String>();
			ArrayList<Integer> wordListIndexes = new ArrayList<Integer>();
			int setIndex = 0;
			
			setIndex =getSetIndex(title);
			wordListIndexes = getSetWordIndexes(setIndex); 
			wordList = getSetWordList(wordListIndexes);
			
			return wordList;
		}
		
		private int getSetIndex(String title){
			int index = 0;
			
			String[] column = new String[]{KEY_SET_ID, KEY_SET_NAME};
			Cursor c = ourDatabase.query(LINK_SET_TABLE, column, KEY_SET_NAME + " = '" + title + "'", null, null, null, KEY_SET_ID);
			
			int iRow = c.getColumnIndex(KEY_SET_ID);
			
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				index = c.getInt(iRow);
			}
			return index;
		}
		
		private ArrayList<Integer> getSetWordIndexes(int setIndex){
			ArrayList<Integer> wordListIndexes = new ArrayList<Integer>();
			
			String[] columns = new String[]{KEY_SET_ID, KEY_WORD_ID};
			Cursor c = ourDatabase.query(LINK_WORD_SET_TABLE, columns, KEY_SET_ID + " = " + setIndex , null, null, null, null);
			
			int iRow = c.getColumnIndex(KEY_WORD_ID);
			
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				wordListIndexes.add(c.getInt(iRow));
			}
			
			return wordListIndexes;
		}
		
		private ArrayList<String> getSetWordList(ArrayList<Integer> wordListIndexes){
			ArrayList<String> wordSet = new ArrayList<String>();
			String[] columns = new String[]{KEY_WORD_ID, KEY_WORD};
			Cursor c = null;
			
			for(int i = 0; i < wordListIndexes.size(); i++){
				c = ourDatabase.query(LINK_WORD_TABLE, columns, KEY_WORD_ID + " = " + wordListIndexes.get(i), null, null, null, null);
				
				int iRow = c.getColumnIndex(KEY_WORD);
				
				for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
					wordSet.add(c.getString(iRow));
				}
			}
			
			return wordSet;
			
		}
		
		//Updating the accuracy and wordAverage of Link Custom Set
		public void updateLinkCustom(String title, int accuracy, int wordAverage){
			
			int setIndex = 0;
			setIndex = getSetIndex(title); 
			
			ourDatabase.execSQL("UPDATE " + LINK_SET_TABLE 
			+ " SET " + KEY_ACCURACY + " = " + accuracy + ", " 
			+ KEY_WORD_AVERAGE + " = " + wordAverage + " WHERE " + KEY_SET_ID + " = " + setIndex + ";");
			
		}
		
		//Returns the values like name, description etc. of each set in Link Custom
		public ArrayList<LinkCustomSet> getLinkCustomDisplay(){
			ArrayList<LinkCustomSet> setInfo = new ArrayList<LinkCustomSet>();
			
			String[] columns = new String[]{KEY_SET_NAME, KEY_DESCRIPTION, KEY_ACCURACY, KEY_WORD_AVERAGE};
			Cursor c = ourDatabase.query(LINK_SET_TABLE, columns, null, null, null, null, null);
			
			int index = 0;
			int iName = c.getColumnIndex(KEY_SET_NAME);
			int iDescription = c.getColumnIndex(KEY_DESCRIPTION);
			int iAccuracy = c.getColumnIndex(KEY_ACCURACY);
			int iWordAverage = c.getColumnIndex(KEY_WORD_AVERAGE);
				
			for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
				for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
					setInfo.add(new LinkCustomSet());
					setInfo.get(index).setSetName(c.getString(iName));
					setInfo.get(index).setDescription(c.getString(iDescription));
			        setInfo.get(index).setAccuracy(c.getInt(iAccuracy));
			        setInfo.get(index).setWordAverage(c.getInt(iWordAverage));
			        index++;
			    }
			}
			
			return setInfo;
		}
	
	public ArrayList<String> getWordList(int n){
		Random random = new Random();
		ArrayList<Integer> uniqueKeys = new ArrayList<Integer>();
		ArrayList<String> uniqueWords = new ArrayList<String>();
		int key = 0;
		boolean unique;
		key = random.nextInt(wordList.size());
		uniqueKeys.add(key);
		uniqueWords.add(wordList.get(key));
		for(int i = 0; i<n;i++){
			unique = false;
			do{
				key = random.nextInt(wordList.size());
				for(int j = 0; unique == false && j<uniqueKeys.size(); j++){
					if(uniqueKeys.get(j).equals(key)){
						unique = false;
					}
					else {
						uniqueKeys.add(key);
						unique = true;
					}
				}
			}while(unique==false);
			uniqueWords.add(wordList.get(key));
		}
		uniqueWords.remove(0);
		return uniqueWords;
	}
	
	//For deleting a set in Link Custom
		public void deleteCustomWordList (String title){
			ArrayList<Integer> wordListIndexes = new ArrayList<Integer>();
			int setIndex = 0;
			
			setIndex =getSetIndex(title);
			wordListIndexes = getSetWordIndexes(setIndex);   
			for(int i = 0; i < wordListIndexes.size(); i++){
				ourDatabase.execSQL("DELETE FROM " + LINK_WORD_TABLE + " WHERE " + KEY_WORD_ID + " = " + wordListIndexes.get(i));
			}
			ourDatabase.execSQL("DELETE FROM " + LINK_SET_TABLE + " WHERE " + KEY_SET_ID + " = " + setIndex);
			ourDatabase.execSQL("DELETE FROM " + LINK_WORD_SET_TABLE + " WHERE " + KEY_SET_ID + " = " + setIndex);
			
		}
	
}
