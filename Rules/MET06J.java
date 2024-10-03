/**
 * The MET06J class demonstrates the implementation of a cloneable Person class.
 * Rule: Do not invoke overridable methods in clone()
 */
public class MET06J {
    
    /**
     * The Person class represents an individual with a name and supports cloning.
     */
    public class Person implements Cloneable {
        private String name;

        /**
         * Clones the current Person object.
         *
         * @return A new Person object that is a clone of this instance.
         * @throws CloneNotSupportedException if the object's class does not support the Cloneable interface.
         */
        @Override
        protected Object clone() throws CloneNotSupportedException {
            Person cloned = (Person) super.clone();
            // Directly copying the field instead of using the setter (which could be overridden)
            cloned.name = this.name;
            return cloned;
        }

        /**
         * Gets the name of the person.
         *
         * @return The name of the person.
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the name of the person.
         *
         * @param name The name to set for the person.
         */
        public void setName(String name) {
            this.name = name;
        }
    }
}
