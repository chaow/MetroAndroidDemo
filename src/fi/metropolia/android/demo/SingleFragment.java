package fi.metropolia.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

import fi.metropolia.android.demo.listener.OnFragmentChangedListener;

public class SingleFragment extends SherlockFragment implements OnClickListener{
	
	private final static String TAG = SingleFragment.class.getSimpleName();
	
	private OnFragmentChangedListener mListener = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mListener = (OnFragmentChangedListener) activity;			
		}catch(Exception e){
			Log.e(TAG, "error", e);
		}
		final ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Session 2");
		actionBar.setSubtitle("single frament");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.single_fragment_layout, container, false); 
		((TextView)view.findViewById(R.id.text_view_indicator)).setText(Long.toString(System.currentTimeMillis()));
		((Button)view.findViewById(R.id.button_dump)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_view_pager)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_rotate_animation)).setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View v) {
		final int viewId = v.getId();
		if(viewId == R.id.button_dump){
			if(mListener != null){
				mListener.onFragmentChanged(R.layout.single_fragment_layout, null);
			}
		}else if(viewId == R.id.button_rotate_animation){
			Animation anim = AnimationUtils.loadAnimation(getSherlockActivity().getApplicationContext(), R.anim.rotate);
			v.startAnimation(anim);
			// clean animation, if needed
			// v.clearAnimation();
		}else if(viewId == R.id.button_view_pager){
			mListener.onFragmentChanged(R.layout.view_pager_layout, null);
		}
	}
	
	
}
