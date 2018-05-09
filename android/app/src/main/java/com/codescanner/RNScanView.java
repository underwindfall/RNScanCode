package com.codescanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by qifan on 2018/2/15.
 */

public class RNScanView extends RNScanComponentView implements RNScanComponentView.ResultHandler {
    private boolean mDrawLaser;
    private ViewFinderView mViewFinderView;
    private int mCameraId = -1;
    private String mPrevCameraType = "";
    private String mTorchMode = "";

    public RNScanView(ThemedReactContext context) {
        super(context);
        setResultHandler(this);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {

        if (mDrawLaser) {
            Log.w("camera", "drawLaser set to true");
            mViewFinderView = new ViewFinderView(context);
        } else {
            Log.w("camera", "drawLaser set to false");
            mViewFinderView = new CustomViewFinderView(context);
        }
        return mViewFinderView;
    }

    private static class CustomViewFinderView extends ViewFinderView {

        public CustomViewFinderView(Context context) {
            super(context);
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void onDraw(Canvas canvas) {
            Log.w("camera", "drawing custom view finder view");
            if (getFramingRect() == null) {
                return;
            }

            drawViewFinderMask(canvas);
            drawViewFinderBorder(canvas);
        }
    }


    // #AARRGGBB
    public void setMaskColor(String maskColor) {
        mViewFinderView.setMaskColor(Color.parseColor(maskColor));
    }

    // #AARRGGBB
    public void setBorderColor(String borderColor) {
        mViewFinderView.setBorderColor(Color.parseColor(borderColor));
    }

    public void setBorderStrokeWidth(int borderStrokeWidth) {
        mViewFinderView.setBorderStrokeWidth(borderStrokeWidth);
    }

    public void setBorderLineLength(int borderLineLength) {
        mViewFinderView.setBorderLineLength(borderLineLength);
    }

    public void setDrawLaser(boolean drawLaser) {
        mDrawLaser = drawLaser;
    }

    // #AARRGGBB
    public void setLaserColor(String laserColor) {
        mViewFinderView.setLaserColor(Color.parseColor(laserColor));
    }

    public void setTorchMode(String torchMode) {
        mTorchMode = torchMode;
        setFlash(torchModeIsEnabled());
    }

    public void setCodeFormats(List<String> codeFormats) {
        List<BarcodeFormat> barcodeFormatList = new ArrayList<>();
        for (String codeFormat : codeFormats) {
            switch (codeFormat) {
                case "AZTEC":
                    barcodeFormatList.add(BarcodeFormat.AZTEC);
                    break;
                case "CODABAR":
                    barcodeFormatList.add(BarcodeFormat.CODABAR);
                    break;
                case "CODE_39":
                    barcodeFormatList.add(BarcodeFormat.CODE_39);
                    break;
                case "CODE_93":
                    barcodeFormatList.add(BarcodeFormat.CODE_93);
                    break;
                case "CODE_128":
                    barcodeFormatList.add(BarcodeFormat.CODE_128);
                    break;
                case "DATA_MATRIX":
                    barcodeFormatList.add(BarcodeFormat.DATA_MATRIX);
                    break;
                case "EAN_8":
                    barcodeFormatList.add(BarcodeFormat.EAN_8);
                    break;
                case "EAN_13":
                    barcodeFormatList.add(BarcodeFormat.EAN_13);
                    break;
                case "ITF":
                    barcodeFormatList.add(BarcodeFormat.ITF);
                    break;
                case "MAXICODE":
                    barcodeFormatList.add(BarcodeFormat.MAXICODE);
                    break;
                case "PDF_417":
                    barcodeFormatList.add(BarcodeFormat.PDF_417);
                    break;
                case "QR_CODE":
                    barcodeFormatList.add(BarcodeFormat.QR_CODE);
                    break;
                case "RSS_14":
                    barcodeFormatList.add(BarcodeFormat.RSS_14);
                    break;
                case "RSS_EXPANDED":
                    barcodeFormatList.add(BarcodeFormat.RSS_EXPANDED);
                    break;
                case "UPC_A":
                    barcodeFormatList.add(BarcodeFormat.UPC_A);
                    break;
                case "UPC_E":
                    barcodeFormatList.add(BarcodeFormat.UPC_E);
                    break;
                case "UPC_EAN_EXTENSION":
                    barcodeFormatList.add(BarcodeFormat.UPC_EAN_EXTENSION);
                    break;
                default:
                    break;
            }

        }
        setFormats(barcodeFormatList);
    }

    public boolean torchModeIsEnabled() {
        return mTorchMode.equals("on");
    }

    public int getCameraId() {
        return mCameraId;
    }

    // front, back
    public void setCameraType(String type) {
        if (mPrevCameraType.equals(type)) return;

        stopCamera();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        mCameraId = -1;

        for (int cameraId = 0; cameraId < Camera.getNumberOfCameras(); cameraId++) {
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (type.equals("back") && cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                mCameraId = cameraId;
                break;
            }
            if (type.equals("front") && cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mCameraId = cameraId;
                break;
            }
        }
        startCamera(mCameraId);
        if (type.equals("back")) {
            setFlash(torchModeIsEnabled());
        }
        mPrevCameraType = type;
    }

    @Override
    public void handleResult(Result result) {
        // Received Barcode Result!
        ReactContext reactContext = (ReactContext) getContext();
        EventDispatcher eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        eventDispatcher.dispatchEvent(new RNScanCodeEvent(getId(), result.getBarcodeFormat().toString(), result.getText()));
        startCamera(mCameraId);
        setFlash(torchModeIsEnabled());
    }


}