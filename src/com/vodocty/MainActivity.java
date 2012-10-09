package com.vodocty;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.vodocty.database.DBOpenHelper;
import com.vodocty.update.Update;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SherlockActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	
	SQLiteOpenHelper soh = new DBOpenHelper(this);
	SQLiteDatabase db = soh.getWritableDatabase();
	
	Update u = new Update(db, this);
	u.doUpdate();
	
	db.close();
	
	
	setTheme(R.style.Sherlock___Theme_DarkActionBar); //Used for theme switching in samples
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
	
	List<String> test = new ArrayList<String>(); 
	test.add("hello");
	test.add("world");
	
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        android.R.layout.simple_list_item_1, test);
	
	ListView listView = (ListView) findViewById(R.id.listview);
	listView.setAdapter(adapter);
	
	ActionBar ab = getSupportActionBar();
	ab.setDisplayShowHomeEnabled(false);
	ab.setDisplayShowTitleEnabled(false);

	
        //((TextView)findViewById(R.id.text)).setText(R.string.simple_content);
    }
    
    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getSupportMenuInflater();
	inflater.inflate(R.menu.main_ab, menu);
	return true;
    }
}