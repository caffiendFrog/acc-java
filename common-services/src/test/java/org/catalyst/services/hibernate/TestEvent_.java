package org.catalyst.services.hibernate;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(TestEvent.class)
public class TestEvent_ {
    public static volatile SingularAttribute<TestEvent, Long> id;
    public static volatile SingularAttribute<TestEvent, String> title;
    public static volatile SingularAttribute<TestEvent, Date> date;
}
