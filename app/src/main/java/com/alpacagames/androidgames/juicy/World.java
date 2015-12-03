package com.alpacagames.androidgames.juicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.alpacagames.androidgames.framework.math.OverlapTester;
import com.alpacagames.androidgames.framework.math.Vector2;

public class World {
	public interface WorldListener {
		public void jump();

		public void highJump();

		public void hit();

		public void coin();
	}

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * -10;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -70);
	public float cloudspeed = -3;

	public final Bob bob;
	public final Bob2 bob2;
	public final List<Platform> platforms;
	public final List<Spring> springs;
	public final List<Star> stars;
	public final List<Jelly> jellys;
	public final List<Squirrel> squirrels;
	public final List<Coin> coins;
	public Castle castle;
	public final WorldListener listener;
	public final Random rand;

	public float heightSoFar;
	public int score;
	public int state;

	public World(WorldListener listener) {
		this.bob = new Bob(5, 8);
		this.bob2 = new Bob2(5,14);
		this.platforms = new ArrayList<Platform>();
		this.springs = new ArrayList<Spring>();
		this.squirrels = new ArrayList<Squirrel>();
		this.coins = new ArrayList<Coin>();
		this.stars = new ArrayList<Star>();
		this.jellys = new ArrayList<Jelly>();
		this.listener = listener;
		rand = new Random();
		generateLevel();
		this.heightSoFar = 0;
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel() {
		float y = 4;
		float juicycount = 0;
		int type = 0;
		int prevtype = 0;
		float prevx = 0;
		while (y > WORLD_HEIGHT ) {
			float x;
			if (rand.nextFloat() > 0.8f && prevtype != Platform.PLATFORM_TYPE_MOVING) {
				type = Platform.PLATFORM_TYPE_MOVING;
			} else {
				type = Platform.PLATFORM_TYPE_STATIC;
			}
			if (prevx <= 2) {
				x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + rand.nextFloat() * 2;
			} else if (prevx >= 4) {
				x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) - rand.nextFloat() * 2;
			} else {
				x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) - rand.nextFloat() * 2 + rand.nextFloat() * 2;
			}
			prevx = x;
			Platform platform = new Platform(type, x, y);
			platforms.add(platform);
			Platform platform2;
			if (prevtype == Platform.PLATFORM_TYPE_MOVING && rand.nextFloat() > 0.6f) {
				platform2 = new Platform(type, x + 8, y);
			} else if (type == Platform.PLATFORM_TYPE_STATIC && rand.nextFloat() > 0.5f && prevx <= 4 && prevx >= 2) {
				platform2 = new Platform(type, x + 3, y);
			} else {
				platform2 = new Platform(type, x + 7, y);
			}
			platforms.add(platform2);
			prevtype = type;

			if (rand.nextFloat() > 0.8f
					&& type != Platform.PLATFORM_TYPE_MOVING) {
				Spring spring = new Spring(platform.position.x,
						platform.position.y + Platform.PLATFORM_HEIGHT / 2
								+ Spring.SPRING_HEIGHT / 2);
				springs.add(spring);
			}

			if (y < (WORLD_HEIGHT * 9 / 10)) {
				if (rand.nextFloat() > 0.5f) {
					Squirrel squirrel = new Squirrel(platform.position.x
							+ rand.nextFloat(), platform.position.y
							+ Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() / 2);
					squirrels.add(squirrel);
				}
			} else if (y < (WORLD_HEIGHT * 2 / 5)) {
				if (rand.nextFloat() > 0.6f) {
					Squirrel squirrel = new Squirrel(platform.position.x
							+ rand.nextFloat(), platform.position.y
							+ Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() / 2);
					squirrels.add(squirrel);
				}
			} else if (y < (WORLD_HEIGHT / 30)) {
				if (rand.nextFloat() > 0.7f) {
					Squirrel squirrel = new Squirrel(platform.position.x
							+ rand.nextFloat(), platform.position.y
							+ Squirrel.SQUIRREL_HEIGHT + rand.nextFloat() / 2);
					squirrels.add(squirrel);
				}
			}

			if (rand.nextFloat() > 0.6f) {
				Coin coin = new Coin(platform.position.x + rand.nextFloat(),
						platform.position.y + Coin.COIN_HEIGHT
								+ rand.nextFloat() / 2);
				coins.add(coin);
				if (rand.nextFloat() > 0.8f) {
					Coin coin2 = new Coin(platform.position.x + rand.nextFloat() * 4,
							platform.position.y + Coin.COIN_HEIGHT
									+ rand.nextFloat() / 2);
					coins.add(coin2);
				}
			}

			if (rand.nextFloat() > 0.9f) {

				Star star = new Star(platform.position.x + rand.nextFloat(),
						platform.position.y + Platform.PLATFORM_HEIGHT / 2 + Star.STAR_HEIGHT / 2);

				stars.add(star);
			}
			if (rand.nextFloat() > 0.6f && type == Platform.PLATFORM_TYPE_STATIC) {
				if (juicycount - 12 > y) {
					Jelly jelly = new Jelly(platform.position.x + rand.nextFloat(),
							platform.position.y + Platform.PLATFORM_HEIGHT / 2 + Jelly.JELLY_HEIGHT / 2);

					jellys.add(jelly);
					juicycount = y;
				}
			}

			y -=2;
		}

		castle = new Castle(WORLD_WIDTH / 2, WORLD_HEIGHT);
	}

	public void update(float deltaTime, float accelX) {
		score += 1;
		updateBob(deltaTime, accelX);
		if (score < 300) {
			cloudspeed = -2;
		} else if (bob.position.y + 2 > bob2.position.y) {
			cloudspeed = -1;
		} else if (bob.position.y + 6 > bob2.position.y) {
			cloudspeed = -2;
		} else if (bob.position.y + 10 > bob2.position.y) {
			cloudspeed = -3;
		} else if (bob.position.y + 20 > bob2.position.y) {
			cloudspeed = -4;
		} else {
			cloudspeed = -5;
		}
		updateBob2(deltaTime, cloudspeed);



		updatePlatforms(deltaTime);
		updateSquirrels(deltaTime);
		updateCoins(deltaTime);
		updateStars(deltaTime);
		updateJellys(deltaTime);
		checkGameOver();
		if (state == WORLD_STATE_GAME_OVER) {
			bob.velocity.x = 0;
			bob.velocity.y = 15;
		}
		if (bob.state != Bob.BOB_STATE_HIT && bob.state != Bob.BOB_STATE_POWER)
			checkCollisions();
	}



	private void updateBob(float deltaTime, float accelX) {
		if (bob.state == Bob.BOB_STATE_HIT) {
			bob.velocity.y = 15;
			bob.velocity.x = 0;
		}
		if (bob.state != Bob.BOB_STATE_HIT)
			bob.velocity.x = -accelX / 10 * Bob.BOB_MOVE_VELOCITY;
		bob.update(deltaTime);
		heightSoFar = Math.min(bob.position.y, heightSoFar);
	}

	private void updateBob2(float deltaTime, float cloudspeed) {
		//bob2.position.x = WORLD_WIDTH / 2;
		bob2.update(deltaTime, cloudspeed);


	}

	private void updatePlatforms(float deltaTime) {
		int len = platforms.size();
		for (int i = 0; i < len; i++) {
			Platform platform = platforms.get(i);
			platform.update(deltaTime);
			if (platform.state == Platform.PLATFORM_STATE_PULVERIZING
					&& platform.stateTime > Platform.PLATFORM_PULVERIZE_TIME) {
				platforms.remove(platform);
				len = platforms.size();
			}
		}
	}

	private void updateSquirrels(float deltaTime) {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			squirrel.update(deltaTime);
		}
	}

	private void updateCoins(float deltaTime) {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}

	private void updateStars(float deltaTime) {
		int len = stars.size();
		for (int i = 0; i < len; i++) {
			Star star = stars.get(i);
			star.update(deltaTime);
		}
	}

	private void updateJellys(float deltaTime) {
		int len = jellys.size();
		for (int i = 0; i < len; i++) {
			Jelly jelly = jellys.get(i);
			jelly.update(deltaTime);
		}
	}

	private void checkCollisions() {
		checkPlatformCollisions();
		checkSquirrelCollisions();
		checkItemCollisions();
		checkStarCollisions();
		checkCastleCollisions();

	}

	private void checkPlatformCollisions() {
		if (bob.velocity.y > 0)
			return;

		int len = platforms.size();

		int length2= jellys.size();
		for (int i = 0; i < len; i++) {
			Platform platform = platforms.get(i);
			if (bob.position.y > platform.position.y) {
				if (OverlapTester
						.topOverlapRectangles(bob.bounds, platform.bounds)) {
					bob.hitPlatform(platform);
					//listener.jump();



					for (int k = 0; k < length2; k++) {
						Jelly jelly = jellys.get(k);
						if (OverlapTester.overlapRectangles(bob.bounds, jelly.bounds)) {

							bob.pulvplats+=5;
							jellys.remove(jelly);
							length2 = jellys.size();

							listener.highJump();

						}
					}


				}
				if(bob.pulvplats>0){
					platform.pulverize();
					bob.pulvplats--;

				}
			}
		}

	}


	private void checkSquirrelCollisions() {
		int len = squirrels.size();
		for (int i = 0; i < len; i++) {
			Squirrel squirrel = squirrels.get(i);
			if (OverlapTester.overlapRectangles(squirrel.bounds, bob.bounds)) {
				bob.hitSquirrel();
				listener.hit();
				state = WORLD_STATE_GAME_OVER;
			}
		}
	}

	private void checkItemCollisions() {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			if (OverlapTester.overlapRectangles(bob.bounds, coin.bounds)) {
				coins.remove(coin);
				len = coins.size();
				listener.coin();
				score += Coin.COIN_SCORE;
			}

		}

		if (bob.velocity.y > 0)
			return;

		len = springs.size();
		for (int i = 0; i < len; i++) {
			Spring spring = springs.get(i);
			if (bob.position.y > spring.position.y) {
				if (OverlapTester.overlapRectangles(bob.bounds, spring.bounds)) {
					bob.hitSpring();
					listener.jump();
				}
			}
		}

	}

	private void checkStarCollisions(){
		int length = stars.size();
		for (int j = 0; j < length; j++) {
			Star star = stars.get(j);
			if (OverlapTester.overlapRectangles(bob.bounds, star.bounds)) {

				//bob.pulvplats+=5;
				stars.remove(star);
				length = stars.size();
				listener.coin();
				score += Star.STAR_SCORE;




			}
		}
	}
	private void checkCastleCollisions() {
		if (bob.state != Bob.BOB_STATE_HIT) {
			if (OverlapTester.overlapRectangles(castle.bounds, bob.bounds)) {

				state = WORLD_STATE_NEXT_LEVEL;
				score += 400;
			}
			if (OverlapTester.overlapRectangles(castle.bounds, bob2.bounds)) {
				state = WORLD_STATE_GAME_OVER;
			}
		}
	}

	private void checkGameOver() {
		if (OverlapTester.overlapRectangles(bob.bounds, bob2.bounds)) {
			listener.hit();
			bob.hitSquirrel();
			state = WORLD_STATE_GAME_OVER;
		}
	}
}