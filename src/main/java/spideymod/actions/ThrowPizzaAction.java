package spideymod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import spideymod.vfx.ThrowPizzaEffect;

public class ThrowPizzaAction extends AbstractGameAction{
	
	  private static final float DURATION = 0.01F;
	  private static final float POST_ATTACK_WAIT_DUR = 0.1F;
	  private int amount;
	  
	  public ThrowPizzaAction(AbstractCreature target, int amount)
	  {
	    this.target = target;
	    this.actionType = AbstractGameAction.ActionType.DEBUFF;
	    this.duration = 0.01F;
	    this.amount = amount;
	  }
	  
	  public void update() {
		  if(this.target == null) {
			  this.isDone = true;
			  return;
		  }

		  if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()){
			  AbstractDungeon.actionManager.clearPostCombatActions();
			  this.isDone = true;
			  return;
		  }
		  
		  AbstractPlayer p = AbstractDungeon.player;
		  
		  AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowPizzaEffect(p.hb.cX, p.hb.cY, this.target.hb.cX, this.target.hb.cY), 0.6F));
		 
		  p.gainGold(this.amount);
		  for(int i = 0; i < this.amount; i++) {
			  AbstractDungeon.actionManager.addToBottom(new VFXAction(new GainPennyEffect(p, this.target.hb.cX, this.target.hb.cY, p.hb.cX, p.hb.cY, true)));
		  }
		  
		  AbstractDungeon.actionManager.addToBottom(new HealAction(this.target, this.target, this.amount));
		  
		  this.isDone = true;
	  }
}
