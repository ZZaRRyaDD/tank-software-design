package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.entity.drawers.base.LevelGraphic;
import ru.mipt.bit.platformer.entity.drawers.decorators.HealthBarDrawerDecorator;
import ru.mipt.bit.platformer.entity.drawers.drawers.factories.BulletDrawerFactory;
import ru.mipt.bit.platformer.entity.drawers.drawers.factories.ObstacleDrawerFactory;
import ru.mipt.bit.platformer.entity.drawers.drawers.factories.TankDrawerFactory;
import ru.mipt.bit.platformer.entity.objects.Bullet;
import ru.mipt.bit.platformer.entity.objects.Level;
import ru.mipt.bit.platformer.entity.objects.Obstacle;
import ru.mipt.bit.platformer.entity.objects.Tank;
import ru.mipt.bit.platformer.entity.objects.base.GameObject;
import ru.mipt.bit.platformer.entity.objects.generators.LevelGenerator;
import ru.mipt.bit.platformer.entity.objects.generators.random.RandomLevelGenerator;
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
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
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
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        levelDrawer = context.getBean("mainLevelDrawer", HealthBarDrawerDecorator.class);
        TiledMapTileLayer groundLayer = context.getBean("groundLayer", TiledMapTileLayer.class);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        initGraphicFactories(levelDrawer, tileMovement, groundLayer);

        LevelGenerator levelGenerator = context.getBean("levelRandomGenerator", RandomLevelGenerator.class);
        levelGenerator.generate();

        level = levelGenerator.getLevel();

        initActionGenerators(levelGenerator, levelDrawer);

        context.close();
    }

    public void initGraphicFactories(LevelGraphic levelDrawer, TileMovement tileMovement, TiledMapTileLayer groundLayer) {
        levelDrawer.addStrategyGraphics(
                Tank.class,
                new TankDrawerFactory("images/blueTank.png", tileMovement)
        );
        levelDrawer.addStrategyGraphics(
                Bullet.class,
                new BulletDrawerFactory("images/bullet.png", tileMovement)
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
        actionGenerators.add(new PlayerInput(keyboardActions, level, object));
    }

    public void configureAIActionController(List<ActionGenerator> actionGenerators, Level level, List<Tank> objects) {
        InputActions aiActions = new AIActions();
        new DefaultAIActions(level).registerActions(aiActions);

        for (GameObject object : objects) {
            actionGenerators.add(new AI(aiActions, level, object));
        }
    }

    public void configureGraphicActionController(List<ActionGenerator> actionGenerators, LevelGraphic levelDrawer) {
        InputActions graphicActions = new GraphicActions();
        new DefaultGraphicActions().registerActions(graphicActions);
        actionGenerators.add(new Graphic(graphicActions, levelDrawer));
    }

    @Override
    public void render() {
        clearScreen();

        List<AbstractAction> actions = new ArrayList<>();
        for (ActionGenerator generator : actionGenerators) {
            actions.addAll(generator.getActions());
        }
        actions.forEach(AbstractAction::apply);

        float deltaTime = Gdx.graphics.getDeltaTime();
        level.updateState(deltaTime);
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
