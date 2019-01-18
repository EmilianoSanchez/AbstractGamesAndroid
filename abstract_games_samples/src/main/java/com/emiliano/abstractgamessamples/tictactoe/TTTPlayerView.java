package com.emiliano.abstractgamessamples.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.emiliano.abstractgameslib.core.Game;
import com.emiliano.abstractgameslib.core.GameImpl;
import com.emiliano.abstractgameslib.core.Player;

import java.util.List;

public class TTTPlayerView extends View implements Player<TTTState,TTTAction,GameImpl<TTTState,TTTAction>> {

    private static final int LINE_THICK = 5;
    private static final int ELT_MARGIN = 20;
    private static final int ELT_STROKE_WIDTH = 15;
    private int width, height, eltW, eltH;
    private Paint gridPaint, oPaint, xPaint;
    private Game<TTTState,TTTAction> gameEngine;
    private TTTActivity activity;

    public TTTPlayerView(Context context) {
        super(context);
    }

    public TTTPlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);
    }

    public void setMainActivity(TTTActivity a) {
        activity = a;
    }

    public void setGameEngine(Game g) {
        gameEngine = g;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        eltW = (width - LINE_THICK) / 3;
        eltH = (height - LINE_THICK) / 3;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameEngine.isGameEnd() && userPlays &&  event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / eltW);
            int y = (int) (event.getY() / eltH);

            userPlays = false;
            boolean correctAction = gameEngine.executeAction(new TTTAction(x, y,0));
            if(correctAction) {
                invalidate();
            }else{
                userPlays = true;
            }

        }

        return super.onTouchEvent(event);
    }

    private void drawBoard(Canvas canvas) {
        TTTState state = gameEngine.getCurrentState();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawElt(canvas, state.getElt(i, j), i, j);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            // vertical lines
            float left = eltW * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gridPaint);

            // horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = eltH * (i + 1);
            float bottom2 = top2 + LINE_THICK;

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    private void drawElt(Canvas canvas, char c, int x, int y) {
        if (c == 'O') {
            float cx = (eltW * x) + eltW / 2;
            float cy = (eltH * y) + eltH / 2;

            canvas.drawCircle(cx, cy, Math.min(eltW, eltH) / 2 - ELT_MARGIN * 2, oPaint);

        } else if (c == 'X') {
            float startX = (eltW * x) + ELT_MARGIN;
            float startY = (eltH * y) + ELT_MARGIN;
            float endX = startX + eltW - ELT_MARGIN * 2;
            float endY = startY + eltH - ELT_MARGIN;

            canvas.drawLine(startX, startY, endX, endY, xPaint);

            float startX2 = (eltW * (x + 1)) - ELT_MARGIN;
            float startY2 = (eltH * y) + ELT_MARGIN;
            float endX2 = startX2 - eltW + ELT_MARGIN * 2;
            float endY2 = startY2 + eltH - ELT_MARGIN;

            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }

    @Override
    public void onGameInit(GameImpl<TTTState,TTTAction> game) {

    }

    boolean userPlays = false;

    @Override
    public void onSelectAction(TTTState currentState,List<TTTAction> validActions) {
        Log.i("TTT","onSelectAction: "+validActions);
        this.userPlays=true;
    }

    @Override
    public void onActionSuccessfullyExecuted() {
        Log.i("TTT","onActionSuccessfullyExecuted");
        invalidate();
    }

    @Override
    public void onGameEnd() {
        double[] playerPayoffs = this.gameEngine.getPlayerPayoffs();
        char win='T';
        if(playerPayoffs[0]>playerPayoffs[1])
            win='X';
        else if(playerPayoffs[0]<playerPayoffs[1])
            win='O';

        activity.gameEnded(win);

    }
}