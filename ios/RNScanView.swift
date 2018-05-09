//
//  RNScanView.swift
//  CodeScanner
//
//  Created by Ronael on 08/02/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

import AVFoundation
import UIKit

@objc(RNScanView)
class RNScanView: ScanView {
  
  override init(frame: CGRect) {
    super.init(frame: frame)
  }
  
  required init?(coder aDecoder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }
  
  override func reactSetFrame(_ frame: CGRect) {
    super.reactSetFrame(frame)
  }
  
  @objc(setCamera:)
  func set(Camera value: Int) {
    camera = AVCaptureDevicePosition(rawValue: value)
  }
  
  @objc(setFlash:)
  func set(Flash value: Int) {
    flash = AVCaptureTorchMode(rawValue: value)
  }
}
