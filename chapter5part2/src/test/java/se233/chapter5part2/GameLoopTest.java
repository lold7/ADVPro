package se233.chapter5part2;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se233.chapter5part2.controller.GameLoop;
import se233.chapter5part2.model.Direction;
import se233.chapter5part2.model.Food;
import se233.chapter5part2.model.Snake;
import se233.chapter5part2.view.GameStage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GameLoopTest {
    private GameStage gameStage;
    private Snake snake;
    private Food food;
    private GameLoop gameLoop;

    @BeforeEach
    public void setUp() {
        gameStage = new GameStage();
        snake = new Snake(new Point2D(5, 5));
        food = new Food(new Point2D(5, 6));
        gameLoop = new GameLoop(gameStage, snake, food);
    }

    private void clockTickHelper() throws Exception {
        ReflectionHelper.invokeMethod(gameLoop, "keyProcess", new Class<?>[]{});
        ReflectionHelper.invokeMethod(gameLoop, "checkCollision", new Class<?>[]{});
        ReflectionHelper.invokeMethod(gameLoop, "redraw", new Class<?>[]{});
    }

    @Test
    public void keyProcess_pressRight_snakeTurnRight() throws Exception {
        ReflectionHelper.setField(gameStage, "key", KeyCode.RIGHT);
        snake.setDirection(Direction.DOWN);
        clockTickHelper();
        assertEquals(Direction.RIGHT, snake.getDirection());
    }

    @Test
    public void keyProcess_whenMovingRight_ignoresLeftKey() throws Exception {
        // 1. Arrange: Set initial direction to RIGHT and press the LEFT key
        snake.setDirection(Direction.RIGHT);
        ReflectionHelper.setField(gameStage, "key", KeyCode.LEFT);

        // 2. Act: Run the key processing logic
        ReflectionHelper.invokeMethod(gameLoop, "keyProcess", new Class<?>[]{});

        // 3. Assert: The direction should remain RIGHT
        assertEquals(Direction.RIGHT, snake.getDirection(), "Snake should not be able to reverse direction from RIGHT to LEFT.");
    }

    @Test
    public void collided_snakeEatFood_shouldGrow() throws Exception {
        clockTickHelper();
        assertTrue(snake.getLength() > 1);
        clockTickHelper();
        assertNotSame(food.getPosition(), new Point2D(5, 6));
    }

    @Test
    public void collided_snakeHitBorder_shouldDie() throws Exception {
        // Arrange
        snake = new Snake(new Point2D(0,0));
        gameLoop = new GameLoop(gameStage, snake, food);
        snake.setDirection(Direction.UP); // Set direction towards the border

        // Act
        ReflectionHelper.setField(gameStage, "key", KeyCode.UP);
        clockTickHelper();

        // Assert
        Boolean running = (Boolean) ReflectionHelper.getField(gameLoop, "running");
        assertFalse(running);
    }
    @Test
    public void redraw_calledThreeTimes_snakeAndFoodShouldRenderThreeTimes() throws Exception {
        GameStage mockGameStage = Mockito.mock(GameStage.class);
        Snake mockSnake = Mockito.mock(Snake.class);
        Food mockFood = Mockito.mock(Food.class);
        GameLoop gameLoop = new GameLoop(mockGameStage, mockSnake, mockFood);
        ReflectionHelper.invokeMethod(gameLoop, "redraw", new Class<?>[]{});
        ReflectionHelper.invokeMethod(gameLoop, "redraw", new Class<?>[]{});
        ReflectionHelper.invokeMethod(gameLoop, "redraw", new Class<?>[]{});
        verify(mockGameStage, times(3)).render(mockSnake, mockFood);
    }
}
