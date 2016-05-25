# This project is no longer maintained

Add these permission to your project

```android
<uses-features android:name="android.hardware.camera"/>
<uses-permission android:name="android.permission.CAMERA"/>	
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

Add this activity
```android
<activity android:name="com.....CameraClass"></activity>
```

Now in your activity implement the **OnPictureTaken** interface and Override the **pictureTaken(Bitmap bitmap, File file)** method which provides your with the bitmap and File 

### Usage
Create and instance of the CustomCamera
```java
private CustomCamera mCustomCamera;

mCustomCamera = new CustomCamera(MainActivity.this);
mCustomCamera.setPictureTakenListner(this);

//To start the custom back camera use this
mCustomCamera.startCamera();
//To send an intent to applications who are listening to Camera request
mCustomCamera.sendCameraIntent();
//To start the front camera use this
mCustomCamera.startCameraFront();
```

Then now you can access the image using pictureTaken(Bitmap bitmap, File file) method, so just use
```java
imageview.setImageBitmap(bitmap);
```


