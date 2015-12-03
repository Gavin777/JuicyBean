package com.alpacagames.androidgames.juicy;

import com.alpacagames.androidgames.framework.GameObject;

public class Jelly extends GameObject {
    public static float JELLY_WIDTH = 0.7f;
    public static float JELLY_HEIGHT = 0.7f;
    
    float stateTime;
    public Jelly(float x, float y) {
        super(x, y, JELLY_WIDTH, JELLY_HEIGHT);
        stateTime=0;
    }        
    
    public void update(float deltaTime) {
        stateTime += deltaTime;
    }
}
