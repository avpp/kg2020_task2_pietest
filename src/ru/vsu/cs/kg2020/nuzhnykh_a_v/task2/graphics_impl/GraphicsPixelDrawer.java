package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.GraphicsProvider;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer;

import java.awt.*;

public class GraphicsPixelDrawer implements PixelDrawer {
    private GraphicsProvider gp;

    public GraphicsPixelDrawer(GraphicsProvider gp) {
        this.gp = gp;
    }

    @Override
    public void setPixel(int x, int y, Color c) {
        gp.getGraphics().setColor(c);
        gp.getGraphics().fillRect(x, y, 1, 1);
    }
}
