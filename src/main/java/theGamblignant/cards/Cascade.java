package theGamblignant.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import theGamblignant.powers.CursePower;

import static theGamblignant.VriskaMod.makeCardPath;

public class Cascade extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(Cascade.class.getSimpleName());
    public static final String IMG = makeCardPath("cascade.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 2;


    public Cascade() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int damageroll = 0;
        if (this.upgraded) {
            damageroll += roll(12,'a');
            for (int i = 1; i < 6; i++) {
                damageroll += roll(12,'r');
            }
        } else {
            damageroll += roll(13,'a');
            for (int i = 1; i < 4; i++) {
                damageroll += roll(13,'r');
            }
        }
        this.baseDamage = damageroll;
        this.calculateCardDamage(m);
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
        this.addToBot(new ApplyPowerAction(p, p, new CursePower(p, 6)));
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
            upgradeMagicNumber(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
