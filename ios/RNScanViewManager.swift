//
//  RNScanViewManager.swift
//  CodeScanner
//
//  Created by Ronael on 06/02/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

import Foundation
import AVFoundation
import UIKit

@objc(RNScanViewManager)
class RNScanViewManager: RCTViewManager {
  override func view() -> UIView! {
    return RNScanView()
  }
  
  func supportedCodeTypes() -> NSDictionary {
    return [
      "aztec": AVMetadataObjectTypeAztecCode,
      "code128": AVMetadataObjectTypeCode128Code,
      "code39": AVMetadataObjectTypeCode39Code,
      "code39Mod43": AVMetadataObjectTypeCode39Mod43Code,
      "code93": AVMetadataObjectTypeCode93Code,
      "dataMatrix": AVMetadataObjectTypeDataMatrixCode,
      "ean13": AVMetadataObjectTypeEAN13Code,
      "ean8": AVMetadataObjectTypeEAN8Code,
      "interleaved2of5": AVMetadataObjectTypeInterleaved2of5Code,
      "itf14": AVMetadataObjectTypeITF14Code,
      "pdf417": AVMetadataObjectTypePDF417Code,
      "qr": AVMetadataObjectTypeQRCode,
      "upce": AVMetadataObjectTypeUPCECode
    ]
  }

  func supportedCameraPositions() -> NSDictionary {
    return [
      "front": AVCaptureDevicePosition.front.rawValue,
      "back": AVCaptureDevicePosition.back.rawValue
    ]
  }

  func supportedFlashModes() -> NSDictionary {
    return [
      "off": AVCaptureTorchMode.off.rawValue,
            "on": AVCaptureTorchMode.on.rawValue
    ]
  }

  override func constantsToExport() -> [AnyHashable : Any]! {
    let constants: [AnyHashable : Any] = [
      "Camera": supportedCameraPositions(),
      "Flash": supportedFlashModes(),
      "Barcode": supportedCodeTypes()
    ]
    return constants
  }
}
