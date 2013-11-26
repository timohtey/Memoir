package DAO;
import java.util.ArrayList;
import java.util.Random;

import DAO.StatisticsValues;

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
	private static final String STATISTICS_TABLE = "Statistics";
	private static final String LINK_SET_TABLE = "SetIndexes";
	private static final String LINK_WORD_TABLE = "CustomWords";
	private static final String LINK_WORD_SET_TABLE = "WordSet";	
	
	//Id column name
	private static final String KEY_ID = "_id";
	
	//WordList Table - column names
	private static final String KEY_WORD = "word";
	
	//TipList Table - column names
	private static final String KEY_TIP = "tip";
	
	//Statistics Table - column names
	private static final String KEY_MINIGAME_NAME = "game_name";
	private static final String KEY_ACCURACY = "accuracy";
	private static final String KEY_WORD_AVERAGE = "words_average";
	private static final String KEY_LEVEL = "level";
	
	//Link Utility Table - column names
	private static final String KEY_SET_ID = "set_id";
	private static final String KEY_WORD_ID = "word_id";
 		
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
	
	private static final String CREATE_STATISTICS_TABLE = "CREATE TABLE " + STATISTICS_TABLE + " ( "
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ KEY_MINIGAME_NAME + " TEXT NOT NULL, " 
			+ KEY_ACCURACY + " INTEGER, " 
			+ KEY_WORD_AVERAGE + " INTEGER, "
			+ KEY_LEVEL + " INTEGER);";
	
	private static final String CREATE_LINK_SET_TABLE  = "CREATE TABLE " + LINK_SET_TABLE + " ( "
			+ KEY_SET_ID + " INTEGER PRIMARY KEY);";
	
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
			
			db.execSQL(CREATE_STATISTICS_TABLE);
			db.execSQL("INSERT INTO " + STATISTICS_TABLE +
					"(" + KEY_MINIGAME_NAME + ", " + KEY_ACCURACY + ", " + KEY_WORD_AVERAGE + ", " + KEY_LEVEL
					+") VALUES (\"Linking Method\", null, null, 1);");
			
			db.execSQL(CREATE_LINK_SET_TABLE);
			db.execSQL(CREATE_LINK_WORD_TABLE);
			db.execSQL(CREATE_LINK_WORD_SET_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + TIPS_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + STATISTICS_TABLE);
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
	
	public void insertWordList(int index, ArrayList<String> wordList){
		int lastIndex = 0;
		ourDatabase.execSQL("INSERT INTO " + LINK_SET_TABLE +
						"(" + KEY_SET_ID + ") VALUES (" + index + ");");
		
		lastIndex = getLastWordIndex();
		lastIndex++;
		
		for(int i = 0 ; i < wordList.size(); i++){
			ourDatabase.execSQL("INSERT INTO " + LINK_WORD_TABLE +
						"(" + KEY_WORD + ") VALUES ('" + wordList.get(i) + "');");
		}
		
		for(int j = 0 ; j < wordList.size(); j++){
			ourDatabase.execSQL("INSERT INTO " + LINK_WORD_SET_TABLE +
						"(" + KEY_SET_ID +", " + KEY_WORD_ID + ") VALUES (" + index + ", " + lastIndex +");");
			lastIndex++;
		}
	}
	
	public int getLastWordIndex(){
		int index = 0;
		String[] column = new String[]{KEY_WORD_ID};
		Cursor c = ourDatabase.query(LINK_WORD_TABLE, column, null, null, null, null, KEY_WORD_ID);
		
		int iRow = c.getColumnIndex(KEY_WORD_ID);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			index = c.getInt(iRow);
		}
		return index;
	}
	
	public ArrayList<String> fetchLinkCustomWords(int index){
		
		ArrayList<Integer> wordIds = new ArrayList<Integer>();
		ArrayList<String> wordList = new ArrayList<String>();
		
		String[] columns = new String[]{KEY_ID, KEY_SET_ID, KEY_WORD_ID};
		String[] columns2 = new String[]{KEY_WORD_ID, KEY_WORD};
		Cursor c = ourDatabase.query(LINK_WORD_SET_TABLE, columns, KEY_SET_ID + " = " + index, null, null, null, KEY_ID);
		Cursor c2;
		int iWordIndex = c.getColumnIndex(KEY_WORD_ID);
		int iWord;
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			wordIds.add(c.getInt(iWordIndex));
		}
		
		for(int j = 0; j < wordIds.size(); j++){
			c2 = ourDatabase.query(LINK_WORD_TABLE, columns2, KEY_WORD_ID + " = " + wordIds.get(j), null, null, null, null);
			iWord = c2.getColumnIndex(KEY_WORD);
			for(c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()){
				wordList.add(c2.getString(iWord));
			}
			
		}
		
		return wordList;
	}
	
	/*
	public void deleteCustomWordList() {
		
	    ourDatabase.execSQL("DELETE FROM " + LINK_CUSTOM_TABLE + " WHERE " + KEY_ID + " > 0");
	}*/
	
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
	
	public int updateStatistics(StatisticsValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues cValues = new ContentValues();
		cValues.put(KEY_ACCURACY, values.getAccuracy());
		cValues.put(KEY_WORD_AVERAGE, values.getWordAverage());

		// updating row
		return db.update(STATISTICS_TABLE, cValues, KEY_ID + " = ?",
				new String[] { String.valueOf(values.getId()) });
	}
	
	public int updateGameLevel(StatisticsValues values){
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues cValues = new ContentValues();
		cValues.put(KEY_LEVEL, values.getLevel());

		// updating row
		return db.update(STATISTICS_TABLE, cValues, KEY_ID + " = ?",
				new String[] { String.valueOf(values.getId()) });
	}
	
public ArrayList<StatisticsValues> fetchStatistics(){
		
		String[] columns = new String[]{KEY_ID, KEY_MINIGAME_NAME, KEY_ACCURACY, KEY_WORD_AVERAGE, KEY_LEVEL};
		Cursor c = ourDatabase.query(STATISTICS_TABLE, columns, null, null, null, null, KEY_ID);
		ArrayList<StatisticsValues> values = new ArrayList<StatisticsValues>();
		int index = 0;
		
		int iRow = c.getColumnIndex(KEY_ID);
		int iName = c.getColumnIndex(KEY_MINIGAME_NAME);
		int iAccuracy = c.getColumnIndex(KEY_ACCURACY);
		int iAverage = c.getColumnIndex(KEY_WORD_AVERAGE);
		int iLevel = c.getColumnIndex(KEY_LEVEL);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            values.add(new StatisticsValues());
            values.get(index).setId(c.getInt(iRow));
            values.get(index).setGameName(c.getString(iName));
            values.get(index).setAccuracy(c.getInt(iAccuracy));
            values.get(index).setWordAverage(c.getInt(iAverage));
            values.get(index).setLevel(c.getInt(iLevel));
            index++;
        }
		
		return values;
	}
}
