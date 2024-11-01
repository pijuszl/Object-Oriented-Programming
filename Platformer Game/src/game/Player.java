package game;

import processing.core.PImage;

public class Player {

    //animation
    PImage image;
    int imageH, imageW;
    int charTileCount;
    PImage[] animation;
    float switchTime = 0;
    float switchSpeed;
    int currentAnimation = 0;

    //position
    float posX = 0;
    float posY = 0;

    //hitbox
    float posX1, posY1;
    float posX2, posY2;
    float posX3, posY3;
    float posX4, posY4;
    int tileX1, tileY1;
    int tileX2, tileY2;

    //movement
    float dx;
    float maxSpeedX = 6;
    float maxSpeedY = 8;
    float speedX = 0;
    float speedY = 0;
    float acc = 0.6f;
    float gravity = 0.4f;

    //states
    boolean onLadder = false;
    boolean isClimbingUp = false;
    boolean isClimbingDown = false;
    boolean inAir = false;
}
