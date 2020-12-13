package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.line;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.LineDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer;

import java.awt.*;

public interface LineDrawerFactoryByPixelDrawer {
    LineDrawer createInstance(PixelDrawer pd);
}
