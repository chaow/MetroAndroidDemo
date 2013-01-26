package fi.metropolia.android.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CButton extends Button implements OnClickListener, OnLongClickListener{

	public CButton(Context context) {
		super(context);
		init();
	}

	public CButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		setOnClickListener(this);
		setOnLongClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onLongClick(View v) {
		Toast.makeText(getContext(), "Long Clicked!", Toast.LENGTH_SHORT).show();
		return false;
	}

}
