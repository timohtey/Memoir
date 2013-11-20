package DAO;
import java.util.ArrayList;
import java.util.Random;

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
	//private static final String STATISTICSTABLE =  "Statistics";
	
	//Id column name
	private static final String KEY_ID = "_id";
	
	//WordList Table - column names
	private static final String KEY_WORD = "word";
	
	//TipList Table - column names
	private static final String KEY_TIP = "tip";
	
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
	
	public ArrayList<String> getWordList(int n){
		Random random = new Random();
		ArrayList<Integer> uniqueKeys = new ArrayList<Integer>();
		ArrayList<String> uniqueWords = new ArrayList<String>();
		int key = 0;
		key = random.nextInt(wordList.size());
		uniqueKeys.add(key);
		for(int i = 0; i<n;i++){
			do{
				key = random.nextInt(wordList.size());
			}while(uniqueKeys.contains(key) != false);
			uniqueWords.add(wordList.get(key));
		}
		return wordList;
	}
}
