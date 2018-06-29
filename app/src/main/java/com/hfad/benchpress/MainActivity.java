package com.hfad.benchpress;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

public class MainActivity extends AppCompatActivity {
    String msg;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);

        pref = getSharedPreferences("myPos", MODE_PRIVATE);
        int position = pref.getInt("position", 0);
        msg = pref.getString("pm", "");

        if (position != 0){
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("pm", msg);
            startActivity(intent);
        }

        TapTargetView.showFor(this,
                TapTarget.forToolbarMenuItem(toolbar, R.id.information, "Начните тренироваться:", "Прочитайте информацию, укажите свой максимум в жиме лёжа и перейдите к тренировкам")
                        .outerCircleAlpha(0.96f)
                        .targetCircleColor(R.color.grey)
                        .titleTextSize(24)
                        .titleTextColor(R.color.white)
                        .descriptionTextSize(20)
                        .descriptionTextColor(R.color.white)
                        .textColor(R.color.white)
                        .dimColor(R.color.black)
                        .drawShadow(true)
                        .cancelable(false)
                        .tintTarget(false)
                        .transparentTarget(false)
                        .targetRadius(30),
                new TapTargetView.Listener() {
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);
                    }
                });
    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        EditText weight = (EditText) findViewById(R.id.pm);
        msg = weight.getText().toString().replace(",", ".");
        intent.putExtra("pm", msg);
        if (!msg.equals("")) {
            pref.edit().putString("pm", msg).apply();
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://drive.google.com/file/d/0B1LuWbXviYhaS3puZmN6X0lhLTQ");
            startActivity(Intent.createChooser(intent, "Отправить ссылку на приложение"));
            return true;
        }

        if (id == R.id.information) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
