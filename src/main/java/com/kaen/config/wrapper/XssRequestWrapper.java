package com.kaen.config.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @ClassName XssRequestWrapper
 * @Description 防止XSS攻击
 * @Author 李兴
 * @Date 2024/3/5 15:33
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    /**
     * 重写getParameter方法
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        value = format(value);
        return value;
    }

    /**
     * 重写getParameterMap
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramMap = new HashMap<String,String[]>(request.getParameterMap());
        for (Iterator<?> iterator = paramMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) iterator.next();
            String[] values = entry.getValue();
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof String) {
                    values[i] = format(values[i]);
                }
            }
            entry.setValue(values);
        }
        return paramMap;
    }


    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {
        try {
            String[] values = super.getParameterValues(name);
            int count = values.length;   //可能抛空指针
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = format(values[i]);
            }
            return encodedValues;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 重写getHeader
     */
    @Override
    public String getHeader(String name) {
        return format(super.getHeader(name));
    }


    public String filter(String message) {
        if (message == null)
            return (null);
        message = format(message);
        return message;
    }


    /**
     * @param name 要替换的字符
     * @desc 统一处理特殊字符的方法，替换掉sql和js的特殊字符
     */
    private String format(String name) {
        return xssEncode(name);
    }

    /**
     * 将容易引起xss & sql漏洞的半角字符直接替换成全角字符
     *
     * @param s
     * @return
     */
    private static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        } else {
            s = stripXSSAndSql(s);
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    sb.append("＞");// 转义大于号
                    break;
                case '<':
                    sb.append("＜");// 转义小于号
                    break;
                case '\'':
                    sb.append("＇");// 转义单引号
                    break;
                case '\"':
                    sb.append("＂");// 转义双引号
                    break;
                case '&':
                    sb.append("＆");// 转义&
                    break;
                case '#':
                    sb.append("＃");// 转义#
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }


    /**
     * 防止xss跨脚本攻击（替换，根据实际情况调整）
     */
    public static String stripXSSAndSql(String value) {
        if (value != null) {
            Pattern scriptPattern = Pattern.compile("<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("javascript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return value;
    }

}
