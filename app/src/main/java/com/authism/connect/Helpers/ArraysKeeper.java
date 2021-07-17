package com.authism.connect.Helpers;

import com.authism.connect.Models.Category;
import com.authism.connect.R;

import java.util.ArrayList;

public class ArraysKeeper {
    public static ArrayList<Category> communicationCards = new ArrayList<>();

    static {
        communicationCards.add(new Category("Я", R.raw.i, R.drawable.i));
        communicationCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        communicationCards.add(new Category("Есть", R.raw.eat, R.drawable.eat));
        communicationCards.add(new Category("Играть", R.raw.play, R.drawable.play));
        communicationCards.add(new Category("Пойти", R.raw.go, R.drawable.go));
        communicationCards.add(new Category("Гигиена", R.raw.hygiene, R.drawable.hygiene));
        communicationCards.add(new Category("Пить", R.raw.drink, R.drawable.drink));
        communicationCards.add(new Category("Рисовать", R.raw.draw, R.drawable.draw));
        communicationCards.add(new Category("Отдыхать", R.raw.chill, R.drawable.chill));
    }

    public static ArrayList<Category> eatCards = new ArrayList<>();

    static {
        eatCards.add(new Category("Я", R.raw.i, R.drawable.i));
        eatCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        eatCards.add(new Category("Мясо", R.raw.meat, R.drawable.meat));
        eatCards.add(new Category("Рис", R.raw.rise, R.drawable.rise));
        eatCards.add(new Category("Овощи", R.raw.vegetables, R.drawable.vegetables));
        eatCards.add(new Category("Выпечка", R.raw.bakery, R.drawable.bakery));
        eatCards.add(new Category("Фрукты", R.raw.fruits, R.drawable.fruits));
        eatCards.add(new Category("Сладкое", R.raw.sweets, R.drawable.sweets));
    }

    public static ArrayList<Category> playCards = new ArrayList<>();

    static {
        playCards.add(new Category("Я", R.raw.i, R.drawable.i));
        playCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        playCards.add(new Category("Машинка", R.raw.car, R.drawable.car));
        playCards.add(new Category("Паровоз", R.raw.train, R.drawable.train));
        playCards.add(new Category("Телевизор", R.raw.tv, R.drawable.tv));
        playCards.add(new Category("Компьютер", R.raw.computer, R.drawable.computer));
        playCards.add(new Category("Паззл", R.raw.puzzle, R.drawable.puzzle));
        playCards.add(new Category("Пластилин", R.raw.plasticine, R.drawable.plasticine));

    }
    public static ArrayList<Category> goCards = new ArrayList<>();

    static {
        goCards.add(new Category("Я", R.raw.i, R.drawable.i));
        goCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        goCards.add(new Category("Дом", R.raw.home, R.drawable.home));
        goCards.add(new Category("Игровая комната", R.raw.playroom, R.drawable.playroom));
        goCards.add(new Category("Бассейн", R.raw.pool, R.drawable.pool));
        goCards.add(new Category("Детская площадка", R.raw.playground, R.drawable.playground));
        goCards.add(new Category("Торговый центр", R.raw.mall, R.drawable.mall));

    }
    public static ArrayList<Category> hygieneCards = new ArrayList<>();

    static {
        hygieneCards.add(new Category("Я", R.raw.i, R.drawable.i));
        hygieneCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        hygieneCards.add(new Category("Предметы", R.raw.objects, R.drawable.objects));
        hygieneCards.add(new Category("Туалет", R.raw.wc, R.drawable.wc));
        hygieneCards.add(new Category("Мыть руки", R.raw.washhands, R.drawable.washhands));
        hygieneCards.add(new Category("Ванна", R.raw.bath, R.drawable.bath));
        hygieneCards.add(new Category("Чистить зубы", R.raw.brushteeths, R.drawable.brushteeth));

    }

    public static ArrayList<Category> drinkCards = new ArrayList<>();

