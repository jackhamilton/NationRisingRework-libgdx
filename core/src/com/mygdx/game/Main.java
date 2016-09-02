package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.awt.Dimension;
import java.awt.Point;

public class Main extends ApplicationAdapter {
    
        public static AssetManager manager = new AssetManager();
	SpriteBatch batch;
	Texture img;
        private OrthographicCamera camera;
        //make a StretchViewport
        private Viewport viewport;
        private int mapSizeX = 75, mapSizeY = 75;
        private int viewX = 1920, viewY = 1080;
        private Tile[][] tiles;
        private Point clickStore = null;
        private Sprite uiBar;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
                camera = new OrthographicCamera();
                viewport = new FitViewport(viewX, viewY, camera);
                tiles = Tile.generateMap(mapSizeX, mapSizeY, new Dimension(viewX, viewY));
                manager.load("UI/UI.png", Texture.class);
                manager.finishLoading();
                uiBar = new Sprite(manager.get("UI/UI.png", Texture.class));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
                float xTranslate = 0, yTranslate = 0;
                //Monopolize any mouse clicks or touches to use for dragging. If you want to be able to click on stuff, set a minimum amount the 
                //cursor has to move from clickStore before the tiles start dragging and map that to a click function. Or use time mouse held down or something.
                //Note that that would prevent click, think, release on same position, expect to have clicked on something. Maybe you need to have started the click
                //on a button? As in, don't trigger drag immediately if the initial click is on a ClickableObject.
                if (Gdx.input.justTouched()) {
                    clickStore = new Point(Gdx.input.getX(0),Gdx.input.getY(0));
                }
                if (!Gdx.input.isTouched(0)) {
                    clickStore = null;
                } else {
                    xTranslate = Gdx.input.getX(0) - clickStore.x;
                    yTranslate = -1 * (Gdx.input.getY(0) - clickStore.y);
                    clickStore.x = Gdx.input.getX(0);
                    clickStore.y = Gdx.input.getY(0);
                }
                //Arrow key based tile movement code.
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    xTranslate += 8f;
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    xTranslate += -8f;
                } 
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    xTranslate = 0f;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    yTranslate += -8f;
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    yTranslate += 8f;
                } 
                if (Gdx.input.isKeyPressed(Input.Keys.UP) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    yTranslate = 0f;
                }
                //Boundary code - left boundary
                if (tiles[0][0].getX() >= 0 && xTranslate > 0) {
                    //If the position is already past zero or equal to it AND the current translation is in a negative direction, translate back to zero.
                    xTranslate = 0 - tiles[0][0].getX();
                } else if (tiles[0][0].getX() + xTranslate >= 0) {
                    //If the current translation would bring it past zero but is not yet at zero, translate to zero. The rest of the boundary code follows this same structure.
                    xTranslate = tiles[0][0].getX() * -1;
                } //Right boundary
                if (tiles[0][tiles.length-1].getX() <= viewX && xTranslate < 0) {
                    xTranslate = tiles[0][tiles.length-1].getX() - viewX;
                } else if (tiles[0][tiles.length-1].getX() + xTranslate <= viewX) {
                    xTranslate = (tiles[0][tiles.length-1].getX() - viewX) * -1;
                }
                //Top boundary
                if (tiles[tiles.length-1][0].getY() <= viewY && yTranslate < 0) {
                    yTranslate = viewY - tiles[tiles.length-1][0].getY();
                } else if (tiles[tiles.length-1][0].getY() + yTranslate <= viewY) {
                    yTranslate = (viewY - tiles[tiles.length-1][0].getY());
                } //Bottom boundary
                if (tiles[0][0].getY() >= 0 && yTranslate > 0) {
                    yTranslate = 0 - tiles[0][0].getY();
                } else if (tiles[0][0].getY() + yTranslate >= 0) {
                    yTranslate = tiles[0][0].getY() * -1;
                }
                for (int x = 0; x < mapSizeX; x++) {
                    for (int y = 0; y < mapSizeY; y++) {
                        tiles[x][y].getSprite().translate(xTranslate, yTranslate);
                        tiles[x][y].getSprite().draw(batch);
                    }
                }
                uiBar.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
        
}
