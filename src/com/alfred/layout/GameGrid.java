package com.alfred.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;

import com.alfred.R;
import com.alfred.listener.GameTouchListener;
import com.alfred.utils.Config;
import com.alfred.view.Card;

/**
 * 描述:
 * 
 * @author LGF
 * @version Ver1.0 2015年5月29日
 * @Update History Copyright (c) 中国石油化工股份有限公司
 */
public class GameGrid extends GridLayout {
	private int column = 4;
	private GameView gameView;
	private Card[][] cards;

	/**
	 * @param context
	 * @param attrs
	 */
	public GameGrid(Context context, AttributeSet attrs) {
		super(context);
		setId(R.id.gameGrid);
		setColumnCount(column);
		gameView = GameView.getGameView();
		cards = Config.cards;
		GameTouchListener gameTouchListener = new GameTouchListener();
		setOnTouchListener(gameTouchListener);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int width = w / 4;
		initGrid(width, width);
		gameView.startGame();
	}

	private void initGrid(int width, int height) {
		Card card;
		LayoutParams params = new LayoutParams();
		params.setMargins(5, 5, 5, 5);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				card = new Card(getContext());
				card.setNum(0);
				cards[x][y] = card;
				card.setLayoutParams(params);
				this.addView(card, width, height);
			}
		}
	}

}
