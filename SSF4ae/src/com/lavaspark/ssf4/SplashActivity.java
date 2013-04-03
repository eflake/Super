package com.lavaspark.ssf4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.lavaspark.db.DBManager;
import com.lavaspark.service.MintentService;
import com.lavaspark.util.GlobalVariables;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 1000; 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initdata();
        Intent intent = new Intent(this, MintentService.class);
        intent.putExtra("parameter", "createdb");
        startService(intent);
        
        /**
         * 使用handler来处理
         */
        new Handler().postDelayed(new Runnable(){
         @Override
         public void run() {
             Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class); 
             SplashActivity.this.startActivity(mainIntent);
             SplashActivity.this.finish(); 
         }
        }, SPLASH_DISPLAY_LENGHT);
    }
	private void initdata() {
		// TODO Auto-generated method stub
		String[] character_name = this.getResources().getStringArray(
				R.array.character_name);
		String[] zhaoshi_name = this.getResources().getStringArray(
				R.array.character_zhaoshi_name);
		GlobalVariables globalVariable = ((GlobalVariables)getApplicationContext());
		globalVariable.setcharacters(character_name);
		globalVariable.setCharacter_zhaoshi(zhaoshi_name);
	}
   
}
