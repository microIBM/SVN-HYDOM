package com.carinsurance.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.carinsurance.maputil.AMapUtil;

//		uid=new MySharePreferences(AfterLandingActivity.this, "user").get("uid", "");
/**
 * 高德地图的定位工具
 * 
 * @author Administrator
 *
 */
public class AMapLocationUtils implements AMapLocationListener, Runnable {
	private String TAG = "AMapLocationUtils";
	private LocationManagerProxy aMapLocManager = null;
	// private TextView myLocation;
	private AMapLocation aMapLocation;// 用于判断定位超时
	private Handler handler = new Handler();
	Context context;
	// 超时时间
	int chaoshi_time = 12000;
	// 定位是否开启
	boolean islocation = true;

	public AMapLocationUtils(Context ct) {
		context = ct;
		// myLocation = (TextView) findViewById(R.id.myLocation);
		aMapLocManager = LocationManagerProxy.getInstance(context);
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapLocManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 2000, 10, this);
		handler.postDelayed(this, chaoshi_time);// 设置超过12秒还没有定位到就停止定位

	}

	public void setOnAMapLocationClistener(getAMapLocation location) {
		location.getAMapLocation(aMapLocation,islocation);
	}

	// @Override
	// protected void onPause() {
	// super.onPause();
	// stopLocation();// 停止定位
	// }

	/**
	 * 销毁定位
	 */
	public void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destory();
		}
		aMapLocManager = null;
	}

	/**
	 * 此方法已经废弃
	 */
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	/**
	 * 混合定位回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			this.aMapLocation = location;// 判断超时机制
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			String cityCode = "";
			String desc = "";
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				cityCode = locBundle.getString("citycode");
				desc = locBundle.getString("desc");
			}
			String str = ("定位成功:(" + geoLng + "," + geoLat + ")" + "\n精    度    :" + location.getAccuracy() + "米" + "\n定位方式:" + location.getProvider() + "\n定位时间:" + AMapUtil.convertToTime(location.getTime()) + "\n城市编码:" + cityCode + "\n位置描述:" + desc + "\n省:" + location.getProvince() + "\n市:" + location.getCity() + "\n区(县):" + location.getDistrict() + "\n区域编码:" + location.getAdCode());
			// myLocation.setText(str);
		}
	}

	@Override
	public void run() {
		if (aMapLocation == null) {
			islocation=false;
			// ToastUtil.show(context, "12秒内还没有定位成功，停止定位");
			// myLocation.setText("12秒内还没有定位成功，停止定位");
			stopLocation();// 销毁掉定位
		}
	}

	public interface getAMapLocation {
		void getAMapLocation(AMapLocation aMapLocation,boolean islocation);
	}

	public void startLocation(Context context) {
		// 这只是一个使用接口的模板
		final AMapLocationUtils aMapLocationUtils = new AMapLocationUtils(context);
		new Thread(new Runnable() {
			boolean isRun = true;

			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (isRun) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					aMapLocationUtils.setOnAMapLocationClistener(new getAMapLocation() {

						@Override
						public void getAMapLocation(AMapLocation aMapLocation,boolean islocation) {
							// TODO Auto-generated method stub
							Log.i(TAG, "" + aMapLocation);
							if (aMapLocation != null) {

								Log.i(TAG, "经度-》" + aMapLocation.getLongitude() + "纬度=》" + aMapLocation.getLatitude());
								isRun = false;
							}

						}
					});
				}

			}
		}).start();
	}

}

// 这只是一个使用接口的模板
// final AMapLocationUtils aMapLocationUtils=new
// AMapLocationUtils(TestActivity.this);
// new Thread(new Runnable() {
// boolean isRun=true;
// @Override
// public void run() {
// // TODO Auto-generated method stub
//
// while(isRun)
// {
// try {
// Thread.sleep(200);
// } catch (InterruptedException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// aMapLocationUtils.setOnAMapLocationClistener(new getAMapLocation() {
//
// @Override
// public void getAMapLocation(AMapLocation aMapLocation) {
// // TODO Auto-generated method stub
// Log.i(TAG,""+aMapLocation);
// if(aMapLocation!=null)
// {
// Log.i(TAG,
// "经度-》"+aMapLocation.getLongitude()+"纬度=》"+aMapLocation.getLatitude());
// isRun=false;
// }
//
// }
// });
// }
//
//
//
//
// }
// }).start();

