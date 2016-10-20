package a8ielts.haipham.com.a8ielts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String PLAYLIST_ID = "PLHDHWz13RIdIIZ8wDHh6l2RSZkZM41-YT";
    public static final String KEY = "AIzaSyAKOtDCpaGQ9624yS8-9pADYH0qFvmK6f8";

    private static final String TAG = MainActivity.class.getSimpleName();
    private String url = "";
    private ProgressDialog pDialog;
    private List<Youtube> youtubeList = new ArrayList<>();
    private VlogListAdapter likeProgramAdapter;
    private RecyclerView recyclerView;
    private CustomProgressDialog customProgressDialog;
    private ImageView imvBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imvBg = (ImageView)findViewById(R.id.imv_bg);
        url = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + PLAYLIST_ID + "&maxResults=" + 50 + "&key=" + "AIzaSyCFCWKR5iGgfZtRS8w8qzEfmruKa616uaI";
        makeJsonObjectRequest();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
            customProgressDialog = null;
        }
    }

    public void getData2() {

//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, this.createRequestSuccessListener(), this.createRequestErrorListener());
//
//        requestQueue.add(jsObjRequest);


    }

    /**
     * Method to make json object request where json response starts wtih {
     */
    private String jsonResponse;

    private void makeJsonObjectRequest() {

        customProgressDialog = new CustomProgressDialog(this);
        // Showing progress dialog before making http request
        customProgressDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("kind");
                    String email = response.getString("etag");
//                    String nextPageToken = response.getString("nextPageToken");

                    JSONObject phone = response.getJSONObject("pageInfo");
                    int totalResults = phone.getInt("totalResults");
                    int resultsPerPage = phone.getInt("resultsPerPage");

                    JSONArray items = response.getJSONArray("items");

                    for (int i = 0; i < items.length(); i++) {
                        Youtube youtube = new Youtube();
                        JSONObject item = items.getJSONObject(i);
                        JSONObject snippet = item.getJSONObject("snippet");

                        if (snippet.has("thumbnails")) {
                            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                            Log.d("hailpt ======", "Item = " + snippet.getString("title"));

                            JSONObject resourceId = snippet.getJSONObject("resourceId");
                            String publishedAt = snippet.getString("publishedAt");

                            Log.d("hailpt ======", "Item = " + snippet.getString("title"));

                            youtube.setThumbnail(thumbnails.getJSONObject("high").getString("url"));
                            //youtube.setThumbnail("https://i.ytimg.com/vi/21vhYPLErlY/sddefault.jpg");
                            youtube.setVideoId(resourceId.getString("videoId"));
                            youtube.setTitle(snippet.getString("title"));
                            youtube.setPublishedAt(publishedAt);
                            youtubeList.add(youtube);
                        }else{
                            Log.d("hailpt ======", " have no thubmail .....................");
                        }
                    }

                    Log.d("hailpt ======", "SIZEEEEEEEEEEEEE = " + youtubeList.size());
                    updateLayout();

                    jsonResponse = totalResults + " === " + resultsPerPage;
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";

//                    Toast.makeText(getApplicationContext(),
//                            jsonResponse,
//                            Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    // Error here
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: +++++++++++ " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidePDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: ============== " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void updateLayout() {
        imvBg.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        likeProgramAdapter = new VlogListAdapter(this, youtubeList, new VlogListAdapter.OnRemoveCallBack() {
            @Override
            public void remove(final int pos) {

            }

            @Override
            public void onItemClick(int pos) {
                Intent intent = new Intent(MainActivity.this,VlogDetailActivity.class);
                intent.putExtra("VIDEO_ID", youtubeList.get(pos).getVideoId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        recyclerView.setAdapter(likeProgramAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
