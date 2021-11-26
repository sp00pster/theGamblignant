package theGamblignant.cards;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
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

public class MindControl extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(MindControl.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 3;

    public MindControl() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    //mostly copied from The Firefly's Mind Control card. bless Vex'd for coming up with the same idea as me

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.getIntentBaseDmg() > -1 && !m.hasPower("Artifact"))
            if ((Boolean) ReflectionHacks.getPrivate(m, AbstractMonster.class, "isMultiDmg")) {
                for (int i = 0; i < (Integer) ReflectionHacks.getPrivate(m, AbstractMonster.class, "intentMultiAmt"); i++) {
                    AbstractMonster q = AbstractDungeon.getRandomMonster();
                    this.addToBot(new DamageAction(q, new DamageInfo(p, m.getIntentDmg(), damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    }
            } else {
                AbstractMonster q = AbstractDungeon.getRandomMonster();
                logger.info(m.getIntentDmg());
                this.addToBot(new DamageAction(q, new DamageInfo(p, m.getIntentDmg(), damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        this.addToBot(new StunMonsterAction(m, p));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.selfRetain = true;
            initializeDescription();
        }
    }
}
