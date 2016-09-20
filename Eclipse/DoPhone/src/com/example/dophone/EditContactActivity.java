package com.example.dophone;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dophone.Dao.ContactManager;
import com.example.dophone.Dao.UserContactDao;
import com.example.dophone.bean.ContactEntity;

public class EditContactActivity extends Activity {

	EditText et_name,et_tel,et_birthday;
	Button bt_back,bt_sumbit;
	ImageView iv_head;
	ContactManager contactManager;
	ContentResolver cr;
	int contact_id;
	int type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_contact);
		contactManager = new ContactManager();
		cr = getContentResolver();
		findView();
		initView();
		if(type==Contants.TYPE_EDIT){
			contact_id = getIntent().getIntExtra(UserContactDao.COL_ID, -1);
			if (contact_id > 0) {
				ContactEntity ce = contactManager.getOneContactById(contact_id,cr);
				et_name.setText(ce.getName());
				et_tel.setText(ce.getContact_tel());
				et_birthday.setText(ce.getContact_birthday());
				if(ce.getContact_headicon() != null){
					iv_head.setImageBitmap(ce.getContact_headicon());
				}else{
					iv_head.setImageResource(R.drawable.defaulthead);
				}
			}
		}else if(type==Contants.TYPE_NEW){
			Toast.makeText(this, "�½���ϵ��", 0).show();
		}
		
	}
	
	
	
	
	
	private void initView() {
		// TODO Auto-generated method stub
		iv_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, Contants.GETCONTENT_RQUEST_CODE);
			}
		});
		
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		bt_sumbit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				String name = "�½���ϵ��";
				if(et_name.getText().toString().equals("")){
					//values.put(UserContactDao.COL_NAME, name);
					Toast.makeText(EditContactActivity.this, name+"�����ֲ���Ϊ��", 0).show();
				}else{
					values.put(UserContactDao.COL_NAME, et_name.getText().toString());
				
					values.put(UserContactDao.COL_BIRTHDAY, et_birthday.getText().toString());
					values.put(UserContactDao.COL_TEL, et_tel.getText().toString());
					
					Bitmap bm = iv_head.getDrawingCache();
					if(bm != null)
					{
						//����������
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						//��ͼƬѹ������
						bm.compress(Bitmap.CompressFormat.PNG, 100, os);
						//ֱ�ӱ���
						values.put(UserContactDao.COL_HEADICON, os.toByteArray());
	
					}
				
					if(type ==Contants.TYPE_EDIT){
						contactManager.updateContact(contact_id,values,cr);
					}else if(type == Contants.TYPE_NEW){
						Uri uri =contactManager.insertContact(values, cr);
						Toast.makeText(EditContactActivity.this, ContentUris.parseId(uri)+":�½���ϵ��", 0).show();
					}
					setResult(RESULT_OK);
					finish();
				}
			}
		});
		
	}





	private void findView() {
		// TODO Auto-generated method stub
		et_name = (EditText) findViewById(R.id.et_name);
		et_tel = (EditText) findViewById(R.id.et_tel);
		et_birthday = (EditText) findViewById(R.id.et_birthday);
		bt_back = (Button) findViewById(R.id.bt_back);
		bt_sumbit = (Button) findViewById(R.id.bt_sumbit);
		iv_head = (ImageView) findViewById(R.id.iv_head);
		type = getIntent().getIntExtra(Contants.TYPE, Contants.TYPE_NEW);
		// ���Ա��滺��ͼƬ
		iv_head.setDrawingCacheEnabled(true);
	}
	
	





	public void confirm(View v)
	{
		ContentValues values = new ContentValues();
		if(et_name.getText().toString()==null){
			return;
		}
		values.put(UserContactDao.COL_NAME, et_name.getText().toString());
		values.put(UserContactDao.COL_BIRTHDAY, et_birthday.getText().toString());
		values.put(UserContactDao.COL_TEL, et_tel.getText().toString());
		
		Bitmap bm = iv_head.getDrawingCache();
		if(bm != null)
		{
			//����������
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			//��ͼƬѹ������
			bm.compress(Bitmap.CompressFormat.PNG, 100, os);
			//ֱ�ӱ���
			values.put(UserContactDao.COL_HEADICON, os.toByteArray());

		}
	
		if(type ==Contants.TYPE_EDIT){
			contactManager.updateContact(contact_id,values,cr);
		}else if(type == Contants.TYPE_NEW){
			Uri uri =contactManager.insertContact(values, cr);
			Toast.makeText(this, ContentUris.parseId(uri)+":�½���ϵ��", 0).show();
		}
		setResult(RESULT_OK);
		finish();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {
		case Contants.ZOO_RQUEST_CODE:
			Uri uri = intent.getData();
			String path = null;
			if (uri != null) {
				path = uri.getPath();

				bm = BitmapFactory.decodeFile(path);

				iv_head.setImageBitmap(bm);
			} else {
				Bundle bundle = intent.getExtras();
				Bitmap photo = null;

				if (bundle != null) {
					bm = (Bitmap) bundle.get("data"); // get bitmap
					iv_head.setImageBitmap(bm);
				}

			}
			break;
		case Contants.GETCONTENT_RQUEST_CODE:
			if (intent == null) {
				return;
			}

			uri = intent.getData();
			path = null;
			if (uri != null) {
				path = uri.getPath();

				bm = BitmapFactory.decodeFile(path);

				iv_head.setImageBitmap(bm);
				startPhotoZoom(uri);
			} else {
				Bundle bundle = intent.getExtras();
				Bitmap photo = null;

				if (bundle != null) {
					bm = (Bitmap) bundle.get("data"); // get bitmap
					iv_head.setImageBitmap(bm);
				}

			}

			break;
		}
	}
	
	
	Bitmap bm;
	
	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		/*
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP ��֮ǰС��û��ϸ��������ʵ��׿ϵͳ���Ѿ����Դ�ͼƬ�ü�����, ��ֱ�ӵ����ؿ�ģ�С����C C++
		 * ���������ϸ�˽�ȥ�ˣ������Ӿ������ӣ������о���������ô ��������...���
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, Contants.ZOO_RQUEST_CODE);
	}
	
}
