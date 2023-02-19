package itstep.learning.asyncs;

import java.util.Random;

public class SyncDemo {
    private Random random;
    private long time1;
    private long time2;
    public void run() {
        // random = new Random();
        // sum = 100;
        // activeThreads = 12;
        time1 = System.nanoTime();
        // for(int i = 0; i < 12; ++i) {
        //     new Thread(this::plusPercent2).start();
        // }
        quadraticEquation(3,7,-10);
    }
    private double sum;
    private Integer activeThreads;
    private final Object activeThreadsLocker = new Object();
    private final Object sumLocker = new Object(); // любой ссылочный тип имеет специальную область
    // памяти - критическую сукцию - примитив синхронизации, сигнальный системный
    // элемент, который управляет остановкой и возобновлением потоков
    private void plusPercent() {
        synchronized(sumLocker) {  // вход в блок - "закрытие" секции если она открыта, либо ожидание ее открытия
            double base = sum;  // чтение данных
            try {
                Thread.sleep(100 + random.nextInt(100));
            }
            catch (InterruptedException ignored) {
                System.err.println("Interruption");
            }
            double percent = 10;
            base *= 1 + percent / 100;
            sum = base; // запись данных
        } // конец синхроблока открывает секцию и следующий поток ее закрывает и начинает работать
        System.out.println(sum);
    }
    private void plusPercent2() {
        try { // это можно делать до транзакции - выносим за синхроблок
            Thread.sleep(100); //+ random.nextInt(100));
        }
        catch (InterruptedException ignored) {
            System.err.println("Interruption");
        }
        double percent = 10;
        double base;
        synchronized(sumLocker) {  // вход в блок - "закрытие" секции если она открыта, либо ожидание ее открытия
            base = sum; // чтение данных
            base *= 1 + percent / 100;
            sum = base; // запись данных
        } // конец синхроблока открывает секцию и следующий поток ее закрывает и начинает работать

        // System.out.println(sum); - вывод общего ресурса, он может поменяться в процессе подготовки вывода
        System.out.println(base);   // локальная переменная - у каждого потока своя
        // определяем последний ли
        // synchronized(activeThreads) { нельзя: Integer - immutable
        synchronized (activeThreadsLocker) {
            activeThreads -= 1; // меняет ссылку на объект, новый объект всегда "открытый"
            if(activeThreads == 0) onFinish();
        }
    }
    private void onFinish() { // должен запуститься после всех потоков (после последнего)
        time2 = System.nanoTime();
        System.out.println("Time: " + (time2 - time1) / 1e9 );
    }
    /*************************************/
    private final Object quadraticEquationLocker = new Object();
    private final Object timerLocker = new Object();
    private int rootCount = 2;

    private void quadraticEquation(int a, int b, int c) {
        System.out.println("Quadratic equation: " + a + "x^2+" + b + "x" + c + "=0");

        int D = (b * b) - (4 * a * c);
        System.out.println("D: " + D);

        Runnable positiveRoot = () -> {
            double root1, x1;
            if (D >= 0) {
                synchronized(quadraticEquationLocker) {
                    root1 = root1(b, D, a);
                    x1 = root1;
                }
                System.out.println("x1: " + x1);
            }
            else {
                System.out.println("Doesn't exist");
            }
            synchronized (timerLocker) {
                rootCount -= 1;
                if(rootCount == 0) onFinish();
            }
        };

        Runnable negativeRoot = () -> {
            double root2, x2;
            if (D >= 0) {
                synchronized(quadraticEquationLocker) {
                    root2 = root2(b, D, a);
                    x2 = root2;
                }
                System.out.println("x2: " + x2);
            }
            else {
                System.out.println("Doesn't exist");
            }
            synchronized (timerLocker) {
                rootCount -= 1;
                if(rootCount == 0) onFinish();
            }
        };

        new Thread(positiveRoot).start();
        new Thread(negativeRoot).start();
    }
    private double root1(double b, double D, double a) {
        return ( -b - Math.sqrt(D) ) / (2 * a);
    }
    private double root2(double b, double D, double a) {
        return ( -b + Math.sqrt(D) ) / (2 * a);
    }
}
/*
Синхронизация потоков

Модельная задача: Нацбанк публикует данные об инфляции за каждый месяц.
Задача - рассчитать годовую инфляцию. Сложность - запросы на API долгие
и могут выполняться параллельно.
Математика:
(100 + 10%) + 20% =?= (100 + 20%) + 10%
+10% == х1.1
(100х1.1)х1.2 =?= (100х1.2)х1.1 == 100х1.1х1.2
==> порядок "сложения" процентов произвольный
    это обосновывает возможность асинхронности

Проблема: разность во времени между операциями чтения и записи данных
Решение: создание "транзакции" на это время
 а) при помощи атомарных типов
 б) при помощи синхроблоков

Проблема: заключение всего тела потоковой функции в синхроблок
"выстраивает" их во времени, по сути убирая параллельность
Решение: рефакторинг кода, уменьшение транзакций до минимально
возможного "расстояния"

!! Все обращения к общим ресурсам (глобальным переменным) должны ОБЯЗАТЕЛЬНО
   быть транзакциями, в т.ч. такие кажущиеся целостными выражения типа sum += 10

Задача: определить какой поток последний и вывести общее время работы
 а) используется коллективное ожидание (надо собирать массив потоков - т.е. ссылок на Thread)
 б) вызывает отдельный метод - завершения


Задание: реализовать решение квадратного уравнения  ax^2 + bx + c = 0
1. расчёт дискриминанта D = b^2 - 4ac  синхронно
2. расчёт двух корней уравнения - параллельно
     если D >= 0 --> x1 = ( -b - sqrt(D) ) / 2a иначе "не существует" --> вывод
     если D >= 0 --> x2 = ( -b + sqrt(D) ) / 2a иначе "не существует" --> вывод
3. определить полное время работы (последний из методов выводит время)

                        ------x1---
схема выполнения: --D--<
                        ------x2---
 */
