# Chat Inputbar Demo App
attachment emojis and voice recording facility in one component


to use this component , just add chatinpur bar in your activity/view where you want user to input text,image,gallery image location etc and 


<img src="https://github.com/tusharuit25/ChatInputbarDemoApp/blob/master/app/src/main/res/drawable/Emoji.png" width="250" /> 
<img src="https://github.com/tusharuit25/ChatInputbarDemoApp/blob/master/app/src/main/res/drawable/Record_snap.png" width="250"/> 
<img src="https://github.com/tusharuit25/ChatInputbarDemoApp/blob/master/app/src/main/res/drawable/attachment.png" width="250"/> 


set the permission in android manifest .

`<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />`


in your Activity layout XML File 

 `<com.twigsoftwares.com.chatinputbar.ChatInputBar
        android:id="@+id/chat_input_bar_comp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.twigsoftwares.com.chatinputbar.ChatInputBar>`
    
in your Activity 

`mchatInputBar = findViewById(R.id.chat_input_bar_comp);`

you can implement various event depending upon your requirement like
`@Override
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
}`
    
    
Enjoy the Component and do whatever you wanted to do man.

Also have a look at MainActivity.java for complete understanding & asking permission at runtime is important prerequestite of this library

Please keep lincenses and other compliences for below libraries for commercial use

`compile 'com.github.uin3566:AllAngleExpandableButton:v1.2.0'
compile 'com.devlomi.record-view:record-view:1.1beta'
compile 'com.github.hani-momanii:SuperNova-Emoji:1.1'`


https://www.buymeacoffee.com/tusharit25
