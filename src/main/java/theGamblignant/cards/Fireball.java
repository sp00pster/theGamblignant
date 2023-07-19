package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.VimPower;

import static theGamblignant.VriskaMod.makeCardPath;

public class Fireball extends AbstractVriskaCard {

    public static final Logger logger = LogManager.getLogger(VriskaMod.class.getName());

    public static final String ID = VriskaMod.makeID(Fireball.class.getSimpleName());
    public static final String IMG = makeCardPath("fireball.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public Fireball() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int damageroll = 0;
        damageroll += roll(6,'a');
        for (int i = 1; i < 8; i++) {
            damageroll += roll(6,'r');

            //TODO the first roll IS affected by vim, and further ones aren't, but the RollNumberEffect for the first one doesn't reflect the increased number. internally it is still raised. i have no idea how this is the case
        }
        this.baseDamage = damageroll;
        this.calculateCardDamage(m);
        this.addToBot(new DamageAction(m, new DamageInfo(p, damageroll, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    public void applyPowers() {
        super.applyPowers();
        int addeddamage = 0;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;}
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;}
        if (addeddamage > 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0]+addeddamage+cardStrings.EXTENDED_DESCRIPTION[2];
        } else if (addeddamage < 0) {
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1]+-addeddamage+cardStrings.EXTENDED_DESCRIPTION[2];
        }
        if (addeddamage == 0) {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
