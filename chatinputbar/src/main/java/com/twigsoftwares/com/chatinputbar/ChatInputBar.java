package com.twigsoftwares.com.chatinputbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devlomi.record_view.OnRecordListener;
import com.devlomi.record_view.RecordButton;
import com.devlomi.record_view.RecordView;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by Tushar on 29/12/17.
 */


@SuppressWarnings({"WeakerAccess", "unused"})
public class ChatInputBar extends RelativeLayout
        implements View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

    // inner views
    private EmojIconActions emojIcon;
    protected EmojiconEditText messageInput;
    protected AllAngleExpandableButton expandableButton;
    protected ImageButton messageSendButton;
    protected ImageView emojiButton;
    protected RecordButton recordButton;
    protected RecordView recordView;

    private ChatInputBarStyle chatInputBarStyle;
    // view which will be adjusted all the time
    protected RelativeLayout middleLayout;

    // rootview for emoji reference
    private RelativeLayout rootView;

    //input
    private CharSequence input;

    // all listeners
    private InputListener inputListener;
    private AllAngleButtonClickListener allAngleButtonClickListener;
    private EmojiKeyboardOpenCloseListener emojiKeyboardOpenCloseListener;
    private RecordingListener recordingListener;


    //recording Functionality variables
    //Sound recorder
    private MediaRecorder mRecorder;
    // start timing
    private long mStartTime = 0;

    // amplitude
    private int[] amplitudes = new int[100];
    //ticking
    private int i = 0;

    // seperate thred for recording sound
    private Handler mHandler = new Handler();
    //actual recorder thread
    private Runnable mTickExecutor = new Runnable() {
        @Override
        public void run() {
            tick();
            mHandler.postDelayed(mTickExecutor, 100);
        }
    };

    // output file after recording
    private File mOutputFile;

    //typing listeners

    private boolean isTyping;
    private int delayTypingStatusMillis;
    private TypingListener typingListener;

    private Runnable typingTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isTyping) {
                isTyping = false;
                if (typingListener != null) typingListener.onStopTyping();
            }
        }
    };

    private boolean lastFocus;


    // component constructors
    public ChatInputBar(Context context) {
        super(context);
        init(context);
    }

    // 2
    public ChatInputBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    //3
    public ChatInputBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }


    public void setRootLayout(Activity activity, RelativeLayout rootView) {

        this.rootView = rootView;

        // emoji functionality
        emojIcon = new EmojIconActions(activity, this.rootView, messageInput, emojiButton);

        // show icon
        emojIcon.ShowEmojIcon();

        // set toogeling drawables
        emojIcon.setIconsIds(R.drawable.keyboard, R.drawable.smiley);

        //set key board opening closing listeners
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
                emojiKeyboardOpenCloseListener.onEmojiKeyboardOpen();
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
                emojiKeyboardOpenCloseListener.onEmojiKeyboardClosed();
            }
        });
    }

    /**
     * Sets callback for 'submit' button.
     *
     * @param inputListener input callback
     */
    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    /**
     * Sets callback for 'attachment' button.
     *
     * @param allAngleButtonClickListener attachment callback
     */
    public void setAllAngleButtonClickListener(AllAngleButtonClickListener allAngleButtonClickListener) {
        this.allAngleButtonClickListener = allAngleButtonClickListener;
    }

    /**
     * Sets callback for 'emoji' button.
     *
     * @param emojiKeyboardOpenCloseListener emoji clicked callback
     */
    public void setEmojiKeyboardOpenCloseListener(EmojiKeyboardOpenCloseListener emojiKeyboardOpenCloseListener) {
        this.emojiKeyboardOpenCloseListener = emojiKeyboardOpenCloseListener;
    }

    /**
     * Sets callback for 'record' button.
     *
     * @param recordingListener record callback
     */
    public void setRecordingListener(RecordingListener recordingListener) {
        this.recordingListener = recordingListener;
    }

    /**
     * Sets callback for 'typing' in typebox.
     *
     * @param typingListener typing callback
     */

    public void setTypingListener(TypingListener typingListener) {
        this.typingListener = typingListener;
    }

    /**
     * Returns EditText for messages input
     *
     * @return EditText
     */
    public EmojiconEditText getInputEditText() {
        return messageInput;
    }

    // really not required
    public ImageButton getButton() {
        return messageSendButton;
    }

    // really not required
    public RecordButton getRecordButton() {
        return recordButton;
    }

    // really not required
    public AllAngleExpandableButton getAttachmentButton() {
        return expandableButton;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        input = charSequence;
        messageSendButton.setEnabled(input.length() > 0);


        if (input.length() > 0) {
            recordButton.animate()
                    .translationY(recordButton.getHeight() - recordButton.getHeight())
                    .alpha(0.0f)
                    .setDuration(50)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            recordButton.setVisibility(View.GONE);
                            messageSendButton.setVisibility(View.VISIBLE);
                            expandableButton.setVisibility(View.GONE);
                            LayoutParams relativeParams = new LayoutParams(
                                    new LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT));
                            int right_margin = (int) getContext().getResources().getDimension(R.dimen.input_right_margin);
                            relativeParams.setMargins(0, 0, right_margin, 0);
                            middleLayout.setLayoutParams(relativeParams);
                            middleLayout.requestLayout();

                        }
                    });

        } else {

            messageSendButton.animate()
                    .translationY(messageSendButton.getHeight() - messageSendButton.getHeight())
                    .alpha(0.0f)
                    .setDuration(50)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            messageSendButton.setVisibility(View.GONE);
                            recordButton.setVisibility(View.VISIBLE);
                            expandableButton.setVisibility(View.VISIBLE);
                            LayoutParams relativeParams = new LayoutParams(
                                    new LayoutParams(
                                            LayoutParams.MATCH_PARENT,
                                            LayoutParams.WRAP_CONTENT));

                            int right_margin = (int) getContext().getResources().getDimension(R.dimen.input_right_margin);
                            int left_margin = (int) getContext().getResources().getDimension(R.dimen.input_left_margin);

                            relativeParams.setMargins(left_margin, 0, right_margin, 0);
                            middleLayout.setLayoutParams(relativeParams);
                            middleLayout.requestLayout();
                        }
                    });
        }


        if (!isTyping) {
            isTyping = true;
            if (typingListener != null) typingListener.onStartTyping();
        }

        removeCallbacks(typingTimerRunnable);
        postDelayed(typingTimerRunnable, delayTypingStatusMillis);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (lastFocus && !hasFocus && typingListener != null) {
            typingListener.onStopTyping();
        }
        lastFocus = hasFocus;
    }

    // event on send button clicked
    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.sendButton) {
            boolean isSubmitted = onSubmit();

            if (isSubmitted) {
                //setting text null after submitting the te
                messageInput.setText("");

            }
            removeCallbacks(typingTimerRunnable);
            post(typingTimerRunnable);
        }


    }

    // event on submit the button

    private boolean onSubmit() {

        return inputListener != null && inputListener.onSubmit(input);

    }


    // send attachment button data
    public void setAttachmentButtonData(Context context, int[] drawable, int[] color, int mainButtonPadding, int otherButtonsPadding) {

        final List<ButtonData> buttonDatas = new ArrayList<>();

        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData;
            if (i == 0) {
                buttonData = ButtonData.buildIconButton(context, drawable[i], mainButtonPadding);
                buttonData.setBackgroundColorId(context, color[i]);
            } else {
                buttonData = ButtonData.buildIconButton(context, drawable[i], otherButtonsPadding);
                buttonData.setBackgroundColorId(context, color[i]);
            }

            buttonDatas.add(buttonData);
        }
        expandableButton.setButtonDatas(buttonDatas);
    }

    private void init(final Context context, AttributeSet attrs) {

        init(context);

        ChatInputBarStyle style = ChatInputBarStyle.parse(context, attrs);
        this.setStyle(style);

        messageSendButton.setOnClickListener(this);
        expandableButton.setOnClickListener(this);
        messageInput.addTextChangedListener(this);
        messageInput.setText("");


        //record button
        recordButton.setRecordView(recordView);

        // length of swipe to cancel the recording
        recordView.setCancelBounds(130);

        // mic color which will be shown while recording
        recordView.setSmallMicColor(Color.parseColor("#c2185b"));

        //prevent recording under one Second
        recordView.setLessThanSecondAllowed(false);

        // cancelling text
        recordView.setSlideToCancelText("Slide To Cancel");

        // start recording and end recording colors.
        recordView.setCustomSounds(R.raw.record_start, R.raw.record_finished, 0);

        // recording event listener
        recordView.setOnRecordListener(new OnRecordListener() {
            @Override
            public void onStart() {

                //make recording view visible
                recordView.setVisibility(View.VISIBLE);
                // start recording
                startRecording();
                // inform to the user that recording inside this component is started successfully
                recordingListener.onStarted();

            }

            @Override
            public void onCancel() {
                // make record view invisible
                recordView.setVisibility(View.GONE);

                // make recorder object null and stop the recoding

                if (mRecorder != null) {

                    // stopped
                    stopRecording(false);
                }
                // inform to user that recording has been cancelled.
                recordingListener.onCancelled();

            }

            @Override
            public void onFinish(long recordTime) {

                // show user the time of record.
                String time = getHumanTimeText(recordTime);

                // set record view visibility to gone as recording is finished
                recordView.setVisibility(View.GONE);
                // release the recorder and save the file
                if (mRecorder != null) {
                    // here 'true' means save the file which has been recorded
                    stopRecording(true);

                }
                // inform the user that recording is finished and file location which is saved after recording;
                recordingListener.onCompleted(mOutputFile.getAbsolutePath());

            }

            @Override
            public void onLessThanSecond() {
                // if record buttn is hold for less that second invalidate the recordided file.
                recordView.setVisibility(View.GONE);
                //infor the user about less that one second recording.
                recordingListener.onInvalid(false);
            }
        });


    }

    // function returnd human understandable time format.
    private String getHumanTimeText(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    private void init(Context context) {

        inflate(context, R.layout.chat_input_bar, this);


        // input text which is enabled for emoji's

        messageInput = (EmojiconEditText) findViewById(R.id.messageinput);

        // for send button
        messageSendButton = (ImageButton) findViewById(R.id.sendButton);

        // for attachment
        expandableButton = (AllAngleExpandableButton) findViewById(R.id.button_expandable);

        // for emoji buton switchng between text keyboard and emoji keyboard

        emojiButton = (ImageView) findViewById(R.id.emojibutton);

        // record view
        recordView = (RecordView) findViewById(R.id.record_view);

        // recording button
        recordButton = (RecordButton) findViewById(R.id.record_button);

        // layout which adust itself depending on the which view is visible and which one is disabled.

        middleLayout = (RelativeLayout) findViewById(R.id.middlelayout);


        //exapnadble button events which will be listened for attachment which used this views.

        expandableButton.setButtonEventListener(new ButtonEventListener() {

            @Override
            public void onButtonClicked(int index) {

                //do whatever you want,the param index is counted from startAngle to endAngle,
                //the value is from 1 to buttonCount - 1(buttonCount if aebIsSelectionMode=true)
                //dismissKeyboard();
                if (index == 1) {
                    //fire event if user clinked on first button like wise other indices
                    allAngleButtonClickListener.onLocationClicked();

                } else if (index == 2) {

                    allAngleButtonClickListener.onImageClicked();

                } else if (index == 3) {

                    allAngleButtonClickListener.onCameraClicked();

                }

            }

            @Override
            public void onExpand() {

                allAngleButtonClickListener.onExpand();
            }

            @Override
            public void onCollapse() {

                allAngleButtonClickListener.onCollapse();
            }
        });

        messageInput.addTextChangedListener(this);

        messageInput.setText("");

        messageInput.setOnFocusChangeListener(this);


    }

    public ChatInputBarStyle getStyle() {
        return this.chatInputBarStyle;
    }

    public void setStyle(ChatInputBarStyle chatInputBarStyle) {
        this.delayTypingStatusMillis = chatInputBarStyle.getDelayTypingStatus();
    }

    //unused

    private void setCursor(Drawable drawable) {
        if (drawable == null) return;

        try {
            final Field drawableResField = TextView.class.getDeclaredField("mCursorDrawableRes");

            drawableResField.setAccessible(true);

            final Object drawableFieldOwner;

            final Class<?> drawableFieldClass;

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {

                drawableFieldOwner = this.messageInput;

                drawableFieldClass = TextView.class;

            } else {
                final Field editorField = TextView.class.getDeclaredField("mEditor");

                editorField.setAccessible(true);

                drawableFieldOwner = editorField.get(this.messageInput);

                drawableFieldClass = drawableFieldOwner.getClass();

            }

            final Field drawableField = drawableFieldClass.getDeclaredField("mCursorDrawable");

            drawableField.setAccessible(true);

            drawableField.set(drawableFieldOwner, new Drawable[]{drawable, drawable});

        } catch (Exception ignored) {

        }
    }

    private void startRecording() {
        try {

            mRecorder = new MediaRecorder();

            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);

                mRecorder.setAudioEncodingBitRate(48000);

            } else {
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

                mRecorder.setAudioEncodingBitRate(64000);
            }

            mRecorder.setAudioSamplingRate(16000);

            mOutputFile = getOutputFile();

            mOutputFile.getParentFile().mkdirs();

            mRecorder.setOutputFile(mOutputFile.getAbsolutePath());

            try {
                mRecorder.prepare();

                mRecorder.start();

                mStartTime = SystemClock.elapsedRealtime();

                mHandler.postDelayed(mTickExecutor, 100);

                Log.d("Voice Recorder", "started recording to " + mOutputFile.getAbsolutePath());
            } catch (IOException e) {
                Log.e("Voice Recorder", "prepare() failed " + e.getMessage());
            }


        } catch (Exception ex) {
            //Toast.makeText(con, "Problem:" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void stopRecording(boolean saveFile) {
        try {
            mRecorder.stop();

            mRecorder.release();

            mRecorder = null;

            mStartTime = 0;

            mHandler.removeCallbacks(mTickExecutor);

            if (!saveFile && mOutputFile != null) {

                mOutputFile.delete();

            }

        } catch (Exception ex) {

        }

    }

    private File getOutputFile() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.US);

        return new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Voice Recorder/RECORDING_" + dateFormat.format(new Date()) + ".m4a");
    }

    private void tick() {

        long time = (mStartTime < 0) ? 0 : (SystemClock.elapsedRealtime() - mStartTime);

        int minutes = (int) (time / 60000);

        int seconds = (int) (time / 1000) % 60;

        int milliseconds = (int) (time / 100) % 10;

        getHumanTimeText(milliseconds);

        //mTimerTextView.setText(minutes+":"+(seconds < 10 ? "0"+seconds : seconds)+"."+milliseconds);
        if (mRecorder != null) {

            amplitudes[i] = mRecorder.getMaxAmplitude();

            //Log.d("Voice Recorder","amplitude: "+(amplitudes[i] * 100 / 32767));

            if (i >= amplitudes.length - 1) {

                i = 0;

            } else {

                ++i;

            }
        }
    }

    /**
     * Interface definition for a callback to be invoked when user pressed 'submit' button
     */
    public interface InputListener {

        /**
         * Fires when user presses 'send' button.
         *
         * @param input input entered by user
         * @return if input text is valid, you must return {@code true} and input will be cleared, otherwise return false.
         */
        boolean onSubmit(CharSequence input);
    }

    /**
     * Interface definition for a callback to be invoked when user pressed 'emoji' button
     */

    public interface EmojiKeyboardOpenCloseListener {

        void onEmojiKeyboardOpen();

        void onEmojiKeyboardClosed();
    }

    /**
     * Interface definition for a callback to be invoked when user pressed 'attachment' button
     */
    public interface AllAngleButtonClickListener {

        void onLocationClicked();

        void onImageClicked();

        void onCameraClicked();

        void onExpand();

        void onCollapse();
    }

    /**
     * Interface definition for a callback to be invoked when user pressed 'record' button
     */
    public interface RecordingListener {

        void onStarted();

        void onCancelled();

        void onInvalid(boolean isValid);

        void onCompleted(String recordedFileName);

    }

    public interface TypingListener {

        /**
         * Fires when user presses start typing
         */
        void onStartTyping();

        /**
         * Fires when user presses stop typing
         */
        void onStopTyping();

    }
}
