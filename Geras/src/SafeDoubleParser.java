
/**
 * Безопасный метод парсинга значений double
 * Предотвращает известный Java bug c 2.2250738585072012e-308.
 */
final public class SafeDoubleParser extends SafeDecimalParser 
{

    /**
     * Безопасный метод парсинга Double из String
     */
    public static Double valueOf(String s) 
    {
        return decimalValueOf(s);
    }

    /**
     * Безопасный метод парсинга Double из String
     */
    public static Double parseDouble(String s) 
    {
        return valueOf(s);
    }
}
