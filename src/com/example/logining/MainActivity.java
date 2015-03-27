package com.example.logining;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends Activity implements OnClickListener {

	private EditText username;
	private EditText password;
	private Button btn_login;
	private Button btn_register;
	private String url = "http://130.234.1.67/Test/login.php";
	private String url1 = "http://130.234.1.67/Test/json_array.php";
	private RequestQueue mQueue = null;
	private JSONObject json = new JSONObject();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle bundle = this.getIntent().getExtras();

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		if(bundle != null){
			username.setText(bundle.getString("empNo"));
			password.setText(bundle.getString("pass"));
		}

	}

	public void onClick(View v){
		int id = v.getId();
		switch(id){
		//登陆按钮点击事件
		case R.id.btn_login:
			//封装成json数据，以供传输
			try {
				json.put("UserName", username.getText().toString().trim());
				json.put("PassWord", password.getText().toString().trim());
				Log.e("json1", json.toString());
			} catch (JSONException e) {
				Log.d("json", "解析JSON出错");
				e.printStackTrace();
			}
			
			//创建一个RequestQueue队列
			mQueue = Volley.newRequestQueue(getApplicationContext());
			
			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, url, json,  
					new Response.Listener<JSONObject>() {  
				@Override  
				public void onResponse(JSONObject response) {  
					Log.d("TAG", response.toString());  
					Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
				}  
			}, new Response.ErrorListener() {  
				@Override  
				public void onErrorResponse(VolleyError error) {  
					Log.e("TAG", error.getMessage(), error);  
				}  
			});  

			mQueue.add(jsonObjectRequest);


			break;
			//注册按钮点击事件
		case R.id.btn_register:
			/*Intent intent = new Intent(MainActivity.this, RegisterSMSAcitivity.class);
			startActivity(intent);*/
			break;
		}
	}

	@Override
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
	}

}
