package com.strideshow.liruxuan.pocketsphinx;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.strideshow.liruxuan.projectslider.SlideActivity;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

import java.io.File;
import java.io.IOException;

/**
 * Created by liruxuan on 2016-09-23.
 */
public class PocketSphinxSpeechRecognizer implements RecognitionListener {

    public static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    // Recognizer search name
    public static final String KEYWORD_SEARCH_NAME = "keyword";

    // keywords
    private static final String NEXT_SLIDE = "next slide";
    private static final String PREV_SLIDE = "previous slide";
    // private static final String GOTO_SLIDE = "goto slide";

    // Dynamic keyword file
    private String assetDirStr;

    //
    private Context context;
    private SpeechRecognizer recognizer;
    private ViewPager viewPager;

    public PocketSphinxSpeechRecognizer(Context ctx, ViewPager viewPager) {
        this.context   = ctx;
        this.viewPager = viewPager;

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                    (SlideActivity) context,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSIONS_REQUEST_RECORD_AUDIO);
        }

        // Setup
        runRecognizerSetup();
    }

    @Override
    public void onBeginningOfSpeech() {
    }


    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
    }


    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }

        String text = hypothesis.getHypstr().trim();

        switch(text) {
            case NEXT_SLIDE:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                break;
            case PREV_SLIDE:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                break;
            // case GOTO_SLIDE:
            // viewPager.setCurrentItem(get last word in string)
            default:
                System.out.println("Default: " + text);
        }
        resetListen(KEYWORD_SEARCH_NAME);
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            //System.out.println("On result: " + text);
        }
    }

    @Override
    public void onError(Exception error) {
        System.out.println("onError reached");
    }

    @Override
    public void onTimeout() {
        System.out.println("onTimeout reached");
    }

    public void onDestroy() {
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    private void resetListen(String searchName) {
        recognizer.stop();
        recognizer.startListening(KEYWORD_SEARCH_NAME);
    }

    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(context);
                    File assetDir = assets.syncAssets();

                    // Keep path for dynamic keyword searches
                    assetDirStr = assetDir.getAbsolutePath();

                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    System.out.println("Permission Denied: Audio recording");
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    Toast.makeText(context, "Failed to init recognizer", Toast.LENGTH_LONG)
                            .show();
                } else {
                    recognizer.startListening(KEYWORD_SEARCH_NAME);
                }
            }
        }.execute();
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                //.setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                //.setKeywordThreshold(1e-45f) // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-20f)

                .setBoolean("-allphone_ci", true)  // Use context-independent phonetic search, context-dependent is too slow for mobile
                .getRecognizer();

        recognizer.addListener(this);

        /**
         * In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        //recognizer.addKeyphraseSearch(KEYWORD_SEARCH_NAME, "next slide");
        recognizer.addKeywordSearch(KEYWORD_SEARCH_NAME, new File(assetsDir, "strideshow.gram"));

        /*// Create grammar-based search for selection between demos
        File menuGrammar = new File(assetsDir, "menu.gram");
        recognizer.addGrammarSearch(MENU_SEARCH, menuGrammar);*/

        // Create grammar-based search for digit recognition
        //File digitsGrammar = new File(assetsDir, "digits.gram");
        //recognizer.addGrammarSearch(DIGITS_SEARCH_NAME, digitsGrammar);

        /*// Create language model search
        File languageModel = new File(assetsDir, "weather.dmp");
        recognizer.addNgramSearch(FORECAST_SEARCH, languageModel);

        // Phonetic search
        File phoneticModel = new File(assetsDir, "en-phone.dmp");
        recognizer.addAllphoneSearch(PHONE_SEARCH, phoneticModel);*/
    }
}
