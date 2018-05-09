import React, {Component} from 'react';
import {Alert, StyleSheet, View} from 'react-native';
import ScanView from "./src/ScanView";

export default class App extends Component {

    constructor(props) {
        super(props);

        this.state = {
            codeString: ''
        }
    }

    onScanCode = (event) => {
        this.setState({codeString: event.value});
        Alert.alert('ScanCodeResult', 'My ScanCode Result is' + ' type : ' + event.type + ' value : ' + event.value, [
                {text: 'cancel'}
            ],
            {cancelable: true}
        );
    };

    render() {
        let {Flash, Barcode} = ScanView.Const;
        return (
            <View style={styles.container}>
                <ScanView
                    onScanCode={this.onScanCode}
                    flash={Flash.off}
                    codeTypes={[Barcode.qr, Barcode.ean8, Barcode.ean13, Barcode.code39]}
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
