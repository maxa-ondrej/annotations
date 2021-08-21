package cz.majksa.tests.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     * The value
     * @return the value
     */
    String value();

}
