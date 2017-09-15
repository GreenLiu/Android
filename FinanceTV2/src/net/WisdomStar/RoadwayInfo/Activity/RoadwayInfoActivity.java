package net.WisdomStar.RoadwayInfo.Activity;

import java.util.ArrayList;
import java.util.Stack;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import net.WisdomStar.AppActivity.AppFragActivity;
import net.WisdomStar.AppActivity.AppMainActivity;
import net.WisdomStar.AppFragment.AppPromoteFragment;
import net.WisdomStar.AppUtils.AppCache;
import net.WisdomStar.AppYouTube.AppFragment.AppYouTubeChannelListFragment;
import net.WisdomStar.AppYouTube.AppFragment.AppYouTubePlayListFragment;
import net.WisdomStar.AppYouTube.AppFragment.AppYouTubePlayVideoListFragment;
import net.WisdomStar.RoadwayInfo.Data.CCTV;
import net.WisdomStar.RoadwayInfo.Data.CCTVData;
import net.WisdomStar.RoadwayInfo.Data.RoadwayData;
import net.WisdomStar.RoadwayInfo.Fragment.CCTVFragment;
import net.WisdomStar.RoadwayInfo.Fragment.RoadInfoFragment;
import net.WisdomStar.RoadwayInfo.Fragment.RoadwayFragment;
import net.WisdomStar.RoadwayInfo.Fragment.SpeedFragment;
import net.WisdomStar.RoadwayInfo.R;

public class RoadwayInfoActivity extends AppFragActivity
{
	private AppYouTubeChannelListFragment mAppYouTubeChannelListFragment;
	private AppYouTubePlayVideoListFragment mAppYouTubePlayVideoListFragment;
	
    private RoadwayFragment mRoadwayFragment;
    private CCTVFragment mCCTVFragment;
    private SpeedFragment mSpeedFragment;
    private AppPromoteFragment mAppPromoteFragment;

    private Fragment mLeaveFragment;
    private SharedPreferences mSharedPrefs;
    private Stack<Fragment> mStackFragment;
    
    private boolean mIsShowAdMobInterstitial;
    
    public RoadwayInfoActivity() 
    {
    	mAppYouTubeChannelListFragment = null;
    	mAppYouTubePlayVideoListFragment = null;
    	
        mRoadwayFragment = null;
        mSpeedFragment = null;
        mCCTVFragment = null;
        mAppPromoteFragment = null;
        
        mStackFragment = new Stack<Fragment>();
        mIsShowAdMobInterstitial = false;
    }

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        RoadInfoFragment.mRoadwayInfoActivity = this;
        
        RoadwayData.initRoadData();
        CCTVData.initCCTVData();
        
        setAppName(R.string.app_simple_name);
        setAppIcon(R.drawable.app_icon);
        
        //mAppYouTubeChannelListFragment = new AppYouTubeChannelListFragment();
        //addFragment(mAppYouTubeChannelListFragment, mAppBarBtnImage5, R.drawable.ic_action_important, true);
        
        mAppYouTubePlayVideoListFragment = new AppYouTubePlayVideoListFragment();
		addFragment(mAppYouTubePlayVideoListFragment, mAppBarBtnImage5, R.drawable.ic_action_important, true);
        
        //mRoadwayFragment = new RoadwayFragment(); 
        //addFragment(mRoadwayFragment, mAppBarBtnImage5, R.drawable.ic_action_important, true);
        
        mSpeedFragment = new SpeedFragment();
        addFragment(mSpeedFragment, null, 0, false);
        
        mCCTVFragment = new CCTVFragment();
        addFragment(mCCTVFragment, null, 0, false);
        
        mAppPromoteFragment = new AppPromoteFragment();
        addFragment(mAppPromoteFragment, mAppBarOverflowImage, 0, false);
        
        addOverflowButton();        
        setAppBarButton(-1);
        
        loadAdMobInterstitial();
        
