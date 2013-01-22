package fi.metropolia.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

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
		transaction.commit();
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final int itemId = item.getItemId();
		if(itemId == android.R.id.home){
			
			// clean up back stack
			int num = getSupportFragmentManager().getBackStackEntryCount();
			if(num > 0){
				for(int i = 0; i < num; i++){
					getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);		
				}	
			}

			// http://developer.android.com/training/implementing-navigation/ancestral.html
			
//          Intent intent = new Intent(getApplicationContext(), Session2Activity.class);
//          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//          startActivity(intent);			
			
//	        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//	            // This activity is not part of the application's task, so
//	            // create a new task
//	            // with a synthesized back stack.
//	            TaskStackBuilder
//	            		.create(getApplicationContext())
//	                    .addNextIntent(new Intent(this, MainActivity.class))
//	                    .addNextIntent(upIntent).startActivities();
//	            finish();
//	        } else {
//	            // This activity is part of the application's task, so simply
//	            // navigate up to the hierarchical parent activity.
//	            NavUtils.navigateUpTo(this, upIntent);
//	        }
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
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
		if(getSupportFragmentManager().getBackStackEntryCount() == 0){
			mActionBar.setTitle(R.string.app_name);
			mActionBar.setSubtitle(R.string.session2);
			mActionBar.setDisplayHomeAsUpEnabled(false);
		}
	}
}
