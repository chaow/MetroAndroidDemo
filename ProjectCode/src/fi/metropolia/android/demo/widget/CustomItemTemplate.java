package fi.metropolia.android.demo.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fi.metropolia.android.demo.R;

public class CustomItemTemplate extends RelativeLayout implements OnClickListener{

	private CheckBox mCheckBox = null;
	private TextView mLabel = null;
	private TextView mContentIndicater = null;
	
	private CustomItemTemplate(Context context) {
		super(context);
	}	
	
	public CustomItemTemplate(Context context, int iconResId, int titleStringResId){
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view= inflater.inflate(R.layout.custom_view_item_template, this);
		view.setClickable(true);
		view.setOnClickListener(this);
		mCheckBox = (CheckBox) view.findViewById(R.id.check_box_indicator);
		mCheckBox.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconResId, 0);
		mLabel = (TextView) view.findViewById(R.id.text_view_content_title);
		mLabel.setText(titleStringResId);
		mContentIndicater = (TextView) view.findViewById(R.id.text_view_content_indicator);
	}

	@Override
	public void onClick(View v) {
		mCheckBox.setChecked(!mCheckBox.isChecked());
	}

	public void setText(final String label, final String contentIndicator){
		if(!TextUtils.isEmpty(label)){
			mLabel.setText(label);
		}
		if(!TextUtils.isEmpty(contentIndicator)){
			mContentIndicater.setText(contentIndicator);
		}
	}
	
}
