package com.example.hxr.trafficstop;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Isabel on 6/20/17.
 */

public class SendPhotosActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    //example 1
    private static int IMG_RESULT = 3;
    String ImageDecode;
    ImageView imageViewLoad;
    Button LoadImage;
    Intent intent;
    String[] FILE;

    //example 2
    private String selectedImagePath;
    //ADDED
    private String filemanagerstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_photos);
        setTitle("Send Information");
        mImageView = (ImageView) findViewById(R.id.imageView);

        //pick image
        imageViewLoad = (ImageView) findViewById(R.id.imageView1);
        LoadImage = (Button)findViewById(R.id.plate);

        LoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);

            }
        });


    }

    public void dispatchTakePictureIntent(View view) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("Error" , String.valueOf(ex));
            }
//            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d("Photo not null" , String.valueOf(photoFile));
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.hxr.trafficstop",
                        photoFile);
                // this line is not working
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                // this one is
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            else {
                Log.d("Photo null", "test");
            }
        }
    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("code", String.valueOf(requestCode));
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                try {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mImageView.setImageBitmap(imageBitmap);
                } catch (Exception e) {
                    Toast.makeText(this, "Please try selfie again", Toast.LENGTH_LONG).show();
                }
            }
           else if (requestCode == IMG_RESULT ) {
                try {
                    Log.d("code", "img result");
                    Uri uri = data.getData();
                    Log.i("Pick Image", "Uri: " + uri.toString());

                    ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                    parcelFileDescriptor.close();
                    imageViewLoad.setImageBitmap(image);

                    //setPic();
                    Picasso.with(getApplicationContext()).load(uri).fit().centerCrop().into(imageViewLoad);


                } catch(Exception e){
                    Toast.makeText(this, "Please try picking " +
                            "again", Toast.LENGTH_LONG)
                            .show();
                }
            }
            else{
                    Toast.makeText(this, "Wrong code" + "again", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "Please try  " + "again", Toast.LENGTH_LONG).show();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageViewLoad.setImageBitmap(bitmap);
    }
}
