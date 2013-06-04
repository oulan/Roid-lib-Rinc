package net.rinc.library.widget.view;

import net.rinc.library.R;
import net.rinc.library.util.SysUtil;
import net.rinc.library.widget.dialog.OptionDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RCComboBox extends ViewGroup{
	private TextView tv;
	private String[] items;
	private Listener listener;
	
	/**
	 * 
	 * @param context
	 */
	public RCComboBox(Context context) {
		super(context);
		init(context,null);
	}
	
	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public RCComboBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}
	
	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public RCComboBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context,attrs);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) { 
        for (int i = 0; i < getChildCount(); i++) {  
            View child = getChildAt(i);  
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());  
        }
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int childCount = getChildCount() ;  
		for(int i=0 ;i<childCount ;i++){  
		  View child = getChildAt(i) ;  
		  child.measure(widthMeasureSpec, heightMeasureSpec);
		 }
	}
	
	private void init(final Context context, AttributeSet attrs){
		View rootView = LayoutInflater.from(context).inflate(R.layout.combo_box, null);
		tv=(TextView) rootView.findViewById(R.id.tv);
		if(attrs!=null){
			TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.RCComboBox);
			int resouceId = typedArray.getResourceId(R.styleable.RCComboBox_defaultText, 0);
			tv.setText(resouceId > 0 ? typedArray.getResources().getText(resouceId)
					: typedArray.getString(R.styleable.RCComboBox_defaultText));
			tv.setTextColor(typedArray.getColor(R.styleable.RCComboBox_textColor, Color.BLACK));
			tv.setTextSize(SysUtil.px2dip(context, typedArray.getDimension(R.styleable.RCComboBox_textSize, 15)));
			typedArray.recycle();
		}
		tv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(items!=null&&listener!=null){
					new OptionDialog(context,
							context.getString(R.string.select_item),
							items,new OptionDialog.Listener(){
								@Override
								public void onItemClick(int position) {
									tv.setText(items[position]);
									listener.onItemSeleted(position);
								}
								@Override
								public void onCancel() {
								}
					}).show();
				}
			}
		});
		this.addView(rootView);
	}
	
	/**
	 * 
	 * @param color
	 */
	public void setTextColor(int color){
		tv.setTextColor(color);
	}
	
	/**
	 * 
	 * @param size
	 */
	public void setTextSize(float size){
		tv.setTextSize(size);
	}
	
	/**
	 * 
	 * @param items
	 * @param listener
	 */
	public void setItems(String[] items, Listener listener){
		this.items=items;
		this.listener=listener;
	}
	
	/**
	 * 
	 */
	public interface Listener{
		public void onItemSeleted(int position);
	}
}