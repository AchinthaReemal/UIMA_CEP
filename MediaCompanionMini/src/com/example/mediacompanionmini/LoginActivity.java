package com.example.mediacompanionmini;

import java.net.MalformedURLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

	public static final String EXTRA_USERNAME = "com.example.android.authenticatordemo.extra.EMAIL";

	// Values of the user name and the password at the logging time
	private String musername;
	private String mpassword;
	public String token="";
	// UI References
	private EditText usernameView;
	private EditText passwordView;
	private View loginFormView;
	private View loginStatusView;
	private TextView loginStatusMessageview;
	private Button btnLogin;

	private Handler handler;
	private ProgressDialog dialog;
	final Context context = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// main thread of the program
		handler = new Handler();

		setContentView(R.layout.activity_login);

		// set up the login form
		musername = getIntent().getStringExtra(EXTRA_USERNAME);
		usernameView = (EditText) findViewById(R.id.username);
		usernameView.setText(musername);

		passwordView = (EditText) findViewById(R.id.password);

		loginFormView = findViewById(R.id.login_form);
		loginStatusView = findViewById(R.id.login_status);
		loginStatusMessageview = (TextView) findViewById(R.id.login_status_message);

		btnLogin = (Button) findViewById(R.id.sign_in_button);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String username = usernameView.getText().toString();
				final String password = passwordView.getText().toString();

				// create a separate thread
				new Thread(new Runnable() {

					@Override
					public void run() {
						handler.post(new Runnable() {

							@Override
							public void run() {
								dialog = ProgressDialog.show(
										LoginActivity.this, "Loading",
										"Please wait loading...");
							}
						});

						try {
							LoginAction loginAction = new LoginAction();
							boolean status = loginAction.login(username,
									password);
							
							if(status==true){

							 //HomeActivity homePage = new HomeActivity();
							 token=username;
							 Intent intent = new Intent(context,
							 HomeActivity.class);
							 intent.putExtra("token", token);
							 startActivity(intent);
								
							}	

						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						handler.post(new Runnable() {

							@Override
							public void run() {
								dialog.dismiss();

							}
						});
					}
				}).start();

			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
