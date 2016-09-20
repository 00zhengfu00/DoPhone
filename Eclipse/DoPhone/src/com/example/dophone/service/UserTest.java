package com.example.dophone.service;

import android.test.AndroidTestCase;
import android.util.Log;

public class UserTest extends AndroidTestCase {
	public void datatest() throws Throwable{
		DatabaseHelper dbhepler=new DatabaseHelper(this.getContext());
		dbhepler.getReadableDatabase();
	}
	//ע��
	public void registerTest(String userName,String password) throws Throwable{	
		UserService uService=new UserService(this.getContext());
		boolean flag=uService.register(userName,password);
		if(flag){
			Log.i("TAG","ע��ɹ�");
		}else{
			Log.i("TAG","ע��ʧ��");
		}
	}
	//��¼
	public void loginTest(String userName,String password) throws Throwable{
		UserService uService=new UserService(this.getContext());
		boolean flag=uService.login(userName, password);
		if(flag){
			Log.i("TAG","��¼�ɹ�");
		}else{
			Log.i("TAG","��¼ʧ��");
		}
	}
	
}
