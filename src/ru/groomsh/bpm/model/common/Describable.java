package ru.groomsh.bpm.model.common;

/**
 * Base interface for Json entities pretty print.
 */
public interface Describable {

    String describe();

    String describe(String linePrefix);

}
