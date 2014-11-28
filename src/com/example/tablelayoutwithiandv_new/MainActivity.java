package com.example.tablelayoutwithiandv_new;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public static class DisplayHelper {

		// DisplayHelper:
		private static Float scale;

		public static int dpToPixel(int dp, Context context) {
			if (scale == null)
				scale = context.getResources().getDisplayMetrics().density;
			return (int) ((float) dp * scale);
		}

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (imgname.endsWith(".png") || imgname.endsWith(".jpg")
					|| imgname.endsWith(".jpeg") || imgname.endsWith(".gif")) {
				bmp = BitmapFactory.decodeFile(myimagefile.toString());
				iv.setVisibility(View.VISIBLE);
				iv.setImageBitmap(bmp);
			} else if (imgname.endsWith(".mp4")) {
				Bitmap bmp = ThumbnailUtils.createVideoThumbnail(
						myimagefile.toString(),
						MediaStore.Images.Thumbnails.MINI_KIND);
				iv.setVisibility(View.VISIBLE);
				iv.setImageBitmap(bmp);

			}

		}
	};
	ArrayList<String> list_name;
	TableLayout table;

	File[] listoffile, listoflogo;
	int lol, logolol, lol2, diff, k = 0;
	String[] name, logoname;
	String[] link, logolink;
	int tr = 101;
	int tv = 201;
	int btnup_id = 301;
	int iml;
	int btndown_id = 401;
	int btndel_id = 501;
	int spindur_id = 601;
	int text_id = 701;
	int spintext_id = 801;
	int spinno = 0;
	Button tv_name, logobtn;
	String imgname = "";
	String[] Imagename;
	String[] duration;
	String[] difflol = null;
	File imgfile;
	String[] sidetexts = null;
	String[] spinitem = { "5", "10", "15", "20", "25", "30", "60", "90", "120",
			"150", "180", "210", "240", "270", "300", "Disable" };
	ImageView iv, li;
	Bitmap bmp;
	Button btn_up;
	Button btn_down;
	Button btn_delete;
	Button Start, btcomtext, importbtn, titlebtn, savebtn;
	Button upload;
	Spinner Spin_duration;
	Button Text;
	Dialog d, comd, logod;
	EditText et;
	String[] links = null;
	String[] duras = null;
	Button textsave;
	String[] texts = null;
	String comtext = "", title = "";
	CheckBox cb;
	String logofulllink;
	int myid;
	int did;
	int spinmyid;
	int rid[] = null;
	int isspinclick = 0;
	TextView nof, spintext;
	View deleteview;
	ArrayAdapter<String> adapter_state;
	int spinid;
	AlertDialog.Builder alertDialogBuilder;
	SharedPreferences sp, macad, newlol;
	Boolean fs = true;

	String mymacid = "00:22:f4:e7:3d:aa", macaddress = "";
	WifiManager wifiman;
	WifiInfo wifiinfo;
	Boolean wifi;
	View v2;
	TextView b;
	int mydelid;
	File myimagefile;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		macad = PreferenceManager.getDefaultSharedPreferences(this);
		newlol = PreferenceManager.getDefaultSharedPreferences(this);
		fs = sp.getBoolean("Firsttime", true);
		macaddress = macad.getString("MacAddress", "");

		Start = (Button) findViewById(R.id.button1);
		Button Str = (Button) findViewById(R.id.button2);
		logobtn = (Button) findViewById(R.id.btnlogo);
		importbtn = (Button) findViewById(R.id.btn_import);
		titlebtn = (Button) findViewById(R.id.btntitle);
		table = (TableLayout) findViewById(R.id.table);
		list_name = new ArrayList<String>();
		iv = (ImageView) findViewById(R.id.imageView1);
		btcomtext = (Button) findViewById(R.id.btncomtext);

		nof = (TextView) findViewById(R.id.nooffile);

		alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

		titlebtn.setOnClickListener(this);
		importbtn.setOnClickListener(this);
		logobtn.setOnClickListener(this);
		Start.setOnClickListener(this);
		btcomtext.setOnClickListener(this);

		if (fs == true) {
			ConnectivityManager cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			wifi = cn.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
					.isConnectedOrConnecting();
			if (!wifi) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						MainActivity.this);

				// Set title
				alertDialogBuilder.setTitle("Set Your Network Setting");

				// Set dialog message
				alertDialogBuilder
						.setMessage("Choose one Configuration")
						.setCancelable(false)
						.setPositiveButton("Wifi",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, close
										// current activity
										// MainActivity.this.finish();
										Intent wi = new Intent(
												Intent.ACTION_VIEW);
										wi.addCategory(Intent.CATEGORY_LAUNCHER);
										wi.setClassName("com.android.settings",
												"com.android.settings.wifi.WifiSettings");
										wi.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
										try {
											startActivity(wi);
										} catch (ActivityNotFoundException e) {

											// ShowAlert("Music");
										}
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										MainActivity.this.recreate();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			} else if (wifi) {

				wifiman = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				wifiinfo = wifiman.getConnectionInfo();

				macaddress = wifiinfo.getMacAddress();
				SharedPreferences.Editor maceditor = macad.edit();
				maceditor.putString("MacAddress", macaddress);
				maceditor.commit();
				CompareMacid();
			}
		} else if (fs == false) {

			CompareMacid();
		}

		Str.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// TODO Auto-generated method stub
