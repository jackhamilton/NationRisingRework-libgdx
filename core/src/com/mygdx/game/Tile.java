package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.flowpowered.noise.NoiseQuality;
import com.flowpowered.noise.module.source.Perlin;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Jack
 */
public class Tile {
    
    private TileType type;
    private Sprite sprite;
    
    public Tile(int x, int y, TileType type) {
        this.type = type;
        this.sprite = new Sprite(type.getTexture());
        sprite.setX(x);
        sprite.setY(y);
    }

    public float getX() {
        return sprite.getX();
    }

    public float getY() {
        return sprite.getY();
    }
    
    public Sprite getSprite() {
        return sprite;
    }
    
    //Generates a grid of tiles with the base as a plains and biomes painted on top
    public static Tile[][] generateMap(int dimension1, int dimension2, Dimension screen) {
        
        double[][][] heatmaps = perlinMapGen(dimension1, dimension2);
        Tile[][] tiles = new Tile[dimension1][dimension2];
        //All tile images will be equal height and width
        int height = TileType.PLAINS.getTexture().getHeight();
        int width = TileType.PLAINS.getTexture().getWidth();
        int totalHeight = height*dimension2;
        int totalWidth = width*dimension1;
        //For centering the map in the screen
        int x = screen.width/2 - totalWidth/2;
        int y = screen.height/2 - totalHeight/2;
        for (int c = 0; c < dimension1; c++) {
            for (int i = 0; i < dimension2; i++) {
                tiles[c][i] = new Tile(x, y, TileType.getBiome(heatmaps[0][c][i], heatmaps[1][c][i]));
                x += width;
            }
            y += height;
            x = screen.width/2 - totalWidth/2;
        }
        return tiles;
    }
    
    //Returns a three dimensional array - an array of two maps. The first dimension only has two elements: the elevation and moisture maps, respectively.
    public static double[][][] perlinMapGen(int xSize, int ySize) {
        //Generate elevation map
        Perlin perlin = new Perlin();
        perlin.setNoiseQuality(NoiseQuality.BEST);
        perlin.setSeed((int)System.nanoTime());
        perlin.setFrequency(0.05);
        perlin.setOctaveCount(3);
        double[][] result=new double[xSize][ySize];
        double min = 23, max = -23;
        for (int i=0; i < xSize; i++) {
          for (int j=0; j < ySize; j++) {
            result[i][j] = perlin.getValue(i,j,0);
            //Add some variation in
            perlin.setFrequency(0.1);
            result[i][j] += .1 * perlin.getValue(i,j,0);
            //For normalization of results
            min = (result[i][j]<min)?result[i][j]:min;
            max = (result[i][j]>max)?result[i][j]:max;
          }
        }
        BufferedImage image = new BufferedImage(result.length, result[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < result.length; x++) {
            for (int y = 0; y < result[x].length; y++) {
                //Normalize to 0-1, raise to an exponent to produce sharper valleys, then convert into an rgb representation.
                //Exponentiation must be done after normalization. 
                //Note: when removing the image, retain this loop and normalize and exponentiate with it.
                image.setRGB(x, y, (int)(255*Math.pow(1/(max-min)*(result[x][y]-max)+1, 3)));
            }
        }
        
        //Generate moisture map
        perlin.setNoiseQuality(NoiseQuality.BEST);
        perlin.setSeed((int)System.nanoTime());
        perlin.setFrequency(0.1);
        perlin.setOctaveCount(3);
        double[][] result2 = new double[xSize][ySize];
        min = 23;
        max = -23;
        for (int i=0; i < xSize; i++) {
          for (int j=0; j < ySize; j++) {
            result2[i][j] = perlin.getValue(i,j,0);
            //For normalization of results
            min = (result2[i][j]<min)?result2[i][j]:min;
            max = (result2[i][j]>max)?result2[i][j]:max;
          }
        }
        BufferedImage moistureImage = new BufferedImage(result2.length, result2[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < result.length; x++) {
            for (int y = 0; y < result[x].length; y++) {
                //Normalize to 0-1, raise to an exponent to produce sharper valleys, then convert into an rgb representation.
                //Exponentiation must be done after normalization. 
                //Note: when removing the image, retain this loop and normalize and exponentiate with it.
                moistureImage.setRGB(x, y, (int)(255*Math.pow(1/(max-min)*(result2[x][y]-max)+1, 1.3)));
            }
        }
        
        //Output to files
        File ImageFile = new File("elevationMap.png");
        File ImageFile2 = new File("moistureMap.png");
        try {
            ImageFile.createNewFile();
            ImageIO.write(image, "png", ImageFile);
            ImageFile2.createNewFile();
            ImageIO.write(moistureImage, "png", ImageFile2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return new double[][][]{result, result2};
    }
    
}