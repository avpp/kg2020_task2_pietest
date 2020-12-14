package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.pie;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.GraphicsProvider;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PieFiller;

import java.awt.*;
import java.awt.geom.Arc2D;

public class GraphicsPieFiller implements PieFiller {
    private GraphicsProvider gp;

    public GraphicsPieFiller(GraphicsProvider gp) {
        this.gp = gp;
    }

    @Override
    public void fillPie(int x, int y, int width, int height, double startAngle, double arcAngle, Color c) {
        Arc2D arc = new Arc2D.Double(x, y, width, height, startAngle*180/Math.PI, arcAngle*180/Math.PI, Arc2D.PIE);
        gp.getGraphics().setColor(c);
        gp.getGraphics().fill(arc);
    }
}
