package com.anysdk.sample;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.anysdk.framework.AdsWrapper;
import com.anysdk.framework.java.AnySDKAdTracking;
import com.anysdk.framework.java.AnySDKAds;
import com.anysdk.framework.java.AnySDKAnalytics;
import com.anysdk.framework.java.AnySDKCrash;
import com.anysdk.framework.java.AnySDKIAP;
import com.anysdk.framework.java.AnySDKParam;
import com.anysdk.framework.java.AnySDKPush;
import com.anysdk.framework.java.AnySDKREC;
import com.anysdk.framework.java.AnySDKShare;
import com.anysdk.framework.java.AnySDKSocial;
import com.anysdk.framework.java.AnySDKUser;
import com.anysdk.framework.java.ToolBarPlaceEnum;

public class Controller {

	public static void onClick(String tag, String title) {
		if (tag.equals("user")) {
			AnySDKUser user = AnySDKUser.getInstance();
			if (title.equals("login")) {
				user.login(DataManager.getInstance().getAccountInfo());
			} else if (title.equals("isLogined")) {
				Log.d("isLogined", user.isLogined() + "");
			} else if (title.equals("getUserID")) {
				Log.d("getUserID", user.getUserID());
			} else if (title.equals("logout")) {
				// 调用登出
				user.callFunction("logout");
			} else if (title.equals("enterPlatform")) {
				// 调用显示平台中心
				user.callFunction("enterPlatform");
			} else if (title.equals("showToolBar")) {
				// 显示悬浮窗
				AnySDKParam param = new AnySDKParam(
						ToolBarPlaceEnum.kToolBarTopLeft.getPlace());
				user.callFunction("showToolBar", param);
			} else if (title.equals("hideToolBar")) {
				// 隐藏悬浮窗
				user.callFunction("hideToolBar");
			} else if (title.equals("accountSwitch")) {
				// 切换账号
				user.callFunction("accountSwitch");
			} else if (title.equals("realNameRegister")) {
				// 实名注册
				user.callFunction("realNameRegister");
			} else if (title.equals("antiAddictionQuery")) {
				// 防沉迷查询
				user.callFunction("antiAddictionQuery");
			} else if (title.equals("submitLoginGameRole")) {
				// 提交角色信息
				user.callFunction("submitLoginGameRole",  new AnySDKParam(DataManager.getInstance().getRoleInfo()));
			}
		} else if (tag.equals("iap")) {
			AnySDKIAP iap = AnySDKIAP.getInstance();
			ArrayList<String> idArrayList = iap.getPluginId();
			if (title.equals("payForProduct")) {
				if (idArrayList.size() == 1) {
					iap.payForProduct(idArrayList.get(0), DataManager
							.getInstance().getProductionInfo());
				} else {
					/**
					 * 多支付
					 */
					MainActivity.ChoosePayMode(idArrayList);
				}
			} else if (title.equals("getOrderId")) {
				// 调用登出
				Log.d("getOrderId", iap.getOrderId(idArrayList.get(0)));

			}
		} else if (tag.equals("ads")) {
			AnySDKAds ads = AnySDKAds.getInstance();
			if (title.equals("queryPoints")) {
				ads.queryPoints();
			} else if (title.equals("spendPoints")) {
				ads.spendPoints(1);
			}
		} else if (tag.equals("share")) {
			if (title.equals("share")) {
				AnySDKShare.getInstance().share(
						DataManager.getInstance().getShareInfo());
			}
		} else if (tag.equals("social")) {
			AnySDKSocial social = AnySDKSocial.getInstance();
			if (title.equals("signIn")) {
				social.signIn();
			} else if (title.equals("signOut")) {
				social.signOut();
			} else if (title.equals("submitScore")) {
				social.submitScore("1", 100);
			} else if (title.equals("showLeaderboard")) {
				social.showLeaderboard("1");
			} else if (title.equals("unlockAchievement")) {
				social.unlockAchievement(DataManager.getInstance()
						.getArchInfo());
			} else if (title.equals("showAchievements")) {
				social.showAchievements();
			}
		} else if (tag.equals("push")) {
			AnySDKPush push = AnySDKPush.getInstance();
			if (title.equals("startPush")) {
				push.startPush();
			} else if (title.equals("closePush")) {
				push.closePush();
			} else if (title.equals("setAlias")) {
				push.setAlias("alias");
			} else if (title.equals("delAlias")) {
				push.delAlias("alias");
			} else if (title.equals("setTags")) {
				push.setTags(DataManager.getInstance().getTagInfo());
			} else if (title.equals("delTags")) {
				push.delTags(DataManager.getInstance().getTagInfo());
			}
		} else if (tag.equals("analytics")) {
			AnySDKAnalytics analytics = AnySDKAnalytics.getInstance();
			if (title.equals("startSession")) {
				analytics.startSession();
			} else if (title.equals("stopSession")) {
				analytics.stopSession();
			} else if (title.equals("setSessionContinueMillis")) {
				analytics.setSessionContinueMillis(15000);
			} else if (title.equals("logError")) {
				analytics.logError("error", "test");
			} else if (title.equals("logEvent")) {
				analytics.logEvent("event");
			} else if (title.equals("logTimedEventBegin")) {
				analytics.logTimedEventBegin("event");
			} else if (title.equals("logTimedEventEnd")) {
				analytics.logTimedEventEnd("event");
			} else if (title.equals("setCaptureUncaughtException")) {
				analytics.setCaptureUncaughtException(true);
			} else if (title.equals("setAccount")) {
				analytics.callFunction("setAccount", new AnySDKParam(DataManager.getInstance().getAccountInfo()));
			} else if (title.equals("onChargeRequest")) {
				analytics.callFunction("onChargeRequest",new AnySDKParam(DataManager.getInstance().getChargeInfo()));
			} else if (title.equals("onChargeSuccess")) {
				analytics.callFunction("onChargeSuccess",new AnySDKParam("123"));
			} else if (title.equals("onChargeFail")) {
				analytics.callFunction("onChargeFail",new AnySDKParam(DataManager.getInstance().getChargeFailInfo()));
			} else if (title.equals("onChargeOnlySuccess")) {
				analytics.callFunction("onChargeOnlySuccess",new AnySDKParam(DataManager.getInstance().getChargeInfo()));
			} else if (title.equals("onPurchase")) {
				analytics.callFunction("onPurchase",new AnySDKParam(DataManager.getInstance().getChargeInfo()));
			} else if (title.equals("onUse")) {
				analytics.callFunction("onUse",new AnySDKParam(DataManager.getInstance().getItemInfo()));
			} else if (title.equals("onReward")) {
				analytics.callFunction("onReward",new AnySDKParam(DataManager.getInstance().getItemInfo()));
			} else if (title.equals("startLevel")) {
				analytics.callFunction("startLevel",new AnySDKParam(DataManager.getInstance().getLevelStartInfo()));
			} else if (title.equals("finishLevel")) {
				analytics.callFunction("finishLevel", new AnySDKParam("123"));
			} else if (title.equals("failLevel")) {
				analytics.callFunction("failLevel",new AnySDKParam(DataManager.getInstance().getLevelFailInfo()));
			} else if (title.equals("startTask")) {
				analytics.callFunction("startTask",new AnySDKParam(DataManager.getInstance().getTaskStartInfo()));
			} else if (title.equals("finishTask")) {
				analytics.callFunction("finishTask", new AnySDKParam("123"));
			} else if (title.equals("failTask")) {
				analytics.callFunction("failTask",new AnySDKParam(DataManager.getInstance().getTaskFailInfo()));
			}
		} else if (tag.equals("rec")) {
			AnySDKREC rec = AnySDKREC.getInstance();
			if (title.equals("startRecording")) {
				rec.startRecording();
			} else if (title.equals("stopRecording")) {
				rec.stopRecording();
			} else if (title.equals("share")) {
				rec.share(DataManager.getInstance().getVideoInfo());
			} else if (title.equals("pauseRecording")) {
				rec.callFunction("pauseRecording");
			} else if (title.equals("resumeRecording")) {
				rec.callFunction("resumeRecording");
			} else if (title.equals("isAvailable")) {
				Log.d("isAvailable", rec.callBoolFunction("isAvailable") + "");
			} else if (title.equals("showToolBar")) {
				rec.callFunction("showToolBar");
			} else if (title.equals("hideToolBar")) {
				rec.callFunction("hideToolBar");
			} else if (title.equals("isRecording")) {
				Log.d("isRecording", rec.callBoolFunction("isRecording") + "");
			} else if (title.equals("showVideoCenter")) {
				rec.callFunction("showVideoCenter");
			} else if (title.equals("enterPlatform")) {
				rec.callFunction("enterPlatform");
			} else if (title.equals("setMetaData")) {
				rec.callFunction("setMetaData", new AnySDKParam(DataManager
						.getInstance().getMetaData()));
			}
		} else if (tag.equals("crash")) {
			AnySDKCrash crash = AnySDKCrash.getInstance();
			if (title.equals("setUserIdentifier")) {
				crash.setUserIdentifier("anysdk");
				Log.d("setUserIdentifier", "setUserIdentifier");
			} else if (title.equals("reportException")) {
				crash.reportException("error", "test");
				String testString = null;
				testString.substring(1);
			} else if (title.equals("leaveBreadcrumb")) {
				crash.leaveBreadcrumb("leaveBreadcrumb");
			}
		} else if (tag.equals("adtracking")) {
            AnySDKAdTracking adTracking = AnySDKAdTracking.getInstance();
            if (title.equals("onRegister")) {
                adTracking.onRegister("userid");
            } else if (title.equals("onLogin")) {
                adTracking.onLogin(DataManager.getInstance().getLoginInfo());
            }else if (title.equals("onPay")) {
                adTracking.onPay(DataManager.getInstance().getPayInfo());
            } else if (title.equals("trackEvent")) {
                adTracking.trackEvent("event_1");
                adTracking.trackEvent("event_2");
                adTracking.trackEvent("onCustEvent1");
                adTracking.trackEvent("onCustEvent2");
            }else if (title.equals("onCreateRole")) {
                adTracking.trackEvent("onCreateRole", DataManager.getInstance().getLoginInfo());
                adTracking.callFunction("onCreateRole", new  AnySDKParam(DataManager.getInstance().getLoginInfo()));
            }else if (title.equals("onLevelUp")) {
                adTracking.trackEvent("onLevelUp", DataManager.getInstance().getLoginInfo());
                adTracking.callFunction("onLevelUp", new  AnySDKParam(DataManager.getInstance().getLoginInfo()));

            }else if (title.equals("onStartToPay")) {
                adTracking.trackEvent("onStartToPay", DataManager.getInstance().getPayInfo());
                adTracking.callFunction("onStartToPay", new  AnySDKParam(DataManager.getInstance().getPayInfo()));

            }
        }else if (tag.equals("Banner")) {
			aboutAds(AdsWrapper.AD_TYPE_BANNER,title);
		}else if (tag.equals("FullScreen")) {
			aboutAds(AdsWrapper.AD_TYPE_FULLSCREEN,title);
		}else if (tag.equals("MoreAPP")) {
			aboutAds(AdsWrapper.AD_TYPE_MOREAPP,title);
		}
		else if (tag.equals("OfferWall")) {
			aboutAds(AdsWrapper.AD_TYPE_OFFERWALL,title);
		}
		
	}

