package com.alfred.view;

import com.alfred.R;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.widget.ImageView;

/**
 * 描述:
 * 
 * @author LGF
 * @version Ver1.0 2015年5月28日
 * @Update History Copyright (c) 中国石油化工股份有限公司
 */
public class Card extends ImageView {

	private int num;
	AnimatorSet appearAnimator ;

	/**
	 * @param context
	 */
	public Card(Context context) {
		super(context);
		appearAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
				R.animator.appear_animator);
		appearAnimator.setTarget(this);
	}

	public void setNum(int num) {
		int id = 0;
		switch (num) {
		case 0:
			id = R.drawable.z_00;
			break;
		case 2:
			id = R.drawable.z_01;
			break;
		case 4:
			id = R.drawable.z_02;
			break;
		case 8:
			id = R.drawable.z_03;
			break;
		case 16:
			id = R.drawable.z_04;
			break;
		case 32:
			id = R.drawable.z_05;
			break;
		case 64:
			id = R.drawable.z_06;
			break;
		case 128:
			id = R.drawable.z_07;
			break;
		case 256:
			id = R.drawable.z_08;
			break;
		case 512:
			id = R.drawable.z_09;
			break;
		case 1024:
			id = R.drawable.z_10;
			break;
		case 2048:
			id = R.drawable.z_11;
			break;
		case 4096:
			id = R.drawable.z_12;
			break;
		}
		this.setBackgroundResource(id);
		this.num = num;
	}

	public void appear() {
		appearAnimator.start();
	}

	public int getNum() {
		return num;
	}

	public boolean equals(Card c) {
		if (this.getNum() == c.getNum()) {
			return true;
		}
		return false;
	}

}
