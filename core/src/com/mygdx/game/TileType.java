package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Jack
 */
public enum TileType {
    
    NATION, DESERT, FOREST, PLAINS, SNOWFOREST, SNOWPLAINS;
    
    public Texture getTexture() {
        switch (this) {
            case NATION :
                Main.manager.load("Tiles/nation.png", Texture.class);
                Main.manager.finishLoading();
                return Main.manager.get("Tiles/nation.png");
            case DESERT :
                Main.manager.load("Tiles/desert.png", Texture.class);
                Main.manager.finishLoading();
                return Main.manager.get("Tiles/desert.png");
            case FOREST :
                Main.manager.load("Tiles/forest.png", Texture.class);
                Main.manager.finishLoading();
                return Main.manager.get("Tiles/forest.png");
            case PLAINS :
                Main.manager.load("Tiles/plains.png", Texture.class);
                Main.manager.finishLoading();
                return Main.manager.get("Tiles/plains.png");
            case SNOWFOREST :
                Main.manager.load("Tiles/snowforest.png", Texture.class);
                Main.manager.finishLoading();
                return Main.manager.get("Tiles/snowforest.png");
            case SNOWPLAINS :
                Main.manager.load("Tiles/snowplains.png", Texture.class);
                Main.manager.finishLoading();
                return Main.manager.get("Tiles/snowplains.png");
        }
        //Unreachable unless more options are added without being added to the case statement
        return null;
    }
    
    public static TileType getBiome(double elevation, double moisture) {
        if (elevation > .7) {
            if (moisture > .4) {
                return TileType.SNOWFOREST;
            } else {
                return TileType.SNOWPLAINS;
            }
        } else if (elevation >= .5) {
            if (moisture > .4) {
                return TileType.FOREST;
            } else if (moisture > .1) {
                return TileType.PLAINS;
            } else {
                return TileType.DESERT;
            }
        } else if (elevation >= .2) {
            if (moisture > .6) {
                return TileType.FOREST;
            } else if (moisture > .1) {
                return TileType.PLAINS;
            } else {
                return TileType.DESERT;
            }
        } else if (elevation < .2) {
            if (moisture > .8) {
                return TileType.FOREST;
            } else if (moisture > .3) {
                return TileType.PLAINS;
            } else {
                return TileType.DESERT;
            }
        }
        return TileType.FOREST;
    }
    
}
