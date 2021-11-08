package com.example.tool.controller;

import com.example.tool.model.RentalContract;
import com.example.tool.service.RentalChargeServiceImpl;
import com.example.tool.service.ToolServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
class ToolControllerTests {

    @Mock
    private static ToolServiceImpl toolService;
    @Mock
    private static RentalChargeServiceImpl rentalChargeService;
    @Autowired
    @InjectMocks
    private RentalChargeController rentalChargeController = new RentalChargeController();

    @Autowired
    public void setToolService(ToolServiceImpl toolService) {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    So usually I would have a separate test class to match every class in the project and unit test the logic everywhere individually, mocking other methods,
    but since I am running very long on time on this demo I am just going to test checkout api in this controller test with pretty basic tests that test everything based on the scenarios provided.
     */
    @Test
    public void happyPathCheckoutTest1() throws Exception {
        String toolCode = "LADW";
        LocalDate rentalDate = LocalDate.parse("2020-07-02");
        int discount = 10;
        int dayCount = 3;
        ResponseEntity<RentalContract> rentalContract = rentalChargeController.checkout(toolCode, dayCount, discount, rentalDate);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(rentalContract),
                () -> Assertions.assertEquals("2020-07-02", rentalContract.getBody().getCheckoutDate().toString()),
                () -> Assertions.assertEquals("2020-07-05", rentalContract.getBody().getDueDate().toString()),
                () -> Assertions.assertEquals(2, rentalContract.getBody().getDaysCharged()),
                () -> Assertions.assertEquals("3.98", rentalContract.getBody().getPreDiscountCharge().toString()),
                () -> Assertions.assertEquals("0.40", rentalContract.getBody().getDiscountAmount().toString()),
                () -> Assertions.assertEquals("3.58", rentalContract.getBody().getFinalCharge().toString())
        );
    }

    @Test
    public void exceptionTest1() throws Exception {
        String toolCode = "JAKR";
        LocalDate rentalDate = LocalDate.parse("2015-09-03");
        int discount = 101;
        int dayCount = 5;
        Throwable exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    ResponseEntity<RentalContract> rentalContract = rentalChargeController.checkout(toolCode, dayCount, discount, rentalDate);
                });
        Assertions.assertEquals("400 BAD_REQUEST \"Discount must be between 0 and 100\"", exception.getMessage());
    }

    @Test
    public void happyPathCheckoutTest2() throws Exception {
        String toolCode = "CHNS";
        LocalDate rentalDate = LocalDate.parse("2015-07-02");
        int discount = 25;
        int dayCount = 5;
        ResponseEntity<RentalContract> rentalContract = rentalChargeController.checkout(toolCode, dayCount, discount, rentalDate);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(rentalContract),
                () -> Assertions.assertEquals("2015-07-02", rentalContract.getBody().getCheckoutDate().toString()),
                () -> Assertions.assertEquals("2015-07-07", rentalContract.getBody().getDueDate().toString()),
                () -> Assertions.assertEquals(3, rentalContract.getBody().getDaysCharged()),
                () -> Assertions.assertEquals("4.47", rentalContract.getBody().getPreDiscountCharge().toString()),
                () -> Assertions.assertEquals("1.12", rentalContract.getBody().getDiscountAmount().toString()),
                () -> Assertions.assertEquals("3.35", rentalContract.getBody().getFinalCharge().toString())
        );
    }

    @Test
    public void happyPathCheckoutTest3() throws Exception {
        String toolCode = "JAKD";
        LocalDate rentalDate = LocalDate.parse("2015-09-03");
        int discount = 0;
        int dayCount = 6;
        ResponseEntity<RentalContract> rentalContract = rentalChargeController.checkout(toolCode, dayCount, discount, rentalDate);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(rentalContract),
                () -> Assertions.assertEquals("2015-09-03", rentalContract.getBody().getCheckoutDate().toString()),
                () -> Assertions.assertEquals("2015-09-09", rentalContract.getBody().getDueDate().toString()),
                () -> Assertions.assertEquals(3, rentalContract.getBody().getDaysCharged()),
                () -> Assertions.assertEquals("8.97", rentalContract.getBody().getPreDiscountCharge().toString()),
                () -> Assertions.assertEquals("0.00", rentalContract.getBody().getDiscountAmount().toString()),
                () -> Assertions.assertEquals("8.97", rentalContract.getBody().getFinalCharge().toString())
        );
    }

    @Test
    public void happyPathCheckoutTest4() throws Exception {
        String toolCode = "JAKR";
        LocalDate rentalDate = LocalDate.parse("2015-07-02");
        int discount = 0;
        int dayCount = 9;
        ResponseEntity<RentalContract> rentalContract = rentalChargeController.checkout(toolCode, dayCount, discount, rentalDate);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(rentalContract),
                () -> Assertions.assertEquals("2015-07-02", rentalContract.getBody().getCheckoutDate().toString()),
                () -> Assertions.assertEquals("2015-07-11", rentalContract.getBody().getDueDate().toString()),
                () -> Assertions.assertEquals(5, rentalContract.getBody().getDaysCharged()),
                () -> Assertions.assertEquals("14.95", rentalContract.getBody().getPreDiscountCharge().toString()),
                () -> Assertions.assertEquals("0.00", rentalContract.getBody().getDiscountAmount().toString()),
                () -> Assertions.assertEquals("14.95", rentalContract.getBody().getFinalCharge().toString())
        );
    }

    @Test
    public void happyPathCheckoutTest5() throws Exception {
        String toolCode = "JAKR";
        LocalDate rentalDate = LocalDate.parse("2020-07-02");
        int discount = 50;
        int dayCount = 4;
        ResponseEntity<RentalContract> rentalContract = rentalChargeController.checkout(toolCode, dayCount, discount, rentalDate);
        Assertions.assertAll(
                () -> Assertions.assertNotNull(rentalContract),
                () -> Assertions.assertEquals("2020-07-02", rentalContract.getBody().getCheckoutDate().toString()),
                () -> Assertions.assertEquals("2020-07-06", rentalContract.getBody().getDueDate().toString()),
                () -> Assertions.assertEquals(1, rentalContract.getBody().getDaysCharged()),
                () -> Assertions.assertEquals("2.99", rentalContract.getBody().getPreDiscountCharge().toString()),
                () -> Assertions.assertEquals("1.50", rentalContract.getBody().getDiscountAmount().toString()),
                () -> Assertions.assertEquals("1.49", rentalContract.getBody().getFinalCharge().toString())
        );
    }
}

