package org.catalyst.sample.mysql;

import org.junit.Test;

import java.sql.SQLException;

public class MySqlProviderTest {
    @Test
    public void testMe() throws SQLException {
        MySqlProvider.getInstance().connectToDb();
    }
}
