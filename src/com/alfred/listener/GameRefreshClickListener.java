/**
 * 
 */
package com.alfred.listener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.alfred.R;
import com.alfred.layout.GameView;
import com.alfred.utils.Config;
import com.alfred.utils.ProviderUtils;

/**
 * 描述:
 * 
 * @author LGF
 * @version Ver1.0 2015年6月9日
 * @Update History Copyright (c) 中国石油化工股份有限公司
 */
public class GameRefreshClickListener implements OnMenuItemClickListener {

	private GameView gameView;

	public GameRefreshClickListener() {
		gameView = GameView.getGameView();
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Builder builder = new AlertDialog.Builder(gameView.getContext());
		builder.setTitle("系统提示");
		builder.setMessage("是否重新开始?");
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {// 添加确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
						Config.isFirst = true;
						ProviderUtils.setInt(gameView.getContext(), R.string.best, gameView.getBest());
						Log.e("sky", ProviderUtils.getInt(gameView.getContext(), R.string.best) + "");
						gameView.startGame();
					}

				});
		builder.setNegativeButton("否", new DialogInterface.OnClickListener() {// 添加返回按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {// 响应事件
					}
				});
		builder.setCancelable(false);
		builder.show();
		return true;
	}

}
