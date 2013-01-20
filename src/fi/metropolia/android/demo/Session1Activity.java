package fi.metropolia.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import fi.metropolia.android.demo.model.Person;

public class Session1Activity extends SherlockFragmentActivity implements OnClickListener{

	private TextView mAutoUpdate = null;
	private Handler mAutoUpdateHandler = new Handler();
	private Runnable mTimedTask = new Runnable(){
		@Override
		public void run() {
			if(mAutoUpdate != null){
				mAutoUpdate.setText(String.valueOf(System.currentTimeMillis()));
			}
			mAutoUpdateHandler.postDelayed(mTimedTask , 100);				
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session1);
		getSupportActionBar().hide();
		initUI();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAutoUpdateHandler.post(mTimedTask);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mAutoUpdateHandler.removeCallbacks(mTimedTask);
	}

	private void initUI(){
		((Button)findViewById(R.id.button_fb_login)).setOnClickListener(this);		
		mAutoUpdate = (TextView)findViewById(R.id.text_view_auto_update);
	}
	
	@Override
	public void onClick(View v) {
		Person p = new Person("Metropolia", 12);
		Bundle b = new Bundle();
		b.putParcelable("person", p);
		Intent it = new Intent(getApplicationContext(), Session1SecondActivity.class);
		it.putExtras(b);
		startActivity(it);
	}


	

}
