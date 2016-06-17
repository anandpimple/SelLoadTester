package gov.tfl.selenium.data;

/**
 * Created by dev on 14/06/16.
 */
public class Header {
    private final String name;
    private final int index;
    public Header(final String name, final int index) {
        if(null == name || name.trim().length() < 1)
            throw new IllegalArgumentException("Name is mandatory for header");
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    public String getName() {
        return name;
    }
    public boolean equals(Object header){
        return null != header && header instanceof  Header && this.getName().equalsIgnoreCase(((Header)header).getName());
    }
    public int hashCode(){
//        System.out.println("Hashcode is "+name.hashCode());
        return name.hashCode();
    }
    public String toString(){
        return "Header : "+name+", index :"+index;
    }
}
