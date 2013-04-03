package com.lavaspark.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lavaspark.ssf4.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PopWindowListAdapter extends BaseAdapter 
{ 
  private Context _ct;
  private String[] _items;
  private int[] _icons;
  private ArrayList<String> _moveList;
  public PopWindowListAdapter(Context ct,ArrayList<String> moveList) 
  {
    _ct=ct;
    _moveList=moveList;
  }

 
  public int getCount()
  {
    return _moveList.size();
  }

  
  public Object getItem(int arg0)
  {
    return arg0;
  }

 
  public long getItemId(int position)
  {
    return position;
  }

 
  public View getView(int position, View convertView, ViewGroup parent)
  {
    LayoutInflater factory = LayoutInflater.from(_ct);
    View v = (View) factory.inflate(R.layout.pop_window_list, null);//绑定自定义的layout
    if (position %2 != 0) {
		
	}
    TextView tv = (TextView) v.findViewById(R.id.textView1);
    tv.setText(_moveList.get(position));
    return v;
  } 
} 

