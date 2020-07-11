package com.opus_bd.myapplication.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.opus_bd.myapplication.Activity.Call.PlaceCallActivity;
import com.opus_bd.myapplication.Activity.Call.SinchService;
import com.opus_bd.myapplication.Activity.VoiceCall.Apps;
import com.opus_bd.myapplication.Activity.VoiceCall.IncommingCallActivity;
import com.opus_bd.myapplication.Activity.VoiceCall.SinchStatus;
import com.opus_bd.myapplication.Adapter.ChatAdapter;
import com.opus_bd.myapplication.Model.IndividualChatModel;
import com.opus_bd.myapplication.R;
import com.opus_bd.myapplication.Utils.Constants;
import com.opus_bd.myapplication.Utils.SharedPrefManager;
import com.sinch.android.rtc.calling.Call;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.rvChats)
    RecyclerView rvChats; @BindView(R.id.ivEmoji)
    ImageView ivEmoji;
    @BindView(R.id.etMessage)
    EditText etMessage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvProfileName)
    TextView tvProfileName;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.civProfileImage)
    CircleImageView civProfileImage;
    @BindView(R.id.fabSent)
    FloatingActionButton fabSent;
    private ChatAdapter chatAdapter;
    ArrayList<IndividualChatModel> individualChatModels = new ArrayList<>();
    public static final String EXTRA_RECEIVER_ID = "extra_receiver_id";
    public static final String EXTRA_RECEIVER_NAME = "extra_receiver_NAME";
    public static final String EXTRA_RECEIVER_PHOTO = "extra_receiver_Photo";
    int receiverId;
    String name, photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initToolbar();

        rootLayout.setBackground(getResources().getDrawable(R.drawable.img_chat_background));
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            receiverId = bundle.getInt(EXTRA_RECEIVER_ID);
            name = bundle.getString(EXTRA_RECEIVER_NAME);
            photo = bundle.getString(EXTRA_RECEIVER_PHOTO);
        }

        Log.i("dfdw5242166", Apps.USER_ID);
        Log.i("dfdw5242288", String.valueOf(Apps.sinchClient.isStarted()));

        /*startService(new Intent(this, SinchService.class));*/

        startService(new Intent(this, SinchService.class));

        if(Apps.callClient!=null&&Apps.sinchClient.isStarted()){
            Log.i("dfdw524219900", "Client Connected, ready to use!");
        }


        tvProfileName.setText(name);

        try {
            Glide.with(this)
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.drawable.ic_person)
                            .error(R.drawable.ic_person))
                    .load(Constants.BASE_URL + photo)
                    .into(civProfileImage);
        } catch (Exception e) {
        }
        initRecyclerView();

    }

    private void initToolbar() {
        //toolbar.setNavigationIcon(R.drawable.app_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Tools.setSystemBarColor(this);
    }

    private void initRecyclerView() {
        showProgressBar(false);
        chatAdapter = new ChatAdapter(individualChatModels, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        rvChats.setLayoutManager(layoutManager);
        rvChats.setHasFixedSize(true);
        rvChats.setAdapter(chatAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.MESSAGE);

        myRef.child(getChatKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                individualChatModels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    showProgressBar(false);

                    IndividualChatModel individualChatModel = snapshot.getValue(IndividualChatModel.class);
                    if (individualChatModel != null)
                        individualChatModels.add(individualChatModel);
                }
                chatAdapter = new ChatAdapter(individualChatModels, ChatActivity.this);
                rvChats.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(ChatActivity.this, "Could not get data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.fabSent)
    public void sendBtnOnClick() {

        if (TextUtils.isEmpty(etMessage.getText().toString()))
            return;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Constants.MESSAGE);

        IndividualChatModel individualChatModel = new IndividualChatModel();
        individualChatModel.setSenderID(Integer.parseInt(SharedPrefManager.getInstance(this).getUserID()));
        individualChatModel.setReceiverID(receiverId);
        individualChatModel.setMessage(etMessage.getText().toString());

        String key = getChatKey();
        myRef.child(key).push().setValue(individualChatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etMessage.setText("");
            }
        });

    }

    private String getChatKey() {
        String chatKey = SharedPrefManager.getInstance(this).getUserID() + "_" + receiverId;
        if (receiverId < Integer.parseInt(SharedPrefManager.getInstance(this).getUserID()))
            chatKey = receiverId + "_" + SharedPrefManager.getInstance(this).getUserID();

        return chatKey;
    }

    private void showProgressBar(boolean visible) {

        if (visible) {
            progressbar.setVisibility(View.VISIBLE);
            rootLayout.setAlpha(Constants.PROGRESSBAR_ALPHA);

        } else {
            progressbar.setVisibility(View.GONE);
            rootLayout.setAlpha(Constants.PROGRESSBAR_AFTER_FINISH_ALPHA);
        }
    }

    public void CallONCLick() {
        Intent intent = new Intent(this, PlaceCallActivity.class);
        PlaceCallActivity.TARGET_CALL_ID = String.valueOf(receiverId);

/*
            intent.putExtra("" + sessionManager.getUserName(), "DOCTOR_NAME");
            intent.putExtra("" + partner_name, "PATIENT_NAME");
            intent.putExtra("" + partner_photo, "USER_PHOTO");*/
        // intent.putExtra("" + 61442, "targerUser");
        this.startActivity(intent);


    }

    @OnClick(R.id.ivAttachment)
    public void FileAttached() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select files"), 1);
    }
    @OnClick(R.id.ivEmoji)
    public void EmojiAttached() {

        /*ivEmoji.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {

            }
            @Override
            public void onKeyboardClose() {
            }
        });*/
    }

    @OnClick(R.id.ivCamera)
    public void ImageAttached() {
        Options options = Options.init()
                .setRequestCode(100)                                           //Request code for activity results
                .setCount(10)                                                   //Number of images to restict selection count
                .setFrontfacing(false)                                         //Front Facing camera on start
              //  .setPreSelectedUrls(returnValue)                               //Pre selected Image Urls
                .setExcludeVideos(false)                                       //Option to exclude videos
                .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/pix/images");                                       //Custom Path For media Storage

        Pix.start(ChatActivity.this, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(ChatActivity.this, Options.init().setRequestCode(100));
                } else {
                    Toast.makeText(ChatActivity.this, "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemCall:
                if(Apps.callClient==null){
                    Log.i("df335353535","Sinch Client not connected");
                }
                customDialog();
                return true;
            case R.id.itemVideo:
                CallONCLick();
                return true;
            case R.id.itemSetting:
                Toast.makeText(getApplicationContext(), "Setting",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemNewGroup:
                /*Toast.makeText(getApplicationContext(), "New Group", Toast.LENGTH_SHORT).show();*/
                myID();
                return true;
            case R.id.itemLogOut:
                Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void myID() {

        final Dialog dialog = new Dialog(ChatActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_input_patner);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        //wlp.horizontalWeight=3;
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        TextView etPartnerID = (TextView) dialog.findViewById(R.id.input_partner_id);
        etPartnerID.setText(Apps.USER_ID);
        Button btnPartnerID = (Button) dialog.findViewById(R.id.btnPatner);
        btnPartnerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void customDialog() {

        final Dialog dialog = new Dialog(ChatActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_input_patner_id);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        //wlp.horizontalWeight=3;
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        EditText etPartnerID = (EditText) dialog.findViewById(R.id.input_partner_id);
        Button btnPartnerID = (Button) dialog.findViewById(R.id.btnPatner);
        btnPartnerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPartnerID.getText().toString().isEmpty()){
                    etPartnerID.setError("ID not valid");
                    return;
                }
                else {
                    dialog.dismiss();
                    Call currentcall = Apps.callClient.callUser(etPartnerID.getText().toString());
                    Intent callscreen = new Intent(ChatActivity.this, IncommingCallActivity.class);
                    callscreen.putExtra("callid", currentcall.getCallId());
                    callscreen.putExtra("incomming", false);
                    callscreen.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callscreen);
                }
            }
        });

        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSinchConnected(SinchStatus.SinchConnected sinchConnected){
        Log.i("Status111","* CONNECTED :)\n---------------------------\n");

        /*mMainCallbtn.setEnabled(true);*/
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSinchDisconnected(SinchStatus.SinchDisconnected sinchDisconnected){

        Log.i("Status2222","* DISCONNECTED\n---------------------------\n");

        /*mMainStatus.append(String.format("* DISCONNECTED\n---------------------------\n"));
        mMainCallbtn.setEnabled(false);*/
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSinchFailed(SinchStatus.SinchFailed sinchFailed){
        Log.i("Status3333","* CONNECTION FAILED: %s\n---------------------------\n"+sinchFailed.error.getMessage());

        /*mMainStatus.append(String.format("* CONNECTION FAILED: %s\n---------------------------\n", sinchFailed.error.getMessage()));
        mMainCallbtn.setEnabled(false);*/
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSinchLogging(SinchStatus.SinchLogger sinchLogger){

        Log.i("Status44","* %s ** %s ** %s\n---------------------------\n"+ " " +sinchLogger.message + " "+ sinchLogger.area +" "+ sinchLogger.level);

        /*mMainStatus.append(String.format("* %s ** %s ** %s\n---------------------------\n", sinchLogger.message, sinchLogger.area, sinchLogger.level));*/
    }
}
