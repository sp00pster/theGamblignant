package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.LuckPower;

import static theGamblignant.VriskaMod.makeCardPath;

public class CriticalStrike extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(CriticalStrike.class.getSimpleName());
    public static final String IMG = makeCardPath("headshot.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 1;

    public CriticalStrike() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = roll(8,'a')*magicNumber;
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void applyPowers() {
        super.applyPowers();
        int addeddamage = 0;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;}
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;}
        if (addeddamage > 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0]+addeddamage+cardStrings.EXTENDED_DESCRIPTION[2];
        } else if (addeddamage < 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1]+addeddamage+cardStrings.EXTENDED_DESCRIPTION[2];
        }
        if (addeddamage == 0) {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        this.initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
            initializeDescription();
        }
    }
}
