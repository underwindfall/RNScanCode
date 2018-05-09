//
//  ScanView.swift
//  CodeScanner
//
//  Created by Ronael on 06/02/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

import Foundation
import AVFoundation
import UIKit

class ScanView: UIView {
  
  var captureSession = AVCaptureSession()
  var videoPreviewLayer: AVCaptureVideoPreviewLayer?
  var codeFrameView: UIView?
  
  public var onScanCode: RCTBubblingEventBlock?
  public var codeTypes: [String]!
  public var camera: AVCaptureDevicePosition!
  public var flash: AVCaptureTorchMode!
  
  override func draw(_ rect: CGRect) {
    prepare()
  }
  
  func prepare() {
    let deviceDiscoverySession = AVCaptureDevice.DiscoverySession(deviceTypes: [.builtInWideAngleCamera], mediaType: AVMediaTypeVideo, position: camera)
    
    guard let captureDevice = deviceDiscoverySession?.devices.first else {
      return
    }
    
    do {
      let input = try AVCaptureDeviceInput(device: captureDevice)
      captureSession.addInput(input)
      
      let captureMetadataOutput = AVCaptureMetadataOutput()
      captureSession.addOutput(captureMetadataOutput)
      
      captureMetadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
      captureMetadataOutput.metadataObjectTypes = codeTypes
    } catch {
      return
    }
    
    videoPreviewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
    videoPreviewLayer?.videoGravity = AVLayerVideoGravityResizeAspectFill
    videoPreviewLayer?.frame = layer.bounds
    layer.addSublayer(videoPreviewLayer!)
    
    captureSession.sessionPreset = AVCaptureSessionPresetHigh
    captureSession.startRunning()
    setTorch(mode: flash, for: captureDevice)
    setFocus(mode: .continuousAutoFocus, for: captureDevice)
    
    let codeFrameSize = frame.width * 0.7
    codeFrameView = UIView(frame: CGRect(x: (frame.width / 2) - codeFrameSize / 2, y: (frame.height / 2) - codeFrameSize / 2, width: codeFrameSize, height: codeFrameSize))
    if let codeFrameView = codeFrameView {
      codeFrameView.layer.borderColor = UIColor.white.cgColor
      codeFrameView.layer.borderWidth = 3
      addSubview(codeFrameView)
      bringSubview(toFront: codeFrameView)
    }
  }
  
  func setTorch(mode: AVCaptureTorchMode, for device: AVCaptureDevice) {
    if device.hasTorch {
      do {
        try device.lockForConfiguration()
        device.torchMode = mode
        device.unlockForConfiguration()
      } catch {
        print("Torch could not be used")
      }
    } else {
      print("Torch is not available")
    }
  }
  
  func setFocus(mode: AVCaptureFocusMode, for device: AVCaptureDevice) {
    if device.isFocusModeSupported(.autoFocus) {
      do {
        try device.lockForConfiguration()
        device.focusMode = mode
        device.unlockForConfiguration()
      } catch {
        print("Focus could not be used")
      }
    } else {
      print("Focus is not available")
    }
  }
}

extension ScanView: AVCaptureMetadataOutputObjectsDelegate {
  func captureOutput(_ output: AVCaptureOutput!, didOutputMetadataObjects metadataObjects: [Any]!, from connection: AVCaptureConnection!) {
    if metadataObjects.count == 0 {
      codeFrameView?.frame = CGRect.zero
      let generator = UINotificationFeedbackGenerator()
      generator.notificationOccurred(.error)
      return
    }
    
    let metadataObj = metadataObjects[0] as! AVMetadataMachineReadableCodeObject
    if codeTypes!.contains(metadataObj.type) {
      if let barCodeObject = videoPreviewLayer?.transformedMetadataObject(for: metadataObj) {
        codeFrameView?.frame = barCodeObject.bounds
      }
      if metadataObj.stringValue != nil {
        let generator = UINotificationFeedbackGenerator()
        generator.notificationOccurred(.success)
        onScanCode?([
          "type": metadataObj.type,
          "value": metadataObj.stringValue
          ])
      }
    }
  }
}
