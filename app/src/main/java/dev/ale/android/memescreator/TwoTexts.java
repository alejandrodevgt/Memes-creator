package dev.ale.android.memescreator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TwoTexts extends AppCompatActivity {

    public static final String URL_POST = "https://api.imgflip.com/caption_image";
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_ID = "imageId";
    public static final String EXTRA_IMAGEURL = "imageUrl";
    public static final String EXTRA_IMAGE = "imageUr";

    ImageView imageView;
    TextView textView;
    EditText editText1, editText2;
    Button probar;
    String imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_texts);
        //this par is for send parameteres to de Api
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        probar = (Button)findViewById(R.id.btnCrear);

        //this part is for onItemClick to show image and id
        Intent intent = getIntent();
       imageId = intent.getStringExtra(EXTRA_ID);
         //String imageId = intent.getStringExtra(EXTRA_ID);
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        imageView = (ImageView)findViewById(R.id.imgMeme);
        textView = (TextView)findViewById(R.id.textView);

        //textView.setText(imageId);
        Picasso.get()
                .load(imageUrl)
                .into(imageView);

        probar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostMeme();
            }
        });

    }

    private void PostMeme() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                        //-----Parse response
                        try {
                            JSONObject jo = new JSONObject(response);
                            JSONObject jsonObject = jo.getJSONObject("data");
                            String imageUrl = jsonObject.getString("url");
                            String imageUr = jsonObject.getString("page_url");
                            Intent intent = new Intent(TwoTexts.this, ShareActivity.class);
                            intent.putExtra(EXTRA_IMAGEURL, imageUrl);
                            intent.putExtra(EXTRA_IMAGE,imageUr);
                            startActivity(intent);

                        }catch(Exception e){
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TwoTexts.this,error + "",Toast.LENGTH_SHORT).show();

            }
        }){
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                String image_id = imageId;
                params.put("template_id", image_id);
                params.put("username", "aledevgt");
                params.put("password", "12345");
                params.put("text0", text1);
                params.put("text1", text2);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
