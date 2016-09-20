package com.example.dophone.util;

import java.io.File;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @see ����Ǯ�� ϵͳ����������
 * @version 2016/1/23
 * @author ̷ҫ��
 */
public class SysCtlUtil {

	/**
	 * @param ������������
	 * @param activity
	 * @param view
	 */
	public static void onClickHideKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * @see ����绰
	 * @param activity
	 * @param str
	 */
	public static void CallPhone(Activity activity, String str) {
		str = str.trim();// ɾ���ַ����ײ���β���Ŀո�

		if (str != null && !str.equals("")) {
			// ����ϵͳ�Ĳ��ŷ���ʵ�ֵ绰������
			// ��װһ������绰��intent�����ҽ��绰�����װ��һ��Uri������
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ str));
			activity.startActivity(intent);// �ڲ���
		}
	}

	private static Runnable mRunnable = null;
	private static Handler Handler = new Handler();

	/**
	 * @param 60s����ʱ
	 * @param view
	 *            :View�ؼ�
	 * @param getcode
	 */
	public static void AuthCode(final TextView view, final String getcode) {

		mRunnable = new Runnable() {
			int mSumNum = 60;

			@Override
			public void run() {
				Handler.postDelayed(mRunnable, 1000);
				view.setText("ʣ��" + mSumNum + " s");
				view.setEnabled(false);
				mSumNum--;
				if (mSumNum < 0) {
					view.setText(getcode);
					view.setEnabled(true);
					// �ɵ������ʱ�����´μ������ۼ�
					Handler.removeCallbacks(mRunnable);
				}
			}

		};
		Handler.postDelayed(mRunnable, 1000);
	}

	/**
	 * @param 60s����ʱ
	 * @param view
	 *            :View�ؼ�
	 */
	public static void AuthCode(final Button view) {

		mRunnable = new Runnable() {
			int mSumNum = 60;

			@Override
			public void run() {
				Handler.postDelayed(mRunnable, 1000);
				view.setText("ʣ��" + mSumNum + " s");
				view.setEnabled(false);
				mSumNum--;
				if (mSumNum < 0) {
					view.setText("��ȡ��֤��");
					view.setEnabled(true);
					// �ɵ������ʱ�����´μ������ۼ�
					Handler.removeCallbacks(mRunnable);
				}
			}

		};

		Handler.postDelayed(mRunnable, 1000);
	}



	/**
	 * @param ����MD5����32λ�ַ���
	 * @param MStr
	 *            :��Ҫ���ܵ��ַ���
	 * @return
	 */
	public static String Md5(String MStr) {
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(MStr.getBytes());
			return bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			return String.valueOf(MStr.hashCode());
		}
	}

	// MD5�ڲ��㷨---------------�����޸�!
	private static String bytesToHexString(byte[] bytes) {
		// http://stackoverflow.com/questions/332079
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	/**
	 * @param ��װ��Toast�ķ���
	 * @param activity
	 * @param str
	 * @param isLong
	 */
	public static void ShowToast(Activity activity, String str, boolean isLong) {
		if (isLong) {
			Toast.makeText(activity, str, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(activity, str, Toast.LENGTH_SHORT).show();
		}
	}

	@SuppressWarnings("rawtypes")
	public static String ShowURL(String url, List<String> keylist, Map valueMap) {
		String urls = url + "?";
		for (int i = 0; i < keylist.size(); i++) {
			urls += keylist.get(i).toString() + "="
					+ valueMap.get(keylist.get(i)).toString() + "&";
		}
		
		return urls;
	}

	
	/**
	 * ��δ��֤�����Ƿ���ȷ
	 * 
	 * @param from
	 * @param to
	 * @param source
	 * @return
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer bf = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			bf.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = source.indexOf(from);
		}
		bf.append(source);
		return bf.toString();
	}

	/**
	 * �����ֻ��м�4λ����
	 * 
	 * @param mobile_phone
	 * @return
	 */
	public static String hideMobilePhone(String mobile_phone) {
		return mobile_phone.substring(0, 3) + "****"
				+ mobile_phone.substring(7, 11);
	}

	/**
	 * �ж��ַ����Ƿ�Ϊ�� Ϊ�ռ�true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullString(String str) {
		return (("").equals(str) || str == null || ("null").equals(str));
	}

	public static int StringToInt(String str) {
		if (SysCtlUtil.isNullString(str)) {
			str = "0";
		}
		return Integer.parseInt(str);
	}

	public static double StringToDouble(String str) {
		if (SysCtlUtil.isNullString(str)) {
			str = "0";
		}
		return Double.parseDouble(str);
	}

	/**
	 * ���ַ�����ʽ��Ϊ����λС�����ַ���
	 * 
	 * @param str
	 * @return
	 */
	public static String format2Decimals(String str) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		if (df.format(StringToDouble(str)).startsWith(".")) {
			return "0" + df.format(StringToDouble(str));
		} else {
			return df.format(StringToDouble(str));
		}
	}

	/**
	 * �ж��Ƿ�Ϊ��ʵ�ֻ���
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	/**
	 * ��֤��������
	 * 
	 * @param cardNo
	 * @return
	 */
	public static boolean isBankCard(String cardNo) {
		Pattern p = Pattern
				.compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$");
		Matcher m = p.matcher(cardNo);

		return m.matches();
	}

	/**
	 * 15λ��18λ���֤�����������ʽ ���֤��֤
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean validateIdCard(String idCard) {
		// 15λ��18λ���֤�����������ʽ
		String regIdCard = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		Pattern p = Pattern.compile(regIdCard);
		return p.matcher(idCard).matches();
	}

	/**
	 * ��ȡ��ǰ����ʱ��
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhMMss");
		return sdf.format(new Date());
	}

	/**
	 * ��ȡ��ǰ����
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	/**
	 * ��ʽ�����п� ��* 3749 **** **** 330
	 * 
	 * @param cardNo
	 * @return
	 */
	public static String formatCard(String cardNo) {
		String card = "";
		card = cardNo.substring(0, 4) + " **** **** ";
		card += cardNo.substring(cardNo.length() - 4);
		return card;
	}

	/**
	 * ���п�����λ
	 * 
	 * @param cardNo
	 * @return
	 */
	public static String formatCardEndFour(String cardNo) {
		String card = "";
		card += cardNo.substring(cardNo.length() - 4);
		return card;
	}

	/**
	 * ��ȡ��Ӧ��ͼƬ����Ŀ¼
	 * 
	 * @param activity
	 * @return
	 */
	public static File getCecheFolder(Activity activity) {
		File folder = new File(activity.getCacheDir(), "IMAGECACHE");
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder;
	}

	/**
	 * ��ȡ��Ӧ��ͼƬ����Ŀ¼
	 * 
	 * @param activity
	 * @return
	 */
	public static File getCecheFolder(Context context) {
		File folder = new File(context.getCacheDir(), "IMAGECACHE");
		if (!folder.exists()) {
			folder.mkdir();
		}
		return folder;
	}

	public static void ShowToast(Context cxt, String str, boolean isLong) {
		if (isLong) {
			Toast.makeText(cxt, str, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(cxt, str, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * ʱ���ת�� String��ʽΪ��2016��01��
	 * 
	 * @param times
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getTimes(String times) {
		String time = "";
		try {
			Date epoch = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
					.parse("01/" + times.toString().substring(5, 7) + "/"
							+ times.toString().substring(0, 4) + " 01:00:00");
			time = epoch.getTime() / 1000 + "";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	

	
	/**
	 * �ֶ������listView�ĸ߶ȣ����ǲ��پ��й���Ч��
	 * @param listView
	 */
	public static void fixListViewHeight(ListView listView) {   

        // ���û��������������������ListViewû��������ء�  

        ListAdapter listAdapter = listView.getAdapter();  

        int totalHeight = 0;   

        if (listAdapter == null) {   

            return;   

        }   

        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {     

            View listViewItem = listAdapter.getView(index , null, listView);  

            // ��������View �Ŀ��   

            listViewItem.measure(0, 0);    

            // ������������ĸ߶Ⱥ�

            totalHeight += listViewItem.getMeasuredHeight();    

        }   

   

        ViewGroup.LayoutParams params = listView.getLayoutParams();   

        // listView.getDividerHeight()��ȡ�����ָ����ĸ߶�   

        // params.height����ListView��ȫ��ʾ��Ҫ�ĸ߶�    

        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   

        listView.setLayoutParams(params);   

    }   
	
	/**
	 * ��ȡ�ֻ�Ψһ��ʶ���к�
	 * @param context
	 * @return
	 */
	public static String getSerialNumber(Context context){
		
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		StringBuilder sb = new StringBuilder();
		sb.append("\nDeviceId(IMEI) =" + tm.getDeviceId());
		// sb.append("\nDeviceSoftwareVersion = " +
		// tm.getDeviceSoftwareVersion());
		// sb.append("\nLine1Number = " + tm.getLine1Number());
		// sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
		// sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
		sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
		// sb.append("\nNetworkType = " + tm.getNetworkType());
		// sb.append("\nPhoneType = " + tm.getPhoneType());
		// sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
		// sb.append("\nSimOperator = " + tm.getSimOperator());
		// sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
		sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
		// sb.append("\nSimState = " + tm.getSimState());
		sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
		// sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
		String Android_id = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);//
		String phoneName = Build.MODEL;// Galaxy nexus Ʒ������
		String phoneBrand = Build.BRAND;// google
		String manuFacturer = Build.MANUFACTURER;// samsung Ʒ��
		Log.d("info====================", sb.toString());
		Log.d("info====================", Android_id.toString());
		Log.d("info====================", phoneName.toString());
		Log.d("info====================", phoneBrand.toString());
		Log.d("info====================", manuFacturer.toString());
		Log.v("getSerialNumber()", getSerialNumber());
		System.out.println(manuFacturer + "-" + phoneName + "-" + getSerialNumber());
		return manuFacturer + "-" + phoneName + "-" + getSerialNumber();
	}
	
	/**
	 * ���к�
	 * @return
	 */
	public static String getSerialNumber() {

		String serial = null;

		try {

			Class<?> c = Class.forName("android.os.SystemProperties");

			Method get = c.getMethod("get", String.class);

			serial = (String) get.invoke(c, "ro.serialno");

		} catch (Exception e) {

			e.printStackTrace();

		}

		return serial;

	}
}
