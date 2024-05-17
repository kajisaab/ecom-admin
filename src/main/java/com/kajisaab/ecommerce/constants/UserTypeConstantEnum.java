package com.kajisaab.ecommerce.constants;

import java.util.ArrayList;
import java.util.List;

public class UserTypeConstantEnum {

    public static final UserTypeConstantEnum MAKER = new UserTypeConstantEnum("MAKER", "Maker");
    public static final UserTypeConstantEnum VENDOR = new UserTypeConstantEnum("VENDOR", "Vendor");
    public static final UserTypeConstantEnum CUSTOMER = new UserTypeConstantEnum("CUSTOMER", "Customer");


    private final String displayName;
    private final String name;

    public UserTypeConstantEnum(String name, String displayName){
        this.name = name;
        this.displayName = displayName;
    }

    public static List<UserTypeConstantEnum> getValues() {
        List<UserTypeConstantEnum> values = new ArrayList<>();
        values.add(MAKER);
        values.add(VENDOR);
        values.add(CUSTOMER);
        return values;
    }

    public String getName() {
        return name;
    }
    public String getDisplayName(){
        return displayName;
    }

    // Static method to get the enum constant based on the display name
    public static UserTypeConstantEnum fromDisplayName(String displayName) {
        for (UserTypeConstantEnum userTypeEnum : getValues()) {
            if (userTypeEnum.getDisplayName().equals(displayName)) {
                return userTypeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}
