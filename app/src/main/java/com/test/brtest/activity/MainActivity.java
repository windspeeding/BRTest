package com.test.brtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.brtest.BrTestApp;
import com.test.brtest.Constants;
import com.test.brtest.R;
import com.test.brtest.adapter.StoreListAdapter;
import com.test.brtest.model.Store;
import com.test.brtest.model.Stores;
import com.test.brtest.rest.RestInterface;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private List<Store> storeList;
    private ListView listView;
    private FloatingActionButton fab;
    private StoreListAdapter listAdapter;
    private TextView emptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        restCall();
    }

    private void restCall() {
        RestInterface apiService = BrTestApp.getRestClient().getApiService();
        Call<Stores> stores = apiService.stores();
        stores.enqueue(new Callback<Stores>() {
            @Override
            public void onResponse(Call<Stores> call, Response<Stores> response) {
                if(response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this,R.string.update_success,Toast.LENGTH_SHORT).show();
                    Stores stores = response.body();
                    storeList = stores.getStores();
                    fab.setVisibility(View.VISIBLE);
                    listAdapter = new StoreListAdapter(MainActivity.this, storeList);
                    listView.setAdapter(listAdapter);
                    if (listView.getVisibility()==View.INVISIBLE){
                        listView.setVisibility(View.VISIBLE);
                        emptyMessage.setVisibility(View.INVISIBLE);
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors
                    ResponseBody errorBody = response.errorBody();
                    Toast.makeText(MainActivity.this,errorBody.toString(),Toast.LENGTH_SHORT).show();
                    fab.setVisibility(View.VISIBLE);
                    emptyMessage.setText(errorBody.toString());
                    listView.setVisibility(View.INVISIBLE);
                    emptyMessage.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Stores> call, Throwable t) {
                // handle execution failures link no internet connectivity
                fab.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                emptyMessage.setVisibility(View.VISIBLE);

                Log.d("FAILURE","Failure");

            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.activity_main);
        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // FAB
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                fab.setVisibility(View.INVISIBLE);
                restCall();
            }
        });
        // EmptyMessage
        emptyMessage = (TextView) findViewById(R.id.empty_message);
        // ListView
        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,DetailActivity.class).putExtra(Constants.TAG_STORE,storeList.get(position)));
            }
        });

    }
}
