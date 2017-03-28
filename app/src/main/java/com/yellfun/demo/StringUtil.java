package com.yellfun.demo;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**字符串工具
 * Created by sharyuke on 3/28/17.
 */
public class StringUtil {

    /**
     * 获取手机UUID  注意:在这里要申请权限
     */
    private static String getPhoneUUID(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = telephonyManager.getDeviceId();
        String tmSerial = "" + telephonyManager.getSimSerialNumber();
        String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode()).toString().replaceAll("-", "");
    }
}
