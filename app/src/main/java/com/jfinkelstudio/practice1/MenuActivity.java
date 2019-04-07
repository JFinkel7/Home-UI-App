/*Software Developer: Denis J Finkel
* Date : 12/22/18
* My Systems UI App
* */
package com.jfinkelstudio.practice1;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CalendarView;
import android.widget.GridLayout;
import android.widget.Toast;
import java.util.List;
import java.util.Locale;
public class MenuActivity extends AppCompatActivity {
    private  ActionBarDrawerToggle actionBarToggle;
    private TextToSpeech textToSpeech;
    private SpeechRecognizer solaceSpeechRecognizer;
    private BottomSheetBehavior mainBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // #1 Toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // #3 For Drawer Layout
        final DrawerLayout  drawer = findViewById(R.id.drawer);
        actionBarToggle = new ActionBarDrawerToggle(MenuActivity.this,drawer,R.string.open,R.string.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawer.addDrawerListener(actionBarToggle);
        actionBarToggle.syncState();


        // #4 NavigationView
        final NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem navItem) {
                // If User Chooses To CLick On Account
                if(navItem.getItemId()==R.id.account){
                    Intent loginActivity = new Intent(MenuActivity.this,LoginActivity.class);
                        startActivity(loginActivity);
                    drawer.closeDrawers();
                    return(true);

                }
                return (false);
            }
        });


        initializingTextToSpeech();
        initializeVoiceRecognizer();

        // bottomNavigationView
        BottomNavigationView menuBottomNavigationView = findViewById(R.id.bottomNavigation);
        menuBottomNavigationView.setOnNavigationItemSelectedListener(menuBottomNavigationListener);


        }// END OF CREATE

    // BottomNavigationListener
        private BottomNavigationView.OnNavigationItemSelectedListener menuBottomNavigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if(menuItem.getItemId()==R.id.search){
                        startActivity(new Intent(MenuActivity.this,WebBrowser.class));
                        return(true);
                    }
                    else if(menuItem.getItemId()==R.id.navigate){
                            Intent navigation = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                            startActivity(navigation);
                            return(true);
                    }
                    return false;
                }
            };
    // Inflates Toolbar menu Items
    public boolean onCreateOptionsMenu(Menu menuFile){
        getMenuInflater().inflate(R.menu.toolbar_items,menuFile);
        return(true);
    }
    // Actions When Selecting Items
    @Override
    public boolean onOptionsItemSelected(MenuItem items){
            //#1.2 Toolbar

            //#1.3 Toolbar Item Selection
                if(items.getItemId()==R.id.settings) {
                    Toast.makeText(MenuActivity.this, "TRUE", Toast.LENGTH_SHORT).show();
                    return(true);
                }
                // Text Speech Initialization Here
                if(items.getItemId()==R.id.solace){

                    Intent solaceFunctions = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    solaceFunctions.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    solaceFunctions.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                    solaceSpeechRecognizer.startListening(solaceFunctions);

                    return(true);
                }
                if(items.getItemId()==R.id.viewPage){
                   View bottomSheet = findViewById(R.id.bottomSheet);
                   mainBottomSheet = BottomSheetBehavior.from(bottomSheet);
                    mainBottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

                if (actionBarToggle.onOptionsItemSelected(items)){
                    return(true);
                }
                return(super.onOptionsItemSelected(items));
    }

    //  On Click Image Button Events
    public void viewMessages(View vMessages) {
       Animation animMateNotes = AnimationUtils.loadAnimation(MenuActivity.this,R.anim.blink_button);
        vMessages.startAnimation(animMateNotes);
       Intent notes = getPackageManager().getLaunchIntentForPackage("com.samsung.android.messaging");
       startActivity(notes);
    }
    public void viewMail(View vMail){
        Animation animMateMail = AnimationUtils.loadAnimation(MenuActivity.this,R.anim.blink_button);
        vMail.startAnimation(animMateMail);
        Intent mail = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
        startActivity(mail);
    }

    public void viewPhone(View vPhone){
        Animation animMatePhone = AnimationUtils.loadAnimation(MenuActivity.this,R.anim.blink_button);
        vPhone.startAnimation(animMatePhone);
        Intent vMessages = getPackageManager().getLaunchIntentForPackage("com.spotify.music");
        startActivity(vMessages);
    }

    public void viewApps(View vApps){
        Animation animMateApps = AnimationUtils.loadAnimation(MenuActivity.this,R.anim.blink_button);
        vApps.startAnimation(animMateApps);
        //Toast.makeText(MenuActivity.this, " Apps Not Available ", Toast.LENGTH_SHORT).show();
    }

    // Creating Text Speech
    public void initializingTextToSpeech(){
            textToSpeech =  new TextToSpeech(MenuActivity.this,new TextToSpeech.OnInitListener(){
                @Override
                public void onInit(int i){
                    textToSpeech.setLanguage(Locale.UK);
                    //solaceSpeaks("Welcome Back Mr. Finkel");
                    }
            });
    }
    public void solaceSpeaks(String message){
        textToSpeech.speak(message,textToSpeech.QUEUE_FLUSH,null,null);
    }
    public void onPause(){
        super.onPause();
        textToSpeech.shutdown();
        textToSpeech.stop();
    }
    // Creating Speech Recognizer
    public void initializeVoiceRecognizer(){
        if(SpeechRecognizer.isRecognitionAvailable(MenuActivity.this)){
            solaceSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(MenuActivity.this);
            solaceSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle stringBundle) {
                List<String>stringResults = stringBundle.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION);
                    solaceProcessResult(stringResults.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }

    }// END OF SPEECH RECOGNIZER

    // solaceProcessResult
    public void solaceProcessResult(String solaceCommands) {
        SolaceWords solaceSpeechWord = new SolaceWords();
        solaceCommands = solaceCommands.toLowerCase();


        if(solaceCommands.contains("my")) {
            if (solaceCommands.contains("profile")) {
                solaceSpeaks("Opening Locked Profile Contents. View Elapse Time Is 30 seconds");

                // Hiding Contents

                final CalendarView calendarView = findViewById(R.id.calender_Header);
                calendarView.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        calendarView.setVisibility(View.VISIBLE);
                        solaceSpeaks("Profile Contents elapse time has expired");
                    }
                },1000*30);
                //solaceSpeaks(solaceSpeechWord.accessDenied());
            } else if (solaceCommands.contains("balance")) {
                solaceSpeaks(solaceSpeechWord.accessDenied());
            } else if (solaceCommands.contains("notes")) {
                solaceSpeaks(solaceSpeechWord.accessDenied());
            } else if (solaceCommands.contains("documents")) {
                solaceSpeaks(solaceSpeechWord.accessDenied());
            } else if (solaceCommands.contains("contacts")) {
                solaceSpeaks(solaceSpeechWord.accessDenied());
            }

        }// END 

        else if(solaceCommands.contains("open")) {
            if (solaceCommands.contains("burner")) {
                solaceSpeaks("Sir To Remain Anonymous remember to delete the number");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent n = getPackageManager().getLaunchIntentForPackage("com.adhoclabs.burner");

                        startActivity(n);
                    }
                }, 4000);
            }
        }//END OF OPEN
        else if(solaceCommands.contains("scan")){
                            solaceSpeaks("Sir Make Sure Network Connections Are Permissible  ");
                      if(solaceCommands.contains("network")){
                            solaceSpeaks("analyzing network packets & network port connections ");
                      }
        }
    }// * END OF solaceProcessResult *

    public void setWebBrowserWebView(WebView webView){
        webView.loadUrl("http://google.com");
        webView.setWebViewClient(new WebViewClient());
    }




}// END OF CLASS
