package ru.mipt.bit.platformer.entity.drawers.drawers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.entity.drawers.base.GameObjectGraphic;
import ru.mipt.bit.platformer.entity.objects.Bullet;
import ru.mipt.bit.platformer.util.GdxGameUtils;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class BulletDrawer implements GameObjectGraphic  {
    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;
    private final Bullet bullet;

    public BulletDrawer(String imagePath, Bullet bullet, TileMovement tileMovement) {
        this.texture = new Texture(imagePath);
        this.textureRegion = new TextureRegion(this.texture);
        this.rectangle = createBoundingRectangle(this.textureRegion);
        this.tileMovement = tileMovement;
        this.bullet = bullet;
    }

    @Override
    public void draw(Batch batch) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                bullet.getCoordinates(),
                bullet.getDestinationCoordinates(),
                bullet.getMovementProgress()
        );
        GdxGameUtils.drawTextureRegionUnscaled(batch, textureRegion, rectangle, bullet.getDirection().getDirectionRotation());
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
