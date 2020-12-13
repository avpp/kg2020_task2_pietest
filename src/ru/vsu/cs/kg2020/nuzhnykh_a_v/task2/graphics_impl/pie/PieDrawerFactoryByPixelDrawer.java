package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.pie;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PieDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer;

public interface PieDrawerFactoryByPixelDrawer {
    PieDrawer createInstance(PixelDrawer pd);
}
