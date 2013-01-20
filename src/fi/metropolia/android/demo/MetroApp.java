package fi.metropolia.android.demo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MetroApp extends Application{

	private static MetroApp mInstance = null;
	
	public static final String PREFS_NAME = "MetroPrefs";
	
	public static MetroApp getInstance() {
		return mInstance;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
	
	
	public synchronized int getId() {
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		return prefs.getInt("metroId", 1);
	}
	
	public synchronized void saveId(int id) {
		SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("metroId", id);
		editor.commit();
	}
	
}
