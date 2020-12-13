package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.ArcDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.GraphicsProvider;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.LineDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PieDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.arc.ArcDrawerFactoryByPixelDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.arc.GraphicsArcDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.line.GraphicsLineDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.line.LineDrawerFactoryByPixelDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.pie.GraphicsPieDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.pie.PieDrawerFactoryByPixelDrawer;

public class PrimitivesFactoryWithDefaultGraphicsImplementation {

    public final LineDrawer createLineDrawer(GraphicsProvider gp) {
        LineDrawerFactoryByPixelDrawer factory = getCustomLineDrawerFactory();
        if (factory != null)
            return factory.createInstance(new GraphicsPixelDrawer(gp));
        return new GraphicsLineDrawer(gp);
    }

    public final ArcDrawer createArcDrawer(GraphicsProvider gp) {
        ArcDrawerFactoryByPixelDrawer factory = getCustomArcDrawerFactory();
        if (factory != null)
            return factory.createInstance(new GraphicsPixelDrawer(gp));
        return new GraphicsArcDrawer(gp);
    }

    public final PieDrawer createPieDrawer(GraphicsProvider gp) {
        PieDrawerFactoryByPixelDrawer factory = getCustomPieDrawerFactory();
        if (factory != null)
            return factory.createInstance(new GraphicsPixelDrawer(gp));
        return new GraphicsPieDrawer(gp);
    }

    protected ArcDrawerFactoryByPixelDrawer getCustomArcDrawerFactory() {
        return null;
    }
    protected PieDrawerFactoryByPixelDrawer getCustomPieDrawerFactory() {
        return null;
    }
    protected LineDrawerFactoryByPixelDrawer getCustomLineDrawerFactory() {
        return null;
    }
}
