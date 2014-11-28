package com.example.tablelayoutwithiandv_new;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Importimage extends Activity implements OnClickListener {

	String[] path;
	String[] imagename;
	String[] path2,path3;
	String[] imagename2,imagename3;
	private File root;
	private ArrayList<File> fileList = new ArrayList<File>();
	private ArrayList<File> fileList2 = new ArrayList<File>();
	private ArrayList<File> fileList3 = new ArrayList<File>();
	ArrayAdapter<String> adapter;
	private LinearLayout view;
 
	ListView lv;
	ImageView iv;
	Button usbbtn, sdcardbtn,downloaddbtn;

	ArrayAdapter<String> myadapter;

	
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

	private ProgressDialog mProgressDialog,pd;
	int i = 0;
	String link1;
	JSONArray ja;
	DefaultHttpClient httpClient ;
	HttpEntity httpEntity = null;
	HttpResponse httpResponse = null;
	String response, result = null;
	InputStream is = null;
	int ll;
	StringBuilder sb;
	ArrayList<String> Imagenamelist;
	String[] imagenamearray=null;
	int ii=0;
	String imgname;
	
	File myfile;
	File [] listoffile;
int lol;
String[] name,link;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.importimage);
		
		
		pd = new ProgressDialog(Importimage.this);
		
