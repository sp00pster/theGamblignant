package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class SpiderClaw extends AbstractVriskaCard {

        public static final String ID = VriskaMod.makeID(SpiderClaw.class.getSimpleName());
        public static final String IMG = makeCardPath("Attack.png");
        private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

        private static final CardRarity RARITY = CardRarity.COMMON;
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
            int times = roll(magicNumber,'a');
            for (int i = 0; i < times; i++) {
                AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, roll(4,'a'), damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
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
