package com.jltrem.chessflashcards;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.concurrent.atomic.AtomicInteger;
import android.widget.LinearLayout;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class MainActivity extends AppCompatActivity {

    class ChessPosition {
        private String id;
        private String description;
        private String fen;
    }

    class Greeting{
        private String id;
        private String content;
    }

    class GetChessPosition extends AsyncTask<Integer, Void, ChessPosition> {

        private Exception exception;

        protected ChessPosition doInBackground(Integer... id) {
            try {
                String url = "http://api.digintodata.com/chess/position/{query}";
                //String url = "http://rest-service.guides.spring.io/greeting";
                //String url = "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&q={query}";
                //final String url = "http://api.icndb.com/jokes/random";
                //String url = "https://api.github.com/users/jltrem";

                RestTemplate restTemplate = new RestTemplate();
                //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

                Log.d("GetChessPosition", url);
                String response = restTemplate.getForObject(url, String.class, id[0]);
                Log.d("GetChessPosition", "complete");
                ChessPosition position = new ChessPosition();
                /*position.id = response.id;
                position.description = response.content;
                position.fen = response.id;*/
                position.id = "";
                position.description = response;
                position.fen = "";
                //Log.d("GetChessPosition", position.description);
                return position;
            } catch (Exception e) {
                Log.e("GetChessPosition", e.toString());
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(ChessPosition position) {

            Log.d("GetChessPosition", position != null ? position.toString() : "null");
            if (position == null)
            {
                position = new ChessPosition();
            }
            TextView text = (TextView) findViewById(R.id.positionId);
            text.setText(position.id);
            text = (TextView) findViewById(R.id.positionDescr);
            text.setText(position.description);
            text = (TextView) findViewById(R.id.positionFen);
            text.setText(position.fen);

            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

    public class OnClickedSquareListener implements View.OnClickListener {
        public void onClick(View v) {
            TextView atSquare = (TextView)findViewById(R.id.atSquare);

            String notation = getSquareNotation(v);
            atSquare.setText(notation);
        }
    }

    public OnClickedSquareListener onClickedSquareListener = new OnClickedSquareListener();

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * Generate a value suitable for use in View.setId
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }


    public static int squareBtnId[][] = new int[8][8];
    public static int[][] squarePieceText =
    {
        {R.string.icon_rook, R.string.icon_knight, R.string.icon_bishop, R.string.icon_queen, R.string.icon_king, R.string.icon_bishop, R.string.icon_knight, R.string.icon_rook},
        {R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn},
        {R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty},
        {R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty},
        {R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty},
        {R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty, R.string.icon_empty},
        {R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn, R.string.icon_pawn},
        {R.string.icon_rook, R.string.icon_knight, R.string.icon_bishop, R.string.icon_queen, R.string.icon_king, R.string.icon_bishop, R.string.icon_knight, R.string.icon_rook},
    };

    public static int[][] squarePieceColor =
    {
        {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK},
        {Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK},
        {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
        {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
        {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
        {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
        {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE},
        {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE},
    };

    public int getSquarePieceTextId(int rowNdx, int colNdx)
    {
        return squarePieceText[rowNdx][colNdx];
    }

    public int getSquarePieceColor(int rowNdx, int colNdx)
    {
        return squarePieceColor[rowNdx][colNdx];
    }

    public String getSquareNotation(View view) {
        int id = view.getId();

        for (int rowNdx = 0; rowNdx < 8; rowNdx++) {
            for (int colNdx = 0; colNdx < 8; colNdx++) {
                if (squareBtnId[rowNdx][colNdx] == id) {
                    return String.format("%C%d", 'A' + colNdx, 8 - rowNdx);
                }
            }
        }

        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int btnSize = Math.min(metrics.widthPixels, metrics.heightPixels) / 10;

        LinearLayout boardLayout = (LinearLayout)findViewById(R.id.board_layout);
        boardLayout.setOrientation(LinearLayout.VERTICAL);

        for (int rowNdx = 0; rowNdx < 8; rowNdx++)
        {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            for(int colNdx = 0; colNdx < 8; colNdx++)
            {
                // create the button in the row
                FontButton btn = new FontButton(this);
                btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                btn.setMinHeight(0);
                btn.setMinWidth(0);
                CustomFontHelper.setCustomFont(btn, "fonts/glyphicons.ttf", this);
                btn.setText(getSquarePieceTextId(rowNdx, colNdx));
                btn.setTextColor(getSquarePieceColor(rowNdx, colNdx));
                btn.setWidth(btnSize);
                btn.setHeight(btnSize);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnSize / 2);
                btn.setOnClickListener(onClickedSquareListener);

                boolean colorToggle = (((colNdx + rowNdx) % 2) == 0);
                int squareColor = colorToggle ? Color.LTGRAY : Color.GRAY;
                btn.setBackgroundColor(squareColor);

                // make an id for the button and put store it in the row/col id array
                int btnId = generateViewId();
                squareBtnId[rowNdx][colNdx] = btnId;
                btn.setId(btnId);
                rowLayout.addView(btn);
            }

            boardLayout.addView(rowLayout);
        }
    }

    protected void onStart() {
        super.onStart();
        new GetChessPosition().execute(1);
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
}
