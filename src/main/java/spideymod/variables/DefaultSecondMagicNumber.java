package spideymod.variables;

import basemod.abstracts.DynamicVariable;
import spideymod.cards.AbstractDefaultCard;

import com.megacrit.cardcrawl.cards.AbstractCard;

import static spideymod.SpideyMod.makeID;

public class DefaultSecondMagicNumber extends DynamicVariable {
    
    @Override
    public String key() {
        return makeID("SecondMagic");
    }
    
    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDefaultCard) card).isDefaultSecondMagicNumberModified;
    }
    
    @Override
    public int value(AbstractCard card) {
        return ((AbstractDefaultCard) card).defaultSecondMagicNumber;
    }
    
    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard) card).defaultBaseSecondMagicNumber;
    }
    
    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard) card).upgradedDefaultSecondMagicNumber;
    }
}