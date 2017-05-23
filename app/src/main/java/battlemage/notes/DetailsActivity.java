package battlemage.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsActivity extends AppCompatActivity {

    TextView detailsTitle, detailsNote,detailsDate;
    Button btnDelete,btnUpdate,btnwidget;

    MySqlite mySqlite ;
    ActionBar actionBar;
    String title,note,date,Id;

    public static  String widget;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsTitle = (TextView)findViewById(R.id.detailsTitle);
        detailsNote = (TextView) findViewById(R.id.detailsNote);
        detailsDate = (TextView)findViewById(R.id.detailsDate);

        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnUpdate =(Button)findViewById(R.id.btnUpdate);
        btnwidget = (Button)findViewById(R.id.btnwidget);

        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffac00")));
        mySqlite = new MySqlite(this);

        Cursor cursor;

        Id=getIntent().getExtras().getString("ID");
        title=getIntent().getExtras().getString("TITLE");
        note=getIntent().getExtras().getString("NOTE");
        date=getIntent().getExtras().getString("DATE");

        detailsTitle.setText(title);
        detailsNote.setText(note);
        detailsDate.setText("date created: "+getDate(Long.parseLong(date),"dd/MM/yyyy hh:mm:ss a"));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this,UpdateActivity.class);

                intent.putExtra("ID",Id);
                intent.putExtra("TITLE",title);
                intent.putExtra("NOTE",note);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        btnwidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                widget=note;
                Toast.makeText(DetailsActivity.this, "Added to Widget(Refresh)", Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(DetailsActivity.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Are you sure you want to Delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                boolean chk = mySqlite.deleteData(Id);
                                if (chk == true) {
                                    Toast.makeText(DetailsActivity.this, "deleted!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DetailsActivity.this, "Error!Contact deletion failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


    }
    public  String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
