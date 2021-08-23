package io.shulie.takin.utils.unit;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author mubai
 * @date 2021-01-05 19:40
 */
public class UnitConvertor {

    /**
     * kb 转换为MB
     *
     * @param source
     * @return
     */
    public static BigDecimal DIVIDE_DATA = new BigDecimal(1024 * 1024);

    public static BigDecimal byteToMb(BigDecimal source) {
        if (null == source) {
            return new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        } else {
            return source.divide(DIVIDE_DATA).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(1122);
        BigDecimal bigDecimal1 = byteToMb(bigDecimal);
        System.out.println(bigDecimal1);
    }
}
