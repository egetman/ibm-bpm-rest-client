package ru.bpmink.util;

import org.apache.commons.lang3.builder.ToStringStyle;
import ru.bpmink.bpm.model.common.Describable;

import java.util.Collection;

import static ru.bpmink.util.Constants.*;

public class Styles {

    public static class ShortClassNameWithLineBreakToStringStyle extends ToStringStyle {

        public ShortClassNameWithLineBreakToStringStyle() {
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
                value = ((String)value).replaceAll(NEW_LINE, SPACE);
            }
            super.appendDetail(buffer, fieldName, value);
        }

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

        @Override
        protected void appendClassName(StringBuffer buffer, Object object) {
            buffer.append(LINE_SEPARATOR);
            super.appendClassName(buffer, object);
        }
    }

    public static class NoClassNameWithLineBreakToStringStyle extends ToStringStyle {

        private final String linePrefix;

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
                value = ((String)value).replaceAll(NEW_LINE, SPACE);
            }

            super.appendDetail(buffer, fieldName, value);
        }

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
}
