package com.example.arithmetic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;

import java.util.Random;

public class ArithMetic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new arithmeticView(this));
    }

    class arithmeticView extends View{

        int color[] = {Color.rgb(0x2e, 0x11, 0x2d), Color.rgb(0x54, 0x00, 0x32), Color.rgb(0x82, 0x03, 0x33),
                Color.rgb(0xc9, 0x28, 0x3e), Color.rgb(0xf0, 0x43, 0x3a),
                Color.rgb(0x5e, 0x00, 0x5e), Color.rgb(0xab, 0x2f, 0x52), Color.rgb(0xe5, 0x5d, 0x4a),
                Color.rgb(0xe8, 0x85, 0x54), Color.rgb(0xff, 0xaf, 0x53),
                Color.rgb(0x45, 0x3f, 0x41), Color.rgb(0xb3, 0x7e, 0x78), Color.rgb(0x57, 0x43, 0x45),
                Color.rgb(0x6c, 0x48, 0x56), Color.rgb(0xa3, 0x5b, 0x67),
                Color.rgb(0xe3, 0x7b, 0x40), Color.rgb(0x46, 0xb2, 0x9d), Color.rgb(0xde, 0x5b, 0x49),
                Color.rgb(0x32, 0x4d, 0x5c), Color.rgb(0xf0, 0xca, 0x4d),
                Color.rgb(0x59, 0x22, 0x2c), Color.rgb(0x8c, 0x51, 0x5c), Color.rgb(0xbf, 0xae, 0x99),
                Color.rgb(0x8c, 0x7b, 0x6c), Color.rgb(0xa6, 0x68, 0x68)
        };

        int ncol = 6, nrow = 6;
        int arrSize = nrow*ncol;
        int num[] = new int[arrSize];
        int sum = 17;
        int dw, dh, y1;

        int[] generateSum(int size, int num){
            int sumArr[] = new int[size];
            Random random = new Random();

            int total = 0;
            for (int i=0; i < size -1; i++){
                int current = num - (total) - (size - i);
                int rnum = random.nextInt(current) + 1;

                sumArr[i] = rnum;
                total += rnum;
            }
            sumArr[sumArr.length-1] = num - total;
            return sumArr;
        }

        public void init(){

            int index = 0;
            for (int i = 0; i < 12; i++){
                int result[] = generateSum(3, sum);
                for (int n: result){
                    num[index++]=n;
                }
            }
        }

        public void shuffle(){
            Random rd = new Random();
            for (int i = 0; i<100; i++){
                int rnum = rd.nextInt(num.length);
                int t = num[0];
                num[0] = num[rnum];
                num[rnum] = t;
            }
        }


        public arithmeticView(Context context){
            super(context);
            init();
        }


        public void onDraw(Canvas canvas){

            int width = getWidth();
            int height = getHeight();
            float x1 = 0, y1 = height - width, x2 = width, y2 = height;

            Paint paint = new Paint();
            paint.setTextSize(100);
//            paint.setColor(Color.BLACK);

            canvas.drawRect(x1, y1, x2, y2, paint);

            String title = "Arithmetic";
            paint.setTextSize(9*width/100);

            float textWidth = paint.measureText(title);
            float x_t = (width/2) - (textWidth/2), y_t = 120;
            canvas.drawText(title, x_t, y_t, paint);
            canvas.drawLine(x_t, y_t+35, x_t + textWidth, y_t + 35, paint);

            dw = dh = width/ncol;
            paint.setStrokeWidth(4);
            paint.setTextSize(30*dw/100);

            int index = 0;
            for (int j = 0; j < nrow; j++){
                for (int i = 0; i < ncol; i++){
                    float x = i * dw;
                    float y = y1 + j * dh;

                    paint.setColor(color[index % color.length]);
                    canvas.drawRect( x, y, x + dw, y + dh, paint);

                    paint.setColor(Color.WHITE);
                    canvas.drawText(""+num[index], x+dw/2-15, y+dh/2+10, paint);

                    index++;
                }
            }

            String instruction = "Select numbers added up to:  " + sum;
            paint.setColor(Color.BLACK);
            canvas.drawText(instruction, 10, y1 - 50, paint);

        }

        public boolean onTouchEvent(MotionEvent event){

            super.onTouchEvent(event);
            int x = (int)event.getX();
            int y = (int)event.getY();
            int col = x/dw;
            int row = (y-y1)/dh;
            int index = row*ncol+col;

            Log.e("index", "index="+index+"num="+num[index]);

            return false;
        }
    }

}