//link1="http://ads1.wizbox.in/mobileapi/getfeedschedule.php";
		
   //     Imagenamelist=new ArrayList<String>();

		// view = (LinearLayout) findViewById(R.id.view);
		lv = (ListView) findViewById(R.id.listView1);
		iv = (ImageView) findViewById(R.id.importimage);
		usbbtn = (Button) findViewById(R.id.btnusb);
		sdcardbtn = (Button) findViewById(R.id.btnsdcard);
		downloaddbtn = (Button) findViewById(R.id.btn_download);
		lv.setVisibility(View.INVISIBLE);

		
		
		usbbtn.setOnClickListener(this);
		sdcardbtn.setOnClickListener(this);
		downloaddbtn.setOnClickListener(this);
		

	}
	
	public ArrayList<File> getfile(File dir) {
		File listFile[] = dir.listFiles();

		if (listFile != null && listFile.length > 0) {
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].isDirectory()) {
					// fileList.add(listFile[i]);
					getfile(listFile[i]);

				} else {
					if (listFile[i].getName().endsWith(".png")
							|| listFile[i].getName().endsWith(".jpg")
							|| listFile[i].getName().endsWith(".jpeg")
							|| listFile[i].getName().endsWith(".gif")
							|| listFile[i].getName().endsWith(".mp4"))

					{
						fileList.add(listFile[i]);
					}
				}

			}
		}
		return fileList;
	}

	public ArrayList<File> getfile2(File dir) {
		File listFile[] = dir.listFiles();

		if (listFile != null && listFile.length > 0) {
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].isDirectory()) {
					// fileList.add(listFile[i]);
					getfile2(listFile[i]);

				} else {
					if (listFile[i].getName().endsWith(".png")
							|| listFile[i].getName().endsWith(".jpg")
							|| listFile[i].getName().endsWith(".jpeg")
							|| listFile[i].getName().endsWith(".gif")
							|| listFile[i].getName().endsWith(".mp4"))

					{
						fileList2.add(listFile[i]);
					}
				}

			}
		}
		return fileList2;
	}
	public ArrayList<File> getfile3(File dir) {
		File listFile[] = dir.listFiles();

		if (listFile != null && listFile.length > 0) {
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].isDirectory()) {
					// fileList.add(listFile[i]);
					getfile3(listFile[i]);

				} else {
					if (listFile[i].getName().endsWith(".png")
							|| listFile[i].getName().endsWith(".jpg")
							|| listFile[i].getName().endsWith(".jpeg")
							|| listFile[i].getName().endsWith(".gif")
							|| listFile[i].getName().endsWith(".JPG")
							|| listFile[i].getName().endsWith(".mp4"))

					{
						fileList3.add(listFile[i]);
					}
				}

			}
		}
		return fileList3;
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnusb:
			pd = new ProgressDialog(Importimage.this);
			pd.setMessage("Please Wait..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(false);
			pd.show();
			 
		   	root = new File("/mnt/usb_storage/");
				fileList.clear();
				if (root.exists() == false) {
					Toast.makeText(getApplicationContext(),
							"USB DEVICE NOT CONNECTED", Toast.LENGTH_LONG).show();
					Importimage.this.finish();
				}
				getfile(root);
				path = new String[fileList.size()];
				imagename = new String[fileList.size()];

				for (int i = 0; i < fileList.size(); i++) {

					path[i] = fileList.get(i).getAbsolutePath();
					imagename[i] = fileList.get(i).getName();

				}
				 adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, imagename);
				lv.setVisibility(View.VISIBLE);

		lv.setAdapter(adapter);
        pd.dismiss();
		lv.setNextFocusRightId(R.id.btnsdcard);
			 	             
		lv.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					lv.setNextFocusRightId(R.id.btnsdcard);

					if (imagename[position].endsWith(".png")
							|| imagename[position].endsWith(".jpg")
							|| imagename[position].endsWith(".jpeg")
							|| imagename[position].endsWith(".gif")) {

						Bitmap bmp = BitmapFactory.decodeFile(path[position]);
						iv.setVisibility(View.VISIBLE);
						iv.setImageBitmap(bmp);
						lv.setNextFocusRightId(R.id.btnsdcard);
					} else if (imagename[position].endsWith(".mp4")) {
						Bitmap bmp = ThumbnailUtils.createVideoThumbnail(
								path[position],
								MediaStore.Images.Thumbnails.MINI_KIND);
						iv.setVisibility(View.VISIBLE);
						iv.setImageBitmap(bmp);
						lv.setNextFocusRightId(R.id.btnsdcard);

					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					// TODO Auto-generated method stub
					lv.setNextFocusRightId(R.id.btnsdcard);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							Importimage.this);

					// Set title

					// Set dialog message
					alertDialogBuilder
							.setMessage("Are You Sure To Copy This File")
							.setCancelable(true)
							.setPositiveButton("YES",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											pd.setMessage("Please Wait..");
											pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
											pd.setCancelable(false);
											pd.show();
											Runnable runnable = new Runnable() {
												public void run() {
													try {
														
														File f1 = new File(
																path[position]);// source
																				// file
														File f2 = new File(
																"sdcard/Image/"
																		+ imagename[position]);// destination
																								// file
														InputStream in = new FileInputStream(
																f1);

														OutputStream out = new FileOutputStream(
																f2);

														byte[] buf = new byte[1024];
														int len;
														while ((len = in
																.read(buf)) > 0) {
															out.write(buf, 0,
																	len);
														}
														in.close();
														out.close();

													} catch (FileNotFoundException ex) {

													} catch (IOException e) {

													}

												}
											};
											Thread mythread = new Thread(
													runnable);
											mythread.start();
											Toast.makeText(
													getApplicationContext(),
													"File Copied",
													Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
											dialog.cancel();

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

			break;

		case R.id.btnsdcard:
			pd = new ProgressDialog(Importimage.this);
			pd.setMessage("Please Wait..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(false);
			pd.show();
			root = new File("/mnt/external_sd/");
			fileList2.clear();
			if (root.exists() == false) {
				Toast.makeText(getApplicationContext(), "SdCard NOT CONNECTED",
						Toast.LENGTH_LONG).show();
				Importimage.this.finish();
			}
			getfile2(root);
			path2 = new String[fileList2.size()];
			imagename2 = new String[fileList2.size()];

			for (int i = 0; i < fileList2.size(); i++) {

				path2[i] = fileList2.get(i).getAbsolutePath();
				imagename2[i] = fileList2.get(i).getName();

			}
			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, imagename2);
			lv.setVisibility(View.VISIBLE);
			lv.setAdapter(adapter1);
			pd.dismiss();
			lv.setNextFocusLeftId(R.id.btnusb);
			

			lv.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					lv.setNextFocusLeftId(R.id.btnusb);

					if (imagename2[position].endsWith(".png")
							|| imagename2[position].endsWith(".jpg")
							|| imagename2[position].endsWith(".jpeg")
							|| imagename2[position].endsWith(".gif")) {

						Bitmap bmp = BitmapFactory.decodeFile(path2[position]);
						iv.setVisibility(View.VISIBLE);
						iv.setImageBitmap(bmp);
						lv.setNextFocusLeftId(R.id.btnusb);

					} else if (imagename2[position].endsWith(".mp4")) {
						Bitmap bmp = ThumbnailUtils.createVideoThumbnail(
								path2[position],
								MediaStore.Images.Thumbnails.MINI_KIND);
						iv.setVisibility(View.VISIBLE);
						iv.setImageBitmap(bmp);
						lv.setNextFocusLeftId(R.id.btnusb);

					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					// TODO Auto-generated method stub
					lv.setNextFocusLeftId(R.id.btnusb);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							Importimage.this);

					// Set title

					// Set dialog message
					alertDialogBuilder
							.setMessage("Are You Sure To Copy This File")
							.setCancelable(true)
							.setPositiveButton("YES",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											pd.setMessage("Please Wait..");
											pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
											pd.setCancelable(false);
											pd.show();
											Runnable runnable = new Runnable() {
												public void run() {
													try {

														File f1 = new File(
																path2[position]);// source
																					// file
														File f2 = new File(
																"sdcard/Image/"
																		+ imagename2[position]);// destination
																								// file
														InputStream in = new FileInputStream(
																f1);

														OutputStream out = new FileOutputStream(
																f2);

														byte[] buf = new byte[1024];
														int len;
														while ((len = in
																.read(buf)) > 0) {
															out.write(buf, 0,
																	len);
														}
														in.close();
														out.close();

													} catch (FileNotFoundException ex) {

													} catch (IOException e) {

													}

												}
											};
											Thread mythread = new Thread(
													runnable);
											mythread.start();
											pd.dismiss();
											Toast.makeText(
													getApplicationContext(),
													"File Copied",
													Toast.LENGTH_SHORT).show();

											dialog.cancel();

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

			break;
		case R.id.btn_download:
			pd = new ProgressDialog(Importimage.this);
			pd.setMessage("Please Wait..");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(false);
			pd.show();
			root = new File(Environment.getExternalStorageDirectory()
							.toString() + "/Dropbox/");
			fileList3.clear();
			if (root.exists() == false) {
				Toast.makeText(getApplicationContext(), "SdCard NOT CONNECTED",
						Toast.LENGTH_LONG).show();
				Importimage.this.finish();
			}
			getfile3(root);
			path3 = new String[fileList3.size()];
			imagename3 = new String[fileList3.size()];
			Toast.makeText(getApplicationContext(),String.valueOf(fileList3.size()),
					Toast.LENGTH_LONG).show();

			for (int i = 0; i < fileList3.size(); i++) {

				path3[i] = fileList3.get(i).getAbsolutePath();
				imagename3[i] = fileList3.get(i).getName();

			}
			ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, imagename3);
			lv.setVisibility(View.VISIBLE);
			lv.setAdapter(adapter3);
			pd.dismiss();
			lv.setNextFocusLeftId(R.id.btnsdcard);
			

			lv.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					lv.setNextFocusLeftId(R.id.btnsdcard);

					if (imagename3[position].endsWith(".png")
							|| imagename3[position].endsWith(".jpg")
							|| imagename3[position].endsWith(".jpeg")
							|| imagename3[position].endsWith(".gif")) {

						Bitmap bmp = BitmapFactory.decodeFile(path3[position]);
						iv.setVisibility(View.VISIBLE);
						iv.setImageBitmap(bmp);
						lv.setNextFocusLeftId(R.id.btnusb);

					} else if (imagename3[position].endsWith(".mp4")) {
						Bitmap bmp = ThumbnailUtils.createVideoThumbnail(
								path3[position],
								MediaStore.Images.Thumbnails.MINI_KIND);
						iv.setVisibility(View.VISIBLE);
						iv.setImageBitmap(bmp);
						lv.setNextFocusLeftId(R.id.btnsdcard );

					}

				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int position, long arg3) {
					// TODO Auto-generated method stub
					lv.setNextFocusLeftId(R.id.btnsdcard);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							Importimage.this);

					// Set title

					// Set dialog message
					alertDialogBuilder
							.setMessage("Are You Sure To Copy This File")
							.setCancelable(true)
							.setPositiveButton("YES",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											pd.setMessage("Please Wait..");
											pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
											pd.setCancelable(false);
											pd.show();
											Runnable runnable = new Runnable() {
												public void run() {
													try {

														File f1 = new File(
																path3[position]);// source
																					// file
														File f2 = new File(
																"sdcard/Image/"
																		+ imagename3[position]);// destination
																								// file
														InputStream in = new FileInputStream(
																f1);

														OutputStream out = new FileOutputStream(
																f2);

														byte[] buf = new byte[1024];
														int len;
														while ((len = in
																.read(buf)) > 0) {
															out.write(buf, 0,
																	len);
														}
														in.close();
														out.close();

													} catch (FileNotFoundException ex) {

													} catch (IOException e) {

													}

												}
											};
											Thread mythread = new Thread(
													runnable);
											mythread.start();
											pd.dismiss();
											Toast.makeText(
													getApplicationContext(),
													"File Copied",
													Toast.LENGTH_SHORT).show();

											dialog.cancel();

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

			break;
		
		}
		
	}

}
