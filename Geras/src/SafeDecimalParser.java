

import java.math.BigDecimal;

class SafeDecimalParser 
{

    /** Константа 2 */
    protected static final BigDecimal TWO = new BigDecimal(2);

    /** Нижнее разрешенное значение */
    protected static final BigDecimal LOWER = new BigDecimal("2.22507385850720113605e-308");

    /** Верхнее разрешенное значение */
    protected static final BigDecimal UPPER = new BigDecimal("2.22507385850720125958e-308");

    /** Середина "плохого" интервала - нужно для округления "плохих" значений */
    protected static final BigDecimal MIDDLE = LOWER.add(UPPER).divide(TWO);

    /** Верхнее разрешенное значение как Double */
    private static final Double UPPER_DOUBLE = Double.valueOf(UPPER.doubleValue());

    /** Нижнее разрешенное значение как Double */
    private static final Double LOWER_DOUBLE = Double.valueOf(LOWER.doubleValue());

    /** Последовательность цифр для определения подозрительного числа */
    private static final String SUSPICIOUS_DIGITS = "22250738585072";

    /**
     * Проверка значения
     */
    final protected static boolean isSuspicious(String s) 
    {
        return digits(s).indexOf(SUSPICIOUS_DIGITS) >= 0;
    }

    /**
     * Безопасный метод парсинга String в Double
     */
    final protected static Double decimalValueOf(String s)
    {
        Double result = null;
        if (s != null) 
        {
            if (isSuspicious(s)) 
            {
                // take the slow path
                result = parseSafely(s);
            } 
            else 
            {
                result = Double.valueOf(s);
            }
        }
        return result;
    }

    /**
     * Медленный метод парсинга подозрительного числа
     * Округляем, если число находится в "плохом" интервале
     */
    final private static Double parseSafely(String s) 
    {
        Double result;
        BigDecimal bd = new BigDecimal(s);
        if (isDangerous(bd)) 
        {
            if (bd.compareTo(MIDDLE) >= 0) 
            {
                result = UPPER_DOUBLE;
            } 
            else 
            {
                result = LOWER_DOUBLE;
            }
        } 
        else
        {
            result = Double.valueOf(s);
        }
        return result;
    }

    /**
     * Извлекаем числа из строки(строка состоит из чисел)
     */
    final private static String digits(String s) 
    {
        char[] ca = s.toCharArray();
        int len = ca.length;
        StringBuilder b = new StringBuilder(len);
        for (int i = 0; i < len; i++) 
        {
            char c = ca[i];
            if (c >= '0' && c <= '9') 
            {
                b.append(c);
            }
        }
        return b.toString();
    }

    /**
     * Проверяем, принадлежит ли значение опасному интервалу
     */
    final private static boolean isDangerous(BigDecimal bd) 
    {
        return bd.compareTo(UPPER) < 0 && bd.compareTo(LOWER) > 0;
    }
}
