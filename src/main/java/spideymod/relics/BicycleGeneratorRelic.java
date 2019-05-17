package spideymod.relics;

import static spideymod.SpideyMod.makeRelicOutlinePath;
import static spideymod.SpideyMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import spideymod.SpideyMod;
import spideymod.util.TextureLoader;

public class BicycleGeneratorRelic extends CustomRelic{
	
    public static final String ID = SpideyMod.makeID("BicycleGenerator");  
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    private final static int CARDS_DRAWN_PER_ENERGY = 20;
    
    //Also need to update the description text if this value changes
    private final static int ENERGY_GAINED = 1;
    
    public BicycleGeneratorRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
        this.counter = 0;
    }
    
    @Override
    public void atBattleStart() {
    	this.counter = 0;
    }
    
    @Override
    public void onCardDraw(AbstractCard drawnCard) {
    	this.counter++;
    	
    	if(this.counter == CARDS_DRAWN_PER_ENERGY) {
    		AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(ENERGY_GAINED));
    		this.counter = 0;
    	}
    }
    
	@Override
    public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + CARDS_DRAWN_PER_ENERGY + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BicycleGeneratorRelic();
    }
}
