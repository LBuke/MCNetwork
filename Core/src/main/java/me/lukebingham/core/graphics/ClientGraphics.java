package me.lukebingham.core.graphics;

/**
 * Created by LukeBingham on 26/03/2017.
 */
public abstract class ClientGraphics implements Graphics {
    private GraphicsType graphicsType;

    public ClientGraphics(GraphicsType graphicsType) {
        this.graphicsType = graphicsType;
    }

    public GraphicsType getGraphicsType() {
        return graphicsType;
    }

    /**
     * This will be fired once at start-up.
     */
    protected abstract void init();
}
