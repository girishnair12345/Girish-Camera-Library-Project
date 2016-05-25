package girish.cameraLibrary;

import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CamPreview extends SurfaceView implements SurfaceHolder.Callback{

	private SurfaceHolder mHolder;
	public Camera mCamera;
	private Context mContext;
	
	private final String LOG_TAG = "Camera Lib : MyPreview";
	public static boolean front_camera = false;
	
	public CamPreview(Context mContext){
		super(mContext);
		this.mContext = mContext;
		mHolder = getHolder();
		mHolder.addCallback(this);
		// Added for devices before 3.0 else it crashes 
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder){
		try{
			if(mCamera == null)
				if(front_camera){
					Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				    int cameraCount = Camera.getNumberOfCameras();
				    for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) {
				        Camera.getCameraInfo( camIdx, cameraInfo );
				        if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
				            try {
				            	mCamera = Camera.open( camIdx );
				            } catch (RuntimeException e) {
				                Log.e(LOG_TAG, "Camera failed to open: " + e.getLocalizedMessage());
				            }
				        }
				    }
				}else{
					mCamera = android.hardware.Camera.open();
				}
				mCamera.setPreviewDisplay(mHolder);
				Camera.Parameters parameters = mCamera.getParameters();
	            List<Size> sizes = parameters.getSupportedPictureSizes();
	            parameters.setPictureSize(sizes.get(0).width, sizes.get(0).height); // mac dinh solution 0
	            parameters.set("orientation","portrait");
	            List<Size> size = parameters.getSupportedPreviewSizes();
	            parameters.setPreviewSize(size.get(0).width, size.get(0).height);
	            mCamera.setParameters(parameters);
	        	//mCamera.setPreviewCallback(null);
		}catch(Exception e){
			Toast.makeText(mContext, e.getMessage(), 35).show();
			Log.e(LOG_TAG, e.getMessage()+"");
			e.printStackTrace();
			System.exit(0);
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
	
	
}
