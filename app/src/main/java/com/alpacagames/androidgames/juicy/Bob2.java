package com.alpacagames.androidgames.juicy;



import com.alpacagames.androidgames.framework.DynamicGameObject;

public class Bob2 extends DynamicGameObject{
    public static final int BOB_STATE_JUMP = 0;
    public static final int BOB_STATE_FALL = 1;
    public static final int BOB_STATE_HIT = 2;
    public static float BOB_JUMP_VELOCITY = -4;
   
  
    public static final float BOB_WIDTH = World.WORLD_WIDTH;
    public static final float BOB_HEIGHT = 0.5f;
    
    int state;
    float stateTime;    
    
  
    public Bob2(float x, float y) {
        super(x, y, BOB_WIDTH, BOB_HEIGHT);
        state = BOB_STATE_FALL;
        velocity.set(0, BOB_JUMP_VELOCITY);
     
        stateTime = 0;        
    }

    public void update(float deltaTime, float speed) {
        velocity.set(0, speed);
    	//velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
       // if (state == BOB_STATE_FALL) { 
       // 	velocity.set (0, BOB_JUMP_VELOCITY ++);
        //}
        //if (BOB_JUMP_VELOCITY > 3)
        //	BOB_JUMP_VELOCITY -- ;
        if(position.x < 0)
            position.x = World.WORLD_WIDTH;
        if(position.x > World.WORLD_WIDTH)
            position.x = 0;
        
        stateTime += deltaTime;
    }
    
    
}
