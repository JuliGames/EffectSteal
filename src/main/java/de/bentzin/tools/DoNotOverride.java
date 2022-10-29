package de.bentzin.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Ture Bentzin
 * 29.10.2022
 * @implNote Decelerate that this method should not be overridden. Suggested use case is on default interface methods
 * or on non-final methods in classes
 */
@Target(ElementType.METHOD)
public @interface DoNotOverride {
}
