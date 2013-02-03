package fi.metropolia.android.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

public class LruCacheExampleFragment extends SherlockFragment{

	private final static String TAG = LruCacheExampleFragment.class.getSimpleName();
	
	private int[] mImageArray = {
			R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, 
			R.drawable.d5, R.drawable.d6, R.drawable.d7
	};
	
	private LruCache<String, Bitmap> mLruCache = null;
	private ViewPager mViewPager = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get max available VM memory, exceeding this amount will throw an
	    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
	    // int in its constructor.
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    Log.v(TAG, "Max memory: " + maxMemory);
	    
	    // Use 1/8th of the available memory for this memory cache.
	    final int cacheSize = maxMemory / 2;
	    Log.v(TAG, "Memory cache size: " + cacheSize);	
	    
		mLruCache = new LruCache<String, Bitmap>(cacheSize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// the number of bytes used to store this bitmap's pixels
				// The cache size will be measured in kilobytes rather than
	            // number of items.
				final int sizeOfObject = (value.getRowBytes() * value.getHeight()) / 1024;
				Log.v(TAG, "The size of the object: " + sizeOfObject);
				return sizeOfObject;
			}
		};
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		final ActionBar actionBar = getSherlockActivity().getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("Session 5");
		actionBar.setSubtitle("lrucache fragment");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lrucache_example, container, false); 
		mViewPager = (ViewPager)view.findViewById(R.id.view_pager_image_container);
		MyFragmentAdapter adapter = new MyFragmentAdapter(getFragmentManager(), mImageArray);
		mViewPager.setAdapter(adapter);
		
		// set a fixed margin
		mViewPager.setPageMargin(-200);
		mViewPager.setHorizontalFadingEdgeEnabled(true);
		mViewPager.setFadingEdgeLength(30);
		
		// Set the number of pages that should be retained to either 
		// side of the current page in the view hierarchy in an idle state.
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setCurrentItem(0);
		return view;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mLruCache != null){
			mLruCache.evictAll();
		}
		System.gc();
	}

	private class MyFragmentAdapter extends FragmentPagerAdapter {

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
			ImageViewFragment ivf = new ImageViewFragment();
			Bundle b = new Bundle();
			b.putInt("position", mIconResId[position]);
			ivf.setArguments(b);
			return ivf;
		}
	}
	
	private class ImageViewFragment extends SherlockFragment{

		 private int mImageResId = -1;
		 private ImageView mImageView = null;
		 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			System.gc();
			super.onCreate(savedInstanceState);
			Bundle b = getArguments();
			if(b != null){
				mImageResId = b.getInt("position", -1);	
			}
		}		 
		 
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.image_cell, container, false); 
			mImageView = (ImageView)view.findViewById(R.id.image_view_cell);
			Bitmap b = mLruCache.get(String.valueOf(mImageResId));
			if(b != null){
				mImageView.setImageBitmap(b);
			}else{
				mLruCache.put(String.valueOf(mImageResId), BitmapFactory.decodeResource(view.getResources(), mImageResId));
				mImageView.setImageBitmap(mLruCache.get(String.valueOf(mImageResId)));
			}
			Log.v(TAG, "total memory: " + (int) (Runtime.getRuntime().totalMemory() / 1024));
			return view;
		}
		
	 }
}
