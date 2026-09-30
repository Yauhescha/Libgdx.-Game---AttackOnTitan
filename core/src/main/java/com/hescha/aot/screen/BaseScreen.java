package com.hescha.aot.screen;
import com.badlogic.gdx.*; import com.badlogic.gdx.graphics.*; import com.badlogic.gdx.graphics.g2d.SpriteBatch; import com.badlogic.gdx.graphics.glutils.ShapeRenderer; import com.badlogic.gdx.math.Vector2; import com.badlogic.gdx.utils.ScreenUtils; import com.badlogic.gdx.utils.viewport.FitViewport; import com.hescha.aot.AotGame; import static com.hescha.aot.config.GameConfig.*;
public abstract class BaseScreen extends ScreenAdapter {
 protected final AotGame game; protected final OrthographicCamera camera=new OrthographicCamera(); protected final FitViewport viewport=new FitViewport(WORLD_W,WORLD_H,camera); protected final SpriteBatch batch=new SpriteBatch(); protected final ShapeRenderer shapes=new ShapeRenderer(); protected final Vector2 touch=new Vector2();
 protected BaseScreen(AotGame g){game=g;camera.position.set(WORLD_W/2f,WORLD_H/2f,0);camera.update();}
 protected void begin(){Gdx.gl.glEnable(GL20.GL_BLEND);Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);ScreenUtils.clear(.035f,.04f,.05f,1);camera.update();batch.setProjectionMatrix(camera.combined);shapes.setProjectionMatrix(camera.combined);} protected Vector2 touch(){touch.set(Gdx.input.getX(),Gdx.input.getY());viewport.unproject(touch);return touch;}
 @Override public void resize(int w,int h){viewport.update(w,h,true);} @Override public void dispose(){batch.dispose();shapes.dispose();}
}