	public static List<String> extendUserFunction(List<String> source) {
		AnySDKUser instance = AnySDKUser.getInstance();
		if (instance.isFunctionSupported("logout")) {
			source.add("logout");
		}
		if (instance.isFunctionSupported("enterPlatform")) {
			source.add("enterPlatform");
		}
		if (instance.isFunctionSupported("showToolBar")) {
			source.add("showToolBar");
		}
		if (instance.isFunctionSupported("hideToolBar")) {
			source.add("hideToolBar");
		}
		if (instance.isFunctionSupported("accountSwitch")) {
			source.add("accountSwitch");
		}
		if (instance.isFunctionSupported("realNameRegister")) {
			source.add("realNameRegister");
		}
		if (instance.isFunctionSupported("antiAddictionQuery")) {
			source.add("antiAddictionQuery");
		}
		if (instance.isFunctionSupported("submitLoginGameRole")) {
			source.add("submitLoginGameRole");
		}
		return source;
	}

	public static List<String> extendAnalyticsFunction(List<String> source) {
		AnySDKAnalytics instance = AnySDKAnalytics.getInstance();
		if (instance.isFunctionSupported("setAccount")) {
			source.add("setAccount");
		}
		if (instance.isFunctionSupported("onChargeRequest")) {
			source.add("onChargeRequest");
		}
		if (instance.isFunctionSupported("onChargeSuccess")) {
			source.add("onChargeSuccess");
		}
		if (instance.isFunctionSupported("onChargeFail")) {
			source.add("onChargeFail");
		}
		if (instance.isFunctionSupported("onChargeOnlySuccess")) {
			source.add("onChargeOnlySuccess");
		}
		if (instance.isFunctionSupported("onPurchase")) {
			source.add("onPurchase");
		}
		if (instance.isFunctionSupported("onUse")) {
			source.add("onUse");
		}
		if (instance.isFunctionSupported("onReward")) {
			source.add("onReward");
		}
		if (instance.isFunctionSupported("startLevel")) {
			source.add("startLevel");
		}
		if (instance.isFunctionSupported("finishLevel")) {
			source.add("finishLevel");
		}
		if (instance.isFunctionSupported("failLevel")) {
			source.add("failLevel");
		}
		if (instance.isFunctionSupported("startTask")) {
			source.add("startTask");
		}
		if (instance.isFunctionSupported("finishTask")) {
			source.add("finishTask");
		}
		if (instance.isFunctionSupported("failTask")) {
			source.add("failTask");
		}
		return source;
	}

