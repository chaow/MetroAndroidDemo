package fi.metropolia.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

import fi.metropolia.android.demo.listener.OnFragmentChangedListener;

public class Session5RootFragment extends SherlockFragment implements OnClickListener{
	
	private final static String TAG = Session5RootFragment.class.getSimpleName();
	
	private OnFragmentChangedListener mListener = null;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mListener = (OnFragmentChangedListener) activity;			
		}catch(Exception e){
			Log.e(TAG, "error", e);
		}
	} 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.session5_root, container, false); 
		((Button)view.findViewById(R.id.button_basic_gesture)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_show_lrucache_example)).setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onClick(View view) {
		final int viewId = view.getId();
		if(viewId == R.id.button_basic_gesture){
			if(mListener != null){
				mListener.onFragmentChanged(R.layout.touch_example, null);
			}
		}else if(viewId == R.id.button_show_lrucache_example){
			if(mListener != null){
				mListener.onFragmentChanged(R.layout.lrucache_example, null);
			}
		}
	}
	
	

}
