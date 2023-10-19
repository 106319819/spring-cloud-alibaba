package com.nacos.provider.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName Util
 * @Description TODO
 * @Author yao
 * @Date 2023/10/17
 * @Version 1.0
 **/
public class Util
{

	/*
	public static Pageable parse(String content) throws LawException{
		ObjectMapper mapper = new ObjectMapper();
		Pageable pageable = null;
		JsonNode root = null;
		try {
			root = mapper.readTree(content);
			pageable = Util.parse(root);
		} catch (IOException e) {
			LawException.throwing(Error.PARAM_JSON, e);
		}

		return pageable;
	}

	public static Pageable parse(JsonNode root) throws LawException{
		Pageable pageable = null;
		String pk = "page";
		String sk = "size";
		if(!root.has(pk)) {
			pk = "pageSize"; //"\"pageSize\":10,\"pageNumber\":0"
		}
		if(!root.has(sk)) {
			sk = "pageNumber";
		}
		int page = root.get(pk).asInt() -1 ;
		int size = root.get(sk).asInt();
		if(root.has("sorts")){
			// {sorts : [{direction:"ASC",property:"field"}]}
			String dk = "direction";
			String ak = "property";
			List<Order> orders = new ArrayList<Order>();
			JsonNode sorts = root.get("sorts");
			for(JsonNode sort : sorts){
				if(sort.has(dk) && sort.has(ak)) {
					String direction = sort.get(dk).asText();
					String property = sort.get(ak).asText();
					JpaOrder order = (JpaOrder)JpaSort.unsafe(Direction.valueOf(direction), property).getOrderFor(property);
					orders.add(order);
				}
			}
			if(!orders.isEmpty()) {
				pageable = PageRequest.of(page, size, JpaSort.by(orders));
			}
		}else{
			pageable = PageRequest.of(page, size);
		}
		return pageable;
	}

	public static Pageable parseSorts(Pageable pageable, JsonNode sorts) throws LawException {

		// {sorts : [{direction:"ASC",property:"field"}]}
		String dk = "direction";
		String ak = "property";
		List<Order> orders = new ArrayList<Order>();
		for (JsonNode sort : sorts) {
			if (sort.has(dk) && sort.has(ak)) {
				String direction = sort.get(dk).asText();
				String property = sort.get(ak).asText();
				JpaOrder order = (JpaOrder)JpaSort.unsafe(Direction.valueOf(direction), property).getOrderFor(property);
				orders.add(order);
			}
		}
		if (!orders.isEmpty()) {
			pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), JpaSort.by(orders));
		}
		return pageable;
	}
	*/

    /**
     * 字段对应属性的编码规则 去除下画线,以大小写分隔
     *
     * @param colName
     * @param upperCase true 首字大小 false 小写
     * @return
     */
    public static StringBuilder camelCase(String colName,boolean upperCase)
    {
		/*
		StringBuilder prop = new StringBuilder(colName.toLowerCase());
		int length = prop.length();
		for(int i=0; i<length;i++) {
			if(prop.charAt(i) == '_') {
				if(i < length) {
					int next = i+1;
					char ch = prop.charAt(next);
					if(ch >= 'a' && ch <='z') {
						ch -= 32;
						prop.setCharAt(next, ch);
					}
				}
				prop.deleteCharAt(i);
				length--;
			}
		}
		if(length == 0){
			return prop;
		}
		// 首字母大写
		char c = prop.charAt(0);
		if ((c >= 'a' && c <= 'z') && upperCase) {//首字小写转大写
			c -= 32;
			prop.setCharAt(0, c);
		}
		return prop;*/
        return camelCase(colName, '_',upperCase);
    }


