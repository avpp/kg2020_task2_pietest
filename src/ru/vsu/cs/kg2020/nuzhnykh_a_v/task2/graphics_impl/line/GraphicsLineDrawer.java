package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.line;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.GraphicsProvider;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.LineDrawer;

import java.awt.*;

public class GraphicsLineDrawer implements LineDrawer {
    private GraphicsProvider gp;

    public GraphicsLineDrawer(GraphicsProvider gp) {
        this.gp = gp;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        gp.getGraphics().setColor(color);
        gp.getGraphics().drawLine(x1, y1, x2, y2);
    }
}
