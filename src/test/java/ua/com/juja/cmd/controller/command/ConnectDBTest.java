package ua.com.juja.cmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.cmd.model.DBManager;
import ua.com.juja.cmd.view.View;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectDBTest {
    private View view;
    private Command command ;
    private DBManager dbManager;

    @Before
    public void setup(){
        view = mock(View.class);
        dbManager = mock(DBManager.class);
        command = new ConnectDB(view, dbManager);
    }

    @Test
    public void testExecute() throws SQLException {
        command.execute("connect|sqlcmd|postgres|postgres");
        verify(dbManager).makeConnection("sqlcmd","postgres","postgres");
        verify(view).write("You are connected to your DB now!" );
    }
}
