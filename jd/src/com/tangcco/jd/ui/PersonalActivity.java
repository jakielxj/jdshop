package com.tangcco.jd.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.tangcco.jd.R;
import com.tangcco.jd.ui.base.BaseActivity;
import com.tangcco.jd.utils.CommonTools;
import com.tangcco.jd.widgets.CustomScrollView;

public class PersonalActivity extends BaseActivity {

	private ImageView mBackgroundImageView = null;
	private Button mLoginButton = null;
	private CustomScrollView mScrollView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		findViewById();
		initView();
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		mBackgroundImageView = (ImageView) findViewById(R.id.personal_background_image);
		mLoginButton = (Button) findViewById(R.id.personal_login_button);
		mScrollView = (CustomScrollView) findViewById(R.id.personal_scrollView);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		mScrollView.setImageView(mBackgroundImageView);
		mLoginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommonTools.showShortToast(PersonalActivity.this, "ÉÔºó¿ª·Å");
			}
		});
	}
}
