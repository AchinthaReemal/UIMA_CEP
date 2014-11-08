package com.example.mediacompanionmini;

import java.net.MalformedURLException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeActivity extends Activity {

	private Handler handler;
	private ProgressDialog dialog;
	final Context context = this;
	String token;
	String jsonResponse;
	int usrID;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// main thread of the program
		handler = new Handler();

		setContentView(R.layout.activity_home);

		Intent intent = getIntent();
		token = intent.getStringExtra("token");
		((TextView) findViewById(R.id.tvMessage)).setText(token);

		
		Button btnEnrolledCourse = (Button) findViewById(R.id.btnEnrolledCorses);

		
		// Button to view available downloads
		btnEnrolledCourse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						handler.post(new Runnable() {

							@Override
							public void run() {
								dialog = ProgressDialog.show(HomeActivity.this,
										"Loading", "Please wait loading...");

							}
						});

						try {
							String jsonString = JsonParserAction.getJSON(token);
							System.out.println(jsonString);
							 Intent intent = new Intent(context,
							 CourseActivity.class);
							 intent.putExtra("token", token);
							 intent.putExtra("response", jsonString);
							 startActivity(intent);

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
}
