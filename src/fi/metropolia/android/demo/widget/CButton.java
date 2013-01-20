package fi.metropolia.android.demo.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CButton extends Button implements OnClickListener{

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
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if(focused){
			setTextColor(Color.BLUE);
		} else {
			setTextColor(Color.RED);
		}
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
	}

}
