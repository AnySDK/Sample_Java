package com.anysdk.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.text.format.Time;

import com.anysdk.framework.AnalyticsWrapper;
import com.anysdk.framework.java.AnySDK;
import com.anysdk.framework.java.AnySDKAnalytics;

public class DataManager {
	private Map<String, String> mRoleInfo = null;
	private Map<String, String> mProductionInfo = null;
	private Map<String, String> mShareInfo = null;
	private ArrayList<String> mTagInfo = null;
	private Map<String, String> mArchInfo = null;
	private Map<String, String> mMetaData = null;
	private Map<String, String> mVideoInfo = null;
	private Map<String, String> mAccountInfo = null;
	private Map<String, String> mChargeInfo = null;
	private Map<String, String> mChargeFailInfo = null;
	
	private Map<String, String> mPurchaseInfo = null;
	private Map<String, String> mItemInfo = null;
	private Map<String, String> mLevelStartInfo = null;
	private Map<String, String> mLevelFailInfo = null;
	private Map<String, String> mTaskStartInfo = null;
	private Map<String, String> mTaskFailInfo = null;
	
	private Map<String, String> mLoginInfo = null;

    private Map<String, String> mPayInfo = null;

	
	
	private static DataManager instance;
	public static DataManager getInstance(){
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	public DataManager(){
		initData();
	}
	
	private void initData()
	{
		mRoleInfo = new HashMap<String, String>();
		mRoleInfo.put("dataType", "1");
		mRoleInfo.put("roleId", "123456");
		mRoleInfo.put("roleName", "test");
		mRoleInfo.put("roleLevel", "1");
		mRoleInfo.put("zoneId", "1");
		mRoleInfo.put("zoneName", "test");
		mRoleInfo.put("balance", "1");
		mRoleInfo.put("partyName", "test");
		mRoleInfo.put("vipLevel", "1");
		mRoleInfo.put("roleCTime", "1480318110");
		mRoleInfo.put("roleLevelMTime", "-1");
		
        mProductionInfo = new HashMap<String, String>();
        mProductionInfo.put("Product_Id", "1");
        mProductionInfo.put("Product_Name", "10元宝");
        mProductionInfo.put("Product_Price", "1");
        mProductionInfo.put("Product_Count", "1");
        mProductionInfo.put("Product_Desc", "gold");
        mProductionInfo.put("Coin_Name","元宝");
        mProductionInfo.put("Coin_Rate", "10");
        mProductionInfo.put("Role_Id","123456");
        mProductionInfo.put("Role_Name", "test");
        mProductionInfo.put("Role_Grade", "1");
        mProductionInfo.put("Role_Balance", "1");
        mProductionInfo.put("Vip_Level", "1");
        mProductionInfo.put("Party_Name", "test");
        mProductionInfo.put("Server_Id", "1");
        mProductionInfo.put("Server_Name", "test");
        mProductionInfo.put("EXT", "test");
        
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
        
        mMetaData = new HashMap<String, String>();
        mMetaData.put("video", "test");
        
        mVideoInfo = new HashMap<String, String>();
        mVideoInfo.put("Video_Title", "test");
        mVideoInfo.put("Video_Desc", "RECSDK是一个神奇的SDK");
        
        mAccountInfo = new HashMap<String, String>();
        mAccountInfo.put("Level_Id", "123456");
        mAccountInfo.put("Account_Id", "123456");
        mAccountInfo.put("Account_Name", "test");
        mAccountInfo.put("Account_Type", String.valueOf(AnalyticsWrapper.ACCOUNT_TYPE_ANONYMOUS));
        mAccountInfo.put("Account_Level", "1");
        mAccountInfo.put("Account_Age", "1");
        mAccountInfo.put("Account_Operate", String.valueOf(AnalyticsWrapper.ACCOUNT_OPERATE_LOGIN));
        mAccountInfo.put("Account_Gender", String.valueOf(AnalyticsWrapper.ACCOUNT_GENDER_MALE));
        mAccountInfo.put("Server_Id", "1");
        
        mChargeInfo = new HashMap<String, String>();
        mChargeInfo.put("Order_Id", "123456");
        mChargeInfo.put("Product_Name", "test");
        mChargeInfo.put("Currency_Amount", "2");
        mChargeInfo.put("Currency_Type", "1");
        mChargeInfo.put("Payment_Type", "渠道");
        mChargeInfo.put("Virtual_Currency_Amount", "100");
        
        mChargeFailInfo = new HashMap<String, String>();
        mChargeFailInfo.put("Order_Id", "123456");
        mChargeFailInfo.put("Fail_Reason", "test");
        
        mPurchaseInfo = new HashMap<String, String>();
        mPurchaseInfo.put("Item_Id", "123456");
        mPurchaseInfo.put("Item_Type", "test");
        mPurchaseInfo.put("Item_Count", "2");
        mPurchaseInfo.put("Virtual_Currency", "1");
        mPurchaseInfo.put("Currency_Type", AnySDK.getInstance().getChannelId());
        
        mItemInfo = new HashMap<String, String>();
        mItemInfo.put("Item_Id", "123456");
        mItemInfo.put("Item_Type", "test");
        mItemInfo.put("Item_Count", "2");
        mItemInfo.put("Use_Reason", "test");
        
        mLevelStartInfo = new HashMap<String, String>();
        mLevelStartInfo.put("Level_Id", "123456");
        mLevelStartInfo.put("Seq_Num",  String.valueOf(1));
        
        mLevelFailInfo= new HashMap<String, String>();
        mLevelFailInfo.put("Level_Id", "123456");
        mLevelFailInfo.put("Fail_Reason",  String.valueOf(1));
        
        mTaskStartInfo = new HashMap<String, String>();
        mTaskStartInfo.put("Task_Id", "123456");
        mTaskStartInfo.put("Task_Type",  String.valueOf(AnalyticsWrapper.TASK_TYPE_GUIDE_LINE));
        
        mTaskFailInfo = new HashMap<String, String>();
        mTaskFailInfo.put("Task_Id", "123456");
        mTaskFailInfo.put("Fail_Reason", "test");
        
        mLoginInfo = new HashMap<String, String>();
        mLoginInfo.put("User_Id", "123456");
        mLoginInfo.put("Role_Id", "test");
        mLoginInfo.put("Role_Name", "test");
        mLoginInfo.put("Level", "10");

        mPayInfo = new HashMap<String, String>();
        mPayInfo.put("User_Id", "123456");
        mPayInfo.put("Order_Id", Time.getCurrentTimezone());
        mPayInfo.put("Currency_Amount", "5");
        mPayInfo.put("Currency_Type", "CNY");
        mPayInfo.put("Payment_Type", "test");
        mPayInfo.put("Payment_Time", Time.getCurrentTimezone());
        mPayInfo.put("Item_Id", "1");
        mPayInfo.put("Item_Count", "1");


        
        
	}
	public Map<String, String> getRoleInfo() {
		return mRoleInfo;
	}
	
	public Map<String, String> getProductionInfo() {
		return mProductionInfo;
	}
	
	public Map<String, String> getShareInfo() {
		return mShareInfo;
	}
	
	public Map<String, String> getArchInfo() {
		return mArchInfo;
	}
	
	public Map<String, String> getMetaData() {
		return mMetaData;
	}
	
	public Map<String, String> getVideoInfo() {
		return mVideoInfo;
	}
	
	public Map<String, String> getAccountInfo() {
		return mAccountInfo;
	}
	
	public Map<String, String> getChargeInfo() {
		return mChargeInfo;
	}
	
	public Map<String, String> getChargeFailInfo() {
		return mChargeFailInfo;
	}
	
	public Map<String, String> getPurchaseInfo() {
		return mPurchaseInfo;
	}
	
	public Map<String, String> getItemInfo() {
		return mItemInfo;
	}
	
	public Map<String, String> getLevelStartInfo() {
		return mLevelStartInfo;
	}
	
	public Map<String, String> getLevelFailInfo() {
		return mLevelFailInfo;
	}
	
	public Map<String, String> getTaskStartInfo() {
		return mTaskStartInfo;
	}
	
	public Map<String, String> getTaskFailInfo() {
		return mTaskFailInfo;
	}
	
	public Map<String, String> getLoginInfo() {
        return mLoginInfo;
    }
	
	public Map<String, String> getPayInfo() {
        return mPayInfo;
    }
	
	public ArrayList<String> getTagInfo() {
		return mTagInfo;
	}
}
