import repos.ConnectionManager;
import view.frames.MainFrame;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager();
        MainFrame studentFrame = new MainFrame(connectionManager);

    }
}
