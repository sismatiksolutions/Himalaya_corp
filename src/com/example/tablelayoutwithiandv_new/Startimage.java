package com.example.tablelayoutwithiandv_new;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class Startimage extends Activity {
	Button b1;
	ImageView iv, iv1, ivv;

	Handler handler1;
	Runnable r1;

	TextView sidetext,titletext;
	int i = 0;
	File[] listoffile;
	int lol;
	String mytext,title="";
	String[] links = null;
	String[] duration = null;
	String texts = "";
	String logo = "";
	String[] sidetexts = null;
	long delay;
	LinearLayout ll;
	VideoView vv;
	ScrollTextView marqueeText;

	@SuppressLint("SdCardPath")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hybrid);
		
		File myfile = new File(Environment.getExternalStorageDirectory()
				.toString() + "/Image/");

		listoffile = myfile.listFiles();
		lol = listoffile.length;
		
		links = new String[lol];
		duration = new String[lol];
         sidetexts = new String[lol];
         
         
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    String json = prefs.getString("Imagenamesp", null);
	    JSONArray a;
		try {
			a = new JSONArray(json);
			for (int i = 0; i < a.length(); i++) {
	            String url = a.optString(i);
	            links[i]=url;}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  SharedPreferences durationprefs = PreferenceManager.getDefaultSharedPreferences(this);
		  String dur = durationprefs.getString("Durationsp", null);
		  
		  JSONArray da;
			try {
				da = new JSONArray(dur);
				for (int i = 0; i < da.length(); i++) {
		            String url = da.optString(i);
		            duration[i]=url;}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			SharedPreferences stprefs = PreferenceManager.getDefaultSharedPreferences(this);
			  String stext = stprefs.getString("Sidetextsp","");
			  
			  JSONArray st;
				try {
					st = new JSONArray(stext);
					for (int i = 0; i < st.length(); i++) {
			            String url = st.optString(i);
			            sidetexts[i]=url;}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 SharedPreferences logoprefs = PreferenceManager.getDefaultSharedPreferences(this);
			  logo = logoprefs.getString("Logosp", null);
			  
			  SharedPreferences titleprefs = PreferenceManager.getDefaultSharedPreferences(this);
			  title = titleprefs.getString("Titlesp","");
			  
			  SharedPreferences comtextprefs = PreferenceManager.getDefaultSharedPreferences(this);
			  texts = comtextprefs.getString("Comtextsp","");
		
		//sidetexts = getIntent().getExtras().getStringArray("SideText");
		ll = (LinearLayout) findViewById(R.id.myll);

		

		handler1 = new Handler();
		// Log.w("creating handler", ":" + handler1);
		r1 = new Runnable() {

			public void run() {

				if (i == lol) {

					i = 0;

				}

				try {

					if (links[i].endsWith(".png") || links[i].endsWith(".jpg")
							|| links[i].endsWith(".jpeg")
							|| links[i].endsWith(".gif")) {
						final Animation b1 = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.fade);
						b1.reset();
						ll.removeAllViews();
						LayoutInflater inflater = getLayoutInflater();
						View myview = inflater.inflate(R.layout.startimage,
								null);
						iv1 = (ImageView) myview.findViewById(R.id.imagelogo);
						iv = (ImageView) myview.findViewById(R.id.imageView1);
						titletext = (TextView) myview.findViewById(R.id.titletext);
						titletext.setVisibility(View.INVISIBLE);
						if (!title.equalsIgnoreCase("")){
							titletext.setVisibility(View.VISIBLE);
							titletext.setText(title);
						}
						
						marqueeText = (ScrollTextView) myview
								.findViewById(R.id.scrolltext);
						sidetext = (TextView) myview
								.findViewById(R.id.sidetext);
						final Animation logoanim = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.rotate);
						
						Bitmap bmp1 = BitmapFactory.decodeFile(logo);
						iv1.setImageBitmap(bmp1);
						iv1.startAnimation(logoanim);
						
						if (!texts.equalsIgnoreCase("")) {
							marqueeText.setVisibility(View.VISIBLE);
							marqueeText.setText(texts);
							marqueeText.setSelected(true);
							marqueeText.startScroll();
							marqueeText
									.setBackgroundResource(R.color.app_bg_new);
							// marqueeText.setSingleLine();
							// marqueeText.setEllipsize(TruncateAt.MARQUEE);
							// marqueeText.setHorizontallyScrolling(true);
						} else if (texts.equalsIgnoreCase("")) {
							marqueeText.setVisibility(View.INVISIBLE);
						}
						final Animation b2 = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.bounce);
						if (!sidetexts[i].equalsIgnoreCase("")) {
							sidetext.setVisibility(View.VISIBLE);
							sidetext.setText(sidetexts[i]);
							sidetext.startAnimation(b2);

						} else if (sidetexts[i].equalsIgnoreCase("")) {
							sidetext.setVisibility(View.INVISIBLE);
						}

						ll.addView(myview);

						Bitmap bmp = BitmapFactory.decodeFile((Environment
								.getExternalStorageDirectory().toString()
								+ "/Image/" + links[i]));
						if (duration[i].equalsIgnoreCase("Disable")) {
							delay = 0;
						} else {
							delay = Long.parseLong(duration[i]);
						}
						iv.setImageBitmap(bmp);
						iv.startAnimation(b1);

					}

					else if (links[i].endsWith(".mp4")) {

						// Toast.makeText(getApplicationContext(), "videos",
						// Toast.LENGTH_SHORT).show();
						ll.removeAllViews();

						LayoutInflater inflater = getLayoutInflater();
						View myview2 = inflater.inflate(R.layout.video, null);
						ivv = (ImageView) myview2.findViewById(R.id.imagelogo);
						vv = (VideoView) myview2.findViewById(R.id.videoview);
						
						marqueeText = (ScrollTextView) myview2
								.findViewById(R.id.scrolltext);
						marqueeText.setVisibility(View.INVISIBLE);
						final Animation logoanim = AnimationUtils.loadAnimation(
								getApplicationContext(), R.anim.rotate);
						
						Bitmap bmp1 = BitmapFactory.decodeFile(logo);
						ivv.setImageBitmap(bmp1);
						ivv.startAnimation(logoanim);
						
						if (!texts.equalsIgnoreCase("")) {
							marqueeText.setSelected(true);
							marqueeText.setVisibility(View.VISIBLE);
							marqueeText
									.setBackgroundResource(R.color.app_bg_new);
							// marqueeText.setSingleLine();
							// marqueeText.setEllipsize(TruncateAt.MARQUEE);
							// marqueeText.setHorizontallyScrolling(true);
							marqueeText.setText(texts);
							marqueeText.startScroll();
						}

						ll.addView(myview2);

						vv.setVideoPath(Environment
								.getExternalStorageDirectory().toString()
								+ "/Image/" + links[i]);
						// File file = new
						// File(Environment.getExternalStorageDirectory()
						// .toString() + "/Image/"+links[i]);
						// //file.get
						MediaMetadataRetriever retriever = new MediaMetadataRetriever();
						retriever.setDataSource(Environment
								.getExternalStorageDirectory().toString()
								+ "/Image/" + links[i]);
						String time = retriever
								.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
						long timeInmillisec = Long.parseLong(time);

						vv.start();
						if (duration[i].equalsIgnoreCase("Disable")) {
							delay = 0;
						} else {
							delay = timeInmillisec / 1000;
						}
						vv.setOnCompletionListener(new OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {
								// TODO Auto-generated method stub
								mp.stop();
							}
						});

					}

				} catch (Exception e) {

				}

				handler1.postDelayed(this, (delay * 1000));
				Log.w("entering to postdelay", "postdelay");
				i++;

				Log.w("i count", ":" + i);
			}

		};

		handler1.postDelayed(r1, (delay * 1000));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Startimage.this.finish();
		startActivity(new Intent(Startimage.this, MainActivity.class));
	}

}
