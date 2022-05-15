package Model;

import Model.Materials.Coal;
import Model.Materials.Ice;
import Model.Materials.Iron;
import Model.Materials.Uranium;

public interface IVisitor {
    void visit(TeleportGate tg);
    void visit(Asteroid a);
    void visit(PlayerShip p);
    void visit(RobotShip r);
    void visit(UFO u);
    void visit(Uranium u);
    void visit(Iron i);
    void visit(Ice i);
    void visit(Coal tg);
    void visit(Base b);
}
