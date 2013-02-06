package fi.metropolia.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

public class TouchExampleFragment extends SherlockFragment implements View.OnTouchListener{

	private static final String TAG = TouchExampleFragment.class.getSimpleName();
	
	private ImageView mImageView = null;
    private ViewGroup mDragRoot = null;

    private int _xDelta;
    private int _yDelta;
    
    private VelocityTracker mVelocity = null;
    
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		final ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Session 5");
		actionBar.setSubtitle("touch fragment");
	}
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.touch_example, container, false); 
		mDragRoot = (ViewGroup)view.findViewById(R.id.drag_root);
		mImageView = (ImageView)view.findViewById(R.id.image_view_gesture);
		mImageView.setOnTouchListener(this);
		
		RelativeLayout.LayoutParams layoutParams 
			= new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    layoutParams.leftMargin = 50;
	    layoutParams.topMargin = 50;
	    layoutParams.bottomMargin = -250;
	    layoutParams.rightMargin = -250;
	    mImageView.setLayoutParams(layoutParams);
		
		return view;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		final int X = (int) event.getRawX();
	    final int Y = (int) event.getRawY();
		final int action = (event.getAction() & MotionEvent.ACTION_MASK);
		switch(action){
			case MotionEvent.ACTION_DOWN:
				RelativeLayout.LayoutParams lParams 
					= (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
	            _xDelta = X - lParams.leftMargin;
	            _yDelta = Y - lParams.topMargin;
				if(mVelocity == null){
					mVelocity = VelocityTracker.obtain();
				}else{
					mVelocity.clear();
				}
				Log.v(TAG, "down " + X + " " + Y);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				Log.v(TAG, "pointer down " + X + " " + Y);
				break;
			case MotionEvent.ACTION_UP:
				mVelocity.addMovement(event);
				mVelocity.computeCurrentVelocity(1000);
				Log.v(TAG, "Velocity " + mVelocity.getXVelocity() + " " + mVelocity.getYVelocity());
				Log.v(TAG, "up " + X + " " + Y);
				break;
			case MotionEvent.ACTION_POINTER_UP:
				Log.v(TAG, "pointer up " + X + " " + Y);
				break;
			case MotionEvent.ACTION_MOVE:
				mVelocity.addMovement(event);
				mVelocity.computeCurrentVelocity(1000);
				RelativeLayout.LayoutParams layoutParams 
					= (RelativeLayout.LayoutParams) mImageView.getLayoutParams();
				layoutParams.leftMargin = X - _xDelta;
				layoutParams.topMargin = Y - _yDelta;
				layoutParams.rightMargin = -250;
				layoutParams.bottomMargin = -250;
				mImageView.setLayoutParams(layoutParams);
				Log.v(TAG, "move " + X + " " + Y);
				break;
			case MotionEvent.ACTION_CANCEL:
				mVelocity.recycle();
				break;				
		}
		mDragRoot.invalidate();
		return true;
	}	
}
