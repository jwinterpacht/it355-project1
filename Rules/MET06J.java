public class MET06J {
    public class Person implements Cloneable {
        private String name;
    
        @Override
        protected Object clone() throws CloneNotSupportedException {
            Person cloned = (Person) super.clone();
            // Directly copying the field instead of using the setter (which could be overridden)
            cloned.name = this.name;
            return cloned;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    }
    

}
