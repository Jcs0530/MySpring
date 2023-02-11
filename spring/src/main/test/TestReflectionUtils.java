import com.cs.ioc.utils.FileUtils;
import com.cs.ioc.utils.ReflectionUtils;
import com.cs.ioc.utils.StringUtils;
import org.junit.Test;
import com.cs.ioc.testBean.User;

/**
 * @author xiaoqiang
 * @date 2019/10/5-15:37
 */
public class TestReflectionUtils {
    @Test
    public void testnewInstance() {
        Integer x = new Integer(20);
        Object[] objects = {"cs", x};
        System.out.println(ReflectionUtils.newInstance(User.class, new Class[]{String.class, int.class}, objects));

    }

    @Test
    public void testFileUtils() {
        FileUtils.getFiles("com.xiaoqiang.ioc.config");
    }

    @Test
    public void testStringUtil() throws Exception {
        String s = StringUtils.matchMethodName("execution(* aop.pointcut.impl.RegexExp" +
                "ressionPointCutResolver.test(..))");
        String className = StringUtils.matchClassName("execution(* aop.pointcut.impl.RegexExpressionPointCutResolver.test(..))");
        System.out.println(className + "   " + s);
    }

    @Test
    public void test1() {
        int x = 1, y = 4;
        int count = 0;
        while (x > 0 || y >0) {
            count += (x ^ y) & 1;
            x >>= 1;
            y >>= 1;
        }
        System.out.println(count);
    }
}
