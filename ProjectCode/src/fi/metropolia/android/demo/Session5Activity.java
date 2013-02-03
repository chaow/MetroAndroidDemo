package fi.metropolia.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import fi.metropolia.android.demo.listener.OnFragmentChangedListener;

public class Session5Activity extends SherlockFragmentActivity implements OnFragmentChangedListener, OnBackStackChangedListener{

	private ActionBar mActionBar = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// do not call the method getActionBar()
		mActionBar = getSupportActionBar();
		mActionBar.setSubtitle(R.string.session5);
		onFragmentChanged(R.layout.session5_root, null);
	}

	@Override
	public void onFragmentChanged(int layoutResId, Bundle bundle) {
		Fragment f = null;
		if(layoutResId == R.layout.session5_root){
			f = new Session5RootFragment();
		}else if(layoutResId == R.layout.touch_example){
			f = new TouchExampleFragment();
		}else if(layoutResId == R.layout.lrucache_example){
			f = new LruCacheExampleFragment();
		}
		if(f != null){
			if(bundle != null){
				f.setArguments(bundle);
			}
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.screen_container, f, f.getClass().getSimpleName());
			transaction.addToBackStack(f.getClass().getSimpleName());
			transaction.commit();			
			getSupportFragmentManager().addOnBackStackChangedListener(this);
		}
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
	public void onBackStackChanged() {
		final int entryCount = getSupportFragmentManager().getBackStackEntryCount();
		if(entryCount == 1){
			mActionBar.setTitle(R.string.app_name);
			mActionBar.setSubtitle(R.string.session5);
			mActionBar.setDisplayHomeAsUpEnabled(false);
		}else if(entryCount == 0){
			// no fragment in stack, so destroy the activity
			finish();
		}
	}

}