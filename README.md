# kg2020_task2_pietest
Вспомогательный код для проверки правильности рисования арок (секторов) алгоритмом Брезенхейма.

***

Проверка корректности происходит путём рисования различных секторов и попиксельным сравнением с результатами рисовния таких же секторов через Graphics.

***
Сразу хочется отметить, что это первая версия и в ней могут быть ошибки как логические, так и архитектурные. Позже (значительно позже), вероятно, это всё будет преобразовано в красивый Unit-тест или независимое приложение для тестирования.
***

Для того, чтобы проверить свой алгоритм, потребуется сделать следующее:
1. Добавить пакет `ru.vsu.cs.kg2020.nuzhnykh_a_v.task2` в свой проект путём простого копирования соответствующей директории, соблюдая иерархию.
2. При необходимости, скопированный пакет можно переименовать. Настоятельно рекомендуется делать это с использованием инструментов среды разработки (предыдущее название выбрано для максимальной уникальности).
3. В своём проекте !один раз! вызовите метод `TestArcs.startTest()`. Это можно сделать, либо по какому-либо событию, либо, например, в конструкторе DrawPanel. В результате будет произведена попытка нарисовать набор проверочных секторов, а результаты будут сравнены с результатами работы штатного инструмента.

Рисование может занять несколько десятков секунд. Результаты можно будет увидеть в указанной директории.

***

При вызове метода требуется передать следующие аргументы:
1. Экземпляр класса `PrimitivesFactoryWithDefaultGraphicsImplementation` или его наследника. В данном классе реализовано рисование примитивов через `Graphics`. Для проверки своего кода, Вам потребуется создать свой класс унаследованный от описываемого и переопределить поведение нескольких методов
2. Аргумент `withMarkers` позволяет включить или выключить вывод вспомогательных маркеров границ внешнего прямоугольника.
3. Также стоит указать путь до директории, в которую сохранить результирующие изображения. Например, можно указать текущую, передав `"."`. Или же создать поддиректорию для результатов и указать её. Для сохранения информации о пикселях, изображения записываются без сжатия в формате BMP, так что потребуется около 0.5Гб свободного места.

***

Для работы тестирующего кода потребовалось объявить свои варианты интерфейсов `PixelDrawer`, `LineDrawer`, `ArcDrawer` и `PieDrawer` (см. пакет `ru.vsu.cs.kg2020.nuzhnykh_a_v.task2`). Собственно, для того, чтобы тест заработал, требуется передать экземпляры классов, реализующих эти интерфейсы. Т.к. у Вас, вероятно, есть собственные классы, расположенные в Ваших пакетах, которые объявляют похожу функциональность, то Вам потребуется, либо изменить наследование у Ваших классов, либо создать вспомогательные классы-адаптеры. Рассмотрим, как это всё организовать на примере.

Пусть у Вас имеется интерфейс:
```java
interface MyPixelDrawer {
  void putPixel(int col, int row, Color color);
}
```
Экземпляр (класса, его реализующего) которого Вам требуется передавать при создании своего `MyPieDrawer`'а:
```java
class MyPieDrawer {
  private MyPixelDrawer pd;
  public MyPieDrawer(MyPixelDrawer pd) {
    this.pd = pd;
  }
  
  public void drawPie(int x, int y, int r_hor, int r_ver, int start_angle, int stop_angle, Color c) {
    /*Здесь рисуем сектор эллипса, центр которого находится в (x, y);
    имеет горизонтальный и вертикальный радиусы, а также начальный и конечный угол в градусах.*/
  }
}
```

Теперь Вам надо "рассказать" тестирующему модулю, как использовать Ваш класс для рисования. При вызове функции Вы обязаны передать нечто, унаследованное от `PrimitivesFactoryWithDefaultGraphicsImplementation`.
Создадим класс-наследник и переопределим в нём необходимый метод. Имеется возможность переопределить любой из методов:
```java
    protected ArcDrawerFactoryByPixelDrawer getCustomArcDrawerFactory() {
        return null;
    }
    protected PieDrawerFactoryByPixelDrawer getCustomPieDrawerFactory() {
        return null;
    }
    protected LineDrawerFactoryByPixelDrawer getCustomLineDrawerFactory() {
        return null;
    }
```
Но в данном случае проверяется, лишь, `getCustomPieDrawerFactory`. Остальное сейчас не используется и написано для отражения общей картины происходящего. В случае необходимости, Вы можете написать подобные тесты и для остальных примитивов.

