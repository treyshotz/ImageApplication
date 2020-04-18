package NTNU.IDATT1002.repository;

import javax.persistence.criteria.Order;

/**
 * To be implemented.
 */
public class Sort {

    public enum Type {
        NAME("name"),
        DATE("uploadedAt");

        private String field;

        Type(String field) {
            this.field = field;
        }

        public static Type from(String field) {
            for (Type type : Type.values())
                if (type.field.equals(field))
                    return type;

            return null;
        }
    }

    public Sort(Type from) {
    }

    public static Sort of(Type type) {
        return null;
    }

    // possibly only pass requested field
    public static Sort of(String field) {
        return new Sort(Type.from(field));
    }



    public Order getSort() {
        // return generated sort

        return null;
    }

}
