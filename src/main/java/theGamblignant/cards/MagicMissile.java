package theGamblignant.cards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class MagicMissile extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(MagicMissile.class.getSimpleName());
    public static final String IMG = makeCardPath("magicmissile.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = -1;


    public MagicMissile() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.doesRoll = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.energyOnUse;
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        if (upgraded) {
            effect += 1;
        }

        if (effect > 0) {
            this.baseDamage = roll(effect, 20, 'a');
            this.calculateCardDamage(m);
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int addeddamage = 0;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;}
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;}
        if (!this.upgraded) {
            if (addeddamage > 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + addeddamage + cardStrings.EXTENDED_DESCRIPTION[4];
            } else if (addeddamage < 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + -addeddamage + cardStrings.EXTENDED_DESCRIPTION[4];
            } else {this.rawDescription = cardStrings.DESCRIPTION;}
        } else {
            if (addeddamage > 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + addeddamage + cardStrings.EXTENDED_DESCRIPTION[4];
            } else if (addeddamage < 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3] + -addeddamage + cardStrings.EXTENDED_DESCRIPTION[4];
            } else {this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;}
        }
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
