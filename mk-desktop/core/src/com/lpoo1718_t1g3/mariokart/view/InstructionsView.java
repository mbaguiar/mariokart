package com.lpoo1718_t1g3.mariokart.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.MarioKart;
import com.lpoo1718_t1g3.mariokart.controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;

import javax.xml.soap.Text;

/**
 * Class that represents the instructions view
 */
public class InstructionsView extends ScreenAdapter {

    private Stage stage;
    private Sprite background;

    /**
     * Initializes instructions view
     */
    public InstructionsView() {
        loadAssests();
        stage = new com.badlogic.gdx.scenes.scene2d.Stage();
        Gdx.input.setInputProcessor(stage);

        Texture texture = MarioKart.getInstance().getAssetManager().get("mario_background.png");
        background = new Sprite(texture);
        background.setSize(stage.getWidth(), stage.getHeight());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        parameter.size = 100;
        labelStyle.font = generator.generateFont(parameter);
        Label instructionsLabel = new Label("Instructions", labelStyle);
        instructionsLabel.setPosition(stage.getWidth()/2, stage.getHeight() * 0.9f, Align.center);

        parameter.size = 70;
        style.font = generator.generateFont(parameter);
        TextButton backButton = new TextButton("Back", style);
        backButton.setPosition(stage.getWidth() / 2, stage.getHeight() * 0.1f, Align.center);

        parameter.size = 20;
        parameter.borderWidth = 3;
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = generator.generateFont(parameter);
        textFieldStyle.fontColor = Color.WHITE;
        TextArea textArea = new TextArea("1. Click play to enter the lobby screen\n\n\n" +
                "2. Enter your party name and click start lobby\n\n\n" +
                "3. Use your phone to connect to the server using\n\n either the QRCode (requires BarcodeScanner)\n\n or the given ip address\n\n\n" +
                "4. Register your name\n\n\n" +
                "5. Click play to pick your characters\n\n\n" +
                "6. When you're done picking, the race starts \n\nautomatically.\n\n\n" +
                "7. Have fun while racing your friends through 3\n\nlaps of a crazy Mario Kart racetrack.\n\n\n" +
                "8. Repeat", textFieldStyle);
        textArea.setPosition(stage.getWidth() / 3f, stage.getHeight()* 0.2f, Align.center);
        textArea.setHeight(stage.getHeight() / 1.8f);
        textArea.setWidth(stage.getWidth() / 2);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameModel.getInstance().setNextScreen(GameModel.game_screen.MENU);
            }
        });

        this.stage.addActor(instructionsLabel);
        this.stage.addActor(backButton);
        this.stage.addActor(textArea);
        generator.dispose();

    }

    @Override
    public void render(float delta) {
        MarioKart.getInstance().getBatch().begin();
        drawBackground();
        MarioKart.getInstance().getBatch().end();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void loadAssests() {
        MarioKart.getInstance().getAssetManager().load("mario_background.png", Texture.class);
        MarioKart.getInstance().getAssetManager().finishLoading();
    }

    private void drawBackground() {
        background.draw(MarioKart.getInstance().getBatch());
    }

}
