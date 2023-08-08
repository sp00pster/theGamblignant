package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class UncannyDodge extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(UncannyDodge.class.getSimpleName());
    public static final String IMG = makeCardPath("uncannydodge.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 1;

    public UncannyDodge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = 2;
        baseMagicNumber = magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int block = roll(1, 8,'a')*magicNumber;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p,p,block));
    }

    //todo add the stringupdate thing so that it works with positive/negative dexterity and reflects it on the card text

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
