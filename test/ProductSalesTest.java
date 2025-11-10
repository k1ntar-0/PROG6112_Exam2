import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductSalesTest {

    private ProductSales ps;

    @Before
    public void setUp() {
        int[][] salesData = {
            {300, 150, 700}, // Year 1
            {250, 200, 600}  // Year 2
        };
        ps = new ProductSales(salesData);
    }

    @Test
    public void GetSalesOverLimit_ReturnsNumberOfSales() {
        int expected = 2; // 700 and 600
        int actual = ps.GetSalesOverLimit();
        assertEquals(expected, actual);
    }
}
