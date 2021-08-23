import io.shulie.takin.parent.exception.entity.BaseException;
import io.shulie.takin.parent.exception.entity.ExceptionCode;
import org.junit.Test;

public class ExceptionTest {

    @Test
    public void test() {

        ///throw new BaseException(ExceptionCode.HTTP_UTIL_URL_EMPTY,"你异常");
        try {
            Integer i = null;
            if(i / 0 == 0) {
                System.out.println(false);
            }
        } catch (Throwable e) {
           throw new BaseException(ExceptionCode.HTTP_UTIL_URL_EMPTY,"sasas");
        }
    }

}
