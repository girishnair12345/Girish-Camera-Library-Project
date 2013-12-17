package com.girish.cameraLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Date;

import com.example.cameratest.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

public class CameraClass extends Activity{
	//Default Camera
	private static final int CAMERA_PIC_REQUEST = 11111; 
	private static OnPictureTaken mOnPictureTaken;
	public static boolean useDefaultCamera = false;
	//Custom Camera
	private CamPreview mPreview;
	private final String LOG_TAG = "Camera Lib : CameraLibrary";
	private RelativeLayout cameraFrame;
	private ImageView imgCapture;
	private RelativeLayout Imagebackground;
	private ImageView imgBackgroundImage;
	private boolean shouldGoBack = true;
	private Bitmap CaptureImage = null;
	private String ImagePath ;
	private ProgressDialog mProgressDialog;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		if(useDefaultCamera){
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		} else {
			ImagePath = getCacheDir() + "/temp.jpg";
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        mProgressDialog = new ProgressDialog(this);
	        mProgressDialog.setTitle("Processing Image");
	        mProgressDialog.setMessage("Please wait...");
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			CreateView();
	        mPreview = new CamPreview(getApplicationContext());
	        cameraFrame.addView(mPreview);

	        imgCapture.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mPreview.mCamera.takePicture(new ShutterCallback() {
						
						@Override
						public void onShutter() {
							
							mProgressDialog.show();
							cameraFrame.setVisibility(View.GONE);
							imgCapture.setVisibility(View.GONE);
							Imagebackground.setVisibility(View.GONE);
						}
					}, null, new PictureCallback() {
						
						@Override
						public void onPictureTaken(byte[] data, Camera camera) {
							
						
							FileOutputStream fos ;
							try {
								
								makeCameraVisible(false);
								File f = new File(ImagePath);
								fos = new FileOutputStream(f);
								fos.write(data);
								fos.close();
								
								CaptureImage = BitmapFactory.decodeFile(new File(ImagePath).getAbsolutePath());
								
								imgBackgroundImage.setImageBitmap(CaptureImage);
								
								mProgressDialog.cancel();
						
							} catch (Exception e) {
								Log.e(LOG_TAG, e.getMessage());
								e.printStackTrace();
								throw new RuntimeException(e.getMessage());
							}
							
						}
					});
				}
			});
		}
	}
	
	public static void setOnPictureTakenListner(OnPictureTaken activity){
		mOnPictureTaken = activity;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    if (requestCode == CAMERA_PIC_REQUEST) {
	    	if(data!=null){
		    	Bitmap bitmap = (Bitmap) data.getExtras().get("data"); 
		    	if(mOnPictureTaken != null)
		    		mOnPictureTaken.pictureTaken(bitmap,new File(getRealPathFromURI(this, data.getData())));
	    	}
	    	shouldGoBack = true;
	    	onBackPressed();
	    }  
	}  
	
	public String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri, proj, null,
					null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	public void CreateView(){
		LayoutParams FILL_PARENT = new LinearLayout.LayoutParams(
          		LinearLayout.LayoutParams.FILL_PARENT,
          		LinearLayout.LayoutParams.FILL_PARENT);
		
		LayoutParams WRAP_CONTENT = new LinearLayout.LayoutParams(
          		LinearLayout.LayoutParams.WRAP_CONTENT,
          		LinearLayout.LayoutParams.WRAP_CONTENT);
		
		LayoutParams BTN_WRAP_CONTENT = new LinearLayout.LayoutParams(
          		LinearLayout.LayoutParams.FILL_PARENT,
          		LinearLayout.LayoutParams.WRAP_CONTENT,0.5f);
		
		LayoutParams WIDTH_FIIL_PARENT = new LinearLayout.LayoutParams(
          		LinearLayout.LayoutParams.FILL_PARENT,
          		LinearLayout.LayoutParams.WRAP_CONTENT);
		
		RelativeLayout mainView = new RelativeLayout(this);
		mainView.setLayoutParams(FILL_PARENT);
		mainView.setBackgroundColor(Color.BLACK);
		
		cameraFrame = new RelativeLayout(this);
		cameraFrame.setLayoutParams(FILL_PARENT);
		cameraFrame.setBackgroundColor(Color.BLACK);
		
		imgCapture = new ImageView(this);
		imgCapture.setLayoutParams(WRAP_CONTENT);
		imgCapture.setImageResource(R.drawable.capture_icon);
		
		Imagebackground = new RelativeLayout(this);
		Imagebackground.setLayoutParams(FILL_PARENT);
		Imagebackground.setVisibility(View.GONE);
		Imagebackground.setBackgroundColor(Color.BLACK);
		Button btnSave = new Button(this);
		btnSave.setLayoutParams(BTN_WRAP_CONTENT);
		btnSave.setText("Save");
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Date date = new Date();
				String Title = new Timestamp(date.getTime()).toString();
				Title = Title.substring(0, Title.indexOf('.'));
				Title = Title.replace('-', '_').replace(':', '_').replace(' ', '_');
				MediaStore.Images.Media.insertImage(getContentResolver(), CaptureImage, Title , "");
				
				if(mOnPictureTaken != null)
					mOnPictureTaken.pictureTaken(CaptureImage, new File(ImagePath));
				CameraClass.this.finish();
				
			}
		});
		
		Button btnCancel = new Button(this);
		btnCancel.setLayoutParams(BTN_WRAP_CONTENT);
		btnCancel.setText("Discard");
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				shouldGoBack = false;
				CameraClass.this.onBackPressed();
			}
		});
		
		LinearLayout ButtonLayout = new LinearLayout(this);
		ButtonLayout.setLayoutParams(WIDTH_FIIL_PARENT);
		ButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
		ButtonLayout.addView(btnSave);
		ButtonLayout.addView(btnCancel);
		
		imgBackgroundImage = new ImageView(this);
		imgBackgroundImage.setLayoutParams(FILL_PARENT);
		Imagebackground.addView(imgBackgroundImage);
		Imagebackground.addView(ButtonLayout);
		
		mainView.addView(cameraFrame);
		mainView.addView(imgCapture);
		mainView.addView(Imagebackground);
		
		
		RelativeLayout.LayoutParams btnSaveLayouParams = (RelativeLayout.LayoutParams) ButtonLayout.getLayoutParams();
		btnSaveLayouParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		ButtonLayout.setLayoutParams(btnSaveLayouParams);
		
		RelativeLayout.LayoutParams imgCaptureLayouParams = (RelativeLayout.LayoutParams) imgCapture.getLayoutParams();
		imgCaptureLayouParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		imgCaptureLayouParams.addRule(RelativeLayout.CENTER_VERTICAL);
		imgCapture.setLayoutParams(imgCaptureLayouParams);
		
		setContentView(mainView);
	}
	
	public void makeCameraVisible(boolean cameraVisible){
		if(cameraVisible){
			shouldGoBack = true;
			cameraFrame.setVisibility(View.VISIBLE);
			imgCapture.setVisibility(View.VISIBLE);
			Imagebackground.setVisibility(View.GONE);
			CaptureImage.recycle();
		}else{
			shouldGoBack = false;
			cameraFrame.setVisibility(View.GONE);
			imgCapture.setVisibility(View.GONE);
			Imagebackground.setVisibility(View.VISIBLE);
		}
		
	}
	
	

	@Override
	public void onBackPressed() {
		if(shouldGoBack){
			super.onBackPressed();
		}else{
			makeCameraVisible(true);
		}
	}
	
}
