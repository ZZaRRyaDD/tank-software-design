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
import ru.mipt.bit.platformer.playerinput.actions.base.AbstractAction;
import ru.mipt.bit.platformer.playerinput.inputs.ActionGenerator;
import ru.mipt.bit.platformer.playerinput.inputs.InputActions;
import ru.mipt.bit.platformer.playerinput.inputs.ai.AIActions;
import ru.mipt.bit.platformer.playerinput.inputs.ai.AI;
import ru.mipt.bit.platformer.playerinput.inputs.ai.DefaultAIActions;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard_player.DefaultKeyboardActions;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard_player.KeyboardPlayerInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.keyboard_player.KeyboardPlayerInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameDesktopLauncher implements ApplicationListener {
    private Level level;
    private LevelDrawer levelDrawer;
    private List<ActionGenerator> actionGenerators;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    @Override
    public void create() {
        LevelGenerator levelGenerator = getLevelGeneratorStrategy(StrategyGenerate.RANDOM);
        level = levelGenerator.generate();

        actionGenerators = new ArrayList<>(Arrays.asList(configurePlayerInput(), configureAIInput()));

        levelDrawer = new LevelDrawer("level.tmx", new SpriteBatch(), level);
        levelDrawer.draw();
    }

    public ActionGenerator configurePlayerInput() {
        InputActions keyboardActions = new KeyboardPlayerInputActions();
        new DefaultKeyboardActions(level).registerActions(keyboardActions);
        return new KeyboardPlayerInput(keyboardActions, level);
    }

    public ActionGenerator configureAIInput() {
        InputActions aiActions = new AIActions();
        new DefaultAIActions(level).registerActions(aiActions);
        return new AI(aiActions, level);
    }

    public LevelGenerator getLevelGeneratorStrategy(StrategyGenerate strategy) {
        LevelGenerator levelGeneratorStrategy;
        switch (strategy) {
            case FROM_FILE_PLAIN_TEXT:
                LevelReader reader = new PlaintTextLevelReader("src/main/resources/level.txt");
                LevelParser parser = new PlainTextLevelParser();
                levelGeneratorStrategy = new FromFileLevelGenerator(reader, parser);
                break;
            case RANDOM:
            default:
                levelGeneratorStrategy = new RandomLevelGenerator(8, 10);
                break;
        }
        return levelGeneratorStrategy;
    }

    @Override
    public void render() {
        clearScreen();

        List<AbstractAction> actions = new ArrayList<>();
        for (ActionGenerator generator : actionGenerators) {
            actions.addAll(generator.getActionList());
        }
        actions.forEach(AbstractAction::apply);

        float movementSpeed = 0.8f, deltaTime = Gdx.graphics.getDeltaTime();
        levelDrawer.renderMoves(deltaTime, movementSpeed);
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
}
