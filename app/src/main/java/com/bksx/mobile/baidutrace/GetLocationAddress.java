package com.bksx.mobile.baidutrace;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


/**
 * Created by Administrator on 2017/6/22.
 */

public  class GetLocationAddress {
    private static GetLocationAddress instance = new GetLocationAddress();

    public LocationClient mLocationClient = null;
    private BDAbstractLocationListener myListener = new MyLocationListener();
    private static String x, y;//经纬度和地址

    private static String address="未找到地址";

    private GetLocationAddress(){

    }

    private static Context mContext;

    public static GetLocationAddress getInstance(Context context) {
        mContext = context;
        return instance;
    }

    public String justGetAddress(){
        return address;
    }

    public String getX() {
        return x;
    }

    public  void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void getAdd(){
        //声明LocationClient类
        mLocationClient = new LocationClient(mContext);
        //初始化定位信息
        initLocation();
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);

    }

    public void requestLocation(){
        if (mLocationClient != null){
            if (mLocationClient.isStarted()){
                mLocationClient.requestLocation();
                mLocationClient.start();
                Log.i("TAG", "===requestLocation: " + "重新请求地址");
            }else {
                Log.i("TAG", "===requestLocation: " + "未请求地址");
            }
        }
    }

    public  class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);
            y = "" + location.getLatitude();//获取纬度信息
            x = "" + location.getLongitude(); //获取经度信息
            address = location.getAddress().toString();//获取详细地址
            sb.append("\nradius : ");
            sb.append(location.getRadius());//获取定位精准度
            Log.i("TAG", "===onReceiveLocation: " + location.getLocType());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                // GPS定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                address = location.getAddrStr();//获取地址信息
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息
                address = location.getAddrStr();     //获取地址信息
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                Log.e("TAG", "onReceiveLocation: "+location.getLatitude() );
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            if (location.getPoiList() != null && location.getPoiList().size() > 0){
                String name  = location.getPoiList().get(0).getName();
                address = address + name;
            }
            Log.i("BaiduLocationApiDem", "X=====" + x + "\nY=====" + y + "\nADDR========" + location.getAddrStr());
            //mLocationClient.stop();
        }
    }
    private void initLocation() {
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.disableCache(true);
        //关闭缓存定位

        option.setWifiCacheTimeOut(1000);
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);

        //开始获取地址
        mLocationClient.start();
        //mLocationClient.stop();
    }


    public void stopLocation(){
        mLocationClient.stop();
    }
}
