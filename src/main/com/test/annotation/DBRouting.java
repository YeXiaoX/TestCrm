package test.annotation;

import java.lang.annotation.*;

/**
 * Created by yexiaoxin on 2017/7/31.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DBRouting {
    String name() default "hello";
}
