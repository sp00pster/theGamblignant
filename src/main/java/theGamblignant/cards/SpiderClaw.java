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

import static theGamblignant.VriskaMod.makeCardPath;

public class SpiderClaw extends AbstractVriskaCard {

        public static final String ID = VriskaMod.makeID(SpiderClaw.class.getSimpleName());
        public static final String IMG = makeCardPath("spiderclaw.png");
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

        private static final CardRarity RARITY = CardRarity.UNCOMMON;
        private static final CardTarget TARGET = CardTarget.ENEMY;
        private static final CardType TYPE = CardType.ATTACK;
        public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

        private static final int COST = 1;
        private static final int UPGRADED_COST = 1;

        public SpiderClaw() {
            super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
            baseMagicNumber = 4;
            magicNumber = baseMagicNumber;
        }

        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            this.baseDamage = roll(4,'a');
            int times = roll(magicNumber,'a');
            this.calculateCardDamage(m);
            for (int i = 0; i < times; i++) {
                AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }

    public void applyPowers() {
        super.applyPowers();
        int addeddamage = 0;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;}
        if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {addeddamage += AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount;}
        if (!this.upgraded) {
            if (addeddamage > 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + addeddamage + cardStrings.EXTENDED_DESCRIPTION[2];
            } else if (addeddamage < 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + -addeddamage + cardStrings.EXTENDED_DESCRIPTION[2];
            } else {this.rawDescription = cardStrings.DESCRIPTION;}
        } else {
            if (addeddamage > 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + addeddamage + cardStrings.EXTENDED_DESCRIPTION[3];
            } else if (addeddamage < 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + -addeddamage + cardStrings.EXTENDED_DESCRIPTION[3];
            } else {this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;}
        }
        this.initializeDescription();
    }

        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeMagicNumber(2);
                upgradeBaseCost(UPGRADED_COST);
                this.rawDescription = UPGRADE_DESCRIPTION;
                initializeDescription();
            }
        }
}
