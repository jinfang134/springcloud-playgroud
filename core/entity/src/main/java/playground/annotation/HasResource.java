package playground.annotation;

import java.lang.annotation.*;
import java.util.List;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasResource {
    String value() default "";
}
