This project is intended to those people who need to take picture from the camera and use it

1. Create a new project in android , right click on it and Choose Android and now clikc add at the bottom to add the "Library" project to your project
2.Place these permission in your Manifest file
<uses-features android:name="android.hardware.camera"/>
<uses-permission android:name="android.permission.CAMERA"/>	

and also this activity under the application tag
<activity android:name="com.camera.library.CameraLibrary"></activity>

3.Now get the instance of the class
 CameraOptions options = CameraOptions.getInstance(this);
 options.takePicture();
 
4. on the click event of button or in any activity call use to start the camera
	Intent intent = new Intent(this,CameraLibrary.class);
	startActivityForResult(intent, REQUEST_CODE);
	
5. Call the options.getBitmapFile() method to get the bitmap iamge when user takes the picture or use the options.getFilePath() method to get the file path 

The images created are stored in the cache directory of the application