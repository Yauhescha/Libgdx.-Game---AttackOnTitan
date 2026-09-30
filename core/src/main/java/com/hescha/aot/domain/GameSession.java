package com.hescha.aot.domain;
import com.badlogic.gdx.utils.Array; import com.hescha.aot.data.GameMode;
public final class GameSession { public final PlayerState player=new PlayerState(); public final Array<EnemyState> enemies=new Array<>(); public final Array<GasPickupState> gasPickups=new Array<>(); public final GameMode mode; public float difficulty,enemyTimer=.5f,gasTimer=4f; public boolean finished,won; public GameSession(GameMode mode){this.mode=mode;} }
