package fi.metropolia.android.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;

public class SessionListFragment extends SherlockListFragment{

	private static final String TAG = SessionListFragment.class.getSimpleName();

	private String[] mSessionArray = {
			"Session 1", "Session 2", "Session 3"		
	};
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.v(TAG, "onAttach");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {	
		super.onActivityCreated(savedInstanceState);
		Log.v(TAG, "onActivityCreated");
		setListAdapter(new ArrayAdapter<String>(getSherlockActivity().getApplicationContext(),
                					android.R.layout.simple_list_item_1, mSessionArray));
		getView().setBackgroundColor(Color.BLACK);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		switch(position){
			case 0:
				// session 1 
				startNewActivity(Session1Activity.class);
				break;
			case 1:
				// session 2 
				startNewActivity(Session2Activity.class);
				break;
			default:
				Toast.makeText(getSherlockActivity().getApplicationContext(), "Nothing here.", Toast.LENGTH_SHORT).show();
				break;
			}
	}

	private void startNewActivity(Class<?> cls){
		Intent it = new Intent(getSherlockActivity().getApplicationContext(), cls);
		getSherlockActivity().startActivity(it);
	}	
	
	@Override
	public void onDetach() {
		super.onDetach();
		Log.v(TAG, "onDetach");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.v(TAG, "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.v(TAG, "onPause");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.v(TAG, "onResume");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.v(TAG, "onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.v(TAG, "onStop");
	}
	
}
