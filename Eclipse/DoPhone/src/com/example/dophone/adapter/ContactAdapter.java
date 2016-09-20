package com.example.dophone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dophone.R;
import com.example.dophone.bean.ContactEntity;
import com.example.pinyinlib.PinyinAdapter;
import com.example.pinyinlib.PinyingBean;



public class ContactAdapter extends PinyinAdapter{

	public ContactAdapter(Context context, List<PinyingBean> strList) {
		super(context, strList);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getChildId(int group, int child) {
		// TODO Auto-generated method stub
		return ((ContactEntity)getChild(group, child)).get_id();
	}

	//��Ԫ�صĴ�������
	@Override
	public View getChildView(int group, int child, boolean arg2,
			View contentView, ViewGroup arg4) {
		ViewHolder holder ;
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(R.layout.list_children_item, null);
			//�Ż�2�������ظ�ʹ��findViewById
			holder = new ViewHolder();
			holder.iv_icon = (ImageView) contentView.findViewById(R.id.iv_item_head);
			holder.tv_name = (TextView) contentView.findViewById(R.id.tv_child_name);
		
			contentView.setTag(holder);
		}else{//��ͼ֮ǰ�Ѿ�������ϵ������
			holder = (ViewHolder) contentView.getTag();
		}
	/*	TextView tv_name = (TextView) contentView.findViewById(R.id.tv_child_name);
		ImageView iv_head = (ImageView) contentView.findViewById(R.id.iv_item_head);*/
		//��ȡchild��ʵ����
		ContactEntity ce = (ContactEntity) getChild(group, child);
		
		if(ce.getContact_headicon() != null)
		{
			holder.iv_icon.setImageBitmap(ce.getContact_headicon());
		}else{
			holder.iv_icon.setImageResource(R.drawable.defaulthead);
		}
		holder.tv_name.setText(ce.getName());
		
		
		return contentView;
	}
	class ViewHolder{
		ImageView iv_icon;
		TextView tv_name;
	}
	//��д��ĸ�Ĳ���
	@Override
	public View getGroupView(int group, boolean arg1, View contentView,
			ViewGroup arg3) {
		// TODO Auto-generated method stub
		//��ȡ��д��ĸ
		String name = getGroupName(group);
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(R.layout.list_group_item, null);
			contentView.setClickable(true);
		}
		TextView tv_name = (TextView) contentView.findViewById(R.id.tv_group_name);
		
		tv_name.setText(name);
		return contentView;
	}

}
