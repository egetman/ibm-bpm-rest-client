package ru.bpmink.bpm.model.common;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import ru.bpmink.util.Styles;

public class RestEntity implements Describable {

    @Override
    public String describe() {
        ToStringStyle stringStyle = Styles.SHORT_CLASS_WITH_LINE_BREAK;
        return new ReflectionToStringBuilder(this, stringStyle).build();
    }

    @Override
    public String describe(String linePrefix) {
        ToStringStyle stringStyle = new Styles.NoClassNameWithLineBreakToStringStyle(linePrefix);
        return new ReflectionToStringBuilder(this, stringStyle).build();
    }
}
