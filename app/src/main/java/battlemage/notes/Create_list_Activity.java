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
import android.widget.TextView;
import android.widget.Toast;

public class Create_list_Activity extends AppCompatActivity {

    TextView txtTitle, txtNote;
    Button btnSave;

    MySqlite mySqlite;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list_);

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtNote = (TextView)findViewById(R.id.txtNote);

        btnSave = (Button)findViewById(R.id.btnSave);
        mySqlite= new MySqlite(this);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffac00")));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = txtTitle.getText().toString();
                String note = txtNote.getText().toString();
                Long date = java.lang.System.currentTimeMillis();
                if(!title.isEmpty())
                {
                  boolean chk=  mySqlite.addToTable(null,title,note,date);
                    if(chk==true){
                        Toast.makeText(Create_list_Activity.this, "Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Create_list_Activity.this, MainActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(Create_list_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    txtTitle.setError("title can't be empty");
                    requestFocus(txtTitle);
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
