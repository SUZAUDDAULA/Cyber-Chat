package com.opus_bd.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Adapter.MemberListAdapter;
import com.opus_bd.myapplication.Model.User.UserListModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
@BindView(R.id.rvUserList)
RecyclerView rvUserList;

    MemberListAdapter memberListAdapter;
    ArrayList<UserListModel> UserListModel = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intRecyclerView();
        getAllUser();
    }

    public void intRecyclerView() {
        memberListAdapter = new MemberListAdapter(UserListModel, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvUserList.setLayoutManager(layoutManager);
        rvUserList.setAdapter(memberListAdapter);
    }



    private void getAllUser() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        int id= Integer.parseInt(SharedPrefManager.getInstance(this).getUserID());
   
            Call<List<UserListModel>> registrationRequest = retrofitService.GetEmployeeInfoExceptMe(id);
            registrationRequest.enqueue(new Callback<List<UserListModel>>() {
                @Override
                public void onResponse(Call<List<UserListModel>> call, @NonNull Response<List<UserListModel>> response) {
                    try {
                        if (response.body() != null) {
                            UserListModel.addAll(response.body());
                        }

                        memberListAdapter.notifyDataSetChanged();
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<List<UserListModel>> call, Throwable t) {
                }
            });

    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                memberListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                memberListAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }*/

}
