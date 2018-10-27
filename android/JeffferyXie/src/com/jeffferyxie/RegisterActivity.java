package com.jeffferyxie;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


public class RegisterActivity extends Activity implements OnClickListener{
	private EditText mPhoneNumberEditText;
    private EditText mPassWordEditText, mNameWordEditText;
    private Button mRegisterButton;
    private RadioButton admin, user;
    private String originAddress = "http://47.97.193.151/parkingsystem2/ManagePowerServlet";
    
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = "";

            if ("OK".equals(msg.obj.toString())){
                result = "success";
            }else if ("Wrong".equals(msg.obj.toString())){
                result = "fail";
            }else {
            	String data = msg.obj.toString();
            	try {
					JSONObject jsonObject = new JSONObject(data);
					String code = jsonObject.getString("code");
					if (code.equals("1")) {
						result = jsonObject.getString("data");
						Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						intent.setClass(RegisterActivity.this, MainActivity.class);  //跳转
						startActivity(intent);  //启动
						finish();
					}else {
						result = jsonObject.getString("data");
						Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
           
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
        initEvent();
    }
    
    private void initView() {
        mPhoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);
        mPassWordEditText = (EditText) findViewById(R.id.passwordEditText);
        mNameWordEditText = (EditText) findViewById(R.id.nameEditText);
        mRegisterButton = (Button) findViewById(R.id.registerButton);
        admin = (RadioButton)findViewById(R.id.admin);
        user = (RadioButton)findViewById(R.id.user);
    }
    
    private void initEvent() {
    	mRegisterButton.setOnClickListener(this);
    }
    
    private boolean isInputValid() {
        //检查用户输入的合法性，这里暂且默认用户输入合法
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
            	register();
                break;
        }
    }
    //登陆
    private void register() {
        //取得用户输入的账号和密码
        if (!isInputValid()){
            return;
        }
        String number = mPhoneNumberEditText.getText().toString().trim();
        String password = mPassWordEditText.getText().toString().trim();
        String name = mNameWordEditText.getText().toString().trim();
        if (number.isEmpty()) {
        	Toast.makeText(RegisterActivity.this, "账号不能为空！", Toast.LENGTH_LONG).show();
        	return;
		}else if (password.isEmpty()) {
			Toast.makeText(RegisterActivity.this, "密码不能为空！", Toast.LENGTH_LONG).show();
			return;
		}else if (name.isEmpty()) {
			Toast.makeText(RegisterActivity.this, "姓名不能为空！", Toast.LENGTH_LONG).show();
			return;
		}else if (!admin.isChecked() && !user.isChecked()) {
			Toast.makeText(RegisterActivity.this, "请选择权限！", Toast.LENGTH_LONG).show();
			return;
		}
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("power", "registered");
        params.put(User.NUMBER, number);
        params.put(User.PASSWORD, password);
        params.put(User.NAME, name);
        if (admin.isChecked()) {
        	params.put("powers", "1");
		}else if (user.isChecked()) {
			params.put("powers", "2");
		}
        
        try {
            String compeletedURL = HttpUtil.getURLWithParams(originAddress, params);
            HttpUtil.sendHttpRequest(compeletedURL, new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Message message = new Message();
                    /*JSONObject jsonObject = new JSONObject();
                    JSONObject data;
                    try {
						 data = jsonObject.getJSONObject(response);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
                    message.obj = response;
                    mHandler.sendMessage(message);
                }

                @Override
                public void onError(Exception e) {
                    Message message = new Message();
                    message.obj = e.toString();
                    mHandler.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}

