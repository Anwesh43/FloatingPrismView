package com.anwesome.games.floatingprisview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.anwesome.games.prisview.PrisButton;
import com.anwesome.games.prisview.PrisButtonClickListener;
import com.anwesome.games.prisview.PrisView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrisView prisView = (PrisView)findViewById(R.id.my_pris_view);
        prisView.setX(200);
        prisView.setY(200);
        PrisButton button1 = PrisButton.newInstance();
        PrisButton button2 = PrisButton.newInstance();
        PrisButton button3 = PrisButton.newInstance();
        PrisButton button4 = PrisButton.newInstance();
        setButtonsAction(button1,button2,button3,button4);
        prisView.addButton(button1,button2,button3,button4);


    }
    private void setButtonsAction(PrisButton...buttons) {
        for(int i=0;i<buttons.length;i++) {
            final String text="this is button "+i;
            PrisButton button = buttons[i];
            button.setOnClickListener(new PrisButtonClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface a1,int v2) {

                        }
                    });
                    dialog.setMessage(text);
                    dialog.show();
                }
            });
        }
    }
}
