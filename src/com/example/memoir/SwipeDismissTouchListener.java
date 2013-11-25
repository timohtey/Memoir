package com.example.memoir;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class SwipeDismissTouchListener implements OnTouchListener {
	 private int mSlop;
	    private int mMinFlingVelocity;
	    private int mMaxFlingVelocity;
	    private long mAnimationTime;

	    // Fixed properties
	    private View mView;
	    private DismissCallbacks mCallbacks;
	    private int mViewWidth = 1; // 1 and not 0 to prevent dividing by zero

	    // Transient properties
	    private float mDownX;
	    private boolean mSwiping;
	    private Object mToken;
	    private VelocityTracker mVelocityTracker;
	    private float mTranslationX;

	    /**
	     * The callback interface used by {@link SwipeDismissTouchListener} to inform its client
	     * about a successful dismissal of the view for which it was created.
	     */
	    public interface DismissCallbacks {
	        /**
	         * Called to determine whether the view can be dismissed.
	         */
	        boolean canDismiss(Object token);

	        /**
	         * Called when the user has indicated they she would like to dismiss the view.
	         *
	         * @param view  The originating {@link View} to be dismissed.
	         * @param token The optional token passed to this object's constructor.
	         */
	        void onDismiss(View view, Object token);
	    }

	    /**
	     * Constructs a new swipe-to-dismiss touch listener for the given view.
	     *
	     * @param view     The view to make dismissable.
	     * @param token    An optional token/cookie object to be passed through to the callback.
	     * @param callbacks The callback to trigger when the user has indicated that she would like to
	     *                 dismiss this view.
	     */
	    public SwipeDismissTouchListener(View view, Object token, DismissCallbacks callbacks) {
	        ViewConfiguration vc = ViewConfiguration.get(view.getContext());
	        mSlop = vc.getScaledTouchSlop();
	        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
	        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
	        mAnimationTime = view.getContext().getResources().getInteger(
	                android.R.integer.config_shortAnimTime);
	        mView = view;
	        mToken = token;
	        mCallbacks = callbacks;
	    }

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// offset because the view is translated during swipe
			event.offsetLocation(mTranslationX, 0);

	        if (mViewWidth < 2) {
	            mViewWidth = mView.getWidth();
	        }

	        switch (event.getActionMasked()) {
	            case MotionEvent.ACTION_DOWN: {
	                // TODO: ensure this is a finger, and set a flag
	                mDownX = event.getRawX();
	                if (mCallbacks.canDismiss(mToken)) {
	                    mVelocityTracker = VelocityTracker.obtain();
	                    mVelocityTracker.addMovement(event);
	                }
	                return false;
	            }

	            case MotionEvent.ACTION_UP: {
	                if (mVelocityTracker == null) {
	                    break;
	                }

	                float deltaX = event.getRawX() - mDownX;
	                mVelocityTracker.addMovement(event);
	                mVelocityTracker.computeCurrentVelocity(1000);
	                float velocityX = mVelocityTracker.getXVelocity();
	                float absVelocityX = Math.abs(velocityX);
	                float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
	                boolean dismiss = false;
	                boolean dismissRight = false;
	                if (Math.abs(deltaX) > mViewWidth / 2) {
	                    dismiss = true;
	                    dismissRight = deltaX > 0;
	                } else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity
	                        && absVelocityY < absVelocityX) {
	                    // dismiss only if flinging in the same direction as dragging
	                    dismiss = (velocityX < 0) == (deltaX < 0);
	                    dismissRight = mVelocityTracker.getXVelocity() > 0;
	                }
	                if (dismiss) {
	                    // dismiss
	                    mView.animate()
	                            .translationX(dismissRight ? mViewWidth : -mViewWidth)
	                            .alpha(0)
	                            .setDuration(mAnimationTime)
	                            .setListener(new AnimatorListenerAdapter() {
	                                @Override
	                                public void onAnimationEnd(Animator animation) {
	                                    performDismiss();
	                                }
	                            });
	                } else if (mSwiping) {
	                    // cancel
	                    mView.animate()
	                            .translationX(0)
	                            .alpha(1)
	                            .setDuration(mAnimationTime)
	                            .setListener(null);
	                }
	                mVelocityTracker.recycle();
	                mVelocityTracker = null;
	                mTranslationX = 0;
	                mDownX = 0;
	                mSwiping = false;
	                break;
	            }

	            case MotionEvent.ACTION_CANCEL: {
	                if (mVelocityTracker == null) {
	                    break;
	                }

	                mView.animate()
	                        .translationX(0)
	                        .alpha(1)
	                        .setDuration(mAnimationTime)
	                        .setListener(null);
	                mVelocityTracker.recycle();
	                mVelocityTracker = null;
	                mTranslationX = 0;
	                mDownX = 0;
	                mSwiping = false;
	                break;
	            }

	            case MotionEvent.ACTION_MOVE: {
	                if (mVelocityTracker == null) {
	                    break;
	                }

	                mVelocityTracker.addMovement(event);
	                float deltaX = event.getRawX() - mDownX;
	                if (Math.abs(deltaX) > mSlop) {
	                    mSwiping = true;
	                    mView.getParent().requestDisallowInterceptTouchEvent(true);

	                    // Cancel listview's touch
	                    MotionEvent cancelEvent = MotionEvent.obtain(event);
	                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
	                        (event.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
	                    mView.onTouchEvent(cancelEvent);
	                    cancelEvent.recycle();
	                }

	                if (mSwiping) {
	                    mTranslationX = deltaX;
	                    mView.setTranslationX(deltaX);
	                    mView.setAlpha(Math.max(0f, Math.min(1f,
	                            1f - 2f * Math.abs(deltaX) / mViewWidth)));
	                    return true;
	                }
	                break;
	            }
	        }
			return false;
		}

	    private void performDismiss() {
	        // Animate the dismissed view to zero-height and then fire the dismiss callback.
	        // This triggers layout on each animation frame; in the future we may want to do something
	        // smarter and more performant.

	        final ViewGroup.LayoutParams lp = mView.getLayoutParams();
	        final int originalHeight = mView.getHeight();

	        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);

	        animator.addListener(new AnimatorListenerAdapter() {
	            @Override
	            public void onAnimationEnd(Animator animation) {
	                mCallbacks.onDismiss(mView, mToken);
	                // Reset view presentation
	                mView.setAlpha(1f);
	                mView.setTranslationX(0);
	                lp.height = originalHeight;
	                mView.setLayoutParams(lp);
	            }
	        });

	        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	            @Override
	            public void onAnimationUpdate(ValueAnimator valueAnimator) {
	                lp.height = (Integer) valueAnimator.getAnimatedValue();
	                mView.setLayoutParams(lp);
	            }
	        });

	        animator.start();
	    }


}
