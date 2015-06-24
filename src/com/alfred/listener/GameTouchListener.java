package com.alfred.listener;

import com.alfred.R;
import com.alfred.layout.GameView;
import com.alfred.utils.Config;
import com.alfred.utils.ProviderUtils;
import com.alfred.view.Card;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 描述:
 * 
 * @author LGF
 * @version Ver1.0 2015年5月29日
 * @Update History Copyright (c) 中国石油化工股份有限公司
 */
@SuppressLint("ClickableViewAccessibility")
public class GameTouchListener implements OnTouchListener {

	private float startX, startY, offsetX, offsetY;
	private Card[][] cards = Config.cards;
	private GameView gameView;
	private boolean isStop = false;

	public GameTouchListener() {
		gameView = GameView.getGameView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		boolean isMove = false;
		if (!isStop) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startX = event.getX();
				startY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				offsetX = event.getX() - startX;
				offsetY = event.getY() - startY;
				// 水平移动
				if (Math.abs(offsetX) > Math.abs(offsetY)) {
					// 右移动
					if (offsetX > 5) {
						swipeRight();
						isMove = true;
					} else if (offsetX < -5) {
						swipeLeft();
						isMove = true;
					}

				} else if (Math.abs(offsetY) > Math.abs(offsetX)) {
					// 下移动
					if (offsetY > 5) {
						swipeDown();
						isMove = true;
					} else if (offsetY < -5) {
						swipeUp();
						isMove = true;
					}

				}
				break;
			}
			if (isMove) {
				boolean isCom = gameView.checkComplete();
				if (!isCom) {
					gameView.addRandomCard();
				} else {
					isStop = true;
					Builder builder = new AlertDialog.Builder(gameView.getContext());
					builder.setTitle("系统提示");
					builder.setMessage("游戏结束,是否重新开始?");
					builder.setPositiveButton("是", new DialogInterface.OnClickListener() {// 添加确定按钮
								@Override
								public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
									Config.isFirst = true;
									ProviderUtils.setInt(gameView.getContext(), R.string.best, gameView.getBest());
									Log.e("sky", ProviderUtils.getInt(gameView.getContext(), R.string.best) + "");
									gameView.startGame();
									isStop = false;
								}

							});
					builder.setNegativeButton("否", new DialogInterface.OnClickListener() {// 添加返回按钮
								@Override
								public void onClick(DialogInterface dialog, int which) {// 响应事件
									ProviderUtils.setInt(gameView.getContext(), R.string.score, 0);
									ProviderUtils.setInt(gameView.getContext(), R.string.best, gameView.getBest());
									ProviderUtils.setBooelan(gameView.getContext(), R.string.is_first, true);
									isStop = false;
									android.os.Process.killProcess(android.os.Process.myPid());
								}

							});
					builder.setCancelable(false);
					builder.show();
				}

			}
		}

		return true;
	}

	private void swipeLeft() {
		Card card;
		Card afCard;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				card = cards[x][y];
				for (int y1 = y + 1; y1 < 4; y1++) {
					afCard = cards[x][y1];
					if (afCard.getNum() > 0) {
						if (card.equals(afCard)) {
							card.setNum(card.getNum() * 2);
							card.appear();
							afCard.setNum(0);
							gameView.addScore(card.getNum());
						} else if (card.getNum() == 0) {
							card.setNum(afCard.getNum());
							afCard.setNum(0);
							y--;
						}
						break;
					}
				}
			}
		}
	}

	private void swipeDown() {
		Card card;
		Card afCard;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				card = cards[x][y];
				for (int x1 = x - 1; x1 >= 0; x1--) {
					afCard = cards[x1][y];
					if (afCard.getNum() > 0) {
						if (card.equals(afCard)) {
							card.setNum(card.getNum() * 2);
							card.appear();
							afCard.setNum(0);
							gameView.addScore(card.getNum());
						} else if (card.getNum() == 0) {
							card.setNum(afCard.getNum());
							afCard.setNum(0);
							x++;
						}
						break;
					}
				}
			}
		}
	}

	private void swipeUp() {
		Card card;
		Card afCard;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				card = cards[x][y];
				for (int x1 = x + 1; x1 < 4; x1++) {
					afCard = cards[x1][y];
					if (afCard.getNum() > 0) {
						if (card.equals(afCard)) {
							card.setNum(card.getNum() * 2);
							card.appear();
							afCard.setNum(0);
							gameView.addScore(card.getNum());
						} else if (card.getNum() == 0) {
							card.setNum(afCard.getNum());
							afCard.setNum(0);
							x--;
						}
						break;
					}
				}
			}
		}
	}

	private void swipeRight() {
		Card card;
		Card afCard;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				card = cards[x][y];
				for (int y1 = y - 1; y1 >= 0; y1--) {
					afCard = cards[x][y1];
					if (afCard.getNum() > 0) {
						if (card.equals(afCard)) {
							card.setNum(card.getNum() * 2);
							card.appear();
							afCard.setNum(0);
							gameView.addScore(card.getNum());
						} else if (card.getNum() == 0) {
							card.setNum(afCard.getNum());
							afCard.setNum(0);
							y++;
						}
						break;
					}
				}
			}
		}
	}
}
