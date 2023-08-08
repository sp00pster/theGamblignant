package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.LuckPower;

import static theGamblignant.VriskaMod.makeCardPath;

public class BigBlind extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(BigBlind.class.getSimpleName());
    public static final String IMG = makeCardPath("bigblind.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY; //should be ALL instead since it affects you too? i dont know where ALL is used
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 0;

    public BigBlind() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = 3;
        baseMagicNumber = magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new LuckPower(p, this.magicNumber), this.magicNumber));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, 2), 2, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(mo, p, new LoseStrengthPower(mo, 2), 2, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
