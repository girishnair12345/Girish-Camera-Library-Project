package com.camera.library;

import java.io.FileOutputStream;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MyPreview extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder mHolder;
	public Camera mCamera;
	private Context context;
	private final String LOG_TAG = "Camera Lib : MyPreview";
	public MyPreview(Context context){
		super(context);
		this.context = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		// Added for devices before 3.0 else it crashes 
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder){
		try{
		if(mCamera == null)
			mCamera = android.hardware.Camera.open();
		}catch(Exception ex){
			Toast.makeText(getContext(), "Unable To Detect Camera" , 50).show();
			System.exit(0);
		}
		
		try{
			mCamera.setPreviewDisplay(mHolder);
			
			mCamera.setPreviewCallback(new PreviewCallback() {
				
				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					FileOutputStream fos = null;
					try{
						fos = new FileOutputStream(context.getCacheDir()+"/temp.jpg");
						fos.write(data);
						fos.close();
					}catch(Exception ex){
						Toast.makeText(context, ex.getMessage(), 35).show();
						Log.e(LOG_TAG, ex.getMessage());
						ex.printStackTrace();
						return;
					}
					MyPreview.this.invalidate();
				}
			});
			
		}catch (Exception e) {
			Toast.makeText(context, e.getMessage(), 35).show();
			Log.e(LOG_TAG, e.getMessage()+"");
			e.printStackTrace();	
			return;
		}
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder){
		if(mCamera != null) {
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w , int h){
		Camera.Parameters params = mCamera.getParameters();
		mCamera.setParameters(params);
		mCamera.startPreview();
	}
	
	public void clear(){
		if(mCamera != null) {
			mCamera.stopPreview();
			mCamera.setPreviewCallback(null);
			mCamera.release();
			mCamera = null;
		}
	}
	
	public void getParametersInfo(){
		
	}

}
