package com.twigsoftwares.com.chatinputbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

/**
 * Created by Tushar on 30/12/17.
 */

public class ChatInputBarStyle extends Style {

    private static final int DEFAULT_MAX_LINES = 5;

    private boolean showAttachmentButton;

    private boolean showEmojiButton;
    private boolean showRecordButton;

    private int attachmentButtonWidth;
    private int attachmentButtonHeight;
    private int attachmentButtonMargin;


    private int recordSendButtonWidth;
    private int recordSendButtonHeight;
    private int recordSendButtonMargin;


    private int sendButtonBackground;
    private int sendButtonDefaultBgColor;
    private int sendButtonDefaultBgPressedColor;
    private int sendButtonDefaultBgDisabledColor;

    private int emojiButtonDefaultIcon;
    private int emojiButtonDefaultIconColor;

    private int sendButtonWidth;
    private int sendButtonHeight;
    private int sendButtonMargin;


    private int inputMaxLines;
    private String inputHint;
    private String inputText;

    private int inputTextSize;
    private int inputTextColor;
    private int inputHintColor;

    private Drawable inputBackground;
    private Drawable inputCursorDrawable;

    private int inputDefaultPaddingLeft;
    private int inputDefaultPaddingRight;
    private int inputDefaultPaddingTop;
    private int inputDefaultPaddingBottom;

    private int inputDefaultMarginLeft;
    private int inputDefaultMarginRight;
    private int inputDefaultMarginTop;
    private int inputDefaultMarginBottom;


    private int delayTypingStatus;
    private static final int DEFAULT_DELAY_TYPING_STATUS = 1500;

    static ChatInputBarStyle parse(Context context, AttributeSet attrs) {
        ChatInputBarStyle style = new ChatInputBarStyle(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessageInput);

        style.showAttachmentButton = typedArray.getBoolean(R.styleable.MessageInput_showAttachmentButton, false);
        style.attachmentButtonWidth = typedArray.getDimensionPixelSize(R.styleable.MessageInput_attachmentButtonWidth, style.getDimension(R.dimen.input_button_width));
        style.attachmentButtonHeight = typedArray.getDimensionPixelSize(R.styleable.MessageInput_attachmentButtonHeight, style.getDimension(R.dimen.input_button_height));
        style.attachmentButtonMargin = typedArray.getDimensionPixelSize(R.styleable.MessageInput_attachmentButtonMargin, style.getDimension(R.dimen.input_button_margin));


        style.recordSendButtonWidth = typedArray.getDimensionPixelSize(R.styleable.MessageInput_recordButtonWidth, style.getDimension(R.dimen.record_button_width));
        style.recordSendButtonHeight = typedArray.getDimensionPixelSize(R.styleable.MessageInput_recordButtonHeight, style.getDimension(R.dimen.record_button_height));
        style.recordSendButtonMargin = typedArray.getDimensionPixelSize(R.styleable.MessageInput_recordButtonMargin, style.getDimension(R.dimen.record_button_margin));


        style.inputMaxLines = typedArray.getInt(R.styleable.MessageInput_inputMaxLines, DEFAULT_MAX_LINES);
        style.inputHint = typedArray.getString(R.styleable.MessageInput_inputHint);
        style.inputText = typedArray.getString(R.styleable.MessageInput_inputText);

        style.inputTextSize = typedArray.getDimensionPixelSize(R.styleable.MessageInput_inputTextSize, style.getDimension(R.dimen.input_text_size));
        style.inputTextColor = typedArray.getColor(R.styleable.MessageInput_inputTextColor, style.getColor(R.color.dark_grey_two));
        style.inputHintColor = typedArray.getColor(R.styleable.MessageInput_inputHintColor, style.getColor(R.color.warm_grey_three));

        style.inputBackground = typedArray.getDrawable(R.styleable.MessageInput_inputBackground);
        style.inputCursorDrawable = typedArray.getDrawable(R.styleable.MessageInput_inputCursorDrawable);
        style.delayTypingStatus = typedArray.getInt(R.styleable.MessageInput_delayTypingStatus, DEFAULT_DELAY_TYPING_STATUS);

        typedArray.recycle();


        style.inputDefaultPaddingLeft = style.getDimension(R.dimen.input_padding_left);
        style.inputDefaultPaddingRight = style.getDimension(R.dimen.input_padding_right);
        style.inputDefaultPaddingTop = style.getDimension(R.dimen.input_padding_top);
        style.inputDefaultPaddingBottom = style.getDimension(R.dimen.input_padding_bottom);



        return style;
    }

    public void setSendButtonBackground(int sendButtonBackground) {
        this.sendButtonBackground = sendButtonBackground;
    }

    public void setSendButtonDefaultBgColor(int sendButtonDefaultBgColor) {
        this.sendButtonDefaultBgColor = sendButtonDefaultBgColor;
    }

