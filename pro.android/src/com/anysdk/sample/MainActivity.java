package com.anysdk.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.anysdk.framework.AdsWrapper;
import com.anysdk.framework.IAPWrapper;
import com.anysdk.framework.PluginWrapper;
import com.anysdk.framework.PushWrapper;
import com.anysdk.framework.ShareWrapper;
import com.anysdk.framework.SocialWrapper;
import com.anysdk.framework.UserWrapper;
import com.anysdk.framework.java.AnySDK;
import com.anysdk.framework.java.AnySDKAds;
import com.anysdk.framework.java.AnySDKAnalytics;
import com.anysdk.framework.java.AnySDKIAP;
import com.anysdk.framework.java.AnySDKListener;
import com.anysdk.framework.java.AnySDKParam;
import com.anysdk.framework.java.AnySDKPush;
import com.anysdk.framework.java.AnySDKShare;
import com.anysdk.framework.java.AnySDKSocial;
import com.anysdk.framework.java.AnySDKUser;
import com.anysdk.framework.java.ToolBarPlaceEnum;



import android.R.bool;
import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity   implements OnClickListener{
	private static final String TAG_STRING = "ANYSDK";
	private static Activity mAct = null;
	private static Handler mUIHandler = null;
	private static boolean mFirst = false;
	private static Dialog myDialog = null;
	private final static String nd91Channle = "000007";
	private Map<String, String> mProductionInfo = null;
	private Map<String, String> mShareInfo = null;
	private ArrayList<String> mTagInfo = null;
	private Map<String, String> mArchInfo = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAct = this;
        mUIHandler = new Handler();
	
	    
		//初始化AnySDK
		initAnySDK();
		//开启推送服务
        AnySDKPush.getInstance().startPush();
        //初始化相关数据
        initData();
        AnySDKAds.getInstance().preloadAds(AdsWrapper.AD_TYPE_FULLSCREEN);


        

        Button btnUser = (Button) this.findViewById(R.id.userSystem);
        btnUser.setOnClickListener(this);


        Button btnIAP = (Button) this.findViewById(R.id.iapSystem);
        btnIAP.setOnClickListener(this);
        
        Button btnShare = (Button) this.findViewById(R.id.shareSystem);
        btnShare.setOnClickListener(this);
        
        Button btnAds = (Button) this.findViewById(R.id.adsSystem);
        btnAds.setOnClickListener(this);
        
        Button btnSocial = (Button) this.findViewById(R.id.socialSystem);
        btnSocial.setOnClickListener(this);
        
        Button btnPush = (Button) this.findViewById(R.id.pushSystem);
        btnPush.setOnClickListener(this);

        LinearLayout main = (LinearLayout) this.findViewById(R.id.main);
       
        if (AnySDK.getInstance().getChannelId().equals(nd91Channle)) {
        	Button shop = new Button(this);
        	shop.setOnClickListener(this);
        	shop.setTag(nd91Channle);
        	shop.setText("91 shop");
        	main.addView(shop);
		}
        Log.d(TAG_STRING, "getCustomParam"+AnySDK.getInstance().getCustomParam());
        Hashtable<String, String> info = new Hashtable<String, String>();
//        info.put("Level_Id", "123456");
//        AnySDKParam param(info);
//        AnySDKAnalytics.getInstance().isFunctionSupported("startLevel",&param,null);
        Log.d(TAG_STRING, String.valueOf(AnySDKAds.getInstance().isAdTypeSupported(AdsWrapper.AD_TYPE_BANNER)));
        
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		PluginWrapper.onNewIntent(intent);
		super.onNewIntent(intent);
	}

	void initAnySDK()
	{
	    /**
	     * appKey、appSecret、privateKey不能使用Sample中的值，需要从打包工具中游戏管理界面获取，替换
	     * oauthLoginServer参数是游戏服务提供的用来做登陆验证转发的接口地址。
	     */
		String appKey = "AEE563E8-C007-DC32-5535-0518D941D6C2";
	    String appSecret = "b9fada2f86e3f73948f52d9673366610";
	    String privateKey = "0EE38DB7E37D13EBC50E329483167860";
	    String oauthLoginServer = "http://oauth.anysdk.com/api/OauthLoginDemo/Login.php";
		AnySDK.getInstance().initPluginSystem(this, appKey, appSecret, privateKey, oauthLoginServer);
		
		/**
		 * 对用户系统、支付系统、广告系统、统计系统、社交系统、推送系统、分享系统设置debug模式
		 * 注意：debug模式开启，即开启了SDK的测试模式，所以上线前务必把debug模式设置为false
		 */
		AnySDKUser.getInstance().setDebugMode(true);
		AnySDKPush.getInstance().setDebugMode(true);
		AnySDKAnalytics.getInstance().setDebugMode(true);
		AnySDKAds.getInstance().setDebugMode(true);
		AnySDKShare.getInstance().setDebugMode(true);
		AnySDKSocial.getInstance().setDebugMode(true);
		AnySDKIAP.getInstance().setDebugMode(true);
		/**
		 * 初始化完成后，必须立即为系统设置监听，否则无法即使监听到回调信息
		 */
		setListener();
		
	}
	
	public void setListener() {
		/**
		 * 为用户系统设置监听
		 */
		AnySDKUser.getInstance().setListener(new AnySDKListener() {
			
			@Override
			public void onCallBack(int arg0, String arg1) {
				Log.d(String.valueOf(arg0), arg1);
				switch(arg0)
				{
				case UserWrapper.ACTION_RET_INIT_SUCCESS://初始化SDK成功回调
					break;
				case UserWrapper.ACTION_RET_INIT_FAIL://初始化SDK失败回调
					Exit();
					break;
				case UserWrapper.ACTION_RET_LOGIN_SUCCESS://登陆成功回调
					showDialog(arg1, "User is  online");
					Log.d(TAG_STRING, String.valueOf(AnySDKUser.getInstance().isLogined()));
			        break;
				case UserWrapper.ACTION_RET_LOGIN_NO_NEED://登陆失败回调
				case UserWrapper.ACTION_RET_LOGIN_TIMEOUT://登陆失败回调
			    case UserWrapper.ACTION_RET_LOGIN_CANCEL://登陆取消回调
				case UserWrapper.ACTION_RET_LOGIN_FAIL://登陆失败回调
					showDialog(arg1, "fail");
					AnySDKAnalytics.getInstance().logError("login", "fail");
			    	break;
				case UserWrapper.ACTION_RET_LOGOUT_SUCCESS://登出成功回调
					break;
				case UserWrapper.ACTION_RET_LOGOUT_FAIL://登出失败回调
					showDialog(arg1  , "登出失败");
					break;
				case UserWrapper.ACTION_RET_PLATFORM_ENTER://平台中心进入回调
					break;
				case UserWrapper.ACTION_RET_PLATFORM_BACK://平台中心退出回调
					break;
				case UserWrapper.ACTION_RET_PAUSE_PAGE://暂停界面回调
					break;
				case UserWrapper.ACTION_RET_EXIT_PAGE://退出游戏回调
			         Exit();
					break;
				case UserWrapper.ACTION_RET_ANTIADDICTIONQUERY://防沉迷查询回调
					showDialog(arg1  , "防沉迷查询回调");
					break;
				case UserWrapper.ACTION_RET_REALNAMEREGISTER://实名注册回调
					showDialog(arg1  , "实名注册回调");
					break;
				case UserWrapper.ACTION_RET_ACCOUNTSWITCH_SUCCESS://切换账号成功回调
					break;
				case UserWrapper.ACTION_RET_ACCOUNTSWITCH_FAIL://切换账号失败回调
					break;
				default:
					break;
				}
			}
		});
		
		/**
		 * 为支付系统设置监听
		 */
		AnySDKIAP.getInstance().setListener(new AnySDKListener() {
			
			@Override
			public void onCallBack(int arg0, String arg1) {
				Log.d(String.valueOf(arg0), arg1);
				String temp = "fail";
				switch(arg0)
				{
				case IAPWrapper.PAYRESULT_INIT_FAIL://支付初始化失败回调
					break;
				case IAPWrapper.PAYRESULT_INIT_SUCCESS://支付初始化成功回调
					break;
				case IAPWrapper.PAYRESULT_SUCCESS://支付成功回调
					temp = "Success";
					showDialog(temp, temp);
					break;
				case IAPWrapper.PAYRESULT_FAIL://支付失败回调
					showDialog(temp, temp);
					break;
				case IAPWrapper.PAYRESULT_CANCEL://支付取消回调
					showDialog(temp, "Cancel" );
					break;
				case IAPWrapper.PAYRESULT_NETWORK_ERROR://支付超时回调
					showDialog(temp, "NetworkError");
					break;
				case IAPWrapper.PAYRESULT_PRODUCTIONINFOR_INCOMPLETE://支付超时回调
					showDialog(temp, "ProductionInforIncomplete");
					break;
				/**
				 * 新增加:正在进行中回调
				 * 支付过程中若SDK没有回调结果，就认为支付正在进行中
				 * 游戏开发商可让玩家去判断是否需要等待，若不等待则进行下一次的支付
				 */
				case IAPWrapper.PAYRESULT_NOW_PAYING:
					showTipDialog();
					break;
				default:
					break;
				}
			}
		});
		
		/**
		 * 为广告系统设置监听
		 */
		AnySDKAds.getInstance().setListener(new AnySDKListener() {
			
			@Override
			public void onCallBack(int arg0, String arg1) {
				Log.d(String.valueOf(arg0), arg1);
				switch (arg0) {
				case AdsWrapper.RESULT_CODE_AdsDismissed://广告消失回调
					break;
				case AdsWrapper.RESULT_CODE_AdsReceived://接受到网络回调
					break;
				case AdsWrapper.RESULT_CODE_AdsShown://显示网络回调
					break;
				case AdsWrapper.RESULT_CODE_PointsSpendFailed://积分墙消费失败
					break;
				case AdsWrapper.RESULT_CODE_PointsSpendSucceed://积分墙消费成功
					break;
				case AdsWrapper.RESULT_CODE_OfferWallOnPointsChanged://积分墙积分改变
					break;
				case AdsWrapper.RESULT_CODE_NetworkError://网络出错
					break;

				default:
					break;
				}
				
			}
		});
		/**
		 * 为分享系统设置监听
		 */
		AnySDKShare.getInstance().setListener(new AnySDKListener() {
			
			@Override
			public void onCallBack(int arg0, String arg1) {
				Log.d(String.valueOf(arg0), arg1);
				switch (arg0) {
				case ShareWrapper.SHARERESULT_CANCEL://取消分享	
					break;
				case ShareWrapper.SHARERESULT_FAIL://分享失败
					break;
				case ShareWrapper.SHARERESULT_NETWORK_ERROR://分享网络出错
					break;
				case ShareWrapper.SHARERESULT_SUCCESS://分享结果成功
					break;

				default:
					break;
				}
				
			}
		});
		/**
		 * 为社交系统设置监听
		 */
		AnySDKSocial.getInstance().setListener(new AnySDKListener() {
			
			@Override
			public void onCallBack(int arg0, String arg1) {
				Log.d(String.valueOf(arg0), arg1);
				switch (arg0) {
				case SocialWrapper.SOCIAL_SIGNIN_FAIL://社交登陆失败
					break;
				case SocialWrapper.SOCIAL_SIGNIN_SUCCEED://社交登陆成功
					break;
				case SocialWrapper.SOCIAL_SIGNOUT_FAIL://社交登出失败
					break;
				case SocialWrapper.SOCIAL_SIGNOUT_SUCCEED://社交登出成功
					break;
				case SocialWrapper.SOCIAL_SUBMITSCORE_FAIL://提交分数失败
					break;
				case SocialWrapper.SOCIAL_SUBMITSCORE_SUCCEED://提交分数成功
					break;
				default:
					break;
				}
				
			}
		});

		/**
		 * 为推送系统设置监听
		 */
		AnySDKPush.getInstance().setListener(new AnySDKListener() {
			
			@Override
			public void onCallBack(int arg0, String arg1) {
				Log.d(String.valueOf(arg0), arg1);
				switch (arg0) {
				case PushWrapper.ACTION_RET_RECEIVEMESSAGE://接受到推送消息
					
					break;

				default:
					break;
				}
			}
		});
	}
	
	public void initData()
	{
        mProductionInfo = new HashMap<String, String>();
        mProductionInfo.put("Product_Price", "1");
        if(AnySDK.getInstance().getChannelId().equals("000016") || AnySDK.getInstance().getChannelId().equals("000009")|| AnySDK.getInstance().getChannelId().equals("000349")){
        	mProductionInfo.put("Product_Id", "10");
		}else{
			mProductionInfo.put("Product_Id", "monthly");
		}
        mProductionInfo.put("Product_Name","gold");
        mProductionInfo.put("Server_Id", "13");
        mProductionInfo.put("Product_Count", "1");
        mProductionInfo.put("Role_Id","1");
        mProductionInfo.put("Role_Name", "1");
        mProductionInfo.put("Role_Grade", "1");
        mProductionInfo.put("Role_Balance", "1");
        
        mShareInfo = new HashMap<String, String>();
        mShareInfo.put("title","ShareSDK是一个神奇的SDK");
        mShareInfo.put("titleUrl","http://sharesdk.cn");
        mShareInfo.put("site","ShareSDK");
        mShareInfo.put("siteUrl", "http://sharesdk.cn");
        mShareInfo.put("text", "ShareSDK集成了简单、支持如微信、新浪微博、腾讯微博等社交平台");
        mShareInfo.put("comment", "无");
        
        mArchInfo = new HashMap<String, String>();
        mArchInfo.put("rank", "friends");
        
        mTagInfo = new ArrayList<String>();
        mTagInfo.add("easy");
        mTagInfo.add("fast");
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {	
	    case R.id.userSystem:
	    	showUserAction();
	        break;
	    case R.id.iapSystem:
	    	showPayAction();
	        break;
	    case R.id.shareSystem:
	    	showShareAction();
	        break;
	    case R.id.adsSystem:
	    	showAdsAction();
	        break;
	    case R.id.socialSystem:
	    	showSocialAction();
	        break;
	    case R.id.pushSystem:
	    	showPushAction();
	        break;
	    case R.id.login:
	    	/**
			  * 调用登陆接口
			  */
	    	AnySDKUser.getInstance().login();
	    	myDialog.dismiss();
	        break;
	    case R.id.pay:
	    	{
	    		/**
	    		 * 支付接口调用
	   		  	 */
	    		ArrayList<String> idArrayList =  AnySDKIAP.getInstance().getPluginId();
	    		if (idArrayList.size() == 1) {
	    			AnySDKIAP.getInstance().payForProduct(idArrayList.get(0), mProductionInfo);
				}
	    		else {
	    			/**
	    			  * 多支付
	    			  */
					ChoosePayMode(idArrayList);
				}
	    		myDialog.dismiss();
	    	}
	        break;
	    case R.id.share:
	    	//调用分享接口
	    	AnySDKShare.getInstance().share(mShareInfo);
	    	myDialog.dismiss();
	        break;
	    case R.id.show:
	    	//调用显示banner广告
	    	AnySDKAds.getInstance().showAds(AdsWrapper.AD_TYPE_BANNER,1);
	    	AnySDKAds.getInstance().showAds(AdsWrapper.AD_TYPE_BANNER,2);
	    	myDialog.dismiss();
	        break;
	    case R.id.hide:
	    	//隐藏banner广告
	    	AnySDKAds.getInstance().hideAds(AdsWrapper.AD_TYPE_BANNER, 1);
	    	myDialog.dismiss();
	        break;
	    case R.id.submitScore:
	    	//社交提交分数
	    	AnySDKSocial.getInstance().submitScore("friend", 1);
	    	myDialog.dismiss();
	        break;
	    case R.id.showLeaderboard:
	    	//社交显示排行榜
	    	AnySDKSocial.getInstance().showAchievements();
	    	myDialog.dismiss();
	        break;
	    case R.id.unlockAchievement:
	    	{
	    		//解锁成就榜
		    	AnySDKSocial.getInstance().unlockAchievement(mArchInfo);
		    	myDialog.dismiss();
	    	}
	        break;
	    case R.id.showAchievements:
	    	//社交显示成就榜
	    	AnySDKSocial.getInstance().showAchievements();
	    	myDialog.dismiss();
	        break;
	    case R.id.closePush:
	    	//关闭推送系统
	    	AnySDKPush.getInstance().closePush();
	    	myDialog.dismiss();
	        break;
	    case R.id.setAlias:
	    	//设置别名
	    	AnySDKPush.getInstance().setAlias("AnySDK");
	    	myDialog.dismiss();
	        break;
	    case R.id.delAlias:
	    	//删除别名
	    	AnySDKPush.getInstance().delAlias("AnySDK");
	    	myDialog.dismiss();
	        break;
	    case R.id.setTags:
	    	//设置tags
	    	AnySDKPush.getInstance().setTags(mTagInfo);
	    	myDialog.dismiss();
	        break;
	    case R.id.delTags:
	    	//删除tag
	    	AnySDKPush.getInstance().delTags(mTagInfo);
	    	myDialog.dismiss();
	        break;
	    default:
	    	if (myDialog != null) {
	    		myDialog.dismiss();
			}
	    	
	    	if (arg0.getTag().equals("logout")) {
	    		//调用登出
	    		AnySDKUser.getInstance().callFunction("logout");
			}
		    else if (arg0.getTag().equals("enterPlatform")) {
		    	//调用显示平台中心
		    	AnySDKUser.getInstance().callFunction("enterPlatform");
			}
		    else if (arg0.getTag().equals("showToolBar")) {
		    	//显示悬浮窗
		    	AnySDKParam param = new AnySDKParam(ToolBarPlaceEnum.kToolBarTopLeft.getPlace());
		    	AnySDKUser.getInstance().callFunction("showToolBar", param);
			}
		    else if (arg0.getTag().equals("hideToolBar")) {
		    	//隐藏悬浮窗
		    	AnySDKUser.getInstance().callFunction("hideToolBar");
			}
		    else if (arg0.getTag().equals("accountSwitch")) {
		    	//切换账号
		    	AnySDKUser.getInstance().callFunction("accountSwitch");
			}
		    else if (arg0.getTag().equals("realNameRegister")) {
		    	//实名注册
		    	AnySDKUser.getInstance().callFunction("realNameRegister");

			}
		    else if (arg0.getTag().equals("antiAddictionQuery")) {
		    	//防沉迷查询
		    	AnySDKUser.getInstance().callFunction("antiAddictionQuery");
			}
		    else if (arg0.getTag().equals("submitLoginGameRole")) {
		    	//提交角色信息
		    	Map<String, String> map = new HashMap<String, String>();
		    	map.put("roleId", "123456");
		    	map.put("roleName","test");
		    	map.put("roleLevel", "10");
		    	map.put("zoneId", "123");
		    	map.put("zoneName", "test");
		    	map.put("dataType","1");
		    	map.put("ext","login"); 
				AnySDKParam param = new AnySDKParam(map);
		    	AnySDKUser.getInstance().callFunction("submitLoginGameRole",param);
			}
		    else if (arg0.getTag().equals(nd91Channle)) {
		    	showDialog("91 shop", "91 shop");
			}
	    	
	        break;
	       
	    }
	
	}
	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		PluginWrapper.onActivityResult(requestCode, resultCode, data);
	}

	public static void showDialog(String title, String msg) {
	        final String curMsg = msg;
	        final String curTitle = title;
	        
	        mUIHandler.post(new Runnable() {
	            @Override
	            public void run() {
	                new AlertDialog.Builder(mAct)
	                .setTitle(curTitle)
	                .setMessage(curMsg)
	                .setPositiveButton("Ok", 
	                        new DialogInterface.OnClickListener() {
	                            
	                            @Override
	                            public void onClick(DialogInterface dialog, int which) {
	                                
	                            }
	                        }).create().show();
	            }
	        });
	    }
	public void showTipDialog() {
        
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(mAct)
                .setTitle(R.string.paying_title)
                .setMessage(R.string.paying_message)
                .setPositiveButton("NO", 
                        new DialogInterface.OnClickListener() {
                            
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            	/**
                       		  	* 重置支付状态
                       		  	*/
                                AnySDKIAP.getInstance().resetPayState();
                            }
                        })
                .setNegativeButton("YES", 
                        new DialogInterface.OnClickListener() {
                            
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                
                            }
                        }).create().show();
            }
        });
    }
	 public static void Exit() {
		 mAct.finish();
		 System.exit(0);
		 
	    }
	 @Override
	 protected void onDestroy() {
		 super.onDestroy();
		 /**
		  * 对用户系统SDK资源进行释放
		  */
	     AnySDKUser.getInstance().callFunction("destroy");
	     /**
		  * 对AnySDK资源进行释放
		  */
	     AnySDK.getInstance().release();
	 };
	 
	 public boolean isAppOnForeground() {
			ActivityManager activityManager = (ActivityManager) getApplicationContext()
					.getSystemService(Context.ACTIVITY_SERVICE);
			String packageName = getApplicationContext().getPackageName();
			List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
			if (appProcesses == null)
				return false;
			for (RunningAppProcessInfo appProcess : appProcesses) {
				if (appProcess.processName.equals(packageName)
						&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					return true;
				}
			}
			return false;
		} 
	 private boolean isAppForeground = true;
	 
	 @Override
	protected void onStop() {
		 super.onStop();
		 AnySDKAnalytics.getInstance().stopSession();
		if(!isAppOnForeground()){
			isAppForeground = false;
		}
	}	
	 
	@Override
	protected void onResume() {
		super.onResume();
		PluginWrapper.onResume();
		AnySDKAnalytics.getInstance().startSession();
		/**
		 * 后台切换回来判断是否支持调用暂停界面接口
		 */
		if(!isAppForeground){
			AnySDKUser.getInstance().callFunction("pause");
			isAppForeground = true;			
		}
	}
	@Override
	public void onPause()
	{
		PluginWrapper.onPause();
		super.onPause();
	}
	
	
	
		
		

		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			/**
			 * 判断是否支持调用退出界面的接口
			 */
			if (AnySDKUser.getInstance().isFunctionSupported("exit")) {
				AnySDKUser.getInstance().callFunction("exit");
				
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private static int  getResourceId(String name, String type) {
		    return mAct.getResources().getIdentifier(name, type, mAct.getPackageName());
		}
		
	private static LinearLayout myLayout;
	/**
	 * 
	* @Title: ChoosePayMode 
	* @Description: 多支付调用方法
	* @param @param payMode     
	* @return void   
	* @throws
	 */
	public void  ChoosePayMode(ArrayList<String> payMode) {
		myLayout = new LinearLayout(mAct);
		OnClickListener onclick = new OnClickListener() { 

			@Override
			public void onClick(View v) {
				AnySDKIAP.getInstance().payForProduct((String) v.getTag(), mProductionInfo);
			}
	    };
		for (int i = 0; i < payMode.size(); i++) {
			Button button = new Button(mAct);
			String res = "Channel" + payMode.get(i);
			button.setText(getResourceId(res,"string"));
			button.setOnClickListener(onclick);
			button.setTag(payMode.get(i));
			myLayout.addView(button);
		}
			
		AlertDialog.Builder dialog02 = new AlertDialog.Builder(mAct);    
	   	dialog02.setView(myLayout); 
	   	dialog02.setTitle("UI PAY");
	   	
	    	
	   	dialog02.show();
	}
	 private void showUserAction() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        View view = View.inflate(this, R.layout.activity_user, null);
	        LinearLayout user = (LinearLayout) view.findViewById(R.id.user);
	        if(AnySDKUser.getInstance().isFunctionSupported("logout"))
	        {
	        	Button logoutButton = new Button(this);
	        	logoutButton.setOnClickListener(this);
	        	logoutButton.setTag("logout");
	        	logoutButton.setText("logout");
	        	user.addView(logoutButton);
	        	//this.addContentView(logoutButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        if(AnySDKUser.getInstance().isFunctionSupported("enterPlatform"))
	        {
	        	Button enterPlatformButton = new Button(this);
	        	enterPlatformButton.setOnClickListener(this);
	        	enterPlatformButton.setTag("enterPlatform");
	        	enterPlatformButton.setText("enterPlatform");
	        	user.addView(enterPlatformButton);
	        	//this.addContentView(enterPlatformButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        if(AnySDKUser.getInstance().isFunctionSupported("showToolBar"))
	        {
	        	Button showToolBarButton = new Button(this);
	        	showToolBarButton.setOnClickListener(this);
	        	showToolBarButton.setTag("showToolBar");
	        	showToolBarButton.setText("showToolBar");
	        	user.addView(showToolBarButton);
	        	//this.addContentView(showToolBarButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        if(AnySDKUser.getInstance().isFunctionSupported("hideToolBar"))
	        {
	        	Button hideToolBarButton = new Button(this);
	        	hideToolBarButton.setOnClickListener(this);
	        	hideToolBarButton.setTag("hideToolBar");
	        	hideToolBarButton.setText("hideToolBar");
	        	user.addView(hideToolBarButton);
	        	//this.addContentView(hideToolBarButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        if(AnySDKUser.getInstance().isFunctionSupported("accountSwitch"))
	        {
	        	Button accoutSwitchButton = new Button(this);
	        	accoutSwitchButton.setOnClickListener(this);
	        	accoutSwitchButton.setTag("accountSwitch");
	        	accoutSwitchButton.setText("accountSwitch");
	        	user.addView(accoutSwitchButton);
	        	//this.addContentView(accoutSwitchButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        if(AnySDKUser.getInstance().isFunctionSupported("realNameRegister"))
	        {
	        	Button realNameRegisterButton = new Button(this);
	        	realNameRegisterButton.setOnClickListener(this);
	        	realNameRegisterButton.setTag("realNameRegister");
	        	realNameRegisterButton.setText("realNameRegister");
	        	user.addView(realNameRegisterButton);
	        	//this.addContentView(realNameRegisterButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        if(AnySDKUser.getInstance().isFunctionSupported("antiAddictionQuery"))
	        {
	        	Button antiAddictionQueryButton = new Button(this);
	        	antiAddictionQueryButton.setOnClickListener(this);
	        	antiAddictionQueryButton.setTag("antiAddictionQuery");
	        	antiAddictionQueryButton.setText("antiAddictionQuery");
	        	user.addView(antiAddictionQueryButton);
	        	//this.addContentView(antiAddictionQueryButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        
	        if(AnySDKUser.getInstance().isFunctionSupported("submitLoginGameRole"))
	        {
	        	Button submitLoginGameRoleButton = new Button(this);
	        	submitLoginGameRoleButton.setOnClickListener(this);
	        	submitLoginGameRoleButton.setTag("submitLoginGameRole");
	        	submitLoginGameRoleButton.setText("submitLoginGameRole");
	        	user.addView(submitLoginGameRoleButton);
	        	//this.addContentView(antiAddictionQueryButton, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	        }
	        myDialog = builder.setView(view).create();

	        myDialog.show();
	        myDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


	        Button btnUser = (Button) view.findViewById(R.id.login);
	        btnUser.setOnClickListener(this);
	    }
	 
	 private void showPayAction() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        View view = View.inflate(this, R.layout.activity_iap, null);
	        myDialog = builder.setView(view).create();

	        myDialog.show();
	        myDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	        Button btnPay = (Button) myDialog.findViewById(R.id.pay);
	        btnPay.setOnClickListener(this);
	    }
	 
	 private void showShareAction() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        View view = View.inflate(this, R.layout.activity_share, null);
	        myDialog = builder.setView(view).create();

	        myDialog.show();
	        myDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	        Button btnShare = (Button) myDialog.findViewById(R.id.share);
	        btnShare.setOnClickListener(this);
	    }
	 
	 private void showAdsAction() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        View view = View.inflate(this, R.layout.activity_ads, null);
	        myDialog = builder.setView(view).create();

	        myDialog.show();
	        myDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	        Button btnShow = (Button) myDialog.findViewById(R.id.show);
	        btnShow.setOnClickListener(this);
	        
	        Button btnhide= (Button) myDialog.findViewById(R.id.hide);
	        btnhide.setOnClickListener(this);
	    }
	 
	 private void showSocialAction() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        View view = View.inflate(this, R.layout.activity_social, null);
	        myDialog = builder.setView(view).create();

	        myDialog.show();
	        myDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	        Button btnSubmitScore = (Button) myDialog.findViewById(R.id.submitScore);
	        btnSubmitScore.setOnClickListener(this);
	        
	        Button btnPay = (Button) myDialog.findViewById(R.id.showLeaderboard);
	        btnPay.setOnClickListener(this);
	        
	        Button btnUnlockAchievement = (Button) myDialog.findViewById(R.id.unlockAchievement);
	        btnUnlockAchievement.setOnClickListener(this);
	        
	        Button btnShowAchievements = (Button) myDialog.findViewById(R.id.showAchievements);
	        btnShowAchievements.setOnClickListener(this);
	    }
	 
	 private void showPushAction() {
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        View view = View.inflate(this, R.layout.activity_push, null);
	        myDialog = builder.setView(view).create();

	        myDialog.show();
	        myDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	        Button btnStopPush = (Button) myDialog.findViewById(R.id.closePush);
	        btnStopPush.setOnClickListener(this);
	        
	        Button btnSetAlias = (Button) myDialog.findViewById(R.id.setAlias);
	        btnSetAlias.setOnClickListener(this);
	        
	        Button btnDelAlias = (Button) myDialog.findViewById(R.id.delAlias);
	        btnDelAlias.setOnClickListener(this);
	        
	        Button btnSetTags = (Button) myDialog.findViewById(R.id.setTags);
	        btnSetTags.setOnClickListener(this);
	        
	        Button btnDelTags = (Button) myDialog.findViewById(R.id.delTags);
	        btnDelTags.setOnClickListener(this);
	    }
	 
	 
	 
}
