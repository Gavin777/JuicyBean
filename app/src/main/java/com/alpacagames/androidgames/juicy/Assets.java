package com.alpacagames.androidgames.juicy;

import com.alpacagames.androidgames.framework.Music;
import com.alpacagames.androidgames.framework.Sound;
import com.alpacagames.androidgames.framework.gl.Animation;
import com.alpacagames.androidgames.framework.gl.Font;
import com.alpacagames.androidgames.framework.gl.Texture;
import com.alpacagames.androidgames.framework.gl.TextureRegion;
import com.alpacagames.androidgames.framework.impl.GLGame;

public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    
    public static Texture items;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScoresRegion;
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion arrow;
    public static TextureRegion pause;    
    public static TextureRegion spring;
    public static TextureRegion castle;
    public static TextureRegion star;
    public static TextureRegion jelly;
    public static Animation coinAnim;
    public static TextureRegion bobJump;
    public static TextureRegion bobFall;
    public static TextureRegion bobHit;
    public static TextureRegion bobPower;
    public static Animation squirrelFly;
    public static TextureRegion platform;
    public static Animation brakingPlatform;  
    public static TextureRegion poison;
    public static Font font;
    
    public static Music music;
    public static Sound jumpSound;
    public static Sound highJumpSound;
    public static Sound hitSound;
    public static Sound coinSound;
    public static Sound clickSound;
    
    public static void load(GLGame game) {
        background = new Texture(game, "background.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);

        items = new Texture(game, "items.png");
        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
        pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
        ready = new TextureRegion(items, 300, 224, 204, 32);
        gameOver = new TextureRegion(items, 352, 256, 160, 96);
        highScoresRegion = new TextureRegion(Assets.items, 0, 261, 301, 110 / 3);
        logo = new TextureRegion(items, 0, 338, 300, 166);
        soundOff = new TextureRegion(items, 0, 0, 64, 64);
        soundOn = new TextureRegion(items, 64, 0, 64, 64);
        arrow = new TextureRegion(items, 0, 64, 64, 64);
        pause = new TextureRegion(items, 64, 64, 64, 64);
        
        star = new TextureRegion(items,160,0,32,32);
        jelly = new TextureRegion(items,192,0,32,32);
        spring = new TextureRegion(items, 128, 0, 32, 32);
        castle = new TextureRegion(items, 0, 810, 260, 260);
        coinAnim = new Animation(0.2f,                                 
                                 new TextureRegion(items, 128, 32, 32, 32),
                                 new TextureRegion(items, 160, 32, 32, 32),
                                 new TextureRegion(items, 192, 32, 32, 32),
                                 new TextureRegion(items, 160, 32, 32, 32));
        bobHit = new TextureRegion(items, 512, 540, 256, 256);
        bobJump = new TextureRegion(items, 0, 540, 256, 256);
        bobFall = new TextureRegion(items, 256, 540, 256, 256);
        bobPower = bobFall;
        squirrelFly = new Animation(0.2f, 
                                    new TextureRegion(items, 0, 160, 32, 32),
                                    new TextureRegion(items, 32, 160, 32, 32));
        platform = new TextureRegion(items, 64, 160, 160, 16);
        brakingPlatform = new Animation(0.2f,
                                     new TextureRegion(items, 64, 160, 160, 16),
                                     new TextureRegion(items, 64, 176, 160, 16),
                                     new TextureRegion(items, 64, 192, 160, 16));
                                    // new TextureRegion(items, 64, 208, 64, 16));
        
        poison = new TextureRegion(items, 64, 208, 160,16);
        font = new Font(items, 224, 0, 16, 16, 20);
        
        music = game.getAudio().newMusic("music.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        if(Settings.soundEnabled)
            music.play();
        jumpSound = game.getAudio().newSound("jump.ogg");
        highJumpSound = game.getAudio().newSound("highjump.ogg");
        hitSound = game.getAudio().newSound("hit.ogg");
        coinSound = game.getAudio().newSound("coin.ogg");
        clickSound = game.getAudio().newSound("click.ogg");       
    }       
    
    public static void reload() {
        background.reload();
        items.reload();
        if(Settings.soundEnabled)
            music.play();
    }
    
    public static void playSound(Sound sound) {
        if(Settings.soundEnabled)
            sound.play(1);
    }
}
