package com.hescha.aot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.hescha.aot.AotGame;
import com.hescha.aot.data.CharacterDef;
import com.hescha.aot.ui.UiButton;
import java.util.List;

public final class CharacterShopScreen extends BaseScreen {
    private static final int PAGE_SIZE = 5;
    private final List<CharacterDef> characters;
    private final Rectangle[] cards = new Rectangle[PAGE_SIZE];
    private final UiButton back = new UiButton("BACK", 30, 35, 160, 75);
    private final UiButton previous = new UiButton("<", 40, 165, 110, 50);
    private final UiButton next = new UiButton(">", 570, 165, 110, 50);
    private final Rectangle action = new Rectangle(220, 35, 470, 75);
    private CharacterDef preview;
    private String message = "";
    private float time;
    private int page;

    public CharacterShopScreen(AotGame game) {
        super(game);
        characters = game.characters().all();
        preview = game.characters().byId(game.save().selectedCharacter());
        page = characters.indexOf(preview) / PAGE_SIZE;
        for (int i = 0; i < cards.length; i++) cards[i] = new Rectangle(40, 720 - i * 120, 640, 110);
    }

    private int pageCount() { return (characters.size() + PAGE_SIZE - 1) / PAGE_SIZE; }
    private int visibleCount() { return Math.min(PAGE_SIZE, characters.size() - page * PAGE_SIZE); }
    private CharacterDef characterAt(int row) { return characters.get(page * PAGE_SIZE + row); }

    @Override public void render(float delta) {
        time += delta;
        begin();
        var font = game.assets().font;
        batch.begin();
        batch.setColor(1, 1, 1, .18f);
        batch.draw(game.assets().street, 0, 0, 720, 1280);
        batch.setColor(Color.WHITE);
        font.setColor(Color.WHITE);
        font.getData().setScale(.88f);
        font.draw(batch, "CHARACTERS", 0, 1220, 720, Align.center, false);
        font.getData().setScale(.55f);
        font.draw(batch, "Coins: " + game.save().coins(), 0, 1155, 720, Align.center, false);
        var animation = game.assets().characterAttack(preview.id);
        float animationTime = time % Math.max(2f, animation.getAnimationDuration() + .7f);
        var frame = animationTime < animation.getAnimationDuration()
                ? animation.getKeyFrame(animationTime, false) : game.assets().character(preview.id);
        batch.draw(frame, 60, 880, 117, 177);
        font.getData().setScale(.78f);
        font.draw(batch, preview.title, 210, 1065, 460, Align.left, true);
        font.getData().setScale(.48f);
        font.draw(batch, "Speed: " + Math.round(preview.moveFactor * 100) + "%\n"
                + "Hook power: " + (int) preview.hookForce + "\n"
                + "Attack reach: " + (int) preview.attackRadius, 210, 990, 460, Align.left, false);
        batch.end();

        for (int i = 0; i < visibleCount(); i++) {
            var character = characterAt(i);
            var card = cards[i];
            shapes.begin(ShapeRenderer.ShapeType.Filled);
            if (character == preview) shapes.setColor(.08f, .27f, .29f, .95f);
            else shapes.setColor(.07f, .09f, .11f, .94f);
            shapes.rect(card.x, card.y, card.width, card.height);
            shapes.end();
            batch.begin();
            batch.draw(game.assets().character(character.id), card.x + 18, card.y + 10, 59, 89);
            font.getData().setScale(.60f);
            font.draw(batch, character.title, card.x + 110, card.y + 77, 300, Align.left, true);
            font.getData().setScale(.42f);
            String state = character.id.equals(game.save().selectedCharacter()) ? "SELECTED"
                    : game.save().unlocked(character) ? "OWNED" : character.price + " coins";
            font.draw(batch, state, card.x + 430, card.y + 70, 185, Align.center, false);
            batch.end();
        }
        batch.begin();
        font.getData().setScale(.42f);
        font.draw(batch, message, 25, 146, 670, Align.center, false);
        if (pageCount() > 1)
            font.draw(batch, "Page " + (page + 1) + " / " + pageCount(), 170, 201, 380, Align.center, false);
        batch.end();
        font.getData().setScale(.52f);
        if (page > 0) previous.draw(shapes, batch, font);
        if (page + 1 < pageCount()) next.draw(shapes, batch, font);
        font.getData().setScale(.58f);
        back.draw(shapes, batch, font);
        String actionText = preview.id.equals(game.save().selectedCharacter()) ? "SELECTED"
                : game.save().unlocked(preview) ? "SELECT" : "BUY - " + preview.price + " COINS";
        new UiButton(actionText, action.x, action.y, action.width, action.height).draw(shapes, batch, font);
        font.getData().setScale(.72f);

        if (Gdx.input.justTouched()) {
            var point = touch();
            if (back.hit(point)) { game.setScreen(new MainMenuScreen(game)); return; }
            if (page > 0 && previous.hit(point)) { changePage(page - 1); return; }
            if (page + 1 < pageCount() && next.hit(point)) { changePage(page + 1); return; }
            for (int i = 0; i < visibleCount(); i++) if (cards[i].contains(point)) {
                preview = characterAt(i);
                time = 0;
                message = "";
                return;
            }
            if (action.contains(point)) {
                if (game.save().unlocked(preview) || game.save().buy(preview)) {
                    game.save().select(preview.id);
                    message = preview.title + " selected";
                } else message = "Need " + (preview.price - game.save().coins()) + " more coins";
            }
        }
    }

    private void changePage(int page) {
        this.page = page;
        preview = characterAt(0);
        time = 0;
        message = "";
    }
}
