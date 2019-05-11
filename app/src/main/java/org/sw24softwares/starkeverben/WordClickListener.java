package org.sw24softwares.starkeverben;

import android.view.View;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import java.util.Locale;


class WordClickListener implements View.OnClickListener {
    TextToSpeech mTTS;
    Locale mLocale;
    String mText;

    public WordClickListener(String text, TextToSpeech tts, Locale l) {
	mText = text;
	mTTS = tts;
	mLocale = l;
    }
    @Override
    public void onClick(View v) {
	mTTS.setLanguage(mLocale);
	mTTS.speak(mText, TextToSpeech.QUEUE_FLUSH, null, null);
    }
}
