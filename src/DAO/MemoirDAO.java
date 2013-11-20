package DAO;
import java.util.ArrayList;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
	//private static final String STATISTICSTABLE =  "Statistics";
	
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
			+ KEY_WORD_AVERAGE + " INTEGER);";

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
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + WORD_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + TIPS_TABLE);
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
	
	public void close(){
		dbHelper.close();
	}
	
	/*
	public long insertWordEntry(String word){
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_WORD, word);
		return ourDatabase.insert(WORD_TABLE, KEY_WORD, cv);
	}
	*/
	
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
	
	public ArrayList<StatisticsValues> fetchStatistics(){
		
		String[] columns = new String[]{KEY_ID, KEY_MINIGAME_NAME, KEY_ACCURACY, KEY_WORD_AVERAGE};
		Cursor c = ourDatabase.query(STATISTICS_TABLE, columns, null, null, null, null, KEY_ID);
		ArrayList<StatisticsValues> values = new ArrayList<StatisticsValues>();
		int index = 0;
		
		int iRow = c.getColumnIndex(KEY_ID);
		int iName = c.getColumnIndex(KEY_MINIGAME_NAME);
		int iAccuracy = c.getColumnIndex(KEY_ACCURACY);
		int iAverage = c.getColumnIndex(KEY_WORD_AVERAGE);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            values.add(new StatisticsValues());
            values.get(index).setId(c.getInt(iRow));
            values.get(index).setGameName(c.getString(iName));
            values.get(index).setAccuracy(c.getInt(iAccuracy));
            values.get(index).setWordAverage(c.getInt(iAverage));
            index++;
        }
		
		return values;
	}
	
	public String fetchWordData(String type){
		
		String table = null;
		String tableColumn = null;
		
		if(type.equals("words")){
			table = WORD_TABLE; 
			tableColumn = KEY_WORD; 
		}
		else if(type.equals("tips")){
			table = TIPS_TABLE; 
			tableColumn = KEY_TIP;
		}
		
		String[] columns = new String[]{KEY_ID, tableColumn};
		Cursor c = ourDatabase.query(table, columns, null, null, null, null, KEY_ID);
		String result = "";
		
		int iRow = c.getColumnIndex(KEY_ID);
		int iWord = c.getColumnIndex(tableColumn);
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			result += c.getString(iRow) + " " + c.getString(iWord) +"\n";
		}
		return result;
	}
}
