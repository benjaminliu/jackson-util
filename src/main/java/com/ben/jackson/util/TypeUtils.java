package com.ben.jackson.util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 类型转换 <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/7/2 <br>
 */
public class TypeUtils {
    public static String castToString(Object value) {
        return value == null ? null : value.toString();
    }

    public static Byte castToByte(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return byteValue((BigDecimal) value);
        } else if (value instanceof Number) {
            return ((Number) value).byteValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal) ? Byte.parseByte(strVal) : null;
        } else {
            throw new Exception("can not cast to byte, value : " + value);
        }
    }

    public static Character castToChar(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof Character) {
            return (Character) value;
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            } else if (strVal.length() != 1) {
                throw new Exception("can not cast to char, value : " + value);
            } else {
                return strVal.charAt(0);
            }
        } else {
            throw new Exception("can not cast to char, value : " + value);
        }
    }

    public static Short castToShort(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return shortValue((BigDecimal) value);
        } else if (value instanceof Number) {
            return ((Number) value).shortValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            return strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal) ? Short.parseShort(strVal) : null;
        } else {
            throw new Exception("can not cast to short, value : " + value);
        }
    }

    public static BigDecimal castToBigDecimal(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        } else {
            String strVal = value.toString();
            if (strVal.length() == 0) {
                return null;
            } else {
                return value instanceof Map && ((Map) value).size() == 0 ? null : new BigDecimal(strVal);
            }
        }
    }

    public static BigInteger castToBigInteger(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof BigInteger) {
            return (BigInteger) value;
        } else if (!(value instanceof Float) && !(value instanceof Double)) {
            if (value instanceof BigDecimal) {
                BigDecimal decimal = (BigDecimal) value;
                int scale = decimal.scale();
                if (scale > -1000 && scale < 1000) {
                    return ((BigDecimal) value).toBigInteger();
                }
            }

            String strVal = value.toString();
            return strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal) ? new BigInteger(strVal) : null;
        } else {
            return BigInteger.valueOf(((Number) value).longValue());
        }
    }

    public static Float castToFloat(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                if (strVal.indexOf(44) != 0) {
                    strVal = strVal.replaceAll(",", "");
                }

                return Float.parseFloat(strVal);
            } else {
                return null;
            }
        } else {
            throw new Exception("can not cast to float, value : " + value);
        }
    }

    public static Double castToDouble(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                if (strVal.indexOf(44) != 0) {
                    strVal = strVal.replaceAll(",", "");
                }

                return Double.parseDouble(strVal);
            } else {
                return null;
            }
        } else {
            throw new Exception("can not cast to double, value : " + value);
        }
    }

    public static Date castToDate(Object value) throws Exception {
        return castToDate(value, (String) null);
    }

    public static Date castToDate(Object value, String format) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof Date) {
            return (Date) value;
        } else if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        } else {
            long longValue = -1L;
            if (value instanceof BigDecimal) {
                longValue = longValue((BigDecimal) value);
                return new Date(longValue);
            } else if (value instanceof Number) {
                longValue = ((Number) value).longValue();
                return new Date(longValue);
            } else {
                if (value instanceof String) {
                    String strVal = (String) value;

                    if (strVal.indexOf(45) > 0 || strVal.indexOf(43) > 0) {
                        if (format == null) {
                            if (strVal.length() == 10) {
                                format = "yyyy-MM-dd";
                            } else if (strVal.length() == 19) {
                                format = "yyyy-MM-dd HH:mm:ss";
                            } else if (strVal.length() == 23) {
                                format = "yyyy-MM-dd HH:mm:ss.SSS";
                            }
                        }

                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                            return dateFormat.parse(strVal);
                        } catch (ParseException var35) {
                            throw new Exception("can not cast to Date, value : " + strVal);
                        }
                    }

                    if (strVal.length() == 0) {
                        return null;
                    }

                    longValue = Long.parseLong(strVal);
                }

                if (longValue == -1L) {
                    throw new Exception("can not cast to Date, value : " + value);
                } else {
                    return new Date(longValue);
                }
            }
        }
    }

    public static Long castToLong(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof BigDecimal) {
            return longValue((BigDecimal) value);
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else {
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                    return null;
                }

                if (strVal.indexOf(44) != 0) {
                    strVal = strVal.replaceAll(",", "");
                }

                try {
                    return Long.parseLong(strVal);
                } catch (NumberFormatException var4) {

                }
            }

            throw new Exception("can not cast to long, value : " + value);
        }
    }

    public static Integer castToInt(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof BigDecimal) {
            return intValue((BigDecimal) value);
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                if (strVal.indexOf(44) != 0) {
                    strVal = strVal.replaceAll(",", "");
                }

                return Integer.parseInt(strVal);
            } else {
                return null;
            }
        } else if (value instanceof Boolean) {
            return (Boolean) value ? 1 : 0;
        } else {
            throw new Exception("can not cast to int, value : " + value);
        }
    }

    public static Boolean castToBoolean(Object value) throws Exception {
        if (value == null) {
            return null;
        } else if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof BigDecimal) {
            return intValue((BigDecimal) value) > 0;
        } else if (value instanceof Number) {
            return ((Number) value).intValue() > 0;
        } else if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                if ("true".equalsIgnoreCase(strVal)) {
                    return Boolean.TRUE;
                }

                if ("false".equalsIgnoreCase(strVal)) {
                    return Boolean.FALSE;
                }

                throw new Exception("can not cast to boolean, value : " + value);
            } else {
                return null;
            }
        } else {
            throw new Exception("can not cast to boolean, value : " + value);
        }
    }


    private static byte byteValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        } else {
            int scale = decimal.scale();
            return scale >= -100 && scale <= 100 ? decimal.byteValue() : decimal.byteValueExact();
        }
    }

    private static short shortValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        } else {
            int scale = decimal.scale();
            return scale >= -100 && scale <= 100 ? decimal.shortValue() : decimal.shortValueExact();
        }
    }

    private static int intValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0;
        } else {
            int scale = decimal.scale();
            return scale >= -100 && scale <= 100 ? decimal.intValue() : decimal.intValueExact();
        }
    }

    private static long longValue(BigDecimal decimal) {
        if (decimal == null) {
            return 0L;
        } else {
            int scale = decimal.scale();
            return scale >= -100 && scale <= 100 ? decimal.longValue() : decimal.longValueExact();
        }
    }
}
