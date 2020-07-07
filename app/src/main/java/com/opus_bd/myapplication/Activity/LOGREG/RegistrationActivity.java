package com.opus_bd.myapplication.Activity.LOGREG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.opus_bd.myapplication.APIClient.RetrofitClientInstance;
import com.opus_bd.myapplication.APIClient.RetrofitService;
import com.opus_bd.myapplication.Activity.LoginActivity;
import com.opus_bd.myapplication.Adapter.CustomAdapter;
import com.opus_bd.myapplication.Model.User.DesignationModel;
import com.opus_bd.myapplication.Model.User.RegisterModel;
import com.opus_bd.myapplication.Model.User.SubUnitsModel;
import com.opus_bd.myapplication.Model.User.UnitModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.opus_bd.myapplication.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.spnUnit)
    Spinner spnUnit;
    @BindView(R.id.spnSubUnit)
    Spinner spnSubUnit;
    @BindView(R.id.spnDesignation)
    Spinner spnDesignation;

    @BindView(R.id.etNAME)
    EditText etNAME;
    @BindView(R.id.etBPNo)
    EditText etBPNo;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPhn)
    EditText etPhn;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassWord)
    EditText etConfirmPassWord;


    ArrayList<UnitModel> unitModelArrayList = new ArrayList<>();
    ArrayList<SubUnitsModel> subUnitsModelArrayList = new ArrayList<>();
    ArrayList<DesignationModel> designationModelArrayList = new ArrayList<>();

    public Integer SELECTED_UNIT_ID,SELECTED_SUB_UNIT_ID;
    String selectOne,SELECTED_DESIGNATION_NAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        selectOne = getResources().getString(R.string.select_option);

        getAllSubUnit();
        getAllUnit();
        getAllDesignation();

    }


    public void getAllUnit() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<List<UnitModel>> listCall = retrofitService.GetAllUnits();
        listCall.enqueue(new Callback<List<UnitModel>>() {
            @Override
            public void onResponse(Call<List<UnitModel>> call, Response<List<UnitModel>> response) {

                if (response.body() != null) {

                    unitModelArrayList.clear();
                    unitModelArrayList.addAll(response.body());

                    addAllUnitSpinnerData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<UnitModel>> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addAllUnitSpinnerData(final List<UnitModel> body) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(0, selectOne);
        for (int i = 0; i < body.size(); i++) {
            arrayList.add(i + 1, body.get(i).getBranchUnitName());
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        spnUnit.setAdapter(customAdapter);
        spnUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i >= 1) {
                        SELECTED_UNIT_ID = body.get(i - 1).getId();
                    } else {
                        SELECTED_UNIT_ID = null;
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage(" " + e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAllSubUnit() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<List<SubUnitsModel>> listCall = retrofitService.GetAllSubUnits();
        listCall.enqueue(new Callback<List<SubUnitsModel>>() {
            @Override
            public void onResponse(Call<List<SubUnitsModel>> call, Response<List<SubUnitsModel>> response) {

                if (response.body() != null) {

                    subUnitsModelArrayList.clear();
                    subUnitsModelArrayList.addAll(response.body());

                    addAllSubUnitSpinnerData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SubUnitsModel>> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addAllSubUnitSpinnerData(final List<SubUnitsModel> body) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(0, selectOne);
        for (int i = 0; i < body.size(); i++) {
            arrayList.add(i + 1, body.get(i).getName());
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        spnSubUnit.setAdapter(customAdapter);
        spnSubUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i >= 1) {
                        SELECTED_SUB_UNIT_ID = body.get(i - 1).getId();
                    } else {
                        SELECTED_SUB_UNIT_ID = null;
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage(" " + e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getAllDesignation() {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<List<DesignationModel>> listCall = retrofitService.GetAllDesignationForCID();
        listCall.enqueue(new Callback<List<DesignationModel>>() {
            @Override
            public void onResponse(Call<List<DesignationModel>> call, Response<List<DesignationModel>> response) {

                if (response.body() != null) {

                    designationModelArrayList.clear();
                    designationModelArrayList.addAll(response.body());

                    addAllDesignationSpinnerData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DesignationModel>> call, Throwable t) {
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addAllDesignationSpinnerData(final List<DesignationModel> body) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add(0, selectOne);
        for (int i = 0; i < body.size(); i++) {
            arrayList.add(i + 1, body.get(i).getDesignationName());
        }

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        spnDesignation.setAdapter(customAdapter);
        spnDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i >= 1) {
                        SELECTED_DESIGNATION_NAME = body.get(i - 1).getDesignationName();
                    } else {
                        SELECTED_DESIGNATION_NAME = "";
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage(" " + e.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @OnClick(R.id.btnNext1)
    public void submitToServer() {

        final RegisterModel registrationModel = new RegisterModel();

        registrationModel.setBPNumber(etBPNo.getText().toString());
        registrationModel.setName(etNAME.getText().toString());
        registrationModel.setDesignation(SELECTED_DESIGNATION_NAME);
        registrationModel.setEmail(etEmail.getText().toString());
        registrationModel.setUnitId(SELECTED_UNIT_ID);
        registrationModel.setSubUnitId(SELECTED_SUB_UNIT_ID);
        registrationModel.setMobile(etEmail.getText().toString());
        registrationModel.setPassword(etPassword.getText().toString());
        registrationModel.setConfirmPassword(etConfirmPassWord.getText().toString());
        //Utilities.showLogcatMessage("RegistrationModel " + registrationModel.toString());

        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        Call<String> registrationRequest = retrofitService.CChatRegister(registrationModel);
        registrationRequest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.body() != null) {
                        try {
                            Intent intent = new Intent(RegistrationActivity.this, RegisterSuccessActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Utilities.showLogcatMessage("Exception 1" + e.toString());
                            Toast.makeText(RegistrationActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(RegistrationActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Utilities.showLogcatMessage("Exception 2" + e.toString());
                    Toast.makeText(RegistrationActivity.this, "Something went Wrong! Please try again later", Toast.LENGTH_SHORT).show();
                }
                //            showProgressBar(false);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Utilities.showLogcatMessage("Fail to connect " + t.toString());
                // Utilities.hideProgress(LoginActivity.this);
                Toast.makeText(RegistrationActivity.this, "Fail to connect " + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}