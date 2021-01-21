import java.util.HashMap;
import java.util.Map;

public class InfoOfObject {

    public static final Map<String, Integer> NUMBERS_OF_COLUMN = new HashMap<>() {{
        put("Кадастровый номер:", 0);
        put("Тип:", 1);
        put("Вид:", 2);
        put("Кадастровый квартал:", 3);
        put("Статус:", 4);
        put("Адрес:", 5);
        put("Категория земель:", 6);
        put("Форма собственности:", 7);
        put("Кадастровая стоимость:", 8);
        put("Дата определения КС:", 9);
        put("Дата внесения сведений о КС:", 10);
        put("Дата утверждения КС:", 11);
        put("Дата применения КС:", 12);
        put("Площадь декларированная:", 13);
        put("Площадь уточненная:", 14);
        put("Разрешенное использование:", 15);
        put("по документу:", 16);
        put("Наименование:", 17);
        put("Общая площадь:", 18);
        put("Назначение:", 19);
        put("Количество этажей (в том числе подземных):", 20);
        put("Количество подземных этажей:", 21);
        put("Материал стен:", 22);
        put("Завершение строительства:", 23);
        put("Площадь застройки:", 24);
        put("Ввод в эксплуатацию:", 25);
    }};

    public static Map<String, Integer> getNumbersOfColumn() {
        return NUMBERS_OF_COLUMN;
    }

    public static Integer getNumberOfOneColumn(String key) {

        return NUMBERS_OF_COLUMN.get(key);
    }
}
