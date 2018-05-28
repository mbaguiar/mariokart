package com.lpoo1718_t1g.mariokart.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ViewDefaults {

    private static final FreeTypeFontGenerator marioFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("SuperMario256.ttf"));
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private static BitmapFont defaultButtonFont;
    private static BitmapFont defaultTextFieldFont;

    public static BitmapFont getDefaultButtonFont(){
        if (defaultButtonFont == null){
            parameter.borderColor = Color.BLACK;
            parameter.borderWidth = 5;
            parameter.size = 80;
            defaultButtonFont = marioFontGenerator.generateFont(parameter);
        }

        return defaultButtonFont;
    }

    public static BitmapFont getDefaultTextFieldFont(){
        if (defaultTextFieldFont == null){
            parameter.borderColor = Color.BLACK;
            parameter.borderWidth = 5;
            parameter.size = 50;
            defaultTextFieldFont = marioFontGenerator.generateFont(parameter);
        }

        return defaultTextFieldFont;
    }




}
