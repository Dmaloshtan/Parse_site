import java.util.HashMap;
import java.util.Map;

public class InfoOfObject {

    private static final Map<String, Integer > NUMBERS_OF_COLUMN = new HashMap<>(){{
        put("Кадастровый номер:",0);
        put("Тип:",1);
        put("Вид:",2);
        put("Кадастровый квартал:",3);
        put("Статус:",4);
        put("Адрес:",5);
        put("Категория земель:",6);
        put("Форма собственности:",7);
        put("Кадастровая стоимость:",8);
        put("Дата определения КС:",9);
        put("Дата внесения сведений о КС:",10);
        put("Дата утверждения КС:",11);
        put("Дата применения КС:",12);
        put("Декларированная площадь:",13);
        put("Разрешенное использование:",14);
        put("По документу:",15);
        put("Наименование:",16);
        put("Общая площадь:",13);
        put("Назначение:",17);
        put("Количество этажей (в том числе подземных):",18);
        put("Количество подземных этажей:",19);
        put("Материал стен:",20);
        put("Завершение строительства:",21);
        put("Площадь застройки:",22);
        put("Ввод в эксплуатацию:",23);}};

    public static Map<String, Integer> getNumbersOfColumn() {
        return NUMBERS_OF_COLUMN;
    }

    public static Integer getNumberOfOneColumn(String key){
        return NUMBERS_OF_COLUMN.get(key);
    }


}
