import PropTypes from 'prop-types';
import React, {Component} from 'react';
import {
    NativeModules,
    requireNativeComponent,
    StyleSheet,
    SafeAreaView,
    ViewPropTypes
} from 'react-native';

let RNScanView = requireNativeComponent('RNScanView', ScanView);
let {
    Camera,
    Flash,
    Barcode
} = NativeModules.RNScanViewManager;

class ScanView extends Component {
    static Const = {
        'Camera': Camera,
        'Flash': Flash,
        'Barcode': Barcode
    };

    static propTypes = {
        ...ViewPropTypes,
        onScanCode: PropTypes.func.isRequired,
        codeTypes: PropTypes.array,
        camera: PropTypes.number,
        flash: PropTypes.number
    };

    _onScanCode = (event) => {
        if (!this.props.onScanCode) {
            return
        }
        this.props.onScanCode(event.nativeEvent);
    };

    static defaultProps = {
        codeTypes: Object.values(Barcode),
        camera: Camera.back,
        flash: Flash.off
    };

    render() {
        return (
            <SafeAreaView style={styles.container}>
                <RNScanView
                    {...this.props}
                    onScanCode={this._onScanCode}
                    style={styles.scanView}
                />
            </SafeAreaView>
        )
    }
}

styles = StyleSheet.create({
    container: {
        flex: 1
    },
    scanView: {
        flex: 1,
        alignSelf: 'stretch'
    }
});

export default ScanView;