package club.vasilis.civbot.common.enums;

public enum FlowerEnum {
    /**
     *
     */
    XIU_QIU_HUA("绣球", 1),
    YU_JIN_XIANG("郁金香", 2),
    QIAN_NIU_HUA("牵牛", 3),
    MEI_GUI("玫瑰", 4),
    BAI_HE("百合", 5),
    YING_GUANG_JUN("荧光菌", 5),
    YU_SHAN_DOU_HUA("羽扇豆花", 6),
    ;


    public String flowerName;
    public Integer code;

    FlowerEnum(String flowerName, Integer code) {
        this.flowerName = flowerName;
        this.code = code;
    }

    public static FlowerEnum getFlower(String flowerName) {

        ;
        for (FlowerEnum value : FlowerEnum.values()) {
            if (flowerName.toLowerCase().matches(value.flowerName + ".*")) {
                return value;
            }
        }
        return null;
    }
}
