package com.lpoo1718_t1g3.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.lpoo1718_t1g3.mariokart.Controller.GameController;
import com.lpoo1718_t1g3.mariokart.model.GameModel;

public class CharacterPickerView extends ScreenAdapter {

    private Stage stage;

    private final String TURN_TO_PICK = "It's your turn to pick!";
    private final String WAIT_TO_PICK = "Wait for your turn";
    private final String CHAR_PICKED = "Character locked";
    private final String CHAR_UNAVAILABLE = "Character unavailable";
    private final String WAIT_FOR_PLAYERS = "Waiting for other players";
    private final Label stateLabel;
    private final Label selectedCharacter;
    private final Label characterUnavailable;
    private final TextButton nextButton;
    private final TextButton prevButton;
    private final TextButton pickButton;
    private final Label waitingLabel;
    private Image characterImage;
    private int selectedIndex = 0;
    private final GestureDetector swipeDetector;
    private final InputMultiplexer inputDetector;
    private final SpriteDrawable[] characterTextures = new SpriteDrawable[GameModel.getInstance().getCharacters().size()];

    public CharacterPickerView(){
        this.stage = new Stage();
        this.stage.setDebugAll(true);
        loadTextures();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = ViewDefaults.getDefaultTextFieldFont();

        TextButton.TextButtonStyle selectorStyle = new TextButton.TextButtonStyle();
        selectorStyle.font = ViewDefaults.getDefaultButtonFont();

        stateLabel = new Label("", labelStyle);
        stateLabel.setPosition(stage.getWidth()/2f, stage.getHeight() * 6f/7f, Align.center);
        stateLabel.setAlignment(Align.center);
        stateLabel.setDebug(true);

        characterImage = new Image(characterTextures[selectedIndex]);
        characterImage.setSize(stage.getWidth()/2.5f, stage.getWidth()/2.5f);
        characterImage.setPosition(stage.getWidth()/2f, stage.getHeight() * 4f/7f, Align.center);

        selectedCharacter = new Label("", labelStyle);
        selectedCharacter.setPosition(stage.getWidth()/2f, stage.getHeight() * 3f/7f, Align.center);
        selectedCharacter.setAlignment(Align.center);
        selectedCharacter.setDebug(true);

        characterUnavailable = new Label(CHAR_UNAVAILABLE, labelStyle);
        characterUnavailable.setPosition(stage.getWidth()/2f, stage.getHeight()* 2f/7f, Align.center);
        characterUnavailable.setAlignment(Align.center);

        pickButton = new TextButton("pick", selectorStyle);
        pickButton.setPosition(stage.getWidth()/2f, stage.getHeight()/7f, Align.center);

        pickButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameModel.getInstance().getCharacters().get(selectedIndex).isAvailable()) {
                    GameModel.getInstance().setSelectedCharacterIndex(selectedIndex);
                    GameController.getInstance().pickMessage(selectedIndex);
                }
                super.clicked(event, x, y);
            }
        });

        nextButton = new TextButton(">", selectorStyle);
        nextButton.setPosition(stage.getWidth() * 5f/6f, stage.getHeight() * 3f/5f, Align.center);

        waitingLabel = new Label(WAIT_FOR_PLAYERS, labelStyle);
        waitingLabel.setPosition(stage.getWidth()/2f, stage.getHeight()/6f, Align.center);
        waitingLabel.setAlignment(Align.center);


        nextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateSelectedIndex(1);
                super.clicked(event, x, y);
            }
        });

        prevButton = new TextButton("<", selectorStyle);
        prevButton.setPosition(stage.getWidth() * 1f/6f, stage.getHeight() * 3f/5f, Align.center);

        prevButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                updateSelectedIndex(-1);
                System.out.println(selectedIndex);
                super.clicked(event, x, y);
            }
        });

        this.stage.addActor(stateLabel);
        this.stage.addActor(selectedCharacter);
        this.stage.addActor(characterImage);
        this.stage.addActor(nextButton);
        this.stage.addActor(prevButton);
        this.stage.addActor(characterUnavailable);
        this.stage.addActor(pickButton);
        this.stage.addActor(waitingLabel);

        swipeDetector = new GestureDetector(new GestureDetector.GestureAdapter(){
            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if (velocityX >= 20) updateSelectedIndex(-1);
                else if (velocityX <= -20) updateSelectedIndex(1);
                return super.fling(velocityX, velocityY, button);
            }
        });

        inputDetector = new InputMultiplexer();
        inputDetector.addProcessor(stage);
        inputDetector.addProcessor(swipeDetector);
        Gdx.input.setInputProcessor(inputDetector);
    }

    @Override
    public void render(float delta) {
        drawBackground();
        updateActors();
        stage.act();
        stage.draw();
        GameController.getInstance().updateStatus();
    }

    private void updateActors() {
        switch (GameModel.getInstance().getPickState()){
            case WAIT:
                stateLabel.setText(WAIT_TO_PICK);

                selectedCharacter.setText(GameModel.getInstance().getCharacters().get(selectedIndex).getName());

                characterImage.setDrawable(characterTextures[selectedIndex]);

                prevButton.setVisible(false);

                nextButton.setVisible(false);

                characterUnavailable.setVisible(!GameModel.getInstance().getCharacters().get(selectedIndex).isAvailable());

                pickButton.setDisabled(true);

                waitingLabel.setVisible(false);

                break;

            case PICK:
                stateLabel.setText(TURN_TO_PICK);

                selectedCharacter.setText(GameModel.getInstance().getCharacters().get(selectedIndex).getName());

                characterImage.setDrawable(characterTextures[selectedIndex]);

                prevButton.setVisible(selectedIndex > 0);

                nextButton.setVisible(selectedIndex < GameModel.getInstance().getCharacters().size() - 1);

                characterUnavailable.setVisible(!GameModel.getInstance().getCharacters().get(selectedIndex).isAvailable());

                pickButton.setDisabled(true);

                waitingLabel.setVisible(false);

                break;

            case PICKED:
                inputDetector.clear();

                inputDetector.addProcessor(stage);

                characterImage.setDrawable(characterTextures[selectedIndex]);

                stateLabel.setText(CHAR_PICKED);

                selectedCharacter.setText(GameModel.getInstance().getCharacters().get(selectedIndex).getName());

                prevButton.setVisible(false);

                nextButton.setVisible(false);

                characterUnavailable.setVisible(false);

                pickButton.setVisible(false);

                waitingLabel.setVisible(true);
        }
    }

    private void drawBackground() {
        Gdx.gl.glClearColor(197/255f, 38/255f, 6/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }


    private void updateSelectedIndex(int d){
        if (d == 1){
            if (selectedIndex + 1 < GameModel.getInstance().getCharacters().size()) {
                selectedIndex++;
            }
        } else if (d == -1){
            if (selectedIndex - 1 >= 0) {
                selectedIndex--;
            }
        }
    }

    private void loadTextures(){
        Texture t = new Texture(Gdx.files.internal("picker_images/mario_picker.png"));
        characterTextures[0] = new SpriteDrawable(new Sprite(t));
        t = new Texture(Gdx.files.internal("picker_images/luigi_picker.png"));
        characterTextures[1] = new SpriteDrawable(new Sprite(t));
        t = new Texture(Gdx.files.internal("picker_images/peach_picker.png"));
        characterTextures[2] = new SpriteDrawable(new Sprite(t));
        t = new Texture(Gdx.files.internal("picker_images/toad_picker.png"));
        characterTextures[3] = new SpriteDrawable(new Sprite(t));
        t = new Texture(Gdx.files.internal("picker_images/yoshi_picker.png"));
        characterTextures[4] = new SpriteDrawable(new Sprite(t));
        t = new Texture(Gdx.files.internal("picker_images/bowser_picker.png"));
        characterTextures[5] = new SpriteDrawable(new Sprite(t));
    }
}