    /**
     * 字段对应属性的编码规则 去除指定分割字符,以大小写分隔
     *
     * @param str
     * @param upperCase true 首字大小 false 小写
     * @return
     */
    public static StringBuilder camelCase(final String str,final char split, boolean upperCase)
    {

        StringBuilder prop = new StringBuilder(str.toLowerCase());
        int length = prop.length();
        for(int i=0; i<length;i++) {
            if(prop.charAt(i) == split ) {
                if(i < length) {
                    int next = i+1;
                    char ch = prop.charAt(next);
                    if(ch >= 'a' && ch <='z') {
                        ch -= 32;
                        prop.setCharAt(next, ch);
                    }
                }
                prop.deleteCharAt(i);
                length--;
            }
        }
        if(length == 0){
            return prop;
        }
        // 首字母大写
        char c = prop.charAt(0);
        if ((c >= 'a' && c <= 'z') && upperCase) {//首字小写转大写
            c -= 32;
            prop.setCharAt(0, c);
        }
        return prop;
    }

    /**
     * 将字串的首字母转大小写
     * @param builder 目标字串
     * @param firstUpperCase true 首字大写 false 小写
     * @return
     */
    public static StringBuilder formatChar(StringBuilder builder, boolean firstUpperCase) {
        // 首字母大写
        char c = builder.charAt(0);
        if ((c >= 'a' && c <= 'z') && firstUpperCase) {//首字小写转大写
            c -= 32;
            builder.setCharAt(0, c);
        } else if ((c >= 'A' && c <= 'Z') && !firstUpperCase) {//首字大写转小写
            c += 32;
            builder.setCharAt(0, c);
        }
        return builder;
    }

    /**
     * 判断字符串是否为空或者是空格
     *
     * @author liuguo
     * @since 2009-5-22下午02:52:13
     * @param
     * @return 如果为空或者是空格则返回true，否则返回false。
     */

    public static<T> boolean isNvl(T t)
    {
        if (null == t) {
            return true;
        }
        if(t instanceof String) {
            return (((String) t).trim().length() == 0);
        }
        else if (t instanceof StringBuffer) {
            return (((StringBuffer) t).length() == 0);
        }else if (t instanceof List) {
            return (((List<?>) t).size() == 0);
        }else if (t instanceof Map) {
            return ((Map<?, ?>)t).isEmpty();
        }else if (t instanceof Object[]) {
            return ((Object[]) t).length == 0;
        }else{
            return false;
        }
    }


    public static <T> T nvl(T v, T defv)
    {
        return isNvl(v) ? defv : v;
    }
    public static <T> T nvl(T v, T defv,T tv)
    {
        return isNvl(v) ? defv : tv;
    }


    /**
     * 去除字符串中的回车字符
     *
     * @author 张尧伟
     * @param str
     * @param pattern
     * @return
     */
    public static String clearSpecialChar(String str, String pattern)
    {
        if(Util.isNvl(str)){
            return str;
        }

        String tp = "\\s*|\t|\r|\n";
        if (null != pattern)
        {
            tp = pattern;
        }
        Pattern p = Pattern.compile(tp);
        Matcher m = p.matcher(str);
        String after = m.replaceAll("");
        return after;
    }

    /**
     * 预处理表达式中的换行回车
     * @title
     * @param express 表达式串
     * @return
     */
    public static String prepare(String express)
    {
        if(Util.isNvl(express)){
            return express;
        }
        Pattern comment = Pattern.compile("//(.*)"); //特征是所有以双斜线开头的
        Matcher mc = comment.matcher(express);
        express = mc.replaceAll(""); //替换注释
        Pattern mp2 = Pattern.compile("/\\*(.*?)\\*/", Pattern.DOTALL); //特征是以/*开始，以*/结尾，Pattern.DOTALL的意思是糊涂模式，这种模式下.（点号）匹配所有字符
        Matcher mc2 = mp2.matcher(express);
        express = mc2.replaceAll(""); //替换第二种注释

//		Pattern p3 = Pattern.compile("/\\*\\*(.*?)\\*/", Pattern.DOTALL); //特征是以/**开始，以*/结尾
//		Matcher mc3 = p3.matcher(express);
//		express = mc3.replaceAll(""); //替换第三种注释

        String tp = "\t|\r|\n";
        Pattern p = Pattern.compile(tp);
        Matcher m = p.matcher(express);
        String after = m.replaceAll(" ");
        return after;
    }

