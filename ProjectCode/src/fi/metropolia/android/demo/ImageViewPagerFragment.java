package fi.metropolia.android.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;

public class ImageViewPagerFragment extends SherlockFragment{
	
	static ImageViewPagerFragment newInstance(){
		return new ImageViewPagerFragment();
	}
	
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
		actionBar.setTitle("Session 2");
		actionBar.setSubtitle("view pager");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.view_pager_layout, container, false); 
		ViewPager viewPager = (ViewPager)view.findViewById(R.id.view_pager_container);
		ImagePagerAdapter adapter = new ImagePagerAdapter(mImageArray);
		viewPager.setAdapter(adapter);
		
		// Set the number of pages that should be retained to either 
		// side of the current page in the view hierarchy in an idle state.
		viewPager.setOffscreenPageLimit(1);

		return view;
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private int[] mImageArray;

		public ImagePagerAdapter(final int[] resIdArray) {
			mImageArray = resIdArray;
		}

		@Override
		public int getCount() {
			return mImageArray.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((ImageView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Context context = container.getContext();
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(mImageArray[position]);
			((ViewPager) container).addView(imageView);
			return imageView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}
	}
	
}