	public static List<String> extendRECFunction(List<String> source) {
		AnySDKREC instance = AnySDKREC.getInstance();
		if (instance.isFunctionSupported("pauseRecording")) {
			source.add("pauseRecording");
		}
		if (instance.isFunctionSupported("resumeRecording")) {
			source.add("resumeRecording");
		}
		if (instance.isFunctionSupported("isAvailable")) {
			source.add("isAvailable");
		}
		if (instance.isFunctionSupported("showToolBar")) {
			source.add("showToolBar");
		}
		if (instance.isFunctionSupported("hideToolBar")) {
			source.add("hideToolBar");
		}
		if (instance.isFunctionSupported("isRecording")) {
			source.add("isRecording");
		}
		if (instance.isFunctionSupported("showVideoCenter")) {
			source.add("showVideoCenter");
		}
		if (instance.isFunctionSupported("enterPlatform")) {
			source.add("enterPlatform");
		}
		if (instance.isFunctionSupported("setMetaData")) {
			source.add("setMetaData");
		}
		return source;
	}
	
	public static List<String> extendAdTrackingFunction(List<String> source) {
        AnySDKAdTracking instance = AnySDKAdTracking.getInstance();
        if (instance.isFunctionSupported("onCreateRole")) {
            source.add("onCreateRole");
        }
        if (instance.isFunctionSupported("onLevelUp")) {
            source.add("onLevelUp");
        }
        if (instance.isFunctionSupported("onStartToPay")) {
            source.add("onStartToPay");
        }
        return source;
    }
	
	public static void aboutAds(int type, String title){
		AnySDKAds ads = AnySDKAds.getInstance();
		if (title.equals("preloadAds1")) {
			ads.preloadAds(type, 1);
		} else if (title.equals("preloadAds2")) {
			ads.preloadAds(type, 2);
		} else if (title.equals("showAds1")) {
			ads.showAds(type, 1);
		} else if (title.equals("showAds2")) {
			ads.showAds(type, 2);
		} else if (title.equals("hideAds1")) {
			ads.hideAds(type, 1);
		} else if (title.equals("hideAds2")) {
			ads.hideAds(type, 2);
		}
	}

}
