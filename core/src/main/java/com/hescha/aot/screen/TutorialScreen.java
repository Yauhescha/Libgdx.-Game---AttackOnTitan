package com.hescha.aot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.hescha.aot.AotGame;
import com.hescha.aot.ui.UiButton;

public final class TutorialScreen extends BaseScreen {
    private final UiButton back = new UiButton("BACK", 20, 40, 180, 70);
    public TutorialScreen(AotGame game) { super(game); game.save().setTutorialSeen(); }

    @Override public void render(float delta) {
        begin();
        batch.begin();
        var font = game.assets().font;
        font.getData().setScale(.95f);
        font.draw(batch, "HOW TO PLAY", 0, 1160, 720, Align.center, false);
        font.getData().setScale(.58f);
        String text = String.join("\n",
                "1. HOLD below the HUD to fire an ODM hook.", "",
                "2. Drag left, right, UP or DOWN.",
                "The hook pulls you toward your finger.",
                "Release to detach and coast.", "",
                "3. Gas drains constantly and faster with hooks.",
                "Touch a GAS canister to refill to 100%.", "",
                "4. Fly around a titan and approach its NAPE",
                "from ABOVE. The sword attack is automatic.",
                "Body contact is dangerous. Training highlights",
                "the nape and respawns you after a collision.", "",
                "5. PAUSE stops movement and gas consumption.", "",
                "6. Tap a character to preview it. Use BUY",
                "or SELECT to equip it for the next run.");
        font.draw(batch, text, 65, 1040, 590, Align.left, true);
        font.getData().setScale(.72f);
        batch.end();
        back.draw(shapes, batch, font);
        if (Gdx.input.justTouched() && back.hit(touch())) game.setScreen(new MainMenuScreen(game));
    }
}
