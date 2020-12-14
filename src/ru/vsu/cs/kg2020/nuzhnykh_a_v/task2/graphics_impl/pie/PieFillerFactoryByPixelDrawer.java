package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.pie;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PieFiller;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer;

public interface PieFillerFactoryByPixelDrawer {
    PieFiller createInstance(PixelDrawer pd);
}
