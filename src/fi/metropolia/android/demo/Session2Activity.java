package fi.metropolia.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import fi.metropolia.android.demo.listener.OnFragmentChangedListener;
import fi.metropolia.android.demo.model.Person;

public class Session2Activity extends SherlockFragmentActivity implements OnFragmentChangedListener, OnBackStackChangedListener {
	
	private ActionBar mActionBar = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// do not call the method getActionBar()
		mActionBar = getSupportActionBar();
		mActionBar.setSubtitle(R.string.session2);
		
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		Person p = new Person("Metropolia", 12);
		Bundle b = new Bundle();
		b.putParcelable("person", p);
		onFragmentChanged(R.layout.session2, b);
	}
	
	@Override
	public void onFragmentChanged(int layoutResId, Bundle bundle) {
		Fragment fragment = null;
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if(layoutResId == R.layout.session2){
			// do not call the method getFragmentManager()
			fragment = new Session2ContentFragment();
			if(bundle != null){
				fragment.setArguments(bundle);
			}
			transaction.replace(R.id.screen_container, fragment);
			transaction.commit();
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(getSupportFragmentManager().getBackStackEntryCount() == 0){
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackStackChanged() {

	}
}
