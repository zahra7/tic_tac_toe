package dz.dev.tech.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity {

    boolean gameActive;
    int activePlayer = 0; // 0 : red, 1 : blue
    int[] states = {2, 2, 2, 2, 2, 2, 2, 2, 2} ; //2 : unplayed, 0:red, 1 : blue
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    TextView msg;
    Button start;
    LinearLayout layout;
    GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msg = findViewById(R.id.msgView);
        start = findViewById(R.id.button);
        layout  = findViewById(R.id.layout);
        grid = findViewById(R.id.grid);

        initGame();


    }

    public void stopGame(){
        gameActive = false;
        start.setEnabled(true);
        for(int i=0;i<grid.getChildCount();i++){
            ImageView image = (ImageView) grid.getChildAt(i);
            image.setEnabled(false);
        }
    }

    public void initGame(){
        gameActive = true;
        layout.setVisibility(View.INVISIBLE);
        start.setEnabled(false);
        for(int i=0;i<grid.getChildCount();i++){
            ImageView image = (ImageView) grid.getChildAt(i);
            image.setEnabled(true);
            image.setImageResource(0);
        }
        msg.setText("");
        for(int i=0; i<states.length;i++){
            states[i]=2;
        }
        activePlayer = 0;
    }

    public void startGame(View v){
        initGame();
    }

    public void dropin(View v){
        ImageView counter = (ImageView) v;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(states[tappedCounter] == 2 ){
            counter.setTranslationY(-1000f);
            states[tappedCounter]=activePlayer;
            if(activePlayer == 0) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            }
            else if(activePlayer == 1) {
                counter.setImageResource(R.drawable.blue);
                activePlayer = 0;
            }
            counter.animate().translationY(0f).rotation(360f).setDuration(300);

            for(int[] winningPosition : winningPositions){
                if(states[winningPosition[0]]==states[winningPosition[1]]
                        && states[winningPosition[1]]==states[winningPosition[2]]
                        && states[winningPosition[2]] != 2
                ){
                    String win = "";
                    if(activePlayer == 1) win="Red has won!";
                    else win="Blue has won!";
                    msg.setText(win);
                    layout.setVisibility(View.VISIBLE);
                    stopGame();
                }
                else{
                    boolean gameIsOver = true;
                    for(int counterState: states){
                        if (counterState == 2) gameIsOver = false;
                    }

                    if(gameIsOver){
                        msg.setText("Game is over. It's a draw!");
                        stopGame();
                    }
                }
            }
        }

    }
}