        mAppBackLayout.setVisibility(View.INVISIBLE);
        mHeaderLayout.setVisibility(View.INVISIBLE);
        mCoverLayout.setVisibility(View.INVISIBLE);
    }

    /*
    public void onDestroy()
    {
    	super.onDestroy();
    	
    	//saveInstanceState();
    	
    	//@@@android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    public void onSaveInstanceState(Bundle savedInstanceState) 
    {
        super.onSaveInstanceState(savedInstanceState);
        
        //saveInstanceState();
    }    
    
    protected void onAppTouch(View v, MotionEvent event)
    {
    	super.onAppTouch(v, event);
    	
		if (v == mAppBarBtnImage3)
			showIncidentFragment();
		
		else if (v == mAppBarBtnImage4)
			showSpeedMapFragment();
		
		else if (v == mAppBarBtnImage5)
			showRoadwayFragment();
		
		else if (v == mAppBarOverflowImage)
			showAppPromoteFragment();
    }
    */
    
    @Override
    public void onBackPressed() 
    {
    	int c = mBodyLayout.getChildCount();
    	View v = mBodyLayout.getChildAt(0);
    	int h = v.getHeight();
    	int w = v.getWidth();
    	
    	v = mAppBarLayout.getChildAt(0);
    	h = v.getHeight();
    	w = v.getWidth();
    	
    	
    	super.onBackPressed();

    	// 從路況攝影機回到路況時速圖顯示插頁廣告
    	if (((AppFragActivity.mPrevFragment == mCCTVFragment  && AppFragActivity.mDispFragment == mSpeedFragment) ||
			 (AppFragActivity.mPrevFragment == mSpeedFragment && AppFragActivity.mDispFragment == mRoadwayFragment)) &&
			!mIsShowAdMobInterstitial)
    	{
    		mIsShowAdMobInterstitial = showAdMobInterstitial();
    	}
    	
    	/*
    	Fragment fragment;
    	if (mStackFragment.size() > 0)
    		fragment = mStackFragment.lastElement();
    	else
    		fragment = null;
    	
    	if (fragment == null)
    	{
    		finish();
    	}
    	else if (fragment == mSpeedFragment || fragment == mCCTVFragment || fragment == mAppPromoteFragment)
        {
    		changeFragment(null, false, false);
        }
        else if (fragment == mRoadwayFragment || fragment == mSpeedMapFragment || fragment == mIncidentFragment)
        {
        	mLeaveFragment = fragment;
        	showAdMobInterstitial();
        	
        	AppCache.deleteCache(mContext);
    		finish();        	
        }
        */
    }   
    
    /*
    public void clickBack() 
    {
        finish();
    }    

    public void showRoadwayFragment() 
    {
    	if (mRoadwayFragment == null)
    		mRoadwayFragment = new RoadwayFragment();

    	changeFragment(mRoadwayFragment, true, true);
    	
    	mRoadwayFragment.onAppDataChanged();
    }

    public void showSpeedMapFragment() 
    {
    	if (mSpeedMapFragment == null)
    		mSpeedMapFragment = new SpeedMapFragment();
    	
    	changeFragment(mSpeedMapFragment, true, true);
    	
    	//mSpeedMapFragment.onAppDataChanged(null);
    }
    
    public void showIncidentFragment() 
    {
    	if (mIncidentFragment == null)
    		mIncidentFragment = new IncidentFragment();
    	
    	changeFragment(mIncidentFragment, true, true);
    }

    public void showAppPromoteFragment() 
    {
    	if (mAppPromoteFragment == null)
    		mAppPromoteFragment = new AppPromoteFragment();
    	
    	changeFragment(mAppPromoteFragment, true, false);
    }
    */
    
    public void showSpeedFragment(String roadEId) 
    {
    	//if (mSpeedFragment == null)
    	//	mSpeedFragment = new SpeedFragment();
    	
    	mSpeedFragment.mRoadEId = roadEId;
    	mSpeedFragment.mUpdateData = true;    	
    	changeFragment(mSpeedFragment, true, false);
    }
    
    public void showCCTVFragment(ArrayList<CCTV> cctvList, String dir) 
    {
    	//if (mCCTVFragment == null)
    	//	mCCTVFragment = new CCTVFragment();
    	
    	mCCTVFragment.mDir = dir;
    	mCCTVFragment.mCCTVList = cctvList;    	
    	changeFragment(mCCTVFragment, true, false);
    }

    /*
    private void changeFragment(Fragment dispFragment, boolean push, boolean clear)
    {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		
		Fragment prevFragment;
		if (!mStackFragment.isEmpty())
			prevFragment = mStackFragment.lastElement();
		else
			prevFragment = null;
			
    	if (clear)
    		mStackFragment.clear();
    	
    	if (push)    		
    		mStackFragment.push(dispFragment);
    	else
    	{
    		prevFragment = mStackFragment.pop();
    		dispFragment = mStackFragment.lastElement();
    	}

		if (prevFragment != null)
			transaction.hide(prevFragment);
		
    	if (!dispFragment.isAdded())
    		transaction.add(R.id.bodyLayout, dispFragment);
    	else
    		transaction.show(dispFragment);

		transaction.commit();
		
		if (mStackFragment.size() == 1)
			setAppName(R.string.app_simple_name);
    }
    
    private void saveInstanceState()
    {
    	int frameId = 0;
    	if (mLeaveFragment == mRoadwayFragment)
    		frameId = 0;
    	else if (mLeaveFragment == mSpeedMapFragment)
    		frameId = 1;
    	else if (mLeaveFragment == mIncidentFragment)
    		frameId = 2;
    		
        Editor edit = mSharedPrefs.edit();        
        edit.putInt("FrameId", frameId);
        edit.commit();    	
    }
    */
}
