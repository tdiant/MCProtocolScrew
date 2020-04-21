package tdiant.mc.protocolscrew.core.trigger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface TriggerTag {
    int packetId() default 0;
}