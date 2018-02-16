package sarah.nci.ie.reminder;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Recycler view
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<CA> cas;

    //Actionbar - Reference - https://www.journaldev.com/9357/android-actionbar-example-tutorial
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.photo:
            //Case 1 - Take photo snipper
            Intent i = new Intent(this, Action_Photo.class);
            this.startActivity(i);
            return(true);
        case R.id.qrCode:
            //Case 2 - Scan QRCode from books to get details
            final Activity activity = this;

            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();

            return(true);
        case R.id.exit:
            //Case 3 - Exit the app
            finish();
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Custom recycler view - Reference https://www.youtube.com/watch?v=CTBiwKlO5IU
        recyclerView = findViewById(R.id.recycler_view);
        //Room database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
        //Call the query from CADao
        cas = db.caDao().getAllCAs();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CAAdapter(cas);
        recyclerView.setAdapter(adapter);

    }

    //FloatButton click - Go to AddActivity
    public void addClick(View v){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Goes to Scanning
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {//Once scan completed...
                Toast.makeText(this, "Success " + result.getContents(),Toast.LENGTH_LONG).show();

                //Forward to the next activity
                Intent intent = new Intent(this, Action_ScanQRCode.class);
                //Set the QR result to intent extra
                intent.putExtra("Result", result.getContents());
                startActivityForResult(intent, Intent_Constants.INTENT_REQUEST_CODE);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


}