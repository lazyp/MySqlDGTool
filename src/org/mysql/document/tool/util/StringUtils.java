package org.mysql.document.tool.util;

/**
 * String 常用操作工具集
 * 
 * @author hxl
 * @date 2012-7-8下午7:28:28
 */
public final class StringUtils {
    /**
     * 
     * <code>
     * <p>
     * 判断一个String对象是否有长度.
     * </p>
     * NULL return false
     * ''   return false
     * " "  return false
     * </code>
     * 
     * @param s
     * @return
     */
    public static boolean hasLength(String s) {
	return !ObjectUtils.isNull(s) && s.trim().length() > 0;
    }
}
