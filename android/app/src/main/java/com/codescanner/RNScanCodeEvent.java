package com.codescanner;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Created by qifan on 2018/2/15.
 */

public class RNScanCodeEvent extends Event<RNScanCodeEvent> {
    public static final String eventName = "topScanCode";
    private final String mType;
    private final String mValue;

    public RNScanCodeEvent(int viewTag,String type,String value) {
        super(viewTag);
        mType = type;
        mValue = value;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }


    private WritableMap serializeEventData(){
        WritableMap arg = Arguments.createMap();
        arg.putString("type", mType);
        arg.putString("value", mValue);
        return arg;
    }
}
