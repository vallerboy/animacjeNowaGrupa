package pl.akademiakodu.animacje;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {


    ImageView logo;
    RelativeLayout layout;

    AnimationDrawable animationDrawable;

    // Definicja handlera
    Handler handlerAnim = new Handler(new Handler.Callback() {
       // metoda która obsługuje przychodzące wiadomości do handlera
        @Override
        public boolean handleMessage(Message msg) {
            // "what" odpowiada za ID wiadomości
            if(msg.what == 1){
                animationDrawable.start();
                Toast.makeText(MainActivity.this, "Wiadomość nr 1", Toast.LENGTH_LONG).show();
            }else if(msg.what == 2){
                animationDrawable.stop();

                Toast.makeText(MainActivity.this, "Wiadomość nr 2", Toast.LENGTH_LONG).show();
            }else  if(msg.what == 3){
                Bundle data = msg.getData();
                Toast.makeText(MainActivity.this, data.getString("msg", "Brak (error)"), Toast.LENGTH_SHORT).show();

            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = (ImageView) findViewById(R.id.logo);
        layout = (RelativeLayout) findViewById(R.id.activity_main);

        // Ta zmienna musi być globalna, aby z niej korzystać w handlerze
        animationDrawable = (AnimationDrawable) layout.getBackground();


        // Wysłanie pustej wiadomości z opóźnieniem
        handlerAnim.sendEmptyMessageDelayed(1, 4000);
        handlerAnim.sendEmptyMessageDelayed(2, 8000);

        // Stworzenie wiadomości z jakimiś danymi
        Message message = new Message();
        message.what = 3;

        // Klasa bundle przechowuje za nas jakieś informacje
        Bundle data = new Bundle();
        data.putString("msg", "Informacja przesłana wiadomością");

        message.setData(data);

        // Wysłanie wiadomości pełnej z jakimś opóźnieniem
        handlerAnim.sendMessageDelayed(message, 5000);



        // Szybkie utworzenie jakiegoś zadania z opóźnieniem
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // jakies cos co wykona sie po 3 sekundach
            }
        }, 3000);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animation.setFillAfter(true); // Pozostawienie widzetu w stanie "poanimacyjnym"


        // ANIMACJA Z KODU
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, Animation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(4000);

        //logo.startAnimation(scaleAnimation);

        // KONIEC ANIMACJI Z KODU

        // Nasłuchiwacz animacji
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(MainActivity.this, "Animacja ruszyła", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(MainActivity.this, "Animacja się skończyła", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }
}

