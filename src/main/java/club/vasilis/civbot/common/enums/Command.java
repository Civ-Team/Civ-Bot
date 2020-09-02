package club.vasilis.civbot.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public enum Command {
    /**
     * minecraft
     */
    MINECRAFT_PING(1, 1, "/ping"),

    FLOWER(1,2,"花价"),

    PRIVATE_DEFAULT(2, 0, ""),
    ;
    /**
     * 类型
     * 1\群聊 2\私聊
     */
    public Integer type;
    /**
     * 关键码
     */
    public Integer code;
    /**
     * 关键词
     */
    public String keyWord;

    Command(Integer type, Integer code, String keyWord) {
        this.type = type;
        this.code = code;
        this.keyWord = keyWord;
    }

    /**
     * 群聊关键词匹配
     *
     * @param keyWord
     * @return
     */
    public static Command groupFind(String keyWord) {

        List<Command> collect = Arrays.stream(Command.values()).filter(e -> e.type.equals(1)).collect(Collectors.toList());
        for (Command value : collect) {
            if (keyWord.toLowerCase().matches(value.keyWord + ".*")) {
                return value;
            }
        }
        return null;
    }

    /**
     * 私聊关键词匹配
     *
     * @param keyWord
     * @return
     */
    public static Command privateFind(String keyWord) {

        List<Command> collect = Arrays.stream(Command.values()).filter(e -> e.type.equals(2)).collect(Collectors.toList());
        for (Command value : collect) {
            if (value.keyWord.equals(keyWord)) {
                return value;
            }
        }
        return Command.PRIVATE_DEFAULT;
    }
}
