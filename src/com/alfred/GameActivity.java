package com.alfred;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import com.alfred.layout.GameView;
import com.alfred.listener.GameRefreshClickListener;
import com.alfred.utils.Config;
import com.alfred.utils.ProviderUtils;
import com.alfred.view.Card;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initGame();
		setContentView(R.layout.activity_game);
	}

	private void initGame() {
		Config.cards = new Card[4][4];
		Config.emptys = new ArrayList<Point>();
		Config.theme = this.getTheme();
		Config.isFirst = ProviderUtils.getBoolean(getApplicationContext(), R.string.is_first);
		
	}

	@Override
	protected void onPause() {
		super.onPause();
//		GameView gameView = GameView.getGameView();
//		int score = gameView.getScore();
//		int best = gameView.getBest();
//		if (score > 0) {
//			Card[][] cards = Config.cards;
//			StringBuffer buffer = new StringBuffer();
//			for (int x = 0; x < 4; x++) {
//				for (int y = 0; y < 4; y++) {
//					buffer.append(cards[x][y].getNum() + ",");
//				}
//			}
//			ProviderUtils.setBooelan(getApplicationContext(), R.string.is_first, false);
//			ProviderUtils.setInt(getApplicationContext(), R.string.score, score);
//			ProviderUtils.setInt(getApplicationContext(), R.string.best, best);
//			ProviderUtils.setString(getApplicationContext(), R.string.cards, buffer.toString());
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 资源文件添加菜单
		new MenuInflater(this).inflate(R.menu.game, menu);
		// 代码添加菜单
		MenuItem item = menu.add("重新开始");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		item.setIcon(android.R.drawable.ic_popup_sync);
		item.setOnMenuItemClickListener(new GameRefreshClickListener());
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("系统提示");
			builder.setMessage("是否退出游戏？");
			builder.setPositiveButton("是", new DialogInterface.OnClickListener() {// 添加确定按钮
						@Override
						public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
							Config.isFirst = true;
							GameView gameView = GameView.getGameView();
							ProviderUtils.setInt(gameView.getContext(), R.string.best, gameView.getBest());
							finish();
						}
					});
			builder.setNegativeButton("否", new DialogInterface.OnClickListener() {// 添加返回按钮
						@Override
						public void onClick(DialogInterface dialog, int which) {// 响应事件
						}

					});
			builder.setCancelable(false);
			builder.show();

		}

		return false;

	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

}
