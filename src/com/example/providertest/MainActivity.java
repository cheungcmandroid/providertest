package com.example.providertest;

import com.example.providertest.R;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";
	
	private String newId;
	
	private Button btn_add_book;
	private Button btn_query_book;
	private Button btn_update_book;
	private Button btn_delete_book;
	
	private final String URI_BOOK = "content://com.example.helloworld.provider/Book";
	private final String URI_CATEGORY = "content://com.example.helloworld.provider/Category";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn_add_book = (Button) findViewById(R.id.btn_add_book);
		btn_query_book = (Button) findViewById(R.id.btn_query_book);
		btn_update_book = (Button) findViewById(R.id.btn_update_book);
		btn_delete_book = (Button) findViewById(R.id.btn_delete_book);
        
        btn_add_book.setOnClickListener(this);
        btn_query_book.setOnClickListener(this);
        btn_update_book.setOnClickListener(this);
        btn_delete_book.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) 
	{
		switch ( v.getId() ) 
		{
			case R.id.btn_add_book :
			{
				Uri uri = Uri.parse(URI_BOOK);
				ContentValues values = new ContentValues();
				values.put("name", "A Clash of Kings");
				values.put("author", "George Martin");
				values.put("pages", 1040);
				values.put("price", 22.85);
				Uri newUri = getContentResolver().insert(uri, values);
				
				newId = newUri.getPathSegments().get(1);
				break;
			}
			case R.id.btn_query_book :
			{
				Uri uri = Uri.parse(URI_BOOK);
				Cursor cursor = getContentResolver().query(uri, 
						null, null, null, null);
				if ( cursor != null )
				{
					while ( cursor.moveToNext() ) 
					{
						String name = cursor.getString(
								cursor.getColumnIndex("name"));
						String author = cursor.getString(
								cursor.getColumnIndex("author"));
						int pages = cursor.getInt(
								cursor.getColumnIndex("pages"));
						double price = cursor.getDouble(
								cursor.getColumnIndex("price"));
						
						Log.i(TAG, "book name = " + name);
						Log.i(TAG, "book author = " + author);
						Log.i(TAG, "book pages = " + pages);
						Log.i(TAG, "book price = " + price);
					}
				}
				cursor.close();
				break;
			}
			case R.id.btn_update_book :
			{
				Uri uri = Uri.parse(URI_BOOK + "/" + newId);
				ContentValues values = new ContentValues();
				values.put("name", "A Storm of Swords");
				values.put("pages", 1216);
				values.put("price", 24.05);
				getContentResolver().update(uri, values, null, null);
				break;
			}
			case R.id.btn_delete_book :
			{
				Uri uri = Uri.parse(URI_BOOK + "/" + newId);
				getContentResolver().delete(uri, null, null);
				break;
			}
			default : 
			{
				break;
			}
		}
	}
}