    public void setSendButtonDefaultBgPressedColor(int sendButtonDefaultBgPressedColor) {
        this.sendButtonDefaultBgPressedColor = sendButtonDefaultBgPressedColor;
    }

    public void setSendButtonDefaultBgDisabledColor(int sendButtonDefaultBgDisabledColor) {
        this.sendButtonDefaultBgDisabledColor = sendButtonDefaultBgDisabledColor;
    }

    public void setEmojiButtonDefaultIcon(int emojiButtonDefaultIcon) {
        this.emojiButtonDefaultIcon = emojiButtonDefaultIcon;
    }

    public void setEmojiButtonDefaultIconColor(int emojiButtonDefaultIconColor) {
        this.emojiButtonDefaultIconColor = emojiButtonDefaultIconColor;
    }

    public void setSendButtonWidth(int sendButtonWidth) {
        this.sendButtonWidth = sendButtonWidth;
    }

    public void setSendButtonHeight(int sendButtonHeight) {
        this.sendButtonHeight = sendButtonHeight;
    }

    public void setSendButtonMargin(int sendButtonMargin) {
        this.sendButtonMargin = sendButtonMargin;
    }

    public void setInputDefaultMarginLeft(int inputDefaultMarginLeft) {
        this.inputDefaultMarginLeft = inputDefaultMarginLeft;
    }

    public void setInputDefaultMarginRight(int inputDefaultMarginRight) {
        this.inputDefaultMarginRight = inputDefaultMarginRight;
    }

    public void setInputDefaultMarginTop(int inputDefaultMarginTop) {
        this.inputDefaultMarginTop = inputDefaultMarginTop;
    }

    public void setInputDefaultMarginBottom(int inputDefaultMarginBottom) {
        this.inputDefaultMarginBottom = inputDefaultMarginBottom;
    }

    public void setShowAttachmentButton(boolean showAttachmentButton) {
        this.showAttachmentButton = showAttachmentButton;
    }

    public void setShowEmojiButton(boolean showEmojiButton) {
        this.showEmojiButton = showEmojiButton;
    }

    public void setShowRecordButton(boolean showRecordButton) {
        this.showRecordButton = showRecordButton;
    }

    public void setAttachmentButtonWidth(int attachmentButtonWidth) {
        this.attachmentButtonWidth = attachmentButtonWidth;
    }

    public void setAttachmentButtonHeight(int attachmentButtonHeight) {
        this.attachmentButtonHeight = attachmentButtonHeight;
    }

    public void setAttachmentButtonMargin(int attachmentButtonMargin) {
        this.attachmentButtonMargin = attachmentButtonMargin;
    }


    public void setInputMaxLines(int inputMaxLines) {
        this.inputMaxLines = inputMaxLines;
    }

    public void setInputHint(String inputHint) {
        this.inputHint = inputHint;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public void setInputTextSize(int inputTextSize) {
        this.inputTextSize = inputTextSize;
    }

    public void setInputTextColor(int inputTextColor) {
        this.inputTextColor = inputTextColor;
    }

    public void setInputHintColor(int inputHintColor) {
        this.inputHintColor = inputHintColor;
    }

    public void setInputBackground(Drawable inputBackground) {
        this.inputBackground = inputBackground;
    }

    public void setInputCursorDrawable(Drawable inputCursorDrawable) {
        this.inputCursorDrawable = inputCursorDrawable;
    }

    public void setDelayTypingStatus(int delayTypingStatus) {
        this.delayTypingStatus = delayTypingStatus;
    }

    public void setInputDefaultPaddingLeft(int inputDefaultPaddingLeft) {
        this.inputDefaultPaddingLeft = inputDefaultPaddingLeft;
    }

    public void setInputDefaultPaddingRight(int inputDefaultPaddingRight) {
        this.inputDefaultPaddingRight = inputDefaultPaddingRight;
    }

    public void setInputDefaultPaddingTop(int inputDefaultPaddingTop) {
        this.inputDefaultPaddingTop = inputDefaultPaddingTop;
    }

    public void setInputDefaultPaddingBottom(int inputDefaultPaddingBottom) {
        this.inputDefaultPaddingBottom = inputDefaultPaddingBottom;
    }


    public void setRecordSendButtonWidth(int recordSendButtonWidth) {
        this.recordSendButtonWidth = recordSendButtonWidth;
    }


    public void setRecordSendButtonHeight(int recordSendButtonHeight) {
        this.recordSendButtonHeight = recordSendButtonHeight;
    }


    public void setRecordSendButtonMargin(int recordSendButtonMargin) {
        this.recordSendButtonMargin = recordSendButtonMargin;
    }

    private ChatInputBarStyle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    int getDelayTypingStatus() {
        return delayTypingStatus;
    }

}
