package com.test.brtest.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.test.brtest.Constants;
import com.test.brtest.R;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    // Initialization
    protected abstract void init();

    // Show Alert
    protected void alert(final Context mContext, final String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setMessage(message)
                .setNeutralButton("OK", null);
        builder.show();
    }

    // Show Toast
    protected void showShortToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    // get Formatted Number
    protected String getFormatNumber (String rawNumber) {

        String formattedNumber = String.format("(%s) %s-%s",
                rawNumber.substring(0, 3),
                rawNumber.substring(4, 7),
                rawNumber.substring(8, 12));

        return formattedNumber;
    }

    // get Formatted Address
    protected String getFormatAddress (String address,String city,String state,String zipcode){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(address)
                .append(Constants.COMMA)
                .append(Constants.SPACE)
                .append(city)
                .append(Constants.COMMA)
                .append(Constants.SPACE)
                .append(state)
                .append(Constants.SPACE)
                .append(zipcode);

        return stringBuilder.toString();
    }
}
