package com.camera.library;

import java.io.File;
import java.io.FileOutputStream;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;

public class CameraOptions {
	private int ActionClick = 0;
	private Bitmap bitmap;
	private static Activity activity;
	private int ResultCode=1;
	private static Context context;
	private final String LOG_TAG = "Camera Lib : CameraOptions";
	private static CameraOptions options; 
	public String PicPath;
	private ShutterCallback mShutterCallback;
	private PictureCallback mRaw_PictureCallback,
							mPostView_PictureCallback,
							mJPEG_PictureCallback;
	
	private CameraOptions(){
		
	}
	
	public static CameraOptions getInstance(Activity _this){
		context = _this.getApplicationContext();
		activity = _this;
		
		if (options == null)
			options = new CameraOptions();
		return options;
	}
	
	//Getters
	public ShutterCallback getShutterCallback(){
		return this.mShutterCallback;
	}
	public PictureCallback getRaw_PictureCallback(){
		return this.mRaw_PictureCallback;
	}
	public PictureCallback getPostView_PictureCallback(){
		return this.mPostView_PictureCallback;
	}
	public PictureCallback getJPEG_PictureCallback(){
		return this.mJPEG_PictureCallback;
	}
	
	//Setters
	public void setShutterCallback(ShutterCallback shutterCallback){
		this.mShutterCallback = shutterCallback;
	}
	public void setRaw_PictureCallback(PictureCallback Raw_PictureCallback){
		this.mRaw_PictureCallback = Raw_PictureCallback;
	}
	public void setPostView_PictureCallback(PictureCallback PostView_PictureCallback){
		this.mPostView_PictureCallback = PostView_PictureCallback;
	}
	public void setJPEG_PictureCallback(PictureCallback JPEG_PictureCallback){
		this.mRaw_PictureCallback = JPEG_PictureCallback;
	}
	
	public void takePicture(){
	
		ShutterCallback shutter = new ShutterCallback(){public void onShutter(){}};
		PictureCallback raw_pic = new PictureCallback(){public void onPictureTaken(byte[] data, Camera camera) {}};
		PictureCallback postview_pic = new PictureCallback(){public void onPictureTaken(byte[] data, Camera camera) {}};
		PictureCallback jpeg_pic = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				FileOutputStream fos ;
				try {
					File f = new File(context.getCacheDir() + "/temp.jpg");
					fos = new FileOutputStream(f);
					fos.write(data);
					fos.close();
					
					bitmap = BitmapFactory.decodeFile(context.getCacheDir() + "/temp.jpg");
					Log.d(LOG_TAG, "Bitmap Created " + bitmap);
					PicPath = f.getAbsolutePath();
					
				} catch (Exception e) {
					Log.e(LOG_TAG, e.getMessage());
					e.printStackTrace();
					throw new RuntimeException(e.getMessage());
				}
				
				Intent i = new Intent();
				activity.setResult(Activity.RESULT_OK, i);
				activity.finish();
			}
		};
		
		this.mShutterCallback = shutter;
		this.mRaw_PictureCallback = raw_pic;
		this.mPostView_PictureCallback = postview_pic;
		this.mJPEG_PictureCallback = jpeg_pic;
		ActionClick = 1;
	}
	
	public void takePicture(ShutterCallback shutterCallback ,
							PictureCallback Raw_PictureCallback,
							PictureCallback JPEG_PictureCallback){
		this.mShutterCallback = shutterCallback;
		this.mRaw_PictureCallback = Raw_PictureCallback;
		this.mJPEG_PictureCallback = JPEG_PictureCallback;
		ActionClick = 2;
	}
	
	public void takePicture(ShutterCallback shutterCallback ,
			PictureCallback Raw_PictureCallback,
			PictureCallback postView_PictureCallback,
			PictureCallback JPEG_PictureCallback){
		this.mShutterCallback = shutterCallback;
		this.mRaw_PictureCallback = Raw_PictureCallback;
		this.mPostView_PictureCallback = postView_PictureCallback;
		this.mJPEG_PictureCallback = JPEG_PictureCallback;
		ActionClick = 3;
	}
	
	public int getActionClick(){
		return ActionClick;
	}
	
	public int getRequesCode(){
		return ResultCode;
	}
	
	public void setRequesCode(int Request_Code){
		ResultCode = Request_Code;
	} 

	public void setBitmapFile(Bitmap bitmap){
		this.bitmap = bitmap;
	}

	public Bitmap getBitmapFile(){
		return bitmap;
	}
	
	public String getFilePath(){
		return PicPath;
	}
	
	public void setFilePath(String path){
		this.PicPath= path;
	}

}
