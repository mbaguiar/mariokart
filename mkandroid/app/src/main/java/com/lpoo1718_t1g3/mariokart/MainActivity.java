package com.lpoo1718_t1g3.mariokart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lpoo1718_t1g3.mk_android.R;
import com.lpoo1718_t1g3.mariokart.networking.Connector;
import com.lpoo1718_t1g3.mariokart.networking.Message;

import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    static ObjectOutputStream w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.connectBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ip = (EditText) findViewById(R.id.ipTxt);
                EditText port = (EditText) findViewById(R.id.portTxt);
                connect(ip.getText().toString(), Integer.parseInt(port.getText().toString()));
            }
        });

    }

    protected void connect(String addr, int port){
        if (Connector.getInstance().connect(addr, port) != null){
            Connector.getInstance().write(new Message(Message.MESSAGE_TYPE.CONNECTION, Message.SENDER.CLIENT));
            Intent i = new Intent(this, ControlActivity.class);
            startActivity(i);
        }

    }
}
