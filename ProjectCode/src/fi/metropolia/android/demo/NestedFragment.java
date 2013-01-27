package fi.metropolia.android.demo;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

public class NestedFragment extends SherlockFragment implements OnClickListener, OnBackStackChangedListener, OnKeyListener{

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		final ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Session 4");
		actionBar.setSubtitle("nested fragment");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.nested_parent, container, false); 
		((Button)view.findViewById(R.id.button_nested_parent)).setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		if(v == null) return;
		final int viewId = v.getId();
		if(viewId == R.id.button_nested_parent){
			addNestedFragment();
		}
	}

	private void addNestedFragment(){
		// set view is current focus view
		getView().setFocusableInTouchMode(true); 
		getView().requestFocus();
		getView().setOnKeyListener(this);
		
		getChildFragmentManager().addOnBackStackChangedListener(this);
		
		ChildFragment cf = new ChildFragment();
		// we get the 'childFragmentManager' for our transaction
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //supply the tag 'child' to the backstack
        transaction.addToBackStack("child");
        // add our new nested fragment
        transaction.replace(R.id.child_screen_container, cf, "child");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        // commit the transaction
        transaction.commit();
	}

	@Override
	public void onBackStackChanged() {
		final int entryCount = getChildFragmentManager().getBackStackEntryCount();
		if(entryCount == 0){
			resetActionBar();
		}
	}

	private void resetActionBar(){
		final ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setTitle("Session 4");
		actionBar.setSubtitle("nested fragment");
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			final int entryCount = getChildFragmentManager().getBackStackEntryCount();
			if(entryCount == 0){
				resetActionBar();
			}
			if(entryCount > 0){
				getChildFragmentManager().popBackStack();
				return true;
			}else{
				return false;
			}
		}
		return false;
	}	
	
	public static class ChildFragment extends SherlockFragment implements OnClickListener{
				
		private long mTimestamp = -1L;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.nested_child, container, false); 
			((Button)view.findViewById(R.id.button_nested_child)).setOnClickListener(this);
			TextView tv = (TextView)view.findViewById(R.id.text_view_nested_child);
			mTimestamp = System.currentTimeMillis();
			tv.setText(String.format(Locale.getDefault(), String.valueOf(tv.getText()), mTimestamp));
			return view;
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			final ActionBar actionBar = getSherlockActivity().getSupportActionBar();
			actionBar.setTitle("Nested fragment");
			actionBar.setSubtitle(String.valueOf(mTimestamp)); 
		}

		@Override
		public void onClick(View v) {
			if(v == null) return;
			final int viewId = v.getId();
			if(viewId == R.id.button_nested_child){
				getParentFragment().getChildFragmentManager().popBackStack();
			}
		}
	}

	
}
