package com.jaddu.demage.dmage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button  browseButton;
    TextView textView;
    Button decryptButton;
    Intent gallery;
    private static final int PICK_IMAGE=100;
    public static final int TOP_MARGIN = 13;
    public static final int SPACE = 14;
    Uri imageUri;
    String filename;
    Bitmap originalImage;
    Bitmap decryptImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.imageView);
        browseButton=(Button)findViewById(R.id.browseButton);
        textView= (TextView) findViewById(R.id.textView);
        decryptButton= (Button)findViewById(R.id.decryptButton);

        //this button is used to browse files from gallery
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decryptImage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

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
                .setMessage("First select the image from your gallery. And click DECRYPT to find the hidden text in the image.")
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
        builder.setTitle("Dmage")
                .setMessage("Used for decrypt the image. DEmage-Decrypt Encrypt Image Developers. Thank you for using this Application.")
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

    private void decryptImage() {
        InputStream fis= null;
        try {
            fis = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        originalImage= BitmapFactory.decodeStream(fis);
        decryptImage=originalImage.copy(originalImage.getConfig(),true);

        int width=decryptImage.getWidth();
        String text="";

        ArrayList<Character> characters = new ArrayList<Character>();

        for (int pixel = 0; pixel < width; pixel++) {
            if (pixel % SPACE == 0) {
                int color=decryptImage.getPixel(pixel,TOP_MARGIN);
                int greenhex= Color.green(color);
                characters.add((char)greenhex);
            }
        }

        for (char character : characters)
            text=text+ character;

        String finalText="";
        String textLen=text.substring(0, 2);
        int textLength=Integer.parseInt(textLen)+2;
        for(int i=2;i<textLength;i++)
            finalText+=text.charAt(i);

        textView.setText(finalText);
    }

    private void openGallery(){
        gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode==PICK_IMAGE && resultcode==RESULT_OK){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);
            filename=imageUri.getPath();
        }
    }
}
