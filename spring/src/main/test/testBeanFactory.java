import com.alibaba.fastjson.JSON;
import com.xiaoqiang.ioc.bean.ConstructorArg;
import com.xiaoqiang.ioc.config.UserController;
import com.xiaoqiang.ioc.factory.AbstractBeanFactory;
import com.xiaoqiang.ioc.factory.ApplicationContext;
import com.xiaoqiang.ioc.factory.JSONBeanFactory;
import com.xiaoqiang.ioc.utils.ClassLoaderUtils;
import com.xiaoqiang.ioc.utils.ReflectionUtils;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date 2019/10/5-16:31
 */
public class testBeanFactory {

    @Test
    public void testFactory() throws ClassNotFoundException {
        JSONBeanFactory jsonBeanFactory = new JSONBeanFactory("applicationContext.json");
//        System.out.println(jsonBeanFactory.getBean("user"));
        System.out.println(jsonBeanFactory.getBean("user2"));
//        System.out.println(jsonBeanFactory.getBean("dept"));
    }

    @Test
    public void testGetObjectForBeanInstance() {
        Map<String, ConstructorArg> map = new HashMap<>();
        ConstructorArg constructorArg = new ConstructorArg();
        constructorArg.setName("name");
        constructorArg.setValue("xiaoqinag");

        ConstructorArg constructorArg1 = new ConstructorArg();
        constructorArg1.setName("age");
        constructorArg1.setValue("18");


        map.put(constructorArg.getName(), constructorArg);
        map.put(constructorArg1.getName(), constructorArg1);

        System.out.println(map);
        System.out.println(JSON.toJSON(map));
    }

    @Test
    public void test() {
        ApplicationContext applicationContext = new ApplicationContext("applicationContext.properties");
        Object user = applicationContext.getBean("user");
        UserController userController = (UserController) applicationContext.getBean("userController");
        System.out.println(user);
//       System.out.println(userController);
//       System.out.println(userController.user);
        userController.testAop();
    }

    @Test
    public void testConfig() throws NoSuchMethodException {
        UserController userController = new UserController();
        Class<?> clazz = userController.getClass();
        Method testAop = clazz.getMethod("testAop");
        ReflectionUtils.invoke(testAop, userController, null);

    }
}
