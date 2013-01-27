package fi.metropolia.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import fi.metropolia.android.demo.listener.OnFragmentChangedListener;

public class Session4Activity extends SherlockFragmentActivity implements OnFragmentChangedListener, OnBackStackChangedListener {

	private ActionBar mActionBar = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// do not call the method getActionBar()
		mActionBar = getSupportActionBar();
		mActionBar.setSubtitle(R.string.session4);
        mActionBar.setHomeButtonEnabled(false);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment f = new Session4RootFragment();
		transaction.replace(R.id.screen_container, f, "session4Root");
		transaction.addToBackStack("session4Root");
		transaction.commit();
		getSupportFragmentManager().addOnBackStackChangedListener(this);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final int itemId = item.getItemId();
		if(itemId == android.R.id.home){
			// clean up back stack
			final int num = getSupportFragmentManager().getBackStackEntryCount();
			if(num > 0){
				// do not clean up the root fragment
				for(int i = 0; i < (num - 1); i++){
					getSupportFragmentManager().popBackStack();		
				}	
			}
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public void onFragmentChanged(int layoutResId, Bundle bundle) {
		
	}	
	
	@Override
	public void onBackStackChanged() {
		// the root fragment is the first one
		final int entryCount = getSupportFragmentManager().getBackStackEntryCount();
		if(entryCount == 1){
			mActionBar.setTitle(R.string.app_name);
			mActionBar.setSubtitle(R.string.session4);
			mActionBar.setDisplayHomeAsUpEnabled(false);
		}else if(entryCount == 0){
			// no fragment in stack, so destroy the activity
			finish();
		}
	}

}
