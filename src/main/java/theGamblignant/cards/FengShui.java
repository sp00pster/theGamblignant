package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.FengShuiPlusPower;
import theGamblignant.powers.FengShuiPower;

import static theGamblignant.VriskaMod.makeCardPath;

public class FengShui extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(FengShui.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 1;

    public FengShui() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.upgraded) {
            this.addToBot(new ApplyPowerAction(p, p, new FengShuiPower(p), magicNumber));
        } else {
            this.addToBot(new GainBlockAction(p,roll(8,'p'))); //its kind of a downgrade if it doesnt do this, because youre missing block on the turn you play it
            this.addToBot(new ApplyPowerAction(p, p, new FengShuiPlusPower(p), magicNumber));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}