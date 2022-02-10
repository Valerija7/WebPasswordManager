package entity;

import entity.AccountBox;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-10T14:32:45")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, AccountBox> accountbox;
    public static volatile SingularAttribute<User, String> firstname;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> salt;
    public static volatile SingularAttribute<User, String> phone;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> lastname;

}