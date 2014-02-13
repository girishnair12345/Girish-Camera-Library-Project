Add these to your manifest
<uses-sdk
    android:minSdkVersion="10"
    android:targetSdkVersion="10" />
<uses-features android:name="android.hardware.camera"/>
<uses-permission android:name="android.permission.CAMERA"/>	
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
	
<activity android:name="com.girish.cameraLibrary.CameraClass"></activity>

Now in your activity implement the com.girish.cameraLibrary.OnPictureTaken interface and Override the pictureTaken(Bitmap bitmap, File file) method which provides your with the bitmap and File 

Create and instance of the CustomCamera
private CustomCamera mCustomCamera;

mCustomCamera = new CustomCamera(MainActivity.this);
mCustomCamera.setPictureTakenListner(this);

//To start the custom back camera use this
mCustomCamera.startCamera();
//To send an intent to applications who are listening to Camera request
mCustomCamera.sendCameraIntent();
//To start the front camera use this
mCustomCamera.startCameraFront();

Then now you can access the image using pictureTaken(Bitmap bitmap, File file) method, so just use

imageview.setImageBitmap(bitmap);

You can mail me at gnairgithub@gmail.com if you wish to want some improvement or need some functionality that needs to be added on
Thank you
:)

