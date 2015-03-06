package com.nomad.internethaber.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Platform {

    public enum Device {
        PHONE, TABLET, BOTH
    }

    Device device() default Device.BOTH;

}
