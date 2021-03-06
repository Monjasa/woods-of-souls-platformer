package org.monjasa.engine.levels;

import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import org.monjasa.engine.PlatformerApplication;
import org.monjasa.engine.entities.PlatformerEntityType;
import org.monjasa.engine.entities.components.EntityHPComponent;
import org.monjasa.engine.perks.PerkTree;

import static com.almasb.fxgl.dsl.FXGL.*;

public class LevelSaveLoadHandler implements SaveLoadHandler {

    @Override
    public void onLoad(DataFile dataFile) {

        Bundle levelBundle = dataFile.getBundle("Level");
        getWorldProperties().setValue("level", levelBundle.<Integer>get("level"));

        Bundle mementoBundle = dataFile.getBundle("Memento");

        getWorldProperties().setValue("coinsCollected", mementoBundle.<Integer>get("coinsCollected"));
        getWorldProperties().setValue("coinsAvailable", mementoBundle.<Integer>get("coinsCollected"));

        Bundle perksBundle = dataFile.getBundle("Perks");
        getWorldProperties().<PerkTree>getObject("perkTree").read(perksBundle);

        Entity player = getGameWorld().getSingleton(PlatformerEntityType.PLAYER);

        player.getComponent(EntityHPComponent.class).setValue(mementoBundle.<Integer>get("EntityHPComponent.value"));
    }

    @Override
    public void onSave(DataFile dataFile) {

        Bundle levelBundle = new Bundle("Level");
        levelBundle.put("level", geti("level"));

        Bundle mementoBundle = FXGL.<PlatformerApplication>getAppCast().getLevelSnapshot().getMementoBundle();

        Bundle perksBundle = new Bundle("Perks");
        getWorldProperties().<PerkTree>getObject("perkTree").write(perksBundle);

        dataFile.putBundle(levelBundle);
        dataFile.putBundle(mementoBundle);
        dataFile.putBundle(perksBundle);
    }
}
