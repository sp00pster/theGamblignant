package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;
import theGamblignant.powers.CursePower;

import java.util.Iterator;

import static theGamblignant.VriskaMod.makeCardPath;

public class ScourgePurge extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(ScourgePurge.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 1;

    public ScourgePurge() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("Curse")) {
            AbstractPower curse = AbstractDungeon.player.getPower("Curse");
            if (!this.upgraded) {
                this.addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, curse.amount), curse.amount, AbstractGameAction.AttackEffect.POISON));
            } else {
                Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

                while (var3.hasNext()) {
                    AbstractMonster mo = (AbstractMonster) var3.next();
                    this.addToBot(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, curse.amount), curse.amount, AbstractGameAction.AttackEffect.POISON));
                }
            }
            this.addToBot(new RemoveSpecificPowerAction(p, p, CursePower.POWER_ID));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.target = CardTarget.ALL_ENEMY;
            initializeDescription();
        }
    }
}
