package com.alfred.layout;

import java.util.List;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alfred.R;
import com.alfred.utils.Config;
import com.alfred.utils.ProviderUtils;
import com.alfred.view.Card;

/**
 * 描述:
 * 
 * @author LGF
 * @version Ver1.0 2015年5月29日
 * @Update History Copyright (c) 中国石油化工股份有限公司
 */
public class GameView extends RelativeLayout {

	private static GameView gameView;
	private int gameWidth, gameHeight, score, best;
	private String scoreStr = "SCORE\n", bestStr = "BEST\n";
	private TextView scoreView, bestView;
	private ImageView center, surface;
	private RelativeLayout gridRelativeLayout;
	private GameGrid gameGrid;
	private Card[][] cards;
	private List<Point> emptys;

	/**
	 * @param context
	 * @param attrs
	 */
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		gameView = this;
		cards = Config.cards;
		emptys = Config.emptys;
		this.setBackgroundResource(R.drawable.z_bg);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		gameHeight = h;
		gameWidth = w;
		scoreView = (TextView) findViewById(R.id.scoreView);
		bestView = (TextView) findViewById(R.id.bestView);
		gridRelativeLayout = (RelativeLayout) findViewById(R.id.gridRelativeLayout);
		gameGrid = (GameGrid) findViewById(R.id.gameGrid);
		center = (ImageView) findViewById(R.id.center);
		surface = (ImageView) findViewById(R.id.surface);
		initGameView();
	}

	private void initGameView() {
		LayoutParams relativeParams = new LayoutParams(LayoutParams.MATCH_PARENT, gameHeight - gameWidth);
		gridRelativeLayout.setLayoutParams(relativeParams);
		LayoutParams imgParams = new LayoutParams(gameHeight - gameWidth, gameHeight - gameWidth);
		center.setLayoutParams(imgParams);
		surface.setLayoutParams(imgParams);
		ObjectAnimator centerAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(),
				R.animator.center_animator);
		LinearInterpolator interpolator = new LinearInterpolator();
		centerAnimator.setInterpolator(interpolator);
		centerAnimator.setTarget(center);
		centerAnimator.start();

		ObjectAnimator surfaceAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(),
				R.animator.surface_animator);
		surfaceAnimator.setTarget(surface);
		surfaceAnimator.setInterpolator(interpolator);
		surfaceAnimator.start();

		LayoutParams gridParams = new LayoutParams(LayoutParams.MATCH_PARENT, gameWidth);
		gridParams.addRule(BELOW, R.id.gridRelativeLayout);
		gameGrid.setLayoutParams(gridParams);
		scoreView.setText(scoreStr + 0);
		bestView.setText(bestStr + 0);
	}

	public void clearScore() {
		score = 0;
		showScore();
	}

	public void addScore(int num) {
		score += num;
		showScore();
	}

	private void showScore() {
		if (scoreView != null) {
			scoreView.setText(scoreStr + score);
			best = Math.max(best, score);
			bestView.setText(bestStr + best);
		}
	}

	public void startGame() {
		best = ProviderUtils.getInt(getContext(), R.string.best);
		clearScore();
		Card card;
		if (Config.isFirst) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					card = cards[x][y];
					card.setNum(0);
				}
			}
			addRandomCard();
			addRandomCard();
		} else {
			score = ProviderUtils.getInt(getContext(), R.string.score);
			showScore();
			String cardString = ProviderUtils.getString(getContext(), R.string.cards);
			String[] cs = cardString.split(",");
			for (int i = 0; i < cs.length; i++) {
				int x = i / 4;
				int y = i - 4 * x;
				cards[x][y].setNum(Integer.parseInt(cs[i]));
			}
		}
	}

	public void addRandomCard() {
		emptys.clear();
		Card card;
		Point point;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				card = cards[x][y];
				if (card.getNum() == 0) {
					point = new Point(x, y);
					emptys.add(point);
				}
			}
		}
		int index = (int) (Math.random() * emptys.size());
		point = emptys.remove(index);
		card = cards[point.x][point.y];
		card.setNum(Math.random() > 0.8 ? 4 : 2);
		card.appear();
	}

	public boolean checkComplete() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Card card = cards[x][y];
				if (card.getNum() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 取得gameView
	 * 
	 * @return the gameView
	 */
	public static GameView getGameView() {
		return gameView;
	}

	/**
	 * 取得gameWidth
	 * 
	 * @return the gameWidth
	 */
	public int getGameWidth() {
		return gameWidth;
	}

	/**
	 * 取得score
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * 取得best
	 * 
	 * @return the best
	 */
	public int getBest() {
		return best;
	}

}
