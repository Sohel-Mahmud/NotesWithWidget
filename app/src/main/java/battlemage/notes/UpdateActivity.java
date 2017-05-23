package battlemage.notes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    String id, title, note;
    EditText updateTitle, updateNote;
    Button finalUpdate;

    MySqlite mySqlite;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateTitle = (EditText) findViewById(R.id.updateTitle);
        updateNote = (EditText) findViewById(R.id.updateNote);
        mySqlite = new MySqlite(this);

        finalUpdate = (Button) findViewById(R.id.finalUpdate);
        id = getIntent().getExtras().getString("ID");
        title = getIntent().getExtras().getString("TITLE");
        note = getIntent().getExtras().getString("NOTE");

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffac00")));


        updateTitle.setText(title);
        updateNote.setText(note);

        finalUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = updateTitle.getText().toString();
                String note = updateNote.getText().toString();

                if (!title.isEmpty()) {
                    if (!note.isEmpty()) {
                        boolean chk = mySqlite.updateData(id, title, note);
                        if (chk == true) {
                            Toast.makeText(UpdateActivity.this, "Note Information Updated!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                            intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(UpdateActivity.this, "Note Updating Failed!", Toast.LENGTH_SHORT).show();
                    } else {
                        updateNote.setError("Please Enter Note");
                        requestFocus(updateNote);
                    }
                } else {
                    updateTitle.setError("Please Enter title");
                    requestFocus(updateTitle);
                }
            }
        });


    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
