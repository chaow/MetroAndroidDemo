package fi.metropolia.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

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
		mActionBar.setHomeButtonEnabled(true);
		
		Person p = new Person("Metropolia", 12);
		Bundle b = new Bundle();
		b.putParcelable("person", p);
		Fragment fragment = null;
		// do not call the method getFragmentManager()
		getSupportFragmentManager().addOnBackStackChangedListener(this);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		fragment = new Session2ContentFragment();
		fragment.setArguments(b);
		transaction.replace(R.id.screen_container, fragment, "session2Content");
		
		// add to stack as root, so the stack count is 1
		transaction.addToBackStack("session2Content");
		transaction.commit();
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final int itemId = item.getItemId();
		if(itemId == android.R.id.home){
			// clean up back stack
			int num = getSupportFragmentManager().getBackStackEntryCount();
			if(num > 0){
				// do not clean up the root fragment
				for(int i = 0; i < (num - 1); i++){
					getSupportFragmentManager().popBackStack();		
				}	
			}

			// http://developer.android.com/training/implementing-navigation/ancestral.html
			
			// kill the activity, and launch a new one
//          Intent intent = new Intent(getApplicationContext(), Session2Activity.class);
//          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//          startActivity(intent);			
			
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.session_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onFragmentChanged(int layoutResId, Bundle bundle) {
		Fragment fragment = null;
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if(layoutResId == R.layout.single_fragment_layout){
			fragment = new SingleFragment();
			if(bundle != null){
				fragment.setArguments(bundle);
			}
			transaction.replace(R.id.screen_container, fragment, "singleFragment");
			transaction.addToBackStack("singleFragment");
			// add animation 
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();
		} else if(layoutResId == R.layout.view_pager_layout){
			fragment = ImageViewPagerFragment.newInstance();
			transaction.replace(R.id.screen_container, fragment, "imageViewPagerFragment");
			transaction.addToBackStack("imageViewPagerFragment");
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();
		}
	}
	
	@Override
	public void onBackStackChanged() {
		final int entryCount = getSupportFragmentManager().getBackStackEntryCount();
		// the root fragment is the first one
		if(entryCount == 1){
			mActionBar.setTitle(R.string.app_name);
			mActionBar.setSubtitle(R.string.session2);
			mActionBar.setDisplayHomeAsUpEnabled(false);
		}else if(entryCount == 0){
			finish();
		}
	}
}
