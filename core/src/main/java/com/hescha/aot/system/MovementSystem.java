package com.hescha.aot.system;
import com.badlogic.gdx.math.MathUtils; import com.badlogic.gdx.math.Vector2; import com.hescha.aot.data.CharacterDef; import com.hescha.aot.domain.PlayerState; import static com.hescha.aot.config.GameConfig.*;
public final class MovementSystem { private final Vector2 tmp=new Vector2(); public void update(PlayerState p,CharacterDef c,float dt){
 p.gas=Math.max(0,p.gas-GAS_IDLE_DRAIN*dt); if(p.gas<=0)p.hook.stop();
 if(p.hook.active&&p.gas>0){tmp.set(p.hook.anchor).sub(p.position);float dist=tmp.len();if(dist>5){tmp.nor().scl(c.hookForce);p.velocity.mulAdd(tmp,dt);}p.gas=Math.max(0,p.gas-GAS_HOOK_DRAIN*dt);} else p.velocity.y+=GRAVITY*dt;
 float drag=Math.max(0,1f-DRAG*dt*.32f);p.velocity.scl(drag);float max=MAX_SPEED*c.moveFactor;if(p.velocity.len2()>max*max)p.velocity.setLength(max);p.position.mulAdd(p.velocity,dt);
 p.position.x=MathUtils.clamp(p.position.x,35,WORLD_W-35);p.position.y=MathUtils.clamp(p.position.y,70,PLAY_AREA_TOP-PLAYER_H/2f-10f); if(p.position.x<=35||p.position.x>=WORLD_W-35)p.velocity.x*=-.3f;if(p.position.y<=70||p.position.y>=PLAY_AREA_TOP-PLAYER_H/2f-10f)p.velocity.y*=-.2f;
 }}
