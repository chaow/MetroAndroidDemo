package fi.metropolia.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ActionBarTabActivity extends SherlockFragmentActivity implements com.actionbarsherlock.app.ActionBar.TabListener{

	private ActionBar mActionBar = null;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	private ViewPager mViewPager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean swipe = false;
		Bundle b = getIntent().getExtras();
		if(b != null){
			swipe = b.getBoolean("swipe", false);
		}
		
		// do not call the method getActionBar()
		mActionBar = getSupportActionBar();

		
		mActionBar.setTitle("Session 2");
		mActionBar.setSubtitle("Tabs");
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		if(swipe){
			// need to have a layout
			setContentView(R.layout.tab_view_paper);
			
			// Create the adapter that will return a fragment for each of the three
			// primary sections of the app.
			 mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
			 
			// Set up the ViewPager with the sections adapter.
	        mViewPager = (ViewPager) findViewById(R.id.pager);
	        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
	        
			// When swiping between different sections, select the corresponding
			// tab. We can also use ActionBar.Tab#select() to do this if we have
			// a reference to the Tab.
	        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						mActionBar.setSelectedNavigationItem(position);
					}
				});
	        mActionBar.setDisplayShowHomeEnabled(false);
	        mActionBar.setDisplayShowTitleEnabled(false);
		}else{
			// disabled the action bar icon and title
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}

		for(int i = 0; i < 4; i++){
			Tab tab = null;
			tab = mActionBar.newTab();
			tab.setIcon(R.drawable.ic_launcher);
			tab.setText("tab " + (i + 1));
			if(swipe){
				tab.setTabListener(this);				
			} else {
				tab.setTabListener(new TabListener<DumpFragment>(this, Long.toString(System.currentTimeMillis()), DumpFragment.class)); 				
			}
			mActionBar.addTab(tab);
		}
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		final int itemId = item.getItemId();
		if(itemId == android.R.id.home){
			finish();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
	
	
	// more info:
	// http://developer.android.com/training/implementing-navigation/lateral.html
	// http://developer.android.com/guide/topics/ui/actionbar.html
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	    private Fragment mFragment;
	    private final Activity mActivity;
	    private final String mTag;
	    private final Class<T> mClass;

	    /** Constructor used each time a new tab is created.
	      * @param activity  The host Activity, used to instantiate the fragment
	      * @param tag  The identifier tag for the fragment
	      * @param clz  The fragment's Class, used to instantiate the fragment
	      */
	    public TabListener(Activity activity, String tag, Class<T> clz) {
	        mActivity = activity;
	        mTag = tag;
	        mClass = clz;
	    }

	    /* The following are each of the ActionBar.TabListener callbacks */

	    public void onTabSelected(Tab tab, FragmentTransaction ft) {
	        // Check if the fragment is already initialized
	        if (mFragment == null) {
	            // If not, instantiate and add it to the activity
	            mFragment = Fragment.instantiate(mActivity, mClass.getName());
	            ft.add(android.R.id.content, mFragment, mTag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(mFragment);
	        }
	    }

	    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	        if (mFragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(mFragment);
	        }
	    }

	    public void onTabReselected(Tab tab, FragmentTransaction ft) {
	        // User selected the already selected tab. Usually do nothing.
	    }
	}

	
	public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
		
		public DemoCollectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new DumpFragment();
			Bundle args = new Bundle();
			args.putString("tag", Long.toString(System.currentTimeMillis()));
			fragment.setArguments(args);
			return fragment;
			// return DumpFragment.newInstance(Long.toString(System.currentTimeMillis()));
		}

		@Override
		public int getCount() {
			return 4;
		}

	}

	public static class DumpFragment extends SherlockFragment {

		static DumpFragment newInstance(String tag) {
			DumpFragment f = new DumpFragment();
			Bundle b = new Bundle();
			b.putString("tag", tag);
			f.setArguments(b);
			return f;
		}

		private String mTag = "";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle b = getArguments();
			if (b != null) {
				mTag = getArguments().getString("tag");
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater
					.inflate(R.layout.dump_layout, container, false);
			final String tag = getTag();
			if (TextUtils.isEmpty(tag)) {
				((TextView) view.findViewById(R.id.text_view_dump_text)).setText("Dump fragment: " + mTag);
			} else {
				((TextView) view.findViewById(R.id.text_view_dump_text)).setText("Dump fragment: " + tag);
			}
			return view;
		}

	}
	
}
