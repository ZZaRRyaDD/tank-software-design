package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.drawers.LevelDrawer;
import ru.mipt.bit.platformer.entity.objects.generators.LevelGenerator;
import ru.mipt.bit.platformer.entity.objects.generators.StrategyGenerate;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.LevelParser;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.parsers.plaint_text.PlainTextLevelParser;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.readers.LevelReader;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.readers.plaint_text.PlaintTextLevelReader;
import ru.mipt.bit.platformer.entity.objects.generators.random.RandomLevelGenerator;
import ru.mipt.bit.platformer.entity.objects.generators.from_file.FromFileLevelGenerator;
import ru.mipt.bit.platformer.playerinput.inputs.PlayerInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard.DefaultKeyboardActions;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard.KeyboardPlayerInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard.KeyboardPlayerInput;
import ru.mipt.bit.platformer.playerinput.inputs.PlayerInput;


public class GameDesktopLauncher implements ApplicationListener {
    private Level level;
    private LevelDrawer levelDrawer;
    private PlayerInput input;

    @Override
    public void create() {
        input = configureInput();

        LevelGenerator levelGenerator = getLevelGeneratorStrategy(StrategyGenerate.RANDOM);
        level = levelGenerator.generate();

        levelDrawer = new LevelDrawer("level.tmx", new SpriteBatch(), level);
        levelDrawer.draw();
    }

    public PlayerInput configureInput() {
        PlayerInputActions keyboardActions = new KeyboardPlayerInputActions();
        new DefaultKeyboardActions().registerActions(keyboardActions);

        return new KeyboardPlayerInput(keyboardActions);
    }

    public LevelGenerator getLevelGeneratorStrategy(StrategyGenerate strategy) {
        LevelGenerator levelGeneratorStrategy;
        switch (strategy) {
            case RANDOM:
                levelGeneratorStrategy = new RandomLevelGenerator(10, 8);
                break;
            case FROM_FILE_PLAIN_TEXT:
                LevelReader reader = new PlaintTextLevelReader("src/main/resources/level.txt");
                LevelParser parser = new PlainTextLevelParser();
                levelGeneratorStrategy = new FromFileLevelGenerator(reader, parser);
                break;
            default:
                levelGeneratorStrategy = new RandomLevelGenerator(10, 8);
                break;
        }
        return levelGeneratorStrategy;
    }

    @Override
    public void render() {
        clearScreen();

        input.getAction().apply(level, level.getFirstMovable());

        levelDrawer.renderMoves(Gdx.graphics.getDeltaTime());
        levelDrawer.recordDrawCommand();
    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        levelDrawer.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
