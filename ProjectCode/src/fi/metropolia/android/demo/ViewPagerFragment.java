package fi.metropolia.android.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

public class ViewPagerFragment extends SherlockFragment{
	
	private int[] mImageArray = {
			R.drawable.d1, R.drawable.d2, R.drawable.d3, 
			R.drawable.d4, R.drawable.d5
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// custom the action bar
		final ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Session 4");
		actionBar.setSubtitle("view pager with fragments");
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.view_pager_layout, container, false); 
		ViewPager viewPager = (ViewPager)view.findViewById(R.id.view_pager_container);
		MyFragmentAdapter adapter = new MyFragmentAdapter(getFragmentManager(), mImageArray);
		viewPager.setAdapter(adapter);
		
		// Set the number of pages that should be retained to either 
		// side of the current page in the view hierarchy in an idle state.
		viewPager.setOffscreenPageLimit(1);
		viewPager.setCurrentItem(0);
		return view;
	}
	
	
	// http://android-developers.blogspot.fi/2011/08/horizontal-view-swiping-with-viewpager.html
	public static class MyFragmentAdapter extends FragmentPagerAdapter {

		private int[] mIconResId;
		
		public MyFragmentAdapter(FragmentManager fm, int[] iconResId) {
			super(fm);
			mIconResId = iconResId;
		}

		@Override
		public int getCount() {
			return mIconResId.length;
		}

		@Override
		public Fragment getItem(int position) {
			return ImageFragment.newInstance(mIconResId[position]);
		}
	}
	
	 
	 public static class ImageFragment extends SherlockFragment{

		 private int mIconResId = -1;
		 
		 static ImageFragment newInstance(final int iconResId){
			 ImageFragment f = new ImageFragment();
			 Bundle b = new Bundle();
			 b.putInt("iconResId", iconResId);
			 f.setArguments(b);
			 return f; 
		 }
		 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle b = getArguments();
			if(b != null){
				mIconResId = b.getInt("iconResId", -1);	
			}
		}		 
		 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			ImageView im = new ImageView(container.getContext());
			im.setBackgroundResource(mIconResId);
			return im;
		}
	 }
	 
}