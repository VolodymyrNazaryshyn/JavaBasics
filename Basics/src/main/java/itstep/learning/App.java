package itstep.learning;

import itstep.learning.oop.Book;
import itstep.learning.oop.Journal;
import itstep.learning.oop.Library;
import itstep.learning.oop.Newspaper;

import java.util.Scanner ;
import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App { // Классы именуются CapitalCamelCase
    public static void main( String[] args ) {
        Library library = new Library() ;

        library.add( new Book( "Knuth", "Art of programming" ) ) ;    // Полиморфизм -
        library.add( new Book( "Shevchenko", "Kobzar") ) ;            // разные реализации
        library.add( new Journal( 10, "ArgC & ArgV") ) ;             // (Book, Journal)
        library.add( new Journal( 5, "Nature") ) ;                   // Одного интерфейса (Literature)
        library.add( new Newspaper( LocalDate.of(2017, 1, 13), "The Daily Telegraph")) ;
        library.add( new Newspaper( LocalDate.of(2021, 4, 20), "The Evening Standard")) ;

        library.printFunds() ;
    }

    public static void hello( String[] args ) { // методы и переменные именуются loverCamelCase
        // region Переменные и типы данных
        // Числовые !! все числовые типы знаковые, беззнаковых - нет
        byte byteVar = 10 ; // -128..127 --> 256 комбинаций
        short shortVar = 30000 ;
        int intVar = 2000000000 ;
        long longVar = (long) 1e15 ;

        float floatVar = 0.01f ;
        double doubleVar = 1e-5 ;

        // Символьный
        char charVar = 'A' ;
        // Строковый (есть пул строк, immutable)
        String stringVar = "A string" ;  // Эти строки будут ссылаться на один и тот же
        String stringVar3 = "A string" ; // объект (пул строк --> уже есть такая --> не новая)
        String stringVar2 = String.format( "-= %s, %d =-", stringVar, shortVar ) ;
        // оператор == для строк не перегружен, проверяется ссылочное равенство
        // для сравнения строк - метод stringVar.equals(stringVar3)

        // Логический
        boolean boolVar = true ;

        // константы
        final int constVar = 100 ;
        // endregion

        // Все типы, кроме примитивов - ссылочные. Примитивы - значимые, но свои типы не делаются
        // для примитивов есть ссылочные аналоги
        // Integer - int, Double - double
        // перегрузки операторов - нет
        // Object - общий родительский класс

        // region Input/Output
        System.out.println( "println - Output with new line" ) ;
        System.out.print( "print - Output without new line  " ) ;
        System.out.printf( "printf - formatted Output x=%d %n", byteVar ) ;
        System.err.println( "err.println - Error output" ) ;

        // System.in - файловый (потоковый) формализм, прямая работа крайне неудобная
        Scanner scanner = new Scanner( System.in ) ;
        int x = scanner.nextInt() ;
        String s = scanner.next() ;
        System.out.printf( "x = %d, s = %s %n", x, s) ;
        // endregion

        /*
        Д.З. Игра "Угадай число" - консольный режим.
        ПК загадывает число
        пользователь вводит варианты
        ПК выдает "больше"/"меньше" или "угадал"
         */
    }
}
/* Введение. Установка и настройка.
1. Intellij Idea (Ultimate, Student license)
2. JDK - Java Developer Kit (компилятор+)
3. Новый проект - Maven-archetype - QuickStart

Java схожа с .NET, но менее скрывает подробности
Общая схема
App.java (текст, исходный код) ---JDK---> App.class (промежуточный/байт - код)
App.class ---JRE/JVM---> исполнение
Сборка JAR(Java Archive) = DLL(библиотека классов)

Управление пакетами (и зависимостями) -- Единой нет, Выбирается при создании проекта
Одна из наиболее популярных - Maven, а также Gradle, Ant
Maven: содержит репозиторий пакетов (https://mvnrepository.com/)
которые подключаются через файл pom.xml

Запуск и конфигурация запуска
Edit Configuration --> + --> Application [ name: App, main class: App ]
 */
/*
В Java строго с именованием.
- В одном файле может быть только один public класс, один класс на несколько файлов не разбивается
- Название файла соответствует названию класса (+.java) с учетом регистра
- Названия пакетов - названия папок (традиция - малыми буквами)
 */
