package girish.cameraLibrary;

import android.app.Activity;
import android.content.Intent;

public class CustomCamera {
	Activity mActivity;
	public CustomCamera(Activity mActivity){
		this.mActivity = mActivity;
	}
	
	public void startCamera(){
		CameraClass.useDefaultCamera = false;
		CamPreview.front_camera = false;
		mActivity.startActivity(new Intent(mActivity,CameraClass.class));
	}
	
	public void startCameraFront(){
		CameraClass.useDefaultCamera = false;
		CamPreview.front_camera = true;
		mActivity.startActivity(new Intent(mActivity,CameraClass.class));
	}
	
	public void sendCameraIntent(){
		CameraClass.useDefaultCamera = true;
		CamPreview.front_camera = false;
		mActivity.startActivity(new Intent(mActivity,CameraClass.class));
	}
	
	public void setPictureTakenListner(OnPictureTaken onPictureTaken){
		CameraClass.setOnPictureTakenListner(onPictureTaken);
	}
}
