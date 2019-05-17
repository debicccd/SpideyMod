package spideymod.relics;

import static spideymod.SpideyMod.makeRelicOutlinePath;
import static spideymod.SpideyMod.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import basemod.abstracts.CustomRelic;
import spideymod.SpideyMod;
import spideymod.actions.SpideyTargetAction;
import spideymod.actions.ThrowPizzaAction;
import spideymod.util.TextureLoader;

public class PizzaDeliveryRelic extends CustomRelic implements ClickableRelic{
	
    public static final String ID = SpideyMod.makeID("PizzaDelivery");
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PizzaDelivery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PizzaDelivery.png"));
    
    private static final String TARGET_FULL_HP_TEXT = "That enemy is already at max hp!";
    private static final String TARGET_IS_MINION = "Can't target a minion!";
    
    private static final int MAX_HP_HEALED = 30;
    
    private boolean usedThisCombat = false;
	
	public PizzaDeliveryRelic() {
		super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
	}
	
	@Override
	public void atBattleStart() {
		this.usedThisCombat = false;
	}
	
	@Override
	public void onRightClick() {
        if (!isObtained || this.usedThisCombat) {
            return;
        }
        
        new SpideyTargetAction(this);
	}
	
	public void deliverPizza(AbstractCreature c) {
		if(c.hasPower("Minion")) {
			AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, TARGET_IS_MINION, true));
			return;
		}
		
		if(c.currentHealth == c.maxHealth) {
			AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, TARGET_FULL_HP_TEXT, true));
			return;
		}
		
		int amtHealed = 0;
		
		if( (c.maxHealth - c.currentHealth) >= MAX_HP_HEALED) {
			amtHealed = MAX_HP_HEALED;
		}else {
			amtHealed = c.maxHealth - c.currentHealth;
		}
		
		if(amtHealed <= 0) {
			amtHealed = 1;
		}
		
		AbstractDungeon.actionManager.addToBottom(new ThrowPizzaAction(c, amtHealed));
		this.usedThisCombat = true;
	}
	
	@Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + MAX_HP_HEALED + DESCRIPTIONS[1];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PizzaDeliveryRelic();
    }

}