    static {
        drinkCards.add(new Category("Я", R.raw.i, R.drawable.i));
        drinkCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        drinkCards.add(new Category("Вода", R.raw.water, R.drawable.water));
        drinkCards.add(new Category("Молоко", R.raw.milk, R.drawable.milk));
        drinkCards.add(new Category("Кола", R.raw.cola, R.drawable.cola));
        drinkCards.add(new Category("Сок", R.raw.juice, R.drawable.juice));
        drinkCards.add(new Category("Чай", R.raw.tea, R.drawable.tea));
        drinkCards.add(new Category("Компот", R.raw.compote, R.drawable.compote));
    }

    public static ArrayList<Category> chillCards = new ArrayList<>();

    static {
        chillCards.add(new Category("Я", R.raw.i, R.drawable.i));
        chillCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        chillCards.add(new Category("Перемена", R.raw.breakk, R.drawable.breakk));
        chillCards.add(new Category("Спать", R.raw.sleep, R.drawable.sleep));
    }

    public static ArrayList<Category> drawCards = new ArrayList<>();

    static {
        drawCards.add(new Category("Я", R.raw.i, R.drawable.i));
        drawCards.add(new Category("Хочу", R.raw.want, R.drawable.want));
        drawCards.add(new Category("Краски", R.raw.paints, R.drawable.paints));
        drawCards.add(new Category("Карандаши", R.raw.pencils, R.drawable.pencils));
        drawCards.add(new Category("Фломастеры", R.raw.markers, R.drawable.markers));
    }
    public static ArrayList<Category> animalCards = new ArrayList<>();

    static {
        animalCards.add(new Category("Слон", R.raw.elephant, R.drawable.elephant));
        animalCards.add(new Category("Кошка", R.raw.cat, R.drawable.cat));
        animalCards.add(new Category("Собака", R.raw.dog, R.drawable.dog));
        animalCards.add(new Category("Корова", R.raw.cow, R.drawable.cow));
        animalCards.add(new Category("Лошадь", R.raw.horse, R.drawable.horse));
        animalCards.add(new Category("Обезьяна", R.raw.monkey, R.drawable.monkey));
        animalCards.add(new Category("Лев", R.raw.lion, R.drawable.lion));
        animalCards.add(new Category("Тигр", R.raw.tiger, R.drawable.tiger));
    }

    public static ArrayList<Category> birdCards = new ArrayList<>();

    static {
        birdCards.add(new Category("Попугай", R.raw.popuga, R.drawable.popuga));
        birdCards.add(new Category("Голубь", R.raw.golub, R.drawable.golub));
        birdCards.add(new Category("Орёл", R.raw.eagle, R.drawable.eagle));
        birdCards.add(new Category("Курица", R.raw.chicken, R.drawable.chicken));
        birdCards.add(new Category("Павлин", R.raw.pavlin, R.drawable.pavlin));
    }

    public static ArrayList<Category> plantCards = new ArrayList<>();

    static {
        plantCards.add(new Category("Дерево", R.raw.tree, R.drawable.tree));
        plantCards.add(new Category("Трава", R.raw.grass, R.drawable.grass));
        plantCards.add(new Category("Гриб", R.raw.grib, R.drawable.grib));
        plantCards.add(new Category("Цветок", R.raw.flower, R.drawable.flower));
    }

    public static ArrayList<Category> techCards = new ArrayList<>();

    static {
        techCards.add(new Category("Пылесос", R.raw.cleaner, R.drawable.cleaner));
        techCards.add(new Category("Смартфон", R.raw.smartphone, R.drawable.smart));
        techCards.add(new Category("Наутбук", R.raw.laptop, R.drawable.laptop));
        techCards.add(new Category("Холодильник", R.raw.holod, R.drawable.holod));
    }

    public static ArrayList<Category> colorCards = new ArrayList<>();

    static {
        colorCards.add(new Category("Черный", R.raw.black, R.drawable.black));
        colorCards.add(new Category("Белый", R.raw.white, R.drawable.white));
        colorCards.add(new Category("Красный", R.raw.red, R.drawable.red));
        colorCards.add(new Category("Желтый", R.raw.yellow, R.drawable.yellow));
        colorCards.add(new Category("Зеленый", R.raw.green, R.drawable.green));
        colorCards.add(new Category("Синий", R.raw.blue, R.drawable.blue));
    }
}
