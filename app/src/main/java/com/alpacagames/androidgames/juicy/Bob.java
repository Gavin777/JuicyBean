package com.alpacagames.androidgames.juicy;

import com.alpacagames.androidgames.framework.DynamicGameObject;
import java.util.Random;

public class Bob extends DynamicGameObject{
    public static final int BOB_STATE_JUMP = 0;
    public static final int BOB_STATE_FALL = 1;
    public static final int BOB_STATE_HIT = 2;
    public static final int BOB_STATE_POWER = 3;
    public static final float BOB_JUMP_VELOCITY = 11;    
    public static final float BOB_MOVE_VELOCITY = 20;
    public static final float BOB_WIDTH = 0.8f;
    public static final float BOB_HEIGHT = 0.8f;
    Random rand = new Random();
    
    int state;
    int pulvplats = 0;
    float stateTime;    

    public Bob(float x, float y) {
        super(x, y, BOB_WIDTH, BOB_HEIGHT);
        state = BOB_STATE_FALL;
        stateTime = 0;        
    }

    public void update(float deltaTime) {
        if (velocity.y > -22) {
            velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        }
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
        if (state == BOB_STATE_HIT) {
            velocity.y = 15;
            velocity.x = 0;
        }
        if(velocity.y > 0 && state != BOB_STATE_HIT) {
            if(state != BOB_STATE_JUMP) {
                state = BOB_STATE_JUMP;
                stateTime = 0;
            }
        }
        
        if(velocity.y < 0 && state != BOB_STATE_HIT && state!= BOB_STATE_POWER) {
            if(state != BOB_STATE_FALL) {
                state = BOB_STATE_FALL;
                stateTime = 0;
            }
        }
        
        if(position.x < 0)
            position.x = World.WORLD_WIDTH;
        if(position.x > World.WORLD_WIDTH)
            position.x = 0;
        
        stateTime += deltaTime;
    }
    
    public void hitSquirrel() {
        velocity.x = 0;
        velocity.y = 15;
        state = BOB_STATE_HIT;        
        stateTime = 0;
    }
    
    public void hitPlatform(Platform platform) {
        if (position.y > platform.height + Platform.PLATFORM_HEIGHT / 2) {
            position.y -= ((position.y - BOB_HEIGHT/2) - (platform.height + Platform.PLATFORM_HEIGHT * 9/20));
            velocity.y = 0;
        }



        if (rand.nextFloat() > 0.5f) {
            state = BOB_STATE_FALL;
        } else {
            state = BOB_STATE_JUMP;
        }


        stateTime = 0;

    }

    public void hitSpring() {
        velocity.y = BOB_JUMP_VELOCITY * 1.5f;
        state = BOB_STATE_JUMP;
        stateTime = 0;   
    }
    
    

public void hitStar() {
	velocity.y = -14;
	state = BOB_STATE_POWER;
	pulvplats += 5; 
	
	
	stateTime = 0;	
}

}
