# react-native-scanview-barcode

A barcode scanner component for React Native built from scratch.

This component has been created for a simple use of barcode scanner with some customizations.

## Get Started

### Supported OS versions

* iOS : 10.0+
* Android : SDK 23+
  
### Installation

`npm install --save react-native-scanview-barcode`

#### Requirements 

##### iOS

You need to add in your Info.plist the NSCameraUsageDescription.

Example
```
<key>NSCameraUsageDescription</key>
<string>Camera used for barcode scanner</string>
```
##### Android

```
<uses-permission android:name="android.permission.CAMERA"/>
```

### Usage

You need to import `ScanView` from `react-native-scanview-barcode` then you can use it as `<ScanView/>` tag.

`import { ScanView } from 'react-native-scanview-barcode';`

### Props

#### Native callbacks props

#### `onScanCode`

Function to be called when native code emit onScanCode, when barcode is detected.

```javascript
onScanCode = (event) => {
    // Type
    console.log(event.type);
    
    // Data
    console.log(event.value);
};
```

#### Properties

#### `codeTypes`

All types are supported by default.


| BarCode Type                                                       | iOS  | Android |
|:-------------------------------------------------------------|:-----:|:-----:|
| Barcode.code39                                               |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.code93                                               |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.code128                                              |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.dataMatrix                                           |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.ean8                                                 |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.ean13                                                |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.itf14                                                |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.pdf417                                               |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.qr                                                   |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.upce                                                 |  :white_check_mark:  |  :white_check_mark:  |
| Barcode.aztec                                                |  :white_check_mark:  |                      |
| Barcode.code39Mod43                                          |  :white_check_mark:  |                      |
| Barcode.interleaved2of5                                      |  :white_check_mark:  |                      |
| Barcode.coda_bar                                             |                      |  :white_check_mark:  |
| Barcode.upca                                                 |                      |  :white_check_mark:  |
| Barcode.rss14                                                |                      |  :white_check_mark:  |


#### `camera`

The back facing camera is used by default.

Values :
`ScanView.Const.Camera.back` or `ScanView.Const.Camera.front`

#### `flash`

The flash is turned off by default.

Values :
`ScanView.Const.Flash.on` or `ScanView.Const.Flash.off`

### Example

```javascript
import React, {Component} from 'react';
import {
    StyleSheet,
    View
} from 'react-native';
import { ScanView } from 'react-native-scanview-barcode';

type Props = {};
export default class App extends Component<Props> {

    constructor(props) {
        super(props);

        this.state = {
            codeString: ''
        }
    }

    onScanCode = (event) => {
        this.setState({codeString: event.value})
    };

    render() {
        let {Flash, Barcode} = ScanView.Const;

        return (
            <View style={styles.container}>
                <ScanView
                    onScanCode={this.onScanCode}
                    flash={Flash.on}
                    codeTypes={[Barcode.qr, Barcode.ean8, Barcode.code39]}
                />
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: '#ff4300',
    }
});
```