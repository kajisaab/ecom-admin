package com.kajisaab.ecommerce.constants;

import java.util.ArrayList;
import java.util.List;


public class UserRoleConstantEnum{

    public static final UserRoleConstantEnum SUPER_ADMIN = new UserRoleConstantEnum("SUPER_ADMIN", "Super Admin");
    public static final UserRoleConstantEnum ADMIN = new UserRoleConstantEnum("ADMIN", "Admin");

    private final String name;
    private final String displayName;

    public UserRoleConstantEnum(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public static List<UserRoleConstantEnum> getValues() {
        List<UserRoleConstantEnum> values = new ArrayList<>();
        values.add(SUPER_ADMIN);
        values.add(ADMIN);
        return values;
    }

    public String getName(){
        return name;
    }

    public String getDisplayName(){
        return displayName;
    }

    public static UserRoleConstantEnum getByName(String displayName) throws IllegalArgumentException {
        for (UserRoleConstantEnum type : getValues()) {
            if (type.getName().equals(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}
