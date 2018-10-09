package dev.ale.android.memescreator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener{
    //Extra keys to call objects

    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_ID = "imageId";

    //For memes api
    private static final String URL_DATA = "https://api.imgflip.com/get_memes";
    public static final String URL_POST = "https://api.imgflip.com/caption_image";


    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<ListItem> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //this improve performance
        recyclerView.setHasFixedSize(true);
        //layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listItems = new ArrayList<>();
        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {

        //To show message while data are loading
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data......");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    //------------Parse response to JSONObject------///
                    JSONObject jo = new JSONObject(response);
                    JSONObject jsonObject = jo.getJSONObject("data");
                    JSONArray jsonArray = jsonObject.getJSONArray("memes");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject a = jsonArray.getJSONObject(i);
                        ListItem item = new ListItem(
                                a.getString("id"),
                                a.getString("name"),
                                a.getString("url")

                        );
                        listItems.add(item);
                    }

                    adapter = new MyAdapter(listItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void OnItemClick(int position) {

        Intent intent = new Intent(this, TwoTexts.class);
        ListItem clickedItem = listItems.get(position);
        intent.putExtra(EXTRA_ID, clickedItem.getId());
        intent.putExtra(EXTRA_URL, clickedItem.getImageUrl());
        startActivity(intent);
    }

}


