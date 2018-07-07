package com.ofoegbuvgmail.phonegeek.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ofoegbuvgmail.phonegeek.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText fromEditText;
    private EditText toEditText;
    private EditText ramSizeEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        initViews();
        searchButton.setOnClickListener(this);
    }

    private void initViews() {
        fromEditText = findViewById(R.id.from_editText);
        toEditText = findViewById(R.id.to_editText);
        ramSizeEditText = findViewById(R.id.ram_size_editText);
        searchButton = findViewById(R.id.searchButton);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (fromEditText.getText() != null && toEditText.getText() != null && ramSizeEditText.getText() != null) {
            String fromPriceRange = fromEditText.getText().toString();
            String toPriceRange = toEditText.getText().toString();
            String ramSize = ramSizeEditText.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("fromPrice", fromPriceRange);
            bundle.putString("toPrice", toPriceRange);
            bundle.putString("ramSize", ramSize);

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please Fill in all the required fields.", Toast.LENGTH_SHORT).show();
        }
    }
}
