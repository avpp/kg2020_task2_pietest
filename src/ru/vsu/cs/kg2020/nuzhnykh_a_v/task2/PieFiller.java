package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2;

import java.awt.*;

public interface PieFiller {

    /**
     * Рисует сектор по параметрам прямоуголника, описывающего эллипс, частью которого является рисуемая арка
     * @param x горизонтальная координата верхнего левого угла прямоугольника
     * @param y вертикальная координата верхнего левого угла прямоугольника
     * @param width ширина прямоугольника (двойной горизонтальный радиус)
     * @param height высота прямоугольника (двойной вертикальный радиус)
     * @param startAngle начальный угол <b>в радианах</b>
     * @param arcAngle угол самой арки <b>в радианах</b>
     * @param c цвет арки
     */
    void fillPie(int x, int y, int width, int height, double startAngle, double arcAngle, Color c);
}
