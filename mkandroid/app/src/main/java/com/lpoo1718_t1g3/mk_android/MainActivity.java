package com.lpoo1718_t1g3.mk_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        Connection c;
        Socket clientSocket;
        try{
            c = new Connection(addr, port);
            clientSocket = c.connect();
            w = new ObjectOutputStream(clientSocket.getOutputStream());
            Intent i = new Intent(this, ControlActivity.class);
            startActivity(i);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
