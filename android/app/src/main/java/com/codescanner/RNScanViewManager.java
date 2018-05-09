package com.codescanner;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by qifan on 2018/2/15.
 */

public class RNScanViewManager extends ViewGroupManager<RNScanView> implements LifecycleEventListener {

    private static final String REACT_CLASS = "RNScanView";
    private RNScanView mScannerView;
    private boolean mScannerViewVisible;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNScanView createViewInstance(ThemedReactContext reactContext) {
        reactContext.addLifecycleEventListener(this);
        mScannerView = new RNScanView(reactContext);
        mScannerView.setViewFinderDisplay(true);
        mScannerViewVisible = true;
        return mScannerView;
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.<String, Object>of(
                "topScanCode",
                MapBuilder.of("registrationName", "onScanCode"));
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedViewConstants() {
        Map<String, Object> constants = new HashMap<>();
        constants.put("Camera", supportedCameraPositions());
        constants.put("Flash", supportedFlashModes());
        constants.put("BarCode", supportedCodeTypes());
        return constants;
    }


    @ReactProp(name = "camera")
    public void setCameraType(RNScanView view, @Nullable String cameraType) {
        if (cameraType != null) {
            view.setCameraType(cameraType);
        }
    }

    @ReactProp(name = "flash")
    public void setTorchMode(RNScanView view, @Nullable String torchMode) {
        if (torchMode != null) {
            view.setTorchMode(torchMode);
        }
    }

    @ReactProp(name = "codeTypes")
    public void setBarCodeFormat(RNScanView view, @Nonnull ReadableArray barcodeFormats) {
        List<String> barcodeFormatList = new ArrayList<>();
        for (int i = 0; i < barcodeFormats.size(); i++) {
            barcodeFormatList.add(barcodeFormats.getString(i));
        }
        if (!barcodeFormatList.isEmpty()) {
            view.setCodeFormats(barcodeFormatList);
        }
    }

    @Override
    public void onDropViewInstance(RNScanView view) {
        super.onDropViewInstance(view);
        ((ThemedReactContext) view.getContext()).removeLifecycleEventListener(this);
        mScannerViewVisible = false;
        mScannerView.stopCamera();
    }

    @Override
    public void onHostResume() {
        if (mScannerViewVisible) {
            mScannerView.startCamera(mScannerView.getCameraId());
            mScannerView.setFlash(mScannerView.torchModeIsEnabled());
        }
    }

    @Override
    public void onHostPause() {
        mScannerView.stopCamera();
    }

    @Override
    public void onHostDestroy() {
        mScannerView.stopCamera();
    }


    private Map<String, String> supportedCodeTypes() {
        Map<String, String> map = new HashMap<>();
        map.put("coda_bar", "CODABAR");
        map.put("code39", "CODE_39");
        map.put("code93", "CODE_93");
        map.put("code128", "CODE_128");
        map.put("dataMatrix", "DATA_MATRIX");
        map.put("ean8", "EAN_8");
        map.put("ean13", "EAN_13");
        map.put("itf14", "ITF");
        map.put("pdf417", "PDF_417");
        map.put("qr", "QR_CODE");
        map.put("rss14", "RSS_14");
        map.put("upca", "UPC_A");
        map.put("upce", "UPC_E");
        return map;
    }

    private Map<String, String> supportedCameraPositions() {
        Map<String, String> map = new HashMap<>();
        map.put("front", "front");
        map.put("back", "back");
        return map;
    }

    private Map<String, String> supportedFlashModes() {
        Map<String, String> map = new HashMap<>();
        map.put("on", "on");
        map.put("off", "off");
        return map;
    }

}
