package com.cdamayab.flox.common;
import java.lang.reflect.Field;

/**
 * Base class for entities
 */
public abstract class BaseEntity {

    /**
     * Returns a string representation of the object, including the name of the class and the values of all its fields.
     * This method uses reflection to access private fields and construct a formatted string representation.
     * If a field is not accessible, it will show "access denied" for that field.
     *
     * @return A string representation of the entity, including field names and their values.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true); // Allows access to private fields
            try {
                sb.append(field.getName()).append(":").append(field.get(this)).append(", ");
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append(":").append("access denied, ");
            }
        }
        return sb.replace(sb.length() - 2, sb.length(), "}").toString();
    }
}