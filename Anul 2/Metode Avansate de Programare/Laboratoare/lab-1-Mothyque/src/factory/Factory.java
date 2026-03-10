package factory;

import domain.Container;
import domain.Strategy;

public interface Factory
{
    Container createContainer(Strategy strategy);
}
