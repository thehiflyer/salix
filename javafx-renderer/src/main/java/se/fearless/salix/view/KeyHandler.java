package se.fearless.salix.view;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class KeyHandler {
	private final SalixRenderer<?> salixRenderer;

	public KeyHandler(SalixRenderer<?> salixRenderer) {
		this.salixRenderer = salixRenderer;
	}


	void handleKeyPresses(Scene scene) {
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.PAGE_UP) {
				salixRenderer.increaseDepth();
			} else if (event.getCode() == KeyCode.PAGE_DOWN) {
				salixRenderer.decreaseDepth();
			}
		});
	}
}
