package entity;

import entity.Picture;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-28T10:14:31")
@StaticMetamodel(AccountBox.class)
public class AccountBox_ { 

    public static volatile SingularAttribute<AccountBox, String> urlLogin;
    public static volatile SingularAttribute<AccountBox, String> urlPassword;
    public static volatile SingularAttribute<AccountBox, String> name;
    public static volatile SingularAttribute<AccountBox, Long> id;
    public static volatile SingularAttribute<AccountBox, String> url;
    public static volatile SingularAttribute<AccountBox, Picture> picture;

}