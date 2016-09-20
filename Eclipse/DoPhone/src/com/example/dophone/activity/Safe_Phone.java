package com.example.dophone.activity;

import com.example.dophone.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




public class Safe_Phone extends Activity {
	
	private SharedPreferences sp;
	
	private TextView tv_safenumber;
	private ImageView iv_protecting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// ȫ����ʾ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		//�ж�һ�£��Ƿ����������򵼣����û������������ת��������ҳ��ȥ���ã���������ŵ�ǰ��ҳ��
		boolean configed = sp.getBoolean("configed", false);
		if(configed){
			// �����ֻ�����ҳ��
			setContentView(R.layout.safe_phone);
			tv_safenumber = (TextView) findViewById(R.id.tv_safenumber);
			iv_protecting = (ImageView) findViewById(R.id.iv_protecting);
			//�õ��������õİ�ȫ����
			String safenumber = sp.getString("safenumber", "");
			tv_safenumber.setText(safenumber);
			//���÷���������״̬
			boolean protecting = sp.getBoolean("protecting", false);
			if(protecting){
				//�Ѿ�������������
				iv_protecting.setImageResource(R.drawable.lock);
			}else{
				//û�п�����������
				iv_protecting.setImageResource(R.drawable.unlock);
			}
			
			
			
		}else{
			//��û������������
			Intent intent = new Intent(this,Safe_Phone_Setup1.class);
			startActivity(intent);
			//�رյ�ǰҳ��
			finish();
		}
		
		
	}
	/**
	 * ���½����ֻ�����������ҳ��
	 * @param view
	 */
	public void reEnterSetup(View view){
		Intent intent = new Intent(this,Safe_Phone_Setup1.class);
		startActivity(intent);
		//�رյ�ǰҳ��
		finish();
	}
	
	
	//����������
	public void SturnToMain(View view){
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
		//�رյ�ǰҳ��
		finish();
	}
	
	public void offS(View view){
		Editor editor = sp.edit();  
        editor.remove("password");  
        //editor.clear();   
        editor.commit();
        Toast.makeText(this, "���ܱ����رճɹ���", 1000).show();
		//�رյ�ǰҳ��
		finish();
	}

}

