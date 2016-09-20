package com.example.dophone.bean;


import android.graphics.drawable.Drawable;

/**
 * ������Ϣ��ҵ��bean
 */
public class AppInfo {
	private Drawable icon;   //����ͼ��
	private String name;     //������
	private String packname; //�������
	private boolean inRom;   //�Ƿ�װ���ֻ��ڴ�
	private boolean userApp; //�Ƿ����û�����
	private int uid;    //�����û�id
	public String rx;  //���ܵ�����
	public String tx;  //���͵�����
	public String totalx;  //�ܹ�����
	public String md5 = "";
	public String sourceDir = "";
	
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackname() {
		return packname;
	}
	public void setPackname(String packname) {
		this.packname = packname;
	}
	public boolean isInRom() {
		return inRom;
	}
	public void setInRom(boolean inRom) {
		this.inRom = inRom;
	}
	public boolean isUserApp() {
		return userApp;
	}
	public void setUserApp(boolean userApp) {
		this.userApp = userApp;
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	
	public String getRx() {
		return rx;
	}
	public void setRx(String rx) {
		this.rx = rx;
	}
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	public String getTotalx() {
		return totalx;
	}
	public void setTotalx(String totalx) {
		this.totalx = totalx;
	}
	@Override
	public String toString() {
		return "AppInfo [name=" + name + ", packname=" + packname + ", inRom="
				+ inRom + ", userApp=" + userApp + "]";
	}
	
	
	
	
}
