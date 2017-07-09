package com.android_mobile.core.utiles;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final String DEFAULT_DOUBLE_FORMAT = "#0.00";
    //6~18位数字或字符或两者组合
    public static final String PATTERN_NUM_CHARS = "^[a-zA-Z0-9]{0,10}$";
    //6~18位数字、字符组合
    public static final String PATTERN_NUM$$CHARS = "^(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,18}$";
    //邮箱
    public static final String PATTERN_EMAIL = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

    /**
     * 判断是否为空
     */
    public static boolean isNull(String obj) {
        return null == obj || "".equals(obj);
    }

    /**
     * 是否为空
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0 || value.equals("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(value.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组是否为空
     *
     * @param values
     * @return
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 银联卡号长度匹配
     */
    public static boolean isUnionPayCardNo(String cardNo) {
        String strPattern = "^[0-9]{16,19}";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(cardNo.trim());
        return m.matches();
    }

    /**
     * 判断是否符合邮箱格式
     */
    public static boolean isEmail(String strEmail) {
        Pattern p = Pattern.compile(PATTERN_EMAIL);
        Matcher m = p.matcher(strEmail.trim());
        return m.matches();
    }

    /**
     * 判断是否为固定电话
     */
    public static boolean isTel(String tel) {
        // Pattern pattern = Pattern.compile("([0-9]{4})([0-9]{4})([0-9]{4})");
        Pattern pattern = Pattern.compile("^(0[0-9]{2,3}\\-)?([0-9]{6,7})+(\\-[0-9]{1,4})?$");
        Matcher m = pattern.matcher(tel.trim());
        return m.matches();
    }

    /**
     * 是否包含特殊字符
     */
    public static boolean isN(String tel) {
        // Pattern pattern =
        // Pattern.compile("^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
        // String regEx =
        // "[`~#$&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）—|{}【】‘；：”“’。，、？]";

        String regEx = "[#$&*()|]";

        Pattern pattern = Pattern.compile(regEx);
        Matcher m = pattern.matcher(tel.trim());
        return m.find();
    }

    /**
     * 判断是否为特殊字符
     */
    public static boolean isSpecialChar(String pInput) {
        if (pInput == null) {
            return false;
        }
        String regEx = ".*[&|'|>|<|\\\\|/].*$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(Pattern.compile("[\\r|\\n]").matcher(pInput).replaceAll(""));
        return matcher.matches();
    }

    public static String StringFilter(String str) {
        // 只允许字母和数字
        // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~#$&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）—|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 判断是否为手机号码
     */
    public static boolean isPhone(String phone) {
        // Pattern pattern =
        // Pattern.compile("^((\\(\\d{3}\\))|(\\d{3}\\-))?13[0-9]\\d{8}|15[089]\\d{8}");
        String hkPhone = "^[5,6,9]{1}\\d{7}";
        String normalPhone = "([0-9]{3})([0-9]{4})([0-9]{4})";
        Pattern pattern = Pattern.compile(normalPhone);
        Matcher m = pattern.matcher(phone.trim());
        return m.matches();
    }

    /**
     * 判断输入是否是数字
     */
    public static boolean isNumeric(String str) {
        String strPattern = "[0-9]*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str.trim());
        return m.matches();
    }

    /**
     * 检查是不是生日类型 支持闰年,2月特殊判断等等
     */
    public static boolean isBirthday(String birth) {
        Pattern pt = Pattern
                .compile("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$");
        return pt.matcher(birth).matches();
    }

    /**
     * 判断输入是否是数字或者字母
     */
    public static boolean isNumOrLetter(String str) {
        String strPattern = "^[A-Za-z0-9]+$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str.trim());
        return m.matches();
    }

    /**
     * 匹配身份证
     *
     * @param idCard
     * @return true 如果匹配，false 不匹配
     */
    public static boolean isIDCard(String idCard) {
        String pattern = "^\\d{10}|\\d{13}|\\d{15}|\\d{17}(\\d|x|X)$";
        return idCard.matches(pattern);
    }

    /**
     * 判断字符长度
     *
     * @param str
     * @param maxLen
     * @return
     */
    public static boolean isLength(String str, int maxLen) {
        char[] cs = str.toCharArray();
        int count = 0;
        int last = cs.length;
        for (char c : cs) {
            if (c > 255)
                count += 2;
            else
                count++;
        }
        return count >= maxLen;
    }

    /**
     * 得到格式化时间
     */
    public static String getFormatTimeMsg(int timeInSeconds) {
        int hours, minutes, seconds;
        hours = timeInSeconds / 3600;
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = timeInSeconds / 60;
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = timeInSeconds;

        String minStr = String.valueOf(minutes);
        String secStr = String.valueOf(seconds);

        if (minStr.length() == 1)
            minStr = "0" + minStr;
        if (secStr.length() == 1)
            secStr = "0" + secStr;

        return (minStr + "分" + secStr + "秒");
    }

    /**
     * 加密手机号
     */
    public static String signPhoneNumber(String phone) {
        if (!isNull(phone) && isPhone(phone)) {
            return phone.substring(0, 4) + "****" + phone.substring(8, 11);
        }
        return "";
    }

    /**
     * 剪切字符串
     */
    public static String cutString(String str, int len) {
        if (!StringUtils.isNull(str)) {
            if (str.length() >= len)
                str = str.substring(0, len) + "...";
        }
        return str;
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     */
    public static int getLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 返回指定格式的字符
     *
     * @param list
     * @return
     */
    public static String getValue(HashMap<String, String> list) {
        String value;
        StringBuilder buffer = new StringBuilder();
        for (Object o : list.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            buffer.append(entry.getValue().toString());
        }
        value = buffer.substring(0, buffer.lastIndexOf(","));
        return value;
    }

    /**
     * Unicode 转 GBK
     *
     * @param s
     * @return
     */
    public static String UnicodeToGBK2(String s) {
        String[] k = s.split(";");
        String rs = "";
        for (String aK : k) {
            int strIndex = aK.indexOf("&#");
            String newstr = aK;
            if (strIndex > -1) {
                String kstr = "";
                if (strIndex > 0) {
                    kstr = newstr.substring(0, strIndex);
                    rs += kstr;
                    newstr = newstr.substring(strIndex);
                }
                int m = Integer.parseInt(newstr.replace("&#", ""));
                char c = (char) m;
                rs += c;
            } else {
                rs += aK;
            }
        }
        return rs;
    }

    /**
     * 格式化double数据
     *
     * @param d
     * @param format
     * @return
     */
    public static String formatDecimal(double d, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(d);
    }

    /**
     * 默认保留小数点后两位
     */
    public static String defaultFormatDecimal(double d) {
        return formatDecimal(d, DEFAULT_DOUBLE_FORMAT);
    }

    public static double defaultFormatDecimal_(double d) {
        return Double.valueOf(formatDecimal(d, DEFAULT_DOUBLE_FORMAT));
    }

    /**
     * double string to int string
     */
    public static String formatDoubleStr2IntStr(String price) {
        if (isEmpty(price)) {
            return "0";
        }
        if (!price.contains(".")) {
            return price;
        } else {
            String newPrice = price;
            try {
                int docIndex = price.indexOf(".");
                newPrice = price.substring(0, docIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return newPrice;
        }
    }

    /**
     * string to double
     */
    public static double parse2double(String doubleStr) {
        if (isEmpty(doubleStr)) {
            return 0;
        }
        return Double.parseDouble(doubleStr);
    }


    /**
     * 正则校验
     *
     * @param str     待校验的字符串
     * @param pattern 正则
     * @return
     */
    public static boolean regularVerify(String str, String pattern) {
        return str.matches(pattern);
    }

    public static String formatPriceStr(Double aFloat) {
        DecimalFormat df = new DecimalFormat("#0.0");
        String str = df.format(aFloat);
        String[] strs = str.split("\\.");
        if (strs.length > 1) {
            if (strs[1].equals("0")) {
                return strs[0];
            }
        }
        return str;
    }

    public static String reducePriceSymbol(String s) {
        if (s.contains("$")) {
            s = s.substring((s.indexOf("$") + 1), s.length());
        }
        return s;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
