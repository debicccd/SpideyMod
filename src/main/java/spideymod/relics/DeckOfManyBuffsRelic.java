package spideymod.relics;

import static spideymod.SpideyMod.makeRelicOutlinePath;
import static spideymod.SpideyMod.makeRelicPath;


import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AfterImagePower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.EnvenomPower;
import com.megacrit.cardcrawl.powers.JuggernautPower;
import com.megacrit.cardcrawl.powers.NoxiousFumesPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThousandCutsPower;
import com.megacrit.cardcrawl.powers.ToolsOfTheTradePower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import spideymod.SpideyMod;
import spideymod.util.TextureLoader;

public class DeckOfManyBuffsRelic extends CustomRelic{
	
    public static final String ID = SpideyMod.makeID("DeckOfManyBuffs");
    
    public AbstractPower[] powerList;
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DeckOfManyBuffs.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DeckOfManyBuffs.png"));
    
    public DeckOfManyBuffsRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }
    
    @Override
    public void onEquip() {
    	super.onEquip();
    	powerList = GetPowers();
    }
    
    @Override
    public void atBattleStart() {
    	if(powerList == null) {
    		powerList = GetPowers();
    	}
    	shuffleArray(this.powerList);
    	
    	AbstractPlayer p = AbstractDungeon.player;
    	
    	AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, powerList[0], powerList[0].amount));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, powerList[1], powerList[0].amount));
    }
    
	@Override
    public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DeckOfManyBuffsRelic();
    }
    
    private static AbstractPower[] GetPowers() {
    	AbstractPlayer p = AbstractDungeon.player;
    	
    	AbstractPower[] powers = {	new StrengthPower(p, 2),
    								new DexterityPower(p, 2),
    								new ArtifactPower(p, 1),
    								new BarricadePower(p),
    								new PlatedArmorPower(p, 5),
    								new NoxiousFumesPower(p, 2),
    								new RetainCardPower(p, 1),
    								new ToolsOfTheTradePower(p, 1),
    								new EnvenomPower(p, 1),
    								new AfterImagePower(p, 1),
    								new ThousandCutsPower(p, 1),
    								new JuggernautPower(p, 3)};
    	
    	return powers;
    }
    
    static void shuffleArray(AbstractPower[] powerList)
    {
      Random r = new Random();
      for (int i = powerList.length - 1; i > 0; i--)
      {
        int index = r.random(i);
        AbstractPower a = powerList[index];
        powerList[index] = powerList[i];
        powerList[i] = a;
      }
    }

}
