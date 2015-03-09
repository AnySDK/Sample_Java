Sample_Java
===========
###Samplefor AnySDK_Framework_Java1.5  
v1.5 -- 2015.3.5  
库更新：  
1、新增接口获取框架版本号，开发者可通过AnySDK.getInstance().getFrameworkVersion()获得版本号  
###Sample for AnySDK_Framework_Java1.4  
v1.4  -- 2015.1.4  
库更新：      
1、新增AnySDK统计，该统计只是统计了接口调用的次数，以方便AnySDK产品的分析，默认是开启的，如果开发者介意改统计可以调用
AnySDK.getInstance().setIsAnaylticsEnabled(false)关闭统计接口    
2、新增   
isUserPluginExist  
isIAPPluginExist  
isAnalyticsPluginExist  
isSharePluginExist  
isSocialPluginExist  
isPushPluginExist   
判断系统插件是否存在接口   
###Sample for AnySDK_Framework_Java1.3.0.1 
库更新：  
1、更新框架1.3.0.1 

###Sample_v1.0.8 for AnySDK_Framework_Java1.2.3
v1.0.8  -- 2014.09.25  
库更新：  
1、更新框架1.2.3  
2、用户系统接口isSupportFunction改为isFunctionSupported  
3、广告系统接口作调整  
&emsp;a、接口支持多个广告位功能  
&emsp;b、新增预加载功能preloadAds  
4、统计系统接口作调整，支持游戏相关统计  
&emsp;a、游戏账号信息统计接口setAccount  
&emsp;b、支付相关接口:onChargeRequest、onChargeOnlySuccess、onChargeSuccess、onChargeFail  
&emsp;c、游戏支付相关接口:onPurchase、onUse、onReward  
&emsp;d、关卡相关接口:startLevel、finishLevel、failLevel  
&emsp;e、任务相关接口:startTask、finishTask、failTask  

###v1.0.7  -- 2014.08.26
库更新：
1、更新框架1.2.2
2、新增onPause onResume onNewIntent