Так вот, данный метод должен вернуть нечто, что умеет создавать экземпляры `PieDrawer`'а (имеется в виду реализации интерфейса из пакета `ru.vsu.cs.kg2020.nuzhnykh_a_v.task2`).
Т.е. сейчас Ваш класс будет выглядеть как-то так:
```java
class MyFactoryImplementation extends PrimitivesFactoryWithDefaultGraphicsImplementation{
  @Override
  protected PieDrawerFactoryByPixelDrawer getCustomPieDrawerFactory() {
    return new PieDrawerFactoryByPixelDrawer() {
        @Override
        public ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PieDrawer createInstance(ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd) {
            /*Здесь будет создаваться и возвращаться экземпляр класса, реализующего PieDrawer*/
        }
    };
  }
}
```
Подумаем над содержимым метода `getCustomPieDrawerFactory`. Здесь потребуется как-то создать экземпляр `MyPieDrawer`'а, который в качестве аргумента требует `MyPixelDrawer`. На вход же подаётся `PixelDawer`. В связи с этим, требуется превратить последний в первый. Для этого создаём новый класс, который при вызове putPixel будет делегировать фактическое рисование имеющемуся экземпляру `PixelDrawer`'а:
```java
class MyPixelDrawerImpl implements MyPixelDrawer {
    private ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pdInstance;
    public MyPixelDrawerImpl(PixelDrawer pd) {
        this.pdInstance = pd;
    }
    
    @Override
    void putPixel(int col, int row, Color color) {
        pd.setPixel(col, row, color);
    }
}
```
Теперь внутри метода `getCustomPieDrawerFactory` мы уже сможем создать соответствующий экземпляр как-то так:
```java
MyPixelDrawer myPD = new MyPixelDrawerImpl(pd);
```
, а следовательно, теперь можно создать и экземпляр `MyPieDrawer`'а:
```java
MyPieDrawer pieDrawer = new MyPieDrawer(myPD);
```

Далее, нам требуется вернуть PieDrawer, а у нас только MyPieDrawer. Применим уже известный приём. Создадим класс `PieDrawerImpl`.
```java
class PieDrawerImpl implements PieDrawer {
    private MyPieDrawer mpd;
    public PieDrawerImpl(MyPieDrawer mpd) {
        this.mpd = mpd;
    }
    
    @Override
    void drawPie(int x, int y, int width, int height, double startAngle, double arcAngle, Color c) {
        /*Не забываем правильно адаптировать аргументы, т.к. смысловая нагрузка у них немного разная (см. описание)*/
        int centerX = x + width/2;
        int centerY = y + height/2;
        double aStart = 180*startAngle/Math.PI;
        double aArc = 180*arcAngle/Math.PI;
        double aEnd = aStart + aArc;
        
        mpd.drawPie(centerX, centerY, width/2, height/2, aStart, aEnd, c);
    }
}
```

Осталось собрать всё вместе и теперь метод `getCustomPieDrawerFactory` выглядит так:
```java
public ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PieDrawer createInstance(ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.PixelDrawer pd) {
    MyPixelDrawer myPD = new MyPixelDrawerImpl(pd);
    MyPieDrawer pieDrawer = new MyPieDrawer(myPD);
    return new PieDrawerImpl(pieDrawer);
}
```

***

После запуска данного метода подобным образом:
```java
TestArcs.startTest(new MyFactoryImplementation(), true, "./results");
/*Предполагается, что в текущей директории уже есть директория results*/
```
будут сформированы результирующие изображения. Для каждого теста, описанного внутри метода `startTest`, создаётся по три изображения с суффиксами:
1. your - результат работы Вашей реализации;
2. ideal - результат работы реализации по умолчанию (через Graphics);
3. diff - На данном изображении отмечены отличающиеся пиксели (красным - те, которые есть на Вашем, но нет на идеальном, т.е. лишние, а зелёным - наоборот недостающие).

Просмотр этих файлов поможет понять правильность работы алгоритмов, а также выявить основные ошибки. Хочется обратить особое внимание на возможное наличие разрывов в линии арки (нет краевых пикселей) или же наличие лишних пикселей. Особенно часть это проявляется на секторах, стороны которых строго вертикальны или горизонтальны (кстати, вертикальность/горизонтальность тоже стоит проверять).

Надеюсь, эта информация позволит обнаружить и исправить ошибки до момента сдачи таска. Принимать буду, просматривая результирующие файлы.