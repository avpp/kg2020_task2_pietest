package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.arc;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.ArcDrawer;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer;

public interface ArcDrawerFactoryByPixelDrawer {
    ArcDrawer createInstance(PixelDrawer pd);
}
