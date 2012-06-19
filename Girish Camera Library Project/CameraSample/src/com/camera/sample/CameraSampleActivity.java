package com.camera.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.camera.library.CameraLibrary;
import com.camera.library.CameraOptions;
/*
 * Get the instance of the CameraOptions class and call the takePicture()  method
 * to get the picture, you can provide your own custom ShutterCallback and 
 * PictureCallback interfaces using the overloaded method of takePicture() 
 * 
 * Use the onActivityResult to do action with the Image
 * If you override the takePicture() method then you should also define the result
 * so that CameraLibrary closes
 * Intent i = new Intent();
 * setResult(getResultCode(), i);
 * finish();
 * 
 * 
 * Place these permission in your Manifest file
 * <uses-features android:name="android.hardware.camera"/>
 * <uses-permission android:name="android.permission.CAMERA"/>	
 * 
 * and also this activity under the application tag
 * <activity android:name="com.camera.library.CameraLibrary"></activity>
 * 
 */
public class CameraSampleActivity extends Activity {
    private ImageView imgPicture ;
    private Button btnCapture;
    private CameraOptions options;
    private TextView txtPath;
    private final int REQUEST_CODE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
        btnCapture = (Button) findViewById(R.id.btncapture);
        txtPath = (TextView) findViewById(R.id.txtPath);
        // Get the instance of this class
        options = CameraOptions.getInstance(this);
        // The takePicture() is mandatory else it will throw Runtime Exception
        // You can also used the overloaded method here to customize it
        options.takePicture();
        // Define the custom request code else default is 1
        options.setRequesCode(REQUEST_CODE);
        
        btnCapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CameraSampleActivity.this,CameraLibrary.class);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
			imgPicture.setImageBitmap(options.getBitmapFile());
			txtPath.setText(options.getFilePath());
		}
	}
    
}