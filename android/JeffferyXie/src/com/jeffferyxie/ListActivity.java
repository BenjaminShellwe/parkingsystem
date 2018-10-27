package com.jeffferyxie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ListView listView;  //声明一个ListView对象
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle myBundle = this.getIntent().getExtras();
		String data = myBundle.getString("data");
		setContentView(R.layout.list_activity);
		//存放json数据
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		try {
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject)jsonArray.get(i);
//				Parking parking = new Parking();
//				parking.setId(jsonObject.getInt("id"));
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id",jsonObject.getString("id"));
				map.put("enterPort",jsonObject.getString("cardtype"));
				map.put("exitPort",jsonObject.getString("cartype"));
				map.put("parkName",jsonObject.getString("number"));
				map.put("parkTel",jsonObject.getString("sum"));
				map.put("parkTotal",jsonObject.getString("time"));
				list.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] str = new String[]{"id", "enterPort", "exitPort", "parkName", "parkTel", "parkTotal"};
		int[] rId = new int[]{R.id.park_id, R.id.enter, R.id.exit, R.id.parkname, R.id.phone, R.id.total};
		SimpleAdapter simpleAdapter = new SimpleAdapter(ListActivity.this, list, R.layout.list_item_activity, str, rId);
		listView = (ListView)this.findViewById(R.id.listview);
//		listView.setAdapter(new ArrayAdapter(ListActivity.this, R.layout.list_item_activity,list));
		listView.setAdapter(simpleAdapter);
//		Toast.makeText(ListActivity.this, data, Toast.LENGTH_LONG).show();
	}
}
