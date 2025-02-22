package com.bamioezequiel.login.logica;

import com.bamioezequiel.login.logica.User;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-11-26T21:01:46", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, String> nameRol;
    public static volatile ListAttribute<Rol, User> listUsers;
    public static volatile SingularAttribute<Rol, String> description;
    public static volatile SingularAttribute<Rol, Integer> id;

}