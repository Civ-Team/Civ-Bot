package club.vasilis.civbot.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MessageUtils {


    /**
     * 关键词分割
     *
     * @param content
     * @return
     */
    public static List<String> split(String content) {
        return Arrays.stream(content.split(" ")).filter(StringUtils::isNotBlank).collect(Collectors.toList());
    }

    /**
     * 获取关键词
     *
     * @param content 内容
     * @param level 位置
     * @return
     */
    public static String getKeyword(String content, Integer level) {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        List<String> split = split(content);
        if (split.isEmpty() || split.size() < level) {
            return "";
        }
        return split.get(level - 1);
    }
}
