package ru.bpmink.util;

import org.apache.commons.lang3.builder.ToStringStyle;

import ru.bpmink.bpm.model.common.Describable;

import java.util.Collection;

import static ru.bpmink.util.Constants.CLOSE_BRACKET;
import static ru.bpmink.util.Constants.EQUALS;
import static ru.bpmink.util.Constants.LINE_SEPARATOR;
import static ru.bpmink.util.Constants.NEW_LINE;
import static ru.bpmink.util.Constants.NULL_STRING;
import static ru.bpmink.util.Constants.OPEN_BRACKET;
import static ru.bpmink.util.Constants.SPACE;
import static ru.bpmink.util.Constants.TAB;

public class Styles {

    //Can be just once instantiated.
    public static final ToStringStyle SHORT_CLASS_WITH_LINE_BREAK = new ShortClassNameWithLineBreakToStringStyle();

    private static class DescribableStyle extends ToStringStyle {

        @Override
        @SuppressWarnings("unchecked")
        protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll) {
            if (coll == null || coll.isEmpty() || !(coll.iterator().next() instanceof Describable)) {
                super.appendDetail(buffer, fieldName, coll);
            } else {
                Collection<Describable> describableCollection = (Collection<Describable>) coll;
                for (Describable describable : describableCollection) {
                    appendDetail(buffer, fieldName, describable);
                }
            }
        }

    }

    private static class ShortClassNameWithLineBreakToStringStyle extends DescribableStyle {

        /**
         * <p>Controls <code>String</code> formatting
         * for {@link org.apache.commons.lang3.builder.ReflectionToStringBuilder}.</p>
         */
        private ShortClassNameWithLineBreakToStringStyle() {
            super();
            setUseShortClassName(true);
            setUseIdentityHashCode(false);
            setFieldSeparatorAtStart(true);

            setNullText(NULL_STRING);
            setContentStart(SPACE + OPEN_BRACKET);
            setFieldSeparator(LINE_SEPARATOR + TAB);
            setFieldNameValueSeparator(SPACE + EQUALS + SPACE);
            setContentEnd(LINE_SEPARATOR + CLOSE_BRACKET);
        }

        @Override
        protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
            if (value instanceof Describable) {
                value = ((Describable) value).describe(getFieldSeparator() + TAB);
            } else if (value instanceof String) {
                value = ((String) value).replaceAll(NEW_LINE, SPACE);
            }
            super.appendDetail(buffer, fieldName, value);
        }


        @Override
        protected void appendClassName(StringBuffer buffer, Object object) {
            buffer.append(LINE_SEPARATOR);
            super.appendClassName(buffer, object);
        }
    }

    public static class NoClassNameWithLineBreakToStringStyle extends DescribableStyle {

        private final String linePrefix;

        /**
         * <p>Controls <code>String</code> formatting
         * for {@link org.apache.commons.lang3.builder.ReflectionToStringBuilder}.</p>
         *
         * @param linePrefix is default prefix for each line.
         */
        public NoClassNameWithLineBreakToStringStyle(String linePrefix) {
            super();
            if (linePrefix == null) {
                throw new IllegalArgumentException("LinePrefix can't be null!");
            }
            this.linePrefix = linePrefix;

            setUseShortClassName(false);
            setUseClassName(false);
            setUseIdentityHashCode(false);
            setFieldSeparatorAtStart(false);

            setNullText(NULL_STRING);
            setContentStart(OPEN_BRACKET + linePrefix);
            setFieldSeparator(linePrefix);
            setFieldNameValueSeparator(SPACE + EQUALS + SPACE);
            setContentEnd(linePrefix.substring(0, linePrefix.length() - 1) + CLOSE_BRACKET);
        }

        @Override
        protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
            if (value instanceof Describable) {
                value = ((Describable) value).describe(linePrefix + TAB);
            } else if (value instanceof String) {
                value = ((String) value).replaceAll(NEW_LINE, SPACE);
            }

            super.appendDetail(buffer, fieldName, value);
        }

    }
}
