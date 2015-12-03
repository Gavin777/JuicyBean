package com.alpacagames.androidgames.juicy;

import com.alpacagames.androidgames.framework.GameObject;

public class Star extends GameObject {
    public static float STAR_WIDTH = 0.6f;
    public static float STAR_HEIGHT = 0.6f;
    public static float STAR_SCORE = 200;
    
    float stateTime;
    public Star(float x, float y) {
        super(x, y, STAR_WIDTH, STAR_HEIGHT);
        stateTime=0;
    }        
    
    public void update(float deltaTime) {
        stateTime += deltaTime;
    }
}
