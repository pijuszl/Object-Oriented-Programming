//Pijus  Zlatkus 4 grupe

package game;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Game extends PApplet
{
    //tilemap
    int tileSize = 16;
    PImage tilemap;
    int tilemapH, tilemapW;
    int tileCount;
    PImage[] tilesImages;

    JSONObject mapInfo;
    int[] tilesCollisions;
    int[] tilesDangers;
    int[] tilesLadders;
    int[] tilesFinishes;

    //menu
    int tileX, tileY;
    float menuX, menuY;
    int tileID = 0;
    boolean openedMenu = false;
    boolean chosenTile = false;

    //map
    int[][] maps;
    int[][] collisions;
    int[][] dangers;
    int[][] ladders;
    int[][] finishes;
    int mapW = 45;
    int mapH = 10;

    //levels
    int level = 0;
    JSONArray levels;

    //screen
    float screenX = 0;
    float screenY = 0;

    //character
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


    boolean onLadder = false;
    boolean isClimbingUp = false;
    boolean isClimbingDown = false;
    boolean inAir = false;

    public static void main(String[] args)
    {
        PApplet.main("game.Game");
    }

    public void settings()
    {
        size(1600, 640);
        noSmooth();
    }

    public void setup()
    {
        loadWorld();

        image = loadImage("character.png");
        imageW = image.width;
        imageH = image.height;
        charTileCount = (imageW * imageH) / (tileSize * tileSize);
        animation = new PImage[charTileCount];

        for (int i = 0; i < charTileCount; i++)
        {
            animation[i] = image.get(i * tileSize * 2, 0, tileSize * 2 , tileSize * 2);
        }

        posX = levels.getJSONObject(level).getInt("spawnX") * tileSize * 4;
        posY = levels.getJSONObject(level).getInt("spawnY") * tileSize * 4;
        generateMap();
    }


    public void draw()
    {
        if(level < 3)
        {
            if (level == 0)
            {
                background(84, 158, 108);
            }
            else if (level == 1)
            {
                background(134, 44, 58);
            }
            else if (level == 2)
            {
                background(54, 84, 125);
            }

            posX1 = posX + 6;
            posY1 = posY + 10;

            posX2 = posX + tileSize * 4 - 20.001f;
            posY2 = posY + 10;

            posX3 = posX + 6;
            posY3 = posY + tileSize * 4 - 0.001f;

            posX4 = posX + tileSize * 4 - 20.001f;
            posY4 = posY + tileSize * 4 - 0.001f;

            isClimbingUp = false;
            isClimbingDown = false;

            if(keyPressed && keyCode == RIGHT && !openedMenu)
            {
                tileX1 = (int) ((posX2 + speedX) / (tileSize * 4));
                tileY1 = (int) (posY2 / (tileSize * 4));

                tileX2 = (int) ((posX4 + speedX) / (tileSize * 4));
                tileY2 = (int) (posY4 / (tileSize * 4));

                if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0)
                {
                    movePlayer(1);

                    if (screenX - speedX >= -mapW * tileSize * 4 + width  && (-screenX) < (posX1 - 600))
                    {
                        screenX -= speedX;
                    }
                }
            }
            else if(keyPressed && keyCode == LEFT && !openedMenu)
            {
                tileX1 = (int) ((posX3 - speedX) / (tileSize * 4));
                tileY1 = (int) (posY3 / (tileSize * 4));

                tileX2 = (int) ((posX4 - speedX) / (tileSize * 4));
                tileY2 = (int) (posY4 / (tileSize * 4));

                if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0)
                {
                    movePlayer(-1);

                    if (screenX + (speedX / 2) < 0 && (-screenX) > (posX1 - 900))
                    {
                        screenX += speedX;
                    }
                }
            }
            else if(keyPressed && keyCode == UP && !openedMenu)
            {
                tileX1 = (int) (posX3 / (tileSize * 4));
                tileY1 = (int) ((posY3)/ (tileSize * 4));

                tileX2 = (int) (posX4 / (tileSize * 4));
                tileY2 = (int) ((posY4) / (tileSize * 4));

                if(ladders[level][tileY1 * mapW + tileX1] == 1 && ladders[level][tileY2 * mapW + tileX2] == 1)
                {
                    onLadder = true;

                    tileX1 = (int) (posX1 / (tileSize * 4));
                    tileY1 = (int) ((posY1 - 3) / (tileSize * 4));

                    tileX2 = (int) (posX2 / (tileSize * 4));
                    tileY2 = (int) ((posY2 - 3) / (tileSize * 4));

                    if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0)
                    {
                        posY -= 3;
                        isClimbingUp = true;
                    }
                }
                else
                {
                    onLadder = false;
                }

            }
            else if(keyPressed && keyCode == DOWN && !openedMenu)
            {
                tileX1 = (int) (posX3 / (tileSize * 4));
                tileY1 = (int) ((posY3)/ (tileSize * 4));

                tileX2 = (int) (posX4 / (tileSize * 4));
                tileY2 = (int) ((posY4) / (tileSize * 4));

                if(ladders[level][tileY1 * mapW + tileX1] == 1 && ladders[level][tileY2 * mapW + tileX2] == 1)
                {
                    onLadder = true;

                    tileX1 = (int) (posX1 / (tileSize * 4));
                    tileY1 = (int) ((posY3 + 3)/ (tileSize * 4));

                    tileX2 = (int) (posX2 / (tileSize * 4));
                    tileY2 = (int) ((posY4 + 3) / (tileSize * 4));

                    if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0)
                    {
                        posY += 3;
                        isClimbingDown = true;
                    }
                }
                else if (ladders[level][tileY1 * mapW + tileX1] == 0 && ladders[level][tileY2 * mapW + tileX2] == 0)
                {
                    tileX1 = (int) (posX1 / (tileSize * 4));
                    tileY1 = (int) ((posY3 + 3)/ (tileSize * 4));

                    tileX2 = (int) (posX2 / (tileSize * 4));
                    tileY2 = (int) ((posY4 + 3) / (tileSize * 4));

                    if(ladders[level][tileY1 * mapW + tileX1] == 1 && ladders[level][tileY2 * mapW + tileX2] == 1)
                    {
                        if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0)
                        {
                            posY += 3;
                            isClimbingDown = true;
                        }
                    }
                }
                else
                {
                    onLadder = false;
                }
            }

            tileX1 = (int) (posX3 / (tileSize * 4));
            tileY1 = (int) ((posY3)/ (tileSize * 4));

            tileX2 = (int) (posX4 / (tileSize * 4));
            tileY2 = (int) ((posY4) / (tileSize * 4));

            if((ladders[level][tileY1 * mapW + tileX1] == 0 || ladders[level][tileY2 * mapW + tileX2] == 0) && onLadder)
            {
                onLadder = false;
            }
            else if (dangers[level][tileY1 * mapW + tileX1] == 1 || dangers[level][tileY2 * mapW + tileX2] == 1)
            {
                restartGame();
            }
            else if (finishes[level][tileY1 * mapW + tileX1] == 1 || finishes[level][tileY2 * mapW + tileX2] == 1)
            {
                changeLevel();
                return;
            }

            if(speedY < 0 && !openedMenu)
            {
                tileX1 = (int) (posX1 / (tileSize * 4));
                tileY1 = (int) ((posY1 + speedY + gravity)/ (tileSize * 4));

                tileX2 = (int) (posX2 / (tileSize * 4));
                tileY2 = (int) ((posY2 + speedY + gravity) / (tileSize * 4));

                if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0 && !onLadder)
                {
                    fallPlayer();
                    inAir = true;
                }
                else
                {
                    speedY = 0;
                    inAir = false;
                }
            }
            else if (speedY >= 0 && !openedMenu)
            {
                tileX1 = (int) (posX3 / (tileSize * 4));
                tileY1 = (int) ((posY3 + speedY + gravity)/ (tileSize * 4));

                tileX2 = (int) (posX4 / (tileSize * 4));
                tileY2 = (int) ((posY4 + speedY + gravity) / (tileSize * 4));

                if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0  && !onLadder)
                {
                    tileX1 = (int) (posX3 / (tileSize * 4));
                    tileY1 = (int) ((posY3 + speedY + gravity)/ (tileSize * 4));

                    tileX2 = (int) (posX4 / (tileSize * 4));
                    tileY2 = (int) ((posY4 + speedY + gravity) / (tileSize * 4));


                    if((maps[level][tileY1 * mapW + tileX1] != 40 && maps[level][tileY1 * mapW + tileX1] != 41 && maps[level][tileY1 * mapW + tileX1] != 42) && (maps[level][tileY2 * mapW + tileX2] != 40 && maps[level][tileY2 * mapW + tileX2] != 41 && maps[level][tileY2 * mapW + tileX2] != 42))
                    {
                        fallPlayer();
                        inAir = true;
                    }
                    else
                    {
                        tileX1 = (int) (posX3 / (tileSize * 4));
                        tileY1 = (int) ((posY3)/ (tileSize * 4));

                        tileX2 = (int) (posX4 / (tileSize * 4));
                        tileY2 = (int) ((posY4) / (tileSize * 4));

                        if((maps[level][tileY1 * mapW + tileX1] == 40 || maps[level][tileY1 * mapW + tileX1] == 41 || maps[level][tileY1 * mapW + tileX1] == 42) || (maps[level][tileY2 * mapW + tileX2] == 40 || maps[level][tileY2 * mapW + tileX2] == 41 || maps[level][tileY2 * mapW + tileX2] == 42))
                        {
                            fallPlayer();
                            inAir = true;
                        }
                    }
                }
                else
                {
                    speedY = 0;
                    inAir = false;
                }
            }

            translate(screenX, screenY);
            drawWorld();
            displayPlayer();
            mapEditor();
        }
        else
        {
            background(15, 121, 153);
            translate(700, -200);
            textSize(128);
            textAlign(CENTER, CENTER);
            text("Congratulations!!!", 100, 500);
            textAlign(CENTER, BOTTOM);
            text("YOU WON", 100, 500);
        }

    }

    public void loadWorld()
    {
        tilemap = loadImage("world.png");
        tilemapW = tilemap.width;
        tilemapH = tilemap.height;
        tileCount = (tilemapH * tilemapW) / (tileSize * tileSize);
        tilesImages = new PImage[tileCount];

        mapInfo = loadJSONObject("tilemap.json");
        tilesCollisions = new int[tileCount];
        tilesDangers = new int[tileCount];
        tilesLadders = new int[tileCount];
        tilesFinishes = new int[tileCount];

        tilesCollisions = mapInfo.getJSONArray("collisions").getIntArray();
        tilesDangers = mapInfo.getJSONArray("dangers").getIntArray();
        tilesLadders = mapInfo.getJSONArray("ladders").getIntArray();
        tilesFinishes = mapInfo.getJSONArray("finishes").getIntArray();

        levels = loadJSONArray("levels.json");
        maps = new int[3][mapW * mapH];
        collisions = new int[3][mapW * mapH];
        dangers = new int[3][mapW * mapH];
        ladders = new int[3][mapW * mapH];
        finishes = new int[3][mapW * mapH];

        for(int i = 0; i < 3; i++)
        {
            maps[i] = levels.getJSONObject(i).getJSONArray("tiles").getIntArray();
            collisions[i] = levels.getJSONObject(i).getJSONArray("collisions").getIntArray();
            dangers[i] = levels.getJSONObject(i).getJSONArray("dangers").getIntArray();
            ladders[i] = levels.getJSONObject(i).getJSONArray("ladders").getIntArray();
            finishes[i] = levels.getJSONObject(i).getJSONArray("finish").getIntArray();
        }
    }

    public void saveWorld()
    {
        for(int i = 0; i < mapW * mapH; i++)
        {
            levels.getJSONObject(level).getJSONArray("tiles").setInt(i, maps[level][i]);
            levels.getJSONObject(level).getJSONArray("collisions").setInt(i, collisions[level][i]);
            levels.getJSONObject(level).getJSONArray("dangers").setInt(i, dangers[level][i]);
            levels.getJSONObject(level).getJSONArray("ladders").setInt(i, ladders[level][i]);
            levels.getJSONObject(level).getJSONArray("finish").setInt(i, finishes[level][i]);
        }

        String location = Game.class.getResource("/data/levels.json").getPath();
        saveJSONArray(levels, location);
    }

    public void restartGame()
    {
        speedX = 0;
        speedY = 0;
        posX = levels.getJSONObject(level).getInt("spawnX") * tileSize * 4 - 0.001f;
        posY = levels.getJSONObject(level).getInt("spawnY") * tileSize * 4 - 0.001f;
        screenX = 0;
        screenY = 0;
    }

    public void changeLevel()
    {
        level++;
        speedX = 0;
        speedY = 0;

        if(level < 3)
        {
            posX = levels.getJSONObject(level).getInt("spawnX") * tileSize * 4 - 0.001f;
            posY = levels.getJSONObject(level).getInt("spawnY") * tileSize * 4 - 0.001f;
            screenX = 0;
            screenY = 0;
        }
    }

    int s = 0;
    public void generateMap()
    {
        for (int j = 0; j < tilemapH; j += tileSize)
        {
            for (int i = 0; i < tilemapW; i += tileSize)
            {
                tilesImages[s++] = tilemap.get(i, j, tileSize, tileSize);

            }
        }
    }
    public void drawWorld()
    {
        for (int j = 0; j < mapW * mapH; j++)
        {
            image(tilesImages[maps[level][j]], (j % 45) * tileSize * 4, (j / 45) * tileSize * 4, tileSize * 4, tileSize * 4);
        }
    }

    public void mapEditor()
    {
        if(openedMenu && !chosenTile)
        {
            image(tilemap, -screenX, 0, 640, 640);
        }
        else if (openedMenu)
        {
            image(tilesImages[tileID], mouseX - screenX, mouseY, tileSize * 4, tileSize * 4);
            text(" " + tileID, mouseX - screenX, mouseY);
        }
    }

    public void displayPlayer()
    {
        if (speedY > 0 && !onLadder)
        {
            currentAnimation = 6;

            pushMatrix();
            if(dx == -1)
            {

                scale(-1, 1);
                image(animation[currentAnimation], -posX - tileSize * 4, posY - tileSize, tileSize * 5, tileSize * 5);
            }
            else
            {
                scale(1, 1);
                image(animation[currentAnimation], posX - tileSize, posY - tileSize, tileSize * 5, tileSize * 5);
            }
            popMatrix();
        }
        else if (speedY < 0 && !onLadder)
        {
            currentAnimation = 5;

            pushMatrix();
            if(dx == -1)
            {
                scale(-1, 1);
                image(animation[currentAnimation], -posX - tileSize * 4, posY - tileSize, tileSize * 5, tileSize * 5);
            }
            else
            {
                scale(1, 1);
                image(animation[currentAnimation], posX - tileSize, posY - tileSize, tileSize * 5, tileSize * 5);
            }
            popMatrix();
        }
        else if(speedY == 0 && speedX > 0 && dx == 1 && !onLadder)
        {
            if(currentAnimation < 1 || currentAnimation > 3)
            {
                currentAnimation = 1;
                switchTime = 0;
            }

            switchSpeed = 0.1f;
            switchTime += switchSpeed;

            if(switchTime >= 1)
            {
                switchTime = 0;
                currentAnimation++;
            }

            pushMatrix();
            scale(1, 1);
            image(animation[currentAnimation], posX - tileSize, posY - tileSize, tileSize * 5, tileSize * 5);
            popMatrix();

        }
        else if(speedY == 0 && speedX > 0 && dx == -1  && !onLadder)
        {
            if(currentAnimation < 1 || currentAnimation > 3)
            {
                currentAnimation = 1;
                switchTime = 0;
            }

            switchSpeed = 0.1f;
            switchTime += switchSpeed;

            if(switchTime >= 1)
            {
                switchTime = 0;
                currentAnimation++;
            }

            pushMatrix();
            scale(-1, 1);
            image(animation[currentAnimation], -posX - tileSize * 4, posY - tileSize, tileSize * 5, tileSize * 5);
            popMatrix();
        }
        else if (onLadder && isClimbingUp)
        {
            if(currentAnimation < 8 || currentAnimation > 11)
            {
                currentAnimation = 8;
                switchTime = 0;
            }

            switchSpeed = 0.05f;
            switchTime += switchSpeed;

            if(switchTime >= 1)
            {
                switchTime = 0;
                currentAnimation++;
            }

            image(animation[currentAnimation], posX - tileSize, posY - tileSize, tileSize * 5, tileSize * 5);
        }
        else if (onLadder && isClimbingDown)
        {
            if(currentAnimation < 8 || currentAnimation > 11)
            {
                currentAnimation = 8;
                switchTime = 0;
            }

            switchSpeed = 0.05f;
            switchTime += switchSpeed;

            if(switchTime >= 1)
            {
                switchTime = 0;
                currentAnimation++;
            }

            image(animation[currentAnimation], posX - tileSize, posY - tileSize, tileSize * 5, tileSize * 5);
        }
        else if (onLadder)
        {
            currentAnimation = 8;
            image(animation[currentAnimation], posX - tileSize, posY - tileSize, tileSize * 5, tileSize * 5);
        }
        else
        {
            currentAnimation = 0;

            pushMatrix();
            if(dx == -1)
            {
                scale(-1, 1);
                image(animation[currentAnimation], -posX - tileSize * 4, posY - tileSize, tileSize * 5, tileSize * 5);
            }
            else
            {
                scale(1, 1);
                image(animation[currentAnimation], posX - tileSize, posY - tileSize, tileSize * 5, tileSize * 5);
            }
            popMatrix();
        }
    }

    public void movePlayer(float dx)
    {
        this.dx = dx;

        posX += dx * speedX;
        if(speedX < maxSpeedX)
        {
            speedX += acc;
        }
    }

    public void jumpPlayer()
    {
        speedY -= maxSpeedY;
        posY += speedY;
    }

    public void fallPlayer()
    {
        speedY += gravity;
        posY += speedY;
    }

    public void keyReleased()
    {
        if (key == 'e' || key == 'E')
        {
            if(openedMenu)
            {
                saveWorld();
            }

            openedMenu = !openedMenu;
            chosenTile = false;
        }
        else if ((key == 'r' || key == 'R') && openedMenu)
        {
            chosenTile = false;
        }
        else if (keyCode == LEFT || keyCode == RIGHT)
        {
            speedX = 0;
        }
        else if(key == ' ' && !openedMenu)
        {
            tileX1 = (int) (posX1 / (tileSize * 4));
            tileY1 = (int) ((posY1 - maxSpeedY) / (tileSize * 4));

            tileX2 = (int) (posX2 / (tileSize * 4));
            tileY2 = (int) ((posY2 - maxSpeedY) / (tileSize * 4));

            if(collisions[level][tileY1 * mapW + tileX1] == 0 && collisions[level][tileY2 * mapW + tileX2] == 0)
            {
                tileX1 = (int) (posX3 / (tileSize * 4));
                tileY1 = (int) ((posY3 + maxSpeedY) / (tileSize * 4));

                tileX2 = (int) (posX4 / (tileSize * 4));
                tileY2 = (int) ((posY4 + maxSpeedY) / (tileSize * 4));

                if(collisions[level][tileY1 * mapW + tileX1] == 1 || collisions[level][tileY2 * mapW + tileX2] == 1)
                {
                    jumpPlayer();
                }
            }
        }
    }

    public void mouseClicked()
    {
        if (mouseButton == LEFT && openedMenu && !chosenTile)
        {
            menuX = map(mouseX - screenX, -screenX, -screenX + 640, 0, tilemapW);
            menuY = map(mouseY, 0, 640, 0, tilemapH);
            if (menuX < tilemapW && menuY < tilemapH && menuX >= 0 && menuY >= 0)
            {
                tileX = (int) (menuX / tileSize);
                tileY = (int) (menuY / tileSize);

                tileID = tileY * tilemapW / tileSize + tileX;
                chosenTile = true;
            }
        }
        else if (openedMenu && chosenTile)
        {
            tileX = (int) ((mouseX - screenX) / (tileSize * 4));
            tileY = (mouseY / (tileSize * 4));

            maps[level][tileY * mapW + tileX] = tileID;
            collisions[level][tileY * mapW + tileX] = tilesCollisions[tileID];
            dangers[level][tileY * mapW + tileX] = tilesDangers[tileID];
            ladders[level][tileY * mapW + tileX] = tilesLadders[tileID];
            finishes[level][tileY * mapW + tileX] = tilesFinishes[tileID];

        }
    }
}