package net.rinc.library.common.reference.social.weibo;

import com.weibo.sdk.android.sso.SsoHandler;

import net.rinc.library.base.RCActivity;
import net.rinc.library.common.reference.social.weibo.WeiboHelper.ReqHandler;
import net.rinc.library.util.UiUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WeiboDemoActivity extends RCActivity{
	private WeiboHelper weibo;
	private SsoHandler ssoHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		weibo = WeiboHelper.getInstance(this);
		if(weibo.isTokenAvailable(this)){
        	sendWeibo();
        }else{
        	ssoHandler=weibo.auth(this, new ReqHandler(){
				@Override
				public void onSucceed() {
					sendWeibo();
				}
				@Override
				public void onFail(String error) {
					sendMsg("授权失败:"+error);
				}
        	});
        }
	}
	
	private void sendWeibo(){
    	weibo.sendText(this,"content","32.0","120.0",new ReqHandler(){
			@Override
			public void onSucceed() {
				sendMsg("成功分享到新浪微博");
			}
			@Override
			public void onFail(String error) {
				sendMsg(error);
			}
		});
    }
	
	private void sendMsg(String msg){
		Message message=new Message();
		message.obj=msg;
		handler.sendMessage(message);
	}
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			UiUtil.toast(WeiboDemoActivity.this,msg.obj.toString());
		}
	};
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        weibo.onActivityResult(ssoHandler, requestCode, resultCode, data);
	}
}