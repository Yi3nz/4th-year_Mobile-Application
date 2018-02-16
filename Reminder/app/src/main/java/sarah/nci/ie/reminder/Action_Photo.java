package sarah.nci.ie.reminder;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Take notes photo page
 * Reference sample app from Anu - CameraGalleryApp
 */

public class Action_Photo extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GET = 2;
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;
    ImageView ivImage;
    TextView tvImageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_photo);

        ivImage = (ImageView) findViewById(R.id.imageView);
        tvImageText = (TextView) findViewById(R.id.textView);
        Button bCamera = (Button) findViewById(R.id.buttonCamera);
        Button bGallery = (Button) findViewById(R.id.buttonGallery);

        bCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        bGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryIntentCode();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivImage.setImageBitmap(imageBitmap);
            tvImageText.setText("Image captured by Camera");
        }
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ivImage.setImageBitmap(thumbnail);
                tvImageText.setText("Image picked from Gallery");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Exception: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cameraIntentCode() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void galleryIntentCode() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_GET);
        }
    }

    private void takePicture() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            cameraIntentCode();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If the permission was granted
                    cameraIntentCode();
                } else {
                    Toast.makeText(getApplicationContext(),"Permission denied",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}