//				TableLayout mytable2 = (TableLayout) findViewById(R.id.table);
//				int count2 = mytable2.getChildCount();
//
//				// Toast.makeText(getApplicationContext(),"count"+
//				// String.valueOf(count2), 1000).show();
//				for (int n = 0; n < count2 - 1; n++) {
//
//					TableRow rv = (TableRow) mytable2.getChildAt((n + 1));
//
//					rid[n] = rv.getId();
//
//					View v2 = rv.getChildAt(0);
//					if (v2 instanceof Button) {
//						Button b = (Button) v2;
//						imgname = b.getText().toString();
//						Imagename[n] = imgname;
//					}
//				}
//				SharedPreferences prefs = PreferenceManager
//						.getDefaultSharedPreferences(MainActivity.this);
//				SharedPreferences.Editor editor = prefs.edit();
//				JSONArray a = new JSONArray();
//				for (int i = 0; i < Imagename.length; i++) {
//					a.put(Imagename[i]);
//				}
//
//				editor.putString("Imagenamesp", a.toString());
//
//				editor.commit();
//				// Toast.makeText(getApplicationContext(),"splength "+
//				// String.valueOf( Imagename.length), 1000).show();
//
//				SharedPreferences.Editor loleditor = newlol.edit();
//				loleditor.putInt("NewLOL", lol);
//				loleditor.commit();
//				if (fs == true) {
//					SharedPreferences durationprefs = PreferenceManager
//							.getDefaultSharedPreferences(MainActivity.this);
//					SharedPreferences.Editor durationeditor = durationprefs
//							.edit();
//					JSONArray da = new JSONArray();
//					for (int i = 0; i < duration.length; i++) {
//						da.put(duration[i]);
//					}
//
//					durationeditor.putString("Durationsp", da.toString());
//
//					durationeditor.commit();
//
//					SharedPreferences.Editor fseditor = sp.edit();
//					fseditor.putBoolean("Firsttime", false);
//					fseditor.commit();
//				}
//
//				SharedPreferences stprefs = PreferenceManager
//						.getDefaultSharedPreferences(MainActivity.this);
//				SharedPreferences.Editor steditor = stprefs.edit();
//				JSONArray st = new JSONArray();
//				for (int i = 0; i < texts.length; i++) {
//					st.put(texts[i]);
//				}
//
//				steditor.putString("Sidetextsp", st.toString());
//
//				steditor.commit();
//
//				Intent startimage = new Intent(MainActivity.this,
//						Startimage.class);
//
//				startActivity(startimage);
				startActivity(new Intent(MainActivity.this, Importimage.class));

			}
		});
		File mylogo = new File(Environment.getExternalStorageDirectory()
				.toString() + "/Logo/");
		mylogo.mkdirs();
		File myfile = new File(Environment.getExternalStorageDirectory()
				.toString() + "/Image/");
		myfile.mkdirs();
		File Dropbox = new File(Environment.getExternalStorageDirectory()
					.toString() + "/Dropbox/");
		 Dropbox.mkdirs();
	
		listoffile = myfile.listFiles();
		lol = listoffile.length;

		lol2 = lol;

		lol = newlol.getInt("NewLOL", lol);
		iml = lol;
		name = new String[lol];
		link = new String[lol];
		links = new String[lol];
		duras = new String[lol];

		for (int i = 0; i < lol; i++) {
			link[i] = (String.valueOf(listoffile[i]));
		}
		for (int n = 0; n < lol; n++) {
			name[n] = listoffile[n].getName();
		}

		diff = lol2 - iml;

		// Toast.makeText(getApplicationContext(),"Diff  "+
		// String.valueOf(diff), 1000).show();
		// difflol = new String[diff];
		if (diff > 0) {

			SharedPreferences.Editor fseditor = sp.edit();
			fseditor.putBoolean("Firsttime", true);
			fseditor.commit();

			SharedPreferences.Editor loleditor = newlol.edit();
			loleditor.putInt("NewLOL", lol2);
			loleditor.commit();
			MainActivity.this.recreate();

			//
			//
			// for(int j=0;j<lol;j++){
			// for(int i=0;i<iml;i++){
			//
			//
			// if(links[i].equalsIgnoreCase(name[j])==true){
			// break;
			// }
			// else if(links[i].equalsIgnoreCase(name[j])==false && i!=iml-1){
			//
			// continue;
			// }
			// else if(links[i].equalsIgnoreCase(name[j])==false && i==iml-1){
			//
			// difflol[k]=name[j];
			// k++;
			// }
			//
			// }
			// }

		}
	

		Imagename = new String[lol];
		duration = new String[lol];
		rid = new int[lol];
		texts = new String[lol];
		sidetexts = new String[lol];
		nof.setText(String.valueOf(lol));
		for (int i = 0; i < lol; i++) {
			texts[i] = "";
		}
		// iml=lol;

		for (int i = 0; i < lol; i++) {
			TableRow table_row = new TableRow(this);
			tv_name = new Button(this);
			btn_up = new Button(this);
			btn_down = new Button(this);
			btn_delete = new Button(this);
			spintext = new TextView(this);
			Spin_duration = new Spinner(this);
			Text = new Button(this);

			TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(
					TableLayout.LayoutParams.WRAP_CONTENT,
					TableLayout.LayoutParams.WRAP_CONTENT);

			int leftMargin = 0;
			int topMargin = 0;
			int rightMargin = 0;
			int bottomMargin = 2;

			tableRowParams.setMargins(leftMargin, topMargin, rightMargin,
					bottomMargin);

			table_row.setLayoutParams(tableRowParams);
			table_row.setBackgroundColor(Color.parseColor("#07000000"));
			;
			table_row.setId(tr + i);
			if (fs == true) {
				tv_name.setText(name[i]);
				for (int i1 = 0; i1 < lol; i1++) {
					duras[i1] = "5";
					duration[i1] = "5";
				}
			}

			else {
				for (int i1 = 0; i1 < lol; i1++) {
					duration[i1] = "5";
				}
				links = new String[lol + 1];
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(this);
				String json = prefs.getString("Imagenamesp", null);
				JSONArray a;
				try {
					a = new JSONArray(json);
					for (int in = 0; in < a.length(); in++) {
						String url = a.optString(in);
						// Toast.makeText(getApplicationContext(),"splength "+
						// String.valueOf( a.length()), 1000).show();
						// Toast.makeText(getApplicationContext(),"lol "+
						// String.valueOf( lol), 1000).show();
						links[in] = url;
					}
					// imgfile = new File(link[i]);
					// if(imgfile.exists()==false){
					// continue;
					// }
					tv_name.setText(links[i]);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			tv_name.setTextColor(Color.WHITE);
			tv_name.setTextSize(15);
			tv_name.setTypeface(Typeface.DEFAULT_BOLD);
			tv_name.setPadding(100, 0, 0, 0);
			tv_name.setGravity(Gravity.LEFT);

			tv_name.setId(tv + i);
			tv_name.setBackgroundDrawable((getResources()
					.getDrawable(R.drawable.custombuttonselect)));

			LayoutParams lp = new TableRow.LayoutParams(8, 50);
			tv_name.setLayoutParams(lp);

			android.widget.TableRow.LayoutParams p = new android.widget.TableRow.LayoutParams(
					1, 50);
			p.leftMargin = DisplayHelper.dpToPixel(8, MainActivity.this);
			btn_up.setLayoutParams(p);

			btn_up.setBackgroundDrawable((getResources()
					.getDrawable(R.drawable.custombuttonup)));
			btn_up.setId(btnup_id + i);

			btn_down.setBackgroundDrawable((getResources()
					.getDrawable(R.drawable.custombuttondown)));
			btn_down.setId(btndown_id + i);

			btn_down.setLayoutParams(p);

			btn_delete.setBackgroundDrawable((getResources()
					.getDrawable(R.drawable.custombuttondel)));
			btn_delete.setId(btndel_id + i);
			android.widget.TableRow.LayoutParams pdel = new android.widget.TableRow.LayoutParams(
					1, 50);
			pdel.leftMargin = DisplayHelper.dpToPixel(20, MainActivity.this);

			btn_delete.setLayoutParams(pdel);

			Spin_duration.setId(spindur_id + i);
			Spin_duration.setGravity(Gravity.RIGHT);
			Spin_duration.setSelection(i);
			Spin_duration.setPrompt("Select Duration in Second");

			adapter_state = new ArrayAdapter<String>(getApplicationContext(),
					R.layout.spinner_item, spinitem);

			adapter_state
					.setDropDownViewResource(R.layout.spinner_dropdown_item);

			Spin_duration.setAdapter(adapter_state);

			android.widget.TableRow.LayoutParams p1 = new android.widget.TableRow.LayoutParams(
					3, 50);
			p1.leftMargin = DisplayHelper.dpToPixel(50, MainActivity.this);

			Spin_duration.setLayoutParams(p1);

			Text.setBackgroundDrawable((getResources()
					.getDrawable(R.drawable.custombuttontext)));
			Text.setId(text_id + i);

			Text.setLayoutParams(p);

			spintext.setId(spintext_id + i);
			if (fs == true) {
				spintext.setText("5");
			} else {

				SharedPreferences durationprefs = PreferenceManager
						.getDefaultSharedPreferences(this);
				String dur = durationprefs.getString("Durationsp", null);

				JSONArray da;
				try {
					da = new JSONArray(dur);
					for (int i1 = 0; i1 < da.length(); i1++) {
						String url = da.optString(i1);
						duras[i1] = url;
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			 spintext.setText(duras[i]);

			spintext.setLayoutParams(p);

			table_row.addView(tv_name);
			table_row.addView(btn_up);
			table_row.addView(btn_down);
			table_row.addView(btn_delete);
			table_row.addView(Spin_duration);

			table_row.addView(spintext);
			table_row.addView(Text);

			table.addView(table_row);
			// for( int ii =0;ii<lol;ii++){
			// Toast.makeText(getApplicationContext(), duras[i], 500).show();
			// }
			if (fs == true) {
				TableLayout mytable2 = (TableLayout) findViewById(R.id.table);
				int count2 = mytable2.getChildCount();

				for (int n = 0; n < count2 - 1; n++) {

					TableRow rv = (TableRow) mytable2.getChildAt((n + 1));

					rid[n] = rv.getId();

					View v2 = rv.getChildAt(0);
					if (v2 instanceof Button) {
						Button b = (Button) v2;
						imgname = b.getText().toString();
						Imagename[n] = imgname;
					}
				}
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(MainActivity.this);
				SharedPreferences.Editor editor = prefs.edit();
				JSONArray a = new JSONArray();
				for (int i1 = 0; i1 < Imagename.length; i1++) {
					a.put(Imagename[i1]);
				}

				editor.putString("Imagenamesp", a.toString());

				editor.commit();

				SharedPreferences durationprefs = PreferenceManager
						.getDefaultSharedPreferences(MainActivity.this);
				SharedPreferences.Editor durationeditor = durationprefs.edit();
				JSONArray da = new JSONArray();
				for (int i1 = 0; i1 < duration.length; i1++) {
					da.put(duration[i1]);

					durationeditor.putString("Durationsp", da.toString());

					durationeditor.commit();

					SharedPreferences.Editor fseditor = sp.edit();
					fseditor.putBoolean("Firsttime", false);
					fseditor.commit();
				}

				SharedPreferences stprefs = PreferenceManager
						.getDefaultSharedPreferences(MainActivity.this);
				SharedPreferences.Editor steditor = stprefs.edit();
				JSONArray st = new JSONArray();
				for (int i1 = 0; i1 < texts.length; i1++) {
					st.put(texts[i1]);
				}

				steditor.putString("Sidetextsp", st.toString());

				steditor.commit();
				MainActivity.this.recreate();
			}
			tv_name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					Runnable r = new Runnable() {
						public void run() {
							TableRow trow = (TableRow) v.getParent();

							View v2 = trow.getChildAt(0);
							if (v2 instanceof Button) {
								Button b = (Button) v2;
								imgname = b.getText().toString();
							}
							myimagefile = new File(Environment
									.getExternalStorageDirectory().toString()
									+ "/Image/" + imgname);

							handler.sendEmptyMessage(0);
						}
					};
					Thread mythread = new Thread(r);
					mythread.start();

				}
			});

			tv_name.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(final View v, boolean arg1) {
					// TODO Auto-generated method stub
					Runnable r = new Runnable() {
						public void run() {
							TableRow trow = (TableRow) v.getParent();

							View v2 = trow.getChildAt(0);
							if (v2 instanceof Button) {
								Button b = (Button) v2;
								imgname = b.getText().toString();
							}
							myimagefile = new File(Environment
									.getExternalStorageDirectory().toString()
									+ "/Image/" + imgname);

							handler.sendEmptyMessage(0);
						}
					};
					Thread mythread = new Thread(r);
					mythread.start();

				}
			});

			btn_up.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					resetcolor();
					TableLayout mytable = (TableLayout) findViewById(R.id.table);

					int count = mytable.getChildCount();

					rid = new int[count - 1];
					int id = v.getId();
					int myid = id - 301;
					if (id != 301) {

						TableRow trI = (TableRow) findViewById(tr + myid);
						TableRow trO = (TableRow) findViewById(tr + myid - 1);

						trO.setBackgroundColor(Color.GRAY);

						View vI = trI.getChildAt(0);
						if (vI instanceof Button) {
							Button b = (Button) vI;

						}

						View vO = trO.getChildAt(0);
						if (vO instanceof Button) {
							Button b = (Button) vO;

						}

						trO.removeView(vO);
						trI.removeView(vI);

						trI.addView(vO, 0);
						trO.addView(vI, 0);

					}
					// for( int ii =0;ii<lol;ii++){
					// Toast.makeText(getApplicationContext(), duration[ii],
					// 500).show();
					// }
				}

			});
			btn_down.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					resetcolor();
					TableLayout mytable = (TableLayout) findViewById(R.id.table);
					int count = mytable.getChildCount();

					int id = v.getId();

					if (id != (401 + count - 2)) {
						int myid = id - 401;

						TableRow trI = (TableRow) findViewById(tr + myid);
						TableRow trO = (TableRow) findViewById(tr + myid + 1);

						trO.setBackgroundColor(Color.GRAY);

						View vI = trI.getChildAt(0);
						if (vI instanceof Button) {
							Button b = (Button) vI;

						}

						View vO = trO.getChildAt(0);
						if (vO instanceof Button) {
							Button b = (Button) vO;

						}

						trO.removeView(vO);
						trI.removeView(vI);

						trI.addView(vO, 0);
						trO.addView(vI, 0);

						TableRow trow = (TableRow) v.getParent();
						View v2 = trow.getChildAt(0);
						if (v2 instanceof Button) {
							Button b = (Button) v2;
							imgname = b.getText().toString();
						}

					}
				}
			});

			btn_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					did = v.getId();
					deleteview = v;

					// Set title
					alertDialogBuilder.setTitle("Delete?????");

					// Set dialog message
					alertDialogBuilder
							.setMessage("Do You Want To Delete This File")
							.setCancelable(true)
							.setPositiveButton("YES",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											lol = lol - 1;

											SharedPreferences.Editor loleditor = newlol
													.edit();
											loleditor.putInt("NewLOL", lol);
											loleditor.commit();

											mydelid = did - 501;
											TableRow trow = (TableRow) deleteview
													.getParent();
											int rowCount = trow.getChildCount();

											View v2 = trow.getChildAt(0);
											if (v2 instanceof Button) {
												Button b = (Button) v2;
												imgname = b.getText()
														.toString();
											}
											File myfile = new File(
													Environment
															.getExternalStorageDirectory()
															.toString()
															+ "/Image/"
															+ imgname);
											myfile.delete();
											View row = (View) deleteview
													.getParent();

											ViewGroup container = ((ViewGroup) row
													.getParent());

											container.removeView(row);
											container.invalidate();
											nof.setText(String.valueOf(lol));

											updatelistsp();

											dialog.cancel();
											MainActivity.this.recreate();

										}

										private void updatelistsp() {
											// TODO Auto-generated method stub
											SharedPreferences.Editor fseditor = sp
													.edit();
											fseditor.putBoolean("Firsttime",
													false);
											fseditor.commit();

											TableLayout mytable2 = (TableLayout) findViewById(R.id.table);
											int count2 = mytable2
													.getChildCount();
											// Toast.makeText(getApplicationContext(),"count"+
											// String.valueOf(count2),
											// 1000).show();
											Imagename = new String[count2 - 1];
											duration = new String[count2 - 1];

											for (int n = 0; n < count2 - 1; n++) {

												TableRow rv = (TableRow) mytable2
														.getChildAt((n + 1));

												rid[n] = rv.getId();

												View v2 = rv.getChildAt(0);
												if (v2 instanceof Button) {
													Button b = (Button) v2;
													imgname = b.getText()
															.toString();
													Imagename[n] = imgname;

												}
											}

											SharedPreferences prefs = PreferenceManager
													.getDefaultSharedPreferences(MainActivity.this);
											SharedPreferences.Editor editor = prefs
													.edit();
											JSONArray a = new JSONArray();
											for (int i = 0; i < Imagename.length; i++) {
												a.put(Imagename[i]);
											}

											editor.putString("Imagenamesp",
													a.toString());

											editor.commit();

											if (fs == true) {
												SharedPreferences durationprefs = PreferenceManager
														.getDefaultSharedPreferences(MainActivity.this);
												SharedPreferences.Editor durationeditor = durationprefs
														.edit();
												JSONArray da = new JSONArray();
												for (int i = 0; i < duration.length; i++) {
													da.put(duration[i]);
												}

												durationeditor.putString(
														"Durationsp",
														da.toString());

												durationeditor.commit();
											} else if (fs == false) {
												// Toast.makeText(getApplicationContext(),
												// String.valueOf(mydelid),
												// 1000).show();
												for (int i = 0; i < duration.length; i++) {
													if (mydelid <= i) {
														duration[i] = duras[i + 1];
													} else {
														duration[i] = duras[i];

													}

												}

												SharedPreferences stprefs = PreferenceManager
														.getDefaultSharedPreferences(MainActivity.this);
												String stext = stprefs
														.getString(
																"Sidetextsp",
																"");

												JSONArray st;
												try {
													st = new JSONArray(stext);
													for (int i = 0; i < st
															.length(); i++) {
														String url = st
																.optString(i);
														sidetexts[i] = url;
													}
												} catch (JSONException e1) {
													// TODO Auto-generated catch
													// block
													e1.printStackTrace();
												}
												texts = new String[count2 - 1];
												for (int i = 0; i < texts.length; i++) {
													if (mydelid <= i) {
														texts[i] = sidetexts[i + 1];
													} else {
														texts[i] = sidetexts[i];

													}

												}

												SharedPreferences.Editor steditor = stprefs
														.edit();
												JSONArray st1 = new JSONArray();
												for (int i1 = 0; i1 < texts.length; i1++) {
													st1.put(texts[i1]);
												}

												steditor.putString(
														"Sidetextsp",
														st1.toString());

												steditor.commit();
												SharedPreferences durationprefs = PreferenceManager
														.getDefaultSharedPreferences(MainActivity.this);
												SharedPreferences.Editor durationeditor = durationprefs
														.edit();
												JSONArray da = new JSONArray();
												for (int i = 0; i < duration.length; i++) {
													da.put(duration[i]);
												}

												durationeditor.putString(
														"Durationsp",
														da.toString());

												durationeditor.commit();
											}

										}

									})
							.setNegativeButton("NO",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();

				}
			});

			Spin_duration
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View v, int itemposition, long arg3) {
							// TODO Auto-generated method stub
							TableRow trow = (TableRow) parent.getParent();
							// for (int i1 = 0; i1 < lol; i1++) {
							//
							// duration[i1] = "5";
							// }
							spinno++;
							if (spinno >= lol + 1) {
								SharedPreferences durationprefs = PreferenceManager
										.getDefaultSharedPreferences(MainActivity.this);
								String dur = durationprefs.getString(
										"Durationsp", null);

								JSONArray da;
								try {
									da = new JSONArray(dur);
									for (int i1 = 0; i1 < da.length(); i1++) {
										String url = da.optString(i1);
										duras[i1] = url;
									}
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								String item = parent.getItemAtPosition(
										itemposition).toString();
								spinmyid = parent.getId() - 601;
								for (int i = 0; i < lol; i++) {
									duration[i] = duras[i];
									// Toast.makeText(getApplicationContext(),"Before"+i+" "+
									// String.valueOf(duration[i]), 100).show();
								}
								duration[spinmyid] = item;
								// SharedPreferences durationprefs =
								// PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
								SharedPreferences.Editor durationeditor = durationprefs
										.edit();
								JSONArray da1 = new JSONArray();
								for (int i1 = 0; i1 < duration.length; i1++) {
									da1.put(duration[i1]);
								}

								durationeditor.putString("Durationsp",
										da1.toString());

								durationeditor.commit();
							}

							//
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}

					});

			Text.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int id = v.getId();

					myid = id - 701;// for array index

					try {
						d = new Dialog(MainActivity.this);
						d.setContentView(R.layout.dialog);

						et = (EditText) d.findViewById(R.id.editText1);
						textsave = (Button) d.findViewById(R.id.button1);
						et.setText(texts[myid]);
						d.setTitle("Enter Text Here");
						d.show();
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), e.toString(),
								Toast.LENGTH_SHORT).show();
					}
					textsave.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String mytext = et.getText().toString();
							texts[myid] = mytext;

							d.cancel();
						}
					});
				}
			});

		}

	}

	private String[] combine(String[] a, String[] b) {
		// TODO Auto-generated method stub
		int length = a.length + b.length;
		String[] result = new String[length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.setup) {
			Intent nt;
			PackageManager manager = getPackageManager();
			try {
				nt = manager.getLaunchIntentForPackage("com.wizbox.settings");
				if (nt == null)
					throw new PackageManager.NameNotFoundException();
				nt.addCategory(Intent.CATEGORY_LAUNCHER);
				startActivity(nt);
			} catch (Exception e) {

				Toast.makeText(getApplicationContext(),
						"Setting app not install", Toast.LENGTH_SHORT).show();
			}

		}
		return super.onOptionsItemSelected(item);
	}

	protected void resetcolor() {
		// TODO Auto-generated method stub
		TableLayout mytable = (TableLayout) findViewById(R.id.table);

		int count = mytable.getChildCount();
		for (int i = 0; i < count; i++) {
			View v = mytable.getChildAt(i);
			v.setBackgroundColor(Color.parseColor("#07000000"));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.btntitle:
			comd = new Dialog(MainActivity.this);
			comd.setContentView(R.layout.dialog);

			et = (EditText) comd.findViewById(R.id.editText1);

			textsave = (Button) comd.findViewById(R.id.button1);
			SharedPreferences titleprefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			title = titleprefs.getString("Titlesp", "");
			et.setText(title);
			comd.setTitle("Enter Text Here");
			comd.show();
			textsave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String mytext = et.getText().toString();
					title = mytext;
					SharedPreferences titleprefs = PreferenceManager
							.getDefaultSharedPreferences(MainActivity.this);
					SharedPreferences.Editor titleeditor = titleprefs.edit();
					titleeditor.putString("Titlesp", title);
					titleeditor.commit();
					comd.cancel();
				}
			});

			break;

		case R.id.btn_import:

			startActivity(new Intent(MainActivity.this, Importimage.class));
			break;

		case R.id.btnlogo:

			logod = new Dialog(MainActivity.this);
			logod.setContentView(R.layout.logodialog);

			ListView ll = (ListView) logod.findViewById(R.id.logolist);
			li = (ImageView) logod.findViewById(R.id.logoimage);
			upload = (Button) logod.findViewById(R.id.btnuploadlogo);
			File logofile = new File(Environment.getExternalStorageDirectory()
					.toString() + "/Logo/");
			logofile.mkdirs();
			listoflogo = logofile.listFiles();
			logolol = listoflogo.length;
			logoname = new String[logolol];
			logolink = new String[logolol];
			for (int i = 0; i < logolol; i++) {
				logolink[i] = (String.valueOf(listoflogo[i]));
			}
			for (int n = 0; n < logolol; n++) {
				logoname[n] = listoflogo[n].getName();
			}
			ArrayAdapter<String> myadapter = new ArrayAdapter<String>(
					MainActivity.this, android.R.layout.simple_list_item_1,
					logoname);
			ll.setAdapter(myadapter);
			logod.setTitle(Html
					.fromHtml("<font color='#FFFFFF' ><big>Choose Logo</big></font>"));
			// logod.setTitle("Choose Logo");
			logod.show();
			upload.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(MainActivity.this,
							ImportLogo.class));
				}
			});
			ll.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					// TODO Auto-generated method stub
					Bitmap bmp = BitmapFactory.decodeFile(logolink[position]);
					li.setVisibility(View.VISIBLE);
					li.setImageBitmap(bmp);

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

			ll.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					// TODO Auto-generated method stub
					Bitmap bmp = BitmapFactory.decodeFile(logolink[position]);
					li.setVisibility(View.VISIBLE);
					li.setImageBitmap(bmp);

					// Set title
					alertDialogBuilder.setTitle("Logo ????");

					// Set dialog message
					alertDialogBuilder
							.setMessage("Do You Want To Make This File Logo")
							.setCancelable(true)
							.setPositiveButton("YES",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

											logofulllink = logolink[position];

											SharedPreferences logoprefs = PreferenceManager
													.getDefaultSharedPreferences(MainActivity.this);
											SharedPreferences.Editor logoeditor = logoprefs
													.edit();
											logoeditor.putString("Logosp",
													logofulllink);
											logoeditor.commit();

											dialog.cancel();
											logod.cancel();

										}
									})
							.setNegativeButton("NO",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											dialog.cancel();
											// logod.cancel();
										}
									});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();

				}
			});
			break;

		case R.id.button1:
			TableLayout mytable2 = (TableLayout) findViewById(R.id.table);
			int count2 = mytable2.getChildCount();

			// Toast.makeText(getApplicationContext(),"count"+
			// String.valueOf(count2), 1000).show();
			for (int n = 0; n < count2 - 1; n++) {

				TableRow rv = (TableRow) mytable2.getChildAt((n + 1));

				rid[n] = rv.getId();

				View v2 = rv.getChildAt(0);
				if (v2 instanceof Button) {
					Button b = (Button) v2;
					imgname = b.getText().toString();
					Imagename[n] = imgname;
				}
			}
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(MainActivity.this);
			SharedPreferences.Editor editor = prefs.edit();
			JSONArray a = new JSONArray();
			for (int i = 0; i < Imagename.length; i++) {
				a.put(Imagename[i]);
			}

			editor.putString("Imagenamesp", a.toString());

			editor.commit();
			// Toast.makeText(getApplicationContext(),"splength "+
			// String.valueOf( Imagename.length), 1000).show();

			SharedPreferences.Editor loleditor = newlol.edit();
			loleditor.putInt("NewLOL", lol);
			loleditor.commit();
			if (fs == true) {
				SharedPreferences durationprefs = PreferenceManager
						.getDefaultSharedPreferences(MainActivity.this);
				SharedPreferences.Editor durationeditor = durationprefs.edit();
				JSONArray da = new JSONArray();
				for (int i = 0; i < duration.length; i++) {
					da.put(duration[i]);
				}

				durationeditor.putString("Durationsp", da.toString());

				durationeditor.commit();

				SharedPreferences.Editor fseditor = sp.edit();
				fseditor.putBoolean("Firsttime", false);
				fseditor.commit();
			}

			SharedPreferences stprefs = PreferenceManager
					.getDefaultSharedPreferences(MainActivity.this);
			SharedPreferences.Editor steditor = stprefs.edit();
			JSONArray st = new JSONArray();
			for (int i = 0; i < texts.length; i++) {
				st.put(texts[i]);
			}

			steditor.putString("Sidetextsp", st.toString());

			steditor.commit();

			Intent startimage = new Intent(MainActivity.this, Startimage.class);

			startActivity(startimage);

			break;

		case R.id.btncomtext:
			comd = new Dialog(MainActivity.this);
			comd.setContentView(R.layout.dialog);

			et = (EditText) comd.findViewById(R.id.editText1);

			textsave = (Button) comd.findViewById(R.id.button1);
			SharedPreferences comtextprefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			comtext = comtextprefs.getString("Comtextsp", "");

			et.setText(comtext);
			comd.setTitle("Enter Text Here");
			comd.show();
			textsave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String mytext = et.getText().toString();
					comtext = mytext;
					SharedPreferences comtextprefs = PreferenceManager
							.getDefaultSharedPreferences(MainActivity.this);
					SharedPreferences.Editor comtexteditor = comtextprefs
							.edit();
					comtexteditor.putString("Comtextsp", comtext);
					comtexteditor.commit();
					comd.cancel();
				}
			});

			break;

		}

	}

	private void CompareMacid() {
		// TODO Auto-generated method stub
		if (macaddress.equalsIgnoreCase(mymacid)) {
			Toast.makeText(getApplicationContext(), "Macid Matched",
					Toast.LENGTH_LONG).show();
		} else {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					MainActivity.this);

			// Set title

			alertDialogBuilder.setTitle("Unregisterd Device...");

			// Set dialog message
			alertDialogBuilder
					.setMessage("Do You Want To Register Your Device")
					.setCancelable(false)
					.setPositiveButton("Register",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// if this button is clicked, close
									// current activity
									// MainActivity.this.finish();
									dialog.cancel();
									// MainActivity.this.finish();
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									MainActivity.this.finish();
								}
							});

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
			// MainActivity.this.finish();

		}
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		MainActivity.this.recreate();
		super.onRestart();
	}

}
