package com.swtec.contentproviderapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.swtec.contentproviderapp.R;
import com.swtec.contentproviderapp.data.DairyEntry;

import static android.view.inputmethod.InputMethodManager.SHOW_FORCED;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(SHOW_FORCED, 0);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        DairyEntry dairyEntry = (DairyEntry) bundle.getParcelable(MainActivity.INTENT_KEY);

        EditText editText = findViewById(R.id.activity_editdata__et);
        editText.requestFocus();
        if (dairyEntry.getEntryText() != null) {
            editText.setText(dairyEntry.getEntryText());
        }

        Button button = findViewById(R.id.activity_main__btn_updateData);
        button.setOnClickListener(v -> {
            Intent resultIntent = new Intent(this, MainActivity.class);
            dairyEntry.setEntryText(editText.getText().toString());
            resultIntent.putExtra(MainActivity.INTENT_KEY, dairyEntry);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
