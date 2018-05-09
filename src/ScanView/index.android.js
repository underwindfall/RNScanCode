import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {
    StyleSheet,
    NativeModules,
    requireNativeComponent,
    ViewPropTypes,
} from 'react-native';

let constants = NativeModules.UIManager.RNScanView.Constants;
let RNScanView = requireNativeComponent('RNScanView', ScanView);

class ScanView extends Component {
    static Const = {
        'Camera': constants.Camera,
        'Flash': constants.Flash,
        'Barcode': constants.BarCode,
    };

    static defaultProps = {
        codeTypes: Object.values([]),
        camera: constants.Camera.back,
        flash: constants.Flash.off,
    };

    _onScanCode = (event) => {
        if (!this.props.onScanCode) {
            return;
        }
        this.props.onScanCode(event.nativeEvent);
    };

    render() {
        return (
            <RNScanView {...this.props}
                        onScanCode={this._onScanCode}
                        style={styles.container}/>
        );
    }


}

ScanView.propTypes = {
    // include the default view properties
    ...ViewPropTypes,
    camera: PropTypes.string,
    flash: PropTypes.string,
    onScanCode: PropTypes.func.isRequired,
    codeTypes: PropTypes.array
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignSelf: 'stretch'
    }
});
export default ScanView;