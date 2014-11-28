package com.example.tablelayoutwithiandv_new;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class ImportLogo extends Activity implements OnFocusChangeListener {
	String[] path;
	String[] imagename;
	String[] path2;
	String[] imagename2;
	private File root;
	private ArrayList<File> fileList = new ArrayList<File>();
	private ArrayList<File> fileList2 = new ArrayList<File>();

	private LinearLayout view;

	ListView lv;
	ImageView iv;
	Button usbbtn, sdcardbtn;

	ArrayAdapter<String> myadapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.importimage);

		// view = (LinearLayout) findViewById(R.id.view);
		lv = (ListView) findViewById(R.id.listView1);
		iv = (ImageView) findViewById(R.id.importimage);
		usbbtn = (Button) findViewById(R.id.btnusb);
		sdcardbtn = (Button) findViewById(R.id.btnsdcard);
		lv.setVisibility(View.INVISIBLE);

		// usbbtn.setOnClickListener(this);
		usbbtn.setOnFocusChangeListener(this);

		// sdcardbtn.setOnClickListener(this);
		sdcardbtn.setOnFocusChangeListener(this);

	}

	@Override
	public void onFocusChange(View v, boolean arg1) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnusb:

			root = new File("/mnt/usb_storage/");
			fileList.clear();
			// root = new File("/mnt/external_sd/");
			if (root.exists() == false) {
				Toast.makeText(getApplicationContext(),
						"USB DEVICE NOT CONNECTED", Toast.LENGTH_LONG).show();
				ImportLogo.this.finish();
			}
			getfile(root);
			path = new String[fileList.size()];
			imagename = new String[fileList.size()];

			for (int i = 0; i < fileList.size(); i++) {

				path[i] = fileList.get(i).getAbsolutePath();
				imagename[i] = fileList.get(i).getName();

			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, imagename);
			lv.setVisibility(View.VISIBLE);
			lv.setAdapter(adapter);
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
							ImportLogo.this);

					// Set title

					// Set dialog message
					alertDialogBuilder
							.setMessage("Are You Sure To Copy This File")
							.setCancelable(true)
							.setPositiveButton("YES",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											Runnable runnable = new Runnable() {
												public void run() {
													try {

														File f1 = new File(
																path[position]);// source
																				// file
														File f2 = new File(
																"sdcard/Logo/"
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
			root = new File("/mnt/external_sd/");
			fileList.clear();
			if (root.exists() == false) {
				Toast.makeText(getApplicationContext(), "SdCard NOT CONNECTED",
						Toast.LENGTH_LONG).show();
				ImportLogo.this.finish();
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
							ImportLogo.this);

					// Set title

					// Set dialog message
					alertDialogBuilder
							.setMessage("Are You Sure To Copy This File")
							.setCancelable(true)
							.setPositiveButton("YES",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											Runnable runnable = new Runnable() {
												public void run() {
													try {

														File f1 = new File(
																path2[position]);// source
																					// file
														File f2 = new File(
																"sdcard/Logo/"
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

}
