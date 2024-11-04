package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.entity.drawers.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.drawers.decorators.HealthBarDrawerDecorator;
import ru.mipt.bit.platformer.entity.drawers.drawers.factories.ObstacleDrawerFactory;
import ru.mipt.bit.platformer.entity.drawers.drawers.factories.TankDrawerFactory;
import ru.mipt.bit.platformer.entity.listener.LevelListener;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.drawers.drawers.LevelDrawer;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
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
import ru.mipt.bit.platformer.playerinput.inputs.graphic.DefaultGraphicActions;
import ru.mipt.bit.platformer.playerinput.inputs.graphic.Graphic;
import ru.mipt.bit.platformer.playerinput.inputs.graphic.GraphicActions;
import ru.mipt.bit.platformer.playerinput.inputs.player.DefaultPlayerInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.player.PlayerInputActions;
import ru.mipt.bit.platformer.playerinput.inputs.player.PlayerInput;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class GameDesktopLauncher implements ApplicationListener {
    private Level level;
    private LevelGraphic levelDrawer;
    private List<ActionGenerator> actionGenerators;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }

    @Override
    public void create() {
        createLevelGraphic();
        createLevel();
    }

    public void createLevel() {
        List<LevelListener> listeners = Arrays.asList(levelDrawer);
        LevelGenerator levelGenerator = getLevelGeneratorStrategy(StrategyGenerate.RANDOM, listeners);
        levelGenerator.generate();

        level = levelGenerator.getLevel();

        initActionGenerators(levelGenerator, levelDrawer);
    }

    public void createLevelGraphic() {
        TiledMap map = new TmxMapLoader().load("level.tmx");
        Batch batch = new SpriteBatch();
        MapRenderer renderer = createSingleLayerMapRenderer(map, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(map);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        levelDrawer = new LevelDrawer(map, renderer, batch);
        levelDrawer = new HealthBarDrawerDecorator(levelDrawer);

        initGraphicFactories(levelDrawer, tileMovement, groundLayer);
    }

    public void initGraphicFactories(LevelGraphic levelDrawer, TileMovement tileMovement, TiledMapTileLayer groundLayer ) {
        levelDrawer.addStrategyGraphics(
                Tank.class,
                new TankDrawerFactory("images/blueTank.png", tileMovement)
        );
        levelDrawer.addStrategyGraphics(
                Obstacle.class,
                new ObstacleDrawerFactory("images/greenTree.png", groundLayer)
        );
    }

    public void initActionGenerators(LevelGenerator levelGenerator, LevelGraphic levelDrawer) {
        actionGenerators = new ArrayList<>();
        configurePlayerActionController(actionGenerators, levelGenerator.getLevel(), levelGenerator.getPlayerTank());
        configureAIActionController(actionGenerators, levelGenerator.getLevel(), levelGenerator.getAITanks());
        configureGraphicActionController(actionGenerators, levelDrawer);
    }

    public void configurePlayerActionController(List<ActionGenerator> actionGenerators, Level level, Tank object) {
        InputActions keyboardActions = new PlayerInputActions();
        new DefaultPlayerInputActions(level).registerActions(keyboardActions);
        actionGenerators.add(new PlayerInput(keyboardActions, object));
    }

    public void configureAIActionController(List<ActionGenerator> actionGenerators, Level level, List<Tank> objects) {
        InputActions aiActions = new AIActions();
        new DefaultAIActions(level).registerActions(aiActions);

        for (GameObject object : objects) {
            actionGenerators.add(new AI(aiActions, object));
        }
    }

    public void configureGraphicActionController(List<ActionGenerator> actionGenerators, LevelGraphic levelDrawer) {
        InputActions graphicActions = new GraphicActions();
        new DefaultGraphicActions().registerActions(graphicActions);
        actionGenerators.add(new Graphic(graphicActions, levelDrawer));
    }

    public LevelGenerator getLevelGeneratorStrategy(StrategyGenerate strategy, List<LevelListener> listeners) {
        LevelGenerator levelGeneratorStrategy;
        switch (strategy) {
            case FROM_FILE_PLAIN_TEXT:
                LevelReader reader = new PlaintTextLevelReader("src/main/resources/level.txt");
                LevelParser parser = new PlainTextLevelParser();
                levelGeneratorStrategy = new FromFileLevelGenerator(reader, parser, listeners);
                break;
            case RANDOM:
            default:
                levelGeneratorStrategy = new RandomLevelGenerator(8, 10, listeners);
                break;
        }
        return levelGeneratorStrategy;
    }

    @Override
    public void render() {
        clearScreen();

        List<AbstractAction> actions = new ArrayList<>();
        for (ActionGenerator generator : actionGenerators) {
            actions.addAll(generator.getActions());
        }
        actions.forEach(AbstractAction::apply);

        level.updateState(Gdx.graphics.getDeltaTime());
        levelDrawer.render();
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
