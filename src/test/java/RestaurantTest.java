import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    LocalTime currentTimeBeforeClosingTime;
    LocalTime currentTimeAfterClosingTime;
    @BeforeEach
    public void addRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        currentTimeBeforeClosingTime = closingTime.minusMinutes(60);
        currentTimeAfterClosingTime = closingTime.plusMinutes(60);

    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spiedRestaurant = Mockito.spy(restaurant);


        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTimeBeforeClosingTime);

        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTimeAfterClosingTime);

        assertFalse(spiedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    //TDD

    //The order value should be calculated and returned,
    //when list of selected Item Names (String) should be passed to this method
    //total order value (int) should be returned
    //The name of the item returned when the user selects the item is always in the menu,
    // hence a fail case scenario/Exception scenario would be unnecessary.
    @Test
    public void order_value_should_be_zero_when_list_of_selected_items_is_empty(){
        List<String> itemNames = new ArrayList<String>();
        assertEquals(0,restaurant.calculateOrderValue(itemNames));
    }

    @Test
    public void order_value_should_be_zero_with_null_list_of_selected_items(){
        List<String> itemNames = null;
        assertEquals(0,restaurant.calculateOrderValue(itemNames));
    }

    @Test
    public void order_value_should_be_calculated_correctly_for_list_of_selected_items(){
        List<String> itemNames = new ArrayList<String>();
        itemNames.add("Sweet corn soup");
        itemNames.add("Vegetable lasagne");
        assertEquals(388,restaurant.calculateOrderValue(itemNames));
    }

}