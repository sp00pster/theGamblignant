package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class SpiderClaw extends AbstractVriskaCard {

        // TEXT DECLARATION

        public static final String ID = VriskaMod.makeID(SpiderClaw.class.getSimpleName());
        public static final String IMG = makeCardPath("Attack.png");


        // /TEXT DECLARATION/


        // STAT DECLARATION

        private static final CardRarity RARITY = CardRarity.COMMON;
        private static final CardTarget TARGET = CardTarget.ENEMY;
        private static final CardType TYPE = CardType.ATTACK;
        public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

        private static final int COST = 1;
        private static final int UPGRADED_COST = 1;

        private int TIMES = 4;
        private final int UPGRADE_TIMES = 6;

        // /STAT DECLARATION/


        public SpiderClaw() {
            super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
            magicNumber = TIMES;
        }


        // Actions the card should do.
        @Override
        public void use(AbstractPlayer p, AbstractMonster m) {
            int times = roll(p,magicNumber);
            for (int i = 0; i < times; i++) {
                AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, roll(p,4), damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }


        // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                TIMES = UPGRADE_TIMES;
                upgradeBaseCost(UPGRADED_COST);
                initializeDescription();
            }
        }
}
