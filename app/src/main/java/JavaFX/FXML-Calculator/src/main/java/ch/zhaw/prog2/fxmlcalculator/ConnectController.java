package ch.zhaw.prog2.fxmlcalculator;

import javafx.scene.Parent;

public interface ConnectController {
	void setModel(ValueHandler model);
	void setParentSceneGraph(Parent sceneGraph);
}
