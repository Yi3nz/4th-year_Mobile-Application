package sarah.nci.ie.reminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * QR Code Result page
 */

public class Action_ScanQRCode extends AppCompatActivity {

    TextView tvScanningResult;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_qrcode);

        tvScanningResult = findViewById(R.id.tvQRCodeResult);

        //Get the intent extra value
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            result= null;
        } else {
            result= extras.getString("Result");
        }
        //Set the value to the textView
        tvScanningResult.setText(result);
    }

}
