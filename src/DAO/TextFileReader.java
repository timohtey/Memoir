package DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

public class TextFileReader extends Activity{

	private ArrayList<String> list;
	
	public ArrayList<String> readFileFromAssets(Context context, String filename) {
        try {
            list = new ArrayList<String>();
            InputStream in = context.getAssets().open(filename);

            if (in != null) {
                // prepare the file for reading
                InputStreamReader input = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(input);
                String line = br.readLine();
                while ((line=br.readLine()) != null) 
                {
                   list.add(line);
                }
                in.close();
            }else{
                System.out.println("It's the assests");
            }

        } catch (IOException e) {
            System.out.println("Couldn't Read File Correctly");
        }
        return list;
    }
	
	public ArrayList<String> readFileFromAssets2(String fileName) {
		
		list = new ArrayList<String>();
		list = null; 
		
        BufferedReader br=null;
        try {
             br = new BufferedReader(new InputStreamReader(getAssets().open(fileName))); 
             String line = br.readLine();
             while ((line = br.readLine()) != null) {
                 list.add(line);
             }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

	
}
