package net.rinc.library.widget.dialog;

import net.rinc.library.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptDialog extends RCDialog{
	private Listener listener;
	private String title, msg;
	private Context context;
	private Button btn_left, btn_right;
	private TextView tv_msg;
	private String leftBtnStr, rightBtnStr;
	private int linkify=-1;
	
	/**
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param leftBtnStr
	 * @param rightBtnStr
	 * @param listener
	 */
	public PromptDialog(Context context, String title, String msg, String leftBtnStr, String rightBtnStr, Listener listener) {
		super(context);
		this.title=title;
		this.msg=msg;
		this.leftBtnStr=leftBtnStr;
		this.rightBtnStr=rightBtnStr;
		this.listener=listener;
		this.context=context;
		super.createView();
		super.setCanceledOnTouchOutside(false);
	}
	
	/**
	 * 
	 * @param context
	 * @param title
	 * @param msg
	 * @param msgLinkify
	 * @param leftBtnStr
	 * @param rightBtnStr
	 * @param listener
	 */
	public PromptDialog(Context context, String title, String msg, int msgLinkify, 
			String leftBtnStr, String rightBtnStr, Listener listener) {
		super(context);
		this.title=title;
		this.msg=msg;
		this.linkify=msgLinkify;
		this.leftBtnStr=leftBtnStr;
		this.rightBtnStr=rightBtnStr;
		this.listener=listener;
		this.context=context;
		super.createView();
		super.setCanceledOnTouchOutside(false);
	}
	
	@Override
	protected View getView() {
		View view=LayoutInflater.from(context).inflate(R.layout.dialog_prompt, null);
		TextView tv_title=(TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(title);
		tv_msg=(TextView) view.findViewById(R.id.tv_msg);
		tv_msg.setAutoLinkMask(linkify);
		tv_msg.setText(msg);
		btn_left=(Button) view.findViewById(R.id.btn_left);
		btn_left.setText(leftBtnStr);
		btn_right=(Button) view.findViewById(R.id.btn_right);
		btn_right.setText(rightBtnStr); 
		btn_left.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view) {
				PromptDialog.this.dismiss();
				listener.onLeftClick();
			}    		
    	});
		btn_right.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view) {
				PromptDialog.this.dismiss();
				listener.onRightClick();
			}    		
    	});
		return view;
	}
	
	/**
	 * 
	 */
	public interface Listener{
		public void onLeftClick();
		public void onRightClick();
	}
}