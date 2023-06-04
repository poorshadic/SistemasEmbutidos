package com.example.automaticlights;

import com.example.automaticlights.databinding.ActivityLightStatusBinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button bt_light;
    private Button bt_status;
    private ImageView iv_img;
    private boolean isOn = false;
    private OkHttpClient client;
    private static final String SERVER_URL = "http://192.168.1.4:5000"; // 10.199.43.15

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLightStatusBinding binding = ActivityLightStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        client = new OkHttpClient();

        bt_light = binding.buttonLight;
        bt_status = binding.buttonStatus;
        iv_img = binding.imageView;

        bt_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json = "{\"isOn\": " + isOn + "}"; // Send to the server the opposite of what is currently
                MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                RequestBody requestBody = RequestBody.create(json, mediaType);

                Request request = new Request.Builder().url(SERVER_URL).post(requestBody).build();

                // Disable both buttons when any kinda of action is performed
                bt_status.setEnabled(false);
                bt_light.setEnabled(false);

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(() -> {
                            bt_status.setEnabled(true);
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(() -> {
                            bt_status.setEnabled(true);
                        });
                        if (response.isSuccessful() && response.body() != null) {
                            final String responseBody = response.body().string();
                            Log.d("LightCallButton", responseBody);
                            if(responseBody.contains("true")){ // When server says that the light is ON
                                isOn();
                            }
                            else if(responseBody.contains("false")){  // When server says that the light is OFF
                                isOff();
                            } else { // When does not send what we expected default behaviour light off
                                Log.d("TTR", "Use Status button");
//                                Toast.makeText(MainActivity.this, "Use Status button", Toast.LENGTH_SHORT).show();
                                isOff();
                                runOnUiThread(() -> {
                                    bt_light.setEnabled(false);
                                });
                            }
                        }
                    }
                });
            }
        });

        bt_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_status();
            }
        });

        get_status();
    }

    private void isOff(){
        runOnUiThread(() -> {
            isOn = true;
            bt_light.setEnabled(true);
            bt_light.setText("Light ON");
            bt_light.setCompoundDrawablesWithIntrinsicBounds(R.drawable.light_on_icon, 0, 0, 0);
            iv_img.setImageResource(R.drawable.light_off);
        });
    }

    private void isOn(){
        runOnUiThread(() -> {
            isOn = false;
            bt_light.setEnabled(true);
            bt_light.setText("Light OFF");
            bt_light.setCompoundDrawablesWithIntrinsicBounds(R.drawable.light_off_icon, 0, 0, 0);
            iv_img.setImageResource(R.drawable.light_on);
        });
    }

    private void get_status(){
        Request request = new Request.Builder().url(SERVER_URL).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    bt_light.setEnabled(false);
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    final String responseBody = response.body().string();
                    Log.d("StatusCallButton", responseBody);
                    if(responseBody.contains("true")){ // When server says that the light is ON
                        isOn();
                    }
                    else if(responseBody.contains("false")){  // When server says that the light is OFF
                        isOff();
                    } else { // When does not send what we expected default behaviour light off
                        isOff();
                        runOnUiThread(() -> {
                            bt_light.setEnabled(false);
                        });
                    }
                }
            }
        });
    }
}