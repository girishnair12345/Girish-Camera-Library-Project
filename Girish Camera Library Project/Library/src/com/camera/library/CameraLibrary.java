package com.camera.library;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CameraLibrary extends Activity {
	
	private LinearLayout linearMain;
	private LinearLayout.LayoutParams paramsMain;
	private MyPreview mPreview;
	private final String LOG_TAG = "Camera Lib : CameraLibrary";
	private Camera.Parameters cam_params;
	private CameraOptions camOptions;
	private String Flashmode = "auto";
	private int Flashmodevalue = 0;
	private int zoomMode = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreview = new MyPreview(getApplicationContext());
        camOptions = CameraOptions.getInstance(this);
        if (camOptions.getActionClick()== 0 || camOptions.getActionClick() >3)
        	throw new RuntimeException("Camera Options is not defined");
        CreateMyView();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
	
    private void CreateMyView(){
    	
    	 LayoutParams MATCH_PARENT_WRAP_CONTENT = new LinearLayout.LayoutParams(
          		LinearLayout.LayoutParams.MATCH_PARENT,
          		LinearLayout.LayoutParams.WRAP_CONTENT);
          
          LayoutParams WRAP_CONTENT_WRAP_CONTENT = new LinearLayout.LayoutParams(
          		LinearLayout.LayoutParams.WRAP_CONTENT,
          		LinearLayout.LayoutParams.WRAP_CONTENT);
          
       
          LayoutParams WRAP_CONTENT_WRAP_CONTENT_020 = new LinearLayout.LayoutParams(
          		LinearLayout.LayoutParams.WRAP_CONTENT,
          		LinearLayout.LayoutParams.WRAP_CONTENT,0.20f);
          
    	 linearMain = new LinearLayout(this);
         paramsMain = new LinearLayout.LayoutParams(
         								LinearLayout.LayoutParams.FILL_PARENT,
         								LinearLayout.LayoutParams.FILL_PARENT);
         linearMain.setLayoutParams(paramsMain);
         linearMain.setOrientation(LinearLayout.HORIZONTAL);
         //linearMain.setBackgroundResource(R.drawable.back);
         linearMain.setBackgroundColor(Color.GRAY);
         
         LayoutParams params1 = new LinearLayout.LayoutParams(
         		LinearLayout.LayoutParams.MATCH_PARENT,
         		LinearLayout.LayoutParams.WRAP_CONTENT,1);
         LinearLayout linear1 = new LinearLayout(this);
         linear1.setOrientation(LinearLayout.VERTICAL);
         linear1.setLayoutParams(params1);
         
         FrameLayout frame = new FrameLayout(this);
         FrameLayout.LayoutParams frameparams = new FrameLayout.LayoutParams(
         		LinearLayout.LayoutParams.MATCH_PARENT,
         		LinearLayout.LayoutParams.WRAP_CONTENT,1);
         frame.setLayoutParams(frameparams);
         frame.setPadding(2, 2, 2, 2);
         frame.addView(mPreview);
         linear1.addView(frame);
         
         final LinearLayout linear2 = new LinearLayout(this);
         linear2.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         linear2.setOrientation(LinearLayout.VERTICAL);
         linear2.setVisibility(View.GONE);
         
         final LinearLayout linear3 = new LinearLayout(this);
         linear3.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         linear3.setOrientation(LinearLayout.VERTICAL);
         
         final ScrollView horizontalView = new ScrollView(this);
         horizontalView.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         horizontalView.setVisibility(View.GONE);

  
         ImageView imgCapture = new ImageView(this);
         imgCapture.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT_020);
         imgCapture.setImageResource(R.drawable.capture_icon);
         imgCapture.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				cam_params = mPreview.mCamera.getParameters();
				switch(camOptions.getActionClick()){
				case 1:
					try{
						mPreview.mCamera.takePicture(camOptions.getShutterCallback(),
								camOptions.getRaw_PictureCallback(), 
								camOptions.getJPEG_PictureCallback());
					}catch (Exception e) {
						Log.e(LOG_TAG, e.getMessage());
						e.printStackTrace();
						throw new RuntimeException("Take Picture: " +e.getMessage());
					}
					break;
					
				case 2:
					try{
						mPreview.mCamera.takePicture(camOptions.getShutterCallback(),
								camOptions.getRaw_PictureCallback(), 
								camOptions.getJPEG_PictureCallback());
					}catch (Exception e) {
						Log.e(LOG_TAG, e.getMessage());
						e.printStackTrace();
						throw new RuntimeException("Take Picture: " +e.getMessage());
					}
					break;
					
				case 3:
					try{
						mPreview.mCamera.takePicture(camOptions.getShutterCallback(),
								camOptions.getRaw_PictureCallback(), 
								camOptions.getPostView_PictureCallback(),
								camOptions.getJPEG_PictureCallback());
					}catch (Exception e) {
						Log.e(LOG_TAG, e.getMessage());
						e.printStackTrace();
						throw new RuntimeException("Take Picture: " +e.getMessage());
					}
					break;
					
				default:
					throw new RuntimeException("Take Picture Undefined");
					
				}
			}
		});
         
         ImageView imgEffects = new ImageView(this);
         imgEffects.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT_020);
         imgEffects.setImageResource(R.drawable.effects_icon);
         imgEffects.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cam_params = mPreview.mCamera.getParameters();
				Log.i(LOG_TAG, "btnEffects Clicked");
				cam_params = mPreview.mCamera.getParameters();
				AlphaAnimation aplha1 = new AlphaAnimation(0, 1);
				aplha1.setDuration(1000);
				AlphaAnimation aplha2 = new AlphaAnimation(1, 0);
				aplha2.setDuration(1000);
				
    			linear3.startAnimation(aplha2);
    			linear3.setVisibility(View.GONE);
    			horizontalView.startAnimation(aplha1);
    			horizontalView.setVisibility(View.VISIBLE);
			}
		});
         
    
         final ImageView imgFlash = new ImageView(this);
         imgFlash.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT_020);
         imgFlash.setImageResource(R.drawable.flash_auto_icon);
         imgFlash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cam_params = mPreview.mCamera.getParameters();
				Flashmodevalue ++;
				if(Flashmodevalue > 4)
					Flashmodevalue = 0;
				
				if(Flashmodevalue == 0 ){
					Flashmode = Camera.Parameters.FLASH_MODE_AUTO;
					 imgFlash.setImageResource(R.drawable.flash_auto_icon);
					Toast.makeText(getApplicationContext(), "Flash Mode Auto",25).show();
				}else if(Flashmodevalue == 1 ){
					Flashmode = Camera.Parameters.FLASH_MODE_ON;
					 imgFlash.setImageResource(R.drawable.flash_on_icon);
					Toast.makeText(getApplicationContext(), "Flash Mode ON",25).show();
				}else if(Flashmodevalue == 2 ){
					Flashmode = Camera.Parameters.FLASH_MODE_OFF;
					 imgFlash.setImageResource(R.drawable.flash_off_icon);
					Toast.makeText(getApplicationContext(), "Flash Mode OFF",25).show();
				}else if(Flashmodevalue == 3 ){
					Flashmode = Camera.Parameters.FLASH_MODE_RED_EYE;
					 imgFlash.setImageResource(R.drawable.flash_red_eye);
					Toast.makeText(getApplicationContext(), "Red Eye",25).show();
				}else if(Flashmodevalue == 4 ){
					Flashmode = Camera.Parameters.FLASH_MODE_TORCH;
					 imgFlash.setImageResource(R.drawable.flash_torch);
					Toast.makeText(getApplicationContext(), "Flash Mode Torch",25).show();
				}
				cam_params.setFlashMode(Flashmode);
				mPreview.mCamera.setParameters(cam_params);
			}
		});
         
         final ImageView imgZoom = new ImageView(this);
         imgZoom.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT_020);
         imgZoom.setImageResource(R.drawable.zoom_icon);
         imgZoom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cam_params = mPreview.mCamera.getParameters();
				Log.i(LOG_TAG, "btnZoom Clicked");
				if (cam_params.isZoomSupported()){
					zoomMode ++;
					if(zoomMode >cam_params.getMaxZoom())
						zoomMode =0;
					cam_params.setZoom(zoomMode);
					mPreview.mCamera.setParameters(cam_params);
					Toast.makeText(getApplicationContext(), zoomMode +"x", 25).show();
				
	    		}else{
	    			Toast.makeText(getApplicationContext(), "Zoom Not Supported", 25).show();
	    			imgZoom.setImageResource(R.drawable.zoom_block);	    		}
			}
		});
         
         ImageView imgExit = new ImageView(this);
         imgExit.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT_020);
         imgExit.setImageResource(R.drawable.exit);
         imgExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				CameraLibrary.this.setResult(Activity.RESULT_CANCELED, i);
				CameraLibrary.this.finish();
			}
		});
         
         linear3.addView(imgCapture);
         linear3.addView(imgEffects);
         linear3.addView(imgFlash);
         linear3.addView(imgZoom);
         linear3.addView(imgExit);
         
         //Main Button Animation
         Animation translate = new TranslateAnimation( Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF,500, Animation.RELATIVE_TO_SELF);
         translate.setDuration(1200);
         translate.setInterpolator(new AccelerateDecelerateInterpolator());
         imgCapture.startAnimation(translate);
         
         Animation translate2 = new TranslateAnimation( Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF,1500, Animation.RELATIVE_TO_SELF);
         translate2.setDuration(1300);
         translate2.setInterpolator(new AccelerateDecelerateInterpolator());
         imgEffects.startAnimation(translate2);
         
         Animation translate3 = new TranslateAnimation( Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF,2500, Animation.RELATIVE_TO_SELF);
         translate3.setDuration(1400);
         translate3.setInterpolator(new AccelerateDecelerateInterpolator());
         imgFlash.startAnimation(translate3);
         
         Animation translate4 = new TranslateAnimation( Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, 3500, Animation.RELATIVE_TO_SELF);
         translate4.setDuration(1500);
         translate4.setInterpolator(new AccelerateDecelerateInterpolator());
         imgZoom.startAnimation(translate4);
         
         Animation translate5 = new TranslateAnimation( Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF, 4500, Animation.RELATIVE_TO_SELF);
         translate5.setDuration(1600);
         translate5.setInterpolator(new AccelerateDecelerateInterpolator());
         imgExit.startAnimation(translate5);
         
        
         
         LinearLayout linear4 = new LinearLayout(this);
         linear4.setLayoutParams(MATCH_PARENT_WRAP_CONTENT);
         linear4.setOrientation(LinearLayout.VERTICAL);
         horizontalView.addView(linear4);
         
         ImageView img_Effects_back = new ImageView(this);
         img_Effects_back.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         img_Effects_back.setImageResource(R.drawable.back_icon);
         img_Effects_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				AlphaAnimation aplha1 = new AlphaAnimation(0, 1);
				aplha1.setDuration(1000);
				AlphaAnimation aplha2 = new AlphaAnimation(1, 0);
				aplha2.setDuration(1000);
				
    			linear3.startAnimation(aplha1);
    			linear3.setVisibility(View.VISIBLE);
    			horizontalView.startAnimation(aplha2);
    			horizontalView.setVisibility(View.GONE);
			}
		});
         
         Button btn_Effects_None = new Button(this);
         btn_Effects_None.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_None.setText("None");
         btn_Effects_None.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cam_params.setColorEffect(Camera.Parameters.EFFECT_NONE);
				mPreview.mCamera.setParameters(cam_params);
			}
		});
         
         Button btn_Effects_Aqua = new Button(this);
         btn_Effects_Aqua.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_Aqua.setText("Aqua");
         btn_Effects_Aqua.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				cam_params.setColorEffect(Camera.Parameters.EFFECT_AQUA);
 				mPreview.mCamera.setParameters(cam_params);
 			}
 		});
         
         Button btn_Effects_BlackBoard = new Button(this);
         btn_Effects_BlackBoard.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_BlackBoard.setText("Blackboard");
         btn_Effects_BlackBoard.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				cam_params.setColorEffect(Camera.Parameters.EFFECT_BLACKBOARD);
  				mPreview.mCamera.setParameters(cam_params);
  			}
  		});
         
         Button btn_Effects_Mono = new Button(this);
         btn_Effects_Mono.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_Mono.setText("Mono");
         btn_Effects_Mono.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				cam_params.setColorEffect(Camera.Parameters.EFFECT_MONO);
  				mPreview.mCamera.setParameters(cam_params);
  			}
  		});
         
         Button btn_Effects_Negative = new Button(this);
         btn_Effects_Negative.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_Negative.setText("Negative");
         btn_Effects_Negative.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				cam_params.setColorEffect(Camera.Parameters.EFFECT_NEGATIVE);
  				mPreview.mCamera.setParameters(cam_params);
  			}
  		});
         
         Button btn_Effects_Posterize = new Button(this);
         btn_Effects_Posterize.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_Posterize.setText("Posterize");
         btn_Effects_Posterize.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				cam_params.setColorEffect(Camera.Parameters.EFFECT_POSTERIZE);
  				mPreview.mCamera.setParameters(cam_params);
  			}
  		});
         
         Button btn_Effects_Sepia = new Button(this);
         btn_Effects_Sepia.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_Sepia.setText("Sepia");
         btn_Effects_Sepia.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				cam_params.setColorEffect(Camera.Parameters.EFFECT_SEPIA);
  				mPreview.mCamera.setParameters(cam_params);
  			}
  		});
         
         Button btn_Effects_Solarize = new Button(this);
         btn_Effects_Solarize.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_Solarize.setText("Solarize");
         btn_Effects_Solarize.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				cam_params.setColorEffect(Camera.Parameters.EFFECT_SOLARIZE);
  				mPreview.mCamera.setParameters(cam_params);
  			}
  		});
         
         Button btn_Effects_Whiteboard = new Button(this);
         btn_Effects_Whiteboard.setLayoutParams(WRAP_CONTENT_WRAP_CONTENT);
         btn_Effects_Whiteboard.setText("Whiteboard");
         btn_Effects_Whiteboard.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				cam_params.setColorEffect(Camera.Parameters.EFFECT_WHITEBOARD);
  				mPreview.mCamera.setParameters(cam_params);
  			}
  		});
        
         linear4.addView(img_Effects_back); 
         linear4.addView(btn_Effects_None);
         linear4.addView(btn_Effects_Aqua);
         linear4.addView(btn_Effects_BlackBoard);
         linear4.addView(btn_Effects_Mono);
         linear4.addView(btn_Effects_Negative);
         linear4.addView(btn_Effects_Posterize);
         linear4.addView(btn_Effects_Sepia);
         linear4.addView(btn_Effects_Solarize);
         linear4.addView(btn_Effects_Whiteboard);
         
         linearMain.addView(linear1);
         linearMain.addView(linear2);
         linearMain.addView(linear3);
         linearMain.addView(horizontalView);
         
         setContentView(linearMain,paramsMain);
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent();
		CameraLibrary.this.setResult(Activity.RESULT_CANCELED, i);
		CameraLibrary.this.finish();
	}
}
