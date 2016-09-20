package com.anysdk.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anysdk.framework.AdsWrapper;
import com.anysdk.framework.IAPWrapper;
import com.anysdk.framework.PluginWrapper;
import com.anysdk.framework.PushWrapper;
import com.anysdk.framework.RECWrapper;
import com.anysdk.framework.ShareWrapper;
import com.anysdk.framework.SocialWrapper;
import com.anysdk.framework.UserWrapper;
import com.anysdk.framework.java.AnySDK;
import com.anysdk.framework.java.AnySDKAds;
import com.anysdk.framework.java.AnySDKAnalytics;
import com.anysdk.framework.java.AnySDKIAP;
import com.anysdk.framework.java.AnySDKListener;
import com.anysdk.framework.java.AnySDKPush;
import com.anysdk.framework.java.AnySDKREC;
import com.anysdk.framework.java.AnySDKShare;
import com.anysdk.framework.java.AnySDKSocial;
import com.anysdk.framework.java.AnySDKUser;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {
	private static final String TAG_STRING = "ANYSDK";
	private static Activity mAct;
	private static Handler mUIHandler;
	private ListView mainListView;
	private SimpleAdapter adapter;
	private List<Map<String, String>> sysList = new ArrayList<Map<String, String>>();

	private String[] mainStrings = new String[] { "User System", "IAP System",
			"Share System", "Social System", "Ads System", "Analytics System",
			"Push System", "Crash System", "REC System", "AdTracking System"};

	private String[] userStrings = new String[] { "return", "login",
			"isLogined", "getUserID" };

	private String[] iapStrings = new String[] { "return", "payForProduct",
			"getOrderId" };

	private String[] shareStrings = new String[] { "return", "share" };

	private String[] socialStrings = new String[] { "return", "signIn",
			"signOut", "submitScore", "showLeaderboard", "unlockAchievement",
			"showAchievements" };

	private String[] adsStrings = new String[] { "return", "Banner",
			"FullScreen", "MoreAPP", "OfferWall", "queryPoints", "spendPoints" };
	
	private String[] adsFuntionStrings = new String[] {"return", "preloadAds1","preloadAds2",
			"showAds1", "showAds2","hideAds1","hideAds2"};

	private String[] analyticsStrings = new String[] { "return",
			"startSession", "stopSession", "setSessionContinueMillis",
			"logError", "logEvent", "logTimedEventBegin", "logTimedEventEnd" };

	private String[] pushStrings = new String[] { "return", "startPush",
			"closePush", "setAlias", "delAlias", "setTags", "delTags" };

	private String[] crashStrings = new String[] { "return",
			"setUserIdentifier", "reportException", "leaveBreadcrumb" };

	private String[] recStrings = new String[] { "return", "startRecording",
			"stopRecording", "share" };
	
	private String[] adTrackingStrings = new String[] { "return", "onRegister",
            "onLogin", "onPay", "trackEvent"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		

		mAct = this;
		mUIHandler = new Handler();
		initAnySDK();

		updataData("main","");
		mainListView = new ListView(this);
		adapter = new SimpleAdapter(this, sysList, R.layout.activity_first,
				new String[] { "title" }, new int[] { R.id.title });
		mainListView.setAdapter(adapter);
		mainListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				HashMap<String, String> map = (HashMap<String, String>) mainListView
						.getItemAtPosition(arg2);
				String tagString = map.get("tag");
				String titleString = map.get("title");
				String lastString = map.get("last");
				if (titleString.equals("return")) {
					updataData(lastString, "main");
					adapter.notifyDataSetChanged();
					mainListView.setAdapter(adapter);
				} else if (tagString.equals("main")) {
					if (titleString.equals("User System")) {
						updataData("user",tagString);
					} else if (titleString.equals("IAP System")) {
						updataData("iap",tagString);
					} else if (titleString.equals("Ads System")) {
						updataData("ads",tagString);
					} else if (titleString.equals("Social System")) {
						updataData("social",tagString);
					} else if (titleString.equals("Share System")) {
						updataData("share",tagString);
					} else if (titleString.equals("Analytics System")) {
						updataData("analytics",tagString);
					} else if (titleString.equals("Push System")) {
						updataData("push",tagString);
					} else if (titleString.equals("Crash System")) {
						updataData("crash",tagString);
					} else if (titleString.equals("REC System")) {
						updataData("rec",tagString);
					}else if (titleString.equals("AdTracking System")) {
                        updataData("adtracking",tagString);
                    }
					else if (titleString.equals("AdTracking System")) {
                        updataData("adtracking",tagString);
                    }
					adapter.notifyDataSetChanged();
					mainListView.setAdapter(adapter);
				} else if(tagString.equals("ads")){
					if (titleString.equals("Banner")) {
						updataData("Banner",tagString);
					} else if (titleString.equals("FullScreen")) {
						updataData("FullScreen",tagString);
					} else if (titleString.equals("MoreAPP")) {
						updataData("MoreAPP",tagString);
					} else if (titleString.equals("OfferWall")) {
						updataData("OfferWall",tagString);
					} else {
						Controller.onClick(tagString, titleString);
					}
					adapter.notifyDataSetChanged();
					mainListView.setAdapter(adapter);
				}
				else {
					Controller.onClick(tagString, titleString);
				}

			}
		});

		setContentView(mainListView);
	}

	private void updataData(String tag,String last) {
		sysList.clear();
		List<String> list = new ArrayList<String>();
		if (tag.equals("main")) {
			list.addAll(Arrays.asList(mainStrings));
		} else if (tag.equals("user")) {
			list.addAll(Arrays.asList(userStrings));
			list = Controller.extendUserFunction(list);
		} else if (tag.equals("iap")) {
			list.addAll(Arrays.asList(iapStrings));
		} else if (tag.equals("share")) {
			list.addAll(Arrays.asList(shareStrings));
		} else if (tag.equals("ads")) {
			list.addAll(Arrays.asList(adsStrings));
		} else if (tag.equals("analytics")) {
			list.addAll(Arrays.asList(analyticsStrings));
			list = Controller.extendAnalyticsFunction(list);
		} else if (tag.equals("social")) {
			list.addAll(Arrays.asList(socialStrings));
		} else if (tag.equals("push")) {
			list.addAll(Arrays.asList(pushStrings));
		} else if (tag.equals("crash")) {
			list.addAll(Arrays.asList(crashStrings));
		} else if (tag.equals("rec")) {
			list.addAll(Arrays.asList(recStrings));
			list = Controller.extendRECFunction(list);
		} else if (tag.equals("adtracking")) {
            list.addAll(Arrays.asList(adTrackingStrings));
            list = Controller.extendAdTrackingFunction(list);
        } else if (tag.equals("Banner")) {
			list.addAll(Arrays.asList(adsFuntionStrings));
		} else if (tag.equals("FullScreen")) {
			list.addAll(Arrays.asList(adsFuntionStrings));
		} else if (tag.equals("MoreAPP")) {
			list.addAll(Arrays.asList(adsFuntionStrings));
		} else if (tag.equals("OfferWall")) {
			list.addAll(Arrays.asList(adsFuntionStrings));
		}
		

		for (String item : list) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", item);
			map.put("tag", tag);
			map.put("last", last);
			sysList.add(map);
		}
	}

	void initAnySDK() {
		/**
		 * appKey、appSecret、privateKey不能使用Sample中的值，需要从打包工具中游戏管理界面获取，替换
		 * oauthLoginServer参数是游戏服务提供的用来做登陆验证转发的接口地址。
		 */
		String appKey = "D22AB625-CD4C-2167-D35C-C5A03E5896F5";
		String appSecret = "8959c650440b6b051d6af588d7f965f3";
		String privateKey = "BA26F2670407E0B8664DDA544026FA54";
		String oauthLoginServer = "http://oauth.anysdk.com/api/OauthLoginDemo/Login.php";
		AnySDK.getInstance().init(this, appKey, appSecret, privateKey,
				oauthLoginServer);

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
				switch (arg0) {
				case UserWrapper.ACTION_RET_INIT_SUCCESS:// 初始化SDK成功回调
					break;
				case UserWrapper.ACTION_RET_INIT_FAIL:// 初始化SDK失败回调
					Exit();
					break;
				case UserWrapper.ACTION_RET_LOGIN_SUCCESS:// 登陆成功回调
					showDialog(arg1, "User is  online");
					Log.d(TAG_STRING, String.valueOf(AnySDKUser.getInstance()
							.isLogined()));
					break;
				case UserWrapper.ACTION_RET_LOGIN_NO_NEED:// 登陆失败回调
				case UserWrapper.ACTION_RET_LOGIN_TIMEOUT:// 登陆失败回调
				case UserWrapper.ACTION_RET_LOGIN_CANCEL:// 登陆取消回调
				case UserWrapper.ACTION_RET_LOGIN_FAIL:// 登陆失败回调
					showDialog(arg1, "fail");
					AnySDKAnalytics.getInstance().logError("login", "fail");
					break;
				case UserWrapper.ACTION_RET_LOGOUT_SUCCESS:// 登出成功回调
					break;
				case UserWrapper.ACTION_RET_LOGOUT_FAIL:// 登出失败回调
					showDialog(arg1, "登出失败");
					break;
				case UserWrapper.ACTION_RET_PLATFORM_ENTER:// 平台中心进入回调
					break;
				case UserWrapper.ACTION_RET_PLATFORM_BACK:// 平台中心退出回调
					break;
				case UserWrapper.ACTION_RET_PAUSE_PAGE:// 暂停界面回调
					break;
				case UserWrapper.ACTION_RET_EXIT_PAGE:// 退出游戏回调
					Exit();
					break;
				case UserWrapper.ACTION_RET_ANTIADDICTIONQUERY:// 防沉迷查询回调
					showDialog(arg1, "防沉迷查询回调");
					break;
				case UserWrapper.ACTION_RET_REALNAMEREGISTER:// 实名注册回调
					showDialog(arg1, "实名注册回调");
					break;
				case UserWrapper.ACTION_RET_ACCOUNTSWITCH_SUCCESS:// 切换账号成功回调
					break;
				case UserWrapper.ACTION_RET_ACCOUNTSWITCH_FAIL:// 切换账号失败回调
					break;
				case UserWrapper.ACTION_RET_OPENSHOP:// 打开游戏商店回调
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
				switch (arg0) {
				case IAPWrapper.PAYRESULT_INIT_FAIL:// 支付初始化失败回调
					break;
				case IAPWrapper.PAYRESULT_INIT_SUCCESS:// 支付初始化成功回调
					break;
				case IAPWrapper.PAYRESULT_SUCCESS:// 支付成功回调
					temp = "Success";
					showDialog(temp, temp);
					break;
				case IAPWrapper.PAYRESULT_FAIL:// 支付失败回调
					showDialog(temp, temp);
					break;
				case IAPWrapper.PAYRESULT_CANCEL:// 支付取消回调
					showDialog(temp, "Cancel");
					break;
				case IAPWrapper.PAYRESULT_NETWORK_ERROR:// 支付超时回调
					showDialog(temp, "NetworkError");
					break;
				case IAPWrapper.PAYRESULT_PRODUCTIONINFOR_INCOMPLETE:// 支付超时回调
					showDialog(temp, "ProductionInforIncomplete");
					break;
				/**
				 * 新增加:正在进行中回调 支付过程中若SDK没有回调结果，就认为支付正在进行中
				 * 游戏开发商可让玩家去判断是否需要等待，若不等待则进行下一次的支付
				 */
				case IAPWrapper.PAYRESULT_NOW_PAYING:
					showTipDialog();
					break;
				case IAPWrapper.PAYRESULT_RECHARGE_SUCCESS:// 充值成功回调
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
				case AdsWrapper.RESULT_CODE_AdsDismissed:// 广告消失回调
					break;
				case AdsWrapper.RESULT_CODE_AdsReceived:// 接受到网络回调
					break;
				case AdsWrapper.RESULT_CODE_AdsShown:// 显示网络回调
					break;
				case AdsWrapper.RESULT_CODE_PointsSpendFailed:// 积分墙消费失败
					break;
				case AdsWrapper.RESULT_CODE_PointsSpendSucceed:// 积分墙消费成功
					break;
				case AdsWrapper.RESULT_CODE_OfferWallOnPointsChanged:// 积分墙积分改变
					break;
				case AdsWrapper.RESULT_CODE_NetworkError:// 网络出错
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
				case ShareWrapper.SHARERESULT_CANCEL:// 取消分享
					break;
				case ShareWrapper.SHARERESULT_FAIL:// 分享失败
					break;
				case ShareWrapper.SHARERESULT_NETWORK_ERROR:// 分享网络出错
					break;
				case ShareWrapper.SHARERESULT_SUCCESS:// 分享结果成功
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
				case SocialWrapper.SOCIAL_SIGNIN_FAIL:// 社交登陆失败
					break;
				case SocialWrapper.SOCIAL_SIGNIN_SUCCEED:// 社交登陆成功
					break;
				case SocialWrapper.SOCIAL_SIGNOUT_FAIL:// 社交登出失败
					break;
				case SocialWrapper.SOCIAL_SIGNOUT_SUCCEED:// 社交登出成功
					break;
				case SocialWrapper.SOCIAL_SUBMITSCORE_FAIL:// 提交分数失败
					break;
				case SocialWrapper.SOCIAL_SUBMITSCORE_SUCCEED:// 提交分数成功
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
				case PushWrapper.ACTION_RET_RECEIVEMESSAGE:// 接受到推送消息

					break;

				default:
					break;
				}
			}
		});
		
		//为录屏分享系统设置监听
		AnySDKREC.getInstance().setListener(new AnySDKListener() {
			
			@Override
			public void onCallBack(int arg0, String arg1) {
				Log.d(String.valueOf(arg0), arg1);
			    switch(arg0)
			    {
			        case RECWrapper.RECRESULT_RECINITSUCCESSS://初始化成功
			        	Log.d(TAG_STRING,"RECRESULT_RECINITSUCCESSS\n");
			            break;
			        case RECWrapper.RECRESULT_RECINITFAIL://初始化失败
			        	Log.d(TAG_STRING,"RECRESULT_RECINITFAIL\n");
			            break;
			        case RECWrapper.RECRESULT_RECSTARTRECORDING://开始录制
			        	Log.d(TAG_STRING,"RECRESULT_RECSTARTRECORDING \n");
			            break;
			        case RECWrapper.RECRESULT_RECSTOPRECORDING://结束录制
			        	Log.d(TAG_STRING,"RECRESULT_RECSTOPRECORDING \n");
			            break;
			        case RECWrapper.RECRESULT_RECPAUSEECORDING://暂停录制
			        	Log.d(TAG_STRING,"RECRESULT_RECPAUSEECORDING \n");
			            break;
			        case RECWrapper.RECRESULT_RECRESUMERECORDING://恢复录制
			        	Log.d(TAG_STRING,"RECRESULT_RECRESUMERECORDING \n");
			            break;
			        case RECWrapper.RECRESULT_RECENTERSDKPAGE://进入SDK页面
			        	Log.d(TAG_STRING,"RECRESULT_RECENTERSDKPAGE \n");
			            break;
			        case RECWrapper.RECRESULT_RECOUTSDKPAGE://退出SDK页面
			        	Log.d(TAG_STRING,"RECRESULT_RECOUTSDKPAGE \n");
			            break;  
			        case RECWrapper.RECRESULT_RECSHARESUCCESS://视频分享成功
			        	Log.d(TAG_STRING,"RECRESULT_RECSHARESUCCESS \n");
			            break;
			        case RECWrapper.RECRESULT_RECSHAREFAIL://视频分享失败
			        	Log.d(TAG_STRING,"RECRESULT_RECSHAREFAIL \n");
			            break;
			        default:
			            break;
			    }
				
			}
		});
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
									public void onClick(DialogInterface dialog,
											int which) {

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
									public void onClick(DialogInterface dialog,
											int which) {
										/**
										 * 重置支付状态
										 */
										AnySDKIAP.getInstance().resetPayState();
									}
								})
						.setNegativeButton("YES",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

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
		PluginWrapper.onDestroy();
		super.onDestroy();
		AnySDK.getInstance().release();
	};

	@Override
	protected void onStop() {
		PluginWrapper.onStop();
		super.onStop();
		AnySDKAnalytics.getInstance().stopSession();
		if (!isAppOnForeground()) {
			isAppForeground = false;
		}
	}

	@Override
	protected void onResume() {
		PluginWrapper.onResume();
		super.onResume();
		AnySDKAnalytics.getInstance().startSession();
		/**
		 * 后台切换回来判断是否支持调用暂停界面接口
		 */
		if (!isAppForeground) {
			// 也没SDK有这个接口了 
			AnySDKUser.getInstance().callFunction("pause");
			isAppForeground = true;
		}
	}

	@Override
	protected void onPause() {
		PluginWrapper.onPause();
		super.onPause();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		PluginWrapper.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		PluginWrapper.onNewIntent(intent);
		super.onNewIntent(intent);
	}

	@Override
	protected void onRestart() {
		PluginWrapper.onRestart();
		super.onRestart();
	}
	
	@Override
	protected void onStart() {
		PluginWrapper.onStart();
		super.onStart();
	}
	
	@Override
	public void onBackPressed() {
		PluginWrapper.onBackPressed();
        super.onBackPressed();
    }
	
    @Override
	public void onConfigurationChanged(Configuration newConfig) {
    	PluginWrapper.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	PluginWrapper.onRestoreInstanceState(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	PluginWrapper.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
 	
	public boolean isAppOnForeground() {
		ActivityManager activityManager = (ActivityManager) getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
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
	public static void  ChoosePayMode(ArrayList<String> payMode) {
		myLayout = new LinearLayout(mAct);
		OnClickListener onclick = new OnClickListener() { 

			@Override
			public void onClick(View v) {
				AnySDKIAP.getInstance().payForProduct((String) v.getTag(), DataManager.getInstance().getProductionInfo());
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
	 
}
