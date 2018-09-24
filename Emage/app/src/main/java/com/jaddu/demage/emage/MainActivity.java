package com.jaddu.demage.emage;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;
    EditText editText;
    Button  encryptButton;
    private static final int PICK_IMAGE=100;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final int TOP_MARGIN = 13;
    public static final int SPACE = 14;
    public static int ENCRYPT_IMAGE =0;
    Uri imageUri;
    String filename;
//    Bitmap bitmap;
    File dir;
    File filePath;
    Intent gallery;
    OutputStream outputStream;
    Bitmap originalImage;
    Bitmap encryptImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        imageView=(ImageView)findViewById(R.id.imageView);
        editText=(EditText) findViewById(R.id.editText);
        encryptButton=(Button)findViewById(R.id.encryptButton);
        //this button is used to browse files from gallery
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        //encryption process is done in this area
        encryptButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                encrptImage();
            }
        });

    }
//menu button
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("Save").setOnMenuItemClickListener(this.SaveImageClickListener).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        menu.add("Exit").setOnMenuItemClickListener(this.ExitClickListener).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//                return true;
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    //exit process


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.exit){
            exitApp();
        }
        if(item.getItemId()==R.id.about){
            aboutApp();
        }
        if(item.getItemId()==R.id.help){
            helpApp();
        }
        if(item.getItemId()==R.id.feedback){
            feedbackApp();
        }

        return super.onOptionsItemSelected(item);
    }

    private void feedbackApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Feedback/Contact")
                .setMessage("For any information contact jeganathpv@gmail.com")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void helpApp() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Help")
                .setMessage("First select the image from your gallery. Then Enter the text And click ENCRYPT. Thank You")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("Contact", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        feedbackApp();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void aboutApp() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Emage")
                .setMessage("Used for Encrypt the text into image. DEmage-Decrypt Encrypt Image Developers. Thank you for using this Application.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void exitApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    //save image process
    MenuItem.OnMenuItemClickListener SaveImageClickListener=new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(ENCRYPT_IMAGE==1){
                Toast.makeText(MainActivity.this,"Please wait, it may take some time",Toast.LENGTH_LONG).show();
                //after marshmallow android has protection so we need to provide runtime permission
                if (!checkPermission()) {
                    savePicture();
                } else {
                    if (checkPermission()) {
                        requestPermissionAndContinue();
                    } else {
                        savePicture();
                    }
                }
            }else{
                Toast.makeText(MainActivity.this,"Please proceed the encryption before you save",Toast.LENGTH_SHORT).show();
            }

            return false;
        }
    };

    private void savePicture() {
        //here we create bitmap image
//        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.jaddupatern);

        //this provide dir-- storage/emulated/0/
        filePath=Environment.getExternalStorageDirectory();
        //we create directory for Emage
        dir=new File(filePath.getAbsolutePath()+"/Emage");
        dir.mkdirs();
        //it returns file name for new file using date format
        String pictureName=getPictureName();
        File file=new File(dir,pictureName);
        try{
            outputStream=new FileOutputStream(file);
            encryptImage.compress(Bitmap.CompressFormat.PNG,100,outputStream);
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(MainActivity.this,"Image saved"+file.toString(),Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getPictureName() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmm");
        String timestamp=sdf.format(new Date());
        return "Emage"+timestamp+".png";
    }

    private void encrptImage() {
        int index = 0;

        ArrayList<Character>  chars = new ArrayList<Character>();
        ArrayList<Integer> asciiChars = new ArrayList<Integer>();
//        String fileImg=filename.toString()+".jpg";
//        File newFile=new File(imageUri.getPath());
        InputStream fis= null;
        try {
            fis = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        originalImage=BitmapFactory.decodeStream(fis);
//        originalImage=Bitmap.createBitmap();
//        originalImage= BitmapFactory.decodeResource(getResources(),R.id.imageView);
//        encryptImage=originalImage.getConfig();
        encryptImage=originalImage.copy(originalImage.getConfig(),true);

//        RectF bitmapRect = new RectF();
//        bitmapRect.right = imageView.getDrawable().getIntrinsicWidth();
//        int width=(int)bitmapRect.width();

        int width=encryptImage.getWidth();
        String text=editText.getText().toString();
        if(text.length()<10)
            text= "0"+text.length() + text;
        else
            text= text.length() + text;

        for(char c: text.toCharArray())
            chars.add(c);

        for(char c:chars)
            asciiChars.add((int)c);

        for (int x = 0; x < width; x++) {
            if (x % SPACE == 0 && index < asciiChars.size()) {
                encryptImage.setPixel(x,TOP_MARGIN,Color.rgb(1,asciiChars.get(index),1));
                index++;
            }
        }
        ENCRYPT_IMAGE=1;

    }

    private void openGallery(){
        gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode==PICK_IMAGE && resultcode==RESULT_OK){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);
            filename=imageUri.getPath();
//            editText.setText(filename);
        }
    }


    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ;
    }

    //Below codes are user to provide runtime permission
    private void requestPermissionAndContinue() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Emage");
                alertBuilder.setMessage("access required");
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE
                                , READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                Log.e("", "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            savePicture();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (permissions.length > 0 && grantResults.length > 0) {

                boolean flag = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                    }
                }
                if (flag) {
                    savePicture();
                } else {
                    finish();
                }

            } else {
                finish();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
