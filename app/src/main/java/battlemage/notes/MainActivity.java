package battlemage.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView CustomList;
   // ArrayAdapter <String> adapter;
    CustomAdapter customAdapter;
    MySqlite mySqlite;
    String ID[],TITLE[],NOTE[],sDate[];
    Long DATE[];
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mySqlite= new MySqlite(this);
        CustomList = (ListView)findViewById(R.id.txtList);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffac00")));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, Create_list_Activity.class);
                startActivity(intent);
            }
        });

        Cursor cursor = mySqlite.display();
        if (cursor.getCount() > 0) {

           // java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
           // sDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(String.valueOf(DATE))).getT))

            ID = new String[cursor.getCount()];
            TITLE = new String[cursor.getCount()];
            NOTE = new String[cursor.getCount()];
            sDate = new String[cursor.getCount()];

            /*java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
            dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(mySqlite.COLUMN4))).getTime());*/

            cursor.moveToFirst();



            int count = 0;

            do {
                ID[count] = cursor.getString(0);
                TITLE[count] = cursor.getString(1);
                NOTE[count] = cursor.getString(2);
                sDate[count] = cursor.getString(3);

                count++;

            } while (cursor.moveToNext());

           // adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, TITLE);
            //CustomList.setAdapter(adapter);
            //contact_search.setOnQueryTextListener(this);
            customAdapter = new CustomAdapter(getApplicationContext(),TITLE,sDate);
            CustomList.setAdapter(customAdapter);

        } else {
        }

        CustomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("ID",ID[position]);
                intent.putExtra("TITLE",TITLE[position]);
                intent.putExtra("NOTE",NOTE[position]);
                intent.putExtra("DATE",sDate[position]);
                startActivity(intent);
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,AboutDeveloper.class);
            startActivity(intent);
            return true;
        }
        else if(id==R.id.exit){
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Exit Confirmation")
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
