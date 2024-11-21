package com.example.calamitycall.utils;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.Looper;

public class FlashNotification {

    private final CameraManager cameraManager;
    private final String cameraId;
    private boolean isFlashing;
    private final Handler handler;

    public FlashNotification(Context context) throws CameraAccessException {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        cameraId = cameraManager.getCameraIdList()[0]; // Get the back camera ID
        handler = new Handler(Looper.getMainLooper());
    }

    public void startFlashing(long onDuration, long offDuration) {
        if (isFlashing) return; // Prevent multiple calls
        isFlashing = true;

        handler.post(new Runnable() {
            boolean flashOn = false;

            @Override
            public void run() {
                if (!isFlashing) return;

                try {
                    // Toggle the flashlight
                    cameraManager.setTorchMode(cameraId, flashOn);
                    flashOn = !flashOn;

                    // Schedule the next toggle
                    handler.postDelayed(this, flashOn ? onDuration : offDuration);
                } catch (CameraAccessException e) {
                    //e.printStackTrace();
                }
            }
        });
    }

    public void stopFlashing() {
        if (!isFlashing) return;
        isFlashing = false;

        // Ensure the flashlight is off
        try {
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            //e.printStackTrace();
        }

        handler.removeCallbacksAndMessages(null); // Stop the handler
    }
}

