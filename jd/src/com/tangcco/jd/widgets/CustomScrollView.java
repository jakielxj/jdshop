package com.tangcco.jd.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

	private View inner;// ����View

	private float y;// ���ʱy����

	private Rect normal = new Rect();// ����(����ֻ�Ǹ���ʽ��ֻ�������ж��Ƿ���Ҫ����.)

	private boolean isCount = false;// �Ƿ�ʼ����

	private boolean isMoveing = false;// �Ƿ�ʼ�ƶ�.

	private ImageView imageView;

	private int initTop, initbottom;// ��ʼ�߶�
	private int top, bottom;// �϶�ʱʱ�߶ȡ�

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/***
	 * ���� XML ������ͼ�������.�ú�����������ͼ�������ã�����������ͼ������֮��. ��ʹ���า���� onFinishInflate
	 * ������ҲӦ�õ��ø���ķ�����ʹ�÷�������ִ��.
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}

	/** touch �¼����� **/
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {
			commOnTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}

	/***
	 * �����¼�
	 * 
	 * @param ev
	 */
	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			top = initTop = imageView.getTop();
			bottom = initbottom = imageView.getBottom();
			break;

		case MotionEvent.ACTION_UP:

			isMoveing = false;
			// ��ָ�ɿ�.
			if (isNeedAnimation()) {

				animation();

			}
			break;
		/***
		 * �ų�����һ���ƶ����㣬��Ϊ��һ���޷���֪y���꣬ ��MotionEvent.ACTION_DOWN�л�ȡ������
		 * ��Ϊ��ʱ��MyScrollView��touch�¼����ݵ�����LIstView�ĺ���item����.���Դӵڶ��μ��㿪ʼ.
		 * Ȼ������ҲҪ���г�ʼ�������ǵ�һ���ƶ���ʱ���û��������0. ֮���¼׼ȷ�˾�����ִ��.
		 */
		case MotionEvent.ACTION_MOVE:

			final float preY = y;// ����ʱ��y����

			float nowY = ev.getY();// ʱʱy����
			int deltaY = (int) (nowY - preY);// ��������
			if (!isCount) {
				deltaY = 0; // ������Ҫ��0.
			}

			if (deltaY < 0 && top <= initTop)
				return;

			// �����������ϻ�������ʱ�Ͳ����ٹ�������ʱ�ƶ�����
			isNeedMove();

			if (isMoveing) {
				// ��ʼ��ͷ������
				if (normal.isEmpty()) {
					// ���������Ĳ���λ��
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());
				}

				// �ƶ�����
				inner.layout(inner.getLeft(), inner.getTop() + deltaY / 3,
						inner.getRight(), inner.getBottom() + deltaY / 3);

				top += (deltaY / 6);
				bottom += (deltaY / 6);
				imageView.layout(imageView.getLeft(), top,
						imageView.getRight(), bottom);
			}

			isCount = true;
			y = nowY;
			break;

		default:
			break;

		}
	}

	/***
	 * ��������
	 */
	public void animation() {

		TranslateAnimation taa = new TranslateAnimation(0, 0, top + 200,
				initTop + 200);
		taa.setDuration(200);
		imageView.startAnimation(taa);
		imageView.layout(imageView.getLeft(), initTop, imageView.getRight(),
				initbottom);

		// �����ƶ�����
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// ���ûص������Ĳ���λ��
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();

		isCount = false;
		y = 0;// ��ָ�ɿ�Ҫ��0.

	}

	// �Ƿ���Ҫ��������
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/***
	 * �Ƿ���Ҫ�ƶ����� inner.getMeasuredHeight():��ȡ���ǿؼ����ܸ߶�
	 * 
	 * getHeight()����ȡ������Ļ�ĸ߶�
	 * 
	 * @return
	 */
	public void isNeedMove() {
		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		// Log.e("jj", "scrolly=" + scrollY);
		// 0�Ƕ����������Ǹ��ǵײ�
		if (scrollY == 0 || scrollY == offset) {
			isMoveing = true;
		}
	}

}