package za.co.wethinkcode.toyrobot.world;

import org.turtle.Turtle;

public class TurtleWorld extends AbstractWorld {
    private Turtle turtle;

    public TurtleWorld() {
        this.turtle = new Turtle(0,0,0);
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        UpdateResponse response =  mainUpdatePosition(nrSteps);
        return response;
    }

}
