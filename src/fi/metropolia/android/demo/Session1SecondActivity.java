package fi.metropolia.android.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import fi.metropolia.android.demo.model.Person;

public class Session1SecondActivity extends SherlockFragmentActivity {
 
	private static final Handler mUIHanlder = new Handler(){	
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case 0:
					Toast.makeText(MetroApp.getInstance().getApplicationContext(), 
									String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
					break;
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
		getSupportActionBar().hide();
		
		Person p = getIntent().getExtras().getParcelable("person");
		if(p != null){
			Toast.makeText(getApplicationContext(), p.toString(), Toast.LENGTH_SHORT).show();
		}
		
		runOnUiThread(new MyRunnable(mUIHanlder));
	}


	private class MyRunnable implements Runnable{
		
		private Handler handler = null;
		
		public MyRunnable(Handler h){
			handler = h;
		}

		@Override
		public void run() {
			if(handler != null){
				Message msg = handler.obtainMessage();
				msg.what = 0;
				msg.obj = new String("Object");
				handler.sendMessage(msg);
			}
		}
	}
}
