package com.girish.cameraLibrary;

import java.io.File;

import android.graphics.Bitmap;

public interface OnPictureTaken {

	public void pictureTaken(Bitmap bitmap, File filePath);
}
