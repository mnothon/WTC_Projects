package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.Robot;

public class TextWorld extends AbstractWorld {

    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        return mainUpdatePosition(nrSteps);
    }

}
