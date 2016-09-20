package com.example.dophone;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dophone.Dao.ContactManager;
import com.example.dophone.Dao.UserContactDao;
import com.example.dophone.bean.ContactEntity;

public class SmsActivity extends Activity {
	EditText ed_sms;
	TextView tv_sms_tel;
	ContentResolver cr;
	ContactManager contactManager;
	VonRecevier vReceiver;
	VonRecevier vdReceiver;
	int contact_id;
	public static final String action1 = "com.tanyaoxiang.sms";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		cr = getContentResolver();
		contactManager = new ContactManager();
		vReceiver = new VonRecevier();
		registerReceiver(vReceiver, new IntentFilter("com.broadcast.send"));
		vdReceiver = new VonRecevier();
		registerReceiver(vdReceiver, new IntentFilter("com.broadcast.deli"));
		findView();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		contact_id = getIntent().getIntExtra(UserContactDao.COL_ID, -1);
		if (contact_id > 0) {
			ContactEntity ce = contactManager.getOneContactById(contact_id,cr);
			tv_sms_tel.setText(ce.getContact_tel());
		}
	}

	public void findView(){
		ed_sms = (EditText) findViewById(R.id.ed_sms);
		tv_sms_tel = (TextView) findViewById(R.id.tv_sms_tel);
	}
	
	
	public void submit(View v){
		SmsManager smsManager = SmsManager.getDefault();
		Intent sentIntent  = new Intent();
		sentIntent.setAction("com.broadcast.send");
		sentIntent.putExtra("content", "���ŷ��ͳɹ�");
		PendingIntent sentPI = PendingIntent.getBroadcast(
				this, 
				1,//�㲥��������
				sentIntent,
				0);	
		Intent deliIntent  = new Intent();
		sentIntent.setAction("com.broadcast.deli");
		sentIntent.putExtra("content", "�Է��ɹ��յ�����");
		PendingIntent deliPI = PendingIntent.getBroadcast(
				this, 
				1,//�㲥��������
				deliIntent,
				0);	
		String tel = tv_sms_tel.getText().toString();
		smsManager.sendTextMessage(tel,//���ŵķ���Ŀ�����
					null,  //�������ĺ���
					ed_sms.getText().toString(),//��������
					sentPI,//�����Ƿ��ͳɹ�����ͼ
					deliPI); //�����Ƿ񱻶Է��յ�����ͼ
		finish();
	}
	
	
	public void cancel(View v){
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(vReceiver);
		super.onDestroy();
	}

}

class VonRecevier extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, intent.getStringExtra("content"), 0).show();
	}
}
