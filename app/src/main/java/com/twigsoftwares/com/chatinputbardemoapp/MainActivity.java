package com.twigsoftwares.com.chatinputbardemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.twigsoftwares.com.chatinputbar.ChatInputBar;

import utils.Utility;

public class MainActivity extends AppCompatActivity implements
        ChatInputBar.InputListener,
        ChatInputBar.AllAngleButtonClickListener,
        ChatInputBar.EmojiKeyboardOpenCloseListener,
        ChatInputBar.RecordingListener,
        ChatInputBar.TypingListener {

    com.twigsoftwares.com.chatinputbar.ChatInputBar mchatInputBar;

    RelativeLayout mrootView;

    Button requestPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission = (Button) findViewById(R.id.permission);
        mrootView = (RelativeLayout) findViewById(R.id.activity_main);
        mchatInputBar = findViewById(R.id.chat_input_bar_comp);
        mchatInputBar.setRootLayout(this, mrootView);

        int[] drawable = {R.drawable.plus, R.drawable.place, R.drawable.gallery, R.drawable.camera};

        int[] color = {R.color.orange, R.color.red, R.color.green, R.color.blue};

        mchatInputBar.setAttachmentButtonData(MainActivity.this, drawable, color, 15, 12);

        //EmojiconEditText emojiEditText =  mchatInputBar.getInputEditText();

        mchatInputBar.setInputListener(this);

        mchatInputBar.setAllAngleButtonClickListener(this);

        mchatInputBar.setEmojiKeyboardOpenCloseListener(this);

        mchatInputBar.setRecordingListener(this);

        mchatInputBar.setTypingListener(this);

        requestPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utility.checkAndRequestPermissions(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onSubmit(CharSequence input) {

        return true;
    }

    @Override
    public void onLocationClicked() {

        Toast.makeText(this, "Location Clicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onImageClicked() {

        Toast.makeText(this, "Image Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraClicked() {
        Toast.makeText(this, "Camera Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExpand() {
        Toast.makeText(this, "Expanded Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCollapse() {
        Toast.makeText(this, "Collapsed Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmojiKeyboardOpen() {
        Toast.makeText(this, "Emoji Keyboard opened", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onEmojiKeyboardClosed() {
        Toast.makeText(this, "Emoji Keyboard closed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStarted() {


    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onInvalid(boolean isInvalid) {

    }

    @Override
    public void onCompleted(String FileName) {
        Toast.makeText(this, FileName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTyping() {
        Toast.makeText(this, "Typing Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTyping() {
        Toast.makeText(this, "Typing Ended", Toast.LENGTH_SHORT).show();
    }
}
