import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Student name:Ndaedzo Tshiovhe
 * student number:10487456
 * @author kinta
 */
public class IProductSalesTest {

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
    public void GetSalesUnderLimit_ReturnsNumberOfSales() {
        int expected = 4; // 300, 150, 250, 200
        int actual = ps.GetSalesUnderLimit();
        assertEquals(expected, actual);
    }
}
