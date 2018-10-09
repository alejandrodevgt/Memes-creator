package dev.ale.android.memescreator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class ShareActivity extends AppCompatActivity {

    public static String EXTRA_IMAGEURL = "imageUrl";
    public static final String EXTRA_IMAGE = "imageUr";

    ImageView imageView;
    Button share, save;
    String imageUr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        share = (Button) findViewById(R.id.btnShare);
        save = (Button) findViewById(R.id.btnSave);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGEURL);
        imageUr = intent.getStringExtra(EXTRA_IMAGE);

        imageView = (ImageView) findViewById(R.id.imgMemeCreated);
        Picasso.get().load(imageUrl).into(imageView);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();
                saveImageToGallery();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShare();
                Toast.makeText(getApplicationContext(),"Sharing....",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveImageToGallery(){
        imageView.setDrawingCacheEnabled(true);
        Bitmap b = imageView.getDrawingCache();
        MediaStore.Images.Media.insertImage(getContentResolver(),
                b,
                "title",
                "description");
    }

    public void startShare(){

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, this.imageUr);
        startActivity(share);
    }

}


