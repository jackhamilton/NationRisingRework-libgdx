package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

public class Main extends ApplicationAdapter {
    
        public static AssetManager manager = new AssetManager();
	SpriteBatch batch;
	Texture img;
        private OrthographicCamera camera;
        //make a StretchViewport
        private Viewport viewport;
        private int mapSizeX = 75, mapSizeY = 75;
        private Tile[][] tiles;
        private Point clickStore = null;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
                camera = new OrthographicCamera();
                int viewX = 1920, viewY = 1080;
                viewport = new FitViewport(viewX, viewY, camera);
                tiles = Tile.generateMap(mapSizeX, mapSizeY, new Dimension(viewX, viewY));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
                float xTranslate = 0, yTranslate = 0;
                
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    xTranslate = 8f;
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    xTranslate = -8f;
                } 
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    xTranslate = 0f;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    yTranslate = -8f;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    yTranslate = 8f;
                } 
                if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    yTranslate = 0f;
                }
                for (int x = 0; x < mapSizeX; x++) {
                    for (int y = 0; y < mapSizeY; y++) {
                        tiles[x][y].getSprite().translate(xTranslate, yTranslate);
                        tiles[x][y].getSprite().draw(batch);
                    }
                }
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
        
}
