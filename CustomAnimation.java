/**********************************************************/
/*  Author:      Brendan Lilly                            */
/*  Course:      CSC421                                   */
/*  Professor:   Spiegel                                  */
/*  Assignment:  3                                        */
/*  Due Date:    TBD                                      */
/*  Start Date:  2/2/2016                                 */
/*  Filename:    CustomAnimation.java                     */
/*  Purpose:     Creates a custom animation               */
/*  Time:        Write: Way to damn long.                 */
/*               Debug: Way to damn long.                 */
/*               Test:  Way to damn long.                 */
/**********************************************************/

package com.client;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.Window;

public class CustomAnimation extends Animation {
   
    /**
     * Image reference that will be updated
     */
    Image frame = new Image();
    int frameCount = 0;
    // Pop the popup panel
    PopupPanel animation = new PopupPanel();
   
      
    /**
     * Called while it runs
     */
    @Override
    protected void onUpdate(double progress) {
        if(frameCount==51)
        {
            frameCount=0;
        }
       frame.setUrl("Animations/superior/frame_"+frameCount+"_delay-0.1s.gif");
       frameCount++;
    }
   
    @Override
    protected void onComplete() {
        super.onComplete();
    }
   
    /**
     * Runs on startup
     */
    @Override
    protected void onStart() {
        super.onStart();

        animation.add(frame);
        animation.setPopupPosition(300,300);
        animation.show();
    }

}