package com.lpb.lifecardsdk.util;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Base64;

import com.google.gson.Gson;
import com.lpb.lifecardsdk.constant.Config;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatter;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlFormatterBuilder;
import com.lpb.lifecardsdk.widget.htmltextview.HtmlResImageGetter;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {

    private static final String TAG = CommonUtils.getTag(StringUtils.class);

    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("###");
    private static final DecimalFormat NUMBER_FORMAT2 = new DecimalFormat("#,###");
    private static final String NICKNAME_PATTERN = "^[a-zA-Z一-龯ぁ-んァ-ン ]+$";
    private static final String FORMAT_EMAIL_PATTERN =
            "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static final String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static final String EMAIL_VALID_LENGTH_PATTERN =
            "^([A-Z0-9a-z._-]{1,64}@[A-Z0-9a-z._-]+){1,255}";
    private static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]*$";
    private static final String DUPLICATE_SPECIAL_CHARACTERS_PATTERN = "[\\.\\-\\_][\\.\\-\\_]";
    private static final String PHONE_PATTERN = "0\\d{9}";

    static {
        Locale locale = new Locale("vi", "VN");
        DecimalFormatSymbols symbol = new DecimalFormatSymbols(locale);
        NUMBER_FORMAT2.setDecimalFormatSymbols(symbol);
    }

    public static boolean checkPhonePattern(String text) {
        return text.matches(PHONE_PATTERN);
    }

    public static boolean isHalfWidthString(String text) {
        boolean isHalfWidthString = true;
        char[] chars = text.toCharArray();
        for (char c : chars) {
            isHalfWidthString = isHalfWidthString && isHalfWidthChar(c);
        }
        return isHalfWidthString;
    }

    public static String capitalize(String str) {
        if (str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Spanned convertHTMLToString(String sHTML, Context context) {
        if (sHTML == null || sHTML.equals("<ul>null</ul>"))
            return null;
        return HtmlFormatter.formatHtml(new HtmlFormatterBuilder().setHtml(sHTML).setImageGetter(new HtmlResImageGetter(context)));
    }

    public static String boldString(String str) {
        return "<b>" + str + "</b>";
    }

    public static String deleteString(String str) {
        return "<del>" + str + "</del>";
    }

    public static String dotsString(String str) {
        return "<ul>" + str + "</ul>";
    }


    public static String convertStringToBase64(String json) {
        byte[] data = new byte[0];
        try {
            data = json.getBytes(Config.CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64.replaceAll("\n", "");
    }

    public static String convertObjectToBase64(Object o) {
        Gson gson = new Gson();
        String json = gson.toJson(o);
        byte[] data = new byte[0];
        try {
            data = json.getBytes(Config.CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64.replaceAll("\n", "");
    }

    public static SpannableString underlineText(String text) {
        SpannableString content = new SpannableString(text);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    public static boolean isHalfWidthChar(char c) {
        return c <= '\u00FF' || '\uFF61' <= c && c <= '\uFFDC' || '\uFFE8' <= c && c <= '\uFFEE';
    }

    public static boolean isAlphanumericString(String text) {
        return Pattern.compile(ALPHANUMERIC_PATTERN, Pattern.CASE_INSENSITIVE).matcher(text).find();
    }

    public static boolean isEmailString(String text) {
        return getPatternEmail().matcher(text).find();
    }

    public static boolean isFormatEmailString(String text) {
        return Pattern.compile(FORMAT_EMAIL_PATTERN, Pattern.CASE_INSENSITIVE).matcher(text).find();
    }

    public static boolean isEmailValidLength(String text) {
        return Pattern.compile(EMAIL_VALID_LENGTH_PATTERN, Pattern.CASE_INSENSITIVE)
                .matcher(text)
                .find();
    }

    public static boolean isNicknameString(String text) {
        return Pattern.compile(NICKNAME_PATTERN, Pattern.CASE_INSENSITIVE).matcher(text).find();
    }

    public static Pattern getPatternEmail() {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.UNICODE_CASE);
        return pattern;
    }

    public static Pattern getPatternNickName() {
        Pattern pattern = Pattern.compile(NICKNAME_PATTERN, Pattern.CASE_INSENSITIVE);
        return pattern;
    }

    public static Pattern getPatternAlphanumeric() {
        return Pattern.compile(ALPHANUMERIC_PATTERN, Pattern.CASE_INSENSITIVE);
    }

    public static boolean hasContinuousDuplicateSpecialCharacters(String text) {
        return Pattern.compile(DUPLICATE_SPECIAL_CHARACTERS_PATTERN, Pattern.CASE_INSENSITIVE)
                .matcher(text)
                .find();
    }

    public static String formatPriceToVND(Integer price_) {
        if (price_ == null) {
            return "0";
        } else {
            NumberFormat format =
                    new DecimalFormat("#,##0.00");
            format.setCurrency(Currency.getInstance(Locale.US));
            String price = String.valueOf(price_);
            price = (!TextUtils.isEmpty(price)) ? price : "0";
            price = price.trim();
            price = format.format(Double.parseDouble(price));
            price = price.replaceAll(",", "\\.");

            if (price.endsWith(".00")) {
                int centsIndex = price.lastIndexOf(".00");
                if (centsIndex != -1) {
                    price = price.substring(0, centsIndex);
                }
            }
            price = String.format("%s", price);
            return price;
        }
    }

    public static int formatPrice(String price) {
        try {
            return Integer.parseInt(price.replaceAll("[\\.\\,\\D]", ""));
        } catch (Exception e) {
            return 0;
        }

    }

    public static String formatPrice(double input) {
        return NUMBER_FORMAT2.format(input);
    }

    public static String formatPrice(long input) {
        return NUMBER_FORMAT2.format(input);
    }

    public static String formatPrice(float input) {
        return NUMBER_FORMAT2.format(input);
    }

    public static String formatPrice(int input) {
        return NUMBER_FORMAT2.format(input);
    }

    public static String formatPrice(short input) {
        return NUMBER_FORMAT2.format(input);
    }

    public static String changeTextToBold(String word) {
        return "<b>" + word + "</b>";
    }

    public static boolean isEmpty(CharSequence data) {
        return data == null || isEmpty(data.toString());
    }

    public static boolean isEmpty(String data) {
        return data == null || data.trim().length() == 0;
    }

    public static String nvl(String str1, String str2) {
        return str1 == null || "".equals(str1) ? str2 : str1;
    }

    public static String lpad(String str1, int length, String str2) {
        StringBuilder sb = new StringBuilder();

        for (int toPrepend = length - str1.length(); toPrepend > 0; toPrepend--) {
            sb.append(str2);
        }

        sb.append(str1);

        return sb.toString();
    }

    public static String valueOf(double input) {
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(long input) {
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(float input) {
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(int input) {
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(short input) {
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(Double input) {
        if (input == null)
            return null;
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(Long input) {
        if (input == null)
            return null;
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(Float input) {
        if (input == null)
            return null;
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(Integer input) {
        if (input == null)
            return null;
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(Short input) {
        if (input == null)
            return null;
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(BigDecimal input) {
        if (input == null)
            return null;
        return NUMBER_FORMAT.format(input);
    }

    public static String valueOf(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(Config.FORMAT_DATE);

        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    public static String valueOfServer(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(Config.FORMAT_TIMEZONE_SERVER);

        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    public static String formatDate(String input) {
        String date = input;
        try {
            date = DateUtils.convertDateToString(DateUtils.convertToDate(input,
                    Config.FORMAT_TIMEZONE_SERVER).getTime(), DateUtils.CALENDAR_DATE_FORMAT_DD_MM_YY_HH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}