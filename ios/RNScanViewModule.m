//
//  RNScanViewModule.m
//  CodeScanner
//
//  Created by Ronael on 06/02/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTViewManager.h>

@interface RCT_EXTERN_MODULE(RNScanViewManager, RCTViewManager)

RCT_EXPORT_VIEW_PROPERTY(onScanCode, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(codeTypes, NSArray)
RCT_EXPORT_VIEW_PROPERTY(camera, NSInteger)
RCT_EXPORT_VIEW_PROPERTY(flash, NSInteger)

@end
