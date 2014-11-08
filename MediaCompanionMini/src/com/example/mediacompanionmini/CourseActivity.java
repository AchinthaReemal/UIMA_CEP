package com.example.mediacompanionmini;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CourseActivity extends ListActivity {

	private ProgressDialog pDialog;
	public String token;
	public String jResponse;
	public String downloadID;

	// Hashmap for ListView
	// ArrayList<Map<String, String>> courseList;
	ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

	// String[] course;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course);

		Intent intent = getIntent();
		token = intent.getStringExtra("token");
		jResponse = intent.getStringExtra("response");
		
		

		// Calling async task to get json
		new GetContacts().execute();
		
		// Obtaining ListView Action performed
		ListView lv = getListView();		

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected list items
				downloadID = ((TextView) view.findViewById(R.id.tvshow_id))
						.getText().toString();
				 System.out.println("Show ID:" + downloadID);

				new Thread(new Runnable() {

					@Override
					public void run() {

						try {

							DownloadPostAction.postID(downloadID, token);

//							Intent cont_intent = new Intent(context,
//									CourseContentActivity.class);
//							cont_intent.putExtra("token", token);
//							cont_intent.putExtra("content_response",
//									jcourseContent);
//							startActivity(cont_intent);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}).start();

			}

		});

	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(CourseActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			// Making a request to url and getting response
			String jsonStr = jResponse;

			try {
				String settings[];
				;
				InputStream inStream;
				DataInputStream dataStream;
				String uri = "", response = "";
				String restURL = "http://10.0.2.2/MediaCompanion/SLIM_API/index.php/availabledownloads/"
						+ token;
				URL url = new URL(restURL);

				inStream = url.openStream();
				dataStream = new DataInputStream(inStream);

				response = dataStream.readLine();
				response = "{ available:" + response + "}";

				JSONObject jsonObj = new JSONObject(response);
				JSONArray single = jsonObj.getJSONArray("available");

				for (int i = 0; i < single.length(); i++) {
					HashMap<String, String> downloadsSet = new HashMap<String, String>();
					downloadsSet.put("tvshow", single.getJSONObject(i)
							.getString("tvshow"));
					downloadsSet.put("id",
							single.getJSONObject(i).getString("id"));
					courseList.add(downloadsSet);
				}


				inStream.close();
				dataStream.close();

			} catch (MalformedURLException ex) {

			} catch (IOException ex) {

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(CourseActivity.this,
					courseList, R.layout.list_item, new String[] { "tvshow",
							"id" }, new int[] { R.id.course_name,
							R.id.tvshow_id });

			setListAdapter(adapter);
		}

	}

}
