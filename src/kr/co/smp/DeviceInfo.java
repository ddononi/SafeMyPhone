package kr.co.smp;

import android.telephony.TelephonyManager;

/**
 * 단말기 정보를 얻어오는 클래스
 * 전화번호 및 sim 정보를 얻어온다.
 *
 */
public class DeviceInfo{
	private static TelephonyManager _telephony = null;
	private static DeviceInfo di = new DeviceInfo();
	private DeviceInfo(){

	}

	public static DeviceInfo setDeviceInfo(final TelephonyManager telphony){
		_telephony = telphony;
		return di;
	}

	public String getDeviceNumber(){
		String phoneNum = (_telephony != null)?_telephony.getLine1Number():"";
		//String cellNum = phoneNum.substring(phoneNum.length()-8, phoneNum.length());
		String cellNum = phoneNum.replace("+82", "0");
		return cellNum;
	}
	/*
	public String getDeviceNumber2(){
		String phoneNum = (_telephony != null)?_telephony.getLine1Number():"";
		//String cellNum = phoneNum.substring(phoneNum.length()-8, phoneNum.length());

		return phoneNum;
	}
*/
	public String getMyDeviceID(){
		return (_telephony != null)? _telephony.getDeviceId():"";
	}

	public String myDeviceSIM(){
		return (_telephony != null)? _telephony.getSimSerialNumber():"";
	}

}