    /**
     * 当指定的string buffer 长度大于0时，追加 chars
     *
     * @author 张尧伟
     * @param sb
     * @return StringBuffer
     */
    public  static StringBuilder  appendChars(StringBuilder sb, String chars)
    {
        if(sb.length() > 0) {
            sb.append(chars);
        }
        return sb;
    }
    /**
     * 当指定的string buffer 长度大于0时，追加 chars
     *
     * @author 张尧伟
     * @param sb
     * @return StringBuffer
     */
    public  static StringBuffer  appendChars(StringBuffer sb, String chars)
    {
        if(sb.length() > 0) {
            sb.append(chars);
        }
        return sb;
    }


    /**
     * 从包含路径的文件名中截取完整的文件名（包含扩展名）
     *
     * @example 从C:\\test\test.txt或者/home/test/test.txt中获取test.txt
     * @return
     */
    public static String fetchFileName(String fileName)
    {
        String tmpFileName = "";
        if (fileName.lastIndexOf("\\\\") > 0)
        {
            tmpFileName = fileName.substring(fileName.lastIndexOf("\\\\") + 1);
        }
        else if (fileName.lastIndexOf("\\") > 0)
        {
            tmpFileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        }
        else if (fileName.lastIndexOf("/") > 0)
        {
            tmpFileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        }
        else
        {
            tmpFileName = fileName;
        }
        return tmpFileName;
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件全名
     * @return .ext
     */
    public static String fetchFileExt(String fileName)
    {
        String tmp[] = fileName.split("\\.");
        return "." + tmp[tmp.length - 1];
    }




    public static<T> T get(List<T> list,int index) {
        if(Util.isNvl(list)) {
            return null;
        }

        if(list.size() > index){
            return list.get(index);
        }else {
            return null;
        }
    }


    /**
     * URL编码
     * @param str
     * @param enc
     * @return String
     */
    public static String encodeUrl(String str, String enc) {
        String tmp = null;
        try {
            tmp = URLEncoder.encode(str, enc);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (null == tmp ) ? str : tmp;
    }

    /**
     * 编码 BASE64
     * @param data
     * @param isRFC822 是否为标准的RFC822要求，即转换时每76个字符就换行
     * @return String
     */
    public static String encodeBASE64(byte[] data,boolean isRFC822 ){
        String str = Base64.encodeBase64String(data);
        // String str =  new sun.misc.BASE64Encoder().encode(data);
        if(isRFC822){//标准的，不删除换行
            return str;
        }else{//自定义的，删除换行
            return str.replaceAll("[\\s*\t\n\r]", "");
        }
    }
    public static <T> Map<String, Object> buildKeyValueMap(T object) {
        Map<String, Object> map = new HashMap<>();
        if(null == object) {
            return map;
        }
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(object.getClass());
        try {
            for (PropertyDescriptor pd : pds) {
                Method method = pd.getReadMethod();
                map.put(pd.getName(), method.invoke(object));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     *
     * @title
     * @param object
     * @return
     */
    public static <T> Map<String, Object> buildKeyValueMap(T object,String... properties) {

        if(Util.isNvl(properties)) {
            return buildKeyValueMap(object);
        }

        Map<String, Object> map = new HashMap<>();
        if(null == object) {
            return map;
        }
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(object.getClass());
        try {
            for(String property : properties) {
                for (PropertyDescriptor pd : pds) {
                    Method method = pd.getReadMethod();
                    if(property.equals( pd.getName()) ) {
                        map.put(pd.getName(), method.invoke(object));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     *
     *
     * @param object
     * @param excludeProperties
     * @return
     */
    public static <T> Map<String, Object> buildKeyValueMapEx(T object,String... excludeProperties) {
        if(Util.isNvl(excludeProperties)) {
            return buildKeyValueMap(object);
        }

        Map<String, Object> map = new HashMap<>();
        if(null == object) {
            return map;
        }
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(object.getClass());
        try {
            for (PropertyDescriptor pd : pds) {
                Method method = pd.getReadMethod();
                boolean ex = false;
                for(String exclude : excludeProperties) {
                    if(exclude.equals( pd.getName()) ) {
                        ex = true;
                        break;
                    }
                }
                if(!ex) {
                    map.put(pd.getName(), method.invoke(object));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


}
