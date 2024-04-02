package com.learnenglishtospanish;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText editTextLeatters;
    TextView textView;
    Button btnTranslate;
    ImageButton spek;
    ImageButton copy;
    ImageButton clear;
    ImageButton clear2;
    ImageButton copy2;
    ImageButton spek2;

    TextToSpeech textToSpeech;
    ProgressBar progressBar;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextLeatters = findViewById(R.id.writing_here);
        textView = findViewById(R.id.answer_here);
        btnTranslate = findViewById(R.id.button);
        spek = findViewById(R.id.spek);
        spek2 = findViewById(R.id.spek2);
        copy = findViewById(R.id.copy);
        copy2 = findViewById(R.id.copy2);
        clear = findViewById(R.id.clear);
        clear2 = findViewById(R.id.clear2);
        progressBar = findViewById(R.id.progresssBar);
        toolbar = findViewById(R.id.toolBar);


        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String text = editTextLeatters.getText().toString();

                if (text.isEmpty()){
                    Toast.makeText(MainActivity.this,"Thare Is No Text..",Toast.LENGTH_SHORT).show();
                }else {

                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(MainActivity.this.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("",editTextLeatters.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(MainActivity.this,"Text Is Copyed..",Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cleare = editTextLeatters.getText().toString();
                if (cleare.isEmpty()){
                    Toast.makeText(MainActivity.this,"There Is No Text To Clear",Toast.LENGTH_SHORT).show();
                }else {

                    editTextLeatters.setText("");
                }
            }
        });

        spek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(""+editTextLeatters.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
            }
        });

        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String eText = editTextLeatters.getText().toString();
                if (eText.isEmpty())progressBar.setVisibility(View.GONE);
                if (TextUtils.isEmpty(editTextLeatters.getText().toString())){
                    Toast.makeText(MainActivity.this,"No Text Here",Toast.LENGTH_SHORT).show();
                }else {
                    TranslatorOptions options = new TranslatorOptions.Builder()
                            .setTargetLanguage("es")
                            .setSourceLanguage("en")
                            .build();
                    Translator translator = Translation.getClient(options);
                    ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Wait..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    translator.downloadModelIfNeeded().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    });
                    String sourceText = editTextLeatters.getText().toString();
                    Task<String>ruselt = translator.translate(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            progressBar.setVisibility(View.GONE);
                            textView.setText(s);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        spek2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(""+textView.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,null);
            }
        });
        copy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = textView.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(MainActivity.this,"Thare is No Text..",Toast.LENGTH_SHORT).show();
                }else {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(MainActivity.this.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("",textView.getText().toString());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(MainActivity.this,"Text Is Copyed..",Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cleare = textView.getText().toString();
                if (cleare.isEmpty()){
                    Toast.makeText(MainActivity.this,"There Is No Text To Clear",Toast.LENGTH_SHORT).show();
                }else {
                    textView.setText("");
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.share){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Chake Out This Cool Apps");
                    intent.putExtra(Intent.EXTRA_TEXT,"Your Application Link Here");
                    startActivity(Intent.createChooser(intent,"Share Via"));
                } else if (item.getItemId()==R.id.info) {
                    Intent intent = new Intent(MainActivity.this,DeveloperInfo.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}