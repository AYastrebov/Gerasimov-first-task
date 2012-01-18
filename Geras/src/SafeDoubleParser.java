
/**
 * Безопасный метод парсинга значений double
 * <p>
 * Предотвращает известный Java bug.
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
