package fi.metropolia.android.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.actionbarsherlock.app.SherlockListFragment;

import fi.metropolia.android.demo.listener.AlertDialogListener;
import fi.metropolia.android.demo.listener.OnFragmentChangedListener;
import fi.metropolia.android.demo.model.Person;

public class Session2ContentFragment extends SherlockListFragment implements OnClickListener{

	private final static String TAG = Session2ContentFragment.class.getSimpleName();
	
	private LayoutAnimationController mLayoutAnim = null; 
	
	private String[] mSampleArray = {
			"Single Fragment", "Standard tabs", "Tabs with swipe support"		
	};
	
	private OnFragmentChangedListener mListener = null;
	
	private boolean mIsBlinking = false;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mListener = (OnFragmentChangedListener) activity;			
		}catch(Exception e){
			Log.e(TAG, "error", e);
		}
		
		mLayoutAnim = AnimationUtils.loadLayoutAnimation(activity.getApplicationContext(), R.anim.list_layout_controller);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.session2, container, false); 
		((Button)view.findViewById(R.id.button_dialog1)).setOnClickListener(this);
		((Button)view.findViewById(R.id.button_dialog2)).setOnClickListener(this);
		setListAdapter(new ArrayAdapter<String>(getSherlockActivity().getApplicationContext(),
                				android.R.layout.simple_list_item_1, mSampleArray));
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		Person p = b.getParcelable("person");
		if(p != null){
			Log.v(TAG, p.toString());
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		getListView().setLayoutAnimation(mLayoutAnim);
		getListView().startLayoutAnimation();
	}
	
	@Override
	public void onClick(View v) {
		final int viewId = v.getId();
		if(viewId == R.id.button_dialog1){
			SimpleDialog sd = new SimpleDialog();
			sd.setAlertDialogListener(new AlertDialogListener(){
				@Override
				public void onPositiveClick() {
					Toast.makeText(getSherlockActivity().getApplicationContext(), "Positive", Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onNegativeClick() {
					Toast.makeText(getSherlockActivity().getApplicationContext(), "Negative", Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onCancel() {
					Toast.makeText(getSherlockActivity().getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
				}
			});
			sd.show(getFragmentManager(), "simpleDialog");
		} else if(viewId == R.id.button_dialog2){
			if(mIsBlinking){
				v.clearAnimation();
				mIsBlinking = false;
			}else{
				Animation anim = AnimationUtils.loadAnimation(getSherlockActivity().getApplicationContext(), R.anim.blinking);
				v.startAnimation(anim);				
				mIsBlinking = true;
			}
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Bundle b = null;
		switch(position){
			case 0:
				if(mListener != null){
					mListener.onFragmentChanged(R.layout.single_fragment_layout, null);
				}
				break;
			case 1:
				b = new Bundle();
				b.putBoolean("swipe", false);
				startNewActivity(ActionBarTabActivity.class, b);
				break;
			case 2:
				b = new Bundle();
				b.putBoolean("swipe", true);
				startNewActivity(ActionBarTabActivity.class, b);
				break;
			default:
				Toast.makeText(getSherlockActivity().getApplicationContext(), "Nothing here.", Toast.LENGTH_SHORT).show();
				break;
		}
	}
	
	private void startNewActivity(Class<?> cls, Bundle b){
		Intent it = new Intent(getSherlockActivity().getApplicationContext(), cls);
		if(b != null){
			it.putExtras(b);
		}
		getSherlockActivity().startActivity(it);
	}
	
	
	
	public static class SimpleDialog extends SherlockDialogFragment{
		
		private AlertDialogListener mListener = null;
		
		public SimpleDialog(){
			super();
			setCancelable(true);
		}
		
		public void setAlertDialogListener(final AlertDialogListener listener){
			mListener = listener;
		}
		
		@Override
		public void onCancel(DialogInterface dialog) {
			if(mListener != null){
				mListener.onCancel();			
			}
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
	    	builder.setTitle(R.string.app_name);
	    	builder.setIcon(android.R.drawable.ic_dialog_info);
	    	builder.setMessage(R.string.simple_dialog_content);
	    	builder.setPositiveButton(R.string.ok, 
	    			new DialogInterface.OnClickListener() {					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mListener.onPositiveClick();
						}
					});
	    	builder.setNegativeButton(R.string.cancel,     			
	    			new DialogInterface.OnClickListener() {					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mListener.onNegativeClick();
						}
					});				
			return builder.create(); 
		}
	}
	
	
}
