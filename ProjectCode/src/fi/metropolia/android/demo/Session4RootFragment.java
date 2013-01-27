package fi.metropolia.android.demo;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;

import fi.metropolia.android.demo.listener.OnFragmentChangedListener;
import fi.metropolia.android.demo.widget.CustomItemTemplate;

public class Session4RootFragment extends SherlockFragment implements OnClickListener, OnLongClickListener{
	
	private final static String TAG = Session4RootFragment.class.getSimpleName();
	
	private OnFragmentChangedListener mListener = null;
	
	private View mBarCustomView = null;
	
	private ActionBar mActionBar = null;
	
	private SearchView mSearchView = null;
	
	private View mCheckBoxDemo = null;
	private View mImageViewDemo = null;
	private boolean mShowCheckbox = false;
	
	private LinearLayout mContentContainer = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// handle the menu clicking by this fragment
	    setHasOptionsMenu(true);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mListener = (OnFragmentChangedListener) activity;			
		}catch(Exception e){
			Log.e(TAG, "error", e);
		}

        mActionBar = getSherlockActivity().getSupportActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setNavigationMode(ActionBar.DISPLAY_SHOW_CUSTOM);

        // inflate custom view
		mBarCustomView = getSherlockActivity().getLayoutInflater().inflate(R.layout.actionbar_custom_view, null);
        ((Button)mBarCustomView.findViewById(R.id.button_custom_button1)).setOnClickListener(this);
        ((Button)mBarCustomView.findViewById(R.id.button_custom_button2)).setOnClickListener(this);
        final ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
        		ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.RIGHT|Gravity.CENTER_VERTICAL;
		lp.setMargins(0, 0, 5, 0);
		mActionBar.setCustomView(mBarCustomView, lp);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.session4_root, container, false); 
		mCheckBoxDemo = view.findViewById(R.id.checkbox_frame_demo);
		mImageViewDemo = view.findViewById(R.id.image_view_frame_demo);
		((Button)view.findViewById(R.id.button_show_content)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_add_view_item)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_more_fragment)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_nested_fragments)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_context_mode)).setOnLongClickListener(this);
		
		mContentContainer = (LinearLayout) view.findViewById(R.id.content_container);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.session4_split_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.session_split_menu_search);
		mSearchView =  (SearchView) searchItem.getActionView();
		if(mSearchView != null){
			// check if API level is bigger than 7
			if(android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.ECLAIR_MR1){
				setupSearchView(searchItem);			
			} else {
				mSearchView.setVisibility(View.GONE);	
			}			
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	private void setupSearchView(MenuItem searchItem) {
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
        		FrameLayout.LayoutParams.WRAP_CONTENT, 
        		FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = 5;
        mSearchView.setLayoutParams(lp);
        
        // http://developer.android.com/guide/topics/search/search-dialog.html
		SearchManager searchManager = (SearchManager) getSherlockActivity().getApplicationContext().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
            SearchableInfo info = searchManager.getSearchableInfo(getSherlockActivity().getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }
        mSearchView.setOnSearchClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// do something
			}
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
        	@Override
        	public boolean onQueryTextSubmit(String query) {
        		Toast.makeText(getSherlockActivity().getApplicationContext(), query, Toast.LENGTH_SHORT).show();
        		// do something
        		return false;
        	}
        	@Override
        	public boolean onQueryTextChange(String newText) {
        		// do something
        		return false;
        	}	
        });
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int itemId = item.getItemId();
		if(itemId == R.id.session_split_menu_download){
			Toast.makeText(getSherlockActivity().getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if(v == null) return;
		final int viewId = v.getId();
		if(viewId == R.id.button_custom_button1){
			Toast.makeText(getSherlockActivity().getApplicationContext(), "Button 1 in custom view", Toast.LENGTH_SHORT).show();
		}else if(viewId == R.id.button_custom_button2){
			Toast.makeText(getSherlockActivity().getApplicationContext(), "Button 2 in custom view", Toast.LENGTH_SHORT).show();
		}else if(viewId == R.id.button_show_content){
			if(mShowCheckbox){
				mCheckBoxDemo.setVisibility(View.VISIBLE);
				mImageViewDemo.setVisibility(View.GONE);
				mShowCheckbox = false;
			}else{
				mCheckBoxDemo.setVisibility(View.GONE);
				mImageViewDemo.setVisibility(View.VISIBLE);
				mShowCheckbox = true;
			}
		}else if(viewId == R.id.button_more_fragment){
			if(mListener != null){
				mListener.onFragmentChanged(R.layout.view_pager_layout, null);
			}
		}else if(viewId == R.id.button_nested_fragments){
			if(mListener != null){
				mListener.onFragmentChanged(R.layout.nested_parent, null);
			}
		}else if(viewId == R.id.button_add_view_item){
			if(mContentContainer != null){
				final Long num = System.currentTimeMillis();
				CustomItemTemplate cit = new CustomItemTemplate(getSherlockActivity().getApplicationContext(), 
						R.drawable.ic_launcher, R.string.app_name);
				cit.setText(Long.toString(num), String.valueOf(num % 100000)); 
				mContentContainer.addView(cit);
				
				// remove a view
				// mContentContainer.removeView(cit);
			}
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if(v == null) return false;
		final int viewId = v.getId();
		if(viewId == R.id.button_context_mode){
			if (mActionMode != null) {
			     return false;
		    }
			
			// Note that ActionMode only works on API level 11 and above
			// http://developer.android.com/guide/topics/ui/menus.html#context-menu
			// with ActionBarSherlock, ActionMode can work fine on Android 2.x
			
			// Start the CAB using the ActionMode.Callback defined above
			mActionMode = getSherlockActivity().startActionMode(mActionModeCallback);
		    v.setSelected(true);
		    return true;
		}
		return false;
	}
	
	protected Object mActionMode = null;
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.session_menu, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			final int itemId = item.getItemId();
			if(itemId == R.id.session_menu_alarm){
				Toast.makeText(getSherlockActivity().getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
				mode.finish(); // Action picked, so close the CAB			
				return true;
			}
			return false;
		}

		// Called when the user exits the action mode
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};



	@Override
	public void onPause() {
		super.onPause();
		if(mBarCustomView != null){
			mBarCustomView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if(mBarCustomView != null){
			mBarCustomView.setVisibility(View.VISIBLE);			
		}
	}
	
	
	
	
}
