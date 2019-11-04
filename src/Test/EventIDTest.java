package Test;

import DAO.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventIDTest extends TestParent {
    private Database db;

    @BeforeEach
    public void setUp() throws Exception {
        db = new Database();
        super.setUp(db);
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown(db);
    }

    @Test
    public void getEventPass() {

    }
    @Test
    public void getEventFail() throws Exception {
    }
}
